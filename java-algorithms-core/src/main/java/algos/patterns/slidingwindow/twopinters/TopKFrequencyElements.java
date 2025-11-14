package algos.patterns.slidingwindow.twopinters;

import algos.sort.KthSelector;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class TopKFrequencyElements {

    static PriorityQueue<Entry<Integer, Integer>> q;

    static void findTopKOccuring(List<Integer> numbers, int k) {
        q = new PriorityQueue<>(k, Comparator.comparingInt(Entry::getValue));
        Map<Integer, Integer> freq = new HashMap<>();
        for (final int key : numbers) {
            int occur = freq.merge(key, 1, Integer::sum);
        }
        freq.forEach((key, value) -> {
            q.add(new AbstractMap.SimpleEntry<>(key, value));
            while (q.size() > k) q.poll();
        });
        System.out.println("Map:" + freq);
        System.out.println("q:" + q);
    }


    static Integer find_fibonacci(Integer n) {
        // Write your code here.
        return map.computeIfAbsent(n, k -> find_fibonacci(k - 1) + find_fibonacci(k - 2));
    }

    static Map<Integer, Integer> map = new HashMap<>() {{
        put(0, 0);
        put(1, 1);
        put(2, 1);
    }};

    static void findTopKFreq(ArrayList<Integer> numbers, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        numbers.forEach(key -> freq.merge(key, 1, Integer::sum));
        Map<Integer, Set<Integer>> inverted = new HashMap<>();
        freq.forEach((key, count) -> inverted.merge(count, new HashSet<>(Set.of(key)), (s1, s2) -> {
            s1.addAll(s2);
            return s1;
        }));
        System.out.println("inverted:" + inverted);
        List<Entry<Integer, Set<Integer>>> input = new ArrayList<>(inverted.entrySet());
        Collections.shuffle(input);
        System.out.println("Before Quick Select:" + input);
        new KthSelector().quickSelect(Entry.comparingByKey(), input, 0, input.size() - 1, input.size() - k);
        System.out.println("After Quick Select:" + input);
        System.out.println("Top " + k + " occurRing elements:" + input.subList(input.size() - k, input.size()));
    }

    static void findTopKPresent(ArrayList<Integer> numbers, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        numbers.forEach(key -> freq.merge(key, 1, Integer::sum));
        List<Entry<Integer, Integer>> input = new ArrayList<>(freq.entrySet());
        Collections.shuffle(input);
        System.out.println("Before Quick Select:" + input);
        new KthSelector().quickSelect(Entry.comparingByValue(), input, 0, input.size() - 1, input.size() - k);
        System.out.println("After Quick Select:" + input);
        System.out.println("Top " + k + " occurRing elements:" + input.subList(input.size() - k, input.size()));
    }

    static ArrayList<ArrayList<Integer>> find_combinations(Integer n, Integer k) {
        // Write your code here.
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.addAll(find_combinations(n - 1, k));
        result.addAll(find_combinations(n - 1, k - 1));
        return result;
    }




    public static void main(String[] args) {
        Random r = new Random();
        ArrayList<Integer> input = (ArrayList<Integer>) r.ints(1, 50)
                .limit(150)
                .boxed().collect(Collectors.toList());
        System.out.println(input);
        //findTopKFreq(input, 3);
        findTopKOccuring(input, 3);

    }
}
