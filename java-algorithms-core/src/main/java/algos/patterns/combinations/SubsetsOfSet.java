package algos.patterns.combinations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubsetsOfSet {
    public static void main (String[] args) {
        /*List<Integer> set = IntStream.rangeClosed(1,5).boxed().collect(Collectors.toList());
        ArrayList<ArrayList<Integer>> result = subsets(set, 2)
                .stream().map(s -> new ArrayList<>(Arrays.asList(s.toArray(Integer[]::new))))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(result);

         */

        ArrayList<String> strings = new ArrayList<>();
        subsetsOfSet(strings,
                "dc",
                0,
                new StringBuilder());
        System.out.println(strings);
    }

    public static <T> Set<Set<T>> subsets(Collection<T> set, int size) {
        if (size < 1) {
            return Set.of(Set.of());
        }
        Set<Set<T>> result = new LinkedHashSet<>();
        for (T removeElement : set) {
            for (Set<T> s : subsets(remove(set ,removeElement), size - 1)) {
                result.add(add(s, removeElement));
            }
        }
        return result;
        /*
        return set.stream()
                .flatMap(e -> subsets(remove(set, e), size - 1)
                        .stream()
                        .map(s -> add(s, e))
                )
                .collect(Collectors.toCollection(LinkedHashSet::new));
         */
    }

    static <T> Set<T> add(Set<T> set, T e) {return new TreeSet<>(set){{add(e);}};}

    static <T> Set<T> remove(Collection<T> set, T e) {return new TreeSet<>(set){{remove(e);}};}

    static void subsetsOfSet(ArrayList<String> ans, String arr, int index, StringBuilder currentSubset) {
        // store only in last iteration
        if (index >= arr.length()) {
            char[] chars = currentSubset.toString().toCharArray();
            Arrays.sort(chars);
            ans.add(new String(chars));
            return;
        }
        subsetsOfSet(ans, arr, index + 1, currentSubset);
        subsetsOfSet(ans, arr, index + 1, currentSubset.append(arr.charAt(index)));
        currentSubset.deleteCharAt(currentSubset.length()-1);
    }

}
