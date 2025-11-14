/**
 * 
 */
package algos.sort;

import algos.heaps.BinaryHeap;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static algos.sort.PIVOT_STRATEGY.RANDOM;

//Using lombok annotation for log4j handle
//Log4j Handle creator (from lombok)
public class KthSelector {

	public int quickSelect(List<Integer> numbers, int low, int high, int index) {
		return quickSelect(Integer::compareTo, numbers, low, high, index);
	}

	public int quickSelectKthLargest(List<Integer> numbers, int low, int high, int kthLarges) {
		return quickSelect(Integer::compareTo, numbers, low, high, numbers.size() - kthLarges);
	}

	public int quickSelectKthSmallest(List<Integer> numbers, int low, int high, int kthSmallest) {
		return quickSelect(Integer::compareTo, numbers, low, high, kthSmallest - 1);
	}

	public <T> T quickSelect(Comparator<T> comparator, List<T> numbers, int low, int high, int index) {
		if (low >= high)
			return numbers.get(high);///because low can be higher than high so to be safe choose high
		int q = partitioner.partition(RANDOM, comparator, numbers, low, high);
		// kth smallest means k-1; n-k means kth largest
		if (index == q)
			return numbers.get(q);
		else if (index < q)
			return quickSelect(comparator, numbers, low, q - 1, index);
		else
			return quickSelect(comparator, numbers, q + 1, high, index);
	}
	PartitionStrategy partitioner = Partitioner.LOMUTO;


	public <T> T kthSelect(Comparator<T> comparator, Stream<T> numbers, int k) {
		BinaryHeap<T> heap = new BinaryHeap<>(comparator, k);
		numbers.forEach(heap::offer);
		T topK =  heap.peek();
		List<T> topKElements = new ArrayList<>();
		while ( ! heap.isEmpty()) {
			topKElements.add(heap.poll());
		}
		System.out.println(topKElements);
		return topK;
	}

	public static void main(String[] args) {
		//ArrayList<Integer> input = Lists.newArrayList(5, 1, 8, 3, 9, 4, 41, 7, 35, 13, 19,
		//        15, 16,30, 29, 34, 25, 28, 22, 24)
		//ArrayList<Integer> input = Lists.newArrayList(-913743, 3241, 1001, 978, 999999, 1340, 1243153, 0, 0, 999999999)
		ArrayList<Integer> input = new Random().ints(100).boxed().distinct()
				.collect(Collectors.toCollection(Lists::newArrayList));
		System.out.println(new KthSelector().kthSelect(Integer::compareTo, input.stream(), 5));
		int k = 5;
		System.out.println(k + "thLargest:" +
				new KthSelector().quickSelectKthLargest(input, 0, input.size() - 1,  k ));
	}
}