package algos.lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.MapUtils;
import org.springframework.util.Assert;

import lombok.Data;
import lombok.ToString;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(includeFieldNames = false, of = { "elementData" }, callSuper = true)
public abstract class AbstractHeap<E extends Comparable<E>> extends ArrayList<E> implements Heap<E> {

  /** Serialization id. */
  private static final long serialVersionUID = 1L;
  protected final Comparator<E> comparator;
  /**
   * A map that keeps a mapping of value to the array index and is in lock-step
   * with heap add/remove/modifications. This is for improving performance of
   * value based searches.
   */
  protected final Map<E, Integer> indexMap;

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
    super();
    comparator = new MIN_COMPARATOR<E>();
    indexMap = new HashMap<>();
  }

  public AbstractHeap(E[] heap) {
    this(Arrays.asList(Objects.requireNonNull(heap)), new MIN_COMPARATOR<E>());
  }

  public AbstractHeap(final List<E> heap, Comparator<E> comparator) {
    super(Objects.requireNonNull(heap));
    this.comparator = Objects.requireNonNull(comparator);
    indexMap = new HashMap<>(heap.size());
    for (int i = 0; i < heap.size(); i++) {
      indexMap.put(heap.get(i), i);
    }
    for (int i = (heap.size() - 1) >> 1; i >= 0; i--)
      heapifyDown(i);
  }

  public void swap(int i, int j) {
    val elementI = get(i);
    val elementJ = get(j);
    indexMap.put(elementI, j);
    indexMap.put(elementJ, i);
    set(i, elementJ);
    set(j, elementI);
  }

  @Override
  public final int heapifyUp(int location) {
    Assert.isTrue(location < size());
    while (compare(location, location >> 1)) {
      swap(location, location >> 1);
      location >>= 1;
    }
    return location;
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
      swap(location, extreme);
      heapifyDown(extreme, limit);
    }
  }

  @Override
  public boolean contains(Object o) {
    return indexMap.containsKey(o);// super.contains(o);
  }

  @Override
  public boolean remove(Object o) {
    boolean res = super.remove(o);
    indexMap.remove(o);
    return res;
  }

  public E remove(int index) {
    E removed = super.remove(index);
    if (removed != null)
      indexMap.remove(removed);
    return removed;
  }

  @Override
  public boolean addAll(Collection<? extends E> collection) {

    List<E> list = new ArrayList<>(collection);// just to ensure the order of
                                               // iteration when adding to map
    int index = size(); // gather the index before addition
    boolean result = super.addAll(list);
    if (result) {
      for (E o : list)
        indexMap.put(o, index++); // use the index increment for the position
      for (int i = (size() - 1) >> 1; i >= 0; i--)
        heapifyDown(i);
    }
    return true;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    super.removeAll(c);
    indexMap.keySet().removeAll(c);
    return true;
  }

  @Override
  public void clear() {
    super.clear();
    indexMap.clear();
  }

  public E poll() {
    if (isEmpty())
      return null;
    else {
      E val = get(0);
      swap(0, size() - 1);
      this.remove(size() - 1);
      heapifyDown(0);
      return val;
    }
  }

  public boolean add(E element) {
    if (super.add(element)) {
      indexMap.put(element, size() - 1);
      heapifyUp(size() - 1);
      return true;
    } else
      throw new IllegalStateException("Could not add to heap!.Is this bounded/readonly collection?");
  }

  public void changeKey(int current, E changedPriorityElement) {
    Assert.isTrue(current >= 0 && current < size(), " current :" + current + " size:" + size());
    int comparisonResult = comparator.compare(changedPriorityElement, get(current));
    indexMap.put(changedPriorityElement, current);
    set(current, changedPriorityElement);
    heapify(current, comparisonResult, changedPriorityElement);
  }

  public int indexOf(E element) {
    int result = MapUtils.getInteger(indexMap, element, -1);
    // Assert.isTrue(result!=-1 && get(result).equals(element) || result==-1 &&
    // !contains(element),
    // "heap contains "+element+" is "+contains(element) + " but heap
    // index="+result);
    return result;
  }

  protected final void heapifyDown(int location) {
    heapifyDown(location, size() - 1);
  }

  private void heapify(int currentIndex, int upOrDown, E changedPriorityElement) {
    if (upOrDown < 0) {
      heapifyUp(currentIndex);
    } else if (upOrDown > 0) {
      heapifyDown(currentIndex);
    } else
      log.warn("Did not heapify! at index {} with the newer value {} " + "due to equal priority of existing element {}",
          currentIndex, changedPriorityElement, get(currentIndex));
  }

  private boolean compare(int current, int other) {
    return current >= 0 && current < size() && other >= 0 && other < size()
        && comparator.compare(get(current), get(other)) < 0;
  }

  // Some collection methods which just relies on the decorated heap array list
  // without additional decorations.

  public E peek() {
    return isEmpty() ? null : get(0);
  }

}