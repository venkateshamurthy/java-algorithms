package algos.dp;

import java.util.*;
import java.util.stream.Collectors;

public class EqualSubsetPartition {
    static int target;
    static Map<Integer, Integer> usedInBucket = new LinkedHashMap<>();
    static ArrayList<Integer> nums;
    public static void main(String[] args) {
        var data = new ArrayList<>(List.of(
                //0,0
                //10, -3, 7, 2, 1, 3
                //-1, -6, -5, -10, -2
                99, -99, 22, -22, 100, -100
                //100, 30, 60, 6, 3, 1, 8, 4
        ));
        System.out.println(equal_subset_sum_partition(data) );
        //System.out.println(equal_sum_partition(data) );
    }
    /*
    static ArrayList<Boolean> equal_subset_sum_partition(ArrayList<Integer> data) {
        nums = data;
        //Collections.sort(nums);
        int buckets = 2;

        target = nums.stream().reduce(Integer::sum).orElseThrow() / buckets;

        var result = backtracking(0, buckets, 0);

        System.out.println("Result:"+result+" Map:"+usedInBucket );

        return usedInBucket.values().stream()
                .map(i -> i != 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    //9972544865 - soumil - airtel escalation
    static boolean backtracking(int index, int buckets, int subsetSum) {
        if (buckets == 0) return true;

        if (subsetSum == target)
            return backtracking(0, buckets - 1, 0);

        for (int j = index; j < nums.size(); j++) {

            if (usedInBucket.containsKey(j) ||
                    subsetSum + nums.get(j) > target)
                continue;

            usedInBucket.put(j, buckets-1);

            if (backtracking(j + 1, buckets, subsetSum + nums.get(j)))
                return true;

            usedInBucket.remove(j);
        }
        return false;
    }
    */
    static ArrayList<Boolean> equal_subset_sum_partition(ArrayList<Integer> s) {
        int sumPos = s.stream().filter(n->n>=0).reduce(Integer::sum).orElseThrow();
        int sumNeg = s.stream().filter(n->n<0).reduce(Integer::sum).orElseThrow();
        int totalSum = sumPos + sumNeg;

        ArrayList<Boolean> result = new ArrayList<Boolean>();
        if(totalSum%2==1){
            return result;
        }
        int target = totalSum/2;

        //try to build the dp table
        boolean[][] dp = buildDP(s, sumPos, sumNeg);
        target -= sumNeg;

        if(!dp[0][target]){
            return result;
        }

        result.add(true);
        target -= s.get(0);

        boolean foundExclude = false;
        for(int i = 1; i<s.size(); i++){
            if (dp[i + 1][target]) {
                result.add(false);
                foundExclude = true;
            } else {
                target -= s.get(i);
                result.add(true);
            }
        }
        if (!foundExclude) return new ArrayList<>();

        return result;
    }

    static boolean[][] buildDP(ArrayList<Integer> s, int sumPos, int sumNeg){
        int range = sumPos - sumNeg + 1;
        boolean[][] dp = new boolean[s.size() + 1][range];

        dp[s.size()][-sumNeg] = true;

        for(int i=s.size()-1; i>=0; i--) {
            for(int j=0; j < range; j++) {
                int include = j - s.get(i);
                dp[i][j] = dp[i+1][j] ||
                        (include >= 0 && include < range && dp[i + 1][include]);
            }
        }
        return dp;
    }
}