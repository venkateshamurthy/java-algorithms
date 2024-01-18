package algos.patterns.slidingwindow.twopinters;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static algos.patterns.slidingwindow.twopinters.Find2NumbersSumToTarget.FindPairs.*;

public class Find2NumbersSumToTarget {

    enum FindPairs {
        AllPairs {
            List<Pair<Integer, Integer>> find(int[] numbers, int target) {
                List<Pair<Integer, Integer>> pairs = new ArrayList<>();
                Arrays.sort(numbers);
                System.out.println(Arrays.toString(numbers) + " Target="+target);
                int i = 0, j = numbers.length - 1;
                while (i < j) {
                    if (numbers[i] < target - numbers[j]) i++;
                    else if (numbers[j] > target - numbers[i]) j--;
                    else
                        pairs.add(Pair.of(i++, j--));
                }
                return pairs;
            }
        },
        AllUniquePairs {
            List<Pair<Integer, Integer>> find(int[] numbers, int target) {
                List<Pair<Integer, Integer>> pairs = new ArrayList<>();
                Arrays.sort(numbers);
                System.out.println(Arrays.toString(numbers) + " Target="+target);
                int i = 0, j = numbers.length - 1;
                while (i < j) {
                    if (numbers[i] < target - numbers[j] ) i++;
                    else if (numbers[j] > target - numbers[i]) j--;
                    else {
                        pairs.add(Pair.of(i++, j--));
                        while (i<j && numbers[i] == numbers[i-1]) i++;
                        while (i<j && numbers[j] == numbers[j-1]) j--;
                    }
                }
                return pairs;
            }
        },
        FirstPair {
            List<Pair<Integer, Integer>> find(int[] numbers, int target) {
                List<Pair<Integer, Integer>> pairs = new ArrayList<>();
                Map<Integer, Integer> map = new HashMap<>();
                System.out.println(Arrays.toString(numbers) + " Target="+target);
                Integer j = null;
                for (int i = 0; i < numbers.length; i++) {
                    if ((j = map.get(target - numbers[i])) != null)
                        return List.of(Pair.of(i, j));
                    else
                        map.put(numbers[i], i);
                }
                return List.of(Pair.of(-1,1));
            }
        };
        abstract List<Pair<Integer, Integer>> find (int[] numbers, int target);
    }
    public static void main(String[] args) {
        IntStream.of(10, 11, 13, 14).boxed()
                .map(target -> FirstPair.find(new int[]{0, 1, 9, 6, 8, 2, 7, 5, 3}, target))
                .forEach(System.out::println);
        System.out.println();
        IntStream.of(10, 11, 13, 14).boxed()
                .map(target -> AllPairs.find(new int[]{0, 1, 9, 6, 8, 2, 7, 5, 3}, target))
                .forEach(System.out::println);
        System.out.println();
        IntStream.of(47).boxed()
                .map(target -> AllUniquePairs.find(new int[]{1, 1, 2, 45, 46, 46}, target))
                .forEach(System.out::println);

        System.out.println();
        IntStream.of(6).boxed()
                .map(target -> AllUniquePairs.find(new int[]{1, 5, 1, 5}, target))
                .forEach(System.out::println);
    }
}
