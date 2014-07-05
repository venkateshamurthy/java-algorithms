package algos.trees;

public interface Heap<E extends Comparable<E>> {
	public boolean isEmpty();

	public void insert(E element);

	public E top();

	public E extractTop();

	void heapify(int i);
}