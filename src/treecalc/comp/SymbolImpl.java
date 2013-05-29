package treecalc.comp; 

public class SymbolImpl implements Symbol {
    private final String name;
    private Scope scope;
    private final int symboltype;
    private final int nargs;
    private int id;
    public SymbolImpl(String name, int symboltype, int nargs) { 
    	this.name = name!=null ? name.toUpperCase() : "";
    	this.symboltype = symboltype;
    	this.nargs = nargs;
    }
    @Override
    public Scope getScope() {
    	return scope;
    }
    @Override
    public void setScope (Scope scope) {
    	this.scope = scope;
    }
    @Override
    public String getSymbolName() { 
    	return name; 
    }
	@Override
	public int getSymboltype() {
		return symboltype;
	}
	@Override
	public int getNumArgs() {
		return nargs;
	}

	@Override
    public void setId(int id) {
		this.id = id;
	}
	@Override
    public int getId() {
		return id;
	}
	
	@Override
    public String toString() {
        if (scope!=null) {
        	return scope.getScopeName() + "." + getSymbolName();
        } else {
        	return getSymbolName();
        }
    }
}
