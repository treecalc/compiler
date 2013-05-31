package treecalc.comp; 

import java.util.List;

public interface Scope {
	String getFullScopeName();
	String getScopeName();
	void define(Symbol sym);
    Symbol resolve(String name, int nargs, boolean selfcalccall);
    ResolvedCalc resolveCalc(ModelSimple model, String calcname, int nargs, boolean selfcalccall);
    ScopeNode getScopeNode();
    List<Symbol> getScopeDefines();
    Scope getParent();
    int getNewSymbolId();
    int getSymbolIdCounter();
}
