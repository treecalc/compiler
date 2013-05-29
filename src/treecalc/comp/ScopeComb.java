package treecalc.comp; 

import java.util.ArrayList;
import java.util.List;

public class ScopeComb implements Scope {
	public final Scope defineScope;
	public final Scope lookupScope;
	public final Scope internalScope;
	public ScopeComb(Scope defineScope, Scope lookupScope) {
		this.defineScope = defineScope;
		this.lookupScope = lookupScope;
		this.internalScope = new ScopeSimple("define:" + defineScope.getScopeName()+",addlookup:" + lookupScope.getScopeName(), null);
	}

	@Override
	public String getFullScopeName() {
		return defineScope.getFullScopeName() + ",addlookup:" + lookupScope.getScopeName();
	}

	@Override
	public String getScopeName() {
		return internalScope.getScopeName();
	}

	@Override
	public void define(Symbol sym) {
		defineScope.define(sym);
	}

	@Override
	public Symbol resolve(String name, int nargs, boolean selfcalccall) {
		Symbol ret = internalScope.resolve(name, nargs, selfcalccall);
		if (ret==null) {
			ret = lookupScope.resolve(name, nargs, selfcalccall);
		}
		return ret;
	}
	@Override
	public ResolvedCalc resolveCalc(ModelSimple simple, String calcname, int nargs, boolean selfcalccall) {
		ResolvedCalc ret = internalScope.resolveCalc(simple, calcname, nargs, selfcalccall);
		if (ret==null) {
			ret = lookupScope.resolveCalc(simple, calcname, nargs, selfcalccall);
		}
		return ret;
	}
	@Override
	public ScopeNode getScopeNode() {
		return internalScope.getScopeNode();
	}

	@Override
	public List<Symbol> getScopeDefines() {
		return new ArrayList<Symbol>(0);
	}

	@Override
	public Scope getParent() {
		return null;
	}

	@Override
	public int getNewSymbolId() {
		return defineScope.getNewSymbolId();
	}
	@Override
	public int getSymbolIdCounter() {
		return defineScope.getSymbolIdCounter();
	}

}
