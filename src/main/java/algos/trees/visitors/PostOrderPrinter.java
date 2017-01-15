/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import algos.trees.Element;
import algos.trees.Tree;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@Accessors(fluent=true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostOrderPrinter<T extends Comparable<T>> implements Visitor<T, T,List<T>> {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	StringBuilder sb = new StringBuilder();
	List<T> collection =new ArrayList<T>();
	@Override public T visit(Element<T> e) {
		T value;
		// L-R-N
		
		if (e.hasLeft()) {
			visit(e.left());
		}
		if (e.hasRight()) {
			visit(e.right());
		}
		value =doSomethingOnElement(e);
		return value;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.trees.Visitor#visit(algos.trees.Tree)
	 */
	@Override public T visit(Tree<T> t) {
		log.debug("printing the binary tree..");
		return visit(t.root());
	}


	/* (non-Javadoc)
	 * @see algos.trees.Visitor#doSomethingOnElement(algos.trees.Element)
	 */
	@Override public T doSomethingOnElement(Element<T> e) {
		collection.add(e.value());
		return e.value();
	}

}
