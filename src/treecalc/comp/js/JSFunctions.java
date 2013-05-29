package treecalc.comp.js;

/* use symbol tables / scopes to resolve names */

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.GenFunction;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;
import treecalc.comp.TcSimpleParser;

import treecalc.rt.V;

public class JSFunctions {
	public final static boolean DEBUGOUT = false;
	public final static boolean USE_REFLECT_FUNCALL = false;
	public final static int USE_REFLECT_GENCALL_NUMFUNC = 100;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	private int stmtsmethod=0;
	private final int MAX_STMTS_IN_METHOD=5000;
	HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();
	// list of TcAst: ^(TT_RESULTDEF id arguments? formula)
	public static String NS_FUNCTIONS = "tc.f";
	public static String NS_HELPER = "tc.f.helper";
	private JSPrintHelper p;
	
	
	private JSFunctions(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		String classname = "F";
		out = new PrintStream(foldernameout + "/" + classname + ".js", "ISO-8859-1");
		p = new JSPrintHelper(out);
		p.pl(0, NS_FUNCTIONS + " = {}");
		print();
		printHelper();
				
//		printDynamicDispatch();		
//		printConstants();
				
		out.close();
	}
	
	private void printHelper() {
		int indent = 0;
		p.pl(indent, NS_HELPER + " = {");
		printFn(indent+1);
//		printGetFuncIndex(indent+1);
		p.pl(indent, "}");
	}
	
	private void printFn(int indent) {		
		p.pl(indent, "fn: [");
		int size = model.getFunctionSize();
		if (size > 0) {			
			for (int i=0; i<size-1; i++) {
				GenFunction function = model.getFunction(i);			
				p.pl(indent+1, "'" + function.getName() + "',");
			}
			p.pl(indent+1, "'" + model.getFunction(size-1).getName() + "'");
		}
		p.pl(indent, "],");		
	}

	private void printGetFuncIndex(int indent) {
		p.pl(indent, "getFuncIndex: function(name) {");
		p.pl(indent+1, "var i = this.fn.indexOf(name);");
		p.pl(indent+1, "return (undefined === i) ? -1 : i;");
		p.pl(indent, "}");		
	}
	
	private void printDynamicDispatch() {
		
		
		
		out.println("   public final static V getFuncref(String funcnameUpper) {");
		out.println("      Integer i = fn.get(funcnameUpper);");
		out.println("      if (i==null) {");
		out.println("         throw new RuntimeException(\"invalid function name: \" + funcnameUpper);");
		out.println("      }");
		out.println("      return V.getInstanceFuncref(i);");
		out.println("   }");
		out.println();
		out.println("   public final static V getFuncref(V funcname) {");
		out.println("      String name = funcname.stringValue().toUpperCase();");
		out.println("      Integer i = fn.get(name);");
		out.println("      if (i==null) {");
		out.println("         throw new RuntimeException(\"invalid function name: \" + name);");
		out.println("      }");
		out.println("      return V.getInstanceFuncref(i);");
		out.println("   }");
		
		out.println();
		out.println("   public final static V dynamicCall(V func, S s, V ... args) {");
		out.println("      int index = func.funcrefValue();");
		out.println("      switch(index) {");
		for (int i=0; i<model.getFunctionSize(); i++) {
			GenFunction function = model.getFunction(i);
			out.print("      case ");
			out.print(String.format("%4d", i));
			out.print(": return ");
			out.print(function.getName());
			out.print("(s");
			for (int j=0; j<function.getArgSize(); j++) {
				out.print(", args[");
				out.print(j);
				out.print(']');
			}
			out.println(");");
		}
		out.println("      default: throw new RuntimeException(\"invalid function reference: \" + func);");
		out.println("      } //end of switch");
		out.println("   }");
	}

	private void print() {
		p.pl(0, NS_FUNCTIONS + " = {");
		for (int i=0; i<model.getFunctionSize(); i++) {
			GenFunction function = model.getFunction(i);
			printFunction(function.getAst());
		}
		p.pl(1, "finalElement: {}");
		p.pl(0, "}");
	}
	private void printFunction(TcAst calc) {
		//^(TT_RESULTDEF id arguments? formula)
		TcAst id = calc.getChild(0);
		TcAst next = calc.getChild(1);
		TcAst args = next.getType()==TcSimpleParser.TT_ARGDEF ? next : null;
		int nargs = args!=null ? args.getChildCount() : 0;
		TcAst formula = calc.getChild(calc.getChildCount()-1);
		String funcname = id.getText().toUpperCase();
		
		Scope scope = calc.getScope();
		Symbol symbol = scope.resolve(funcname, nargs, false);
		if (!(symbol instanceof ScopedSymbol)) {
			throw new RuntimeException("printFunction: expected ScopedSymbol, but got " + symbol.getClass()+ " for symbol " + symbol.getSymbolName() + ", " + symbol.toString());
		}
		ScopedSymbol calcsymbol = (ScopedSymbol) symbol;
		TcAst node = (TcAst) calcsymbol.getAst();

		int indent = 1;
		p.p(indent, funcname + ": function");
		printArguments(calcsymbol);
		out.println();
		
//		out.print("line ");
//		out.println(calc.getLine());
		
		/* caching */
		GenFunction genfunc = model.getFunction(funcname);
		int funcindex = genfunc.getIndex();
		if (JSFormula.isSimple(model, formula)) {
			printFormula("return ", formula);			
		} else {
			p.p(indent+1, "var cacheKey = _s.getCacheKey(" + model.getCacheId(funcname));
			for (Symbol argument : calcsymbol.getScopeDefines()) {
				String argname = argument.getSymbolName();
				out.print(", ");
				out.print(argname.toLowerCase());
			}
			out.println(");");
			p.pl(indent+1, "var ret = _s.readCache(cacheKey);");
			p.pl(indent+1, "if(ret != undefined) {");
			p.pl(indent+2, "return ret;");
			p.pl(indent+1, "}");			
			printFormula("ret = ", formula);
			p.pl(indent+1, "_s.writeCache(cacheKey, ret);");
			p.pl(indent+1, "return ret;");			
		}
		p.pl(indent, "},");
	}

	private void printArguments(ScopedSymbol calcsymbol) {
		out.print("(_s"); /* TODO (optimization): s not needed when independent */				
		List<Symbol> arguments = calcsymbol.getScopeDefines();
		for (Symbol argument : arguments) {
			String argname = argument.getSymbolName();
			out.print(", ");
			out.print(argname.toLowerCase());
		}		
		out.print(") {");
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
	
	private void printConstants() {
		out.println();
		for (Entry<String,FormulaConstant> entry : constants.entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
			out.print  ("var ");
			out.print  (name);
			out.print  (" = ");
			out.print  ("_C.");
			out.print  (name);
			out.println(";");
		}
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JSFunctions obj = new JSFunctions(model, foldernameout, packagename);
		obj.run();
	}
}
