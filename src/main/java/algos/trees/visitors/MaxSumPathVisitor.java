/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayDeque;
import java.util.Deque;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Data
// (staticConstructor="of")
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaxSumPathVisitor<T extends Number & Comparable<T>> implements BSTVisitor<T, Integer, Deque<T>> {

	Deque<T> collection = new ArrayDeque<T>();


	@Override
	public Integer visit(BSTNode<T> e) {
		return visit(e, 0);
	}

	private int visit(BSTNode<T> e, int sum) {
		collection.addFirst(e.value());
		sum += e.value().intValue();
		if (e.isBachelor()) {
			if (sum > maxSum) {
				maxSum = sum;
				BSTNode<T> t = e;
				while (t.hasParent()) {
					t=t.parent();
				}
			}
		} else {
			if (e.hasLeft())
				visit(e.left(), sum);
			if (e.hasRight())
				visit(e.right(), sum);
		}
		collection.removeFirst();
		return maxSum;
	}


	@NonFinal
	int maxSum;

}
