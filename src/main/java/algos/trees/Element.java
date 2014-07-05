package algos.trees;

import java.util.Comparator;

import algos.trees.visitors.Visitor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * @author vmurthy
 * 
 */
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false, of = "value")
@ToString(of = "value")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Element<T extends Comparable<T>> {
    public static <Y extends Comparable<Y>> Element<Y> of(Comparator<Y> comparator,Y t){
        return new Element<Y>(comparator, t);
    }
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

	@NonNull Comparator<T> comparator;
	@NonNull @NonFinal T value;
	@NonFinal Element<T> parent = this, left = this, right = this;
	
	
	public int compare(Element<T> e) {
		return comparator.compare(value, e.value());
	}

	public boolean eq(Element<T> e) {
		return value.equals(e.value());
	}

	public boolean lt(Element<T> e) {
		return comparator.compare(value, e.value()) < 0;
	}

	public boolean le(Element<T> e) {
		return comparator.compare(value, e.value()) <= 0;
	}

	public boolean gt(Element<T> e) {
		return comparator.compare(value, e.value()) > 0;
	}

	public boolean ge(Element<T> e) {
		return comparator.compare(value, e.value()) >= 0;
	}

	public boolean isParent() {
		return left != this || right != this;
	}

	public Element<T> getChild(int cmp) {
		if (cmp == 0)
			return this;
		else
			return cmp < 0 ? left : right;
	}

	public Element<T> setChild(int cmp, Element<T> child) {
		if (cmp != 0) {
			Element<T> e = cmp < 0 ? left(child) : right(child);
		}
		return this;
	}

	public boolean hasLeft() {
		return left != this;
	}

	public boolean isBachelor() {
		// OMG See how happy it looks..
		return left == this && right == this;
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
	public Element<T> minimum() {
		Element<T> node = this;

		while (node.left() != node)
			node = node.left();

		return node;
	}

	/**
	 * Obtains the node with the largest value starting from this.
	 * 
	 * @return The maximum.
	 */
	public Element<T> maximum() {
		Element<T> node = this;

		while (node.right() != node)
			node = node.right();

		return node;
	}

	/**
	 * Obtains the node with the next largest value starting from this.
	 * 
	 * @return The successor; or <code>null</code>.
	 */
	public Element<T> successor() {
		if (right != this)
			return right.minimum();

		Element<T> node = this;

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
	public Element<T> predecessor() {
		if (left != this)
			return left.maximum();

		// else reach a top parent that is a left child
		Element<T> node = this;

		while (node.isLeftChildOfItsParent()) {
			node = node.parent();
		}

		return node.parent();
	}

	public Element<T> add(@NonNull Comparator<T> c, @NonNull T t) {
		Element<T> e = of(c, t);
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
	public Element<T> getOnlyChild() {
		return left != this ? left : right;
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
	 * 
	 */
	public void swapChildrenPosition() {
		val temp = left;
		left = right;
		right = temp;
	}

}
