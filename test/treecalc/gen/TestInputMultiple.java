package treecalc.gen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import treecalc.rt.S;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;


import gen.testinputmultiple._S;

@RunWith(Parameterized.class)
public class TestInputMultiple {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	public TestInputMultiple(Method method) {
		switch(method) {
		case METHOD_JAVA:
			_s = new _S();
			break;
		case METHOD_JCX:
			try {
				_s = new TciMachine(TciAsmReaderWriter.read("./src/test/java/gen/testinputmultiple/gen.testinputmultiple.tcx"));
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
	TABLE tab01 (key, value) {
		1, a;
		2, b;
		3, c;
	}
	=> direct access
*/
	private S _s;
	
	@Before 
	public void initialize() {
	}

	@After
	public void cleanup() {
		_s.reset();
	}
	
	@Test
	public void test1() {
		_s.setInput("I_Persons", "0");
		assertEquals("0", _s.calculateMaintree("P_Test"));
		assertEquals("0", _s.calculateMaintree("P_TestIndex"));
		assertEquals("0", _s.calculateMaintree("P_TestIndexBin"));
	}

	@Test
	public void test2() {
		_s.setInput("I_Persons", "1");
		assertEquals("1", _s.calculateMaintree("P_Test"));
		assertEquals("0", _s.calculateMaintree("P_TestIndex"));
		assertEquals("1", _s.calculateMaintree("P_TestIndexBin"));
	}

	@Test
	public void test3() {
		_s.setInput("I_Persons", "2");
		assertEquals("2", _s.calculateMaintree("P_Test"));
		assertEquals("1", _s.calculateMaintree("P_TestIndex"));
		assertEquals("3", _s.calculateMaintree("P_TestIndexBin"));
	}

	@Test
	public void test4() {
		_s.setInput("I_Persons", "1");
		_s.setInput("I_Age[0]", "30");
		_s.setInput("I_AgeAuto[0]", "30");
		assertEquals("30", _s.calculateMaintree("P_AgeManual"));
		assertEquals("30", _s.calculateMaintree("P_AgeAuto"));
	}

	@Test
	public void test5() {
		_s.setInput("I_Persons", "2");
		_s.setInput("I_Age[0]", "30");
		_s.setInput("I_Age[1]", "38");
		_s.setInput("I_AgeAuto[0]", "30");
		_s.setInput("I_AgeAuto[1]", "38");
		assertEquals("68", _s.calculateMaintree("P_AgeManual"));
		assertEquals("68", _s.calculateMaintree("P_AgeAuto"));
		assertEquals("68", _s.calculateMaintree("P_Func"));
		assertEquals("60", _s.calculateMaintree("P_AgeFirst"));
		assertEquals("0 - (1)", _s.calculate("P_TestInputx"));
		assertEquals("0 - (1)", _s.calculate("P_TestInputCalcX"));
		assertEquals("0 - (1)", _s.calculate("P_TestInputCalcX2"));
		assertEquals("(2) - (2)", _s.calculate("P_TestInputCalcX3"));
		assertEquals("0", _s.calculate("I_TestInput"));
		assertEquals("0", _s.calculate("I_TestInput[0;0]"));
		assertEquals("(1)", _s.calculate("I_TestInput[1]"));
		assertEquals("(1)", _s.calculate("I_TestInput[1;0]"));
		assertEquals("(1!0!2)", _s.calculate("I_TestInput[1;0;2]"));
		assertEquals("0", _s.calculate("I_TestInput.value"));
		assertEquals("0", _s.calculate("I_TestInput[0;0].value"));
		assertEquals("(1)", _s.calculate("I_TestInput[1].value"));
		assertEquals("(1)", _s.calculate("I_TestInput[1;0].value"));
		assertEquals("(1!0!2)", _s.calculate("I_TestInput[1;0;2].value"));
	}
}
