package algos.graphs;


import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;

import org.springframework.util.Assert;

import algos.graphs.Graph.Vertex;

@Log4j2
public class GraphAlgorithms<T extends Comparable<T>> {
	private static Void v_o_i_d;
	private static final double INFINITY = Double.MAX_VALUE;

	public static void main(String[] args) {
		int[][] edges = { { 0, 1 }, { 1, 7 }, { 7, 2 }, { 2, 5 }, { 5, 6 },
				{ 3, 2 }, { 3, 4 }, { 0, 8 }, { 8, 4 } };
		Graph<Integer> G = Graph.of(TYPE.UNDIRECTED);
		for (int i = 0; i < edges.length; i++)
			G.addEdge(edges[i][0], edges[i][1], 0.0);
		Vertex<Integer> zeroVertex = Vertex.of(0);
		GraphAlgorithms<Integer> bfsDfs = new GraphAlgorithms<Integer>();
		bfsDfs.bfs(G, zeroVertex);
	}

	/**
	 * Breadth first search
	 * 
	 * @param G
	 *            graph
	 * @param s
	 *            start node
	 */
	public void bfs(Graph<T> G, Vertex<T> s) {
		BFS<T> bfs = BFS.of(G);
		bfs.visit(s);
		for (VertexInterface<T> v : bfs.collection())
			log.debug(v);
	}

	/**
	 * Minimum Spanning tree
	 * 
	 * @param G
	 *            - Graph
	 * @param r
	 *            - root at which MST spans
	 */
	public VertexInterface<T> mstPrim(Graph<T> G, VertexInterface<T> r) {
		Prim<T> prim = Prim.of(G);
		prim.visit(r);
		for (VertexInterface<T> v : prim.collection())
			log.debug(v);
		return r;
	}

	/**
	 * DFS
	 * 
	 * @param G
	 */
	public void dfs(Graph<T> G) {
		DFS<T> d_f_s = DFS.of();
		d_f_s.visit(G);
		for (val v : d_f_s.collection())
			log.debug(v);
	}

	/**
	 * BFS Visitor of the Graph<Type>
	 * 
	 * @author vmurthy
	 * 
	 * @param <Type>
	 *            of the object that Graph holds
	 */
	@Accessors(fluent = true)
	@Data
	// (staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	static class BFS<Type extends Comparable<Type>> implements
			Visitor<VertexInterface<Type>, Void, Set<VertexInterface<Type>>> {
		public static <Y extends Comparable<Y>> BFS<Y> of(GraphInterface<Y> g) {
			return new BFS<Y>(g);
		}

		/**
		 * Passed in Graph G
		 */
		GraphInterface<Type> G;

		/**
		 * Collected results. Keep it always named as collection so u dont have
		 * to code getCollection()/collection() method.
		 */
		Set<VertexInterface<Type>> collection = new LinkedHashSet<VertexInterface<Type>>();

		/**
		 * The Breadth First {@link Queue}
		 */
		Deque<VertexInterface<Type>> Q = new ArrayDeque<VertexInterface<Type>>();

		/**
		 * <pre>
		 * BFS(G,s) 
		 * for each vertex u belonging to G.V - {s} 
		 *     u.color = WHITE 
		 *     u.d = infinity 
		 *     u.pi = NIL
		 * 
		 * s.color = GRAY 
		 * s.d = 0 
		 * s.pi = NIL
		 * 
		 * Q = NIL
		 * ENQUEUE(Q, s)
		 *  
		 * while Q != NIL 
		 *    u = DEQUEUE(Q) 
		 *    for each v belonging to G.Adj[u] 
		 *       if v.color == WHITE 
		 *          v.color = GRAY 
		 *          v.d = u.d + 1 
		 *          v.pi = u
		 *          ENQUEUE(Q, v)
		 *    u.color = BLACK
		 * </pre>
		 */
		@Override
		public Void visit(VertexInterface<Type> s) {
			Assert.isTrue(G.verticies().contains(s));
			// Initialize pi, color and distance except for s
			for (val u : G.verticies())
				if (!u.equals(s))
					u.weight(INFINITY).clearVisit().pi(null);

			s.startVisit().weight(0d).pi(null);

			Q.add(s);
			collection.add(s);

			while (!Q.isEmpty()) {
				val u = Q.remove();

				for (val v : G.adjV(u)) {
					if (v.visitCleared()) {
						Q.add(v.startVisit().weight(u.weight() + 1).pi(u));
						collection.add(v);
					}
				}
				u.endVisit();
			}
			return v_o_i_d;
		}
	}

