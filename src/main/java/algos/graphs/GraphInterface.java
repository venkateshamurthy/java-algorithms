/**
 * 
 */
package algos.graphs;

import java.util.List;
import java.util.Set;

//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)
public interface GraphInterface<T extends Comparable<T>> extends Visitable<T>,Comparable<GraphInterface<T>> {
  VertexInterface<T> addVertex(T value, Double weight);
	EdgeInterface<T> addEdge(T from, T to, Double cost);
	EdgeInterface<T> findEdge(T from, T to);
	EdgeInterface<T> findEdge(VertexInterface<T> from, VertexInterface<T> to);
	Set<VertexInterface<T>> verticies();
	Set<EdgeInterface<T>> edges();
	List<VertexInterface<T>> adjV(VertexInterface<T> u);
	List<EdgeInterface<T>> adjE(VertexInterface<T> u);
  boolean isDirected();
}
