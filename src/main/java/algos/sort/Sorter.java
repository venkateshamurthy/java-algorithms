/**
 * 
 */
package algos.sort;

import static algos.utils.Utils.swap;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import algos.lists.MaxHeap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 * 
 */
@Data
@Slf4j
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sorter {
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
		else if (name.equals("heap"))
      return sorter.new HeapSort();

		return sorter.new QuickSort();

	}

	@Data
	class InsertionSort implements SortInterface {
		public int[] sort() {
			for (int i = 1; i < a.length; i++) {
				for (int j = i; j > 0; j--)
					if (a[j - 1] > a[j])
						swap(a, j - 1, j);
			}
			return a;
		}
	}

	// a[0,1,2....length-1]
	class BubbleSort implements SortInterface {
		public int[] sort() {
			for (int i = 0; i < a.length - 1; i++) {
				int temp;
				for (int j = 0; j < a.length - i; j++) {
					// swap only if required
					if (a[j] > a[j + 1]) {
						temp = a[j]; // first load a[j] to temp
						a[j] = a[j + 1]; // next load a[j+1] to a[j]
						a[j + 1] = temp; // next load temp to a[j+1]
						// swap(a,j,j+1);
					}
				}
			}
			return a;
		}

		public int[] sort(int a[]) {
			for (int i = 0; i < a.length - 1; i++) {
				int temp;
				for (int j = 0; j < a.length - i; j++) {
					if (a[j] > a[j + 1]) {
						temp = a[j];     // first load a[j] to temp
						a[j] = a[j + 1]; // next load a[j+1] to a[j]
						a[j + 1] = temp; // next load temp to a[j+1]
						// swap(a,j,j+1);
					}
				}
			}
			return a;
		}
	}

	//   0 1 2 3 4 5 6 7 8 9
	//  {8 3 4 2 7 1 6 5 9 0}
	//i  |               |
	//j    |       |   |   |
	class SelectionSort implements SortInterface {
		public int[] sort() {
			for (int i = 0; i < a.length - 1; i++) {
				int pos = i, min = a[pos];
				for (int j = i + 1; j < a.length; j++){
					if (a[j] < min) {
						pos = j;
						min = a[pos];
					}
				}
				int temp = a[i]; // first load a[j] to temp
				a[i] = a[pos]; // next load a[j+1] to a[j]
				a[pos] = temp; // next load temp to a[j+1]
				//swap(a, i, index);
			}
			return a;
		}
	}

	class MergeSort implements SortInterface {
		int[] b;

		@Override
		public int[] sort() {
			b = new int[(a.length)+1 / 2];
			log.debug(ArrayUtils.toString(a));
			return mergeSort(a, 0, a.length - 1);
		}

		protected int[] mergeSort(int[] a, int i, int j) {
			if (i < j) {
				int mid = i + (j - i) / 2;
				mergeSort(a, i, mid);
				mergeSort(a, mid + 1, j);
				merge(a, i, mid, mid + 1 , j);
			}
			return a;
		}

		protected int[] merge(int[] a, final int leftS, final int leftE,final int rightS, final int rightE) {
			int i = leftS, j = rightS, k = 0;
			while (i <= leftE && j <= rightE) 
				b[k++] = a[i] <= a[j] ? a[i++] : a[j++];
			while (i <= leftE)
				b[k++] = a[i++];
			while (j <= rightE)
				b[k++] = a[j++];
			System.arraycopy(b, 0, a, leftS, rightE-leftS+1);
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
		@Override
		public int select(int low, int high, int i) {

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

	class HeapSort implements SortInterface {

		@Override
		public int[] sort() {
			MaxHeap<Integer> heap = new MaxHeap<Integer>(Arrays.asList((ArrayUtils.toObject(a))));
			for(int i=a.length-1;i>=0;) {
			  Collections.swap(heap,0,i);
			  i--;
			  heap.heapifyDown(0,i);
			}
			return ArrayUtils.toPrimitive(heap.toArray(new Integer[0]));
		}

	}

	class CountingSort implements SortInterface {
		private final int k;

		public CountingSort(int k) {
			this.k = k;
		}

		@Override
		public int[] sort() {
			int c[] = new int[k];

			for (int i = 0; i < a.length; i++)
				c[a[i]]++;

			for (int i = 1; i < k; i++)
				c[i] += c[i - 1];

			int b[] = new int[a.length];

			for (int i = a.length - 1; i >= 0; i--)
				b[--c[a[i]]] = a[i];

			return b;
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

		@Override
		public int pivot(int[] numbers, int low, int high) {
			return low + (high - low) / 2;
		}

	},
	RANDOM() {

		@Override
		public int pivot(int[] numbers, int low, int high) {
			return random.nextInt(low, high);
		}

	},
	MEDIAN_OF_3() {

		@Override
		public int pivot(int[] numbers, int low, int high) {
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