package com.github.venkateshamurthy.algorithms.trees;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

@Data(staticConstructor = "of")
@EqualsAndHashCode(exclude = { "parent", "left", "right" })
@Accessors(fluent = true, chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Element<T extends Comparable<T>> implements Visitable<T> {

	/** Data **/
	@Setter(onParam = @_(@NonNull))
	T data;
	@Setter(onParam = @_(@NonNull))
	Element<T> parent = this;
	@Setter(onParam = @_(@NonNull))
	Element<T> left = this;
	@Setter(onParam = @_(@NonNull))
	Element<T> right = this;

	/** Comparison Methods **/
	public int compare(Element<T> that) {
		return data.compareTo(that.data);
	}

	public boolean lt(Element<T> that) {
		return data.compareTo(that.data) < 0;
	}

	public boolean gt(Element<T> that) {
		return data.compareTo(that.data) > 0;
	}

	public boolean le(Element<T> that) {
		return data.compareTo(that.data) <= 0;
	}

	public boolean ge(Element<T> that) {
		return data.compareTo(that.data) >= 0;
	}

	/** Check Methods **/
	public boolean isBachelor() {
		return left == this && right == this;
	}

	public boolean isParent() {
		return left != this || right != this;
	}

	public boolean isZombie() {
		return left == this && right == this && parent == this;
	}

	public boolean hasLeft() {
		return left != this;
	}

	public boolean hasRight() {
		return right != this;
	}

	public boolean hasBoth() {
		return hasLeft() && hasRight();
	}

	public boolean isLeftChildOfItsParent() {
		return parent != null && this == parent.left;
	}

	public boolean isRightChildOfItsParent() {
		return parent != null && this == parent.right;
	}

	/** Get and Set methods **/
	public Element<T> becomeZombie() {
		parent.child(isRightChildOfItsParent(), this);
		parent = left = right = this;
		return this;
	}

	public Element<T> child(int cmp) {
		if (cmp == 0)
			return this;
		else
			return child(cmp > 0);
	}

	public Element<T> child(boolean right) {
		return right ? this.right : this.left;
	}

	public Element<T> child(int cmp, Element<T> child) {
		return cmp == 0 ? this : child(cmp > 0, child);
	}

	public Element<T> child(boolean right, Element<T> child) {
		if (right)
			right(child);
		else
			left(child);
		return this;
	}

	public Element<T> child() {
		return left != this ? left : right;
	} // return any child

	// Min and Max
	public Element<T> minimum() {
		Element<T> node = this;
		while (node.hasLeft())
			node = node.left();
		return node;
	}

	public Element<T> maximum() {
		Element<T> node = this;
		while (node.hasRight())
			node = node.right();
		return node;
	}

	/**
	 * Successor
	 */
	public Element<T> successor() {
		Element<T> node = right.minimum();
		if (node == this)
			do {
				node = node.parent();
			} while (node.isRightChildOfItsParent());
		return node;
	}

	/**
	 * Predecessor
	 */
	public Element<T> predecessor() {
		Element<T> node = left.maximum();
		if (node == this)
			do {
				node = node.parent();
			} while (node.isLeftChildOfItsParent());
		return node;
	}

	public int height() {
		return 1 + Math.max(hasLeft() ? left.height() : 0,
				hasRight() ? right.height() : 0);
	}

	public static <U extends Comparable<U>> Element<U> of(U data) {
		ElementBuilder<U> b = Element.builder();
		return b.data(data).build();
	}

	/**
	 * Ad a child
	 */
	public Element<T> add(Element<T> e) {
		if (!equals(e)) {
			e.parent(this);
			return child(e.gt(this), e);
		} else
			return this;
	}

	/**
	 * Attach only child to its granny
	 */
	public void attachTheOnlyChildToItsGranny() {
		if (isParent()) {
			Element<T> onlyChild = child();
			onlyChild.parent(parent);
			parent.child(isRightChildOfItsParent(), onlyChild);
		}
	}

	/**
	 * Swap Children positions
	 */
	public void swapChildren() {
		Element<T> temp = left;
		left = right;
		right = temp;
	}

	/**
	 * Rotate left of a
	 * 
	 * <pre>
	 *     p                      p
	 *     |                      |
	 *     a      ========>       b   
	 *    / \                    / \
	 *  a)   b                  a   (b
	 *      / \                / \
	 * (a,b)   (b            a)   (a,b)
	 * </pre>
	 * 
	 */
	public Element<T> rotateLeft(Element<T> a) {
		Element<T> p = a.parent();
		Element<T> b = a.right();
		Element<T> ab = b.left();
		b.parent(p);
		b.left(a);
		p.child(a.isRightChildOfItsParent(), b);
		a.parent(b);
		a.right(ab);
		ab.parent(a);
		return b;
	}

	/**
	 * Right rotate
	 * 
	 * @param b
	 * @return
	 */
	public Element<T> rotateRight(Element<T> b) {
		Element<T> p = b.parent();
		Element<T> a = b.left();
		Element<T> ab = a.right();
		a.parent(p);
		a.right(b);
		p.child(b.isRightChildOfItsParent(), a);
		b.parent(a);
		b.left(ab);
		ab.parent(b);
		return a;
	}

	/**
	 * Find
	 */
	public Element<T> find(Element<T> that) {
		int cmp = compare(that);
		if (cmp == 0)
			return this;
		else if (cmp > 0 && hasRight())
			return right.find(that);
		else if (hasLeft())
			return left.find(that);
		else
			return null;
	}

	/**
	 * Remove
	 * 
	 * @param that
	 * @return removed element
	 */
	public Element<T> remove(Element<T> that) {
		Element<T> returned = null;
		if ((returned = find(that)) != null) {
			if (returned.isBachelor())
				returned.becomeZombie();
			else {
				Element<T> successor = that.hasBoth() ? that.successor() : that;
				that.data(successor.data());
				successor.attachTheOnlyChildToItsGranny();
				if (successor.isBachelor() && successor != that)
					successor.becomeZombie();
			}
		}
		return returned;
	}

	public boolean validate(T min, T max){
		return     min.compareTo(data)<0 && max.compareTo(data)>0
				&& (hasLeft() && validate(min,left.data))
				&& (hasRight() && validate(right.data,max));
	}
	@Override
	public void accept(Visitor<T, ?, ?> visitor) {
		visitor.visit(this);
	}
}