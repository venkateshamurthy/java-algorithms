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
	public BSTNode<T> visit(BSTNode<T> root) {
	  if (root.gt(first) && root.gt(second) && root.hasLeft())
      return visit(root.left());
    else if (root.lt(first) && root.lt(second) && root.hasRight())
      return visit(root.right());
    //Just ensure 
    return root.ge(second) && root.le(first) ?  root : Null;
	}
}
