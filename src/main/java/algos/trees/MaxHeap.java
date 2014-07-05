package algos.trees;

import static java.util.Collections.swap;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

/**
 * @authot vmurthy
 */
public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
	private ArrayList<E> a, heap;

	public MaxHeap() {
		a = heap = new ArrayList<E>();
	}

	public E extractTop() {
		if (heap.size() <= 0)
			return null;
		else {
			E maxVal = heap.get(0);
			heap.set(0, heap.get(heap.size() - 1)); // Move last to position 0
			heap.remove(heap.size() - 1);
			heapify(0);
			return maxVal;
		}
	}

	public void insert(E element) {
		heap.add(element); // Put new value at end;
		int loc = heap.size() - 1; // and get its location

		// Swap with parent until parent not larger
		while (loc > 0 && heap.get(loc).compareTo(heap.get(parent(loc))) > 0) {
			swap(heap, loc, parent(loc));
			loc = parent(loc);
		}
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}

	public E top() {
		if (heap.isEmpty())
			return null;
		else
			return heap.get(0);
	}

	@Override public void heapify(int i) {
		int left = leftChild(i); // index of node i's left child
		int right = rightChild(i); // index of node i's right child
		int largest = i; // will hold the index of the node with the largest
							// element

		if (left <= a.size() - 1 && a.get(left).compareTo(a.get(i)) > 0)
			largest = left; // yes, so the left child is the largest so far

		if (right <= a.size() - 1
				&& a.get(right).compareTo(a.get(largest)) > 0)
			largest = right; // yes, so the right child is the largest

		if (largest != i) {
			swap(a, i, largest);
			heapify(largest);
		}
	}

	private static int leftChild(int i) {
		return 2 * i + 1;
	}

	private static int rightChild(int i) {
		return 2 * i + 2;
	}

	private static int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * A testing program
	 */
	public static void main(String[] args) {
		System.out.println(parent(0));
		Heap<String> pq = new MaxHeap<String>();
		pq.insert("cat");
		pq.insert("dog");
		pq.insert("bee");
		System.out.println("Largest is: " + pq.top());
		System.out.println("Largest again should be same as: " + pq.extractTop());
		System.out.println("Next largest is " + pq.extractTop());
		System.out.println("Is it empty? : " + pq.isEmpty());
		pq.insert("eagle");
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Is it empty? : " + pq.isEmpty());
		System.out.println("Max of empty queue: " + pq.top());
		System.out.println("Remove max of empty queue: " + pq.extractTop());
		pq.insert("bear");
		System.out.println("Largest is: " + pq.top());
		System.out.println("Largest again: " + pq.extractTop());
		pq.insert("cat");
		pq.insert("dog");
		pq.insert("sheep");
		pq.insert("cow");
		pq.insert("eagle");
		pq.insert("bee");
		pq.insert("lion");
		pq.insert("tiger");
		pq.insert("zebra");
		pq.insert("ant");
		System.out.println("Bigger example:");
		System.out.println("Largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());
		System.out.println("Next largest is: " + pq.extractTop());

		MaxHeap<Integer> rs = new MaxHeap<Integer>();
		rs.insert(4);
		rs.insert(3);
		rs.insert(1);
		System.out.println("Largest is: " + rs.top());
		System.out.println("Largest again: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Is it empty? : " + rs.isEmpty());
		rs.insert(5);
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Is it empty? : " + rs.isEmpty());
		System.out.println("Max of empty queue: " + rs.top());
		System.out.println("Remove max of empty queue: " + rs.extractTop());
		rs.insert(10);
		System.out.println("Largest is: " + rs.top());
		System.out.println("Largest again: " + rs.extractTop());
		rs.insert(20);
		rs.insert(19);
		rs.insert(18);
		rs.insert(17);
		rs.insert(16);
		rs.insert(15);
		rs.insert(14);
		rs.insert(13);
		rs.insert(12);
		rs.insert(11);
		System.out.println("Bigger example:");
		System.out.println("Largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		System.out.println("Next largest is: " + rs.extractTop());
		
		Integer a[] = { 4, 7, 1, 3, 2, 9, 5, 8, 6,11,19,18,22,23,13,15,12 ,21};
		int kth = 1;
		for (int i =0;i<a.length;i++) {
			rs.insert(a[i]);
			if (i >= kth)
				System.out.println("Removed:"+rs.extractTop());
			else
				System.out.println("Accessed:"+rs.top());
		}
		System.out.println("The kth smallest :" + rs.top());
		System.out.println(ArrayUtils.toString(rs.a.toArray()));

		
	}
}
