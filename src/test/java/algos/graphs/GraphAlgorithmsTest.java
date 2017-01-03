package algos.graphs;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import algos.graphs.Graph.Edge;
import algos.graphs.Graph.Vertex;
import algos.graphs.GraphAlgorithms.BellmanFord;
import algos.graphs.GraphAlgorithms.Dijkstra;
import algos.graphs.GraphAlgorithms.Prim;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GraphAlgorithmsTest {
  // http://algs4.cs.princeton.edu/lectures/43DemoPrim.pdf
  /**
   * 1-7 0.19000 0-2 0.26000 2-3 0.17000 4-5 0.35000 5-7 0.28000 6-2 0.40000 0-7
   * 0.16000 1.81000
   */
  @Test
  public void testPrimRoberSedgwick() {
    Graph<String> G = Graph.of(TYPE.UNDIRECTED);
    G.addEdge("0", "7", 0.16);
    G.addEdge("2", "3", 0.17);
    G.addEdge("1", "7", 0.19);
    G.addEdge("0", "2", 0.26);
    G.addEdge("5", "7", 0.28);
    G.addEdge("1", "3", 0.29);
    G.addEdge("1", "5", 0.32);
    G.addEdge("2", "7", 0.34);
    G.addEdge("4", "5", 0.35);
    G.addEdge("1", "2", 0.36);
    G.addEdge("4", "7", 0.37);
    G.addEdge("0", "4", 0.38);
    G.addEdge("6", "2", 0.40);
    G.addEdge("3", "6", 0.52);
    G.addEdge("6", "0", 0.58);
    G.addEdge("6", "4", 0.93);
    Set<EdgeInterface<String>> safeEdgesExpected = new HashSet<>();
    safeEdgesExpected.add(Edge.of("0", "7", 0.16));
    safeEdgesExpected.add(Edge.of("1", "7", 0.19));
    safeEdgesExpected.add(Edge.of("0", "2", 0.26));
    safeEdgesExpected.add(Edge.of("2", "3", 0.17));
    safeEdgesExpected.add(Edge.of("5", "7", 0.28));
    safeEdgesExpected.add(Edge.of("4", "5", 0.35));
    safeEdgesExpected.add(Edge.of("6", "2", 0.4));
    final double minCost = 1.81d;
    Prim<String> mst = Prim.<String> of(G);
    mst.visit(Vertex.of("0"));
    log.info("Prim MST {} {}", mst.minSpanningTreeCost(),mst.collection());
    Assert.assertEquals(minCost, mst.minSpanningTreeCost(), 1e-02);
    for(val edge:mst.collection()) {
      safeEdgesExpected.remove(edge);
      safeEdgesExpected.remove(edge.reverse());//since this is undirected; we can encounter reversed edge also; so try check that
    }
    Assert.assertTrue(safeEdgesExpected.toString(),safeEdgesExpected.isEmpty());

  }

  // http://algs4.cs.princeton.edu/lectures/44DemoBellmanFord.pdf
  // Answer is in 48th slide and is 0-4-5-2-6
  @Test
  public void testBellmanFordRoberSedgwick() {
    Graph<String> G = Graph.of(TYPE.DIRECTED);
    G.addEdge("0", "1", 5d);
    G.addEdge("0", "4", 9d);
    G.addEdge("0", "7", 8d);
    G.addEdge("1", "2", 12d);
    G.addEdge("1", "3", 15d);
    G.addEdge("1", "7", 4d);
    G.addEdge("2", "3", 3d);
    G.addEdge("2", "6", 11d);
    G.addEdge("3", "6", 9d);
    G.addEdge("4", "5", 4d);
    G.addEdge("4", "6", 20d);
    G.addEdge("4", "7", 5d);
    G.addEdge("5", "2", 1d);
    G.addEdge("5", "6", 13d);
    G.addEdge("7", "5", 6d);
    G.addEdge("7", "2", 7d);
    BellmanFord<String> shortestPathFinder = BellmanFord.<String> of(G);
    shortestPathFinder.visit(Pair.create(Vertex.of("0"), Vertex.of("6")));
    log.info("V={}", G.verticies());
    Set<VertexInterface<String>> s = Vertex.of("0,4,5,2,6".split(","));
    log.info("{} {}", s, shortestPathFinder.collection());
    Iterator<VertexInterface<String>> iter = s.iterator();
    while (iter.hasNext() && !shortestPathFinder.collection().isEmpty()) {
      val a = iter.next();
      val b = shortestPathFinder.collection().pop();
      Assert.assertEquals("" + a + " " + b, a, b);
    }
  }

  // http://algs4.cs.princeton.edu/lectures/44DemoDijkstra.pdf
  // Answer is in 34th slide and is 0-4-5-2-6
  @Test
  public void testDijkstraRoberSedgwick() {
    Graph<String> G = Graph.of(TYPE.DIRECTED);
    G.addEdge("0", "1", 5d);
    G.addEdge("0", "4", 9d);
    G.addEdge("0", "7", 8d);
    G.addEdge("1", "2", 12d);
    G.addEdge("1", "3", 15d);
    G.addEdge("1", "7", 4d);
    G.addEdge("2", "3", 3d);
    G.addEdge("2", "6", 11d);
    G.addEdge("3", "6", 9d);
    G.addEdge("4", "5", 4d);
    G.addEdge("4", "6", 20d);
    G.addEdge("4", "7", 5d);
    G.addEdge("5", "2", 1d);
    G.addEdge("5", "6", 13d);
    G.addEdge("7", "5", 6d);
    G.addEdge("7", "2", 7d);
    Dijkstra<String> shortestPathFinder = Dijkstra.of(G);
    shortestPathFinder.visit(Pair.create(Vertex.of("0"), Vertex.of("6")));
    log.info("V={}", G.verticies());
    Set<VertexInterface<String>> s = Vertex.of("0,4,5,2,6".split(","));
    log.info("{} {}", s, shortestPathFinder.collection());
    Iterator<VertexInterface<String>> iter = s.iterator();
    while (iter.hasNext() && !shortestPathFinder.collection().isEmpty()) {
      val a = iter.next();
      val b = shortestPathFinder.collection().pop();
      Assert.assertEquals("" + a + " " + b, a, b);
    }
  }
}
