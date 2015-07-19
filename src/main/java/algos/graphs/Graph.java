package algos.graphs;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import  static algos.graphs.Color.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Accessors(fluent = true)
@Data//(staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Graph<T extends Comparable<T>> implements
		GraphInterface<T> {
    public static <Y extends Comparable<Y>> Graph<Y> of(TYPE type){
        return new Graph<Y>(type);
    }
	/**
	 * A collection of vertices considered in inserted order
	 */
	Set<VertexInterface<T>> verticies = new CopyOnWriteArraySet<VertexInterface<T>>();
	/**
	 * A collection of edges considered in inserted order
	 */
	Set<EdgeInterface<T>> edges = new CopyOnWriteArraySet<EdgeInterface<T>>();
	/**
	 * The type of graph {@link TYPE#DIRECTED} or {@link TYPE#UNDIRECTED}
	 */
	TYPE type;

	MultiValueMap<VertexInterface<T>, VertexInterface<T>> endVertices = new LinkedMultiValueMap<VertexInterface<T>, VertexInterface<T>>();
	MultiValueMap<VertexInterface<T>, EdgeInterface<T>> endEdges = new LinkedMultiValueMap<VertexInterface<T>, EdgeInterface<T>>();
	/**
	 * Adjacent Vertex List
	 * @param u
	 * @return
	 */
	@Override public List<VertexInterface<T>> adjV(VertexInterface<T> u){
		return endVertices.get(u);
	}
	/**
	 * Adjacent Edges
	 * @param u
	 * @return
	 */
	@Override public List<EdgeInterface<T>> adjE(VertexInterface<T> u){
		return endEdges.get(u);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override public String toString() {
		StringBuilder builder = new StringBuilder();
		for (VertexInterface<T> v : verticies) {
			builder.append(v.toString());
		}
		return builder.toString();
	}

	@Override public VertexInterface<T> addVertex(T value, Double weight) {
		Vertex<T> v = Vertex.of(value);
		v.weight(weight);
		return addVertex(v);
	}

	@Override public EdgeInterface<T> addEdge(T from, T to, Double cost) {
		Vertex<T> vFrom = Vertex.of(from), vTo = Vertex.of(to);
		return addEdge(vFrom, vTo, cost);
	}

	@Override public VertexInterface<T> addVertex(
			VertexInterface<T> v) {
		verticies.add(v);
		return v;
	}

	@Override public EdgeInterface<T> addEdge(
			VertexInterface<T> vFrom, VertexInterface<T> vTo,
			Double cost) {
		addVertex(vFrom);
		addVertex(vTo);
		Edge<T> e = Edge.of(vFrom, vTo).cost(cost).isDirected(type == TYPE.DIRECTED);
		edges.add(e);
		endVertices.add(vFrom, vTo);
		endEdges.add(vFrom,e);
		if (type == TYPE.UNDIRECTED) {
			Edge<T> reverse = Edge.of(vTo, vFrom).cost(cost).isDirected(type == TYPE.DIRECTED);
			edges.add(reverse);
			endVertices.add(vTo, vFrom);
			endEdges.add(vTo, reverse);
		}
		return e;
	}
	
	@Override public EdgeInterface<T> findEdge(T from, T to){
		Vertex<T> vf=Vertex.of(from),vt=Vertex.of(to);
		return findEdge(vf,vt);
	}

	@Override public EdgeInterface<T> findEdge(
			VertexInterface<T> from, VertexInterface<T> to) {
		val emanatingEdges = endEdges.get(from);
		if (emanatingEdges != null) {
			for (val e : emanatingEdges)
				if (e.to().equals(to))
					return e;
		}
		return null;
	}

	public void accept(Visitor<Visitable<T>,?,?> visitor) {
		//Visitable<T> rootVertex = endVertices.keySet().iterator()
		//		.next();
		visitor.visit(this);
	}

	public void accept(Visitor<Visitable<T>,?,?> visitor,
			Visitable<T> visitable) {
		visitor.visit(visitable);
	}

	@Accessors(fluent = true)
	@Data//(staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@EqualsAndHashCode(of = { "value", "weight" })
	@ToString(of = { "value", "weight", "color", "pi", "discoveredState","finishedState" })
	public static  class Vertex<T extends Comparable<T>> implements
			VertexInterface<T>{
	    public static<Y extends Comparable<Y>> Vertex<Y> of(Y y){
	        return new Vertex<Y>(y);
	    }
		T value;
		@NonFinal Double weight = 0d;
		@NonFinal Color color = WHITE;
		@NonFinal VertexInterface<T> pi;
		@NonFinal int discoveredState = 0, finishedState = 0;

		@Override public int compareTo(VertexInterface<T> v) {
			if (this.value == null || v.value() == null)
				return -1;
			return this.value.compareTo(v.value());
		}

		/* (non-Javadoc)
		 * @see algos.graphs.Visitable#accept(algos.graphs.Visitor)
		 */
		@Override public void accept(Visitor<Visitable<T>, ?, ?> visitor) {
			// TODO Auto-generated method stub
			visitor.visit(this);
		}

		@Override
		public VertexInterface<T> clearVisit() {
			return color(WHITE);
		}

		@Override
		public boolean visitCleared() {
			return color==WHITE;
		}

		@Override
		public VertexInterface<T> startVisit() {
			return color(GRAY);
		}

		@Override
		public boolean visitStarted() {
			return color==GRAY;
		}

		@Override
		public VertexInterface<T> endVisit() {
			return color(BLACK);
		}

		@Override
		public boolean visitEnded() {
			return color==BLACK;
		}

		
	}

	@Accessors(fluent = true)
	@Data//(staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@EqualsAndHashCode(of = { "from", "to" })
	@ToString(of = { "from", "to", "cost" })
	public static class Edge<T extends Comparable<T>> implements
			EdgeInterface<T> {
	    public static <Y extends Comparable<Y>> Edge<Y> 
	                        of(VertexInterface<Y> from, VertexInterface<Y> to){
	        return new Edge<Y>(from,to);
	    }
	    @NonNull VertexInterface<T> from, to;
		@NonFinal Double cost;
		@NonFinal boolean isDirected;

		@Override public void accept(Visitor<Visitable<T>, ?,?> visitor) {
			visitor.visit(this);
		}

		@Override public int compareTo(EdgeInterface<T> e) {
			if (this.cost == e.cost())
				return 0;
			else
				return cost.compareTo(e.cost());
		}

		public VertexInterface<T> mate(VertexInterface<T> v) {
			if (from.equals(v))
				return to;
			else if (to.equals(v))
				return from;
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override public int compareTo(GraphInterface<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
