/**
 * 
 */
package algos.trees.visitors;

import algos.trees.BaseNode;
import algos.trees.ComparableNode;
import algos.trees.Tree;

//Using lombok annotation for log4j handle
/**
 * Visitor interface for a element of type T that might return of type R and may
 * have collected Results in a type of C.
 * 
 * @author vmurthy
 * 
 */
public interface Visitor<T extends Comparable<T>, N extends BaseNode<N> & ComparableNode<T>, R, C> {
  
  default R visit(Tree<T, N> t) {
      return visit(t.root());
  }

  R visit(N e);

  C collection();
}