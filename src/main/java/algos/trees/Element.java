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
	public Element<T> left(){
		return left;
	}	
	public Element<T> right(){
		return  right;
	}
	public Element<T> parent(){
		return  parent;
	}
	public  int compare(Element<T>e) {
		return comparator.compare(value, e.value());
	}

	public  boolean eq(Element<T>e) {
		return value.equals(e.value());
	}

	public  boolean lt(Element<T>e) {
		return comparator.compare(value, e.value()) < 0;
	}

	public  boolean le(Element<T>e) {
		return comparator.compare(value, e.value()) <= 0;
	}

	public   boolean gt(Element<T>e) {
		return comparator.compare(value, e.value()) > 0;
	}

	public  boolean ge(Element<T>e) {
		return comparator.compare(value, e.value()) >= 0;
	}

	public boolean isParent() {
		return left != this || right != this;
	}

	public  Element<T>getChild(int cmp) {
		if (cmp == 0)
			return  this;
		else
			return  (cmp < 0 ? left : right);
	}

	public  Element<T>setChild(int cmp, Element<T>child) {
		if (cmp != 0) {
			Element<T> e= cmp < 0 ? left(child) : right(child);
			child.parent(this);
		}
		return  this;
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
	public  Element<T>minimum() {
		Element<T>node =  this;

		while (node.left() != node)
			node = node.left();

		return node;
	}

	/**
	 * Obtains the node with the largest value starting from this.
	 * 
	 * @return The maximum.
	 */
	public  Element<T>maximum() {
		Element<T>node =  this;

		while (node.right() != node)
			node = node.right();

		return node;
	}

	/**
	 * Obtains the node with the next largest value starting from this.
	 * 
	 * @return The successor; or <code>null</code>.
	 */
	public  Element<T>successor() {
		if (right != this)
			return right.minimum();

		Element<T>node =  this;

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
	public  Element<T> predecessor() {
		if (left != this)
			return left.maximum();

		// else reach a top parent that is a left child
		Element<T>node =  this;

		while (node.isLeftChildOfItsParent()) {
			node = node.parent();
		}

		return node.parent();
	}

	public <E extends Element<T>> E add(Factory<T, E> factory, @NonNull T t) {
		E e = factory.create( t);
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
	public  Element<T>getOnlyChild() {
		return  (left != this ? left : right);
	}

	/**
	 * Does it have both children
	 * 
	 * @return
	 */
	public boolean hasBoth() {
		return left != this && right != this;
	}

	public boolean hasParent() {
		return parent != this;
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
	public static class Factory<Y extends Comparable<Y>, E extends Element<Y>> extends AbstractElementFactory<Y,E>{

		public Factory() {
			super();
		}
		public Factory(Comparator<Y> comparator) {
			super(comparator);
		}

		@Override
		public E create(Y t) {
			return (E)new Element<Y>(comparator(),t);
		}
	}
}
