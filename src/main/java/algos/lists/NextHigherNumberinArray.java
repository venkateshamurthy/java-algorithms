/**
 * 
 */
package algos.lists;

import java.util.Arrays;
import java.util.Stack;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 *
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextHigherNumberinArray {

	static final int[] a = { 8, 7, 1, 3, 4, 5, 6, 10, 13, 9, 11 };

	/*
	 * Function to replace every element with the next greatest element
	 */
	static void nextGreatest(int arr[]) {
		int size = arr.length;

		// Initialize the next greatest element
		int max_from_right = arr[size - 1];

		// The next greatest element for the rightmost
		// element is always -1
		arr[size - 1] = -1;

		// Replace all other elements with the next greatest
		for (int i = size - 2; i >= 0; i--) {
			// Store the current element (needed later for
			// updating the next greatest element)
			int temp = arr[i];

			// Replace current element with the next greatest
			arr[i] = max_from_right;

			// Update the greatest element, if needed
			if (max_from_right < temp)
				max_from_right = temp;
		}
	}

	public static void getNGE(int[] a) {
		Stack<Integer> s = new Stack<Integer>();
		s.push(a[0]);

		for (int i = 1, pop = s.peek(); i < a.length; i++) {
			if (s.isEmpty()) {
				s.push(a[i]);
			} else {
				pop = s.pop();
				while (!s.empty() && pop < a[i]) {
					pop = s.pop();
					log.info(pop + ":" + a[i]);
				}
				if (pop > a[i])
					s.push(a[i]);
			}
		}
		while (!s.empty()) {
			log.info(s.pop() + "<-:->" + -1);
		}
	}

	public void printNextHigherNumber() {
		class IndexedA implements Comparable<IndexedA> {
			public IndexedA(int i, int i2) {
				value = i;
				index = i2;
			}

			int value;
			int index;

			@Override
			public int compareTo(IndexedA o) {
				if (value == o.value)
					return 0;
				else
					return (value > o.value) ? 1 : -1;
			}
		}
		IndexedA[] b = new IndexedA[a.length];
		for (int i = 0; i < a.length; i++)
			b[i] = new IndexedA(a[i], i);
		Arrays.sort(b);
		for (int i = 0; i < a.length - 1; i++)
			log.info(a[i] + " " + b[i + 1].value + " " + b[i + 1].index);
	}

	public static void main(String[] args) {
		// new NextHigherNumberinArray().printNextHigherNumber();
		getNGE(a);
	}
}
