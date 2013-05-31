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
public class TestTab09 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab09(Method method) {
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
TABLE tab09 (key1, key2, 1, 2, 3) {
	1, 1, 111, 112, 113 ;
	1, 2, 121, 122, 123 ;
	2, 1, 211, 212, 213 ;
	2, 2, 221, 222, 223 ;
}
	=> non-direct access, first column not unique 
*/
	@Test
	public void test01() {
		assertEquals(TAB09.findExact(V.getInstance("1"), V.getInstance("1")).stringValue(), "111");
		assertEquals(TAB09.findExact(V.getInstance("1"), V.getInstance("2")).stringValue(), "121");
		assertEquals(TAB09.findExact(V.getInstance("2"), V.getInstance("1")).stringValue(), "211");
		assertEquals(TAB09.findExact(V.getInstance("2"), V.getInstance("2")).stringValue(), "221");
		assertEquals(TAB09.findExact(V.getInstance("1"), V.getInstance("1"), V.getInstance("111")).stringValue(), "112");
		assertEquals(TAB09.findExact(V.getInstance("1"), V.getInstance("2"), V.getInstance("121")).stringValue(), "122");
		assertEquals(TAB09.findExact(V.getInstance("2"), V.getInstance("1"), V.getInstance("211")).stringValue(), "212");
		assertEquals(TAB09.findExact(V.getInstance("2"), V.getInstance("2"), V.getInstance("221")).stringValue(), "222");
		assertEquals(TAB09.findExact(V.getInstance("2"), V.getInstance("1"), V.getInstance("211"), V.getInstance("212")).stringValue(), "213");
		try{assertNull(TAB09.findExact(V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExact(V.getInstance("1"), V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExact(V.getInstance("1"), V.getInstance("1"), V.getInstance("121")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExact(V.getInstance("1"), V.getInstance("1"), V.getInstance("111"), V.getInstance("222")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test02() {
		assertEquals(TAB09.findExactColumn(V.getInstance("1"), V.getInstance("1"), V.getInstance("1")).stringValue(), "111");
		assertEquals(TAB09.findExactColumn(V.getInstance("1"), V.getInstance("1"), V.getInstance("2")).stringValue(), "121");
		assertEquals(TAB09.findExactColumn(V.getInstance("1"), V.getInstance("2"), V.getInstance("1")).stringValue(), "211");
		assertEquals(TAB09.findExactColumn(V.getInstance("1"), V.getInstance("2"), V.getInstance("2")).stringValue(), "221");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("1"), V.getInstance("1"), V.getInstance("111")).stringValue(), "112");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("1"), V.getInstance("2"), V.getInstance("121")).stringValue(), "122");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("2"), V.getInstance("1"), V.getInstance("211")).stringValue(), "212");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("2"), V.getInstance("2"), V.getInstance("221")).stringValue(), "222");
		assertEquals(TAB09.findExactColumn(V.getInstance("3"), V.getInstance("2"), V.getInstance("1"), V.getInstance("211"), V.getInstance("212")).stringValue(), "213");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("1"), V.getInstance("1")).stringValue(), "112");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("1"), V.getInstance("2")).stringValue(), "122");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("2"), V.getInstance("1")).stringValue(), "212");
		assertEquals(TAB09.findExactColumn(V.getInstance("2"), V.getInstance("2"), V.getInstance("2")).stringValue(), "222");
		assertEquals(TAB09.findExactColumn(V.getInstance("3"), V.getInstance("1"), V.getInstance("1"), V.getInstance("111")).stringValue(), "113");
		assertEquals(TAB09.findExactColumn(V.getInstance("3"), V.getInstance("1"), V.getInstance("2"), V.getInstance("121")).stringValue(), "123");
		assertEquals(TAB09.findExactColumn(V.getInstance("3"), V.getInstance("2"), V.getInstance("1"), V.getInstance("211")).stringValue(), "213");
		assertEquals(TAB09.findExactColumn(V.getInstance("3"), V.getInstance("2"), V.getInstance("2"), V.getInstance("221")).stringValue(), "223");
		assertEquals(TAB09.findExactColumn(V.getInstance("1"), V.getInstance("2"), V.getInstance("1"), V.getInstance("211"), V.getInstance("212")).stringValue(), "211");
		try{assertNull(TAB09.findExactColumn(V.getInstance("key1"), V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExactColumn(V.getInstance("key1"), V.getInstance("1"), V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExactColumn(V.getInstance("key1"), V.getInstance("1"), V.getInstance("1"), V.getInstance("121")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB09.findExactColumn(V.getInstance("key1"), V.getInstance("1"), V.getInstance("1"), V.getInstance("111"), V.getInstance("222")));} catch(ExceptionCalculation e) {}
	}
}
