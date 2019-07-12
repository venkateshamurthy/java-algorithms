/**
 * 
 */
package algos.graphs;
/**
 * @author vmurthy
 *
 * @param <T> is type of object being vertexed in a graph.
 * Both the vertex and the object it holds must be Comparable.
 * It must also be a visitable. All the setter methods are fluent access style that support method chaining
 */
public interface VertexInterface<T extends Comparable<T>> extends  Visitable<T>,Comparable<VertexInterface<T>>{
	
	T	value();
	Double weight();
	Color color();
	VertexInterface<T> pi();
	int discoveredState();
	int finishedState();
	VertexInterface<T> weight(Double weight);
	VertexInterface<T> color(Color color);
	VertexInterface<T> clearVisit();
	/**if true un-visited vertex*/
	boolean visitCleared();
	VertexInterface<T> startVisit();
	/**if true visit started */
	boolean visitStarted();
	VertexInterface<T> endVisit();
	/**if visit ended/completed*/
	boolean visitEnded();
	VertexInterface<T> pi(VertexInterface<T> parent);
	VertexInterface<T> discoveredState(int state);
	VertexInterface<T> finishedState(int state);
}
