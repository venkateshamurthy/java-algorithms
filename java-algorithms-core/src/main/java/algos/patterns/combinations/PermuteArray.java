package algos.patterns.combinations;

import java.util.*;
import java.util.stream.Collectors;

public class PermuteArray {
    public static void main(String[] ags) {
        Set<ArrayList<Integer>> res = new LinkedHashSet<>();
       // System.out.println(permutateOnLetterCase("a1z"));
        permutations(res, new ArrayList<>(Arrays.asList(6, 3, 4, 7, 2, 9, 1, 0)));
        ArrayList<ArrayList<Integer>> var = new ArrayList<>(res);
        var.forEach(System.out::println);
    }

    static void permutations(Set<ArrayList<Integer>> ans, ArrayList<Integer> nums) {
        permutations(ans, nums, 0, nums.size() - 1);
    }

    static void permutations(Set<ArrayList<Integer>> ans, ArrayList<Integer> nums, int start, int end) {
        // Base case
        if (start == end) {
            ans.add(new ArrayList<>(nums));
            return;
        }
        for (int i = start; i <= end; i++) {
            Collections.swap(nums, start, i);
            permutations(ans, nums, start + 1, end);
            Collections.swap(nums, i, start);
        }
    }
}

