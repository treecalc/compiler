package treecalc.comp; 

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class TcAst extends CommonTree {
	public TcAst(Token t) { super(t); }
    private Scope scope; // recorded in parser, used in visitor
    public Scope getScope() {
    	return scope;
    }
    public void setScope(Scope scope) {
    	this.scope = scope;
    }

    @Override
	public TcAst getParent() {
		return (TcAst) super.getParent();
	}
	@Override
	public TcAst getChild(int i) {
		return (TcAst) super.getChild(i);
	}
	@Override
	public TcAst getAncestor(int type) {
		return (TcAst) super.getAncestor(type);
		
	}
	@Override
	public TcAst getFirstChildWithType(int type) {
		return (TcAst) super.getFirstChildWithType(type);
	}
}
