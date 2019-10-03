package algos.graphs;

import static algos.graphs.Color.BLACK;
import static algos.graphs.Color.GRAY;
import static algos.graphs.Color.WHITE;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.collections4.ListUtils;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Accessors(fluent = true)
@Data // (staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Graph<T extends Comparable<T>> implements GraphInterface<T> {
  public static final double INFINITY = Double.MAX_VALUE;

  public static <Y extends Comparable<Y>> Graph<Y> of(TYPE type) {
    return new Graph<Y>(type);
  }

  /** A collection of vertices considered in inserted order. */
  Map<VertexInterface<T>, VertexInterface<T>> verticies = new LinkedHashMap<>();

  /** A collection of edges considered in inserted order. */
  Map<EdgeInterface<T>, EdgeInterface<T>> edges = new LinkedHashMap<>();

  /** The type of graph {@link TYPE#DIRECTED} or {@link TYPE#UNDIRECTED}. */
  TYPE type;

  MultiValueMap<VertexInterface<T>, VertexInterface<T>> endVertices = new LinkedMultiValueMap<>();
  MultiValueMap<VertexInterface<T>, EdgeInterface<T>> edgesSourcedFrom = new LinkedMultiValueMap<>();

  public Set<VertexInterface<T>> verticies() {
    return verticies.keySet();
  }

  public Set<EdgeInterface<T>> edges() {
    return edges.keySet();
  }

  /**
   * Adjacent Vertex List
   */
  @Override
  public List<VertexInterface<T>> adjV(VertexInterface<T> u) {
    return ListUtils.emptyIfNull(endVertices.get(u));
  }

  /**
   * Adjacent Edges
   */
  @Override
  public List<EdgeInterface<T>> adjE(VertexInterface<T> u) {
    return ListUtils.emptyIfNull(edgesSourcedFrom.get(u));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (VertexInterface<T> v : edgesSourcedFrom.keySet()) {
      builder.append(v.value() + " " + edgesSourcedFrom.get(v) + "\n");
    }
    return builder.toString();
  }

  @Override
  public VertexInterface<T> addVertex(T value, Double weight) {
    return addVertex(Vertex.of(value).weight(weight));
  }

  protected VertexInterface<T> addVertex(VertexInterface<T> v) {
    return verticies.computeIfAbsent(v, Function.identity());
    /*if (!verticies.containsKey(v))
      verticies.put(v, v);
    return verticies.get(v);*/
  }

  @Override
  public EdgeInterface<T> addEdge(T from, T to, Double cost) {
    return addEdge(addVertex(from, 0d), addVertex(to, 0d), cost);
  }

  protected EdgeInterface<T> addEdge(VertexInterface<T> vFrom, VertexInterface<T> vTo, Double cost) {
    Assert.isTrue(verticies.keySet().containsAll(Arrays.asList(vFrom, vFrom)));
    EdgeInterface<T> e = Edge.of(vFrom, vTo).cost(cost).isDirected(type == TYPE.DIRECTED);

    if (!edges.containsKey(e)) {
      edges.put(e, e);
      endVertices.add(e.from(), e.to());
      edgesSourcedFrom.add(e.from(), e);
    }
    e = edges.get(e);

    if (type == TYPE.UNDIRECTED) {
      EdgeInterface<T> reverse = e.reverse();
      if (!edges.containsKey(reverse)) {
        edges.put(reverse, reverse);
        endVertices.add(reverse.from(), reverse.to());
        edgesSourcedFrom.add(reverse.from(), reverse);
      }
    }
    return e;
  }

  @Override
  public EdgeInterface<T> findEdge(T from, T to) {
    VertexInterface<T> vf = Vertex.of(from), vt = Vertex.of(to);
    return findEdge(vf, vt);
  }

  @Override
  public EdgeInterface<T> findEdge(VertexInterface<T> from, VertexInterface<T> to) {
    val emanatingEdges = edgesSourcedFrom.get(from);
    if (emanatingEdges != null) {
      for (val e : emanatingEdges)
        if (e.to().equals(to))
          return e;
    }
    return null;
  }

  public void accept(Visitor<Visitable<T>, ?, ?> visitor) {
    visitor.visit(this);
  }

  public void accept(Visitor<Visitable<T>, ?, ?> visitor, Visitable<T> visitable) {
    visitor.visit(visitable);
  }

  @Accessors(fluent = true)
  @Data // (staticConstructor = "of")
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  @EqualsAndHashCode(of = { "value" })
  // @ToString(of = { "value", "weight", "color", "pi", "discoveredState",
  // "finishedState" })
  @ToString(of = { "value", "weight" })
  public static class Vertex<T extends Comparable<T>> implements VertexInterface<T> {
    public static <Y extends Comparable<Y>, V extends VertexInterface<Y>> V of(Y y) {
      return (V) new Vertex<Y>(y);
    }

    public String toString() {
      return value + "," + (weight == INFINITY ? "INF" : weight) + "" + (pi != null ? "{" + pi.value() + "}" : "");
    }

    T value;
    @NonFinal
    Double weight = 0d;
    @NonFinal
    Color color = WHITE;
    @NonFinal
    VertexInterface<T> pi;
    @NonFinal
    int discoveredState = 0, finishedState = 0;

    @Override
    public int compareTo(VertexInterface<T> v) {
      Assert.isTrue(weight != null && v != null);
      int result = this.weight.compareTo(v.weight());
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see algos.graphs.Visitable#accept(algos.graphs.Visitor)
     */
    @Override
    public void accept(Visitor<Visitable<T>, ?, ?> visitor) {
      // TODO Auto-generated method stub
      visitor.visit(this);
    }

    @Override
    public VertexInterface<T> clearVisit() {
      return color(WHITE);
    }

    @Override
    public boolean visitCleared() {
      return color == WHITE;
    }

    @Override
    public VertexInterface<T> startVisit() {
      return color(GRAY);
    }

    @Override
    public boolean visitStarted() {
      return color == GRAY;
    }

    @Override
    public VertexInterface<T> endVisit() {
      return color(BLACK);
    }

    @Override
    public boolean visitEnded() {
      return color == BLACK;
    }

    @SuppressWarnings("serial")
    public static <Y extends Comparable<Y>, V extends VertexInterface<Y>> Set<V> of(final Y... ys) {
      return new LinkedHashSet<V>(10) {
        {
          for (Y y : ys)
            add((V) Vertex.of(y));
        }
      };
    }

  }

  @Accessors(fluent = true)
  @Data // (staticConstructor = "of")
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  @EqualsAndHashCode(of = { "from", "to" })
  @ToString(of = { "from", "to", "cost" })
  public static class Edge<T extends Comparable<T>> implements EdgeInterface<T> {
    public static <Y extends Comparable<Y>> Edge<Y> of(VertexInterface<Y> from, VertexInterface<Y> to) {
      return new Edge<Y>(from, to);
    }

    public static <Y extends Comparable<Y>> Edge<Y> of(Y from, Y to, double cost) {
      return new Edge<Y>(Vertex.of(from), Vertex.of(to)).cost(cost);
    }

    public Edge<T> reverse() {
      return Edge.of(to, from).cost(cost);
    }

    public String toString() {
      return from.value() + "--(" + cost + ")-->" + to.value();
    }

    @NonNull
    VertexInterface<T> from, to;
    @NonFinal
    Double cost = 0d;
    @NonFinal
    boolean isDirected = false;

    @Override
    public void accept(Visitor<Visitable<T>, ?, ?> visitor) {
      visitor.visit(this);
    }

    @Override
    public int compareTo(EdgeInterface<T> e) {
      return this.cost == e.cost() ? 0 : cost.compareTo(e.cost());
    }

    public VertexInterface<T> mate(VertexInterface<T> v) {
      if (from.equals(v))
        return to;
      else if (to.equals(v))
        return from;
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(GraphInterface<T> o) {
    return 0;
  }

  @Override
  public boolean isDirected() {
    return type == TYPE.DIRECTED;
  }

}
