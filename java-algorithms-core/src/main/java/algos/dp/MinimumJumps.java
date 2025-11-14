package algos.dp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimumJumps {

    private static int minJumps(List<Integer> arr, int n) {
        int jumps[] = new int[n];
        Arrays.fill(jumps, Integer.MAX_VALUE);
        jumps[0]=0;
        if (arr.get(0) == 0 && n==1)
            return 1;
        if (arr.get(0) == 0 && n>1)
            return Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            final int k = i;
            jumps[i] = IntStream.range(0, i).boxed()
                    .filter(j -> jumps[j] != Integer.MAX_VALUE)
                    .filter(j -> k <= j + arr.get(j))
                    .map(j ->  jumps[j] + 1)
                    .reduce(Integer::min).orElse(Integer.MAX_VALUE);
            /*for (int j = 0; j < i; j++) {
                if (jumps[j] != Integer.MAX_VALUE && i <= j + arr.get(j)) {
                    jumps[i] = Math.min(jumps[i], jumps[j] + 1);
                    break;
                }
            }*/
        }
        return jumps[n - 1];
    }

    public static void main(String[] args) {
        List<Integer> arr =
                //List.of(0);
                //List.of(0,100000); //ans=1
                List.of(1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9); //ans=3
        System.out.println("Can reach end is : " + (minJumps(arr, arr.size())==Integer.MAX_VALUE?0:1));
    }
}
