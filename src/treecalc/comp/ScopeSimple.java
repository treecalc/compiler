package treecalc.comp; 

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScopeSimple implements Scope {
	private final String name;
	private final Scope parent; // null if global (outermost) scope
	private final boolean isFormulaTopScope;  
	private int maxnargs=0;
	private int symbolcounter=0; /* number local vars. etc starting from 0 */

	
	private final Map<String, Symbol> symbols = new LinkedHashMap<String, Symbol>();

    public ScopeSimple(String name, Scope parent) {
    	this(name, parent, false);
    }
    public ScopeSimple(String name, Scope parent, boolean isFormulaTopScope) {
    	this.name = name;
    	this.parent = parent;
    	this.isFormulaTopScope = isFormulaTopScope;
    }
	
	public String getScopeName() {
		return name;
	}

	public void define(Symbol sym) {
		int nargs = sym.getNumArgs();
		if (nargs> maxnargs) {
			maxnargs = nargs;
		}
		symbols.put(sym.getSymbolName() + ':' + sym.getNumArgs(), sym);
		sym.setScope(this); //keep reference to the scope in the symbol
		sym.setId(getNewSymbolId());
	}

	@Override
	public List<Symbol> getScopeDefines() {
		return new ArrayList<Symbol>(symbols.values());
	}

	@Override
    public Symbol resolve(String name, int nargs, boolean selfcalccall) {
		if (nargs>=0) {
			Symbol s = symbols.get((name!=null ? name.toUpperCase() : null) + ':' + nargs);
			if (s!=null) {
				return s;
			}
		} else {
			for (int i=0; i<=maxnargs; i++) {
				Symbol s = symbols.get((name!=null ? name.toUpperCase() : null) + ':' + i);
				if (s!=null) {
					return s;
				}
			}
		}
        Scope parent = getParent();
        return parent != null ? parent.resolve(name, nargs, selfcalccall) : null;
	}

    @Override
	public ResolvedCalc resolveCalc(ModelSimple model, String calcname, int nargs, boolean selfcalccall) {
    	/* don't look for the symbol, because it has to be in ScopeNode -> use parent */
        return parent != null ? parent.resolveCalc(model, calcname, nargs, selfcalccall) : null;
	}

    @Override
	public ScopeNode getScopeNode() {
		return parent != null ? parent.getScopeNode() : null;
	}

    public Scope getParent() { 
    	return parent; 
    }

	@Override
	public String toString() { 
		return symbols.entrySet().toString(); 
	}

	@Override
	public String getFullScopeName() {
		ArrayList<String> scopenames = new ArrayList<String>();
		for (Scope current = this; current!=null; current = current.getParent()) {
			scopenames.add(0, current.getScopeName());
		}
		return scopenames.toString();
	}

	@Override
	public int getNewSymbolId() {
		if (isFormulaTopScope) {
			return symbolcounter++;
		} else {
			return parent!=null ? parent.getNewSymbolId() : -1;
		}
	}
	@Override
	public int getSymbolIdCounter() {
		if (isFormulaTopScope) {
			return symbolcounter;
		} else {
			return parent!=null ? parent.getSymbolIdCounter() : -1;
		}
	}

}
