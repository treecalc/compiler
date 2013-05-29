package treecalc.comp; 

import java.util.List;

public interface Scope {
	public String getFullScopeName();
	public String getScopeName();
	public void define(Symbol sym);
    public Symbol resolve(String name, int nargs, boolean selfcalccall);
    public ResolvedCalc resolveCalc(ModelSimple model, String calcname, int nargs, boolean selfcalccall);
    public ScopeNode getScopeNode();
    public List<Symbol> getScopeDefines();
    public Scope getParent();
    public int getNewSymbolId();
    public int getSymbolIdCounter();
}
