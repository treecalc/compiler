package treecalc.comp.js;

/* use symbol tables / scopes to resolve names */

import java.io.IOException;
import java.io.PrintStream;

import treecalc.comp.GenTable;
import treecalc.comp.ModelSimple;


public class JSTables {
	private final ModelSimple model;
	private final String foldernameout;	
	private PrintStream out;
	private GenTable table;
	private JSPrintHelper p;
	 

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

	public static final String FUNCTION_FINDEXACT = "findExact";
	public static final String FUNCTION_FINDEXACTCOLUMN = "findExactColumn";
	public static final String FUNCTION_FINDEXACTCOLUMNINDEX = "findExactColumnIndex";
	public static final String FUNCTION_FINDINTERVALDOWN = "findIntervalDown";
	public static final String FUNCTION_FINDINTERVALDOWNCOLUMN = "findIntervalDownColumn";
	public static final String FUNCTION_FINDINTERVALUP = "findIntervalUp";
	public static final String FUNCTION_FINDINTERVALUPCOLUMN = "findIntervalUpColumn";
	public static final String FUNCTION_FINDROW = "findRow";
	public static final String FUNCTION_FINDROWDIRECT = "findRowDirect";
	public static final String FUNCTION_FINDROWEXACT = "findRowExact";
	public static final String FUNCTION_GET = "get";
	public static final String FUNCTION_GETCELL = "getCell";
	public static final String FUNCTION_GETCELLBYNAME = "getCellByName";
	public static final String FUNCTION_GETCELLS = "getCells";
	public static final String FUNCTION_GETCELLSBYNAME = "getCellsByName";
	public static final String FUNCTION_GETCELLSCOLUMN = "getCellsColumn";
	public static final String FUNCTION_GETCELLSCOLUMNBYNAME = "getCellsColumnByName";
	public static final String FUNCTION_GETCELLSROW = "getCellsRow";
	public static final String FUNCTION_GETCELLSROWBYNAME = "getCellsRowByName";
	public static final String FUNCTION_GETCOLINDEX = "getColindex";
	public static final String FUNCTION_GETDATA = "getData";
	public static final String FUNCTION_GETIN = "getIN";
	public static final String FUNCTION_GETNUMCOLS = "getNumcols";
	public static final String FUNCTION_GETNUMROWS = "getNumrows";
	public static final String FUNCTION_GETTABLEID = "getTableid";
	public static final String FUNCTION_GETOO = "getOo";	
	public static final String FUNCTION_INTERPOL = "interpol";
	
	private static final String NS_TABLE = "tc.t";
	private static final String NS_FUNCTIONS = NS_TABLE + ".functions";
	public static final String NS_TABLES = NS_TABLE + ".tables";
	private static final String METHOD_EXACT_STRI = NS_FUNCTIONS + ".METHOD_EXACT_STRI";
	private static final String METHOD_INTERVAL_UP = NS_FUNCTIONS + ".METHOD_INTERVAL_UP";
	private static final String METHOD_INTERVAL_DOWN = NS_FUNCTIONS + ".METHOD_INTERVAL_DOWN";
	
	
		
	public static final String[] FUNCTIONNAMES = new String[]{
		FUNCTION_FINDEXACT,
		FUNCTION_FINDEXACTCOLUMN,
		FUNCTION_FINDEXACTCOLUMNINDEX,
		FUNCTION_FINDINTERVALDOWN,
		FUNCTION_FINDINTERVALDOWNCOLUMN,
		FUNCTION_FINDINTERVALUP,
		FUNCTION_FINDINTERVALUPCOLUMN,
		FUNCTION_FINDROW,
		FUNCTION_FINDROWDIRECT,
		FUNCTION_FINDROWEXACT,
		FUNCTION_GET,
		FUNCTION_GETCELL,
		FUNCTION_GETCELLBYNAME,
		FUNCTION_GETCELLS,
		FUNCTION_GETCELLSBYNAME,
		FUNCTION_GETCELLSCOLUMN,
		FUNCTION_GETCELLSCOLUMNBYNAME,
		FUNCTION_GETCELLSROW,
		FUNCTION_GETCELLSROWBYNAME,
		FUNCTION_GETCOLINDEX,
		FUNCTION_GETDATA,
		FUNCTION_GETIN,
		FUNCTION_GETNUMCOLS,
		FUNCTION_GETNUMROWS,
		FUNCTION_GETTABLEID,
		FUNCTION_GETOO,		
		FUNCTION_INTERPOL		
	};
	
	
	
	
	private JSTables(ModelSimple model, String foldernameout, String packagename) {
		this.model = model;
		this.foldernameout = foldernameout;		
	}

