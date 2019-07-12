package algos.dp;

import java.math.BigInteger;
import java.util.Arrays;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountNumberOfWaysToAscendStairCase {

    // A child can hop 1 step /2 steps / 3 steps at a time. Find number of ways to
    // ascend the staircase

    private final long[] memo;
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

    public static void main(String[] args) {
        int nsteps = 80;
        CountNumberOfWaysToAscendStairCase totalWays = new CountNumberOfWaysToAscendStairCase(nsteps);
        log.info("total ways to reach {} steps iteratively is {}", nsteps, totalWays.countWaysIterative(nsteps));
        totalWays.reset();
        log.info("total ways to reach {} steps iteratively with minimal array is {}", nsteps, totalWays.countWaysMinimalArray(nsteps));
        totalWays.reset();
        log.info("total ways to reach {} steps recursively is {}", nsteps, totalWays.countWays(nsteps));
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
