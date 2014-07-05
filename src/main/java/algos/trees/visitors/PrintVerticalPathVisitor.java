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
import lombok.experimental.Builder;
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
@Data//(staticConstructor="of")
@Accessors(fluent = true,chain=true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrintVerticalPathVisitor<T extends Comparable<T>> implements
		Visitor<T, Void,List<List<T>> > {
    public static <Y extends Comparable<Y>> PrintVerticalPathVisitor<Y> of(Boolean headToTail) {
        return new PrintVerticalPathVisitor<Y>(headToTail);
    }
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	Deque<T> queue = new ArrayDeque<T>();
	
	//We need this collection Deque list for outside access so maintain same name
	List<List<T>> collection=new ArrayList<List<T>>();
	
	Boolean headToTail;
	@NonFinal static Void v;

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Tree)
	 */
	@Override public Void visit(Tree<T> t) {
		visit(t.root());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Element)
	 */
	@Override public Void visit(Element<T> e) {
		if (headToTail)
			queue.addLast(e.value());
		else
			queue.addFirst(e.value());
		if (e.isBachelor())
			printStack();
		else {
			if (e.hasLeft())
				visit(e.left());
			if (e.hasRight())
				visit(e.right());
		}
		if (headToTail)
			queue.removeLast();
		else
			queue.removeFirst();
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
	 */
	@Override public Void doSomethingOnElement(Element<T> e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 */
	private void printStack() {
		collection.add(new ArrayList<T>(queue));
		StringBuilder sb = new StringBuilder();
		for (val t : queue)
			sb.append(t).append(" ");
		log.debug(sb.toString());
	}

}
