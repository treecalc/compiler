package treecalc.comp; 

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class TcAstErrorNode extends TcAst {
	public TcAstErrorNode(TokenStream input, Token start, Token stop,
			RecognitionException e) {
		super(start); // no need to record anything for this example
	}
}
