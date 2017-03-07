package algos.trees.visitors;

import java.util.HashMap;
import java.util.Map;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountOfPathsHavingAGivenSum implements BSTVisitor<Integer, Integer, Integer> {

  Map<Integer, Integer> pathCount = new HashMap<>();
  int targetSum;
  @NonFinal
  Integer collection = 0;

  @Override
  public Integer visit(BSTNode<Integer> e) {
    return visit(e, 0);
  }

  private int visit(BSTNode<Integer> e, int sum) {
    sum += e.value();
    
    int totalPaths = pathCount.getOrDefault(targetSum - sum, 0) + targetSum == sum ? 1 : 0;
    
    pathCount.put(sum, pathCount.getOrDefault(sum, 0) + 1);
    totalPaths += e.hasLeft()? visit(e.left(), sum):0;
    totalPaths += e.hasRight()?visit(e.right(), sum):0;
    pathCount.put(sum, pathCount.getOrDefault(sum, 0) - 1);
    return collection = totalPaths;
  }

}
