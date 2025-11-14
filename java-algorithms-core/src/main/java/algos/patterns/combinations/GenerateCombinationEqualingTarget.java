package algos.patterns.combinations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateCombinationEqualingTarget {

    static int target;
    static Set<Collection<Integer>> ans = new HashSet<>();
    public static void main(String[] arge) {
        generate_all_combinations(
                new ArrayList<>(Arrays.asList(42, 68, 35, 1, 70, 25, 79, 59, 63, 65, 6, 46, 82, 28, 62, 92, 96, 43, 28, 37, 92, 5, 3, 54, 93)),
                83).forEach(System.out::println);
    }
    static ArrayList<ArrayList<Integer>> generate_all_combinations(ArrayList<Integer> arr, Integer t) {
        // Write your code here.
        target = t;
        subset_sum(arr, new ArrayList<>());
        return ans.stream().map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static void subset_sum(List<Integer> nums, List<Integer> partial) {
        int sum = partial.stream().reduce(Integer::sum).orElse(Integer.MIN_VALUE);

        //check if the partial sum is equals to target; sort the partial and add to rsult as per the question
        if (sum == target) {
            // Make a copy of partial, sort it and then add
            ans.add(partial.stream().sorted().collect(Collectors.toList()));
            return;
        }

        //No point to continue if sum has exceeded target; just return
        if (sum > target)
            return;

        // if not add one number at a time to the partial list and sum it up
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> remaining = nums.subList(i + 1, nums.size());
            partial.add(nums.get(i));                  //add nums[i] to the last
            subset_sum(remaining, partial);            //recurse
            partial.remove(partial.size() - 1);  //remove nums[i] / last added
        }
    }
}
