package algos.katemats;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.cglib.core.Predicate;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import algos.graphs.GraphInterface;
import algos.graphs.VertexInterface;
import algos.graphs.Visitor;

/**
 * A DFS traverser of a {@link GraphInterface&lt;Type&gt;} that uses a data
 * structure instead of implicit recursion
 * 
 * @author vemurthy
 *
 * @param <Type>
 */
@Accessors(fluent = true)
@Data(staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DFS<Type extends Comparable<Type>> implements
		GraphVisitor<Type, Void, Set<VertexInterface<Type>>> {

	/** A constant to indicate Inf */
	static double INFINITY = Double.POSITIVE_INFINITY;
	static Void v_o_i_d = null;

	/** time at which the node was visited */
	@NonFinal
	int time;

	/**
	 * A collection variable accumulating as the visit traverses
	 */
	Set<VertexInterface<Type>> collection = new LinkedHashSet<>();

	/**
	 * {@link java.util.Deque Deque} data structure for queuing/pushing the
	 * vertices discovered for their neighbors/descendant traversals
	 * respectively
	 */
	Deque<VertexInterface<Type>> S = new ArrayDeque<>();

	/** A graph instance */
	GraphInterface<Type> G;

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
	public Void visit(VertexInterface<Type> s) {
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
	void clear(GraphInterface<Type> G) {
		for (VertexInterface<Type> u : G.verticies())
			u.weight(INFINITY).clearVisit().pi(null).discoveredState(0)
					.finishedState(0);
		time = 0;
		collection.clear();
		S.clear();
	}

	@Override
	public Void visit(GraphInterface<Type> G) {
		// Initialize pi, color and distance except for s
		clear(G);
		for (val v : G.verticies())
			visit(v);
		return v_o_i_d;
	}
}
