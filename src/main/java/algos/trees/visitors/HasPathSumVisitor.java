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
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data//(staticConstructor = "of")
@Accessors(fluent = true)
public class HasPathSumVisitor implements BSTVisitor<Integer, Boolean, List<Deque<Integer>>> {
	int targetSum;
	List<Deque<Integer>> collection = new ArrayList<Deque<Integer>>();
	Deque<Integer> temp = new ArrayDeque<>();
	@Override
	public  Boolean visit(BSTNode<Integer> e) {
		return visit(e,targetSum);
	}

	private boolean visit(final BSTNode<Integer> e, final int sum) {
		final int amount=sum - e.value();
		temp.addFirst(e.value());
		if (e.isBachelor()&&  amount<=0) {
		   CollectionUtils.addAll(collection, temp.iterator());
		   return true;
		}
		temp.removeFirst();
		return e.hasLeft() && visit(e.left(),amount) || e.hasRight() && visit(e.right(),amount);
	}

	public HasPathSumVisitor(int targetSum) {
		this.targetSum = targetSum;
	}
}
