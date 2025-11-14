package algos.sort;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

import static algos.sort.PIVOT_STRATEGY.RANDOM;
import static java.util.Collections.swap;

class Quicksort {

    public static void main(String[] args) {
        //ArrayList<Integer> input = Lists.newArrayList(5, 1, 8, 3, 9, 4, 41, 7, 35, 13, 19,
        //        15, 16,30, 29, 34, 25, 28, 22, 24)
        //ArrayList<Integer> input = Lists.newArrayList(-913743, 3241, 1001, 978, 999999, 1340, 1243153, 0, 0, 999999999)
        ArrayList<Integer> input = new Random().ints(100).boxed().distinct()
                .collect(Collectors.toCollection(Lists::newArrayList))
        ;
        ArrayList<Integer> result = quick_sort(input);
        System.out.println("result:"+result);
    }

    static ArrayList<Integer> quick_sort(ArrayList<Integer> arr) {
        // Write your code here.
        quickSort(arr, 0, arr.size() - 1);
        return arr;
    }

    static void quickSort(List<Integer> numbers, int low, int high) {
        if (low >= high)
            return;
        int q = partitioner.partition(RANDOM, Integer::compareTo, numbers, low, high);
        quickSort(numbers, low, q - 1);
        quickSort(numbers, q + 1, high);
    }

    static PartitionStrategy partitioner = Partitioner.LOMUTO;
}
