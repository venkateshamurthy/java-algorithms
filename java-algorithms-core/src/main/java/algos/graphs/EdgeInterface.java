/**
 * 
 */
package algos.graphs;
//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)
public interface EdgeInterface<T extends Comparable<T>> extends Visitable<T>,Comparable<EdgeInterface<T>>{
	VertexInterface<T> from();
	VertexInterface<T> to();
	Double cost();
	boolean isDirected();
	VertexInterface<T> mate(VertexInterface<T> v);
	EdgeInterface<T> cost(Double cost);
	EdgeInterface<T> isDirected(boolean isDirected);
  EdgeInterface<T> reverse();
}
