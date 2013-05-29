/* use symbol tables / scopes to resolve names */

package treecalc.comp.vm; 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import treecalc.comp.GenTable;
import treecalc.comp.ModelSimple;


public class VmTables {
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private PrintStream out;
	private GenTable table;

	private VmTables(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;
		this.packagename = packagename;
	}

	private void run() throws IOException {
		out = new PrintStream(new FileOutputStream(foldernameout + "/" + packagename + ".tci", true), true, "ISO-8859-1");
		printProlog();
		processTables();
		out.close();
	}

	private void printProlog() {
		out.print(".tables size=");
		out.println(model.getTableSize());
	}
	
	private void processTables() throws IOException {
		for (int i=0; i<model.getTableSize(); i++) {
			table = model.getTable(i);
			printTable();
		}
	}
	
	private void printTable() throws IOException {
		out.print(".table");
	    out.print(" table=");
	    out.print(table.getIndex());
	    out.print(" name=");
	    out.print(table.getName());
	    out.print(" rows=");
	    out.print(table.getNumrows());
	    out.print(" cols=");
	    out.print(table.getNumcols());
	    out.print(" shuffled=");
	    out.print(table.isShuffled());
	    out.print(" directaccess=");
	    out.print(table.isDirectAccess());
	    if (table.isDirectAccess()) {
	    	out.print(" directaccessoffset=");
	    	out.print(table.getDirectAccessOffset());
	    }
	    out.println();
	    String[] colnames = table.getColnames();
	    boolean[] colnumeric = table.getColnumeric();
	    boolean[] colnumericsuper = table.getColnumericsuper();
	    boolean[] colnumericunique = table.getColnumericunique();
		for (int i=0; i<colnames.length; i++) {
			out.print(".tablecol");
			out.print(" table=");
			out.print(table.getIndex());
			out.print(" col=");
			out.print(i);
			out.print(" name=");
			String colname = colnames[i];
			boolean isstring = colname!=null && colname.length()>0 && colname.charAt(0)=='"';
			if (!isstring) {
				out.print('"');
			}
			out.print(colname!=null ? colname.toUpperCase() : "");
			if (!isstring) {
				out.print('"');
			}
		    out.print(" numeric=");
		    out.print(colnumeric[i]);
		    out.print(" numericsuper=");
		    out.print(colnumericsuper[i]);
		    out.print(" numericunique=");
		    out.print(colnumericunique[i]);
		    out.println();
		}
		printData();
	}

	private void printData() {
		int indvalue=0;
		for (int indrow=0; indrow<table.getNumrows(); indrow++) {
			String[] datarow = table.getData()[indrow];
			Object[] datarowObjects = table.getDataObjects()[indrow];
			for (int indvalueinrow = 0; indvalueinrow<table.getNumcols(); indvalueinrow++, indvalue++) {
				String value = datarow[indvalueinrow];
				Object valueObject = datarowObjects[indvalueinrow];

				out.print(".tablevalue");
				out.print(" table=");
				out.print(table.getIndex());
				out.print(" ind=");
				out.print(indvalue);
				out.print(" row=");
				out.print(indrow);
				out.print(" col=");
				out.print(indvalueinrow);
			    out.print(" value=");
				out.print('"');
				out.print(value);
				out.print('"');
				out.println();
			}
		}
		if (table.isShuffled()) {
			int[] oo = table.getRowindori();
			for (int indrow=0; indrow<table.getNumrows(); indrow++) {
				out.print(".tablerow");
				out.print(" table=");
				out.print(table.getIndex());
				out.print(" row=");
				out.print(indrow);
				out.print(" oo=");
				out.print(oo[indrow]);
				out.println();
			}
		}
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		VmTables obj = new VmTables(model, foldernameout, packagename);
		obj.run();
	}
}
