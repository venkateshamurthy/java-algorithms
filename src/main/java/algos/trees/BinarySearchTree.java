/**
 * 
 */
package algos.trees;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import org.apache.commons.math3.util.FastMath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import algos.trees.visitors.Visitor;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Data
// (staticConstructor="of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false, of = "value")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {
	public static <Y extends Comparable<Y>> BinarySearchTree<Y> of() {
		return new BinarySearchTree<Y>();
	}

	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	Comparator<T> comparator = new NaturalComparator<T>();
	Element<T> root = null;
	boolean iterative = false;
	BST bst = new BST();
	Element.Factory<T, Element<T>> factory = new Element.Factory<>(comparator);

	@Override
	public Element<T> add(T value) {
		Element<T> t = factory.create(value);
		if (root == null)
			root = t;
		size.incrementAndGet();
		return iterative ? bst.iterativeAdd(root, t) : bst
				.recursiveAdd(root, t);
	}

	/**
	 * Remove
	 */
	@Override
	public Element<T> remove(T value) {
		return bst.remove(value);
	}

	@Override
	public void clear() {
		root.left(root);
		root.right(root);
		size.set(0);
	}

	@Override
	public Element<T> contains(T tValue) {
		return bst.contains(tValue);
	}

	@Override
	public int size() {
		// return new SizeVisitor<T>().visit(root);
		return bst.size(root);
		// Tree<T> t = this;
		// this.accept((Visitor<T, ?, ?>) SizeVisitor.builder().build());
	}

	@Override
	public boolean validate(T min, T max) {
		return (root == null) ? true : bst.validate(root, min, max);
	}

	@Override
	public int height() {
		return bst.height(root);
	}

	final AtomicInteger size = new AtomicInteger(0);

	public void addAll(T[] t, int start, int end) {
		if (start <= end) {
			int mid = start + (end - start) / 2;
			add(t[mid]);
			addAll(t, start, mid - 1);
			addAll(t, mid + 1, end);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Tree#accept(algos.trees.Visitor)
	 */
	@Override
	public void accept(Visitor<T, ?, ?> visitor) {
		visitor.visit(this);
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object another) {
		log.debug("Equals..check");
		if (another.getClass().isInstance(this))
			return sameTree(this.root(), ((Tree<T>) another).root());
		else
			return false;

	}

	/**
	 * @param root2
	 * @param root3
	 * @return
	 */
	private boolean sameTree(Element<T> u, Element<T> v) {
		boolean valueEquals = u.eq(v);
		if (valueEquals) {
			if (u.isBachelor() && v.isBachelor())
				return true;
			else if (u.hasBoth() && v.hasBoth())
				return sameTree(u.left(), v.left())
						&& sameTree(u.right(), v.right());
			else if (!u.hasLeft() && !v.hasLeft())
				return sameTree(u.right(), v.right());
			else if (!u.hasRight() && !v.hasRight())
				return sameTree(u.left(), v.left());
		}
		return false;
	}

	/**
	 * Red black tree
	 */
	protected class RBT extends BST {

		protected Element<T> remove(Element<T> h, T tValue) {
			Element<T> t = factory.create(tValue);
			if (t.lt(h)) {
				h = moveRedLeft(h);
				h.left(remove(h.left(), tValue));
			} else {
				if (h.left().isRed())
					h = rotateRight(h);
				if (t.eq(h) && h.right() == h)
					return null;
				h = moveRedRight(h);
				if (t.eq(h)) {
					h = recursiveFind(h.right(), h.right().minimum());
					h = h.right().minimum();
					h.right(deleteMin(h.right()));
				} else
					h.right(remove(h.right(), tValue));
			}
			return fixUp(h);
		}

		public void deleteMin() {
			root = deleteMin(root);
			root.color(Element.BLACK);
		}

		private Element<T> deleteMin(Element<T> h) {
			if (h.left() == null)
				return null;

			h = moveRedLeft(h);

			h.left(deleteMin(h.left()));

			return fixUp(h);
		}

		private Element<T> fixUp(Element<T> h) {
			if (h.right().isRed())
				h = rotateLeft(h);

			if (h.left().isRed() && h.left().left().isRed())
				h = rotateRight(h);

			if (h.left().isRed() && h.right().isRed())
				h.colorFlip();

			return h;// setN(h)
		}

		/**
		 * Move Red Left
		 * 
		 * <pre>
		 *                                                                                  Rz                 Rz
		 *                                                                                 / \                / \
		 *         R(h)               B(h)                 B(h)                           /   \              /   \
		 *        / \    Flip color  / \      RRotate(x)  / \      Left Rotate(h)        /     \            /     \
		 *       B   x(C)   ===>    R   x(!C) ========>  R   Rz    ===============>    Bh       x(!C) ==>  Rh      x(!C)
		 *      /   / \            /   / \              /   / \                        / \     / \        / \     / \  
		 *     B   Rz  y          B   Rz  y            B  <Rz  x(!C)                  R  <Rz >Rz         B  <Bz >Rz
		 *        / \                / \                      / \                    /                  /
		 *     <Rz  >Rz           <Rz  >Rz                 >Rz   y                  B                  B
		 * 
		 * 
		 * </pre>
		 * 
		 * @param h
		 * @return
		 */
		private Element<T> moveRedLeft(Element<T> h) {
			if (h.isRed() && h.left().isBlack() && h.left().left().isBlack()) {
				h.colorFlip();
				val x = h.right();
				if (x.left().isRed()) {
					h.right(rotateRight(x));
					h = rotateLeft(h);
					h.colorFlip();
				}
			}
			return h;
		}

		/**
		 * Move Red Right
		 * 
		 * <pre>
		 * 
		 * 
		 *         R(h)               B(h)                !Cx                             
		 *        / \    Flip color  / \      RRotate(h)  / \      
		 *      Cx   B   ========>!Cx   R     ========>  R   B(h)     
		 *      / \ / \            /   / \                  / \                      
		 *     R       y          R   Bz  y            B       R                     
		 *                           / \                      / \                  
		 *                        <z   >z                        y
		 * 
		 * 
		 * </pre>
		 * 
		 * @param h
		 * @return
		 */
		private Element<T> moveRedRight(Element<T> h) {
			if (h.isRed() && h.right().isBlack() && h.right().left().isBlack()) {
				h.colorFlip();
				if (h.left().left().isRed()) {
					h = rotateRight(h);
					h.colorFlip();
				}
			}
			return h;
		}

		protected Element<T> recursiveAdd(Element<T> e, Element<T> t) {
			if (e.left().isRed() && e.right().isRed())
				e.colorFlip();
			super.recursiveAdd(e, t);
			if (e.right().isRed() && !e.left().isRed())
				e = rotateLeft(e);
			if (e.left().isRed() && e.left().left().isRed())
				e = rotateRight(e);

			return t;
		}

		/**
		 * Rotate left of a
		 * 
		 * <pre>
		 * 
		 *     p                   p  
		 *     |                   |
		 *     a                   b
		 *    / \     =====>      / \	
		 *  a)   b               a   (b
		 *      / \             / \
		 * (a,b)   (b         a)  (a,b)
		 * 
		 * </pre>
		 * <p>
		 * The illustration is about rotating a left around b. the round braces
		 * a) indicate range excluding a and (b indicates everything beyond b.
		 * The (a,b) indicates everything between a and b excluding a and b
		 * itself.
		 * 
		 * <pre>
		 * So first take     b = a.right;
		 * Next    set a.right = b.left
		 * Next    set b.left  = a (Apply the color)
		 * Next    set     b.p = a.p
		 * Next            a.p = b
		 * </pre>
		 * 
		 * @param a
		 * @return b
		 */

		protected Element<T> rotateLeft(Element<T> a) {
			val b = a.right();
			a.right(b.left());
			b.left(a).color(a.color());
			a.color(Element.RED);
			b.parent(a.parent());
			if (a.isLeftChildOfItsParent())
				a.parent().left(b);
			else
				a.parent().right(b);
			a.parent(b);
			return b;
		}

		/**
		 * Rotate left of a
		 * 
		 * <pre>
		 * 
		 *      p              p       
		 *      |              |     
		 *      b              a     
		 *     / \   ====>    / \    	
		 *    a   (b        a)   b   
		 *   / \                / \  
		 * a)   (a,b)      (a,b)   (b
		 * 
		 * </pre>
		 * <p>
		 * The illustration is about rotating a left around b. the round braces
		 * a) indicate range excluding a and (b indicates everything beyond b.
		 * The (a,b) indicates everything between a and b excluding a and b
		 * itself.
		 * 
		 * <pre>
		 * So first take     a = b.left;
		 * Next    set  b.left = a.right;
		 * Next    set  a.right= b (Apply the color)
		 * Next    set     a.p = b.p
		 * Next            b.p = a
		 * </pre>
		 * 
		 * @param b
		 * @return a
		 */

		protected Element<T> rotateRight(Element<T> b) {
			val a = b.left();
			b.left(a.right());
			a.right(b).color(b.color());
			b.color(Element.RED);
			a.parent(b.parent());
			if (b.isLeftChildOfItsParent())
				b.parent().left(a);
			else
				b.parent().right(a);
			b.parent(a);
			return a;
		}

	}

	/**
	 * Vanilla BST
	 * 
	 * @author vmurthy
	 * 
	 */
	@Data()
	private class BST {
		protected Element<T> iterativeAdd(Element<T> e, final Element<T> t) {
			if (root != null) {
				Element<T> next = e;
				int cmp = 0;
				do {
					e = next;
					cmp = t.compare(e);
					if (cmp > 0 && e.hasRight())
						next = e.right();
					else if (cmp < 0 && e.hasLeft())
						next = e.left();
				} while (next != e);
				next.setChild(cmp, t);
				t.parent(next);
			}
			return t;
		}

		protected Element<T> recursiveAdd(Element<T> e, Element<T> t) {
			if (root != null) {

				int cmp = 0;
				cmp = t.compare(e);
				if (cmp > 0 && e.hasRight())
					return recursiveAdd(e.right(), t);
				else if (cmp < 0 && e.hasLeft())
					return recursiveAdd(e.left(), t);
				e.setChild(cmp, t);
				t.parent(e);

			}
			return t;
		}

		protected Element<T> remove(Element<T> e, final T value) {
			Element<T> toBeReturned = factory.create(value);
			Element<T> t = recursiveFind(e, toBeReturned);
			if (t == null)
				return null;
			// log.debug("Size before decrement:" + size.get());
			size.decrementAndGet();

			if (t.isBachelor())
				t.becomeZombie();
			else {
				Element<T> successor = t.hasBoth() ? t.successor() : t;
				t.value(successor.value());
				successor.attachTheOnlyChildToItsGranny();
				if (successor.isBachelor() && successor != t)
					successor.becomeZombie();
			}
			return toBeReturned;
		}

		protected Element<T> remove(final T value) {
			return remove(root, value);
		}

		protected Element<T> recursiveFind(Element<T> e, Element<T> t) {
			if (e == null)
				return null;
			int cmp = t.compare(e);
			if (cmp == 0)
				return e;
			else if (cmp < 0 && e.hasLeft())
				return recursiveFind(e.left(), t);
			else if (cmp > 0 && e.hasRight())
				return recursiveFind(e.right(), t);
			else
				return null;
		}

		@SuppressWarnings("unused")
		protected Element<T> iterativeFind(Element<T> root, Element<T> t) {
			Element<T> e = root;
			if (e == null)
				return null;
			do {
				int cmp = t.compare(e);
				if (cmp == 0)
					return e;
				else if (cmp > 0 && e.hasRight())
					e = e.right();
				else if (cmp < 0 && e.hasLeft())
					e = e.left();
				else
					break;
			} while (true);
			return null;
		}

		protected Element<T> contains(T tValue) {
			return recursiveFind(root, factory.create(tValue));
		}

		protected int size(Element<T> e) {

			return 1 + (e.hasLeft() ? size(e.left()) : 0)
					+ (e.hasRight() ? size(e.right()) : 0);

		}

		protected int height(Element<T> e) {
			return 1 + FastMath.max(e.hasLeft() ? height(e.left()) : 0,
					e.hasRight() ? height(e.right()) : 0);
		}

		protected boolean validate(Element<T> e, T min, T max) {
			Element<T> MIN = factory.create(min), MAX = factory.create(max);
			boolean result = true;
			if (result && e.hasLeft())
				result = e.left().gt(MIN) && validate(e.left(), min, e.value());
			if (result && e.hasRight())
				result = e.right().lt(MAX)
						&& validate(e.right(), e.value(), max);
			return result;
		}

	}
}
