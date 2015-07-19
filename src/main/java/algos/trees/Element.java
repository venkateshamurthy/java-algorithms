package algos.trees;

import java.util.Comparator;

import algos.trees.visitors.Visitor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * An abstraction for a simple factory
 * @author vemurthy
 *
 * @param <Y>
 * @param <E>
 */
@Data
@Accessors(fluent=true)
@FieldDefaults(level=AccessLevel.PRIVATE,makeFinal=true)
abstract class AbstractElementFactory<Y extends Comparable<Y>, E extends Element<Y>>{
	Comparator<Y> DEFAULT_COMPARATOR = new Comparator<Y>(){
		@Override
		public int compare(Y o1, Y o2) {
			return o1.compareTo(o2);
		}
	};
	
	/** Comparator */
	Comparator<Y> comparator;
	
	/** Default constructor */
	AbstractElementFactory(){
		comparator=DEFAULT_COMPARATOR;
	}
	
	/**
	 * Constructor
	 * @param comparator an instance of Comparator<Y>
	 */
	AbstractElementFactory(Comparator<Y> comparator){
		this.comparator = comparator;
	}
	/**
	 * A method to create instances of type E
	 * @param t an instance of type Y
	 * @return an instance type E
	 */
	public abstract E create(Y t);
}


/**
 * @author vmurthy
 * 
 */
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false, of = "value")
@ToString(of = "value")
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public  class  Element<T extends Comparable<T>> {
	@NonNull Comparator<T> comparator;
	@NonNull @NonFinal T value;
	@NonFinal Element<T> parent = this, left = this, right = this;
	
	/**
	 * Left Lean Red Black tree changes to BST
	 * *** START ****
	 */
	public static Boolean RED = true, BLACK = false;
	@NonFinal Boolean color=RED;
	public boolean isRed() {return parent!=this && color==RED;}
	public boolean isBlack() {return parent!=this && color==BLACK;}
	public Boolean colorFlip() {left.color=!left.color;right.color=!right.color;return color=!color;}
	/** END of LLRB Changes **/
	
	public void accept(Visitor<T, ?, ?> visitor) {
		visitor.visit(this);
	}
	public <E extends Element<T>>  E left(){
		return (E) left;
	}	
	public <E extends Element<T>>  E right(){
		return (E) right;
	}
	public <E extends Element<T>>  E parent(){
		return (E) parent;
	}
	public <E extends Element<T>> int compare(E e) {
		return comparator.compare(value, e.value());
	}

	public <E extends Element<T>> boolean eq(E e) {
		return value.equals(e.value());
	}

	public <E extends Element<T>> boolean lt(E e) {
		return comparator.compare(value, e.value()) < 0;
	}

	public <E extends Element<T>> boolean le(E e) {
		return comparator.compare(value, e.value()) <= 0;
	}

	public <E extends Element<T>>  boolean gt(E e) {
		return comparator.compare(value, e.value()) > 0;
	}

	public <E extends Element<T>> boolean ge(E e) {
		return comparator.compare(value, e.value()) >= 0;
	}

	public boolean isParent() {
		return left != this || right != this;
	}

	public <E extends Element<T>> E getChild(int cmp) {
		if (cmp == 0)
			return (E) this;
		else
			return (E) (cmp < 0 ? left : right);
	}

	public <E extends Element<T>> E setChild(int cmp, E child) {
		if (cmp != 0) {
			Element<T> e = cmp < 0 ? left(child) : right(child);
			child.parent(this);
		}
		return (E) this;
	}

	public boolean hasLeft() {
		return left != this;
	}

	public boolean isBachelor() {
		// OMG See how happy it looks..
		return left == this && right == this;
	}
	// just a convenient, traditional named method
	public boolean isLeaf() {
		// OMG See how happy it looks..
		return isBachelor();
	}

	public boolean hasRight() {
		return right != this;
	}

	public boolean isLeftChildOfItsParent() {
		return parent != null && parent.left == this;
	}

	public boolean isRightChildOfItsParent() {
		return parent != null && parent.right == this;
	}

	/**
	 * Obtains the node with the smallest value starting from this.
	 * 
	 * @return The minimum.
	 */
	public <E extends Element<T>> E minimum() {
		E node = (E) this;

		while (node.left() != node)
			node = node.left();

		return node;
	}

	/**
	 * Obtains the node with the largest value starting from this.
	 * 
	 * @return The maximum.
	 */
	public <E extends Element<T>> E maximum() {
		E node = (E) this;

		while (node.right() != node)
			node = node.right();

		return node;
	}

	/**
	 * Obtains the node with the next largest value starting from this.
	 * 
	 * @return The successor; or <code>null</code>.
	 */
	public <E extends Element<T>> E successor() {
		if (right != this)
			return right.minimum();

		E node = (E) this;

		while (node.isRightChildOfItsParent()) {
			node = node.parent();
		}

		return node.parent();
	}

	/**
	 * Obtains the node with the next smallest value starting from this.
	 * 
	 * @return The predecessor; or <code>null</code>.
	 */
	public <E extends Element<T>> E  predecessor() {
		if (left != this)
			return left.maximum();

		// else reach a top parent that is a left child
		E node = (E) this;

		while (node.isLeftChildOfItsParent()) {
			node = node.parent();
		}

		return node.parent();
	}

	public <E extends Element<T>> E  add(Factory<T> factory, @NonNull T t) {
		E e = (E)factory.create( t);
		e.parent(this);
		if (lt(e))
			left = e;
		else if (gt(e))
			right = e;
		return e;
	}

	/**
	 * Check zombie
	 * 
	 * @return
	 */
	public boolean isZombie() {
		return parent == this && left == this && right == this;
	}

	/**
	 * Make yourself zombie and also ensure parent link is broken
	 */
	public void becomeZombie() {
		if (isLeftChildOfItsParent())
			parent.left(parent);
		else
			parent.right(parent);
		this.parent = this.left = this.right = this;
	}

	/**
	 * Attach the only Child to its Granny
	 */
	public void attachTheOnlyChildToItsGranny() {
		if (isBachelor()) // well, Get Married soon!
			return;
		// Attach only child either to left or right of granny
		if (isLeftChildOfItsParent())
			parent.left(getOnlyChild());
		else
			parent.right(getOnlyChild());
		// cild to granny
		getOnlyChild().parent(parent);
	}

	/**
	 * Return the only child
	 * 
	 * @return
	 */
	public <E extends Element<T>> E getOnlyChild() {
		return (E) (left != this ? left : right);
	}

	/**
	 * Does it have both children
	 * 
	 * @return
	 */
	public boolean hasBoth() {
		return left != this && right != this;
	}

	/**
	 * Simple swap
	 */
	public void swapChildrenPosition() {
		val temp = left;
		left = right;
		right = temp;
	}
	/**
	 * A Factory class to create instances
	 * @author vemurthy
	 *
	 * @param <Y>
	 */
	public static class Factory<Y extends Comparable<Y>> extends AbstractElementFactory<Y,Element<Y>>{

		public Factory() {
			super();
		}
		public Factory(Comparator<Y> comparator) {
			super(comparator);
		}

		@Override
		public Element<Y> create(Y t) {
			return new Element<Y>(comparator(),t);
		}
	}
}
