package algos.lists;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 */
@Slf4j
public class MaxHeap<E extends Comparable<E>> extends AbstractHeap<E> {
		
	public MaxHeap(List<E> heap) {
		super(heap,new MAX_COMPARATOR<E>());
	}

	public MaxHeap() {
		this(new ArrayList<E>());
	}
	
	
	/**
	 * A testing program
	 */
	public static void main(String[] args) {
		Heap<String> pq = new MaxHeap<String>();
		pq.add("cat");
		pq.add("dog");
		pq.add("bee");
		log.info("Largest is: " + pq.peek());
		log.info("Largest again should be same as: " + pq.poll());
		log.info("Next largest is " + pq.poll());
		log.info("Is it empty? : " + pq.isEmpty());
		pq.add("eagle");
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Is it empty? : " + pq.isEmpty());
		log.info("Max of empty queue: " + pq.peek());
		log.info("Remove max of empty queue: " + pq.poll());
		pq.add("bear");
		log.info("Largest is: " + pq.peek());
		log.info("Largest again: " + pq.poll());
		pq.add("cat");
		pq.add("dog");
		pq.add("sheep");
		pq.add("cow");
		pq.add("eagle");
		pq.add("bee");
		pq.add("lion");
		pq.add("tiger");
		pq.add("zebra");
		pq.add("ant");
		log.info("Bigger example:");
		log.info("Largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());
		log.info("Next largest is: " + pq.poll());

		MaxHeap<Integer> rs = new MaxHeap<Integer>();
		rs.add(4);
		rs.add(3);
		rs.add(1);
		log.info("Largest is: " + rs.peek());
		log.info("Largest again: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Is it empty? : " + rs.isEmpty());
		rs.add(5);
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Is it empty? : " + rs.isEmpty());
		log.info("Max of empty queue: " + rs.peek());
		log.info("Remove max of empty queue: " + rs.poll());
		rs.add(10);
		log.info("Largest is: " + rs.peek());
		log.info("Largest again: " + rs.poll());
		rs.add(20);
		rs.add(19);
		rs.add(18);
		rs.add(17);
		rs.add(16);
		rs.add(15);
		rs.add(14);
		rs.add(13);
		rs.add(12);
		rs.add(11);
		log.info("Bigger example:");
		log.info("Largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());
		log.info("Next largest is: " + rs.poll());

		Integer a[] = { 4, 7, 1, 3, 2, 9, 5, 8, 6, 11, 19, 18, 22, 23, 13, 15,
				12, 21 };
		int kth = 1;
		for (int i = 0; i < a.length; i++) {
			rs.add(a[i]);
			if (i >= kth)
				log.info("Removed:" + rs.poll());
			else
				log.info("Accessed:" + rs.peek());
		}
		log.info("The kth smallest :" + rs.peek());
		log.info(StringUtils.arrayToCommaDelimitedString(rs.heap
				.toArray()));
	}
}
