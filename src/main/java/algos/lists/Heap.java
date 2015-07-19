package algos.lists;

import java.util.Queue;

public interface Heap<E extends Comparable<E>> extends Queue<E>{

	void heapify(int i);
	int heapifyUp(int loc);
}