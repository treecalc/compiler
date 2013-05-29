/* use symbol tables / scopes to resolve names */

package treecalc.comp.java; 

import static treecalc.comp.TcSimpleParser.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.GenInput;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.GenInput.GenInputCalc;

import treecalc.rt.ExceptionCalculation;
import treecalc.rt.V;
import treecalc.rt.VDouble;

public class JavaInput {
	/* TODO: put autocounters into index, index2 for unchecked, check, all calcs */
	/* TODO: optimize list things when table name / column name is a string -> static instead of dynamic access */
	/* TODO: optimize list things to get data-array and do everything 'inline' to minimize number of function calls */
	/* TODO: get rid of index and index2 in case they are not used */
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private final boolean gwt;
	private final boolean trace;
	private PrintStream out;
	private int stmtsmethod=0;
	private final int MAX_STMTS_IN_METHOD=5000;
	private GenInput input;
	private HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();

	// list of TcAst: ^(TT_RESULTDEF id arguments? formula)
	
	private JavaInput(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
		this.gwt = gwt;
		this.trace = trace;
	}

	private String getClassnameCopyOf() {
		return gwt ? "ArraysHelper" : "Arrays";
	}
	
	private void run() throws IOException {
		String classname = "I";
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
		}
		out.println("import java.util.ArrayList;");
		out.println("import java.util.Arrays;");
		out.println("import java.util.HashMap;");
		out.println("import java.util.List;");
		out.println();
		out.println("import com.hackhofer.tc.rt.ExceptionCalculation;");
		out.println("import com.hackhofer.tc.rt.V;");
		out.println("import com.hackhofer.tc.rt.VDouble;");
		out.println("import com.hackhofer.tc.rt.VList;");
		out.println("import com.hackhofer.tc.rt.VNull;");
		out.println("import com.hackhofer.tc.rt.S;");
		out.println();
		out.println("import static com.hackhofer.tc.rt.B.*;");
		out.println("import static com.hackhofer.tc.rt.V.*;");
		if(gwt) {
			out.println("import com.hackhofer.tc.rt.gwt." + getClassnameCopyOf() + ";");
		}

		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		print();
		out.println("}");
		out.close();
	}

	private void print() {
		printDynamicDispatch();
		printHelperIndex();
		for (int i=0; i<model.getInputSize(); i++) {
			input = model.getInput(i);
			printInput();
		}
		printConstants();
	}

	private void printHelperIndex() {
		out.println();
		out.println("   /* returns normalized index */");
		out.println("   private final static V getIndexNorm(final V[] index) {");
		out.println("      if (index==null) {");
		out.println("         return VDouble.vdbl0;");
		out.println("      }");
		out.println("      final int len=index.length;");
		out.println("      int lastnotzero = -1;");
		out.println("      for (int i=len; --i>=0; ) {");
		out.println("         if(index[i].doubleValue()>0) {");
		out.println("            lastnotzero=i; ");
		out.println("            break;");
		out.println("         }");
		out.println("      }");
		out.println("      if (lastnotzero<0) {");
		out.println("         return VDouble.vdbl0;");
		out.println("      }");
		out.println("      return V.getInstance(" + getClassnameCopyOf() + ".copyOf(index, lastnotzero+1));");
		out.println("   }");
	}
	private void printInput() {
		String inputname = input.getName().toUpperCase();
		String funcname = inputname;
		out.println();
		out.print("   public static V ");
		out.print(funcname);
		out.println("(final S _s, final V..._index) {");
		if (input.hasCheck()) {
			out.print("      return ");
			out.print(funcname);
			out.println("$CHECK(_s, _index);");
		} else {
			int[] autocounters = model.getInputAutocounters(input);
			if (autocounters!=null) {
				out.println("      if (_index==null) {");
				String scounters = "new int[] " + Arrays.toString(autocounters).replace('[', '{').replace(']', '}');
				out.print  ("         return _s.getInputAutocounter(");
				out.print  (input.getIndex());
				out.print  (", ");
				out.print  (scounters);
				out.println(" /* " + inputname + "*/" + ");");
				out.println("      } else {");
				out.print  ("         return _s.getInput(");
				out.print  (input.getIndex());
				out.print  (" /* " + inputname + " */ ");
				out.println(", _index);");
				out.println("      }");
			} else {
				out.print  ("      return _s.getInput(");
				out.print  (input.getIndex());
				out.print  (" /* " + inputname + " */ ");
				out.println(", _index);");
			}
		}
		out.println("   }");

		/* method to query list */
		out.println();
		out.print("   public static List<String[]> ");
		out.print  (funcname);
		out.print  ("$$LIST");
		out.println("(final S _s, final V... _index) {");
		if (input.isChoiceable()) {
			if (input.hasVector()) {
				//vector
				out.print  ("      V vlist = ");
				out.print  (funcname);
				out.print  ("$VECTOR");
				out.println("(_s, _index);");
				out.println("      if (vlist instanceof VList) {");
				out.println("         final List<V> olist = vlist.listValue();");
				out.println("         final int len = olist.size();");
				out.println("         final ArrayList<String[]> ret = new ArrayList<String[]>(len);");
				out.println("         for (V elem : olist) {");
				out.println("            final List<V> ilist = elem.listValue();");
				out.println("            final String key = ilist.get(0).stringValue();");
				out.println("            final String value = ilist.get(1).stringValue();");
				out.println("            ret.add(new String[] { key, value });");
				out.println("         }");
				out.println("         return ret;");
				out.println("      } else {");
				out.println("         return null;");
				out.println("      }");
			} else if (input.hasReference()) {
				out.print  ("      final V submodelname = ");
				out.print  (funcname);
				out.println("$REFERENCE(_s, _index);");
				out.println("      final List<String[]> list = _s.getSubmodel(submodelname.stringValue()).getInputList(\"" + inputname + "\" + (_index.length>0 ? Arrays.toString(_index) : \"\"));");
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
						out.print  (funcname);
						out.println("$FILTER$internal(_s, vkey, _index).booleanValue()) {");
						out.println("            continue;");
						out.println("         }");
					}
					out.println("         V text = V.getInstance(keyvalue[1]);");
					if (input.hasDisplaytext()) {
						out.print  ("         text = ");
						out.print  (funcname);
						out.println("$DISPLAYTEXT$internal(_s, key, text, _index);");
						out.println("         ret.add(new String[] { key, text.stringValue() });");
					} else {
						out.println("         ret.add(keyvalue);");
					}
					out.println("      }");
					out.println("      return ret;");
				}
			} else { //if input.hasTable()
				out.print  ("      final V vtabname = ");
				out.print  (funcname);
				out.println("$TABLE(_s, _index);");
				out.println("      final V vtabref = V.getInstanceTabref(Tables.getTableid(vtabname.stringValue()));");
				out.print  ("      final V vnumrows = ");
				out.println("(V) Tables.dynamicCall(vtabref, Tables.CALL_NUMROWS);");
				if (!input.hasFilter() && !input.hasDisplay() && !input.hasDisplaytext()) {
					out.print  ("      V vlist = ");
					out.println("(V) Tables.dynamicCall(vtabref, Tables.CALL_CELL_R_R, V.getInstance(1), vnumrows, V.getInstance(1), V.getInstance(2));");
					out.println("      if (vlist instanceof VList) {");
					out.println("         final List<V> olist = vlist.listValue();");
					out.println("         final int len = olist.size();");
					out.println("         final ArrayList<String[]> ret = new ArrayList<String[]>(len);");
					out.println("         for (V elem : olist) {");
					out.println("            final List<V> ilist = elem.listValue();");
					out.println("            final String key = ilist.get(0).stringValue();");
					out.println("            final String value = ilist.get(1).stringValue();");
					out.println("            ret.add(new String[] { key, value });");
					out.println("         }");
					out.println("         return ret;");
					out.println("      } else {");
					out.println("         return null;");
					out.println("      }");
				} else {
					out.println("      final int numrows = (int) vnumrows.doubleValue();");
					out.println("      final ArrayList<String[]> ret = new ArrayList<String[]>(numrows);");
					out.println("      int colindText = 1;");
					if (input.hasDisplay()) {
						out.print  ("      final V colname = ");
						out.print  (funcname);
						out.println("$DISPLAY(_s, _index);");
						out.println("      colindText = (Integer) Tables.dynamicCall(vtabref, Tables.CALL_COLINDEX, colname.stringValue());");
					}
					out.println("      for (int indrow=0; indrow<numrows; indrow++) {");
					out.println("         V key = (V) Tables.dynamicCall(vtabref, Tables.CALL_GET, indrow, 0);");
					if(input.hasFilter()) {
						out.print  ("          if (!");
						out.print  (funcname);
						out.println("$FILTER$internal(_s, key, _index).booleanValue()) {");
						out.println("            continue;");
						out.println("          }");
					}
					out.println("         V text = (V) Tables.dynamicCall(vtabname, Tables.CALL_GET, indrow, colindText);");
					if (input.hasDisplaytext()) {
						out.print  ("         text = ");
						out.print  (funcname);
						out.println("$DISPLAYTEXT$internal(_s, key, text, _index);");
					}
					out.println("         ret.add(new String[] { key.stringValue(), text.stringValue() });");
					out.println("      }");
					out.println("      return ret;");
				}
			}
		} else {
			out.println("      return null;");
		}
		out.println("   }");
		
		out.println();
		out.print("   public static V ");
		out.print(funcname);
		out.print("$UNCHECKED");
		out.println("(final S _s, final V..._index) {");

		int[] autocounters = model.getInputAutocounters(input);
		if (autocounters!=null) {
			out.println("      if (_index==null) {");
			String scounters = "new int[] " + Arrays.toString(autocounters).replace('[', '{').replace(']', '}');
			out.print  ("         return _s.getInputAutocounter(");
			out.print  (input.getIndex());
			out.print  (", ");
			out.print  (scounters);
			out.println(" /* " + inputname + "*/" + ");");
			out.println("      } else {");
			out.print  ("         return _s.getInput(");
			out.print  (input.getIndex());
			out.print  (" /* " + inputname + " */ ");
			out.println(", _index);");
			out.println("      }");
		} else {
			out.print  ("      return _s.getInput(");
			out.print  (input.getIndex());
			out.print  (" /* " + inputname + " */ ");
			out.println(", _index);");
		}
		out.println("   }");
		
		for (GenInputCalc genCalc : input.getCalcs()) {
			printInputcalc(genCalc);
		}
	}
	private void printInputcalc(GenInputCalc genCalc) {
		String inputname = input.getName();
		TcAst calc = genCalc.getCalcast();
		//^(TT_RESULTDEF id arguments? formula)
		
		TcAst id = calc.getChild(0);
		TcAst next = calc.getChild(1);
		TcAst args = next.getType()==TT_ARGDEF ? next : null;
		TcAst formula = calc.getChild(calc.getChildCount()-1);
		
		String calcname = id.getText().toUpperCase();
		
		
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

		out.println();
		String funcname = inputname + "$" + calcname;
		List<Symbol> params = calcsymbol.getScopeDefines();
		out.print  ("   public static V ");
		out.print  (funcname);
		out.println("(final S _s, V... _index) {");
		ArrayList<String> specialnames = new ArrayList<String>();
		if (params.size()>2) {
			for (Symbol param : params) {
				String name = param.getSymbolName().toUpperCase();
				if (name.equals("INDEX")) {
				} else if (name.equals("INDEX2")) {
				} else {
					specialnames.add(name);
					out.print  ("      final V ");
					out.print  (name.toLowerCase());
					out.println(" = VNull.vnull;");
				}
			}
			out.print  ("      return ");
			out.print  (funcname + "$internal(_s");
			for (String specialname : specialnames) {
				out.print(", ");
				out.print(specialname.toLowerCase());
			}
			out.println(", _index);");
		} else {
			int[] autocounters = model.getInputAutocounters(input);
			if (autocounters!=null) {
				out.println("      if (_index==null) {");
				out.print  ("         _index = _s.getAutocounterValues(new int[] ");
				out.print  (Arrays.toString(autocounters).replace('[', '{').replace(']','}'));
				out.println(");");
				out.println("      }");
			} 
			out.println("      final int _indexlen = _index!=null ? _index.length : 0;");
			out.println("      final V index = _indexlen>0 ? getIndexNorm(_index) : VDouble.vdbl0;");
			out.println("      final V index2 = _indexlen>1 ? _index[1] : VDouble.vdbl0;");
			printFormula("return ", formula);
		}
		out.println("   }");

		if (params.size()>2) {
			out.println();
			out.print  ("   public static V ");
			out.print  (funcname + "$internal");
			out.print  ("(final S _s");
			for (String specialname : specialnames) {
				out.print(", final V ");
				out.print(specialname.toLowerCase());
			}
			out.print(", final V... _index");
			out.println(") {");
			out.println("      final int _indexlen = _index!=null ? _index.length : 0;");
			out.println("      final V index = _indexlen>0 ? getInstance(_index) : VDouble.vdbl0;");
			out.println("      final V index2 = _indexlen>1 ? _index[1] : VDouble.vdbl0;");
			printFormula("return ", formula);
			out.println("   }");
		}
	}

	private void printFormula(String prefix, TcAst child) {
		PrintFormulaInfos printFormulaInfos = JavaFormula.printFormula(out, child, model, 2, prefix, "");
		HashMap<String, FormulaConstant> constantMap = printFormulaInfos.getConstants();
		for (Entry<String,FormulaConstant> entry : constantMap.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			constants.put(constname, constant);
		}
	}

	private void printConstants() {
		out.println();
		for (Entry<String,FormulaConstant> entry : constants.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
			out.print  ("   private final static V ");
			out.print  (name);
			out.print  (" = ");
			out.print  ("_C.");
			out.print  (name);
			out.println(";");
		}
	}

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
	
	public static void generate(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) throws IOException {
		JavaInput obj = new JavaInput(model, foldernameout, packagename, gwt, trace);
		obj.run();
	}
}
