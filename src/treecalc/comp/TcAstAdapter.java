package treecalc.comp; 

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;

public class TcAstAdapter extends CommonTreeAdaptor {
	public Object create(Token token) {
		return new TcAst(token);
	}
	public Object dupNode(Object t) {
		if ( t==null ) {
			return null;
		}
		return create(((TcAst)t).token);
	}
	public Object errorNode(TokenStream input, Token start, Token stop,
			RecognitionException e)	{
		return new TcAstErrorNode(input, start, stop, e);
	}
}
