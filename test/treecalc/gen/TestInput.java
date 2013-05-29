package treecalc.gen;

import static org.junit.Assert.*;

import gen.testinput.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import treecalc.rt.S;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

@RunWith(Parameterized.class)
public class TestInput {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	public TestInput(Method method) {
		switch(method) {
		case METHOD_JAVA:
			_s = new _S();
			break;
		case METHOD_JCX:
			try {
				_s = new TciMachine(TciAsmReaderWriter.read("./src/test/java/gen/testinput/gen.testinput.tcx"));
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
	public void test0() {
		_s.setInput("I0", "111");
		_s.setInput("I0[1]", "222");
		assertEquals("(111!222!111!111)", F.F0(_s).stringValue());
		_s.setInput("I0[1]", "333");
		assertEquals("(111!333!111!111)", F.F0(_s).stringValue());
		_s.setInput("I0[0;1]", "0");
		assertEquals("(111!333!111!111)", F.F0(_s).stringValue());
		assertEquals("111", F.F0_1(_s).stringValue());
	}

	@Test
	public void test01() {
		_s.setInput("I1", "1");
		_s.setInput("I1[1]", "3");
		assertEquals("(2!2!6!2!6)", F.F1(_s).stringValue());
	}
//	INPUT I2 {
//		check = IF index=5 THEN self[4] * 2 ELSE 45 ENDIF ;
//	}
//	FUNC F2 = v_stringx(list(
//		I2[5],
//		I2[4],
//		I2
//	));
	@Test
	public void test02() {
		_s.setInput("I2[4]", "111");
		assertEquals("(222!45!45)", F.F2(_s).stringValue());
	}
//	INPUT I3 {
//		check = IF index>0 THEN index * self[index-1] ELSE 1 ENDIF ;
//	} 
//	FUNC F3 = v_stringx(list(
//		I3[5],
//		I3,
//		I3[-1]
//	));
	@Test
	public void test03() {
		_s.setInput("I3[4]", "40");
		assertEquals("(200!1!1)", F.F3(_s).stringValue());
	}
//	INPUT I4 {
//		check = self; // with index 4 -> will ask for input I4[4] */
//		info  = self; // with index 4 -> will ask just for input (not for input[4])! */
//	}
//	FUNC F4 = v_stringx(list(
//		I4[4],
//		I4,
//		I4[4].info,
//		I4.info
//	));
	@Test
	public void test04() {
		_s.setInput("I4[4]", "4");
		_s.setInput("I4[0,0,0,0]", "0");
		assertEquals("(4!0!0!0)", F.F4(_s).toString());
	}
	
//	INPUT I5 ;
//	FUNC F5 = v_stringx(list(
//		I5,
//		I5[0],
//		I5[0,0,0],
//		I5[0,0,0,1],
//		I5[1,0,0],
//		I5[1],
//		I5.unchecked,
//		I5[5].unchecked
//	));
	@Test
	public void test05() {
		_s.setInput("I5", "0");
		_s.setInput("I5[1,0]", "1");
		_s.setInput("i5[00, 0, 0, 1]", "0-0-0-1");
		_s.setInput("I5[5]", "5");
		assertEquals("(0!0!0!0-0-0-1!1!1!0!5)", F.F5(_s).toString());
	}	
	
	@Test
	public void test06() {
		assertEquals("I6;I6;I6;I6 should all be I6", F.F6(_s).stringValue());
	}
	@Test
	public void test07() {
		assertEquals("abc;I7 should be 'abc;I7'", F.F7(_s).stringValue());
	}
	@Test
	public void test08() {
		assertEquals("name=i8 hardcoded!;abc;i8 should be name=i8 hardcoded;abc;i8", F.F8(_s).stringValue());
	}

	@Test
	public void test09() {
		List<String[]> list = _s.getInputList("I9");
		String[][] expected = { { "1", "code 1"},  {"2", "code 2"}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test10() {
		List<String[]> list = _s.getInputList("I10");
		String[][] expected = { {"2", "code 2"}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test11() {
		List<String[]> list = _s.getInputList("I11");
		String[][] expected = { { "1", "text 1"},  {"2", "text 2"}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test12() {
		List<String[]> list = _s.getInputList("I12");
		String[][] expected = { { "1", "key=1,text=code 1"},  {"2", "key=2,text=code 2"}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test13() {
		List<String[]> list = _s.getInputList("I13");
		String[][] expected = { { "1", "key=1,text=text 1"},  {"2", "key=2,text=text 2"}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test14() {
		List<String[]> list = _s.getInputList("I14");
		String[][] expected = { { "1", "111"},  {"2", "222"	}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test15() {
		List<String[]> list = _s.getInputList("I15");
		String[][] expected = { { "1", "111"},  {"2", "222"	}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test16() {
		List<String[]> list = _s.getInputList("I16");
		String[][] expected = { { "1", "key 1"},  {"2", "key 2"	}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
	@Test
	public void test17() {
		List<String[]> list = _s.getInputList("I17");
		String[][] expected = { { "1", "key 1"},  {"2", "key 2"	}};
		int i=0;
		assertEquals(expected.length, list.size());
		for (String[] actualkeyvalue : list) {
			String expectedkeyvalueStr = Arrays.toString(expected[i]);
			String actualkeyvalueStr   = Arrays.toString(actualkeyvalue);
			assertEquals(expectedkeyvalueStr, actualkeyvalueStr);
			i++;
		}
	}
}
