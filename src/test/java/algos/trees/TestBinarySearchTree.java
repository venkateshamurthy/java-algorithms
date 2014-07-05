/**
 * 
 */
package algos.trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static java.lang.Integer.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.util.FastMath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;

import algos.trees.visitors.HasPathSumVisitor;
import algos.trees.visitors.InOrderPrinter;
import algos.trees.visitors.LevelOrderPrinter;
import algos.trees.visitors.MaxSumPathVisitor;
import algos.trees.visitors.MirroringVisitor;
import algos.trees.visitors.PostOrderPrinter;
import algos.trees.visitors.PreOrderPrinter;
import algos.trees.visitors.PrintVerticalPathVisitor;
import algos.trees.visitors.Visitor;
import custom.junit.runners.OrderedJUnit4ClassRunner;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RunWith(OrderedJUnit4ClassRunner.class)
public class TestBinarySearchTree {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	@Rule public TestWatchman testWatchMan = new TestWatchman() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.junit.rules.TestWatchman#starting(org.junit.runners.model.
		 * FrameworkMethod)
		 */
		@Override public void starting(FrameworkMethod method) {
			// TODO Auto-generated method stub
			super.starting(method);
			log.debug("Starting .." + method.getName());
		}
	};

	Tree<Integer> bst = BinarySearchTree.of();
	
	static Integer[] x = new Integer[32];

	@BeforeClass public static void beforeClass() {
		for (int i = 0; i < x.length; i++)
			x[i] = i + 1;
	}

	@Before public void before() {
		bst.addAll(x, 0, x.length - 1);
	}

	@Test public void testAdd() {
		assertEquals(x.length, bst.size());
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
	}

	@Test public void testRemove() {
		assertEquals(x.length, bst.size());
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		List list = new ArrayList();
		// Remove Node that has Both Children
		Integer EIGHT = 8;
		Element<Integer> t = bst.remove(EIGHT);
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		log.debug("removed value=" + t.value());
		assertEquals(EIGHT, t.value());

		Visitor<Integer, Integer, List<Integer>> inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst.root());
		// for (Integer i : inOrderPrinter.list())
		// log.debug(i);
		log.debug("Size=" + bst.size());
		assertEquals(x.length - 1, bst.size());
		assertNull(bst.contains(EIGHT));
		Integer[] y = (Integer[]) ArrayUtils.remove(x,
				ArrayUtils.indexOf(x, EIGHT));
		Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());

		// Remove Node that has two childs but the successor has one child.
		Integer NINE = 9;
		t = bst.remove(NINE);
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		log.debug("removed value=" + t.value());
		assertEquals(NINE, t.value());

		inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst.root());
		// for (Integer i : inOrderPrinter.list())
		// log.debug(i);
		log.debug("Size=" + bst.size());
		assertEquals(x.length - 2, bst.size());
		assertNull(bst.contains(NINE));

		y = (Integer[]) ArrayUtils.remove(y, ArrayUtils.indexOf(y, NINE));
		Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());

		// Remove Node that bachelor
		Integer THIRTY_TWO = 32;
		t = bst.remove(THIRTY_TWO);
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		if (t != null) {
			log.debug("removed value=" + t.value());
			assertEquals(THIRTY_TWO, t.value());

			inOrderPrinter = new InOrderPrinter<Integer>();
			inOrderPrinter.visit(bst.root());
			// for (Integer i : inOrderPrinter.list())
			// log.debug(i);
			log.debug("Size=" + bst.size());
			assertEquals(x.length - 3, bst.size());
			assertNull(bst.contains(THIRTY_TWO));

			y = (Integer[]) ArrayUtils.remove(y,
					ArrayUtils.indexOf(y, THIRTY_TWO));
			Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());
		}

		// Remove Node that bachelor
		Integer TWENTY_FOUR = 24;
		t = bst.remove(TWENTY_FOUR);
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		if (t != null) {
			log.debug("removed value=" + t.value());
			assertEquals(TWENTY_FOUR, t.value());

			inOrderPrinter = new InOrderPrinter<Integer>();
			inOrderPrinter.visit(bst.root());
			// for (Integer i : inOrderPrinter.list())
			// log.debug(i);
			log.debug("Size=" + bst.size());
			assertEquals(x.length - 4, bst.size());
			assertNull(bst.contains(TWENTY_FOUR));

			y = (Integer[]) ArrayUtils.remove(y,
					ArrayUtils.indexOf(y, TWENTY_FOUR));
			Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());
		}

	}

	@Test public void testInorder() {
		Visitor<Integer, Integer, List<Integer>> inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst.root());
		Assert.assertArrayEquals(x, inOrderPrinter.collection().toArray());
	}

	@Test public void testPreorder() {
		Integer[] y = { 16, 8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15,
				24, 20, 18, 17, 19, 22, 21, 23, 28, 26, 25, 27, 30, 29, 31, 32 };
		Visitor<Integer, Integer, List<Integer>> preOrderPrinter = new PreOrderPrinter<Integer>();
		preOrderPrinter.visit(bst.root());
		Assert.assertArrayEquals(y, preOrderPrinter.collection().toArray());
	}

	@Test public void testPostorder() {
		Integer[] y = { 1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13, 15, 14, 12, 8, 17,
				19, 18, 21, 23, 22, 20, 25, 27, 26, 29, 32, 31, 30, 28, 24, 16 };
		Visitor<Integer, Integer, List<Integer>> printer = new PostOrderPrinter<Integer>();
		printer.visit(bst.root());
		Assert.assertArrayEquals(y, printer.collection().toArray());
	}

	@Test public void testLevelorder() {
		Integer[] y = { 16, 8, 24, 04, 12, 20, 28, 02, 06, 10, 14, 18, 22, 26,
				30, 01, 03, 05, 07, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29,
				31, 32 };
		Visitor<Integer, Integer, List<Integer>> printer = new LevelOrderPrinter<Integer>();
		printer.visit(bst.root());
		Assert.assertArrayEquals(y, printer.collection().toArray());
	}

	@Test public void testHeight() {
		// Formula is N = POW(2,h) - 1; therefore h=log(2,N+1)
		int N = x.length;
		assertEquals((int) FastMath.ceil(FastMath.log(2, N + 1)), bst.height());
		bst.remove(32);
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		assertEquals((int) FastMath.ceil(FastMath.log(2, N)), bst.height());
	}

	@Test public void testContains() {
		for (Integer i : x)
			assertEquals(i, bst.contains(i).value());
		bst.remove(24);
		bst.remove(8);
		bst.remove(16);
		assertNull(bst.contains(24));
		assertNull(bst.contains(8));
		assertNull(bst.contains(16));
		bst.remove(1);
		bst.remove(32);
		assertNull(bst.contains(1));
		assertNull(bst.contains(32));
		assertTrue(bst.validate(MIN_VALUE,MAX_VALUE));
		assertEquals(27, bst.size());
	}

	@Test public void testHasPathSum() {
		Visitor<Integer, Boolean, Integer> v = HasPathSumVisitor.Builder
				.builder().targetSum(2400).tree(bst).build();

		assertFalse(v.visit(bst));
		log.debug(v.collection());

		v = HasPathSumVisitor.Builder.builder().targetSum(129).tree(bst)
				.build();
		log.debug(v.collection());
		assertTrue(v.visit(bst));
	}

	final Integer[][] headToTailValues = { { 16, 8, 4, 2, 1 },
			{ 16, 8, 4, 2, 3 }, { 16, 8, 4, 6, 5 }, { 16, 8, 4, 6, 7 },
			{ 16, 8, 12, 10, 9 }, { 16, 8, 12, 10, 11 }, { 16, 8, 12, 14, 13 },
			{ 16, 8, 12, 14, 15 }, { 16, 24, 20, 18, 17 },
			{ 16, 24, 20, 18, 19 }, { 16, 24, 20, 22, 21 },
			{ 16, 24, 20, 22, 23 }, { 16, 24, 28, 26, 25 },
			{ 16, 24, 28, 26, 27 }, { 16, 24, 28, 30, 29 },
			{ 16, 24, 28, 30, 31, 32 } };

	@Test(timeout = 5000) public void testPrintVertical() {
		Boolean HEAD_TO_TAIL = true, TAIL_TO_HEAD = false;
		List<List<Integer>> headToTailList = new ArrayList<List<Integer>>(), tailToHeadList = new ArrayList<List<Integer>>();
		for (Integer[] a : headToTailValues) {
			val list = Arrays.asList(a);
			headToTailList.add(new ArrayList<Integer>(list));
			// Now tail to head
			Collections.reverse(list);
			tailToHeadList.add(new ArrayList<Integer>(list));
		}
		Visitor<Integer, Void, List<List<Integer>>> headToTailVisitor = PrintVerticalPathVisitor
				.of(HEAD_TO_TAIL);
		headToTailVisitor.visit(bst.root());
		assertEquals(headToTailList, headToTailVisitor.collection());

		Visitor<Integer, Void, List<List<Integer>>> tailToHeadVisitor = PrintVerticalPathVisitor
				.of(TAIL_TO_HEAD);
		tailToHeadVisitor.visit(bst.root());
		assertEquals(tailToHeadList, tailToHeadVisitor.collection());
	}

	@Test(timeout = 5000) public void testMaxSum() {
		Boolean HEAD_TO_TAIL = true, TAIL_TO_HEAD = false;
		List<Integer> headToTailList = Arrays.asList(16, 24, 28, 30, 31, 32), tailToHeadList = Arrays
				.asList(32, 31, 30, 28, 24, 16);
		Visitor<Integer, Void, List<Integer>> headToTailVisitor = MaxSumPathVisitor
				.of(HEAD_TO_TAIL);
		headToTailVisitor.visit(bst.root());
		assertEquals(headToTailList, headToTailVisitor.collection());

		Visitor<Integer, Void, List<Integer>> tailToHeadVisitor = MaxSumPathVisitor
				.of(TAIL_TO_HEAD);
		tailToHeadVisitor.visit(bst.root());
		assertEquals(tailToHeadList, tailToHeadVisitor.collection());
	}

	@Test(timeout = 5000) public void testMirror() {
		Visitor<Integer, Void, Void> mirroring = MirroringVisitor.of();
		Visitor<Integer, Integer, List<Integer>> inOrderPrinter = new InOrderPrinter<Integer>();
		StringBuilder sb = new StringBuilder();
		// First reverse
		mirroring.visit(bst.root());
		inOrderPrinter.visit(bst.root());
		sb.setLength(0);
		for (int i : inOrderPrinter.collection())
			sb.append(" ").append(i);
		log.debug(sb.toString());

		ArrayUtils.reverse(x);
		assertEquals(Arrays.asList(x), inOrderPrinter.collection());
		// Second reverse
		mirroring = MirroringVisitor.of();
		mirroring.visit(bst.root());
		inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst.root());
		sb.setLength(0);
		for (int i : inOrderPrinter.collection())
			sb.append(" ").append(i);
		log.debug(sb.toString());
		ArrayUtils.reverse(x);
		assertEquals(Arrays.asList(x), inOrderPrinter.collection());

	}

	@Test(timeout = 5000) public void testSame() {
		assertTrue(bst.equals(bst));
		Tree<Integer> newBst = BinarySearchTree.of();
		ArrayUtils.reverse(x);
		newBst.addAll(x, 0, x.length - 1);
		assertFalse(bst.equals(newBst));
	}

	@Test(timeout = 5000) public void testSame1() {
		assertTrue(bst.equals(bst));
		Tree<Integer> newBst = BinarySearchTree.of();
		newBst.addAll(x, 0, x.length - 1);
		assertTrue(bst.equals(newBst));
		newBst.remove(10);
		assertFalse(bst.equals(newBst));
		bst.remove(10);
		assertTrue(bst.equals(newBst));
		bst.remove(24);
		newBst.remove(24);
		assertTrue(bst.equals(newBst));

	}
}
