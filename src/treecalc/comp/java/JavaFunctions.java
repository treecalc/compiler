/* use symbol tables / scopes to resolve names */

package treecalc.comp.java; 

import static treecalc.comp.TcSimpleParser.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import treecalc.comp.FormulaConstant;
import treecalc.comp.GenFunction;
import treecalc.comp.ModelSimple;
import treecalc.comp.PrintFormulaInfos;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;

import treecalc.rt.V;

public class JavaFunctions {
	public final static boolean DEBUGOUT = false;
	public final static boolean USE_REFLECT_FUNCALL = false;
	public final static int USE_REFLECT_GENCALL_NUMFUNC = 100;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private final boolean trace;
	private PrintStream out;
	private int stmtsmethod=0;
	private final int MAX_STMTS_IN_METHOD=5000;
	HashMap<String, FormulaConstant> constants = new HashMap<String, FormulaConstant>();
	// list of TcAst: ^(TT_RESULTDEF id arguments? formula)
	
	private JavaFunctions(ModelSimple model, String foldernameout, String packagename, boolean trace) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
		this.trace = trace;
	}

	private void run() throws IOException {
		String classname = "F";
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
		}
		out.println("import java.util.ArrayList;");
		out.println("import java.util.HashMap;");
		out.println();
		out.println("import com.hackhofer.tc.rt.ExceptionCalculation;");
		if (trace) {
			out.println("import com.hackhofer.tc.rt.S.Traceaction;");
		}
		out.println("import com.hackhofer.tc.rt.V;");
		out.println("import com.hackhofer.tc.rt.S;");
		out.println("import com.hackhofer.tc.rt.VFuncref;");
		out.println("import com.hackhofer.tc.rt.VNull;");
		out.println();
		out.println("import static com.hackhofer.tc.rt.B.*;");
		out.println("import static com.hackhofer.tc.rt.V.*;");
		out.println();
		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		print();
		
		printDynamicDispatch();
		
		printConstants();
		
		out.println("}");
		out.close();
	}

	private void printDynamicDispatch() {
		out.print  ("   private final static HashMap<String,Integer> fn = new HashMap<String,Integer>(");
		out.print  (model.getFunctionSize());
		out.println(", 0.5f);");
		out.println("   static {");
		out.println("      final HashMap<String,Integer> m = fn;");
		for (int i=0; i<model.getFunctionSize(); i++) {
			GenFunction function = model.getFunction(i);
			String functionname = function.getName();
			out.print  ("      m.put(");
			out.print  ('"');
			out.print  (functionname);
			out.print  ('"');
			out.print  (", ");
			out.print  (i);
			out.println(");");
		}
		out.println("   }");
		
		out.println();
		out.println("   public final static int getFuncIndex(String funcnameUpper) {");
		out.println("      Integer i = fn.get(funcnameUpper);");
		out.println("      return i!=null ? i : -1;");
		out.println("   }");
		out.println();
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
			out.print(": ");
			out.print("if (args.length!=");
			out.print(function.getArgSize());
			out.print(") throw new ExceptionCalculation(\"wrong number of arguments\", null); "); 
			out.print("return ");
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
		for (int i=0; i<model.getFunctionSize(); i++) {
			GenFunction function = model.getFunction(i);
			printFunction(function.getAst());
		}
	}
	private void printFunction(TcAst calc) {
		//^(TT_RESULTDEF id arguments? formula)
		TcAst id = calc.getChild(0);
		TcAst next = calc.getChild(1);
		TcAst args = next.getType()==TT_ARGDEF ? next : null;
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

		out.println();
		out.print("   public static final V ");
		out.print(funcname);
		out.print("(S _s"); /* TODO (optimization): s not needed when independent */
		List<Symbol> arguments = calcsymbol.getScopeDefines();
		String sDescription = funcname;
		for (Symbol argument : arguments) {
			String argname = argument.getSymbolName();
			out.print(", V ");
			out.print(argname.toLowerCase());
		}
		
		out.print(") { //");
		out.print("line ");
		out.println(calc.getLine());
		
		/* caching */
		GenFunction genfunc = model.getFunction(funcname);
		int funcindex = genfunc.getIndex();

		if (trace) {
			out.println("      StringBuffer $$argss = new StringBuffer();");
			boolean first=true;
			for (Symbol argument : arguments) {
				String argname = argument.getSymbolName();
				if (!first) {
					out.println("      $$argss.append(\", \");");
				}
				out.println("      $$argss.append(\"" + argname + "=\");");
				out.println("      $$argss.append(" + argument.getSymbolName().toLowerCase() + ");");
				first = false;
			}
			out.println("      String $$args = $$argss.toString();");
		}  
		if (JavaFormula.isSimple(model, formula)) {
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", null, Traceaction.CALL);");
				printFormula("final V $$return = ", formula);
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$return.toString(), Traceaction.RETURN_SIMPLE);");
				out.println("      return $$return;");
			} else {
				printFormula("return ", formula);
			}
			out.println("   }");
		} else {
			out.print  ("      final Object cacheKey = _s.getCacheKey(" + model.getCacheId(sDescription));
			for (Symbol argument : arguments) {
				String argname = argument.getSymbolName();
				out.print(", ");
				out.print(argname.toLowerCase());
			}
			out.println(");");
			out.println("      final V $$returncache = _s.readCache(cacheKey);");
			out.println("      if($$returncache!=null) {");
			if (trace) {
				out.println("         _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$returncache.toString(), Traceaction.RETURN_HIT);");
			}
			out.println("         return $$returncache;");
			out.println("      }");
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", null, Traceaction.CALL);");
			}
			printFormula("final V $$return = ", formula);
			out.print  ("      _s.writeCache(cacheKey, $$return);");
			if (trace) {
				out.println("      _s.trace(\"" + sDescription + "(\" + $$args + \")\", $$return.toString(), Traceaction.RETURN_MISS);");
			}
			out.println("      return $$return;");
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

	public static void generate(ModelSimple model, String foldernameout, String packagename, boolean trace) throws IOException {
		JavaFunctions obj = new JavaFunctions(model, foldernameout, packagename, trace);
		obj.run();
	}
}
