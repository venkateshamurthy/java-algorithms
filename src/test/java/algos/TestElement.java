/**
 * 
 */
package algos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import algos.lists.Element;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Slf4j
public class TestElement {
	@Test(expected = NullPointerException.class) public void testNullCheck() {
		Element.of(null);
		fail("Should throw NPE...cannot succeed");
	}
	
	@Test(expected = NullPointerException.class) public void testNullInsertCheck() {
		Element.of(0).insert(null);
		fail("Should throw NPE...cannot succeed");
	}

	Element<Integer>[] e;

	@Before public void before() {
		e = Element.arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	@Test public void testInitialAssignmentsForLeftOrRight() {

		assertEquals(e[0].left(),e[0]);
		assertEquals(e[0].right(),e[0]);

		e[2].insertAfter(e[0]);
		
		//In case of 2 elements just think like they are diametrically opposite in a circular loop
		assertTrue(e[2].hasLeft());
		assertEquals(e[2].left(), e[0]);
		assertEquals(e[2].right(), e[0]);
		//In case of 2 elements just think like they are diametrically opposite in a circular loop
		assertTrue(e[0].hasRight());
		assertEquals(e[0].right(), e[2]);
		assertEquals(e[0].left(), e[2]);
				
		assertTrue(e[0].hasLeft());
		assertTrue(e[2].hasRight());

		e[1].insert(e[2]);
		assertEquals(e[1].right(), e[2]);
		assertEquals(e[1].left(), e[0]);
		assertEquals(e[0].right(), e[1]);
		assertEquals(e[2].left(), e[1]);
		
		assertTrue(e[0].hasLeft());
		assertTrue(e[1].hasLeft());
		assertTrue(e[1].hasRight());
		assertTrue(e[2].hasRight());
		
		assertEquals(e[2].right(), e[0]);
		assertEquals(e[0].left(), e[2]);
	}

	@Test public void testAllInserts() {
		for (int i = 1; i < e.length; i++)
			e[i].insertAfter(e[i - 1]);
		for (int i = 0; i < e.length - 1; i++)
			assertEquals(e[i].right(), e[i + 1]);
		for (int j = e.length - 1; j > 0; j--)
			assertEquals(e[j].left(), e[j - 1]);
	}

	@Test public void testDeletes() {
		for (int i = 1; i < e.length; i++)
			e[i].insertAfter(e[i - 1]);
		for (int i = 0; i < e.length - 1; i++) {
			e[i].delete();
			assertTrue(e[i].isZombie());
			if (i + 1 < e.length - 1) {
				assertTrue(e[i + 1].hasRight());
				assertTrue(e[i+1].hasLeft());
				log.debug("%d=%s e[i+1].left= %s hasLeft=%s",i+1,e[i+1],e[i+1].left(),e[i+1].hasLeft());
				//assertFalse((i+1)+"'th one messedup",e[i + 1].hasLeft());//b'cos e[i] is deleted
			}
			else {
				assertFalse(e[i + 1].hasRight());
				assertFalse(e[i + 1].hasLeft());
			}

		}
	}

	@Test public void testDeletesInMiddle() {
		for (int i = 1; i < e.length; i++)
			e[i].insertAfter(e[i - 1]);
		for (int i = e.length / 2 - 1, j = i + 1; i >= 0; i--, j++) {
			assertEquals(e[i].right(), e[j]);
			assertEquals(e[j].left(), e[i]);
			e[i].delete();
			assertTrue(e[i].isZombie());
			e[j].delete();
			assertTrue(e[j].isZombie());
		}
		for (int i = 0; i < e.length; i++)
			assertTrue(i+"'th element mesing up",e[i].isZombie());
	}
}
