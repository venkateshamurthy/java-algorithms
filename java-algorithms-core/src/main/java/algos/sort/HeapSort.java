package algos.sort;

import algos.heaps.BinaryHeap;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeapSort {
    public static void main(String[] args) {
        ArrayList<Integer> list= Lists.newArrayList(5, 8, 3, 9, 4, 1, 7);
                //(ArrayList)IntStream.generate(RandomUtils::nextInt).limit(100).boxed().collect(Collectors.toList());
        System.out.println(heap_sort(list) +"\n"+
                list.stream().reduce(1, (old, recent)->recent>old?1:0));
    }
    static final Comparator<Integer> minComparator = Integer::compareTo;
    static final Comparator<Integer> maxComparator = minComparator.reversed();

    static ArrayList<Integer> heap_sort(ArrayList<Integer> numbers) {
        //Write your code here
        final BinaryHeap<Integer> heap = new BinaryHeap<>(numbers, maxComparator);
        for (int i = numbers.size() - 1; i >= 0; ) {
            Collections.swap(numbers, 0, i);
            i--;
            heap.heapifyDown(0, i);
        }
        return numbers;
    }


}

