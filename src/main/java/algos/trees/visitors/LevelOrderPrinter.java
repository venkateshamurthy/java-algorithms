/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
//Using lombok annotation for log4j handle

import algos.trees.Element;
import algos.trees.Tree;

/**
 * @author vmurthy
 * 
 */
@Data
@Accessors(fluent=true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelOrderPrinter<T extends Comparable<T>> implements Visitor<T,T,List<T>> {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	List<T> collection = new ArrayList<T>();
	Queue<Element<T>> queue = new LinkedList<Element<T>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Tree)
	 */
	@Override public T visit(Tree<T> t) {
		log.debug("Printing Level Order Tree");
		queue.add(t.root());
		return visit(t.root());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Element)
	 */
	@Override public T visit(Element<T> e1) {
		if(e1!=null)queue.add(e1);
		while (!queue.isEmpty()) {
			Element<T> e = queue.poll();
			T t = doSomethingOnElement(e);
			if (e.hasLeft())
				queue.add(e.left());
			if (e.hasRight())
				queue.add(e.right());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
	 */
	@Override public T doSomethingOnElement(Element<T> e) {
		T value = e.value();
		collection.add(value);
		return value;
	}

}