	/**
	 * <pre>
	 * 	MST-PRIM(G, w, s)
	 * 		for each u in G:V
	 * 			u.weight = INF
	 * 			u.pi = NIL
	 * 		s.wright = 0
	 * 		Q.add(G.V)
	 * 		
	 * 		while Q != empty
	 * 			u = Q.remove()
	 * 			for each v in G.Adj[u]
	 * 				find-edge e(u,v) = G.edge(u,v)
	 * 				if Q.contains(v) and e(u,v).cost < v.weight
	 * 				v.pi = u
	 * 				v.weight = e(u,v).cost
	 * </pre>
	 * 
	 * Prim MST Visitor of the Graph<Type>
	 * 
	 * @author vmurthy
	 * 
	 * @param <Type>
	 *            of the object that Graph holds
	 */
	@Accessors(fluent = true)
	@Data
	// (staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	static class Prim<Type extends Comparable<Type>> implements
			Visitor<VertexInterface<Type>, Void, Set<VertexInterface<Type>>> {
		public static <Y extends Comparable<Y>> Prim<Y> of(GraphInterface<Y> g) {
			return new Prim<Y>(g);
		}

		/**
		 * Passed in Graph G
		 */
		GraphInterface<Type> G;
		/**
		 * Collected results. Keep it always named as collection so u dont have
		 * to code getCollection()/collection() method.
		 */
		@NonFinal
		Set<VertexInterface<Type>> collection = new LinkedHashSet<VertexInterface<Type>>();

		@Override
		public Void visit(VertexInterface<Type> s) {
			/**
			 * The Breadth First {@link Queue}
			 */
			Queue<VertexInterface<Type>> Q = new PriorityQueue<VertexInterface<Type>>(
					G.verticies().size(),
					new Comparator<VertexInterface<Type>>() {
						@Override
						public int compare(VertexInterface<Type> o1,
								VertexInterface<Type> o2) {
							if (o1.weight() == o2.weight())
								return 0;
							else
								return o1.weight() < o2.weight() ? -1 : 1;
						}
					});

			// Initialize pi,and distance to INF
			for (val u : G.verticies())
				if (!u.equals(s))
					u.weight(INFINITY).pi(null);

			// Mark root alone to zero while all are INF add to priority queue
			s.weight(0d);
			Q.addAll(G.verticies());

			while (!Q.isEmpty()) {
				// Pick up the vertex u and find the adjV
				val u = Q.remove(); // this is extract first or extract top
				for (val v : G.adjV(u)) {
					val edge = G.findEdge(u, v);
					if (Q.contains(v) && edge != null
							&& edge.cost() < v.weight()){
						Q.remove(v);
						Q.add(v.pi(u).weight(edge.cost()));
						
					}
				}
			}

			return v_o_i_d;
		}
	}

