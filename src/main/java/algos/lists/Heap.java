package algos.lists;

import java.util.Collection;

public interface Heap<E extends Comparable<E>> {
  boolean isEmpty();
  boolean contains(Object o);
  boolean add(E element);
  boolean remove(Object o);
  boolean addAll(Collection<? extends E> c);
  boolean removeAll(Collection<?> c);
  void clear();
  E peek();
  E poll();

  /**
   * Heapify Down operation
   * 
   * @param location
   *          at which the heapify down occurs
   * @param limit
   *          is the limiting end index of heap array till heapifyDown can be
   *          performed
   */
  void heapifyDown(int location, int limit);

  /**
   * Heapify Up operation
   * 
   * @param location
   *          at which the heapify occurs
   * @return
   */
  int heapifyUp(int location);

  /**
   * Index of the element.
   * 
   * @param element
   *          foe which index is to be found
   * @return index in the list
   */
  int indexOf(E element);

  /**
   * Change priority at the index.
   * 
   * @param current
   *          index at which changeKey is attempted
   * @param changedPriorityElement
   */
  void changeKey(int current, E changedPriorityElement);

  /**
   * Exchange elements at indexes
   * 
   * @param posA
   * @param posB
   */
  void exchange(int posA, int posB);
}