package treecalc.comp;

import java.util.Arrays;
import java.util.Collection;

public enum Method {
	METHOD_JAVA,
	METHOD_JAVASCRIPT,
	METHOD_JCX;
	
	public static Collection<Object[]> getMethodsNoJavaScript() {
		return Arrays.asList(new Object[][] {
				  { Method.METHOD_JAVA }
				, { Method.METHOD_JCX  }
		});
	}
}
