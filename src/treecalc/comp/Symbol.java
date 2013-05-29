package treecalc.comp; 

public interface Symbol {
	public static final int SYMBOLTYPE_BULITIN=1;
	public static final int SYMBOLTYPE_FUNCTION=2;
	public static final int SYMBOLTYPE_INPUT=3;
	public static final int SYMBOLTYPE_INPUTRESULTDEF=4;
	public static final int SYMBOLTYPE_TABLE=5;
	public static final int SYMBOLTYPE_TABLECOLUMN=6;
	public static final int SYMBOLTYPE_PARAMETER=7;
	public static final int SYMBOLTYPE_NODERESULTDEF=8;
	public static final int SYMBOLTYPE_TIMESINDEX=9;
	public static final int SYMBOLTYPE_LOCALVAR=10;
	public static final int SYMBOLTYPE_SPECIAL=11; //e.g. SELF
	
	
	public String getSymbolName();
	public int getNumArgs();
    public void setScope(Scope scope);
    public Scope getScope();
    public int getSymboltype();
    /* Id: unique number, e.g. for parameters/local vars within function */
    public void setId(int id); 
    public int getId(); 
}
