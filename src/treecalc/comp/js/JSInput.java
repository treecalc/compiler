package treecalc.comp.js;

/* use symbol tables / scopes to resolve names */

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.GenInput;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.TcSimpleParser;
import treecalc.comp.GenInput.GenInputCalc;


public class JSInput {
	/* TODO: put autocounters into index, index2 for unchecked, check, all calcs */
	/* TODO: optimize list things when table name / column name is a string -> static instead of dynamic access */
	/* TODO: optimize list things to get data-array and do everything 'inline' to minimize number of function calls */
	/* TODO: get rid of index and index2 in case they are not used */
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	private int stmtsmethod=0;
	private final int MAX_STMTS_IN_METHOD=5000;
	private GenInput input;
	private HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();
	private JSPrintHelper p;
	
	public static final String NS_INPUT = "tc.i";
	private static final String NS_HELPER = NS_INPUT + ".helper";
	

	// list of TcAst: ^(TT_RESULTDEF id arguments? formula)
	
	private JSInput(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		String classname = "I";
		out = new PrintStream(foldernameout + "/" + classname + ".js", "ISO-8859-1");
		p = new JSPrintHelper(out);
		
		out.println(NS_INPUT  + " = {};");
		out.println(NS_HELPER  + " = {");		
		printHelperIndex(1);
		out.println("}");
		print();		
		out.close();
	}

	private void print() {
		// printDynamicDispatch();				
		for (int i=0; i<model.getInputSize(); i++) {
			input = model.getInput(i);
			printInput();
		}
		// printConstants();
	}

