package treecalc.comp; 

import java.util.List;

public class ScopedSymbol implements Scope, Symbol {
	private TcAst ast; /* optional referenct to Ast */
	private final Scope scope;
	private final Symbol symbol;
	/* used so far for
	 * - SYMBOLTYPE_INPUT ........... defines are for SYMBOLTYPE_INPUTRESULTDEF
	 * - SYMBOLTYPE_FUNCTION ........ defines are for parameters etc.
	 * - SYMBOLTYPE_INPUTRESULTDEF .. defines are for parameters etc.
	 * - SYMBOLTYPE_NODERESULTDEF ... defines are for parameters etc.
	 */
	ScopedSymbol(String scopename, int symboltype, String symbolname, int nargs, Scope currentscope) {
		symbol = new SymbolImpl(symbolname, symboltype, nargs);
		scope = new ScopeSimple(scopename, currentscope, true);
	}

	public void setAst(TcAst ast) {
		this.ast = ast;
	}
	public TcAst getAst() {
		return ast;
	}
	
	@Override /* Symbol */ 
	public String getSymbolName() {
		return symbol.getSymbolName();
	}
	@Override /* Symbol */
	public void setScope(Scope scope) {
		symbol.setScope(scope);
	}

	@Override /* Symbol */
	public Scope getScope() {
		return symbol.getScope();
	}
	@Override /* Symbol */
	public int getSymboltype() {
		return symbol.getSymboltype();
	}
	@Override
	public int getNumArgs() {
		return symbol.getNumArgs();
	}


	@Override /* Scope*/ 
	public String getScopeName() {
		return scope.getScopeName();
	}
	@Override /* Scope */
	public Symbol resolve(String name, int nargs, boolean selfcalccall) {
		return scope.resolve(name, nargs, selfcalccall);
	}

	@Override /* Scope */
	public ResolvedCalc resolveCalc(ModelSimple model, String calcname, int nargs, boolean selfcalccall) {
		return scope.resolveCalc(model, calcname, nargs, selfcalccall);
	}
	@Override
	public ScopeNode getScopeNode() {
		return scope.getScopeNode();
	}

	@Override /* Scope */
	public void define(Symbol sym) {
		scope.define(sym);
	}

	@Override /* Scope */
	public Scope getParent() {
		return scope.getParent();
	}

	@Override /* Scope */
	public List<Symbol> getScopeDefines() {
		return scope.getScopeDefines();
	}

	@Override /* Scope */
	public String getFullScopeName() {
		return scope.getFullScopeName();
	}
	
	@Override
	public int getNewSymbolId() {
		return scope.getNewSymbolId();
	}
	@Override
	public int getSymbolIdCounter() {
		return scope.getSymbolIdCounter();
	}

	@Override
	public void setId(int id) {
		symbol.setId(id);
	}

	@Override
	public int getId() {
		return symbol.getId();
	}
}
