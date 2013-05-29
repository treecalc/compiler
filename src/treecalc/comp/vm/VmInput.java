/* use symbol tables / scopes to resolve names */

package treecalc.comp.vm; 

import static treecalc.comp.TcSimpleParser.*;

import java.io.FileOutputStream;
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

import treecalc.rt.V;
import treecalc.rt.VDouble;

public class VmInput {
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	private GenInput input;

	private VmInput(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		out = new PrintStream(new FileOutputStream(foldernameout + "/" + packagename + ".tci", true), true, "ISO-8859-1");
		printInputCalcIds();
		printInputs();
		out.close();
	}

	private void printInputCalcIds() {
		out.print(".inputcalcids");
		out.print(" size=");
		out.println(model.getInputcalcSize());
		for (int i=0; i<model.getInputcalcSize(); i++) {
			out.print(".inputcalcid");
		    out.print(" icalc=");
		    out.print(i);
		    out.print(" name=");
			out.println(model.getInputcalcName(i));
		}
	}
	
	private void printInputs() {
		out.print(".inputs size=");
		out.print(model.getInputSize());
		out.println();
		for (int i=0; i<model.getInputSize(); i++) {
			input = model.getInput(i);
			printInput();
		}
	}
	private void printInput() {
		String inputname = input.getName().toUpperCase();
		out.print(".input");
		out.print(" input=");
		out.print(input.getIndex());
		out.print(" name=");
		out.print(inputname);
//		out.print(" hascheck=");
//		out.print(input.hasCheck());

		int[] autocounters = model.getInputAutocounters(input);
		int aclen = autocounters == null ? 0 : autocounters.length;
		out.print(" autocounters=");
		out.print(aclen);
		
//		out.print(" hasdefault=");
//		out.print(input.hasDefault());
		out.print(" choiceable=");
		out.print(input.isChoiceable());
//		out.print(" hasvector=");
//		out.print(input.hasVector());
//		out.print(" hasreference=");
//		out.print(input.hasReference());
//		out.print(" hastable=");
//		out.print(input.hasTable());
//		out.print(" hasfilter=");
//		out.print(input.hasFilter());
//		out.print(" hasdisplay=");
//		out.print(input.hasDisplay());
//		out.print(" hasdisplaytext=");
//		out.print(input.hasDisplaytext());
		out.println();

		for (int i=0; i<aclen; i++) {
			out.print(".inputautocounter");
			out.print(" input=");
			out.print(input.getIndex());
			out.print(" ind=");
			out.print(i);
			out.print(" counter=");
			out.println(autocounters[i]);
		}

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

		int idformula = model.putFormula(formula);
		int icalc = model.getInputcalcIndex(genCalc.getCalcname());
		out.print(".inputcalc");
		out.print(" input=");
		out.print(input.getIndex());
		out.print(" icalc=");
		out.print(icalc);
		out.print(" formula=");
		out.print(idformula);
		out.print(" ;");
		out.print(genCalc.getCalcname().toUpperCase());
		out.println();
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		VmInput obj = new VmInput(model, foldernameout, packagename);
		obj.run();
	}
}
