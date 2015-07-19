package algos.lists;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

/**
 * @authot vmurthy
 */
@Slf4j
public class MinHeap<E extends Comparable<E>> extends AbstractHeap<E> {

	public MinHeap() {
		super(new ArrayList<E>(),new MIN_COMPARATOR<E>());
	}


	/**
	 * A testing program
	 */
	public static void main(String[] args) {
		log.info("{}",parent(0));
		Heap<String> pq = new MinHeap<String>();
		pq.add("cat");
		pq.add("bee");
		log.info("Smallest is: " + pq.peek());
		log.info("Smallest again: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Is it empty? : " + pq.isEmpty());
		pq.add("eagle");
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Is it empty? : " + pq.isEmpty());
		log.info("Min of empty queue: " + pq.peek());
		log.info("Remove min of empty queue: " + pq.poll());
		pq.add("bear");
		log.info("Smallest is: " + pq.peek());
		log.info("Smallest again: " + pq.poll());
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
		log.info("Smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());
		log.info("Next smallest is: " + pq.poll());

		MinHeap<Integer> rs = new MinHeap<Integer>();
		rs.add(4);
		rs.add(3);
		rs.add(1);
		log.info("Smallest is: " + rs.peek());
		log.info("Smallest again: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Is it empty? : " + rs.isEmpty());
		rs.add(5);
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Is it empty? : " + rs.isEmpty());
		log.info("Min of empty queue: " + rs.peek());
		log.info("Remove min of empty queue: " + rs.poll());
		rs.add(10);
		log.info("Smallest is: " + rs.peek());
		log.info("Smallest again: " + rs.poll());
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
		log.info("Smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());
		log.info("Next smallest is: " + rs.poll());

		Integer a[] = { 4, 7, 1, 3, 2, 9, 5, 8, 6, 11, 19, 18, 22, 23, 13, 15,
				12, 21 };
		int kth = 3;
		for (int i = 0; i < rs.heap.size(); i++) {
			rs.add(a[i]);
			if (i >= kth)
				log.info("Removed:" + rs.poll());
			else
				log.info("Accessed:" + rs.peek());
		}
		log.info("The kth largest :" + rs.peek());
		log.info(StringUtils.arrayToCommaDelimitedString(rs.heap.toArray()));
	}
}
