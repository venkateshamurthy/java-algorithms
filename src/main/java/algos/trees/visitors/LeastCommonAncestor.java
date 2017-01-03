package algos.trees.visitors;

import algos.trees.Element;
import algos.trees.Tree;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class LeastCommonAncestor<T extends Comparable<T>, E extends Element<T>> implements Visitor<T, E, Void> {
	private Element.Factory<T, E> factory = new Element.Factory<>();
	private E Null = factory.create(null);
	private E first;
	private E second;
	private Void collection;

	LeastCommonAncestor(T first, T second) {
		this.first = factory.create(first);
		this.second = factory.create(second);
	}

	@Override
	public E visit(Tree<T> t) {
		return visit(t.root());
	}

	@Override
	public E visit(Element<T> element) {
		E e = (E) element;
		if (e.eq(first) || e.eq(second))
			return e;
		E left = e.hasLeft() ? visit(e.left()) : Null;
		E right = e.hasRight() ? visit(e.right()) : Null;
		if (left != Null && right != Null)
			return e;
		return left != Null ? left : right;
	}

	private E visitInBst(Element<T> root) {
		if (root.gt(first) && root.gt(second) && root.hasLeft())
			return visitInBst(root.left());
		else if (root.lt(first) && root.lt(second) && root.hasRight())
			return visitInBst(root.right());
		//Just ensure 
		return root.ge(second) && root.le(first) ? (E) root : Null;
	}

	@Override
	public E doSomethingOnElement(Element<T> e) {
		return null;
	}

}
