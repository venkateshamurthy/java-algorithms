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
 */
@Data
@Accessors(fluent=true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InOrderPrinter<T extends Comparable<T>> implements BSTVisitor<T,T,List<T>> {

	List<T> collection=new ArrayList<T>();

	@Override public T visit(BSTNode<T> e) {
		// L-N-R
		if (e.hasLeft()) {
			visit(e.left());
		}
		T value = doSomethingOnElement(e);
		
		if (e.hasRight()) {
			visit(e.right());
		}
		return value;
	}

	@Override public T doSomethingOnElement(BSTNode<T> e) {
		collection.add(e.value());
		return e.value();
	}

}
