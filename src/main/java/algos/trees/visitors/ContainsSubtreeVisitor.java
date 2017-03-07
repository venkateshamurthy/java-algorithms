package algos.trees.visitors;

import java.util.Objects;

import algos.trees.BSTNode;
import algos.trees.Tree;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
@Data
@Accessors(fluent=true)
public class ContainsSubtreeVisitor implements BSTVisitor<Integer, Boolean, Boolean> {
  @NonNull Tree<Integer, BSTNode<Integer>> firstTree, secondTree;
  Boolean collection;
  
  @Override
  public Boolean visit(BSTNode<Integer> e) {
    return collection=firstTree.sameNodes(firstTree.root(), secondTree.root());
  }

/**
 * <pre>
 * default boolean sameNodes(N thisNode, N thatNode) {
    boolean result = Objects.equals(thisNode.value(), thatNode.value());
    boolean leftResult= result &&
        (!thisNode.hasLeft() && !thatNode.hasLeft() ||
        thisNode.hasLeft() && thatNode.hasLeft() && sameNodes(thisNode.left,thatNode.left));
    boolean rightResult= leftResult  && 
        (!thisNode.hasRight() && !thatNode.hasRight() ||
        thisNode.hasRight() && thatNode.hasRight() && sameNodes(thisNode.right,thatNode.right));
    return result = result && leftResult && rightResult;
  }</pre>
 */
}
