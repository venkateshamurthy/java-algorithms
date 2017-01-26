/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayList;
import java.util.List;

import algos.trees.BSTNode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Data
@Accessors(fluent=true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostOrderPrinter<T extends Comparable<T>> implements BSTVisitor<T, T,List<T>> {
	StringBuilder sb = new StringBuilder();
	List<T> collection =new ArrayList<T>();
	
	@Override public T visit(BSTNode<T> e) {
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

	@Override public T doSomethingOnElement(BSTNode<T> e) {
		collection.add(e.value());
		return e.value();
	}

}
