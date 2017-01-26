package algos.trees.visitors;

import algos.trees.BSTNode;
import algos.trees.NodeFactory;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class LeastCommonAncestor<T extends Comparable<T>> implements BSTVisitor<T,BSTNode<T> , Void> {
	private NodeFactory<T, BSTNode<T>> factory = (s)->new BSTNode<T>(s);
	private BSTNode<T> Null = factory.create(null);
	private BSTNode<T> first;
	private BSTNode<T> second;
	private Void collection;

	LeastCommonAncestor(T first, T second) {
		this.first = factory.create(first);
		this.second = factory.create(second);
	}

	@Override
	public BSTNode<T> visit(BSTNode<T> element) {
	  BSTNode<T> e = element;
		if (e.eq(first) || e.eq(second))
			return e;
		BSTNode<T> left = e.hasLeft() ? visit(e.left()) : Null;
		BSTNode<T> right = e.hasRight() ? visit(e.right()) : Null;
		if (left != Null && right != Null)
			return e;
		return left != Null ? left : right;
	}

	private BSTNode<T> visitInBst(BSTNode<T> root) {
		if (root.gt(first) && root.gt(second) && root.hasLeft())
			return visitInBst(root.left());
		else if (root.lt(first) && root.lt(second) && root.hasRight())
			return visitInBst(root.right());
		//Just ensure 
		return root.ge(second) && root.le(first) ?  root : Null;
	}

}
