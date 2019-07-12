/**
 * 
 */
package algos.graphs;

/**
 * @author vmurthy
 * 
 * @param <T>
 *            is type of object to visit
 * @param <R>
 *            is the return type on each visit
 * @param <C>
 *            is a collection/aggregation of result accumulated over each visit. It could be
 *            Collection of R as well.
 */
public interface Visitor<T, R, C> {
	/**
	 * visit an instance of {@link Visitable<T>}
	 * 
	 * @param t
	 *            an instance of Visitable
	 * @return	a result R
	 */
	R visit(T t);

	/**
	 * @return a Collection/Aggregation of result accumulated over each visit
	 */
	C collection();
}