	/**
	 * <pre>
	 *  DFS(G)
	 * 		for each vertex u in G:V
	 * 			u.color = WHITE
	 * 			u.pi = NIL
	 * 	        time = 0
	 * 		for each vertex u in G:V
	 * 		if u.color == WHITE
	 * 			DFS-VISIT(G,u)
	 * 			
	 * 		DFS-VISIT(G, u)
	 * 		u.d = time++ //discovered
	 * 		u.color =  GRAY
	 * 		for each v in G.Adj[u] // explore edge u,v
	 * 			if v.color == WHITE
	 * 				v.pi = u
	 * 				DFS-VISIT(G, v)
	 * 		u.color = BLACK // blacken u; it is finished
	 * 		u.f = time++ //finished
	 * </pre>
	 * 
	 * @author vemurthy
	 *
	 * @param <Type>
	 */
	@Accessors(fluent = true)
	@Data
	// (staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE)
	static class DFS<Type extends Comparable<Type>> implements
			Visitor<GraphInterface<Type>, Void, Set<VertexInterface<Type>>> {
		public static <Y extends Comparable<Y>> DFS<Y> of() {
			return new DFS<Y>();
		}

		/**
		 * DFS Its more of a logical time when every node was visited first time
		 * and when it could have finished
		 */
		int time = 0;
		/**
		 * DFS Just take the traced Path of dfs
		 */
		Set<VertexInterface<Type>> collection = new LinkedHashSet<VertexInterface<Type>>();

		@Override
		public Void visit(GraphInterface<Type> G) {
			// Initialize pi, color and distance and time-stamps/State counts
			for (VertexInterface<Type> u : G.verticies())
				u.weight(INFINITY).clearVisit( ).pi(null).discoveredState(0)
						.finishedState(0);
			time = 0;
			collection.clear();

			// Start the visit with if color is white
			for (VertexInterface<Type> u : G.verticies())
				if (u.visitCleared()) {
					dfsVisit(G, u);
					collection.add(u);
				}
			return v_o_i_d;
		}

		private void dfsVisit(GraphInterface<Type> G, VertexInterface<Type> u) {
			// For every u: Stamp the color and time and mark visit started
			u.startVisit().discoveredState(++time);
			// Next find the adjacent children and mark pi and recurse
			for (VertexInterface<Type> v : G.adjV(u))
				if (v.visitCleared()) {
					dfsVisit(G, v.pi(u));
					collection.add(v);
				}

			// U is colored black (meaning end visited) and time stamped
			u.endVisit().finishedState(++time);
		}
	}

	@Accessors(fluent = true)
	@Data(staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	static class Dijkstra<Type extends Comparable<Type>> implements
			Visitor<VertexInterface<Type>, Void, Set<VertexInterface<Type>>> {
		/**
		 * Passed in Graph G
		 */
		GraphInterface<Type> G;
		/**
		 * Collected results. Keep it always named as collection so u dont have
		 * to code getCollection()/collection() method.
		 */
		@NonFinal
		Set<VertexInterface<Type>> collection = new LinkedHashSet<VertexInterface<Type>>();

		Dijkstra(GraphInterface<Type> G) {
			this.G = G;
			Q = new PriorityQueue<VertexInterface<Type>>(G.verticies().size(),
					new Comparator<VertexInterface<Type>>() {
						@Override
						public int compare(VertexInterface<Type> o1,
								VertexInterface<Type> o2) {
							if (o1.weight() == o2.weight())
								return 0;
							else
								return o1.weight() < o2.weight() ? -1 : 1;
						}
					});
		}

		PriorityQueue<VertexInterface<Type>> Q;

		public static <Y extends Comparable<Y>> Dijkstra<Y> of(
				GraphInterface<Y> g) {
			return new Dijkstra<Y>(g);
		}

		@Override
		public Void visit(VertexInterface<Type> s) {
			
			// Initialize
			for (val v : G.verticies()) {
				v.pi(null).weight(INFINITY);
			}
			s.weight(0d);
			// Add start node
			Q.addAll(G.verticies());

			while (!Q.isEmpty()) {
				val u = Q.poll();
				// Get adjacent edges of u
				for (val e : G.adjE(u)) {
					val v = e.to();
					// relax the edge weight
					if (Q.contains(v) && v.weight() > (e.cost() + u.weight())) {
						Q.remove(v); // u need to remove and add to induce the
										// behavior of decrease the priority
						Q.add(v.pi(u).weight(e.cost() + u.weight()));
					}
				}
				collection.add(u);
			}
			return null;
		}
	}
}