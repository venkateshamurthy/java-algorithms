package algos.sort;

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
        final BinaryHeap heap = new BinaryHeap(numbers, maxComparator);
        for (int i = numbers.size() - 1; i >= 0; ) {
            Collections.swap(numbers, 0, i);
            i--;
            heap.heapifyDown(0, i);
        }
        return numbers;
    }

    private static class BinaryHeap {
        final Comparator<Integer> comparator;
        final List<Integer> a;
        BinaryHeap(final List<Integer> numbers, Comparator<Integer> comparator) {
            this.comparator = comparator;
            this.a = numbers;
            for (int i = (a.size() - 1) >> 1; i >= 0; i--) {
                heapifyDown(i);
            }
        }

        private boolean compare(int current, int other) {
            return current >= 0 && current < a.size() && other >= 0 && other < a.size()
                    && comparator.compare(a.get(current), a.get(other)) == -1;
        }

        private int heapifyUp(int location) {
            while (compare(location, location >> 1)) {
                Collections.swap(a, location, location >> 1);
                location >>= 1;
            }
            return location;
        }

        private void heapifyDown(int location) {
            heapifyDown(location, a.size() - 1);
        }

        private void heapifyDown(int location, int limit) {
            int left = (location << 1) + 1; // index of node i's left child
            int right = (location << 1) + 2; // index of node i's right child
            int extreme = location; // Find out which is real extreme(i.e min/max)
            if (left <= limit && compare(left, location))
                extreme = left;

            if (right <= limit && compare(right, extreme))
                extreme = right;

            if (extreme != location) {
                Collections.swap(a, location, extreme);
                heapifyDown(extreme, limit);
            }
        }
    }
}

