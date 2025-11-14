package algos.dp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountNumberOfWaysToAscendStairCase {

    // A child can hop 1 step /2 steps / 3 steps at a time. Find number of ways to
    // ascend the staircase

    private  long[] memo;
    private final int nSteps;

    CountNumberOfWaysToAscendStairCase(int nSteps) {
        Preconditions.checkArgument(nSteps > 0);
        this.nSteps = nSteps;
        memo = new long[nSteps + 1];
        reset();
    }

    private void reset() {
        Arrays.fill(memo, -1L);
        memo[0] = 0L;
        memo[1] = 1L;
        memo[2] = 2L;
        memo[3] = 6L;
    }

    Long count_ways_to_climb(List<Integer> steps, Integer n) {
        // Write your code here.
        memo = new long[n + 1];
        Arrays.fill(memo, -1);
        memo[0] = 1L;

        return countWays(steps, n);
    }

    long countWays(List<Integer> steps, int n) {
        if (n<0) return 0L;
        memo[n] = memo[n] != -1
                ? memo[n]
                : steps.stream()
                    //.sorted()
                    .map(s -> countWays(steps,n - s ))
                    //.peek(System.out::println)
                    .reduce(Long::sum).orElse(0L);
        System.out.println("memo["+n+"]=" + memo[n]);
        return memo[n];
    }

    public static void main(String[] args) {
        int nsteps = 80;

        CountNumberOfWaysToAscendStairCase totalWays = new CountNumberOfWaysToAscendStairCase(nsteps);
        /*log.info("total ways to reach {} steps iteratively is {}", nsteps, totalWays.countWaysIterative(nsteps));
        totalWays.reset();
        log.info("total ways to reach {} steps iteratively with minimal array is {}", nsteps, totalWays.countWaysMinimalArray(nsteps));
        totalWays.reset();
        log.info("total ways to reach {} steps recursively is {}", nsteps, totalWays.countWays(nsteps));
        totalWays.reset();*/
        // Actual answer: 47695241177, my answer: 44636710951
        System.out.println("total ways to reach "+ totalWays.count_ways_to_climb(
                //List.of(1,2,3,4,5),5)
                //List.of(2,3), 7)
                List.of(32, 28, 2, 14, 21, 35, 10, 29, 39, 15, 8),117)
                 +" steps");
    }

    private long countTotalWays() {
        return countWays(nSteps);
    }

    private long countWays(int n) {
        if (n < 0) {
            return 0;
        }
        if (memo[n] == -1) {
            memo[n] = countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
        return memo[n];
    }

    private long countWaysIterative(int n) {
        if (n < 0)
            return 0L;

        memo[0] = 0L;
        memo[1] = 1L;
        memo[2] = 2L;
        memo[3] = 6L;
        for (int i = 4; i <= n; i++) {
            memo[i] = memo[i - 3] + memo[i - 2] + memo[i - 1];
        }
        return memo[n];
    }

    private BigInteger countWaysMinimalArray(int n) {
        if (n < 0)
            return BigInteger.ZERO;
        BigInteger memoized[] = new BigInteger[5];
        memoized[0] = BigInteger.ZERO;
        memoized[1] = BigInteger.ONE;
        memoized[2] = BigInteger.valueOf(2);
        memoized[3] = BigInteger.valueOf(6);
        for (int i = 4; i <= n; i++) {
            memoized[4] = memoized[3].add(memoized[2]).add(memoized[1]);
            memoized[1] = memoized[2];
            memoized[2] = memoized[3];
            memoized[3] = memoized[4];
        }
        return memoized[4];
    }
}
