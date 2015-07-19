package algos.katemats;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.springframework.util.Assert;

import algos.graphs.GraphInterface;
import algos.graphs.VertexInterface;

/**
 * A DFS traverser of a {@link GraphInterface&lt;T&gt;} that uses a data
 * structure instead of implicit recursion
 * 
 * @author vemurthy
 *
 * @param <T>
 */
@Accessors(fluent = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DFS<T extends Comparable<T>> implements GraphVisitor<T, Void, Set<VertexInterface<T>>> 
{

	/** A constant to indicate Inf */
	static double INFINITY = Double.POSITIVE_INFINITY;
	static Void v_o_i_d = null;

	/** time at which the node was visited */
	@NonFinal
	int time;

	/**
	 * A collection variable accumulating as the visit traverses
	 */
	Set<VertexInterface<T>> collection = new LinkedHashSet<>();

	/**
	 * {@link java.util.Deque Deque} data structure for queuing/pushing the
	 * vertices discovered for their neighbors/descendant traversals
	 * respectively
	 */
	Deque<VertexInterface<T>> S = new ArrayDeque<>();

	/** A graph instance */
	GraphInterface<T> G;

	/**
	 * Post construct call as i dont want to add this into a lombok
	 * auto-constructor
	 */
	@PostConstruct
	public void postConstruct() {
		clear(G);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algos.graphs.Visitor#visit(java.lang.Object)
	 */
	@Override
	public Void visit(VertexInterface<T> s) {
		Assert.isTrue(G.verticies().contains(s));
		if (s.visitCleared()) {
			s.startVisit().weight(0d).pi(null);
			S.push(s);
			collection.add(s);
			while (!S.isEmpty()) {
				val u = S.pop();
				for (val v : G.adjV(u)) {
					if (v.visitCleared()) {
						S.push(v.startVisit().weight(u.weight() + 1).pi(u));
						collection.add(v);
					}
				}
				u.endVisit();
			}
		}
		return v_o_i_d;
	}

	/**
	 * Clears all the internal structures
	 * 
	 * @param G
	 *            is the graph passed.
	 */
	void clear(GraphInterface<T> G) {
		for (VertexInterface<T> u : G.verticies())
			u.weight(INFINITY).clearVisit().pi(null).discoveredState(0)
					.finishedState(0);
		time = 0;
		collection.clear();
		S.clear();
	}

	@Override
	public Void visit(GraphInterface<T> G) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
