package treecalc.comp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gen.testtab.*;

import treecalc.rt.ExceptionCalculation;
import treecalc.rt.S;
import treecalc.rt.V;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

@RunWith(Parameterized.class)
public class TestTab11 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab11(Method method) {
		switch(method) {
		case METHOD_JAVA:
			_s = new _S();
			break;
		case METHOD_JCX:
			try {
				_s = new TciMachine(TciAsmReaderWriter.read("./test/gen/testtab/gen.testtab.tcx"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		default:
			throw new RuntimeException("method " + method + " not yet supported by the testcase.");
		}
		this.method = method;
	}
/*
TABLE tab11 (key, value) {
	"a", "a oder A";
	1, "1";
	"XXX", "[xX][xX][xX]";
	"01", "01";
}
	=> nun-numeric, shuffled, non-direct access 
*/
	@Test
	public void test01() {
		assertEquals(TAB11.findExact(V.getInstance("1")).stringValue(), "1");
		assertEquals(TAB11.findExact(V.getInstance("01")).stringValue(), "01");
		assertEquals(TAB11.findExact(V.getInstance("a")).stringValue(), "a oder A");
		assertEquals(TAB11.findExact(V.getInstance("A")).stringValue(), "a oder A");
		try{assertNull(TAB11.findExact(V.getInstance("a ")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB11.findExact(V.getInstance(" a")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB11.findExact(V.getInstance("XXX")).stringValue(), "[xX][xX][xX]");
		assertEquals(TAB11.findExact(V.getInstance("xXx")).stringValue(), "[xX][xX][xX]");
		try{assertNull(TAB11.findExact(V.getInstance("XXX ")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB11.findExact(V.getInstance(" XXX")));} catch(ExceptionCalculation e) {}
	}
}
