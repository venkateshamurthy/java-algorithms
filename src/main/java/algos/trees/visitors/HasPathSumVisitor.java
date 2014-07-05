/**
 * 
 */
package algos.trees.visitors;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.springframework.util.Assert;

import algos.trees.Element;
import algos.trees.Tree;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data(staticConstructor = "of")
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class HasPathSumVisitor implements Visitor<Integer, Boolean,Integer> {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	Integer targetSum;
	@Getter AtomicInteger runningSum;

	/**
	 * Just a wrapper builder with asserts. Be sure to use
	 * 
	 * @Data(staticConstructor="builder")
	 * 
	 * @author vmurthy
	 * 
	 */
	@Data(staticConstructor = "builder")
	@EqualsAndHashCode(callSuper = false)
	public static class Builder extends HasPathSumVisitorBuilder {
		@Override public HasPathSumVisitor build() {
			Assert.notNull(super.tree, "Please make sure to set the tree");
			super.maximum = super.tree.root().maximum();
			Assert.notNull(super.targetSum,
					"Please make sure to set the targetSum");
			// Just set runningSum to default if its null.
			if (super.runningSum == null)
				super.runningSum = new AtomicInteger(super.targetSum);
			Assert.state(super.runningSum.get() == super.targetSum,
					" Running Sum cannot be different than targetSum");
			return super.build();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Tree)
	 */
	@Override public Boolean visit(Tree<Integer> t) {
		// TODO Auto-generated method stub
		return visit(t.root());
	}

	Tree<Integer> tree;
	Element<Integer> maximum;

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Element)
	 */
	@Override public Boolean visit(Element<Integer> e) {

		log.debug(e.value() + " s=" + runningSum.get());
		if (e.isBachelor()) {
			return (runningSum.get() <= 0);
		} else {
			// otherwise check both subtrees
			doSomethingOnElement(e);
			return (e.hasLeft() && visit(e.left()) || e.hasRight()
					&& visit(e.right()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
	 */
	@Override public Boolean doSomethingOnElement(Element<Integer> e) {
		runningSum.getAndAdd(-e.value());
		return runningSum().get()<=0;
	}

	/* (non-Javadoc)
	 * @see algos.trees.Visitor#getCollection()
	 */
	@Override public Integer collection() {
		return runningSum.get();
	}
}
