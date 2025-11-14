package algos.patterns.combinations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NChooseK {
    public static void main(String[] args) {
        System.out.println(find_combinations(6,2));
    }

    static ArrayList<ArrayList<Integer>> find_combinations(Integer n, Integer k) {
        Collection<Integer> input = IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());
        // Write your code here.
        return subsets(input, k)
                .stream().map(s -> new ArrayList<>(Arrays.asList(s.toArray(Integer[]::new))))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static <T> Set<Set<T>> subsets(Collection<T> set, int size) {
        if (set.isEmpty()) {
            return Set.of(Set.of());
        }
        return set.stream()
                .flatMap(e -> subsets(remove(set, e), size - 1)
                        .stream()
                        .map(s -> add(s, e)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static <T> Set<T> add(Set<T> set, T e) {
        return new TreeSet<>(set) {{add(e);}};
    }

    static <T> Set<T> remove(Collection<T> set, T e) {
        return new TreeSet<>(set) {{remove(e);}};
    }
}