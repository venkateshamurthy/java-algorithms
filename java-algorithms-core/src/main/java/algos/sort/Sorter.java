/**
 *
 */
package algos.sort;

import static algos.utils.Utils.swap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import algos.lists.MaxHeap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author vmurthy
 *
 */
@Data

@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Sorter {
    int[] a;

    public static SortInterface of(String name, int[] a) {
        Sorter sorter = new Sorter(a);
        if (name.equals("insertion"))
            return sorter.new InsertionSort();
        else if (name.equals("binsertion"))
            return sorter.new BinaryInsertionSort();
        else if (name.equals("selection"))
            return sorter.new SelectionSort();
        else if (name.equals("merge"))
            return sorter.new MergeSort();
        else if (name.equals("quick"))
            return sorter.new QuickSort();
        else if (name.equals("heap"))
            return sorter.new HeapSort();
		else if (name.equals("count"))
			return sorter.new CountingSort(10);

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

    class BinaryInsertionSort implements SortInterface {
        public int[] sort() {
			/*
			for (int i = 0; i < arr.size(); i++) {
				int x = a[i];
				int k = Math.abs(Arrays.binarySearch(a, 0, i, x) + 1);
				System.arraycopy(a, k, a, k+1, i-k);
				a[k]=x;
			}
			System.out.println("a:"+Arrays.toString(a));
			 */
            List<Integer> arr = Arrays.stream(a).boxed().collect(Collectors.toList());
            int[] b = arr.stream().mapToInt(Integer::intValue).toArray();
            for (int i = 0; i < arr.size(); i++) {
                int x = arr.get(i);
                int j = i - 1;
                while (j >= 0 && arr.get(j) > x) {
                    arr.set(j + 1, arr.get(j));
                    j--;
                }
                arr.set(j + 1, x);
            }

            System.out.println(arr);
            return arr.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    // a[0,1,2....length-1]
    class BubbleSort implements SortInterface {
        public int[] sort() {
            for (int i = 0; i < a.length - 1; i++) {
                int temp;
                for (int j = a.length - 1; j >= i + 1; j--) {
                    // swap only if required
                    if (a[j - 1] > a[j]) {
                        temp = a[j]; // first load a[j] to temp
                        a[j] = a[j - 1]; // next load a[j+1] to a[j]
                        a[j - 1] = temp; // next load temp to a[j+1]
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
                for (int j = i + 1; j < a.length; j++) {
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
        //int[] b;

        @Override
        public int[] sort() {
            //b = new int[(a.length+1)/2];
            //log.debug(ArrayUtils.toString(a));
            return mergeSort(a, 0, a.length - 1);
        }

        protected int[] mergeSort(int[] a, int i, int j) {
            if (i < j) {
                int mid = i + (j - i) / 2;  // (i+j)/2
                mergeSort(a, i, mid);
                mergeSort(a, mid + 1, j);
                merge(a, i, mid, j);
            }
            return a;
        }

        protected int[] merge(int[] a, final int l, final int mid, final int r) {
            int a1[] = new int[mid - l + 1];
            for (int i = l; i <= mid; i++) a1[i - l] = a[i];

            int a2[] = new int[r - mid];
            for (int i = mid + 1; i <= r; i++) a2[i - mid - 1] = a[i];

            int i = 0, j = 0, k = l;

            //merging the elements of both the ranges.
            while (i < a1.length && j < a2.length) {
                if (a1[i] <= a2[j]) a[k++] = a1[i++];
                else a[k++] = a2[j++];
            }
            //checking if any of the elements left.
            while (i < a1.length) a[k++] = a1[i++];
            while (j < a2.length) a[k++] = a2[j++];
            return a;
        }

    }

    class QuickSort implements SortInterface {
        int[] numbers;
        PIVOT_STRATEGY pivotingStrategy = PIVOT_STRATEGY.RANDOM;
        PartitionStrategy partitionStrategy = Partitioner.HOARE;

        public int[] sort() {
            this.numbers = Arrays.copyOf(a, a.length);
            quicksort(0, numbers.length - 1);
            return numbers;
        }

        protected int partition(int left, int right) {
            int pivot = pivotingStrategy.pivot(numbers, left, right);
            swap(numbers, pivot, 0);// swap the pivot obtained to
            return partitionStrategy.partition(numbers, left, right);
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
            for (int i = a.length - 1; i >= 0; ) {
                Collections.swap(heap, 0, i);
                i--;
                heap.heapifyDown(0, i);
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

interface PartitionStrategy {
    int partition(int[] numbers, int start, int end);
}

enum Partitioner implements PartitionStrategy {
    LOMOTO {
        public int partition(int[] numbers, int start, int end) {
            int smaller = start;
            for (int bigger = start + 1; bigger < end; bigger++) {
                if (numbers[bigger] < numbers[start]) {
                    smaller++;
                    swap(numbers, smaller, bigger);
                }
            }
            swap(numbers, smaller, start);
            return smaller;
        }
    },
    HOARE {
        public int partition(int[] a, int start, int end) {
            int smaller = start + 1, bigger = end;
            while (smaller <= bigger) {
                if (a[smaller] < a[start]) smaller++;
                else if (a[bigger] >= a[start]) bigger--;
                else swap(a, smaller++, bigger--);
            }
            swap(a, start, bigger);
            return bigger;
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
            int[] pivots = new int[]{numbers[low], numbers[middle], numbers[high]};
            Arrays.sort(pivots);
            return pivots[1];
			/*
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
			} else { // if(wEnd>wBegin)
				if (wMiddle < wBegin)
					return low;
				else if (wMiddle > wEnd)
					return high;
				else
					return middle;
			}
			*/
        }

    };

    RandomDataGenerator random = new RandomDataGenerator();
}