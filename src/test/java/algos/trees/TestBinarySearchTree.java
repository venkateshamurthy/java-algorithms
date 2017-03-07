package algos.trees;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.FastMath;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;

import com.google.common.collect.Lists;

import algos.trees.visitors.BSTVisitor;
import algos.trees.visitors.CountOfPathsHavingAGivenSum;
import algos.trees.visitors.HasPathSumVisitor;
import algos.trees.visitors.InOrderPrinter;
import algos.trees.visitors.LevelOrderPrinter;
import algos.trees.visitors.MaxSumPathVisitor;
import algos.trees.visitors.MirroringVisitor;
import algos.trees.visitors.PostOrderPrinter;
import algos.trees.visitors.PreOrderPrinter;
import algos.trees.visitors.PrintVerticalPathVisitor;
import algos.trees.visitors.SizeVisitor;
import custom.junit.runners.OrderedJUnit4ClassRunner;
import lombok.AccessLevel;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RunWith(OrderedJUnit4ClassRunner.class)
public class TestBinarySearchTree {
	//static final Logger log = LogManager.getLogger(StringFormatterMessageFactory.INSTANCE);
	@Rule
	public TestWatchman testWatchMan = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			super.starting(method);
			log.debug("Starting .." + method.getName());
		}
	};

	Tree<Integer, BSTNode<Integer>> bst = new VanillaBST<Integer>(o->new BSTNode<Integer>(o));

	static Integer[] x = new Integer[32];

	@BeforeClass
	public static void beforeClass() {
		for (int i = 0; i < x.length; i++)
			x[i] = i + 1;
	}

	@Before
	public void before() {
	  Arrays.sort(x);
		bst.addAll(x);
	}
	
	@After
  public void after() {
    bst.clear();
  }

	@Test
	public void testAdd() {
		assertEquals(x.length, bst.size());
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
	}

	//@Test
	@Ignore
	public void testRemove() {
		assertEquals(x.length, bst.size());
		InOrderPrinter<Integer> inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst);
		log.debug("{}",inOrderPrinter.collection());
		Assert.assertArrayEquals(x, inOrderPrinter.collection().toArray());
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		
		// Remove Node that has Both Children
		Integer EIGHT = 8;
		BSTNode<Integer> t = bst.remove(EIGHT);
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		log.debug("removed value=" + t.value());
		assertEquals(EIGHT, t.value());
		inOrderPrinter.visit(bst);
		log.debug("After removing {} {}",EIGHT, inOrderPrinter.collection());
		assertEquals(x.length - 1, bst.size());
		assertNull(bst.contains(EIGHT));
		Integer[] y =  ArrayUtils.remove(x, ArrayUtils.indexOf(x, EIGHT));
		log.debug("y={}",ArrayUtils.toString(y));;
		Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());

		// Remove Node that has two childs but the successor has one child.
		Integer NINE = 9;
		t = bst.remove(NINE);
		assertNull(bst.contains(NINE));
		inOrderPrinter.visit(bst);
		log.debug("After removing {} {}",NINE, inOrderPrinter.collection());
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		log.debug("removed value=" + t.value());
		assertEquals(NINE, t.value());

		inOrderPrinter.visit(bst);
		log.debug("Size=" + bst.size());
		assertEquals(y.length - 1, bst.size());
		assertNull(bst.contains(NINE));
		Assert.assertTrue(ArrayUtils.indexOf(y, NINE)!=-1);
		Integer[] z = ArrayUtils.remove(y, ArrayUtils.indexOf(y, NINE));
		log.debug("z={}",ArrayUtils.toString(z));;
		Assert.assertArrayEquals(z, inOrderPrinter.collection().toArray());

		// Remove Node that bachelor
		Integer THIRTY_TWO = 32;
		t = bst.remove(THIRTY_TWO);
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		if (t != null) {
			log.debug("removed value=" + t.value());
			assertEquals(THIRTY_TWO, t.value());

			inOrderPrinter.visit(bst);
			log.debug("Size=" + bst.size());
			assertEquals(x.length - 3, bst.size());
			assertNull(bst.contains(THIRTY_TWO));

			y = (Integer[]) ArrayUtils.remove(y, ArrayUtils.indexOf(y, THIRTY_TWO));
			Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());
		}

		// Remove Node that bachelor
		Integer TWENTY_FOUR = 24;
		t = bst.remove(TWENTY_FOUR);
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		if (t != null) {
			log.debug("removed value=" + t.value());
			assertEquals(TWENTY_FOUR, t.value());

			inOrderPrinter.visit(bst);
			log.debug("Size=" + bst.size());
			assertEquals(x.length - 4, bst.size());
			assertNull(bst.contains(TWENTY_FOUR));

			y = (Integer[]) ArrayUtils.remove(y, ArrayUtils.indexOf(y, TWENTY_FOUR));
			Assert.assertArrayEquals(y, inOrderPrinter.collection().toArray());
		}

	}

	@Test
	public void testInorder() {
		BSTVisitor<Integer, Integer, List<Integer>> inOrderPrinter = new InOrderPrinter<Integer>();
		inOrderPrinter.visit(bst.root());
		Assert.assertArrayEquals(x, inOrderPrinter.collection().toArray());
	}

	@Test
	public void testPreorder() {
		Integer[] y = { 16, 8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15, 24, 20, 18, 17, 19, 22, 21, 23, 28, 26,
				25, 27, 30, 29, 31, 32 };
		BSTVisitor<Integer, Integer, List<Integer>> preOrderPrinter = new PreOrderPrinter<Integer>();
		preOrderPrinter.visit(bst.root());
		Assert.assertArrayEquals(y, preOrderPrinter.collection().toArray());
	}

	@Test
	public void testPostorder() {
		Integer[] y = { 1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13, 15, 14, 12, 8, 17, 19, 18, 21, 23, 22, 20, 25, 27, 26, 29,
				32, 31, 30, 28, 24, 16 };
		BSTVisitor<Integer, Integer, List<Integer>> printer = new PostOrderPrinter<Integer>();
		printer.visit(bst.root());
		Assert.assertArrayEquals(y, printer.collection().toArray());
	}

	@Test
	public void testLevelorder() {
		Integer[] y = { 16, 8, 24, 04, 12, 20, 28, 02, 06, 10, 14, 18, 22, 26, 30, 01, 03, 05, 07, 9, 11, 13, 15, 17,
				19, 21, 23, 25, 27, 29, 31, 32 };
		BSTVisitor<Integer, Integer, List<Integer>> printer = new LevelOrderPrinter<Integer>();
		printer.visit(bst.root());
		Assert.assertArrayEquals(y, printer.collection().toArray());
	}

	@Test
	public void testHeight() {
		// Formula is N = POW(2,h) - 1; therefore h=log(2,N+1)
		int N = x.length;
		assertEquals((int) FastMath.ceil(FastMath.log(2, N + 1)), bst.height());
		bst.remove(32);
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		assertEquals((int) FastMath.ceil(FastMath.log(2, N)), bst.height());
	}

	@Test
	public void testContains() {
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
		assertTrue(bst.validate(MIN_VALUE, MAX_VALUE));
		assertEquals(27, bst.size());
		assertTrue(27 == new SizeVisitor<Integer>().visit(bst));
	}
	
	@Test
  public void testCountOfPathsHavingSum() {
	  /**
	   * <pre>
	   *             10
	   *           5    -3
	   *        3     2     11
	   *     3    -2     1
	   * </pre>
	   */
	  Tree<Integer, BSTNode<Integer>> tree = new VanillaBST<Integer>();
	  tree.add(10);
	  val root =tree.root();
	  root.left(5).right(-3);
	  root.left.left(3).right(2);root.right.right(11);
	  root.left.left.left(3).right(-2);root.left.right.right(1);
	  
	  
    Assert.assertTrue(3==new CountOfPathsHavingAGivenSum(18).visit(tree));
  }

	@Test
	public void testHasPathSum() {
		BSTVisitor<Integer, Void, List<Deque<Integer>>> v = new HasPathSumVisitor(121);
		v.visit(bst);
		List<Integer> expectedListFor121=Lists.newArrayList(16, 24, 28, 26, 27);
		Assert.assertEquals("\n121 is not contained in ",expectedListFor121, Lists.newArrayList(v.collection().get(0)));
		List<Integer> expectedListFor55=Lists.newArrayList(16, 8, 12, 10, 9);
		v = new HasPathSumVisitor(55);
		v.visit(bst);
		Assert.assertEquals("\n55 is not contained in {}",expectedListFor55,Lists.newArrayList(v.collection().get(0)));
	}

	final Integer[][] headToTailValues = { { 16, 8, 4, 2, 1 }, { 16, 8, 4, 2, 3 }, { 16, 8, 4, 6, 5 },
			{ 16, 8, 4, 6, 7 }, { 16, 8, 12, 10, 9 }, { 16, 8, 12, 10, 11 }, { 16, 8, 12, 14, 13 },
			{ 16, 8, 12, 14, 15 }, { 16, 24, 20, 18, 17 }, { 16, 24, 20, 18, 19 }, { 16, 24, 20, 22, 21 },
			{ 16, 24, 20, 22, 23 }, { 16, 24, 28, 26, 25 }, { 16, 24, 28, 26, 27 }, { 16, 24, 28, 30, 29 },
			{ 16, 24, 28, 30, 31, 32 } };

	@Test(timeout = 5000)
	public void testPrintVertical() {
		Boolean HEAD_TO_TAIL = true, TAIL_TO_HEAD = false;
		List<List<Integer>> headToTailList = new ArrayList<>(),
				tailToHeadList = new ArrayList<>();
		for (Integer[] a : headToTailValues) {
			val list = Arrays.asList(a);
			headToTailList.add(new ArrayList<>(list));
			// Now tail to head
			Collections.reverse(list);
			tailToHeadList.add((list));
		}
		BSTVisitor<Integer, Void, List<List<Integer>>> headToTailVisitor = PrintVerticalPathVisitor.of(HEAD_TO_TAIL);
		headToTailVisitor.visit(bst.root());
		assertEquals(headToTailList, headToTailVisitor.collection());

		BSTVisitor<Integer, Void, List<List<Integer>>> tailToHeadVisitor = PrintVerticalPathVisitor.of(TAIL_TO_HEAD);
		tailToHeadVisitor.visit(bst.root());
		assertEquals(tailToHeadList, tailToHeadVisitor.collection());
	}

	@Test(timeout = 5000)
	public void testMaxSum() {
		Deque<Integer> headToTailList = new ArrayDeque<>(Arrays.asList(16, 24, 28, 30, 31, 32));
		BSTVisitor<Integer, Integer, Deque<Integer>> headToTailVisitor = new MaxSumPathVisitor<>();
		headToTailVisitor.visit(bst.root());
		Iterator<Integer> i1 = headToTailList.iterator(), i2 = headToTailVisitor.collection().iterator();
		while (i1.hasNext() && i2.hasNext())
			assertEquals(i1.next(), i2.next());
	}

	@Test(timeout = 5000)
	public void testMirror() {
		BSTVisitor<Integer, Void, Void> mirroring = MirroringVisitor.of();
		BSTVisitor<Integer, Integer, List<Integer>> inOrderPrinter = new InOrderPrinter<Integer>();
		StringBuilder sb = new StringBuilder();
		// First reverse
		mirroring.visit(bst.root());
		inOrderPrinter.visit(bst.root());
		sb.setLength(0);
		for (int i : inOrderPrinter.collection())
			sb.append(" ").append(i);
		log.debug(sb.toString());

		org.apache.commons.lang3.ArrayUtils.reverse(x);
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

	@Test//(timeout = 5000)
	public void testSame() {
		assertTrue(bst.equals(bst));
		Tree<Integer,BSTNode<Integer>> newBst = new VanillaBST<Integer>(o->new BSTNode<>(o));
		ArrayUtils.reverse(x);
		newBst.addAll(x);
		assertFalse(bst.equals(newBst));
	}

	@Test//(timeout = 5000)
	public void testSame1() {
	  Tree<Integer,BSTNode<Integer>> oldBst = new VanillaBST<Integer>(o->new BSTNode<>(o));
	  oldBst.addAll(x);;
		assertTrue(oldBst.equals(oldBst));
		Tree<Integer,BSTNode<Integer>> newBst = new VanillaBST<Integer>(o->new BSTNode<>(o));
		newBst.addAll(x);
		assertTrue(oldBst.equals(newBst));
		BSTNode<Integer> TEN=new BSTNode<>(10);
		assertTrue(newBst.contains(newBst.root(),TEN).equals(TEN));
		newBst.remove(10);
		assertTrue(newBst.contains(10)==null);
		assertFalse(oldBst.equals(newBst));
		oldBst.remove(10);
		assertTrue(oldBst.equals(newBst));
		oldBst.remove(24);
		newBst.remove(24);
		assertTrue(oldBst.equals(newBst));

	}
}
