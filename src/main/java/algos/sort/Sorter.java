/**
 * 
 */
package algos.sort;

import static algos.utils.Utils.swap;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sorter {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
	int[] a;

	public static SortInterface of(String name, int[] a) {
		Sorter sorter = new Sorter(a);
		if (name.equals("insertion"))
			return sorter.new InsertionSort();
		else if (name.equals("selection"))
			return sorter.new SelectionSort();
		else if (name.equals("merge"))
			return sorter.new MergeSort();
		else if (name.equals("quick"))
			return sorter.new QuickSort();
		return sorter.new QuickSort();

	}

	@Data
	class InsertionSort implements SortInterface {
		public int[] sort() {
			for (int i = 1; i < a.length; i++) {
				for (int j = i; j > 0; j--)
					swap(a, j - 1, j);
			}
			return a;
		}
	}

	class SelectionSort implements SortInterface {
		public int[] sort() {
			for (int i = 0; i < a.length - 1; i++) {
				int index = i, min = a[index];
				for (int j = i + 1; j < a.length; j++)
					if (a[j] < min) {
						index = j;
						min = a[index];
					}
				swap(a, i, index);
			}
			return a;
		}
	}

	class MergeSort implements SortInterface {
		int[] b;

		@Override public int[] sort() {
			b = new int[(a.length)];
			log.debug(ArrayUtils.toString(a));
			return mergeSort(a, 0, a.length - 1);
		}

		protected int[] mergeSort(int[] a, int i, int j) {
			if (i < j) {
				int mid = i + (j - i) / 2;
				mergeSort(a, i, mid);
				mergeSort(a, mid + 1, j);
				merge(a, i, mid+1, j);
			}
			return a;
		}

		protected int[] merge(int[] a, int l, int m, int r) {
			int i = l, j = m -1, k = l;
			System.arraycopy(a, 0, b, 0, j-l);
			while (i < m && j < r) {
				if (a[i] <= b[j])
					a[k] = a[i++];
				else
					a[k] = b[j++];
				k++;
			}
			while (i < m)
				a[k++] = a[i++];
			while (j < r)
				a[k++] = a[j++];
			return a;
		}

	}

	class QuickSort implements SortInterface {
		int[] numbers;
		PIVOT_STRATEGY pivotingStrategy = PIVOT_STRATEGY.MEDIAN_OF_3;

		public int[] sort() {
			this.numbers = Arrays.copyOf(a, a.length);
			quicksort(0, numbers.length - 1);
			return numbers;
		}

		protected int partition(int left, int right) {
			int pivot = pivotingStrategy.pivot(numbers, left, right);
			int pivotVal = numbers[pivot];

			// Swap the pivot with right most
			swap(numbers, pivot, right);

			// Start with left
			int storeIndex = left;
			do {
				if (numbers[left] < pivotVal) {
					swap(numbers, left, storeIndex);
					storeIndex++;
				}
			} while (left++ < right);

			// Swap storeIndex with rightmost
			swap(numbers, right, storeIndex);
			return storeIndex;
		}

		public void quicksort(int low, int high) {
			if (low >= high)
				return;
			int q = partition(low, high);
			/**
			 * <code>
			 * int i = low, j = high, pivot = numbers[q];
			 * while (i &lt;= j) {
			 * 	while (numbers[i] &lt; pivot)
			 * 		i++;
			 * 	while (numbers[j] &gt; pivot)
			 * 		j--;
			 * 	if (i &lt;= j)
			 * 		exchange(i++, j--);
			 * }
			 * </code>
			 */
			quicksort(low, q - 1);
			quicksort(q + 1, high);
		}
	}

	class QuickSelect extends QuickSort implements KthSelector {
		/**
		 * Quick Select
		 * 
		 * @param low
		 * @param high
		 * @param ith
		 *            largest
		 * @return
		 */
		@Override public int select(int low, int high, int i) {

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
	}
}

/**
 * A Pivoting strategy
 * 
 * @author vmurthy
 * 
 */
interface PivotingStrategy {
	int pivot(int[] numbers, int low, int high);
}

/**
 * Enum implementation of {@link PivotingStrategy}
 * 
 * @author vmurthy
 * 
 */
enum PIVOT_STRATEGY implements PivotingStrategy {

	MEDIAN() {

		@Override public int pivot(int[] numbers, int low, int high) {
			return low + (high - low) / 2;
		}

	},
	RANDOM() {

		@Override public int pivot(int[] numbers, int low, int high) {
			return random.nextInt(low, high);
		}

	},
	MEDIAN_OF_3() {

		@Override public int pivot(int[] numbers, int low, int high) {
			final int middle = low + (high - low) / 2;
			final double wBegin = numbers[low];
			final double wMiddle = numbers[middle];
			final double wEnd = numbers[high];
			if (wBegin < wEnd) {
				if (wMiddle < wBegin)
					return low;
				else if (wMiddle > wEnd)
					return high;
				else
					return middle;
			} else /* if(wEnd>wBegin) */{
				if (wMiddle < wBegin)
					return low;
				else if (wMiddle > wEnd)
					return high;
				else
					return middle;
			}
		}

	};
	RandomDataGenerator random = new RandomDataGenerator();
}