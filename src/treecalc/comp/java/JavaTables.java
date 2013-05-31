/* use symbol tables / scopes to resolve names */

package treecalc.comp.java; 

import java.io.IOException;
import java.io.PrintStream;

import treecalc.comp.GenTable;
import treecalc.comp.ModelSimple;


public class JavaTables {
	private final ModelSimple model;
	private final String foldernameout;
	private final String packagename;
	private final boolean gwt;
	private final boolean trace;
	private PrintStream out;
	private GenTable table;

	private static final int MAXINITSTMTS_INMETHOD = 2500;      
	
	public static final int CALL_EXIST             =  1;  //int findRowExact(V ... keys)    
	public static final int CALL_GET               =  2;  //V get(int rowind, int colind)    
	public static final int CALL_GET_COL           =  3;  //V get(int rowind, V columnname)    
	public static final int CALL_EXACT             =  4;  //V findExact(V ... keys)    
	public static final int CALL_EXACT_COL         =  5;  //V findExactColumn(V columnname, V ... keys)    
	public static final int CALL_EXACT_COLIND      =  6;  //V findExactColumnIndex(int colind, V ... keys)    
	public static final int CALL_INTERVAL_UP       =  7;  //V findIntervalUp(V ... keys)    
	public static final int CALL_INTERVAL_UP_COL   =  8;  //V findIntervalUpColumn(V columnname, V ... keys)    
	public static final int CALL_INTERVAL_DOWN     =  9;  //V findIntervalDown(V ... keys)    
	public static final int CALL_INTERVAL_DOWN_COL = 10;  //V findIntervalDownColumn(V columnname, V ... keys)    
	public static final int CALL_CELL_S_S          = 11;  //V getCell(V row, V col)    
	public static final int CALL_CELL_S_R          = 12;  //V getCellsRow(V row, V colFrom, V colTo)    
	public static final int CALL_CELL_R_S          = 13;  //V getCellsColumn(V rowFrom, V rowTo, V col)    
	public static final int CALL_CELL_R_R          = 14;  //V getCells(V rowFrom, V rowTo, V colFrom, V colTo)    
	public static final int CALL_CELL_S_SN         = 15;  //V getCellByName(V row, V colname)    
	public static final int CALL_CELL_S_RN         = 16;  //V getCellsRowByName(V row, V colNameFrom, V colNameTo)    
	public static final int CALL_CELL_R_SN         = 17;  //V getCellsColumnByName(V rowFrom, V rowTo, V colname)    
	public static final int CALL_CELL_R_RN         = 18;  //V getCellsByName(V rowFrom, V rowTo, V colNameFrom, V colNameTo)    
	public static final int CALL_INTERPOL          = 19;  //V interpol(V key)    
	public static final int CALL_NUMROWS           = 20;  //V numrows()    
	public static final int CALL_NUMCOLS           = 21;  //V numcols()
	public static final int CALL_COLINDEX          = 22;  //int getColindex(String colname)
	public static final int CALL_DATA              = 23;  //Object[] getData();
	public static final int CALL_OO                = 24;  //int[] getOo();

	private JavaTables(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) {
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
		processTables();
		printT();
	}

	/**
	 * class for dynamic dispatching 
	 * @throws IOException 
	 */
	private void printT() throws IOException {
		String classname = "Tables";
		out = new PrintStream(foldernameout + "/" + classname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
		}
		out.println("import java.util.HashMap;");
		out.println();
		out.println("import treecalc.rt.ExceptionCalculation;");
		out.println("import treecalc.rt.V;");
		out.println("import treecalc.rt.VTabref;");
		
		out.println();


		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		printCallConstants("public");
		out.println();
//		out.println("   /* uppercase tablenames at position tableindex */");
//		out.print  ("   private final static String[] tc = new String[");
//		out.print  (tables.size());
//		out.println("];");
		out.println("   /* key: tablename uppercase; value: tableindex */");
		out.print  ("   private final static HashMap<String,Integer> tn = new HashMap<String,Integer>(");
		out.print  (model.getTableSize());
		out.println(", 0.5f);");
		out.println("   static {");
		out.println("      final HashMap<String,Integer> m = tn;");
		for (int i=0; i<model.getTableSize(); i++) {
			table = model.getTable(i);
			String tablename = table.getName().toUpperCase();
			out.print("      m.put(");
			out.print('"');
			out.print(tablename);
			out.print('"');
			out.print(", ");
			out.print(i);
			out.println(");");
		}
		out.println("   } //end of static initializer"); 

		out.println();
		out.println("   public final static int getTableid(String tabname) {");
		out.println("      Integer i = tn.get(tabname.toUpperCase());");
		out.println("      return i!=null ? i : -1;");
		out.println("   }");
		
		out.println();
		printDynamicDispatch();
		out.println("}");
		out.close();
	}
	
	private void processTables() throws IOException {
		for (int i=0; i<model.getTableSize(); i++) {
			table = model.getTable(i);
			printTable();
		}
	}
	
