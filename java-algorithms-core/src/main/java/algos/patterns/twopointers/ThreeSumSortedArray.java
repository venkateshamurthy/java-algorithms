package algos.patterns.twopointers;

import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThreeSumSortedArray {
    public static Map<int[], int[]> threeSum(int[] nums, int target) {
        Arrays.sort(nums);
        Map<int[], int[]> map = new LinkedHashMap<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < target) left++;
                else if (sum > target) right--;
                else {
                    map.put(new int[]{i, left, right}, new int[]{nums[i], nums[left], nums[right]});
                    left++;
                    right--;
                } // if-elses
            }     // while
        }         // for
        return map;
    }
}
