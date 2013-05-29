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
public class TestTab02 {
	@Parameters
	public static Collection<Object[]> configs() {
		return Method.getMethodsNoJavaScript();
	}

	private Method method;
	private S _s;
	public TestTab02(Method method) {
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
	TABLE tab02 (key, value) {
		3, c;
		1, a;
		2, b;
	}
	=> direct access, shuffled
*/

	@Test
	public void test01() {
		assertEquals(TAB02.findExact(V.getInstance("1")).stringValue(), "a");
		assertEquals(TAB02.findExact(V.getInstance("2")).stringValue(), "b");
		assertEquals(TAB02.findExact(V.getInstance("3")).stringValue(), "c");
		assertEquals(TAB02.findExact(V.getInstance(1)).stringValue(), "a");  
		try{assertNull(TAB02.findExact(V.getInstance("0")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExact(V.getInstance("4")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExact(V.getInstance(1.1)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExact(V.getInstance("01")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExact(V.getInstance("")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExact(V.getInstance("abc")));} catch(ExceptionCalculation e) {}
		
		assertEquals("a", _s.calculate("F02_01(1)"));
		assertEquals("b", _s.calculate("F02_01(2)"));
		assertEquals("c", _s.calculate("F02_01(3)"));
		assertEquals("a", _s.calculate("F02_01(\"1\")"));
	}
	
	@Test
	public void test02() {
		assertEquals(TAB02.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("a")
		).stringValue(), "1");
		assertEquals("1", _s.calculate("F02_02_01(1,\"a\")"));

		assertEquals(TAB02.findExactColumn(
				V.getInstance("key"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "1");
		assertEquals("1", _s.calculate("F02_02_01(1,\"A\")"));

		assertEquals(TAB02.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		assertEquals("a", _s.calculate("F02_02_02(1,\"A\")"));

		assertEquals(TAB02.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("1"), 
				V.getInstance("A")
		).stringValue(), "a");
		assertEquals("a", _s.calculate("F02_02_03(1,\"A\")"));
		
		try{assertNull(TAB02.findExactColumn(
				V.getInstance("VALuexxx"), 
				V.getInstance("1"), 
				V.getInstance("A")
		));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExactColumn(
				V.getInstance("VALue"), 
				V.getInstance("3"), 
				V.getInstance("A")
		));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findExactColumn(
				V.getInstance("value"), 
				V.getInstance("1"), 
				V.getInstance("c")
		));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test03() {
		assertEquals(TAB02.findIntervalUp(V.getInstance("1")).toString(), "a");
		assertEquals(TAB02.findIntervalUp(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB02.findIntervalUp(V.getInstance(2)).toString(), "b");
		assertEquals(TAB02.findIntervalUp(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB02.findIntervalUp(V.getInstance(3)).toString(), "c");
		assertEquals(TAB02.findIntervalUp(V.getInstance("03.000")).toString(), "c");
		assertEquals(TAB02.findIntervalUp(V.getInstance("10000.000")).toString(), "c");
		try{assertNull(TAB02.findIntervalUp(V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findIntervalUp(V.getInstance(0.999)));} catch(ExceptionCalculation e) {}
		
		assertEquals("a", _s.calculate("F02_03(1)"));
		assertEquals("a", _s.calculate("F02_03(1.2)"));
		assertEquals("b", _s.calculate("F02_03(2)"));
		assertEquals("b", _s.calculate("F02_03(2.999)"));
		assertEquals("c", _s.calculate("F02_03(3)"));
		assertEquals("c", _s.calculate("F02_03(03.000)"));
		assertEquals("c", _s.calculate("F02_03(10000.000)"));
		
	}
	@Test
	public void test04() {
		try{assertNull(TAB02.findIntervalDown(V.getInstance("1")));} catch(ExceptionCalculation e) {}
		assertEquals(TAB02.findIntervalDown(V.getInstance(1.2)).toString(), "a");
		assertEquals(TAB02.findIntervalDown(V.getInstance(2)).toString(), "a");
		assertEquals(TAB02.findIntervalDown(V.getInstance(2.999)).toString(), "b");
		assertEquals(TAB02.findIntervalDown(V.getInstance(3)).toString(), "b");
		assertEquals(TAB02.findIntervalDown(V.getInstance("03.000")).toString(), "b");
		assertEquals(TAB02.findIntervalDown(V.getInstance(3.0001)).toString(), "c");
		assertEquals(TAB02.findIntervalDown(V.getInstance("10000.000")).toString(), "c");
		try{assertNull(TAB02.findIntervalUp(V.getInstance(0)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.findIntervalUp(V.getInstance(0.999)));} catch(ExceptionCalculation e) {}
		
		assertEquals("a", _s.calculate("F02_04(1.2)"));
		assertEquals("a", _s.calculate("F02_04(2)"));
		assertEquals("b", _s.calculate("F02_04(2.999)"));
		assertEquals("b", _s.calculate("F02_04(3)"));
		assertEquals("b", _s.calculate("F02_04(03.000)"));
		assertEquals("c", _s.calculate("F02_04(3.0001)"));
		assertEquals("c", _s.calculate("F02_04(10000.000)"));
	}
	@Test
	public void test05() {
		assertEquals(TAB02.getCell(V.getInstance(1), V.getInstance(1)).stringValue(),"3");
		assertEquals(TAB02.getCell(V.getInstance(1), V.getInstance(2)).stringValue(),"c");
		assertEquals(TAB02.getCell(V.getInstance(2), V.getInstance(1)).stringValue(),"1");
		assertEquals(TAB02.getCell(V.getInstance(2), V.getInstance(2)).stringValue(),"a");
		assertEquals(TAB02.getCell(V.getInstance(3), V.getInstance(1)).stringValue(),"2");
		assertEquals(TAB02.getCell(V.getInstance(3), V.getInstance(2)).stringValue(),"b");

		assertEquals("3", _s.calculate("F02_05(1, 1)"));
		assertEquals("c", _s.calculate("F02_05(1, 2)"));
		assertEquals("1", _s.calculate("F02_05(2, 1)"));
		assertEquals("a", _s.calculate("F02_05(2, 2)"));
		assertEquals("2", _s.calculate("F02_05(3, 1)"));
		assertEquals("b", _s.calculate("F02_05(3, 2)"));
		
		try{ assertNull(TAB02.getCell(V.getInstance(4), V.getInstance(0)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(4), V.getInstance(1)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(4), V.getInstance(2)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(4), V.getInstance(3)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(0), V.getInstance(0)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(0), V.getInstance(1)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(0), V.getInstance(2)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(0), V.getInstance(3)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(-100), V.getInstance(1)));  } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(1), V.getInstance(-100)));  } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(1), V.getInstance(0)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(1), V.getInstance(3)));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCell(V.getInstance(1), V.getInstance(100)));   } catch(ExceptionCalculation e) {}

	}
	@Test
	public void test06() {
		assertEquals(TAB02.getCellByName(V.getInstance(1), V.getInstance("key")).stringValue(),"3");
		assertEquals(TAB02.getCellByName(V.getInstance(1), V.getInstance("value")).stringValue(),"c");
		assertEquals(TAB02.getCellByName(V.getInstance(2), V.getInstance("KEY")).stringValue(),"1");
		assertEquals(TAB02.getCellByName(V.getInstance(2), V.getInstance("VALUE")).stringValue(),"a");
		assertEquals(TAB02.getCellByName(V.getInstance(3), V.getInstance("kEy")).stringValue(),"2");
		assertEquals(TAB02.getCellByName(V.getInstance(3), V.getInstance("vAlUe")).stringValue(),"b");

		assertEquals("3", _s.calculate("F02_06(1, \"key\")"));
		assertEquals("c", _s.calculate("F02_06(1, \"value\")"));
		assertEquals("1", _s.calculate("F02_06(2, \"KEY\")"));
		assertEquals("a", _s.calculate("F02_06(2, \"VALUE\")"));
		assertEquals("2", _s.calculate("F02_06(3, \"kEy\")"));
		assertEquals("b", _s.calculate("F02_06(3, \"vAlUe\")"));
		
		try{ assertNull(TAB02.getCellByName(V.getInstance(4), V.getInstance("key")));        } catch(ExceptionCalculation e) {}     
		try{ assertNull(TAB02.getCellByName(V.getInstance(4), V.getInstance("value")));      } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(4), V.getInstance("xxx")));        } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(4), V.getInstance("")));           } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(0), V.getInstance("")));           } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(0), V.getInstance("key")));        } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(0), V.getInstance("value")));      } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(0), V.getInstance("xxx")));        } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(-100), V.getInstance("key")));     } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(1), V.getInstance("notthere")));   } catch(ExceptionCalculation e) {}
		try{ assertNull(TAB02.getCellByName(V.getInstance(1), V.getInstance("")));           } catch(ExceptionCalculation e) {}
	}
	
	@Test
	public void test07() {
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("3"), V.getInstance("c") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("3") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("c") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("1"), V.getInstance("a") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(2), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("2"), V.getInstance("b") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("2") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(3), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("b") });
		assertArrayEquals(TAB02.getCellsRow(V.getInstance(3), V.getInstance(1), V.getInstance(0)).listValue().toArray(), new V[] {  });
		
		try{assertNull(TAB02.getCellsRow(V.getInstance(1), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsRow(V.getInstance(1), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test08() {
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("3"), V.getInstance("1") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("3") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(0), V.getInstance(1)).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("c"), V.getInstance("a") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("c") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(2), V.getInstance(2), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("a") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(3), V.getInstance(1)).listValue().toArray(), new V[] { V.getInstance("3"), V.getInstance("1"), V.getInstance("2") });
		assertArrayEquals(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(3), V.getInstance(2)).listValue().toArray(), new V[] { V.getInstance("c"), V.getInstance("a"), V.getInstance("b") });
		try{assertNull(TAB02.getCellsColumn(V.getInstance(0), V.getInstance(1), V.getInstance(3)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsColumn(V.getInstance(1), V.getInstance(1), V.getInstance(0)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test09() {
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("key")).listValue().toArray(), new V[] { V.getInstance("3"), V.getInstance("1") });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("KEY")).listValue().toArray(), new V[] { V.getInstance("3") });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("kEy")).listValue().toArray(), new V[] { V.getInstance("1") });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(0), V.getInstance("key")).listValue().toArray(), new V[] {  });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(2), V.getInstance("value")).listValue().toArray(), new V[] { V.getInstance("c"), V.getInstance("a") });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("VALUE")).listValue().toArray(), new V[] { V.getInstance("c") });
		assertArrayEquals(TAB02.getCellsColumnByName(V.getInstance(2), V.getInstance(2), V.getInstance("vAlUe")).listValue().toArray(), new V[] { V.getInstance("a") });
		try{assertNull(TAB02.getCellsColumnByName(V.getInstance(0), V.getInstance(1), V.getInstance("key")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsColumnByName(V.getInstance(1), V.getInstance(1), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test10() {
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[3, c], [1, a]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[3], [1]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[c], [a]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(2), V.getInstance(2), V.getInstance(1)).toString(), "[[], []]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(2)).toString(), "[[3, c]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(1), V.getInstance(1)).toString(), "[[3]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(1), V.getInstance(2), V.getInstance(2)).toString(), "[[c]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(1), V.getInstance(1)).toString(), "[[1]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(2), V.getInstance(2), V.getInstance(2)).toString(), "[[a]]" );
		assertEquals(TAB02.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[2, b]]" );
		assertEquals(TAB02.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[2]]" );
		assertEquals(TAB02.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[b]]" );
		assertEquals(TAB02.getCells(V.getInstance(3), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[1, a], [2, b]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[1], [2]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[a], [b]]" );
		assertEquals(TAB02.getCells(V.getInstance(2), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], []]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(2)).toString(), "[[3, c], [1, a], [2, b]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(1)).toString(), "[[3], [1], [2]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(2)).toString(), "[[c], [a], [b]]" );
		assertEquals(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(2), V.getInstance(0)).toString(), "[[], [], []]" );
		try{assertNull(TAB02.getCells(V.getInstance(0), V.getInstance(3), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCells(V.getInstance(100), V.getInstance(100), V.getInstance(1), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(0), V.getInstance(2)));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCells(V.getInstance(1), V.getInstance(3), V.getInstance(1), V.getInstance(100)));} catch(ExceptionCalculation e) {}
	}
	@Test
	public void test11() {
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[3, c], [1, a]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("key"), V.getInstance("KEY")).toString(), "[[3], [1]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("vAlUe"), V.getInstance("value")).toString(), "[[c], [a]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(2), V.getInstance("value"), V.getInstance("key")).toString(), "[[], []]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("value")).toString(), "[[3, c]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("key"), V.getInstance("key")).toString(), "[[3]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(1), V.getInstance("value"), V.getInstance("value")).toString(), "[[c]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("value")).toString(), "[[1, a]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("key"), V.getInstance("key")).toString(), "[[1]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(2), V.getInstance(2), V.getInstance("value"), V.getInstance("value")).toString(), "[[a]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[2, b]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[2]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[b]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(3), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("value")).toString(), "[[3, c], [1, a], [2, b]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("key")).toString(), "[[3], [1], [2]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("value")).toString(), "[[c], [a], [b]]" );
		assertEquals(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("value"), V.getInstance("key")).toString(), "[[], [], []]" );
		try{assertNull(TAB02.getCellsByName(V.getInstance(0), V.getInstance(3), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsByName(V.getInstance(100), V.getInstance(100), V.getInstance("key"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("aaa"), V.getInstance("value")));} catch(ExceptionCalculation e) {}
		try{assertNull(TAB02.getCellsByName(V.getInstance(1), V.getInstance(3), V.getInstance("key"), V.getInstance("xxx")));} catch(ExceptionCalculation e) {}
	}
	
}
