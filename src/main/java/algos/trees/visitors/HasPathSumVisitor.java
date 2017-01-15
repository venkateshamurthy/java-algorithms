/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import algos.trees.Element;
import algos.trees.Tree;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class HasPathSumVisitor implements Visitor<Integer, Boolean, Deque<Integer>> {
	Integer targetSum;
	Deque<Integer> collection = new ArrayDeque<>();

	@Override
	public Boolean visit(Tree<Integer> t) {
		return visit(t.root());
	}

	@Override
	public  Boolean visit(Element<Integer> e) {
		return visit(e,targetSum);
	}

	private Boolean visit(final Element<Integer> e, final Integer sum) {
		final Integer amount=sum-e.value();
		collection.clear();
		if (e.isBachelor()) {
			boolean exists = amount <= 0;
			Element<Integer> t = e;
			collection.addFirst(t.value());
			while(t.hasParent()) {
				t=t.parent();
				collection.addFirst(t.value());
			}
			return exists;
		}
		return e.hasLeft() && visit(e.left(),amount) || e.hasRight() && visit(e.right(),amount);
	}

	@Override
	public Boolean doSomethingOnElement(Element<Integer> e) {
		return null;
	}

	public HasPathSumVisitor(int targetSum) {
		this.targetSum = targetSum;
	}
}
