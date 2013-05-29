package treecalc.gen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import gen.testtab.*;

import treecalc.rt.ExceptionCalculation;
import treecalc.rt.S;
import treecalc.rt.V;
import treecalc.vm.TciMachine;
import treecalc.vm.asm.TciAsmReaderWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestTab03 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab03(Method method) {
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
	TABLE tab03 (key, value) {
		1, a;
		2, b;
		4, d;
	}
	=> non-direct access, not shuffled
*/

	@Test
	public void test01() {
		assertEquals(TAB03.findExact(V.getInstance("1")).stringValue(), "a");
		assertEquals(TAB03.findExact(V.getInstance("2")).stringValue(), "b");
		assertEquals(TAB03.findExact(V.getInstance("4")).stringValue(), "d");
		assertEquals(TAB03.findExact(V.getInstance(1)).stringValue(), "a");  
		try{assertNull(TAB03.findExact(V.getInstance("0")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExact(V.getInstance("3")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExact(V.getInstance(1.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExact(V.getInstance("01")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExact(V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExact(V.getInstance("abc")));} catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test02() {
		assertEquals(TAB03.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("a")
		).stringValue(), "1");

		assertEquals(TAB03.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "1");

		assertEquals(TAB03.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		assertEquals(TAB03.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		try{assertNull(TAB03.findExactColumn(
				V.getInstance("VALuexxx"), 
				V.getInstance("1"), 
				V.getInstance("A")
		));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("3"), 
				V.getInstance("A")
		));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("4"), 
				V.getInstance("A")
		));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("c")
		));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB03.findIntervalUp(V.getInstance("1")).toString(), "a");
		assertEquals(TAB03.findIntervalUp(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB03.findIntervalUp(V.getInstance(2)).toString(), "b");
		assertEquals(TAB03.findIntervalUp(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB03.findIntervalUp(V.getInstance(3)).toString(), "b");
		assertEquals(TAB03.findIntervalUp(V.getInstance("03.000")).toString(), "b");
		assertEquals(TAB03.findIntervalUp(V.getInstance(4)).toString(), "d");
		assertEquals(TAB03.findIntervalUp(V.getInstance("04.000")).toString(), "d");
		assertEquals(TAB03.findIntervalUp(V.getInstance("10000.000")).toString(), "d");
		try{assertNull(TAB03.findIntervalUp(V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findIntervalUp(V.getInstance(0.999)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test04() {
		try{assertNull(TAB03.findIntervalDown(V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB03.findIntervalDown(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB03.findIntervalDown(V.getInstance(2)).toString(), "a");
		assertEquals(TAB03.findIntervalDown(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB03.findIntervalDown(V.getInstance(3)).toString(), "b");
		assertEquals(TAB03.findIntervalDown(V.getInstance("03.000")).toString(), "b");
		assertEquals(TAB03.findIntervalDown(V.getInstance(3.0001)).toString(), "b");
		assertEquals(TAB03.findIntervalDown(V.getInstance("04.000")).toString(), "b");
		assertEquals(TAB03.findIntervalDown(V.getInstance(4.0001)).toString(), "d");
		assertEquals(TAB03.findIntervalDown(V.getInstance("10000.000")).toString(), "d");
		try{assertNull(TAB03.findIntervalUp(V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.findIntervalUp(V.getInstance(0.999)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test05() {
		assertEquals(TAB03.getCell(V.getInstance(1), V.getInstance(1)).stringValue(),"1");
		assertEquals(TAB03.getCell(V.getInstance(1), V.getInstance(2)).stringValue(),"a");
		assertEquals(TAB03.getCell(V.getInstance(2), V.getInstance(1)).stringValue(),"2");
		assertEquals(TAB03.getCell(V.getInstance(2), V.getInstance(2)).stringValue(),"b");
		assertEquals(TAB03.getCell(V.getInstance(3), V.getInstance(1)).stringValue(),"4");
		assertEquals(TAB03.getCell(V.getInstance(3), V.getInstance(2)).stringValue(),"d");
		try{assertNull(TAB03.getCell(V.getInstance(4), V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(4), V.getInstance(1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(4), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(4), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(0), V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(0), V.getInstance(1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(0), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(-100), V.getInstance(1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(1), V.getInstance(-100)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(1), V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCell(V.getInstance(1), V.getInstance(100)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test06() {
		assertEquals(TAB03.getCellByName(V.getInstance(1), V.getInstance("key")).stringValue(),"1");
		assertEquals(TAB03.getCellByName(V.getInstance(1), V.getInstance("value")).stringValue(),"a");
		assertEquals(TAB03.getCellByName(V.getInstance(2), V.getInstance("KEY")).stringValue(),"2");
		assertEquals(TAB03.getCellByName(V.getInstance(2), V.getInstance("VALUE")).stringValue(),"b");
		assertEquals(TAB03.getCellByName(V.getInstance(3), V.getInstance("kEy")).stringValue(),"4");
		assertEquals(TAB03.getCellByName(V.getInstance(3), V.getInstance("vAlUe")).stringValue(),"d");
		try{assertNull(TAB03.getCellByName(V.getInstance(4), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(4), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(4), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(4), V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(0), V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(0), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(0), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(0), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(-100), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(1), V.getInstance("notthere")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellByName(V.getInstance(1), V.getInstance("")));} catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test07() {
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("a") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("2"), V.getInstance("b") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("b") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("4"), V.getInstance("d") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("4") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(3), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("d") });
		assertArrayEquals(TAB03.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		
		try{assertNull(TAB03.getCellsRow(V.getInstance(1), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test08() {
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(0), V.getInstance(1)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a"), V.getInstance("b") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("b") });
		assertArrayEquals(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(3), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2"), V.getInstance("4") });
		try{assertNull(TAB03.getCellsColumn(V.getInstance(0), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(0)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test09() {
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("key")).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("2") });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("KEY")).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("kEy")).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(0), V.getInstance("key")).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("value")).listValue().toArray(), new V[] { V.getInstance("a"), V.getInstance("b") });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("VALUE")).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB03.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("vAlUe")).listValue().toArray(), new V[] { V.getInstance("b") });
		try{assertNull(TAB03.getCellsColumnByName(V.getInstance(0), V.getInstance(1), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test10() {
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a], [2, b]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[1], [2]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[a], [b]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(1)).toString(), "[[], []]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(1)).toString(), "[[1]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(2), V.getInstance(2)).toString(), "[[a]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[2, b]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[2]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[b]]" );
		assertEquals(TAB03.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[4, d]]" );
		assertEquals(TAB03.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[4]]" );
		assertEquals(TAB03.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[d]]" );
		assertEquals(TAB03.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[2, b], [4, d]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[2], [4]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[b], [d]]" );
		assertEquals(TAB03.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], []]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a], [2, b], [4, d]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[1], [2], [4]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[a], [b], [d]]" );
		assertEquals(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], [], []]" );
		try{assertNull(TAB03.getCells(V.getInstance(0), V.getInstance(3), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCells(V.getInstance(100), V.getInstance(100), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(100)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test11() {
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a], [2, b]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("KEY")).toString(), "[[1], [2]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("vAlUe"), V.getInstance("value")).toString(), "[[a], [b]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("value"), V.getInstance("key")).toString(), "[[], []]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("key")).toString(), "[[1]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("value"), V.getInstance("value")).toString(), "[[a]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[2, b]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("key")).toString(), "[[2]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("value"), V.getInstance("value")).toString(), "[[b]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[4, d]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[4]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[d]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a], [2, b], [4, d]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[1], [2], [4]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[a], [b], [d]]" );
		assertEquals(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[], [], []]" );
		try{assertNull(TAB03.getCellsByName(V.getInstance(0), V.getInstance(3), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsByName(V.getInstance(100), V.getInstance(100), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("aaa"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB03.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
	
}
