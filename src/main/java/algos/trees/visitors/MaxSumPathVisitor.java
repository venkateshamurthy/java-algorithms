/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import algos.trees.Element;
import algos.trees.Tree;

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
public class MaxSumPathVisitor<T extends Number & Comparable<T>> implements Visitor<T, Integer, Deque<T>> {
	public static <Y extends Number & Comparable<Y>> MaxSumPathVisitor<Y> of() {
		return new MaxSumPathVisitor<Y>();
	}

	static final Logger log = LogManager.getLogger(StringFormatterMessageFactory.INSTANCE);

	Deque<T> collection = new ArrayDeque<T>();

	@NonFinal
	static Void v;

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Tree)
	 */
	@Override
	public Integer visit(Tree<T> t) {
		return visit(t.root());
	}

	@Override
	public Integer visit(Element<T> e) {
		return visit(e, 0);
	}

	private int visit(Element<T> e, int sum) {
		collection.clear();
		sum += e.value().intValue();
		if (e.isBachelor()) {
			if (sum > maxSum) {
				maxSum = sum;
				Element<T> t = e;
				collection.addFirst(t.value());
				while (t.hasParent()) {
					t=t.parent();
					collection.addFirst(t.value());
				}
			}
		} else {
			if (e.hasLeft())
				visit(e.left(), sum);
			if (e.hasRight())
				visit(e.right(), sum);
		}

		return maxSum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
	 */
	@Override
	public Integer doSomethingOnElement(Element<T> e) {
		throw new UnsupportedOperationException();
	}

	@NonFinal
	int maxSum;

}
