/* use symbol tables / scopes to resolve names */

package treecalc.comp.java; 


import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;

import treecalc.comp.FormulaConstant;
import treecalc.comp.ModelSimple;

import treecalc.rt.V;
import treecalc.rt.VDouble;


public class JavaConstants {
	public final static boolean DEBUGOUT = false;
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;

	private JavaConstants(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		String classname = "_C";
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
		}
		out.println("import com.hackhofer.tc.rt.V;");
		out.println("import com.hackhofer.tc.rt.VDouble;");
		out.println("import com.hackhofer.tc.rt.VString;");
		out.println("import com.hackhofer.tc.rt.VFuncref;");
		out.println("import com.hackhofer.tc.rt.VTabref;");
		out.println("import com.hackhofer.tc.rt.VNull;");
		out.println();
		out.println("/**");
		out.println(" * All constants of the model");
		out.println(" * @author Stefan");
		out.println(" */");
		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		out.println();
		for (Entry<String,FormulaConstant> entry : model.getConstants().entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
			out.print  ("   public final static V ");
			out.print  (name);
			out.print  (" = ");
			out.print  (createStatement);
			out.println(";");
		}
		out.println("   static {");
		for (Entry<String,FormulaConstant> entry : model.getConstants().entrySet()) {
			String constname = entry.getKey();
			FormulaConstant constant = entry.getValue();
			String name = constant.getNameInMethod();
			String createStatement = constant.getCreatestatement();
			V vconstant = constant.getConstant();
			if (vconstant instanceof VDouble) {
				out.print  ("      ");
				out.print  (name);
				out.println(".stringValue();");
			} 
		}
		out.println("   }");
		
		out.println("}");

		out.close();
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JavaConstants obj = new JavaConstants(model, foldernameout, packagename);
		obj.run();
	}
}
