package algos.sort;

import java.util.Arrays;
import static algos.utils.Utils.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

public class Quicksort {
	private int[] numbers;
	private int number;
	private RandomDataGenerator random = new RandomDataGenerator();
	PIVOT_STRATEGY pivoting = PIVOT_STRATEGY.MEDIAN;

	public static void main(String[] args) {
		int[] input = { 10, 9, 8, 7, 6, 5, 11, 23, 45, 67, 89, 32, 37, 52, 43,
				68, 14, 4, 3, 2, 1 };

		new Quicksort(PIVOT_STRATEGY.RANDOM).sort(input);
	}

	public Quicksort(PIVOT_STRATEGY s) {
		this.pivoting = s;
	}

	public void sort(int[] values) {
		// check for empty or null array
		if (values == null || values.length == 0) {
			return;
		}
		this.numbers = Arrays.copyOf(values, values.length);
		number = values.length;
		//
		for (int i = 0; i < number; i++) {
			this.numbers = Arrays.copyOf(values, values.length);
			System.out.println("quickselect[" + i + "]:"
					+ quickselect(0, number - 1, i));
		}
		this.numbers = Arrays.copyOf(values, values.length);
		quicksort(0, number - 1);
		System.out.println(ArrayUtils.toString(numbers));
	}

	private int partition(int left, int right) {
		int pivot = pivoting.pivot(numbers, left, right);
		int pivotVal = numbers[pivot];

		// Swap the pivot with right most
		swap(numbers, pivot, right);

		// Start with left
		int storeIndex = left--;
		//While entering left will be negative; but due to post incr itsback to 0
		while (left++ < right) {
			if (numbers[left] < pivotVal) 
				swap(numbers, left, storeIndex++);
		}

		// Swap storeIndex with rightmost
		swap(numbers, right, storeIndex);
		return storeIndex;
	}

	// ith Largest
	private int quickselect(int low, int high, int i) {

		while (high > low) {
			int pivotIndex = partition(low, high);
			if (pivotIndex - low == i) {
				high = low = pivotIndex;
			} else if (pivotIndex - low < i) {
				i -= (pivotIndex - low) + 1;
				low = pivotIndex + 1;
			} else {
				high = pivotIndex - 1;
			}
		}
		return numbers[low];

	}

	private void quicksort(int low, int high) {
		if (low >= high)
			return;
		int q = partition(low, high);
		/**
		 * <pre>
		 * int i = low, j = high, pivot = numbers[q];
		 * while (i &lt;= j) {
		 * 	while (numbers[i] &lt; pivot)
		 * 		i++;
		 * 	while (numbers[j] &gt; pivot)
		 * 		j--;
		 * 	if (i &lt;= j)
		 * 		exchange(i++, j--);
		 * }
		 * </pre>
		 */
		quicksort(low, q - 1);
		quicksort(q + 1, high);
	}
}