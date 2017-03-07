/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import algos.trees.BSTNode;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data // (staticConstructor = "of")
@Accessors(fluent = true)
public class HasPathSumVisitor implements BSTVisitor<Integer, Void, List<Deque<Integer>>> {
  int targetSum;
  List<Deque<Integer>> collection = new ArrayList<Deque<Integer>>();
  Deque<Integer> temp = new ArrayDeque<>();
  static Void v_o_i_d;

  @Override
  public Void visit(BSTNode<Integer> e) {
    log.info("{}", e);
    return visit(e, targetSum);
  }

  private Void visit(final BSTNode<Integer> e, final int sum) {
    final int amount = sum - e.value();
    temp.addLast(e.value());
    if (e.isBachelor()) {
      if (amount == 0)
        collection.add(new ArrayDeque<>(temp));
    } else {
      if (e.hasLeft())
        visit(e.left(), amount);
      if (e.hasRight())
        visit(e.right(), amount);
    }
    temp.removeLast();
    return v_o_i_d;
  }

  public HasPathSumVisitor(int targetSum) {
    this.targetSum = targetSum;
  }
}
