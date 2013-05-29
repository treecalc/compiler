package treecalc.gen;

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
public class TestTab08 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab08(Method method) {
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
TABLE tab08 (key1, key2, value) {
	1, 1, 111 ;
	2, 2, 444 ;
	2, 1, 333 ;
	1, 2, 222 ;
}
    same as tab07, but ordering messed up
	=> non-direct access, shuffled, first column not unique 
*/

	@Test
	public void test01() {
		assertEquals(TAB08.findExact(V.getInstance("1"), V.getInstance("1")).stringValue(), "111");
		assertEquals(TAB08.findExact(V.getInstance("1"), V.getInstance("2")).stringValue(), "222");
		assertEquals(TAB08.findExact(V.getInstance("2"), V.getInstance("1")).stringValue(), "333");
		assertEquals(TAB08.findExact(V.getInstance("2"), V.getInstance("2")).stringValue(), "444");
		try{assertNull(TAB08.findExact(V.getInstance("-1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance(0.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance("1"), V.getInstance("01")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance("1"), V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance("3"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExact(V.getInstance("abc")));} catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test02() {
		assertEquals(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1")).stringValue(), "111");
		assertEquals(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("2")).stringValue(), "222");
		assertEquals(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1")).stringValue(), "333");
		assertEquals(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("2")).stringValue(), "444");
		try{assertNull(TAB08.findExactColumn(V.getInstance("xxx"), V.getInstance("1"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("-1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance(0.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("01")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("3"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findExactColumn(V.getInstance("value"), V.getInstance("abc")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB08.findIntervalUp(V.getInstance("1"), V.getInstance("1")).toString(), "111");
		assertEquals(TAB08.findIntervalUp(V.getInstance("1"), V.getInstance("1.999")).toString(), "111");
		assertEquals(TAB08.findIntervalUp(V.getInstance("1"), V.getInstance("2")).toString(), "222");
		assertEquals(TAB08.findIntervalUp(V.getInstance("1"), V.getInstance("1000")).toString(), "222");
		assertEquals(TAB08.findIntervalUp(V.getInstance("2"), V.getInstance("1")).toString(), "333");
		assertEquals(TAB08.findIntervalUp(V.getInstance("2"), V.getInstance("1.999")).toString(), "333");
		assertEquals(TAB08.findIntervalUp(V.getInstance("2"), V.getInstance("2")).toString(), "444");
		assertEquals(TAB08.findIntervalUp(V.getInstance("2"), V.getInstance("1000")).toString(), "444");
		try{assertNull(TAB08.findIntervalUp(V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUp(V.getInstance("0.9999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUp(V.getInstance("1"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUp(V.getInstance("2"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test04() {
		try{assertNull(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("1.00001")).toString(), "111");
		assertEquals(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("1.999")).toString(), "111");
		assertEquals(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("2")).toString(), "111");
		assertEquals(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("2.000001")).toString(), "222");
		assertEquals(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("1000")).toString(), "222");
		try{assertNull(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("1.0000001")).toString(), "333");
		assertEquals(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("1.999")).toString(), "333");
		assertEquals(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("2")).toString(), "333");
		assertEquals(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("2.00001")).toString(), "444");
		assertEquals(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("1000")).toString(), "444");
		try{assertNull(TAB08.findIntervalDown(V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDown(V.getInstance("0.9999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDown(V.getInstance("1"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDown(V.getInstance("2"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDown(V.getInstance("3"), V.getInstance("2")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test05() {
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1")).toString(), "111");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("VaLue"), V.getInstance("1"), V.getInstance("1.999")).toString(), "111");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("2")).toString(), "222");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1000")).toString(), "222");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1")).toString(), "333");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1.999")).toString(), "333");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("2")).toString(), "444");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1000")).toString(), "444");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("key1"), V.getInstance("1"), V.getInstance("3")).toString(), "1");
		assertEquals(TAB08.findIntervalUpColumn(V.getInstance("KEY2"), V.getInstance("1"), V.getInstance("3")).toString(), "2");
		try{assertNull(TAB08.findIntervalUpColumn(V.getInstance("xxx"), V.getInstance("2"), V.getInstance("1000")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("0.9999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalUpColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test06() {
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1.00001")).toString(), "111");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1.999")).toString(), "111");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("2")).toString(), "111");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("2.000001")).toString(), "222");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("1000")).toString(), "222");
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1.0000001")).toString(), "333");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("1.999")).toString(), "333");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("2")).toString(), "333");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("2.00001")).toString(), "444");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("VALUE"), V.getInstance("2"), V.getInstance("1000")).toString(), "444");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("key1"), V.getInstance("1"), V.getInstance("1000")).toString(), "1");
		assertEquals(TAB08.findIntervalDownColumn(V.getInstance("key2"), V.getInstance("1"), V.getInstance("1000")).toString(), "2");
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("xxx"), V.getInstance("1"), V.getInstance("5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("0.9999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("1"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("2"), V.getInstance("0.999")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB08.findIntervalDownColumn(V.getInstance("value"), V.getInstance("3"), V.getInstance("2")));} catch(ExceptionCalculation e) {}
	}
}
