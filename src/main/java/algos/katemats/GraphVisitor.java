package algos.katemats;

import algos.graphs.GraphInterface;
import algos.graphs.VertexInterface;
/**
 * Graph interface
 * @author vemurthy
 *
 * @param <T>
 * @param <R>
 * @param <C>
 */
public interface  GraphVisitor<T extends Comparable<T>, R, C> {
	/**
	 * Visit method to visit inside graph nd basically leads to {@link #visit(VertexInterface)}. 
	 * However in this method any start up/initial one time initializations or activities can be done in this method,
	 * @param G
	 * @return an instance of type R
	 */
	public R visit(GraphInterface<T> G);
	/**
	 * visit an instance of {@link Visitable<T>}
	 * 
	 * @param t
	 *            an instance of Visitable
	 * @return	a result R
	 */
	R visit(VertexInterface<T> t);

	/**
	 * @return a Collection/Aggregation of result accumulated over each visit
	 */
	C collection();
}