	private void run() throws IOException {
		// processTables();
		printT();
	}

	/**
	 * class for dynamic dispatching 
	 * @throws IOException 
	 */
	private void printT() throws IOException {
		String classname = "Tables";		
		out = new PrintStream(foldernameout + "/" + classname + ".js", "ISO-8859-1");
		p = new JSPrintHelper(out);
				 		
		out.println(NS_TABLE + " = {};");
		out.println(NS_FUNCTIONS + " = {");
		int indent = 0;
		printConstants(1);
		printTableNames(indent+1);
//		printGetTableIndex(indent+1);
		printAccessFunctions(1);
		out.println("}");
		
		out.println(NS_TABLES + " = {");		
		processTables();
		p.pl(1, "finalElement: {}");
		
		

//		printDynamicDispatch();
		out.println("}");
		out.close();
	}
	
	private void processTables() throws IOException {
		for (int i=0; i<model.getTableSize(); i++) {
			table = model.getTable(i);
			printTable(1);
		}
	}
	
	private void printConstants(int indent) {
		p.pl(indent, "METHOD_EXACT_STRI: 0,");
		p.pl(indent, "METHOD_INTERVAL_UP: 1,");
		p.pl(indent, "METHOD_INTERVAL_DOWN: 2,");
		out.println();
	}
	
	private void printTableNames(int indent) {
		p.pl(indent, "tn: [");
		int size = model.getTableSize();
		if (size > 0) {
			if (size > 1) {
				for (int i=0; i<size-1; i++) {
					table = model.getTable(i);
					String tablename = table.getName().toUpperCase();
					p.pl(indent+1, "'" + tablename + "',");					
				}
			}
			table = model.getTable(size-1);
			String tablename = table.getName().toUpperCase();
			p.pl(indent+1, "'" + tablename + "'");
		}
		p.pl(indent, "],");
	}
	
	private void printGetTableIndex(int indent) {
		p.pl(indent, "getTableIndex: function(tabname) {");
		p.pl(indent+1, "var i = this.tn.indexOf(tabname);");
		p.pl(indent+1, "return (undefined === i) ? -1 : i;");
		p.pl(indent, "},");
	}
	
	private void printTable(int indent) throws IOException {
		out.println();						
		p.p(indent, table.getName());
		out.println(": {");
		printTableProperties(2);
		printData();		
		printTableFunctions(2);
		p.pl(1, "},");	
	}
	
	private void printTableFunctions(int indent) {		
		for (String name:FUNCTIONNAMES) {
			p.printProperty(indent, name, NS_FUNCTIONS + "." + name);
		}
	}
	
	private void printTableProperties(int indent) {
		p.printProperty(indent, "colnames", table.getColnames());
		p.printProperty(indent, "colnumeric", table.getColnumeric());
		p.printProperty(indent, "numcols", String.valueOf(table.getNumcols()));
		p.printProperty(indent, "numrows", String.valueOf(table.getNumrows()));
		p.printProperty(indent, "directAccess", String.valueOf(table.isDirectAccess()));
		p.printProperty(indent, "directAccessOffset", String.valueOf(table.getDirectAccessOffset()));
		p.printProperty(indent, "shuffled", String.valueOf(table.isShuffled()));
	}
	
	private void printAccessFunctions(int indent) {
		printGetColindex(1);		
		printGetData(1);		
		printGetOo(1);			
		printGetNumrows(1);		
		printGetNumcols(1);		
		printFindRowExact(1);		
		printGetIN(1);		
		printGet(1);		
		printFindExact(1);		
		printFindExactColumn(1);		
		printFindExactColumnIndex(1);		
		printFindIntervalUp(1);		
		printFindIntervalUpColumn(1);		
		printFindIntervalDown(1);		
		printFindIntervalDownColumn(1);		

//		if (table.isDirectAccess()) {			
			printFindRowDirect(1);			
//		}
		
		printFindRow(1);		
		printGetCell(1);		
		printGetCellsRow(1);		
		printGetCellsColumn(1);		
		printGetCells(1);		
		printGetCellByName(1);		
		printGetCellsRowByName(1);
		printGetCellsColumnByName(1);
		printGetCellsByName(1);
		printInterpol(1);


		
		out.println("");
	}
	
	private void printGetColindex(int indent) {
		/* find column index based on column name */
		/* linear search ... could be replaced by binary search if necessary */
		/* linear search should be fast though especially when starting at end of the columnnames-list
		 * because normally first columns are used for comparisoin, last ones to get the value
		 */
		p.pl(indent, "getColindex: function(name) {");
		p.pl(indent+1, "var nameup;");
		p.pl(indent+1, "var i;");
		p.pl(indent+1, "nameup = name.toUpperCase();");
		p.pl(indent+1, "for (i=0; i<this.numcols; i++) {");
		p.pl(indent+2, "if (nameup == this.colnames[i]) {");
		p.pl(indent+3, "return i;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return -1;");
		p.pl(indent, "},");
	}
	
