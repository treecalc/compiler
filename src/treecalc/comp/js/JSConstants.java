package treecalc.comp.js;

/* use symbol tables / scopes to resolve names */

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.ModelSimple;

import treecalc.rt.V;



public class JSConstants {
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;	
	public static final String NS_CONSTANTS = "tc.c";

	private JSConstants(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		String classname = "_C";
		out = new PrintStream(foldernameout + "/" + classname + ".js", "ISO-8859-1");
						
		out.print(NS_CONSTANTS);
		out.println(" = {};");
		out.println();
		
		for (Entry<String,FormulaConstant> entry : model.getConstants().entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
								
			out.print  (name);
			out.print  (" = ");
			out.print (createStatement);
			out.println(";");
		}
		
//		out.println("   static {");
//		for (Entry<String,Formula.FormulaConstant> entry : model.getConstants().entrySet()) {
//			String constname = entry.getKey();
//			Formula.FormulaConstant constant = entry.getValue();
//			String name = constant.getNameInMethod();
//			String createStatement = constant.getCreatestatement();
//			V vconstant = constant.getConstant();
//			if (vconstant instanceof VDouble) {
//				out.print  ("      ");
//				out.print  (name);
//				out.println(".stringValue();");
//			} 
//		}
//		out.println("   }");				

		out.close();
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JSConstants obj = new JSConstants(model, foldernameout, packagename);
		obj.run();
	}
}
