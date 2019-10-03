package algos.arrays;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindowMax {

    static int[] sarr;

    public static void main(String[] args) {
        //3, 6, 6, 6, 5, 7, 8,
        final int[] arr = {1, 3, 2, 6, 5, 4, 1, 7, 8};
        System.out.println("Sliding Window Max:"+solveEfficient(arr, 4));
    }

    public static Map<Integer, Integer> solveEfficient(int[] arr, int k) {
        final Deque<Integer> deque = new ArrayDeque<>();
        final Map<Integer, Integer> result = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            final int index = i;

            deque.descendingIterator()
                    .forEachRemaining(j -> {
                        if (arr[j] <= arr[index]) {
                            deque.removeLast();
                        }
                    });

            deque.removeIf(j -> j <= index - k);

            deque.addLast(i);

            deque.stream().map(j -> arr[j]).forEach(System.out::print);

            result.put(index, deque.getFirst());
        }
        return result;
    }
}
 