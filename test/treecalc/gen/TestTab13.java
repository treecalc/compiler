package treecalc.gen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gen.testtab.TAB13;
import gen.testtab._S;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import treecalc.rt.ExceptionCalculation;
import treecalc.rt.S;
import treecalc.rt.V;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

@RunWith(Parameterized.class)
public class TestTab13 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab13(Method method) {
		switch(method) {
		case METHOD_JAVA:
			_s = new _S();
			break;
		case METHOD_JCX:
			try {
				_s = new TciMachine(TciAsmReaderWriter.read("./src/test/java/gen/testtab/gen.testtab.tcx"));
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
TABLE tab13(key, value) {
	1, 10;
	2, 20;
	3, 30;
}
	=> numeric, direct access
	=> suitable for interpol 
*/
	@Test
	public void test01() {
		assertEquals(TAB13.interpol(V.getInstance("1")).stringValue(), "10");
		assertEquals(TAB13.interpol(V.getInstance("2")).stringValue(), "20");
		assertEquals(TAB13.interpol(V.getInstance("1.7")).stringValue(), "17");
		try{assertNull(TAB13.interpol(V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB13.interpol(V.getInstance("3.001")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB13.interpol(V.getInstance("4")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB13.interpol(V.getInstance("0.9999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB13.interpol(V.getInstance("0")));} catch(ExceptionCalculation e) {}
	}
}
