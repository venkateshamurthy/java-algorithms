/**
 * 
 */
package algos.trees.visitors;

import algos.trees.Element;
import algos.trees.Tree;

//Using lombok annotation for log4j handle
/**
 * Visitor interface for a element of type T that might return of type R and may
 * have collected Results in a type of C.
 * 
 * @author vmurthy
 * 
 */
public interface Visitor<T extends Comparable<T>, R, C> {
	R visit(Tree<T> t);

	 R visit(Element<T> e);

	R doSomethingOnElement(Element<T> e);

	C collection();
}
