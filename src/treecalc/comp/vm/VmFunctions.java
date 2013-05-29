/* use symbol tables / scopes to resolve names */

package treecalc.comp.vm; 

import static treecalc.comp.TcSimpleParser.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import treecalc.comp.GenFunction;
import treecalc.comp.ModelSimple;
import treecalc.comp.Scope;
import treecalc.comp.ScopedSymbol;
import treecalc.comp.Symbol;
import treecalc.comp.TcAst;


public class VmFunctions {
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	// list of TcAst: ^(TT_RESULTDEF id arguments? formula)
	
	private VmFunctions(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		out = new PrintStream(new FileOutputStream(foldernameout + "/" + packagename + ".tci", true), true, "ISO-8859-1");
		print();
		out.close();
	}

	private void print() {
		out.print(".funcs");
		out.print(" size=");
		out.println(model.getFunctionSize());
		for (int i=0; i<model.getFunctionSize(); i++) {
			GenFunction function = model.getFunction(i);
			out.print(".func");
			out.print(" func=");
			out.print(i);
			out.print(" name=");
			out.print(function.getName().toUpperCase());
			out.print(" args=");
			out.print(function.getArgSize());
//			out.print(" simple=");
//			TcAst formula = function.getAst().getChild(function.getAst().getChildCount()-1);
//			out.print(VmFormula.isSimple(model,  formula));
			printFunctionFormula(function.getAst());
			out.println();
		}
	}
	private void printFunctionFormula(TcAst calc) {
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

		out.print(" formula=");
		int idformula = model.putFormula(formula);
		out.print(idformula);
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		VmFunctions obj = new VmFunctions(model, foldernameout, packagename);
		obj.run();
	}
}
