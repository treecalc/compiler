package treecalc.comp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import treecalc.rt.S;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;


import gen.tclife._S;

@RunWith(Parameterized.class)
public class TestTcLife {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	public TestTcLife(Method method) {
		switch(method) {
		case METHOD_JAVA:
			_s = new gen.tclife._S();
			break;
		case METHOD_JCX:
			try {
				_s = new TciMachine(TciAsmReaderWriter.read("./test/gen/tclife/gen.tclife.tcx"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			break;
		default:
			throw new RuntimeException("method " + method + " not yet supported by the testcase.");
		}
		this.method = method;
	}
	
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
		_s.setInput("I_DateOfBirth", "12.5.1994");
		_s.setInput("I_ContractBeginDate", "1.10.2013");
		_s.setInput("I_ContractEndDate", "1.10.2033");
		_s.setInput("I_SumInsured", "10000");
		_s.setInput("I_PaymentFrequency", "1");
		_s.setInput("I_AccidentalDeath_YN", "0");
		assertEquals(427.45, Double.parseDouble(_s.calculate("R_PremYear")), 0.001);
		assertEquals(427.45, Double.parseDouble(_s.calculate("R_PremFreq")), 0.001);
		assertEquals(16.44,  Double.parseDouble(_s.calculate("R_Tax")), 0.001);
		assertEquals(411.01, Double.parseDouble(_s.calculate("R_PremMain")), 0.001);
		assertEquals("0", _s.calculate("R_PremAccDeath"));
		assertEquals("19", _s.calculate("R_Age"));
		assertEquals("20", _s.calculate("R_DurationYears"));
		double[] expectedSurrenderValues = new double[] {
				0, 306.28, 670.83, 1048.97, 1442.85, 1851.98, 2277.25, 2718.06, 3175.49, 3649.96, 4142.58, 4634.55, 5141.37, 5663.58, 6201.86, 6756.18, 7327.71, 7917.05, 8525.14, 9152.45, 10000
		};
		long idSurrenderValue = _s.getCalculateId("R_SurrenderValue(0)");
		for (int indYear=0; indYear<expectedSurrenderValues.length; indYear++) {
			String val = _s.calculate(idSurrenderValue, indYear);
			assertEquals(expectedSurrenderValues[indYear], Double.parseDouble(val), 0.001);
		}
		
	}
	@Test
	public void test2() {
		double dSum=0;
		Random rand = new Random(42);
		for (int indexRun=0; indexRun<1000; indexRun++) {
			int age = 20 + rand.nextInt(40);
			int n = 10 + rand.nextInt(30);
			int paymentFrequency = rand.nextBoolean() ? 1 : 12;
			int yearContractBegin = 2013;
			int yearOfBirth = yearContractBegin - age;
			int yearContractEnd = yearContractBegin + n;
			_s.setInput("I_DateOfBirth", "12.5." + yearOfBirth);
			_s.setInput("I_ContractBeginDate", "1.10." + yearContractBegin);
			_s.setInput("I_ContractEndDate", "1.10." + yearContractEnd);
			_s.setInput("I_SumInsured", "10000");
			_s.setInput("I_PaymentFrequency", Integer.toString(paymentFrequency));
			_s.setInput("I_AccidentalDeath_YN", "0");
			dSum += Double.parseDouble(_s.calculate("R_PremYear"));
			dSum += Double.parseDouble(_s.calculate("R_PremFreq"));
			dSum += Double.parseDouble(_s.calculate("R_Tax"));
			dSum += Double.parseDouble(_s.calculate("R_PremMain"));
			dSum += Double.parseDouble(_s.calculate("R_PremAccDeath"));
			dSum += Double.parseDouble(_s.calculate("R_Age"));
			String sDuration = _s.calculate("R_DurationYears");
			int nOut = Integer.parseInt(sDuration);
			long idSurrenderValue = _s.getCalculateId("R_SurrenderValue(0)");
			for (int indYear=0; indYear<=nOut; indYear++) {
				String val = _s.calculate(idSurrenderValue, indYear);
				dSum += Double.parseDouble(val);
			}
//			System.out.println("lv comp " + indexRun);
		}
		System.out.println(dSum);
	}
}
