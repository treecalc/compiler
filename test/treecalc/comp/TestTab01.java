package treecalc.comp;

import static org.junit.Assert.*;

import gen.testtab.*;

import treecalc.rt.ExceptionCalculation;
import treecalc.rt.S;
import treecalc.rt.V;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestTab01 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab01(Method method) {
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
	TABLE tab01 (key, value) {
		1, a;
		2, b;
		3, c;
	}
	=> direct access
*/

	@Test
	public void test01() {
		assertEquals(TAB01.findExact(V.getInstance("1")).stringValue(), "a");
		assertEquals(TAB01.findExact(V.getInstance("2")).stringValue(), "b");
		assertEquals(TAB01.findExact(V.getInstance("3")).stringValue(), "c");
		assertEquals(TAB01.findExact(V.getInstance(1)).stringValue(), "a");  
		
		try { assertNull(TAB01.findExact(V.getInstance("0"))); } catch(ExceptionCalculation e) {}
		try { assertNull(TAB01.findExact(V.getInstance("4")));  } catch(ExceptionCalculation e) {}
		try { assertNull(TAB01.findExact(V.getInstance(1.1))); } catch(ExceptionCalculation e) {}
		try { assertNull(TAB01.findExact(V.getInstance("01"))); } catch(ExceptionCalculation e) {}
		try { assertNull(TAB01.findExact(V.getInstance(""))); } catch(ExceptionCalculation e) {}
		try { assertNull(TAB01.findExact(V.getInstance("abc"))); } catch(ExceptionCalculation e) {}
		
		assertEquals("a", _s.calculate("F01_01(1)"));
		assertEquals("b", _s.calculate("F01_01(2)"));
		assertEquals("c", _s.calculate("F01_01(3)"));
		assertEquals("a", _s.calculate("F01_01(\"1\")"));
	}
	
	@Test
	public void test02() {
		assertEquals(TAB01.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("a")
		).stringValue(), "1");
		assertEquals("1", _s.calculate("F01_02_01(1;\"a\")"));

		assertEquals(TAB01.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "1");
		assertEquals("1", _s.calculate("F01_02_01(1;\"A\")"));

		assertEquals(TAB01.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		assertEquals("a", _s.calculate("F01_02_02(1;\"A\")"));

		assertEquals(TAB01.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		assertEquals("a", _s.calculate("F01_02_03(1;\"A\")"));

		try { assertNull(TAB01.findExactColumn(
				V.getInstance("VALuexxx"), 
				V.getInstance("1"), 
				V.getInstance("A")
		)); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("3"), 
				V.getInstance("A")
		)); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("c")
		)); } catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB01.findIntervalUp(V.getInstance("1")).toString(), "a");
		assertEquals(TAB01.findIntervalUp(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB01.findIntervalUp(V.getInstance(2)).toString(), "b");
		assertEquals(TAB01.findIntervalUp(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB01.findIntervalUp(V.getInstance(3)).toString(), "c");
		assertEquals(TAB01.findIntervalUp(V.getInstance("03.000")).toString(), "c");
		assertEquals(TAB01.findIntervalUp(V.getInstance("10000.000")).toString(), "c");
		
		try {assertNull(TAB01.findIntervalUp(V.getInstance(0))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.findIntervalUp(V.getInstance(0.999))); } catch(ExceptionCalculation e) {}

		assertEquals("a", _s.calculate("F01_03(1)"));
		assertEquals("a", _s.calculate("F01_03(1.2)"));
		assertEquals("b", _s.calculate("F01_03(2)"));
		assertEquals("b", _s.calculate("F01_03(2.999)"));
		assertEquals("c", _s.calculate("F01_03(3)"));
		assertEquals("c", _s.calculate("F01_03(03.000)"));
		assertEquals("c", _s.calculate("F01_03(10000.000)"));
	}
	@Test
	public void test04() {
		try{assertNull(TAB01.findIntervalDown(V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB01.findIntervalDown(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB01.findIntervalDown(V.getInstance(2)).toString(), "a");
		assertEquals(TAB01.findIntervalDown(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB01.findIntervalDown(V.getInstance(3)).toString(), "b");
		assertEquals(TAB01.findIntervalDown(V.getInstance("03.000")).toString(), "b");
		assertEquals(TAB01.findIntervalDown(V.getInstance(3.0001)).toString(), "c");
		assertEquals(TAB01.findIntervalDown(V.getInstance("10000.000")).toString(), "c");
		
		try {assertNull(TAB01.findIntervalUp(V.getInstance(0))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.findIntervalUp(V.getInstance(0.999))); } catch(ExceptionCalculation e) {}

		assertEquals("a", _s.calculate("F01_04(1.2)"));
		assertEquals("a", _s.calculate("F01_04(2)"));
		assertEquals("b", _s.calculate("F01_04(2.999)"));
		assertEquals("b", _s.calculate("F01_04(3)"));
		assertEquals("b", _s.calculate("F01_04(03.000)"));
		assertEquals("c", _s.calculate("F01_04(3.0001)"));
		assertEquals("c", _s.calculate("F01_04(10000.000)"));
	}
	@Test
	public void test05() {
		assertEquals(TAB01.getCell(V.getInstance(1), V.getInstance(1)).stringValue(),"1");
		assertEquals(TAB01.getCell(V.getInstance(1), V.getInstance(2)).stringValue(),"a");
		assertEquals(TAB01.getCell(V.getInstance(2), V.getInstance(1)).stringValue(),"2");
		assertEquals(TAB01.getCell(V.getInstance(2), V.getInstance(2)).stringValue(),"b");
		assertEquals(TAB01.getCell(V.getInstance(3), V.getInstance(1)).stringValue(),"3");
		assertEquals(TAB01.getCell(V.getInstance(3), V.getInstance(2)).stringValue(),"c");
		
		try {assertNull(TAB01.getCell(V.getInstance(4), V.getInstance(0))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(4), V.getInstance(1))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(4), V.getInstance(2))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(4), V.getInstance(3))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(0), V.getInstance(0))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(0), V.getInstance(1))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(0), V.getInstance(2))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(0), V.getInstance(3))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(-100), V.getInstance(1))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(1), V.getInstance(-100))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(1), V.getInstance(0))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(1), V.getInstance(3))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCell(V.getInstance(1), V.getInstance(100))); } catch(ExceptionCalculation e) {}
		//assertNull(TAB01.getCell(V.getInstance("a"), V.getInstance(1)));
		//assertNull(TAB01.getCell(V.getInstance(1), V.getInstance("a")));
		
		assertEquals("1", _s.calculate("F01_05(1, 1)"));
		assertEquals("a", _s.calculate("F01_05(1, 2)"));
		assertEquals("2", _s.calculate("F01_05(2, 1)"));
		assertEquals("b", _s.calculate("F01_05(2, 2)"));
		assertEquals("3", _s.calculate("F01_05(3, 1)"));
		assertEquals("c", _s.calculate("F01_05(3, 2)"));
	}
	@Test
	public void test06() {
		assertEquals(TAB01.getCellByName(V.getInstance(1), V.getInstance("key")).stringValue(),"1");
		assertEquals(TAB01.getCellByName(V.getInstance(1), V.getInstance("value")).stringValue(),"a");
		assertEquals(TAB01.getCellByName(V.getInstance(2), V.getInstance("KEY")).stringValue(),"2");
		assertEquals(TAB01.getCellByName(V.getInstance(2), V.getInstance("VALUE")).stringValue(),"b");
		assertEquals(TAB01.getCellByName(V.getInstance(3), V.getInstance("kEy")).stringValue(),"3");
		assertEquals(TAB01.getCellByName(V.getInstance(3), V.getInstance("vAlUe")).stringValue(),"c");
		try {assertNull(TAB01.getCellByName(V.getInstance(4), V.getInstance("key"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(4), V.getInstance("value"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(4), V.getInstance("xxx"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(4), V.getInstance(""))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(0), V.getInstance(""))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(0), V.getInstance("key"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(0), V.getInstance("value"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(0), V.getInstance("xxx"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(-100), V.getInstance("key"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(1), V.getInstance("notthere"))); } catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellByName(V.getInstance(1), V.getInstance(""))); } catch(ExceptionCalculation e) {}

		assertEquals("1", _s.calculate("F01_06(1, \"key\")"));
		assertEquals("a", _s.calculate("F01_06(1, \"value\")"));
		assertEquals("2", _s.calculate("F01_06(2, \"KEY\")"));
		assertEquals("b", _s.calculate("F01_06(2, \"VALUE\")"));
		assertEquals("3", _s.calculate("F01_06(3, \"kEy\")"));
		assertEquals("c", _s.calculate("F01_06(3, \"vAlUe\")"));
	}
	@Test
	public void test07() {
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("a") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("2"), V.getInstance("b") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("b") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("3"), V.getInstance("c") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("3") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(3), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("c") });
		assertArrayEquals(TAB01.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });

		assertEquals("(1!a)", _s.calculate("F01_07(1, 1, 2)"));
		assertEquals("(1)",   _s.calculate("F01_07(1, 1, 1)"));
		assertEquals("(a)",   _s.calculate("F01_07(1, 2, 2)"));
		assertEquals("(#)",   _s.calculate("F01_07(1, 1, 0)"));
		assertEquals("(2!b)", _s.calculate("F01_07(2, 1, 2)"));
		assertEquals("(2)",   _s.calculate("F01_07(2, 1, 1)"));
		assertEquals("(b)",   _s.calculate("F01_07(2, 2, 2)"));
		assertEquals("(#)",   _s.calculate("F01_07(2, 1, 0)"));
		assertEquals("(3!c)", _s.calculate("F01_07(3, 1, 2)"));
		assertEquals("(3)",   _s.calculate("F01_07(3, 1, 1)"));
		assertEquals("(c)",   _s.calculate("F01_07(3, 2, 2)"));
		assertEquals("(#)",   _s.calculate("F01_07(3, 1, 0)"));
		
		try {assertNull(TAB01.getCellsRow(V.getInstance(1), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test08() {
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(0), V.getInstance(1)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a"), V.getInstance("b") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("b") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(3), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2"), V.getInstance("3") });
		assertArrayEquals(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(3), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a"), V.getInstance("b"), V.getInstance("c") });

		assertEquals("(1!2)",   _s.calculate("F01_08(1, 2, 1)"));
		assertEquals("(1)",     _s.calculate("F01_08(1, 1, 1)"));
		assertEquals("(2)",     _s.calculate("F01_08(2, 2, 1)"));
		assertEquals("(#)",     _s.calculate("F01_08(1, 0, 1)"));
		assertEquals("(a!b)",   _s.calculate("F01_08(1, 2, 2)"));
		assertEquals("(a)",     _s.calculate("F01_08(1, 1, 2)"));
		assertEquals("(b)",     _s.calculate("F01_08(2, 2, 2)"));
		assertEquals("(1!2!3)", _s.calculate("F01_08(1, 3, 1)"));
		assertEquals("(a!b!c)", _s.calculate("F01_08(1, 3, 2)"));
		
		try{assertNull(TAB01.getCellsColumn(V.getInstance(0), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB01.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(0)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test09() {
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("key")).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2") });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("KEY")).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("kEy")).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(0), V.getInstance("key")).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("value")).listValue().toArray(), new V[] { V.getInstance("a"), V.getInstance("b") });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("VALUE")).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB01.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("vAlUe")).listValue().toArray(), new V[] { V.getInstance("b") });

		assertEquals("(1!2)",   _s.calculate("F01_09(1, 2, \"key\")"));
		assertEquals("(1)",     _s.calculate("F01_09(1, 1, \"KEY\")"));
		assertEquals("(2)",     _s.calculate("F01_09(2, 2, \"kEy\")"));
		assertEquals("(#)",     _s.calculate("F01_09(1, 0, \"key\")"));
		assertEquals("(a!b)",   _s.calculate("F01_09(1, 2, \"value\")"));
		assertEquals("(a)",     _s.calculate("F01_09(1, 1, \"VALUE\")"));
		assertEquals("(b)",     _s.calculate("F01_09(2, 2, \"vAlUe\")"));

		try {assertNull(TAB01.getCellsColumnByName(V.getInstance(0), V.getInstance(1), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test10() {
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a], [2, b]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[1], [2]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[a], [b]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(1)).toString(), "[[], []]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(1)).toString(), "[[1]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(2), V.getInstance(2)).toString(), "[[a]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[2, b]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[2]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[b]]" );
		assertEquals(TAB01.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[3, c]]" );
		assertEquals(TAB01.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[3]]" );
		assertEquals(TAB01.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[c]]" );
		assertEquals(TAB01.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[2, b], [3, c]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[2], [3]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[b], [c]]" );
		assertEquals(TAB01.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], []]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a], [2, b], [3, c]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[1], [2], [3]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[a], [b], [c]]" );
		assertEquals(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], [], []]" );

		assertEquals("((1!a)!(2!b))",      _s.calculate("F01_10(1, 2, 1, 2)"));
		assertEquals("((1)!(2))",          _s.calculate("F01_10(1, 2, 1, 1)"));
		assertEquals("((a)!(b))",          _s.calculate("F01_10(1, 2, 2, 2)"));
		assertEquals("((#)!(#))",          _s.calculate("F01_10(1, 2, 2, 1)"));
		assertEquals("((1!a))",            _s.calculate("F01_10(1, 1, 1, 2)"));
		assertEquals("((1))",              _s.calculate("F01_10(1, 1, 1, 1)"));
		assertEquals("((a))",              _s.calculate("F01_10(1, 1, 2, 2)"));
		assertEquals("((2!b))",            _s.calculate("F01_10(2, 2, 1, 2)"));
		assertEquals("((2))",              _s.calculate("F01_10(2, 2, 1, 1)"));
		assertEquals("((b))",              _s.calculate("F01_10(2, 2, 2, 2)"));
		assertEquals("((3!c))",            _s.calculate("F01_10(3, 3, 1, 2)"));
		assertEquals("((3))",              _s.calculate("F01_10(3, 3, 1, 1)"));
		assertEquals("((c))",              _s.calculate("F01_10(3, 3, 2, 2)"));
		assertEquals("((#))",              _s.calculate("F01_10(3, 3, 2, 0)"));
		assertEquals("((2!b)!(3!c))",      _s.calculate("F01_10(2, 3, 1, 2)"));
		assertEquals("((2)!(3))",          _s.calculate("F01_10(2, 3, 1, 1)"));
		assertEquals("((b)!(c))",          _s.calculate("F01_10(2, 3, 2, 2)"));
		assertEquals("((#)!(#))",          _s.calculate("F01_10(2, 3, 2, 0)"));
		assertEquals("((1!a)!(2!b)!(3!c))",_s.calculate("F01_10(1, 3, 1, 2)"));
		assertEquals("((1)!(2)!(3))",      _s.calculate("F01_10(1, 3, 1, 1)"));
		assertEquals("((a)!(b)!(c))",      _s.calculate("F01_10(1, 3, 2, 2)"));
		assertEquals("((#)!(#)!(#))",      _s.calculate("F01_10(1, 3, 2, 0)"));

		try {assertNull(TAB01.getCells(V.getInstance(0), V.getInstance(3), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCells(V.getInstance(100), V.getInstance(100), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try {assertNull(TAB01.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(100)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test11() {
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a], [2, b]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("KEY")).toString(), "[[1], [2]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("vAlUe"), V.getInstance("value")).toString(), "[[a], [b]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("value"), V.getInstance("key")).toString(), "[[], []]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("key")).toString(), "[[1]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("value"), V.getInstance("value")).toString(), "[[a]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[2, b]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("key")).toString(), "[[2]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("value"), V.getInstance("value")).toString(), "[[b]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[3, c]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[3]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[c]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a], [2, b], [3, c]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[1], [2], [3]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[a], [b], [c]]" );
		assertEquals(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[], [], []]" );

		assertEquals("((1!a)!(2!b))",      _s.calculate("F01_11(1, 2, \"key\", \"value\")"));
		assertEquals("((1)!(2))",          _s.calculate("F01_11(1, 2, \"key\", \"key\")"));
		assertEquals("((a)!(b))",          _s.calculate("F01_11(1, 2, \"value\", \"value\")"));
		assertEquals("((#)!(#))",          _s.calculate("F01_11(1, 2, \"value\", \"key\")"));
		assertEquals("((1!a))",            _s.calculate("F01_11(1, 1, \"key\", \"value\")"));
		assertEquals("((1))",              _s.calculate("F01_11(1, 1, \"key\", \"key\")"));
		assertEquals("((a))",              _s.calculate("F01_11(1, 1, \"value\", \"value\")"));
		assertEquals("((2!b))",            _s.calculate("F01_11(2, 2, \"key\", \"value\")"));
		assertEquals("((2))",              _s.calculate("F01_11(2, 2, \"key\", \"key\")"));
		assertEquals("((b))",              _s.calculate("F01_11(2, 2, \"value\", \"value\")"));
		assertEquals("((3!c))",            _s.calculate("F01_11(3, 3, \"key\", \"value\")"));
		assertEquals("((3))",              _s.calculate("F01_11(3, 3, \"key\", \"key\")"));
		assertEquals("((c))",              _s.calculate("F01_11(3, 3, \"value\", \"value\")"));
		assertEquals("((#))",              _s.calculate("F01_11(3, 3, \"value\", \"key\")"));
		assertEquals("((1!a)!(2!b)!(3!c))",_s.calculate("F01_11(1, 3, \"key\", \"value\")"));
		assertEquals("((1)!(2)!(3))",      _s.calculate("F01_11(1, 3, \"key\", \"key\")"));
		assertEquals("((a)!(b)!(c))",      _s.calculate("F01_11(1, 3, \"value\", \"value\")"));
		assertEquals("((#)!(#)!(#))",      _s.calculate("F01_11(1, 3, \"value\", \"key\")"));
		
		try{assertNull(TAB01.getCellsByName(V.getInstance(0), V.getInstance(3), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB01.getCellsByName(V.getInstance(100), V.getInstance(100), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("aaa"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB01.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
}
