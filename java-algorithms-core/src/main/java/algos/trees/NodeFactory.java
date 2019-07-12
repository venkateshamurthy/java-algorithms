package algos.trees;

@FunctionalInterface
public interface NodeFactory<T extends Comparable<T>, N extends BaseNode<N> & ComparableNode<T>> {
  N create(T value);
}