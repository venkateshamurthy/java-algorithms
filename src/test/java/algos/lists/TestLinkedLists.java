/**
 * 
 */
package algos.lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.junit.Test;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestLinkedLists {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	@Test public void detectLoop() {
		boolean doLoop = true, isLoop = false;

		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		for (int i = 0; i < 15; i++)
			linkedList.add(i);
		if (doLoop)
			linkedList.elementAt(14).right(linkedList.elementAt(9));

		val sentinel = linkedList.sentinel();
		Element<Integer> slow = linkedList.elementAt(0), fast = slow.right();
		while (slow != sentinel && fast != sentinel && fast.right() != sentinel) {
			log.warn("slow=%d,fast=%d:", slow.value(), fast.value());
			if (slow == fast) {
				log.warn("Loop ptrs met at:" + slow.value());
				isLoop = true;
				break;
			}

			slow = slow.right();
			fast = fast.right().right();

		}
		assertFalse(doLoop ^ isLoop);
		if (isLoop) {
			log.debug("Removing the loop");
			Element<Integer> storeLoopNode = slow, p1 = linkedList.elementAt(0), p2 = slow;
			while (p1 != p2.right()) {

				p1 = p1.right();
				p2 = p2.right();
				log.warn("p1=" + p1.value() + "p2=" + p2.value());
			}
			p2.right(sentinel);//set this to sentinel to remove the loop
		}

		slow = linkedList.elementAt(0);
		fast = slow.right();
		isLoop = false;
		while (slow != sentinel && fast != sentinel && fast.right() != sentinel) {
			log.warn("slow=%d,fast=%d:", slow.value(), fast.value());
			if (slow == fast) {
				log.warn("Loop ptrs met at:" + slow.value());
				isLoop = true;
				break;
			}
			slow = slow.right();
			fast = fast.right().right();
		}
		assertFalse(isLoop);
	}

	@Test public void testEmpty() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		assertTrue(linkedList.isEmpty());
		assertTrue(linkedList.size() == 0);
	}

	@Test public void testAddAndGet() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		assertEquals(-1, linkedList.indexOf(0));
		assertTrue(linkedList.isEmpty());
		for (int i = 0; i < 10; i++)
			linkedList.add(i);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, (int) linkedList.get(i));
		}
	}

	@Test public void testAddAndIndexOf() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		assertEquals(-1, linkedList.indexOf(0));
		assertTrue(linkedList.isEmpty());
		for (int i = 0; i < 10; i++)
			linkedList.add(i);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, (int) linkedList.indexOf(i));
		}
		assertEquals(-1, linkedList.indexOf(10));
		assertEquals(-1, linkedList.indexOf(-1));
		assertEquals(10, linkedList.size());
		assertFalse(linkedList.isEmpty());
	}

	@Test public void testDelete() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		assertEquals(-1, linkedList.indexOf(0));
		for (int i = 0; i < 10; i++)
			linkedList.add(i);
		for (int i = 0; i < 10 - 1; i++) {
			linkedList.delete(0);
			log.debug("testDelete:" + (i) + " value(0):" + linkedList.get(0));
			assertEquals(i + 1, (int) linkedList.get(0));
		}

	}

	@Test(expected = IndexOutOfBoundsException.class) public void testDeleteWithIndexOutOfBounds() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		assertEquals(-1, linkedList.indexOf(0));
		for (int i = 0; i < 10; i++)
			linkedList.add(i);
		for (int i = 0; i < 10 - 1; i++) {
			linkedList.delete(0);
			log.debug("testDelete:" + (i) + " value(0):" + linkedList.get(0));
			assertEquals(i + 1, (int) linkedList.get(0));
		}
		linkedList.delete(0);
		linkedList.get(0);
		fail("This must throw exception as get must fail after the last element is deleted..");

	}
}
