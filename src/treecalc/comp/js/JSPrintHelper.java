package treecalc.comp.js;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class JSPrintHelper {

	private PrintStream out;
	
	public JSPrintHelper(PrintStream out) {
		this.out = out;
	}
	
	public void printArgumentsToArrayAndShift(int indent) {
		printArgumentsToArrayAndShift(indent, "indices");		
	}
	
	public void printArgumentsToArrayAndShift(int indent, String varname) {
		printArgumentsToArray(indent, varname);
		pl(indent, varname + ".shift();");
	}
	
	public void printArgumentsToArray(int indent, String varname) {
		pl(indent, "var " + varname + " = " + argumentsToArray() + ";");
	}
	
	public static String argumentsToArray() {
		return "Array.prototype.slice.call(arguments)";
	}
	
	public void printProperty(int indent, String name, boolean[] b) {
		printProperty(indent, name, printAsString(b));
	}
	
	public void printProperty(int indent, String name, String[] b) {
		printProperty(indent, name, buildJSString(b));
	}
	
	public void printProperty(int indent, String name, String value) {
		p(indent, name);
		out.print(": ");
		out.print(value);
		out.println(",");
	}
	
	public void p(int n, String text) {
		out.print(getIndent(n) + text);
	}
	
	public void pl(int n, String text) {
		out.println(getIndent(n) + text);
	}
	
	public static String getIndent(int n) {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<n; i++) {
			builder.append("\t");
		}
		return builder.toString();
	}
	
	public static final String buildJSString(String[] array) {
		if (array.length == 0) {
			return "[]";
		}		
		StringBuilder b = new StringBuilder();
		b.append("[");
		for (int i=0; i<array.length-1; i++) {
			printAsString(b, array[i]);			
			b.append(",");
		}
		printAsString(b, array[array.length-1]);		
		b.append("]");
		return b.toString();
	}
	
	public static String buildJSString(String name) {
		return "'" + name + "'";
	}
	
	private static final void printAsString(StringBuilder b, String str) {
		b.append("'");
		b.append(str);
		b.append("'");		
	}
	
	public static final String printAsString(boolean[] array) {
		StringBuilder b = new StringBuilder();
		b.append("[");
		for (int i=0; i<array.length-1; i++) {
			b.append(array[i]);			
			b.append(",");
		}
		b.append(array[array.length-1]);		
		b.append("]");
		return b.toString();
	}
		
	public static void printPrint(String js) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(js));
			PrintWriter writer = new PrintWriter("js/generated/printPrint");
			String line;
			while ((line = reader.readLine()) != null) {												
				writer.println("p.pl(indent+" + getIndent(line) + ", \"" + line.trim() + "\");");
			}
			writer.flush();
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getIndent(String line) {
		int i = 0;
		int indent = 0;
		while (i<line.length()) {
			if (line.charAt(i) == '\t') {
				indent++;
			}
			else {
				break;
			}
			i++;
		}
		return indent;
	}
}
