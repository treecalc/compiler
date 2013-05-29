package treecalc.gen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import gen.testtab.*;

import treecalc.rt.S;
import treecalc.rt.ExceptionCalculation;
import treecalc.rt.V;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

@RunWith(Parameterized.class)
public class TestTab12 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab12(Method method) {
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
TABLE tab12 (gruppe, ccmvon, "simple beschreibung", prem) {
	a,	   0, "0-49 ccm"     , 0   ;
	a,	  50, "50-249 ccm"   , 37  ;
	a,	 250, "250 - 999 ccm", 89  ;
	a,	1000, ">=1000 ccm"   , 343 ;
	b,	   0, "0-99 ccm"     , 1   ;
	b,	 100, "50-249 ccm"   , 38  ;
	b,	 500, "500 - 749 ccm", 90  ;
	b,	 750, ">=750 ccm"    , 344 ;
}
	=> nun-numeric, non-direct access 
*/
	@Test
	public void test01() {
		assertEquals(TAB12.findExact(V.getInstance("a"), V.getInstance("0")).stringValue(), "0-49 ccm");
		assertEquals(TAB12.findExact(V.getInstance("a"), V.getInstance("50")).stringValue(), "50-249 ccm");
		assertEquals(TAB12.findExact(V.getInstance("a"), V.getInstance("250")).stringValue(), "250 - 999 ccm");
		assertEquals(TAB12.findExact(V.getInstance("a"), V.getInstance("1000")).stringValue(), ">=1000 ccm");
		try{assertNull(TAB12.findExact(V.getInstance("a"), V.getInstance("-1")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExact(V.getInstance("a"), V.getInstance(0.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExact(V.getInstance("a"), V.getInstance("00")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExact(V.getInstance("a"), V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExact(V.getInstance("a"), V.getInstance("abc")));} catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test02() {
		assertEquals(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("0")).stringValue(), "0");
		assertEquals(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("50")).stringValue(), "37");
		assertEquals(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("250")).stringValue(), "89");
		assertEquals(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("1000")).stringValue(), "343");
		try{assertNull(TAB12.findExactColumn(V.getInstance("VALuexxx"),V.getInstance("a"), V.getInstance("0")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("00")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findExactColumn(V.getInstance("prem"),V.getInstance("a"), V.getInstance("1")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("0.0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("50")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("50.0")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("250")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("250.000")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("1000")).toString(), ">=1000 ccm");
		assertEquals(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalUp(V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test04() {
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("0.0001")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("50")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("50.0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("250")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("250.000")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("1000")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalDown(V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test05() {
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("0.0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50.0")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250.000")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("1000")).toString(), ">=1000 ccm");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalUpColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test06() {
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("0.0001")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("0.1")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("49.999")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50.0")).toString(), "0-49 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("50.0001")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("249")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("249.999")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250.000")).toString(), "50-249 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("250.1")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("999")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("999.99")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("1000")).toString(), "250 - 999 ccm");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("100000")).toString(), ">=1000 ccm");
		try{assertNull(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalDownColumn(V.getInstance("SIMPLE beschreibung"), V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test07() {
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("0")).toString(), "0");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("0.0")).toString(), "0");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("0.1")).toString(), "0");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("49.999")).toString(), "0");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("50")).toString(), "37");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("50.0")).toString(), "37");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("50.0001")).toString(), "37");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("249")).toString(), "37");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("249.999")).toString(), "37");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("250")).toString(), "89");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("250.000")).toString(), "89");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("prem"), V.getInstance("a"), V.getInstance("250.1")).toString(), "89");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("999")).toString(), "89");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("999.99")).toString(), "89");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("1000")).toString(), "343");
		assertEquals(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("100000")).toString(), "343");
		try{assertNull(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalUpColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test08() {
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("0.0001")).toString(), "0");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("0.1")).toString(), "0");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("49.999")).toString(), "0");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("50")).toString(), "0");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("50.0")).toString(), "0");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("50.0001")).toString(), "37");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("249")).toString(), "37");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("249.999")).toString(), "37");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("250")).toString(), "37");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("250.000")).toString(), "37");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("250.1")).toString(), "89");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("999")).toString(), "89");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("999.99")).toString(), "89");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("1000")).toString(), "89");
		assertEquals(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("100000")).toString(), "343");
		try{assertNull(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("-5")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB12.findIntervalDownColumn(V.getInstance("PrEM"), V.getInstance("a"), V.getInstance("-0.0001")));} catch(ExceptionCalculation e) {}
	}
}
