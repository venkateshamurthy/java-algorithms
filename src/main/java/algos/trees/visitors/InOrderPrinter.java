/**
 * 
 */
package algos.trees.visitors;

import java.util.ArrayList;
import java.util.List;

import algos.trees.BSTNode;
import algos.trees.Tree;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 */
@Slf4j
@Data
@Accessors(fluent=true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InOrderPrinter<T extends Comparable<T>> implements BSTVisitor<T,T,List<T>> {

	List<T> collection=new ArrayList<T>();
	@Override public T visit(Tree<T, BSTNode<T>> t) {
	  collection.clear();
    return visit(t.root());
}
	@Override public T visit(BSTNode<T> e) {
		// L-N-R
		if (e.hasLeft()) {
			visit(e.left());
		}
		collection.add(e.value());
		if (e.hasRight()) {
			visit(e.right());
		}
		return e.value();
	}

}
