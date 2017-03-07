package algos.trees.visitors;

import algos.trees.BSTNode;
import algos.trees.Tree;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

@Data
@Accessors(fluent = true)
public class FirstCommonAncestor implements BSTVisitor<Integer, BSTNode<Integer>, BSTNode<Integer>> {
  BSTNode<Integer> Null = new BSTNode<>(null);
  @NonFinal
  BSTNode<Integer> collection;
  BSTNode<Integer> first, second;
  
  @Override
  public BSTNode<Integer> visit(BSTNode<Integer> root){
    return collection=visit(root, first, second);
  }
  
  private BSTNode<Integer> visit(BSTNode<Integer> root, BSTNode<Integer> p, BSTNode<Integer> q) {

    if (root.eq(p) || root.eq(q)) {
      return root;
    }

    if (root.hasLeft() && root.gt(p) && root.gt(q)) {
      return visit(root.left(), p, q);
    } else if (root.hasRight() && root.lt(p) && root.lt(q)) {
      return visit(root.right(), p, q);
    } else {
      return root;
    }
  }

}
