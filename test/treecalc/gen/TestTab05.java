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
public class TestTab05 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab05(Method method) {
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
TABLE tab05 (ccmvon, "simple beschreibung", prem) {
	   0, "0-49 ccm"     , 0   ;
	  50, "50-249 ccm"   , 37  ;
	 250, "250 - 999 ccm", 89  ;
	1000, ">=1000 ccm"   , 343 ;
}
	=> non-direct access, not shuffled, string-columnname, string value 
*/

	@Test
	public void test01() {
		assertEquals(TAB05.findExact(V.getInstance("0")).stringValue(), "0-49 ccm");
		assertEquals(TAB05.findExact(V.getInstance("50")).stringValue(), "50-249 ccm");
		assertEquals(TAB05.findExact(V.getInstance("250")).stringValue(), "250 - 999 ccm");
		assertEquals(TAB05.findExact(V.getInstance("1000")).stringValue(), ">=1000 ccm");
		try{assertNull(TAB05.findExact(V.getInstance("-1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExact(V.getInstance(0.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExact(V.getInstance("00")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExact(V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExact(V.getInstance("abc")));} catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test02() {
		assertEquals(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("0")).stringValue(), "0");
		assertEquals(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("50")).stringValue(), "37");
		assertEquals(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("250")).stringValue(), "89");
		assertEquals(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("1000")).stringValue(), "343");
		try{assertNull(TAB05.findExactColumn(V.getInstance("VALuexxx"),V.getInstance("0")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("00")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findExactColumn(V.getInstance("prem"),V.getInstance("1")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB05.findIntervalUp(V.getInstance("0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("0.0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("50")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("50.0")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("250")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("250.000")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("1000")).toString(), ">=1000 ccm");
		assertEquals(TAB05.findIntervalUp(V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB05.findIntervalUp(V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalUp(V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test04() {
		assertEquals(TAB05.findIntervalDown(V.getInstance("0.0001")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("50")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("50.0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("250")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("250.000")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("1000")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDown(V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB05.findIntervalDown(V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalDown(V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test05() {
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("0.0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50.0")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250.000")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("1000")).toString(), ">=1000 ccm");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test06() {
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("0.0001")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50.0")).toString(), "0-49 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250.000")).toString(), "50-249 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("1000")).toString(), "250 - 999 ccm");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test07() {
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("0")).toString(), "0");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("0.0")).toString(), "0");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("0.1")).toString(), "0");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("49.999")).toString(), "0");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("50")).toString(), "37");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("50.0")).toString(), "37");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("50.0001")).toString(), "37");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("249")).toString(), "37");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("249.999")).toString(), "37");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("250")).toString(), "89");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("250.000")).toString(), "89");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("250.1")).toString(), "89");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("999")).toString(), "89");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("999.99")).toString(), "89");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("1000")).toString(), "343");
		assertEquals(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("100000")).toString(), "343");
		try{assertNull(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test08() {
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("0.0001")).toString(), "0");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("0.1")).toString(), "0");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("49.999")).toString(), "0");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("50")).toString(), "0");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("50.0")).toString(), "0");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("50.0001")).toString(), "37");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("249")).toString(), "37");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("249.999")).toString(), "37");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("250")).toString(), "37");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("250.000")).toString(), "37");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("250.1")).toString(), "89");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("999")).toString(), "89");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("999.99")).toString(), "89");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("1000")).toString(), "89");
		assertEquals(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("100000")).toString(), "343");
		try{assertNull(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB05.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
}