	private void printGetData(int indent) {		
		p.pl(indent, "/* get raw table data ... might be reordered, you get the ordering by getOo() */");
		p.pl(indent, JSTables.FUNCTION_GETDATA + ": function() {");
		p.pl(indent+1, "return this.d;");
		p.pl(indent, "},");
	}
	
	private void printGetOo(int indent) {		
		p.pl(indent, JSTables.FUNCTION_GETOO + ": function() {");
		p.pl(indent+1, "return this.shuffled ? this.oo : null;");
		p.pl(indent, "},");
	}
	
	private void printGetNumrows(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETNUMROWS + ": function() {");
		p.pl(indent+1, "return this.numrows;");		
		p.pl(indent, "},");
	}

	private void printGetNumcols(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETNUMCOLS + ": function() {");
		p.pl(indent+1, "return this.numcols;");
		p.pl(indent, "},");
	}
	
	private void printFindRowExact(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDROWEXACT + ": function(/* (V ... keys) */) {");		
		p.pl(indent+1, "if (arguments.length >= this.numcols) {");
        p.pl(indent+2, "return -1;");
        p.pl(indent+1, "}");
        p.printArgumentsToArray(indent+1, "args");
        p.pl(indent+1, "args.unshift(" + NS_FUNCTIONS + ".METHOD_EXACT_STRI);");
        p.pl(indent+1, "return " + NS_FUNCTIONS + ".findRow.apply(this, args);");
        p.pl(indent, "},");
	}
	
	private void printGetIN(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETIN + ": function(rowind, columnname) {");
		p.pl(indent+1, "var colname;");
        p.pl(indent+1, "var colind;");
        p.pl(indent+1, "colname = columnname.stringValue();");
        p.pl(indent+1, "colind = this.getColindex(colname);");
        p.pl(indent+1, "if (colind<0) {");
        p.pl(indent+2, "return null;");
        p.pl(indent+1, "}");
        p.pl(indent+1, "if (rowind<0 || rowind>=this.numrows) {");
        p.pl(indent+2, "return null;");
        p.pl(indent+1, "}");
        p.pl(indent+1, "return this.d[rowind*this.numcols + colind];");
        p.pl(indent, "},");
	}
	
	private void printGet(int indent) {		
		p.pl(indent, JSTables.FUNCTION_GET + ": function(row, col) {");
//		if (!table.isShuffled()) {
			p.p(indent+1, "return this.d[row*");
//		} else {
//			out.print  ("      return V.getInstance(d[oo[rowind]*");
//		}
		out.print  ("this.numcols");
		out.println(" + col];");
		p.pl(indent, "},");
	}
	
	private void printFindExact(int indent) {
		/*
		 * Sucht die Zeile, in der alle übergebenen Werte in der gleichen Reihenfolge zu finden sind.
		 */
		p.pl(indent, JSTables.FUNCTION_FINDEXACT + ": function(/*V ... keys*/) {");		
        p.pl(indent+1, "if (arguments.length >= this.numcols) {");
        p.pl(indent+2, "return null;");
        p.pl(indent+1, "}");
        printFindRowCall(indent+1, 0, METHOD_EXACT_STRI);        
        p.pl(indent+1, "return this.d[ind*this.numcols + arguments.length];");
        p.pl(indent, "},");
	}
	
	private void printFindExactColumn(int indent) {
		/*
		 * Funktioniert mit den keys wie findExact. Anschließendes Filtern/Slicen mit Spaltenname.
		 */
		p.pl(indent, JSTables.FUNCTION_FINDEXACTCOLUMN + ": function(columnname) /*, V ... keys*/ {");               
        p.pl(indent+1, "var colname = columnname.stringValue();");
        p.pl(indent+1, "var colind = this.getColindex(colname);");
        p.printArgumentsToArrayAndShift(indent+1, "args");
        p.pl(indent+1, "args.unshift(colind);");
        p.pl(indent+1, "return " + JSTables.NS_FUNCTIONS + "." + JSTables.FUNCTION_FINDEXACTCOLUMNINDEX + ".apply(this, args);");
        p.pl(indent, "},");        
	}
	
	private void printFindExactColumnIndex(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDEXACTCOLUMNINDEX + ": function(colind /*, V ... keys*/) {");		
        p.pl(indent+1, "if (colind<0 || colind>=this.numcols) {");
        p.pl(indent+2, "return null;");
        p.pl(indent+1, "}");
        printFindRowCall(indent+1, 1, METHOD_EXACT_STRI);        
        p.pl(indent+1, "return this.d[ind*this.numcols + colind];");
        p.pl(indent, "},");		
	}
	
	private void printFindRowCall(int indent, int shiftNTimes, String mode) {
		p.printArgumentsToArray(indent, "args");
		for (int i=0; i<shiftNTimes; i++) {
			p.pl(indent, "args.shift();");
		}
        p.pl(indent, "args.unshift(" + mode + ");");
        p.pl(indent, "var ind = " + NS_FUNCTIONS + ".findRow.apply(this, args);");
        p.pl(indent, "if (ind<0) {");
        p.pl(indent+1, "return null;");
        p.pl(indent, "}");
	}
	
	
	private void printFindIntervalUp(int indent) {		
		p.pl(indent, JSTables.FUNCTION_FINDINTERVALUP + ": function() /*V ... keys*/ {");        
        p.pl(indent+1, "if (arguments.length >= this.numcols) {");
        p.pl(indent+2, "return null;");
        p.pl(indent+1, "}");
        printFindRowCall(indent+1, 0, METHOD_INTERVAL_UP);        
        p.pl(indent+1, "return this.d[ind*this.numcols + arguments.length];");
    	p.pl(indent, "},");		
	}
	
	private void printFindIntervalDown(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDINTERVALDOWN + ": function(/*V ... keys*/) {");
		p.pl(indent+1, "if (arguments.length >= this.numcols) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		printFindRowCall(indent+1, 0, METHOD_INTERVAL_DOWN);		
		p.pl(indent+1, "return this.d[ind*this.numcols + arguments.length];");
		p.pl(indent, "},");
	}
	
	private void printFindRowDirect(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDROWDIRECT + ": function(key) {");
		p.pl(indent+1, "if (!key.isDouble()) {");
		p.pl(indent+2, "return -1;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var dval = key.doubleValue();");
		p.pl(indent+1, "var i = Math.floor(dval);");
		p.pl(indent+1, "if (dval - i != 0) {");
		p.pl(indent+2, "return -1;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var ind = i - this.directAccessOffset;");
		p.pl(indent+1, "if (ind<0 || ind>=this.numrows) {");
	    p.pl(indent+2, "return -1;");
	    p.pl(indent+1, "}");
	    p.pl(indent+1, "var valstr = this.d[ind*this.numcols].stringValue();");
	    p.pl(indent+1, "var keystr = key.stringValue();");
	    p.pl(indent+1, "return valstr.seqi(keystr) ? ind : -1;");
	    p.pl(indent, "},");		
	}
	
	private void printFindRow(int indent) {		
		p.pl(indent, JSTables.FUNCTION_FINDROW + ": function(method/*,final V ... keys*/) {");
		p.pl(indent+1, "if (1 == arguments.length) {");
		p.pl(indent+2, "return -1;");
		p.pl(indent+1, "}");
		p.printArgumentsToArrayAndShift(indent+1, "keys");        
        p.pl(indent+1, "if (this.directAccess) {");
		p.pl(indent+2, "if (1 === keys.length && " + NS_FUNCTIONS + ".METHOD_EXACT_STRI === method) {");
		p.pl(indent+3, "return this.findRowDirect(keys[0]);");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");		
		p.pl(indent+1, "var keysString = [];");
		p.pl(indent+1, "var keysDouble = [];");
		p.pl(indent+1, "for (var i=0; i<keys.length; i++) {");
		p.pl(indent+2, "var key = keys[i];");
		p.pl(indent+2, "keysString[i] = key.stringValue();");
		p.pl(indent+2, "if (key.isDouble()) {");
		p.pl(indent+3, "keysDouble[i] = key.doubleValue();");
		p.pl(indent+2, "} else {");
		p.pl(indent+3, "/* if key cannot be converted to a double, but the column is numeric, the value can never be found */");
		p.pl(indent+3, "if (this.colnumeric[i]) {");
		p.pl(indent+4, "return -1;");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var low = 0;");
		p.pl(indent+1, "var high = this.numrows-1;");
		p.pl(indent+1, "var mid;");
		p.pl(indent+1, "var lenexact = keys.length;");
		p.pl(indent+1, "if (method != " + NS_FUNCTIONS + ".METHOD_EXACT_STRI) {");
		p.pl(indent+2, "lenexact--;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "while (low <= high) {");
		p.pl(indent+2, "mid = (low + high) >>> 1;");
		p.pl(indent+2, "var indrowdata = mid*this.numcols;");
		p.pl(indent+2, "var cmp=0;");
		p.pl(indent+2, "/* exact comparison for first columns */");
		p.pl(indent+2, "for (var indcol=0; indcol<lenexact; indcol++, indrowdata++) {");
		p.pl(indent+3, "var val = this.d[indrowdata];");
		p.pl(indent+3, "var vald = +val;");
		p.pl(indent+3, "if (this.colnumeric[indcol]) {");		
		p.pl(indent+4, "cmp = (keysDouble[indcol] < vald) ? -1 : (keysDouble[indcol] > vald ? 1 : 0);");		
		p.pl(indent+4, "if (cmp==0) {");
		p.pl(indent+5, "cmp = keysString[indcol].toUpperCase().localeCompare(val.toUpperCase());");
		p.pl(indent+4, "}");
		p.pl(indent+3, "}");
		p.pl(indent+3, "else { ");
		p.pl(indent+4, "cmp = keysString[indcol].toUpperCase().localeCompare(val.toUpperCase());");
		p.pl(indent+3, "}");
		p.pl(indent+3, "if (cmp!=0) {");
		p.pl(indent+4, "break;");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if (cmp==0 && method!= " + NS_FUNCTIONS + ".METHOD_EXACT_STRI) {");
		p.pl(indent+3, "/* interval comparison */");
		p.pl(indent+3, "var val = this.d[indrowdata];");
		p.pl(indent+3, "var vald = +val;");
		p.pl(indent+3, "var dkey = keysDouble[lenexact];");
		p.pl(indent+3, "cmp = (dkey < vald) ? -1 : (dkey > vald ? 1 : 0);");		
		p.pl(indent+3, "/* lookup  :  value-in-row <= key <  value-in-next-row */");
		p.pl(indent+3, "/* lookdown:  value-in-row <  key <= value-in-next-row */");
		p.pl(indent+3, "if (cmp>0 || cmp==0 && method==" + NS_FUNCTIONS + ".METHOD_INTERVAL_UP) {");
		p.pl(indent+4, "cmp = 0;");
		p.pl(indent+4, "/* compare with next row if there is one*/");
		p.pl(indent+4, "if (mid < this.numrows-1) {");
		p.pl(indent+5, "/* see if next column would fit as well */");
		p.pl(indent+5, "indrowdata = (mid+1)*this.numcols;");
		p.pl(indent+5, "for (var indcol=0; indcol<lenexact; indcol++, indrowdata++) {");
		p.pl(indent+6, "val = this.d[indrowdata];");
		p.pl(indent+6, "vald = +val;");
		p.pl(indent+6, "if (this.colnumeric[indcol]) {");
		p.pl(indent+7, "cmp = keysDouble[indcol] < vald ? -1 : (keysDouble[indcol]>vald ? 1 : 0);");
		p.pl(indent+7, "if (cmp==0) {");
		p.pl(indent+8, "cmp = keysString[indcol].toUpperCase().localeCompare(val.toUpperCase());");
		p.pl(indent+7, "}");
		p.pl(indent+6, "}");
		p.pl(indent+6, "else { ");
		p.pl(indent+7, "cmp = keysString[indcol].toUpperCase().localeCompare(val.toUpperCase());");
		p.pl(indent+6, "}");
		p.pl(indent+6, "if (cmp!=0) {");
		p.pl(indent+7, "break;");
		p.pl(indent+6, "}");
		p.pl(indent+5, "}");
		p.pl(indent+5, "/* next row does not fit -> we have it! */");
		p.pl(indent+5, "if (cmp!=0) {");
		p.pl(indent+6, "cmp = 0;");
		p.pl(indent+5, "}");
		p.pl(indent+5, "else {");
		p.pl(indent+6, "/* see if the interval-search-key fits for next row */");
		p.pl(indent+6, "var valnext = this.d[indrowdata];");
		p.pl(indent+6, "var valnextd = +valnext;");
		p.pl(indent+6, "cmp = dkey < valnextd ? -1 : (dkey > valnextd ? 1 : 0);");
		p.pl(indent+6, "if (cmp<0 || cmp==0 && method==" + NS_FUNCTIONS + ".METHOD_INTERVAL_DOWN) {");
		p.pl(indent+7, "cmp = 0;");
		p.pl(indent+6, "}");
		p.pl(indent+6, "else {");
		p.pl(indent+7, "cmp = 1; //continue search behind current row");
		p.pl(indent+6, "}");
		p.pl(indent+5, "}");
		p.pl(indent+4, "}");
		p.pl(indent+4, "else {");
		p.pl(indent+5, "cmp = 0;");
		p.pl(indent+4, "}");
		p.pl(indent+3, "} else {");
		p.pl(indent+4, "cmp = -1;");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if (cmp < 0) {");
		p.pl(indent+3, "high = mid - 1;");
		p.pl(indent+2, "} else if (cmp > 0) {");
		p.pl(indent+3, "low = mid + 1;");
		p.pl(indent+2, "} else {");
		p.pl(indent+3, "return mid;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return -1;");
		p.pl(indent, "},");
	}
	
	private void printGetCell(int indent) {
		p.pl(indent, "/**");
		p.pl(indent, " * @param rownum row index, starting with 1");
		p.pl(indent, " * @param colnum column index, starting with 1");
		p.pl(indent, " */");
		p.pl(indent, JSTables.FUNCTION_GETCELL + ": function(row, col) {");
		p.pl(indent+1, "var rownum = row.longValue();");
		p.pl(indent+1, "var colnum = col.longValue();");
		p.pl(indent+1, "if (rownum<1 || rownum>this.numrows || colnum<1 || colnum>this.numcols) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return this.shuffled ? this.d[this.oo[(rownum-1)]*this.numcols + colnum-1] : this.d[(rownum-1)*this.numcols + colnum-1];");
		p.pl(indent, "},");
	}
	
	private void printGetCellsRow(int indent) {
		/*
		 * Holt aus der 'row'-Zeile alle Werte im Spaltenintervall colFrom-colTo 
		 */
		p.pl(indent, JSTables.FUNCTION_GETCELLSROW + ": function(row, colFrom, colTo) {");
		p.pl(indent+1, "var rownum = row.longValue();");
		p.pl(indent+1, "var colnumFrom = colFrom.longValue();");
		p.pl(indent+1, "var colnumTo = colTo.longValue();");
		p.pl(indent+1, "if (rownum<1 || rownum>this.numrows || colnumFrom<1 || colnumFrom>this.numcols || colnumTo>this.numcols) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var len = colnumTo - colnumFrom + 1;");
		p.pl(indent+1, "if (len<0) {");
		p.pl(indent+2, "len = 0;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var ret = [];");		
		p.pl(indent+1, "var ind = this.shuffled ? this.oo[rownum-1]*this.numcols + colnumFrom-1 : (rownum-1)*this.numcols + colnumFrom-1;");
		p.pl(indent+1, "for (i=0; i<len; i++, ind++) {");
		p.pl(indent+2, "ret[i] = this.d[ind].stringValue();");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return ret;");
		p.pl(indent, "},");
	}
	
	private void printGetCellsColumn(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLSCOLUMN + ": function(rowFrom, rowTo, col) {");
		p.pl(indent+1, "var rownumFrom = rowFrom.longValue();");
		p.pl(indent+1, "var rownumTo = rowTo.longValue();");
		p.pl(indent+1, "var colnum = col.longValue();");
		p.pl(indent+1, "if (rownumFrom<1 || rownumFrom>this.numrows || rownumTo>this.numrows || colnum<1 || colnum>this.numcols) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var len = rownumTo - rownumFrom + 1;");
		p.pl(indent+1, "if (len<0) {");
		p.pl(indent+2, "len = 0;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "ret = new Array(len);");
		p.pl(indent+1, "var col = colnum-1;");
		p.pl(indent+1, "if (this.shuffled) {");		
		p.pl(indent+2, "var row = rownumFrom-1;");
		p.pl(indent+2, "for (var i=0; i<len; i++, row++) {");
		p.pl(indent+3, "var ind = this.oo[row]*this.numcols + col;");
		p.pl(indent+3, "ret[i] = this.d[ind];");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "else {");
		p.pl(indent+2, "var ind = (rownumFrom-1)*this.numcols + col;");
		p.pl(indent+2, "for (var i=0; i<len; i++, ind+=this.numcols) {");
		p.pl(indent+3, "ret[i] = this.d[ind];");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return ret;");
		p.pl(indent, "},");
	}
	
	private void printGetCells(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLS + ": function(rowFrom, rowTo, colFrom, colTo) {");
		p.pl(indent+1, "var rownumFrom = rowFrom.longValue();");
		p.pl(indent+1, "var rownumTo = rowTo.longValue();");
		p.pl(indent+1, "var colnumFrom = colFrom.longValue();");
		p.pl(indent+1, "var colnumTo = colTo.longValue();");
		p.pl(indent+1, "if (rownumFrom<1 || rownumTo>this.numrows || colnumFrom<1 || colnumFrom>this.numcols || colnumTo>this.numcols) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var lenRows = rownumTo - rownumFrom + 1;");
		p.pl(indent+1, "if (lenRows<0) {");
		p.pl(indent+2, "lenRows=0;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var lenCols = colnumTo - colnumFrom + 1;");
		p.pl(indent+1, "if (lenCols<0) {");
		p.pl(indent+2, "lenCols=0;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var ret = new Array(lenRows);");
		p.pl(indent+1, "for (var row=0; row<lenRows; row++) {");
		p.pl(indent+2, "if (this.shuffled) {");
		p.pl(indent+3, "var ind = this.oo[rownumFrom+row-1]*this.numcols + colnumFrom - 1;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "else {");
		p.pl(indent+3, "var ind = (rownumFrom+row-1)*this.numcols + colnumFrom - 1;");
		p.pl(indent+2, "}");
		p.pl(indent+2, "var vrow = new Array(lenCols);");
		p.pl(indent+2, "for (var col=0; col<lenCols; col++, ind++) {");
		p.pl(indent+3, "vrow[col] = this.d[ind];");
		p.pl(indent+2, "}");		
		p.pl(indent+2, "ret[row] = vrow;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return ret;");
		p.pl(indent, "},");
	}
	
	private void printGetCellByName(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLBYNAME + ": function(row, colname) {");
		p.pl(indent+1, "var coln = colname.stringValue();");
		p.pl(indent+1, "var col = this.getColindex(coln);");
		p.pl(indent+1, "return this.getCell(row, col+1);");
		p.pl(indent, "},");
	}
	
	private void printGetCellsRowByName(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLSROWBYNAME + ": function(row, colNameFrom, colNameTo) {");
		p.pl(indent+1, "var colnFrom = colNameFrom.stringValue();");
		p.pl(indent+1, "var colnTo = colNameTo.stringValue();");
		p.pl(indent+1, "var colFrom = this.getColindex(colnFrom);");
		p.pl(indent+1, "var colTo = this.getColindex(colnTo);");
		p.pl(indent+1, "if (colFrom<0 || colTo<0) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return this.getCellsRow(row, colFrom+1, colTo+1);");
		p.pl(indent, "},");
	}
	
	private void printGetCellsColumnByName(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLSCOLUMNBYNAME + ": function(rowFrom, rowTo, colname) {");
		p.pl(indent+1, "var coln = colname.stringValue();");
		p.pl(indent+1, "var col = this.getColindex(coln);");
		p.pl(indent+1, "return this.getCellsColumn(rowFrom, rowTo, col+1);");		
		p.pl(indent, "},");
	}
	
	private void printGetCellsByName(int indent) {
		p.pl(indent, JSTables.FUNCTION_GETCELLSBYNAME + ": function(rowFrom, rowTo, colNameFrom, colNameTo) {");
		p.pl(indent+1, "var colnFrom = colNameFrom.stringValue();");
		p.pl(indent+1, "var colnTo = colNameTo.stringValue();");
		p.pl(indent+1, "var colFrom = this.getColindex(colnFrom);");
		p.pl(indent+1, "var colTo = this.getColindex(colnTo);");
		p.pl(indent+1, "if (colFrom<0 || colTo<0) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return this.getCells(rowFrom, rowTo, colFrom+1, colTo+1);");
		p.pl(indent, "},");
	}
	
	private void printInterpol(int indent) {
		p.pl(indent, JSTables.FUNCTION_INTERPOL + ": function(key) {");
		p.pl(indent+1, "if (this.numcols<2 || !this.colnumeric[0]) {");
		p.pl(indent+2, "return null; /* only 1 column or first column not numeric */");
		p.pl(indent+1, "}");
		p.pl(indent+1, "var dkey;");
		p.pl(indent+1, "if (key.isDouble()) {");
		p.pl(indent+2, "dkey = +key;");
		p.pl(indent+1, "} else {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		p.pl(indent+1, "/* do lookup-search with just one key */");
		p.pl(indent+1, "var low = 0;");
		p.pl(indent+1, "var high = 1;");
		p.pl(indent+1, "var mid;");
		p.pl(indent+1, "while (low <= high) {");
		p.pl(indent+2, "mid = (low+high) >>> 1;");
		p.pl(indent+2, "var indrowdata = mid*this.numcols;");
		p.pl(indent+2, "/* interval comparison */");
		p.pl(indent+2, "var val = this.d[indrowdata];");
		p.pl(indent+2, "var drow1 = +val;");
		p.pl(indent+2, "var cmp = dkey < drow1 ? -1 : (dkey > drow1 ? 1 : 0);");
		p.pl(indent+2, "/* lookdown:  value-in-row <  key <= value-in-next-row */");
		p.pl(indent+2, "if (cmp>=0) {");
		p.pl(indent+3, "if (mid>=this.numrows-1) {");
		p.pl(indent+4, "return cmp==0 ? null : null; /* also when last entry found exactly -> same es notfound in VPMS!? */");
		p.pl(indent+3, "}");
		p.pl(indent+3, "if (cmp==0) { // found exactly");
		p.pl(indent+4, "var dval1 = +this.d[indrowdata+1]");
		p.pl(indent+4, "return dval1;");
		p.pl(indent+3, "}");
		p.pl(indent+3, "/* see if next column would fit as well */");
		p.pl(indent+3, "var indrowdatanext = (mid+1)*this.numcols;");
		p.pl(indent+3, "var valnext = this.d[indrowdatanext];");
		p.pl(indent+3, "var drow2 = +valnext;");
		p.pl(indent+3, "cmp = dkey < drow2 ? -1 : (dkey > drow2 ? 1 : 0);");
		p.pl(indent+3, "if (cmp<0) { // got it!");
		p.pl(indent+4, "var dval1 = +this.d[indrowdata+1];");
		p.pl(indent+4, "var dval2 = +this.d[indrowdatanext+1];");
		p.pl(indent+4, "var dresult = dval1 + (dkey-drow1) * (dval2-dval1) / (drow2-drow1);");
		p.pl(indent+4, "return dresult;");
		p.pl(indent+3, "} else {");
		p.pl(indent+4, "cmp = 1; // continue search behind current row");
		p.pl(indent+3, "}");
		p.pl(indent+2, "}");
		p.pl(indent+2, "if (cmp < 0) {");
		p.pl(indent+3, "high = mid - 1;");
		p.pl(indent+2, "} else if (cmp > 0) {");
		p.pl(indent+3, "low = mid + 1;");
		p.pl(indent+2, "}");
		p.pl(indent+1, "}");
		p.pl(indent+1, "return null;");
		p.p(indent, "}");
	}
	
	private void printFindIntervalUpColumn(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDINTERVALUPCOLUMN + ": function(columnname/*, V ... keys*/) {");
		p.pl(indent+1, "var colname = columnname.stringValue();");
		p.pl(indent+1, "var col = this.getColindex(colname);");
		p.pl(indent+1, "if (col<0) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		printFindRowCall(indent+1, 1, METHOD_INTERVAL_UP);		
		p.pl(indent+1, "return this.d[ind*this.numcols + col];");
		p.pl(indent, "},");
	}
	
	private void printFindIntervalDownColumn(int indent) {
		p.pl(indent, JSTables.FUNCTION_FINDINTERVALDOWNCOLUMN + ": function(columnname /*V ... keys*/) {");
		p.pl(indent+1, "var colname = columnname.stringValue();");
		p.pl(indent+1, "var col = this.getColindex(colname);");
		p.pl(indent+1, "if (col<0) {");
		p.pl(indent+2, "return null;");
		p.pl(indent+1, "}");
		printFindRowCall(indent+1, 1, METHOD_INTERVAL_DOWN);		
		p.pl(indent+1, "return this.d[ind*this.numcols + col]");
		p.pl(indent+1, "},");
	}
	
	
	
	private void printData() {		
		p.p(2, "d: [");		
		
		int indvalue=0;
		int indvaluestaticinitializer=0; /* number of values initialized in current static initializer / method */		
		
		for (int indrow=0; indrow<table.getNumrows(); indrow++) {								
			String[] datarow = table.getData()[indrow];			
			for (int indvalueinrow = 0; indvalueinrow<table.getNumcols(); indvalueinrow++, indvalue++, indvaluestaticinitializer++) {
				
				String value = datarow[indvalueinrow];																
				out.print("'");
				out.print(value);
				out.print("'");
				
				if (!((table.getNumcols() == indvalueinrow+1) && (table.getNumrows() == indrow+1))) {
					out.print(",");					
				}
				if ((table.getNumcols() == indvalueinrow+1) && (table.getNumrows() != indrow+1)) {
					out.println();
					p.p(2, "");
				}				
			}			
		}
		out.println("],");
				
		if (table.isShuffled()) {
			p.p(2, "oo: [");			
			int[] o = table.getRowindori();
			if (1 < table.getNumrows()) {
				for (int row=0; row<table.getNumrows()-1; row++) {
					if ((row+1) % 10 == 0) {
						out.println();
						p.p(2, "");
					}
					out.print("'");				
					out.print(o[row]);
					out.print("',");							
				}
			}
			out.print("'");				
			out.print(o[table.getNumrows()-1]);
			out.print("'");
			out.println("],");
		}		
	}
		
	public static void generate(ModelSimple model, String foldernameout, String packagename) throws IOException {
		JSTables obj = new JSTables(model, foldernameout, packagename);
		obj.run();
	}
}