	private void printCallConstants(String modifier) {
		out.print("   "); out.print(modifier); out.println(" static final int CALL_EXIST             =  1;  //int findRowExact(V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_GET               =  2;  //V get(int rowind, int coling)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_GET_COL           =  3;  //V get(int rowind, V columnname)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_EXACT             =  4;  //V findExact(V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_EXACT_COL         =  5;  //V findExactColumn(V columnname, V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_EXACT_COLIND      =  6;  //V findExactColumnIndex(int colind, V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_INTERVAL_UP       =  7;  //V findIntervalUp(V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_INTERVAL_UP_COL   =  8;  //V findIntervalUpColumn(V columnname, V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_INTERVAL_DOWN     =  9;  //V findIntervalDown(V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_INTERVAL_DOWN_COL = 10;  //V findIntervalDownColumn(V columnname, V ... keys)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_S_S          = 11;  //V getCell(V row, V col)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_S_R          = 12;  //V getCellsRow(V row, V colFrom, V colTo)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_R_S          = 13;  //V getCellsColumn(V rowFrom, V rowTo, V col)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_R_R          = 14;  //V getCells(V rowFrom, V rowTo, V colFrom, V colTo)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_S_SN         = 15;  //V getCellByName(V row, V colname)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_S_RN         = 16;  //V getCellsRowByName(V row, V colNameFrom, V colNameTo)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_R_SN         = 17;  //V getCellsColumnByName(V rowFrom, V rowTo, V colname)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_CELL_R_RN         = 18;  //V getCellsByName(V rowFrom, V rowTo, V colNameFrom, V colNameTo)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_INTERPOL          = 19;  //V interpol(V key)    ");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_NUMROWS           = 20;  //V numrows()");
		out.print("   "); out.print(modifier); out.println(" static final int CALL_NUMCOLS           = 21;  //V numcols()");    
		out.print("   "); out.print(modifier); out.println(" static final int CALL_COLINDEX          = 22;  //int getColindex(String colname)");    
		out.print("   "); out.print(modifier); out.println(" static final int CALL_DATA              = 23;  //Object[] getData()");    
		out.print("   "); out.print(modifier); out.println(" static final int CALL_OO                = 24;  //int[] getOo()");    
	}
	
	private void printTable() throws IOException {
		String tabname = table.getName();
		String classname = tabname;
		out = new PrintStream(foldernameout + "/" + tabname + ".java", "UTF-8");
		if (packagename!=null && packagename.length()>0) {
			out.print("package ");
			out.print(packagename);
			out.println(";");
		}
		out.println("import java.util.Arrays;");
		out.println("import treecalc.rt.ExceptionCalculation;");
		out.println("import treecalc.rt.V;");
		if(gwt) {
			out.println("import treecalc.rt.gwt." + getClassnameCopyOf() + ";");
		}
		out.println();

		out.print("public final class ");
		out.print(classname);
		out.println(" {");
		out.println("   private static final int METHOD_EXACT_STRI    = 0;");
		out.println("   private static final int METHOD_INTERVAL_UP   = 1;");
		out.println("   private static final int METHOD_INTERVAL_DOWN = 2;");
		out.println();
		
		printCallConstants("private");
		out.println();

		String[] colnames = table.getColnames();
		out.print("   private final static String[] colnames = { ");
		for (int i=0; i<colnames.length; i++) {
			if (i>0) {
				out.print(", ");
			}
			String colname = colnames[i];
			boolean isstring = colname!=null && colname.length()>0 && colname.charAt(0)=='"';
			if (!isstring) {
				out.print('"');
			}
			out.print(colname!=null ? colname.toUpperCase() : "");
			if (!isstring) {
				out.print('"');
			}
		}
		out.println(" };");
		out.print("   private final static boolean[] colnumeric       = { ");
		boolean[] colnumeric = table.getColnumeric();
		for (int i=0; i<table.getNumcols(); i++) {
			if(i>0) {
				out.print(", ");
			}
			out.print(colnumeric[i] ? "true " : "false");
		}
		out.println(" };");
		out.print("   //private final static boolean[] colnumericsuper  = { ");
		boolean[] colnumericsuper = table.getColnumericsuper();
		for (int i=0; i<table.getNumcols(); i++) {
			if(i>0) {
				out.print(", ");
			}
			out.print(colnumericsuper[i] ? "true " : "false");
		}
		out.println(" };");
		out.print("   //private final static boolean[] colnumericunique = { ");
		boolean[] colnumericunique = table.getColnumericunique();
		for (int i=0; i<table.getNumcols(); i++) {
			if(i>0) {
				out.print(", ");
			}
			out.print(colnumericunique[i] ? "true " : "false");
		}
		out.println(" };");

		int numcols = table.getNumcols();
		out.print("   //private final static int numcols = ");
		out.print(numcols);
		out.println(";");

		/* data: filled in static initializers */
		//			static {
		//				d[0] = "abc"; d[1] = "def";
		//              d[2] = "xxx"; d[3] = "yyy";
		//              ...
		//				init2();
		//			}
		int numrows = table.getNumrows(); 
		out.print("   //private final static int numrows = ");
		out.print(numrows);
		out.println(";");
		out.print("   //private final static boolean directAccess       = ");
		out.print(table.isDirectAccess());
		out.println(";");
		out.print("   //private final static int     directAccessOffset = ");
		out.print(table.getDirectAccessOffset());
		out.println(";");
		out.print("   //private final static boolean shuffled           = ");
		out.print(table.isShuffled() ? "true " : "false");
		out.println(';');
		
		/* find column index based on column name */
		/* linear search ... could be replaced by binary search if necessary */
		/* linear search should be fast though especially when starting at end of the columnnames-list
		 * because normally first columns are used for comparisoin, last ones to get the value
		 */
		out.println();
		out.println("   private final static int getColindex(final String name) {");
		out.println("      final String nameup = name.toUpperCase();");
		out.print  ("      for (int i=0; i<");
		out.print(numcols);
		out.println("; i++) {");
		out.println("         if (nameup.equals(colnames[i])) {");
		out.println("            return i;");
		out.println("         }");
		out.println("      }");
		out.println("      return -1;");
		out.println("   }");
			
		printAccessFunctions();
		printDynamicCall();	
		
		int numcells = numrows * numcols;
		out.println();
		out.print("   private final static Object[] d = new Object[");
		out.print(numcells);
		out.println("];");
		if (table.isShuffled()) {
			out.println("   /* original ordering */");
			out.print  ("   private final static int[] oo = new int[");
			out.print(numrows);
			out.println("];");
		}
		printData();
		out.close();
	}

