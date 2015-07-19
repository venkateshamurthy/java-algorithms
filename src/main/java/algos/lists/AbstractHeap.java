package algos.lists;

import static java.util.Collections.swap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import lombok.Data;

@Data
public abstract class AbstractHeap<E extends Comparable<E>> implements Heap<E> {

	protected final List<E> heap;
	private int heapSize;
	
	protected final Comparator<E> comparator;

	protected static class MIN_COMPARATOR<T extends Comparable<T>> implements Comparator<T>{
		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	}
	protected static class MAX_COMPARATOR<T extends Comparable<T>> implements Comparator<T>{
		@Override
		public int compare(T o1, T o2) {
			return o2.compareTo(o1);
		}
	}
	public Heap<E> build() {
		for (int i = heap.size() / 2; i > 0; i--)
			heapify(i);
		return this;
	}

	
	public boolean compare(int current, int other){
		return current >=0 && current<heap.size() && other>=0 && other<heap.size() && 
				comparator.compare(heap.get(current), heap.get(other))<0;
	}
	@Override
	public final int heapifyUp(int loc) {
		while (compare(loc,parent(loc))) {
			swap(heap, loc, parent(loc));
			loc = parent(loc);
		}
		return loc;
	}

	@Override
	public final void heapify(int i) {
		int left = leftChild(i); // index of node i's left child
		int right = rightChild(i); // index of node i's right child
		int extreme = i; // will hold the index of the node with the extreme( largest/smallest)
							// element

		if (compare(left,i))
			extreme = left; // yes, so the left child is the extreme( largest/smallest) so far

		if (compare(right,extreme))
			extreme = right; // yes, so the right child is the extreme( largest/smallest)

		if (extreme != i) {
			swap(heap, i, extreme);
			heapify(extreme);
		}
	}
	public AbstractHeap() {
		this(new ArrayList<E>(),new MIN_COMPARATOR<E>());
	}

	public AbstractHeap(final List<E> heap,Comparator<E> comparator) {
		this.heap = (heap);
		this.comparator=comparator;
	}

	public AbstractHeap(E[] heap) {
		this(Arrays.asList(heap),new MIN_COMPARATOR<E>());
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
		return heap.contains(o);
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
		return heap.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return heap.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return heap.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return heap.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return heap.retainAll(c);
	}

	@Override
	public void clear() {
		heap.clear();
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

	protected static int leftChild(int i) {
		return i << 2 + 1;
	}

	protected static int rightChild(int i) {
		return i << 2 + 2;
	}

	protected static int parent(int i) {
		return (i - 1) / 2;
	}
	
	public E poll() {
		if (heap.size() <= 0)
			return null;
		else {
			E val = heap.get(0);
			heap.set(0, heap.get(heap.size() - 1)); // Move last to position 0
			heap.remove(heap.size() - 1);
			heapify(0);
			return val;
		}
	}
	
	public boolean add(E element) {
		if (heap.add(element)) {// Put new value at end;
			// Swap with parent until parent not larger
			heapifyUp(heap.size() - 1);
			return true;
		} else
			throw new IllegalStateException();
	}

}