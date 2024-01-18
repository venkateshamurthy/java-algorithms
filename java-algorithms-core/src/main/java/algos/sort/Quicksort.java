package algos.sort;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

class Quicksort {

    public static void main(String[] args) {
        ArrayList<Integer> input = Lists
                //.newArrayList(5, 1, 8, 3, 9, 4, 1, 7, 5)
                .newArrayList(-913743, 3241, 999999, 1243153, 0, 0, 999999999)
        ;
        ArrayList<Integer> result = quick_sort(input);
        System.out.println(result);
    }

    static ArrayList<Integer> quick_sort(ArrayList<Integer> arr) {
        // Write your code here.
        quickSort(arr, 0, arr.size() - 1);
        return arr;
    }

    static void quickSort(List<Integer> numbers, int low, int high) {
        if (low >= high)
            return;
        int q = partitioner.partition(numbers, low, high);
        quickSort(numbers, low, q - 1);
        quickSort(numbers, q + 1, high);
    }

    static PartitionStrategy partitioner = Partitioner.LOMUTO;

    interface PartitionStrategy {
        int partition(List<Integer> numbers, int start, int end);
    }

    enum Partitioner implements PartitionStrategy {
        LOMUTO {
            public int partition(List<Integer> numbers, int start, int end) {
                int smaller = start;// start is the pivot
                for (int bigger = start + 1; bigger <= end; bigger++) {
                    if (numbers.get(bigger) <= numbers.get(start)) {
                        smaller++;
                        swap(numbers, smaller, bigger);
                    }
                }
                swap(numbers, smaller, start);
                return smaller;
            }
        },
        HOARE {
            public int partition(List<Integer> a, int start, int end) {
                int smaller = start+1, bigger = end;
                while (smaller <= bigger) {
                    if      (a.get(smaller) <  a.get(start)) smaller++;
                    else if (a.get(bigger)  >= a.get(start)) bigger--;
                    else swap(a, smaller++, bigger--);
                }
                swap(a, start, bigger);
                return bigger;
            }
        };
    }
}