	private void printDynamicCall() {
		out.println("   public final static Object dynamicCall(int call, Object...args) {");
		out.println("      switch(call) {");
		out.println("      case CALL_EXIST             : //int findRowExact(V ... keys)");
		if (gwt) {
			out.println("          return findRowExact(" + getClassnameCopyOf() + ".copyOfV(args, args.length));");
		} else {
			out.println("          return findRowExact(" + getClassnameCopyOf() + ".copyOf(args, args.length, V[].class));");
		}
		out.println();
		out.println("      case CALL_GET               : //V get(int rowind, int colind)    ");
		out.println("          return get((Integer) args[0], (Integer) args[1]);");
		out.println();
		out.println("      case CALL_GET_COL           : //V get(int rowind, V columnname)    ");
		out.println("          return get((Integer) args[0], (V) args[1]);");
		out.println();
		out.println("      case CALL_EXACT             : //V findExact(V ... keys)    ");
		if (gwt) {
			out.println("          return findExact(" + getClassnameCopyOf() + ".copyOfV(args, args.length));");
		} else {
			out.println("          return findExact(" + getClassnameCopyOf() + ".copyOf(args, args.length, V[].class));");
		}
		out.println();
		out.println("      case CALL_EXACT_COL         : //V findExactColumn(V columnname, V ... keys)    ");
		if (gwt) {
			out.println("          return findExactColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRangeV(args, 1, args.length));");
		} else {
			out.println("          return findExactColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRange(args, 1, args.length, V[].class));");
		}
		out.println();
		out.println("      case CALL_EXACT_COLIND      : //V findExactColumnIndex(int colind, V ... keys)    ");
		if (gwt) {
			out.println("          return findExactColumnIndex((Integer) args[0], " + getClassnameCopyOf() + ".copyOfRangeV(args, 1, args.length));");
		} else {
			out.println("          return findExactColumnIndex((Integer) args[0], " + getClassnameCopyOf() + ".copyOfRange(args, 1, args.length, V[].class));");
		}
		out.println();
		out.println("      case CALL_INTERVAL_UP       : //V findIntervalUp(V ... keys)    ");
		if (gwt) {
			out.println("         return findIntervalUp(" + getClassnameCopyOf() + ".copyOfV(args, args.length));");
		} else {
			out.println("         return findIntervalUp(" + getClassnameCopyOf() + ".copyOf(args, args.length, V[].class));");
		}
		out.println("         ");
		out.println("      case CALL_INTERVAL_UP_COL   : //V findIntervalUpColumn(V columnname, V ... keys)    ");
		if (gwt) {
			out.println("         return findIntervalUpColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRangeV(args, 1, args.length));");
		} else {
			out.println("         return findIntervalUpColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRange(args, 1, args.length, V[].class));");
		}
		out.println("         ");
		out.println("      case CALL_INTERVAL_DOWN     : //V findIntervalDown(V ... keys)    ");
		if (gwt) {
			out.println("         return findIntervalDown(" + getClassnameCopyOf() + ".copyOfV(args, args.length));");
		} else {
			out.println("         return findIntervalDown(" + getClassnameCopyOf() + ".copyOf(args, args.length, V[].class));");
		}
		out.println("         ");
		out.println("      case CALL_INTERVAL_DOWN_COL : //V findIntervalDownColumn(V columnname, V ... keys)    ");
		if (gwt) {
			out.println("         return findIntervalDownColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRangeV(args, 1, args.length));");
		} else {
			out.println("         return findIntervalDownColumn((V) args[0], " + getClassnameCopyOf() + ".copyOfRange(args, 1, args.length, V[].class));");
		}
		out.println("      ");
		out.println("      case CALL_CELL_S_S          : //V getCell(V row, V col)    ");
		out.println("         return getCell((V) args[0], (V) args[1]);");
		out.println("         ");
		out.println("      case CALL_CELL_S_R          : //V getCellsRow(V row, V colFrom, V colTo)    ");
		out.println("         return getCellsRow((V) args[0], (V) args[1], (V) args[2]);");
		out.println();
		out.println("      case CALL_CELL_R_S          : //V getCellsColumn(V rowFrom, V rowTo, V col)    ");
		out.println("         return getCellsColumn((V) args[0], (V) args[1], (V) args[2]);");
		out.println();
		out.println("      case CALL_CELL_R_R          : //V getCells(V rowFrom, V rowTo, V colFrom, V colTo)    ");
		out.println("         return getCells((V) args[0], (V) args[1], (V) args[2], (V) args[3]);");
		out.println("         ");
		out.println("      case CALL_CELL_S_SN         : //V getCellByName(V row, V colname)    ");
		out.println("         return getCellByName((V) args[0], (V) args[1]);");
		out.println("         ");
		out.println("      case CALL_CELL_S_RN         : //V getCellsRowByName(V row, V colNameFrom, V colNameTo)    ");
		out.println("         return getCellsRowByName((V) args[0], (V) args[1], (V) args[2]);");
		out.println("         ");
		out.println("      case CALL_CELL_R_SN         : //V getCellsColumnByName(V rowFrom, V rowTo, V colname)    ");
		out.println("         return getCellsColumnByName((V) args[0], (V) args[1], (V) args[2]);");
		out.println();
		out.println("      case CALL_CELL_R_RN         : //V getCellsByName(V rowFrom, V rowTo, V colNameFrom, V colNameTo)    ");
		out.println("         return getCellsByName((V) args[0], (V) args[1], (V) args[2], (V) args[3]);");
		out.println("      ");
		out.println("      case CALL_INTERPOL          : //V interpol(V key)    ");
		out.println("         return interpol((V) args[0]);");
		out.println("         ");
		out.println("      ");
		out.println("      case CALL_NUMROWS           :");
		out.print  ("         return V.getInstance(");
		out.print  (table.getNumrows());
		out.println(");");
		out.println();
		out.println("      case CALL_NUMCOLS           :");
		out.print  ("         return V.getInstance(");
		out.print  (table.getNumcols());
		out.println(");");
		out.println();
		out.println("      case CALL_COLINDEX          : //int getColindex(String colname)    ");
		out.println("         return getColindex((String) args[0]);");
		out.println();
		out.println("      case CALL_DATA              : //Object[] getData()");
		out.println("         return getData();");
		out.println();
		out.println("      case CALL_OO                : //int[] getOo()    ");
		out.println("         return getOo();");
		out.println();
		out.println("      default:");
		out.println("         throw new RuntimeException(\"unknown table access call: \" + call);");
		out.println("      }");
		out.println("   }");
	}
	