	private void printHelperIndex(int indent) {		
		p.pl(indent, "getIndexNorm: function(indices) {");
		p.pl(indent+1, "if (undefined==indices) {");
		p.pl(indent+2, "return 0;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var len = indices.length;");
		p.pl(indent+1, "var lastnotzero = -1;");
		p.pl(indent+1, "for (var i=len; --i>=0;) {");
		p.pl(indent+2, "if (+indices[i] > 0) {");
		p.pl(indent+3, "lastnotzero=i;");
		p.pl(indent+3, "break;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "if (lastnotzero<0) {");
		p.pl(indent+2, "return 0;");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "return indices.slice().slice(0, lastnotzero+1);");
		p.pl(indent, "}");		
	}
	
	private void printInput() {
		String inputname = input.getName().toUpperCase();
		String funcname = inputname;
		
		out.println();
		int indent = 0;
		p.pl(indent, NS_INPUT + "." + inputname + " = {");
		printValue(indent+1);
		
		printList(1, funcname);
		printUnchecked(1);
		
		for (GenInputCalc genCalc : input.getCalcs()) {
			printInputcalc(indent+1, genCalc);
		}
		
		p.pl(indent+1, "finalElement: {}");
		p.pl(indent, "}");
	}
	
	private void printValue(int indent) {
		String inputname = input.getName().toUpperCase();
		p.pl(indent, "$$value: function(_s /*, ... indices */) {");		
		if (input.hasCheck()) {			
			p.pl(indent+1, "return this.$check.apply(this, Array.prototype.slice.call(arguments));");
		} else {
			p.printArgumentsToArrayAndShift(indent+1);
			int[] autocounters = model.getInputAutocounters(input);
			if (autocounters!=null) {
//				out.println("      if (_index==null) {");
//				String scounters = "new int[] " + Arrays.toString(autocounters).replace('[', '{').replace(']', '}');
//				out.print  ("         return _s.getInputAutocounter(");
//				out.print  (input.getIndex());
//				out.print  (", ");
//				out.print  (scounters);
//				out.println(" /* " + inputname + "*/" + ");");
//				out.println("      } else {");
//				out.print  ("         return _s.getInput(");
//				out.print  (input.getIndex());
//				out.print  (" /* " + inputname + " */ ");
//				out.println(", _index);");
//				out.println("      }");
			} else {
				p.pl(indent+1, "var args = indices.slice();");
				p.pl(indent+1, "args.unshift(" + input.getIndex() + ");");				
				p.pl(indent+1, "return tc.s.getInput.apply(_s, args);");				
			}
		}
		p.pl(indent, "},");
	}
	
	private void printUnchecked(int indent) {		
		p.pl(indent, "$unchecked: function(_s /*, ... indices */) {");		

		int[] autocounters = model.getInputAutocounters(input);
		p.printArgumentsToArrayAndShift(indent+1);
		if (autocounters!=null) {
			p.pl(indent+1, "if (indices.length > 0) {");			
			p.p(indent+2, "return _s.getInputAutocounter(");
			out.print(input.getIndex());
			out.print(", ");
			out.print(Arrays.toString(autocounters));
			out.println(");");
			p.pl(indent+1, "} else {");
			p.pl(indent+2, "return _s.getInput(" + input.getIndex() + ", indices);");
			p.pl(indent+1, "}");			
		} else {			
			p.pl(indent+1, "return _s.getInput(" + input.getIndex() + ", indices);");			
		}
		p.pl(indent, "},");
	}
	
	private void printList(int indent, String funcname) {
		/* method to query list */
		
		p.pl(indent, "$$list: function(_s /* ,... indices */) {");
		p.printArgumentsToArrayAndShift(indent+1);
		if (input.isChoiceable()) {
			if (input.hasVector()) {				
				p.pl(indent+1, "var list = this.$vector(_s, indices);");
				p.pl(indent+1, "var ret = [];");
				p.pl(indent+1, "for (var i=0; i<list.length; i++) {");
				p.pl(indent+2, "var key = list[i][0];");
				p.pl(indent+2, "var value = list[i][1];");
				p.pl(indent+2, "ret.push([key, value]);");
				p.pl(indent+1, "}");
				p.pl(indent+1, "return ret;");
			} else if (input.hasReference()) {
				out.print  ("      final V submodelname = ");
				//out.print  (funcname);
				out.println("$REFERENCE(_s, _index);");
				// out.println("      final List<String[]> list = _s.getSubmodel(submodelname.stringValue()).getInputList(\"" + inputname + "\");");
				if (!input.hasFilter() && !input.hasDisplaytext()) {
					out.println("      return list;");
				} else {
					out.println("      final int len = list.size();");
					out.println("      final ArrayList<String[]> ret = new ArrayList<String[]>(len);");
					out.println("      for (int indrow=0; indrow<len; indrow++) {");
					out.println("         final String[] keyvalue = list.get(indrow);");
					out.println("         final String key = keyvalue[0];");
					out.println("         final V vkey = V.getInstance(key);");
					if(input.hasFilter()) {
						out.print  ("         if (!");
						// out.print  (funcname);
						out.println("$FILTER$internal(_s, vkey, _index).booleanValue()) {");
						out.println("            continue;");
						out.println("         }");
					}
					out.println("         V text = V.getInstance(keyvalue[1]);");
					if (input.hasDisplaytext()) {
						out.print  ("         text = ");
						// out.print  (funcname);
						out.println("$DISPLAYTEXT$internal(_s, key, text, _index);");
						out.println("         ret.add(new String[] { key, text.stringValue() });");
					} else {
						out.println("         ret.add(keyvalue);");
					}
					out.println("      }");
					out.println("      return ret;");
				}
			} else { //if input.hasTable()
				p.pl(indent+1, "var tabname = this.$table(arguments).toUpperCase();");												
				p.pl(indent+1, "var table = " + JSTables.NS_TABLES + "[tabname];");
				// V.getInstanceTabref(Tables.getTableid(vtabname.stringValue()));");
				p.pl(indent+1, "var numrows = table.getNumrows();");
								
				if (!input.hasFilter() && !input.hasDisplay() && !input.hasDisplaytext()) {					
					p.pl(indent+1, "return table.getCells(1, numrows, 1, 2);");										
				} else {
					p.pl(indent+1, "var ret = [];");
					p.pl(indent+1, "var colindText = 1;");					
					if (input.hasDisplay()) {
						p.pl(indent+1, "var colname = this.$display(_s, indices);");
						p.pl(indent+1, "colindText = table.getColindex(colname);");						
					}
					p.pl(indent+1, "for (var row=0; row<numrows; row++) {");
					p.pl(indent+2, "var key = table.get(row, 0);");
					if(input.hasFilter()) {
						p.pl(indent+2, "if (!this.$filter$internal(_s, key, indices)) {");
						p.pl(indent+3, "continue;");
						p.pl(indent+2, "}");
					}
					p.pl(indent+2, "var text = table.get(row, colindText);");					
					if (input.hasDisplaytext()) {
						p.pl(indent+2, "text = this.$displaytext$internal(_s, key, text, indices);");						
					}
					p.pl(indent+2, "ret.push([key, text]);");
					p.pl(indent+1, "}");
					p.pl(indent+1, "return ret;");
				}
			}
		} else {
			out.println("      return null;");
		}
		p.pl(indent, "},");
	}
	
	private void printInputcalc(int indent, GenInputCalc genCalc) {
		String inputname = input.getName();
		TcAst calc = genCalc.getCalcast();
		//^(TT_RESULTDEF id arguments? formula)
		
		TcAst id = calc.getChild(0);
		TcAst next = calc.getChild(1);
		TcAst args = next.getType()==TcSimpleParser.TT_ARGDEF ? next : null;
		TcAst formula = calc.getChild(calc.getChildCount()-1);
		
		String calcname = id.getText().toLowerCase();
		
		
		Scope scope = calc.getScope();
		Symbol symbol = scope.resolve(calcname, 0, false);
		if (symbol==null) {
			throw new RuntimeException("could not resolve " + calcname + ", line " + id.getLine() + ", in scope " + scope.getFullScopeName() + ": " + scope.getScopeDefines());
		}
		if (!(symbol instanceof ScopedSymbol)) {
			throw new RuntimeException("printFunction: expected ScopedSymbol, but got " + symbol.getClass()+ " for symbol " + symbol.getSymbolName() + ", " + symbol.toString());
		}
		ScopedSymbol calcsymbol = (ScopedSymbol) symbol;
		TcAst node = (TcAst) calcsymbol.getAst();

		
		String funcname = "$" + calcname;
		List<Symbol> params = calcsymbol.getScopeDefines();
		
		
		p.pl(indent, funcname + ": function(_s /* ... indices '*/) {");
		p.printArgumentsToArrayAndShift(indent+1);		
		ArrayList<String> specialnames = new ArrayList<String>();
		if (params.size()>2) {			
			for (Symbol param : params) {
				String name = param.getSymbolName().toUpperCase();
				if (name.equals("INDEX")) {
				} else if (name.equals("INDEX2")) {
				} else {					
					specialnames.add(name);				
					p.pl(indent+1, "var " + name.toLowerCase() + " = 0;");
				}
			}						
			p.p(indent+1, "return this.");			
			out.print  (funcname + "$internal(_s");
			for (String specialname : specialnames) {
				out.print(", ");
				out.print(specialname.toLowerCase());
			}
			out.println(", indices);");			
		} else {
//			int[] autocounters = model.getInputAutocounters(input);
//			if (autocounters!=null) {
//				out.println("      if (_index==null) {");
//				out.print  ("         _index = _s.getAutocounterValues(new int[] ");
//				out.print  (Arrays.toString(autocounters).replace('[', '{').replace(']','}'));
//				out.println(");");
//				out.println("      }");
//			}
			/*
			 * In der Formel können index und index2 (= ein VPMS Relikt) vorkommen.
			 */
			p.pl(indent+1, "var index = indices.length > 0 ? " + NS_HELPER + ".getIndexNorm(indices) : 0;");
			p.pl(indent+1, "var index2 = indices.length > 1 ? indices[1] : 0;");						
			printFormula("return ", formula);			
		}
		p.pl(indent, "},");
		

		if (params.size()>2) {
			p.p(indent, funcname + "$internal: function(_s ");
			for (String specialname : specialnames) {
				out.print(", ");
				out.print(specialname.toLowerCase());
			}			
			out.println(", indices) {");			
			p.pl(indent+1, "var index = indices.length > 0 ? indices : 0;");
			p.pl(indent+1, "var index2 = indices.length > 1 ? indices[1] : 0;");			
			printFormula("return ", formula);					
			p.pl(indent, "},");
		}		
	}

	private void printFormula(String prefix, TcAst child) {
		PrintFormulaInfos printFormulaInfos = JSFormula.printFormula(out, child, model, 2, prefix, "");
		HashMap<String, FormulaConstant> constantMap = printFormulaInfos.getConstants();
		for (Entry<String,FormulaConstant> entry : constantMap.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			constants.put(constname, constant);
		}
	}

//	private void printConstants() {
//		out.println();
//		for (Entry<String,Formula.FormulaConstant> entry : constants.entrySet()) {
//			String constname = entry.getKey();
//			Formula.FormulaConstant constant = entry.getValue();
//			String name = constant.getNameInMethod();
//			String createStatement = constant.getCreatestatement();
//			V vconstant = constant.getConstant();
//			out.print  ("   private final static V ");
//			out.print  (name);
//			out.print  (" = ");
//			out.print  ("_C.");
//			out.print  (name);
//			out.println(";");
//		}
//	}

	private void printDynamicDispatch() {
		out.println();
		out.println("   /* calcid: -2->input access, -1->list, inputcalcid otherwise */");
		out.println("   public final static Object dynamicCall(int inpindex, int calcid, S _s, V...index) {");
		out.println("      switch(inpindex) {");


		for (int i=0; i<model.getInputSize(); i++) {
			GenInput input = model.getInput(i);
			String inputname = input.getName().toUpperCase();
			out.print  ("      case ");
			out.print  (String.format("%4d", i));
			out.println(": switch(calcid) {");
			out.print  ("         case -2: return ");
			out.print  (inputname);
			out.println("(_s, index);");
			out.print  ("         case -1: return ");
			out.print  (inputname);
			out.println("$$LIST(_s, index); ");
			ArrayList<GenInputCalc> inputcalcs = input.getCalcs();
			for (GenInputCalc gencalc : inputcalcs) {
				String inputcalcname = gencalc.getCalcname();
				int inputcalcindex = model.getInputcalcIndex(inputcalcname);
				if (inputcalcindex<0) {
					throw new RuntimeException("did not find inputcalcindex for " + inputcalcname + ", line " + gencalc.getCalcast().getLine());
				}
				out.print  ("         case ");
				out.print  (inputcalcindex);
				out.print  (": return ");
				out.print  (inputname);
				out.print  ('$');
				out.print  (inputcalcname.toUpperCase());
				out.println("(_s, index);");
			}
			out.println("         default: throw new ExceptionCalculation(\"->036< calc undefined\", \"" + inputname + "\");");
			out.println("         }");
		}
		out.println("      default: throw new RuntimeException(\"invalid input reference: \" + inpindex);");
		out.println("      } //end of switch");
		out.println("   }");
	}
	
	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JSInput obj = new JSInput(model, foldernameout, packagename);
		obj.run();
	}
}
