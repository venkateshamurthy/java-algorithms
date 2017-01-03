package algos.lists;

import static java.util.Collections.swap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.SetUtils;
import org.springframework.util.Assert;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(includeFieldNames = false, of = { "heap" })
public abstract class AbstractHeap<E extends Comparable<E>> implements Heap<E> {

  protected final List<E> heap;

  protected final Comparator<E> comparator;
  protected final Map<E, Integer> indexMap = new HashMap<>();

  protected static class MIN_COMPARATOR<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
      return o1.compareTo(o2);
    }
  }

  protected static class MAX_COMPARATOR<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
      return o2.compareTo(o1);
    }
  }

  public AbstractHeap() {
    this(new ArrayList<E>(), new MIN_COMPARATOR<E>());
  }

  public AbstractHeap(E[] heap) {
    this(new ArrayList<>(Arrays.asList(heap)), new MIN_COMPARATOR<E>());
  }

  public AbstractHeap(final List<E> heap, Comparator<E> comparator) {
    this.heap = (heap);
    this.comparator = comparator;
    for (int i = 0; i < heap.size(); i++) {
      indexMap.put(heap.get(i), i);
    }
    build();
  }

  public Heap<E> build() {
    for (int i = (size() - 1) >> 1; i >= 0; i--)
      heapifyDown(i);
    return this;
  }

  private boolean compare(int current, int other) {
    return current >= 0 && current < heap.size() && other >= 0 && other < heap.size()
        && comparator.compare(heap.get(current), heap.get(other)) < 0;
  }

  @Override
  public void exchange(int posA, int posB) {
    Assert.isTrue(posA >= 0 && posA < heap.size());
    Assert.isTrue(posB >= 0 && posB < heap.size());
    indexMap.put(heap.get(posA), posB);
    indexMap.put(heap.get(posB), posA);
    swap(heap, posA, posB);
  }

  @Override
  public final int heapifyUp(int location) {
    Assert.isTrue(location < size());
    while (compare(location, location >> 1)) {
      exchange(location, location >> 1);
      location >>= 1;
    }
    return location;
  }

  @Override
  public final void heapifyDown(int location) {
    /**
     * <pre>
     * int left = (location << 1) + 1; // index of node i's left child
     * int right = (location << 1) + 2; // index of node i's right child
     * int extreme = location; // Find out which is real extreme(i.e min/max)
     * if (compare(left, location))
     *   extreme = left;
     * 
     * if (compare(right, extreme))
     *   extreme = right;
     * 
     * if (extreme != location) {
     *   exchange(location, extreme);
     *   heapifyDown(extreme);
     * }
     * </pre>
     **/
    heapifyDown(location, size() - 1);
  }

  @Override
  public final void heapifyDown(int location, int limit) {
    int left = (location << 1) + 1; // index of node i's left child
    int right = (location << 1) + 2; // index of node i's right child
    int extreme = location; // Find out which is real extreme(i.e min/max)
    if (left <= limit && compare(left, location))
      extreme = left;

    if (right <= limit && compare(right, extreme))
      extreme = right;

    if (extreme != location) {
      exchange(location, extreme);
      heapifyDown(extreme, limit);
    }
  }

  @Override
  public boolean offer(E e) {
    boolean offered = false;
    try {
      offered = add(e);
    } finally {

    }
    return offered;
  }

  @Override
  public E remove() {
    return poll();
  }

  @Override
  public E element() {
    return heap.iterator().next();
  }

  @Override
  public int size() {
    return heap.size();
  }

  @Override
  public boolean contains(Object o) {
    return indexMap.containsKey(o);// heap.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    return heap.iterator();
  }

  @Override
  public Object[] toArray() {
    return heap.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return heap.toArray(a);
  }

  @Override
  public boolean remove(Object o) {
    indexMap.remove(o);
    return heap.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return heap.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    int index = heap.size();
    for (E o : c)
      indexMap.put(o, index++);
    boolean result = heap.addAll(c);

    if (result)
      build();
    return result;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    for (Object o : c)
      indexMap.remove(o);
    return heap.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    boolean result = heap.retainAll(c);

    if (result) {
      indexMap.keySet().retainAll(c);
      build();
    }
    return result;
  }

  @Override
  public void clear() {
    heap.clear();
    indexMap.clear();
  }

  @Override
  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public E peek() {
    if (heap.isEmpty())
      return null;
    else
      return heap.get(0);
  }

  public E poll() {
    if (heap.size() <= 0)
      return null;
    else {
      E val = heap.get(0);
      heap.set(0, heap.get(heap.size() - 1)); // Move last to position 0
      heap.remove(heap.size() - 1);
      indexMap.remove(val);
      heapifyDown(0);
      return val;
    }
  }

  public boolean add(E element) {
    if (heap.add(element)) {
      indexMap.put(element, heap.size() - 1);
      heapifyUp(heap.size() - 1);
      return true;
    } else
      throw new IllegalStateException("Could not add to heap!.Is this bounded/readonly collection?");
  }

  public void changeKey(int current, E changedPriorityElement) {
    Assert.isTrue(current >= 0 && current < heap.size());
    int comparisonResult = comparator.compare(changedPriorityElement, heap.get(current));
    heap.set(current, changedPriorityElement);
    heapify(current, comparisonResult, changedPriorityElement);
  }

  public int indexOf(E element) {
    if (!indexMap.containsKey(element))
      indexMap.put(element, heap.indexOf(element));
    return indexMap.get(element);
  }

  private void heapify(int currentIndex, int upOrDown, E changedPriorityElement) {
    if (upOrDown < 0) {
      heapifyUp(currentIndex);
    } else if (upOrDown > 0) {
      heapifyDown(currentIndex);
    } else
      log.warn("Did not heapify! at index {} with the newer value {} " + "due to equal priority of existing element {}",
          currentIndex, changedPriorityElement, heap.get(currentIndex));
  }

}