	private void printDynamicDispatch() {
		out.println("   public final static Object dynamicCall(V tab, int call, Object...args) {");
		out.println("      int tabindex;");
		out.println("      if (tab instanceof VTabref) {");
		out.println("         tabindex = ((VTabref) tab).tabrefValue();");
		out.println("      } else {");
		out.println("         String tabname = tab.stringValue().toUpperCase();");
		out.println("         Integer ind = tn.get(tabname);");
		out.println("         if (ind==null) {");
		out.println("            throw new ExceptionCalculation(\"unknown table: \" + tabname, null);");
		out.println("         }");
		out.println("         tabindex = ind;");
		out.println("      }");
		out.println("      switch(tabindex) {");
		for (int i=0; i<model.getTableSize(); i++) {
			GenTable table = model.getTable(i);
			out.print("      case ");
			out.print(String.format("%4d", i));
			out.print(": return ");
			out.print(table.getName().toUpperCase());
			out.println(".dynamicCall(call, args); ");
		}
		out.println("      default: throw new RuntimeException(\"invalid table reference: \" + tab);");
		out.println("      } //end of switch");
		out.println("   }");
	}
	private void printAccessFunctions() {
		out.println();
		out.println("   /* get raw table data ... might be reordered, you get the ordering by getOo() */");
		out.println("   public final static Object[] getData() {");
		out.print  ("      return d;");
		out.println("   }");

		out.println();
		out.println("   /* get original ordering indizes */");
		out.println("   public final static int[] getOo() {");
		if (table.isShuffled()) {
			out.println("      return oo;");
		} else {
			out.println("      return null;");
		}
		out.println("   }");

		out.println();
		out.println("   public final static int getNumrows() {");
		out.print  ("         return ");
		out.print  (table.getNumrows());
		out.println(";");
		out.println("   }");

		out.println();
		out.println("   public final static int getNumcols() {");
		out.print  ("         return ");
		out.print  (table.getNumcols());
		out.println(";");
		out.println("   }");
		
		out.println();
		out.println("   public final static int findRowExact(V ... keys) {");
		out.print  ("      if (keys.length >= ");
		out.print  (table.getNumcols());
		out.println(") {");
		out.println("         return -1;");
		out.println("      }");
		out.println("      return findRow(METHOD_EXACT_STRI, keys);");
		out.println("   }");
		out.println("   public final static V get(int rowind, V columnname) {");
		out.println("      String colname = columnname.stringValue();");
		out.println("      int colind = getColindex(colname);");
		out.println("      if (colind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"column '\" + colname + \"' does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.print  ("      if (rowind<0 || rowind>=");
		out.print  (table.getNumrows());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"row-index \" + rowind + \" invalid for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		if (!table.isShuffled()) {
			out.print  ("      return V.getInstance(d[rowind*");
		} else {
			out.print  ("      return V.getInstance(d[oo[rowind]*");
		}
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		
		out.println();
		out.println("   public final static V get(int rowind, int colind) {");
		out.print  ("      if (colind<0 || colind>=");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"column with index \" + colind + \" does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.print  ("      if (rowind<0 || rowind>=");
		out.print  (table.getNumrows());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"row '\" + rowind + \" invalid for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      ");
		if (!table.isShuffled()) {
			out.print  ("      return V.getInstance(d[rowind*");
		} else {
			out.print  ("      return V.getInstance(d[oo[rowind]*");
		}
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		out.println("   public final static V findExact(V ... keys) {");
		out.print  ("      if (keys.length >= ");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"searched with too many keys in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_EXACT_STRI, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with keys: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + keys.length].toString());");
		out.println("   }");
		out.println("   public final static V findExactColumn(V columnname, V ... keys) {");
		out.println("      String colname = columnname.stringValue();");
		out.println("      int colind = getColindex(colname);");
		out.println("      if (colind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"column '\" + colname + \"' does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_EXACT_STRI, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with keys: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		out.println();
		out.println("   public final static V findExactColumnIndex(int colind, V ... keys) {");
		out.print  ("      if (colind<0 || colind>=");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"column with index '\" + colind + \" does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_EXACT_STRI, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with keys: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		out.println();
		out.println("   public final static V findIntervalUp(V ... keys) {");
		out.print  ("      if (keys.length >= ");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"searched with too many keys in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_INTERVAL_UP, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with interval search (up) using search criteria: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + keys.length].toString());");
		out.println("   }");
		out.println("   ");
		out.println("   public final static V findIntervalUpColumn(V columnname, V ... keys) {");
		out.println("      String colname = columnname.stringValue();");
		out.println("      int colind = getColindex(colname);");
		out.println("      if (colind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"column '\" + colname + \"' does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_INTERVAL_UP, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with interval search (up) with search criteria: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		out.println("   ");
		out.println("   public final static V findIntervalDown(V ... keys) {");
		out.print  ("      if (keys.length >= ");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"searched with too many keys in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_INTERVAL_DOWN, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with interval search (down) with search criteria: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + keys.length].toString());");
		out.println("   }");
		out.println("   ");
		out.println("   public final static V findIntervalDownColumn(V columnname, V ... keys) {");
		out.println("      String colname = columnname.stringValue();");
		out.println("      int colind = getColindex(colname);");
		out.println("      if (colind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"column '\" + colname + \"' does not exist in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int ind = findRow(METHOD_INTERVAL_DOWN, keys);");
		out.println("      if (ind<0) {");
		out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
		out.print  (table.getName());
		out.println(" with interval search (down) with search criteria: \"+Arrays.toString(keys), null);");
		out.println("      }");
		out.print  ("      return V.getInstance(d[ind*");
		out.print  (table.getNumcols());
		out.println(" + colind].toString());");
		out.println("   }");
		out.println("   private final static double getDouble(Object o) {");
		out.println("      if (o instanceof Integer) {");
		out.println("         return ((Integer) o).intValue();");
		out.println("      } else if (o instanceof Float) {");
		out.println("         return ((Float) o).floatValue();");
		out.println("      } else if (o instanceof Double) {");
		out.println("         return (Double) o;");
		out.println("      } else {");
		out.println("         return Double.parseDouble(o.toString());");
		out.println("      }");
		out.println("   }");
		out.println("   private final static int compareNumerical(double val, Object o) {");
		out.println("      double comp;");
		out.println("      if (o instanceof Integer) {");
		out.println("         comp = ((Integer) o).doubleValue();");
		out.println("      } else if (o instanceof Float) {");
		out.println("         comp = ((Float) o).floatValue();");
		out.println("      } else if (o instanceof Double) {");
		out.println("         comp = (Double) o;");
		out.println("      } else {");
		out.println("         comp = Double.parseDouble(o.toString());");
		out.println("      }");
		out.println("      return Double.compare(val, comp);");
		out.println("   }");
		if (table.isDirectAccess()) {
			out.println();
			out.println("   private final static int findRowDirect(V key) {");
			out.println("      if (!key.isDouble()) {");
			out.println("         return -1;");
			out.println("      }");
			out.println("      double dval = key.doubleValue();");
			out.println("      int i = (int) dval;");
			out.println("      if (dval - (double) i != 0) {");
			out.println("         return -1;");
			out.println("      }");
			out.print  ("      int ind = i - ");
			out.print  (table.getDirectAccessOffset());
			out.println(";");
		    out.print  ("      if (ind<0 || ind>=");
		    out.print  (table.getNumrows());
		    out.println(") {");
		    out.println("	      return -1;");
		    out.println("      }");
			out.print  ("      String valstr = d[ind*");
			out.print  (table.getNumcols());
			out.println("].toString();");
			out.println("      String keystr = key.stringValue();");
			out.println("      return valstr.equalsIgnoreCase(keystr) ? ind : -1;");
			out.println("      ");
			out.println("   }");
		}
		out.println();
		out.println("   private final static int findRow(int method, final V ... keys) {");
		out.println("      int keysLen = keys.length;");
		if (table.isDirectAccess()) {
			out.println("      if (keysLen==1 && method==METHOD_EXACT_STRI) {");
			out.println("         return findRowDirect(keys[0]);");
			out.println("      }");
		}
		out.println("      if (keysLen==0) {");
		out.println("         return -1;");
		out.println("      }");
		out.println("      String[] keysString = new String[keysLen];");
		out.println("      double[] keysDouble = new double[keysLen];");
		out.println("      for (int i=0; i<keysLen; i++) {");
		out.println("         V key = keys[i];");
		out.println("         keysString[i] = key.stringValue();");
		out.println("         if (key.isDouble()) {");
		out.println("            keysDouble[i] = key.doubleValue();");
		out.println("         } else {");
		out.println("            /* if key cannot be converted to a double, but the column is numeric, the value can never be found */");
		out.println("            if (colnumeric[i]) {");
		out.println("               return -1;");
		out.println("            }");
		out.println("         }");
		out.println("      }");
		out.println("      int low = 0;");
		out.print  ("      int high = ");
		out.print  (table.getNumrows()-1);
		out.println(';');
		out.println("      int mid;");
		out.println("      int lenexact = keysLen;");
		out.println("      if (method!=METHOD_EXACT_STRI) {");
		out.println("         lenexact--;");
		out.println("      }");
		out.println("      while (low <= high) {");
		out.println("         mid = (low + high) >>> 1;");
		out.print  ("         int indrowdata = mid*");
		out.print  (table.getNumcols());
		out.println(';');
		out.println("         int cmp=0;");
		out.println("         /* exact comparison for first columns */");
		out.println("         for (int indcol=0; indcol<lenexact; indcol++, indrowdata++) {");
		out.println("            Object val = d[indrowdata];");
		out.println("            if (colnumeric[indcol]) {");
		out.println("               cmp = compareNumerical(keysDouble[indcol], val);");
		out.println("               if (cmp==0) {");
		out.println("                  cmp = keysString[indcol].compareToIgnoreCase(val.toString());");
		out.println("               }");
		out.println("            } else { ");
		out.println("               cmp = keysString[indcol].compareToIgnoreCase(val.toString());");
		out.println("            }");
		out.println("            if (cmp!=0) {");
		out.println("               break;");
		out.println("            }");
		out.println("         }");
		out.println("         if (cmp==0 && method!=METHOD_EXACT_STRI) {");
		out.println("            /* interval comparison */");
		out.println("            Object val = d[indrowdata];");
		out.println("            double dkey = keysDouble[lenexact];");
		out.println("            cmp = compareNumerical(dkey, val);");
		out.println("            /* lookup  :  value-in-row <= key <  value-in-next-row */");
		out.println("            /* lookdown:  value-in-row <  key <= value-in-next-row */");
		out.println("            if (cmp>0 || cmp==0 && method==METHOD_INTERVAL_UP) {");
		out.println("               cmp = 0;");
		out.println("               /* compare with next row if there is one*/");
		out.print  ("               if (mid<");
		out.print  (table.getNumrows()-1);
		out.println(") {");
		out.println("                  /* see if next column would fit as well */");
		out.print  ("                  indrowdata = (mid+1)*");
		out.print  (table.getNumcols());
		out.println(';');
		out.println("                   for (int indcol=0; indcol<lenexact; indcol++, indrowdata++) {");
		out.println("                       val = d[indrowdata];");
		out.println("                       if (colnumeric[indcol]) {");
		out.println("                          cmp = compareNumerical(keysDouble[indcol], val);");
		out.println("                          if (cmp==0) {");
		out.println("                             cmp = keysString[indcol].compareToIgnoreCase(val.toString());");
		out.println("                          }");
		out.println("                       } else { ");
		out.println("                          cmp = keysString[indcol].compareToIgnoreCase(val.toString());");
		out.println("                       }");
		out.println("                       if (cmp!=0) {");
		out.println("                          break;");
		out.println("                       }");
		out.println("                    }");
		out.println("                   /* next row does not fit -> we have it! */");
		out.println("                   if (cmp!=0) {");
		out.println("                      cmp = 0;");
		out.println("                   } else {");
		out.println("                      /* see if the interval-search-key fits for next row */");
		out.println("                       Object valnext = d[indrowdata];");
		out.println("                       cmp = compareNumerical(dkey, valnext);");
		out.println("                       if (cmp<0 || cmp==0 && method==METHOD_INTERVAL_DOWN) {");
		out.println("                          cmp = 0;");
		out.println("                       } else {");
		out.println("                          cmp = 1; //continue search behind current row");
		out.println("                       }");
		out.println("                   }");
		out.println("               } else {");
		out.println("                  cmp = 0;");
		out.println("               }");
		out.println("            } else {");
		out.println("               cmp = -1;");
		out.println("            }");
		out.println("         }");
		out.println("         if (cmp < 0) {");
		out.println("            high = mid - 1;");
		out.println("         } else if (cmp > 0) {");
		out.println("            low = mid + 1;");
		out.println("         } else {");
		out.println("            return mid;");
		out.println("         }");
		out.println("      }");
		out.println("      return -1;");
		out.println("   }");
		out.println();
		out.println("   /**");
		out.println("    * @param rownum row index, starting with 1");
		out.println("    * @param colnum column index, starting with 1");
		out.println("    */");
		out.println("   public final static V getCell(V row, V col) {");
		out.println("      int rownum = (int) row.longValue();");
		out.println("      int colnum = (int) col.longValue();");
		out.print  ("      if (rownum<1 || rownum>");
		out.print  (table.getNumrows());
		out.print  (" || colnum<1 || colnum>");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"invalid columnnumber \" + colnum + \" in table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		if (table.isShuffled()) {
			out.print  ("      return V.getInstance(d[oo[(rownum-1)]*");
			out.print  (table.getNumcols());
			out.println("+colnum-1].toString());");
		} else {
			out.print  ("      return V.getInstance(d[(rownum-1)*");
			out.print  (table.getNumcols());
			out.println("+colnum-1].toString());");
		}
		out.println("   }");
		out.println("   public final static V getCellsRow(V row, V colFrom, V colTo) {");
		out.println("      int rownum     = (int) row.longValue();");
		out.println("      int colnumFrom = (int) colFrom.longValue();");
		out.println("      int colnumTo   = (int) colTo.longValue();");
		out.print  ("      if (rownum<1 || rownum>");
		out.print  (table.getNumrows());
		out.print  (" || colnumFrom<1 || colnumFrom>");
		out.print  (table.getNumcols());
		out.print  (" || colnumTo>");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"invalid column/rownumber in cell-access for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int len = colnumTo - colnumFrom + 1;");
		out.println("      if (len<0) {");
		out.println("         len = 0;");
		out.println("      }");
		out.println("      V[] ret = new V[len];");
		if (table.isShuffled()) {
			out.print  ("      int ind = (oo[rownum-1])*");
			out.print  (table.getNumcols());
			out.println("+colnumFrom-1;");
		} else {
			out.print  ("      int ind = (rownum-1)*");
			out.print  (table.getNumcols());
			out.println("+colnumFrom-1;");
		}
		out.println("      for (int i=0; i<len; i++, ind++) {");
		out.println("         ret[i] = V.getInstance(d[ind].toString());");
		out.println("      }");
		out.println("      return V.getInstance(ret);");
		out.println("   }");
		out.println("   public final static V getCellsColumn(V rowFrom, V rowTo, V col) {");
		out.println("      int rownumFrom = (int) rowFrom.longValue();");
		out.println("      int rownumTo   = (int) rowTo.longValue();");
		out.println("      int colnum     = (int) col.longValue();");
		out.print  ("      if (rownumFrom<1 || rownumFrom>");
		out.print  (table.getNumrows());
		out.print  (" || rownumTo>");
		out.print  (table.getNumrows());
		out.print  (" || colnum<1 || colnum>");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"invalid column/rownumber in cell-access for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int len = rownumTo - rownumFrom + 1;");
		out.println("      if (len<0) {");
		out.println("         len = 0;");
		out.println("      }");
		out.println("      V[] ret = new V[len];");
		if (table.isShuffled()) {
			out.println("      int colind=colnum-1;");
			out.println("      for (int i=0, rowind=rownumFrom-1; i<len; i++, rowind++) {");
			out.print  ("         int ind = oo[rowind]*");
			out.print  (table.getNumcols());
			out.println("+colind;");
			out.println("         ret[i] = V.getInstance(d[ind].toString());");
			out.println("      }");
		} else {
			out.print  ("      int ind = (rownumFrom-1)*");
			out.print  (table.getNumcols());
			out.print  ("+colnum-1;");
			out.print  ("      for (int i=0; i<len; i++, ind+=");
			out.print  (table.getNumcols());
			out.println(") {");
			out.println("         ret[i] = V.getInstance(d[ind].toString());");
			out.println("      }");
		}
		out.println("      return V.getInstance(ret);");
		out.println("   }");
		out.println("   public final static V getCells(V rowFrom, V rowTo, V colFrom, V colTo) {");
		out.println("      int rownumFrom = (int) rowFrom.longValue();");
		out.println("      int rownumTo   = (int) rowTo.longValue();");
		out.println("      int colnumFrom = (int) colFrom.longValue();");
		out.println("      int colnumTo   = (int) colTo.longValue();");
		out.print  ("      if (rownumFrom<1 || rownumTo>");
		out.print  (table.getNumrows());
		out.print  (" || colnumFrom<1 || colnumFrom>");
		out.print  (table.getNumcols());
		out.print  (" || colnumTo>");
		out.print  (table.getNumcols());
		out.println(") {");
		out.print  ("         throw new ExceptionCalculation(\"invalid column/rownumber in cell-access for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      int lenRows = rownumTo - rownumFrom + 1;");
		out.println("      if (lenRows<0) {");
		out.println("         lenRows = 0;");
		out.println("      }");
		out.println("      int lenCols = colnumTo - colnumFrom + 1;");
		out.println("      if (lenCols<0) {");
		out.println("         lenCols = 0;");
		out.println("      }");
		out.println("      V[] ret = new V[lenRows];");
		out.println("      for (int irow=0; irow<lenRows; irow++) {");
		if (table.isShuffled()) {
			out.print  ("         int ind = oo[rownumFrom+irow-1] * ");
			out.print  (table.getNumcols());
			out.println(" + colnumFrom - 1;");
		} else {
			out.print  ("         int ind = (rownumFrom+irow-1) * ");
			out.print  (table.getNumcols());
			out.println(" + colnumFrom - 1;");
		}
		out.println("         V[] vrow = new V[lenCols];");
		out.println("         for (int icol=0; icol<lenCols; icol++, ind++) {");
		out.println("            vrow[icol] = V.getInstance(d[ind].toString());");
		out.println("         }");
		out.println("         V retrow = V.getInstance(vrow);");
		out.println("         ret[irow] = retrow;");
		out.println("      }");
		out.println("      return V.getInstance(ret);");
		out.println("   }");
		out.println("   public final static V getCellByName(V row, V colname) {");
		out.println("      String coln = colname.stringValue();");
		out.println("      int colind = getColindex(coln);");
		out.println("      return getCell(row, V.getInstance(colind+1));");
		out.println("   }");
		out.println("   public final static V getCellsRowByName(V row, V colNameFrom, V colNameTo) {");
		out.println("      String colnFrom = colNameFrom.stringValue();");
		out.println("      String colnTo   = colNameTo.stringValue();");
		out.println("      int colindFrom  = getColindex(colnFrom);");
		out.println("      int colindTo    = getColindex(colnTo);");
		out.println("      if(colindFrom<0 || colindTo<0) {");
		out.print  ("         throw new ExceptionCalculation(\"invalid columnnumber in cell-access for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      return getCellsRow(row, V.getInstance(colindFrom+1), V.getInstance(colindTo+1));");
		out.println("   }");
		out.println("   public final static V getCellsColumnByName(V rowFrom, V rowTo, V colname) {");
		out.println("      String coln = colname.stringValue();");
		out.println("      int colind = getColindex(coln);");
		out.println("      return getCellsColumn(rowFrom, rowTo, V.getInstance(colind+1));");
		out.println("   }");
		out.println("   public final static V getCellsByName(V rowFrom, V rowTo, V colNameFrom, V colNameTo) {");
		out.println("      String colnFrom = colNameFrom.stringValue();");
		out.println("      String colnTo   = colNameTo.stringValue();");
		out.println("      int colindFrom  = getColindex(colnFrom);");
		out.println("      int colindTo    = getColindex(colnTo);");
		out.println("      if(colindFrom<0 || colindTo<0) {");
		out.print  ("         throw new ExceptionCalculation(\"invalid column/rownumber in cell-access for table ");
		out.print  (table.getName());
		out.println("\", null);");
		out.println("      }");
		out.println("      return getCells(rowFrom, rowTo, V.getInstance(colindFrom+1), V.getInstance(colindTo+1));");
		out.println("   }");
		out.println("   public final static V interpol(V key) {");
		if (table.getNumcols()<2 || !table.getColnumeric()[0]) {
			out.println("      /* only 1 column or first column not numeric */");
			out.print  ("      throw new ExceptionCalculation(\"did not find row in table ");
			out.print  (table.getName());
			out.println(" access 'interpol' with key: \"+key.stringValue(), null);");
		} else {
			out.println("      double dkey; ");
			out.println("      if (key.isDouble()) {");
			out.println("         dkey = key.doubleValue();");
			out.println("      } else {");
			out.print  ("         throw new ExceptionCalculation(\"did not find row in table ");
			out.print  (table.getName());
			out.println(" access 'interpol' with key: \"+key.stringValue(), null);");
			out.println("      }");
			out.println("      /* do lookup-search with just one key */");
			out.println("      int low = 0;");
			out.println("      int high = 1;");
			out.println("      int mid;");
			out.println("      while (low <= high) {");
			out.println("         mid = (low + high) >>> 1;");
			out.print  ("         int indrowdata = mid*");
			out.print  (table.getNumcols());
			out.println(';');
			out.println("         /* interval comparison */");
			out.println("         Object val = d[indrowdata];");
			out.println("         double drow1 = getDouble(val);");
			out.println("         int cmp = Double.compare(dkey, drow1);");
			out.println("         /* lookdown:  value-in-row <  key <= value-in-next-row */");
			out.println("         if (cmp>=0) {");
			out.print  ("            if (mid>=");
			out.print  (table.getNumrows()-1);
			out.println(") {");
			out.println("               return cmp==0 ? null : null; /* also when last entry found exactly -> same es notfound in VPMS!? */");
			out.println("            }");
			out.println("            if (cmp==0) { //found exactly");
			out.println("               double dval1 = getDouble(d[indrowdata+1]);");
			out.println("               return V.getInstance(dval1);");
			out.println("            }");
			out.println("            /* see if next column would fit as well */");
			out.print  ("            int indrowdatanext = (mid+1)*");
			out.print  (table.getNumcols());
			out.println(';');
			out.println("            Object valnext = d[indrowdatanext];");
			out.println("            double drow2 = getDouble(valnext);");
			out.println("            cmp = Double.compare(dkey, drow2);");
			out.println("            if (cmp<0) { //got it!");
			out.println("               double dval1 = getDouble(d[indrowdata+1]);");
			out.println("               double dval2 = getDouble(d[indrowdatanext+1]);");
			out.println("               double dresult = dval1 + (dkey-drow1) * (dval2-dval1) / (drow2-drow1);");
			out.println("               return V.getInstance(dresult);");
			out.println("            } else {");
			out.println("               cmp = 1; //continue search behind current row");
			out.println("            }");
			out.println("         }");
			out.println("         if (cmp < 0) {");
			out.println("            high = mid - 1;");
			out.println("         } else if (cmp > 0) {");
			out.println("            low = mid + 1;");
			out.println("         } ");
			out.println("      }");
			out.print  ("      throw new ExceptionCalculation(\"did not find row in table ");
			out.print  (table.getName());
			out.println(" access 'interpol' with key: \"+key.stringValue(), null);");
		}
		out.println("   }");
	}
	
	private void printData() {
		/* initialization of data */
		/* we start with static block */
		/* after the static block, there might be one or more init<n>-methods
		 * which calls the next one if there is one
		 * TODO: create additional init-classes if >65000 constants
		 * because number of constants in class is restricted to 2^16 values
		 */
		out.println("   static {");
		out.println("      final Object[] v = d;");
		int indvalue=0;
		int indvaluestaticinitializer=0; /* number of values initialized in current static initializer / method */
		int indstaticinitializer=0; /* number of static initializers / init methods */
		for (int indrow=0; indrow<table.getNumrows(); indrow++) {
			/* start new static initializer if old one is pretty big */
			if (indrow>0 && indvaluestaticinitializer>MAXINITSTMTS_INMETHOD) {
				indvaluestaticinitializer=0;
				indstaticinitializer++;
				out.print("      init");
				out.print(indstaticinitializer);
				out.println("();");
				out.println("   }");
				out.print("   private final static void init");
				out.print(indstaticinitializer);
				out.println("() {");
				out.println("      final Object[] v = d;");
				if (indvalue>32767) {
					out.print("      int i=");
					out.print(indvalue);
					out.println(";");
				}
			}
			out.print("      ");
			String[] datarow = table.getData()[indrow];
			Object[] datarowObjects = table.getDataObjects()[indrow];
			for (int indvalueinrow = 0; indvalueinrow<table.getNumcols(); indvalueinrow++, indvalue++, indvaluestaticinitializer++) {
				String value = datarow[indvalueinrow];
				Object valueObject = datarowObjects[indvalueinrow];
				if (indvalue==32767) { //ints above 32767 would go into constant table -> better to use local variable
					out.println();
					out.println("      int i=32767;");
					out.print  ("      ");
				}
				out.print("v[");
				if (indvalue<32767) {
					out.print(indvalue);
				} else {
					out.print("i++");
				}
				out.print("]=");
				
//				if (valueObject instanceof Float) {
//					out.print(value);
//					out.print('f');
//				} else if (valueObject instanceof Double) {
//					out.print(value);
//					out.print('d');
//				} else if (valueObject instanceof Byte || valueObject instanceof Short || valueObject instanceof Integer || valueObject instanceof Long){
//					out.print(value);
//				} else {
					out.print('"');
					out.print(value);
					out.print('"');
//				}
				out.print("; ");
			}
			out.println();
		}
		if (table.isShuffled()) {
			out.println("      initoo();");
		}
		out.println("   }");
		if (table.isShuffled()) {
			out.println("   private final static void initoo() {");
			out.println("      final int[] o = oo;");
			out.print  ("     ");
			int[] o = table.getRowindori();
			for (int indrow=0; indrow<table.getNumrows(); indrow++) {
				if ((indrow+1) % 10 ==0) {
					out.println();
					out.print("      ");
				} else {
					out.print(' ');
				}
				out.print("o[");
				out.print(indrow);
				out.print("]=");
				out.print(o[indrow]);
				out.print(';');
			}
			out.println();
			out.println("   }");
		}
		out.println("}");
		out.close();
	}

	public static void generate(ModelSimple model, String foldernameout, String packagename, boolean gwt, boolean trace) throws IOException {
		JavaTables obj = new JavaTables(model, foldernameout, packagename, gwt, trace);
		obj.run();
	}
}
