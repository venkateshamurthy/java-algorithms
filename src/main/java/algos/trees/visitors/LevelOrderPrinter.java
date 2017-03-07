package algos.trees.visitors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
//Using lombok annotation for log4j handle

import algos.trees.BSTNode;
import algos.trees.Tree;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author vmurthy
 * 
 */
@Data
@Accessors(fluent=true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelOrderPrinter<T extends Comparable<T>> implements BSTVisitor<T,T,List<T>> {
	static final Logger log = LogManager.getLogger(StringFormatterMessageFactory.INSTANCE);
	List<T> collection = new ArrayList<T>();
	Queue<BSTNode<T>> queue = new LinkedList<>();

	@Override public T visit(Tree<T,BSTNode<T>> t) {
		log.debug("Printing Level Order Tree");
		queue.add(t.root());
		return visit(t.root());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Element)
	 */
	@Override public T visit(BSTNode<T> e1) {
		if(e1!=null)queue.add(e1);
		while (!queue.isEmpty()) {
			BSTNode<T> e = queue.poll();
			collection.add(e.value());
			if (e.hasLeft())
				queue.add(e.left());
			if (e.hasRight())
				queue.add(e.right());
		}
		return null;
	}

}
