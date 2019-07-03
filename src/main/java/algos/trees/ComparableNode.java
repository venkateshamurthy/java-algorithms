package algos.trees;

import java.util.Comparator;

import lombok.NonNull;

public interface ComparableNode<T extends Comparable<T>>{
  @NonNull Comparator<T> comparator();
  T        value();
  void     setValue(T value);
  default  int compare(ComparableNode<T>e){return comparator().compare(value(), e.value());}
  default  int compareInverse(ComparableNode<T>e){return comparator().compare( e.value(), value());}
  default  boolean eq(ComparableNode<T>e) {return value().equals(e.value());}
  default  boolean lt(ComparableNode<T>e) {return compare(e) <  0;}
  default  boolean le(ComparableNode<T>e) {return compare(e) <= 0;}
  default  boolean gt(ComparableNode<T>e) {return compare(e) >  0;}
  default  boolean ge(ComparableNode<T>e) {return compare(e) >= 0;}
}