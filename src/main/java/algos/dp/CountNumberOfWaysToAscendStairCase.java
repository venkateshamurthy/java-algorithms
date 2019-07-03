package algos.dp;

import java.util.Arrays;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountNumberOfWaysToAscendStairCase {

  // A child can hop 1 step /2 steps / 3 steps at a time. Find number of ways to
  // ascend the staircase

  private final int[] memo;
  private final int nSteps;
  
  CountNumberOfWaysToAscendStairCase(int nSteps){
    Preconditions.checkArgument(nSteps > 0);
    this.nSteps = nSteps;
    memo = new int[nSteps + 1];
    Arrays.fill(memo, -1);
  }

  public static void main(String[] args) {
    CountNumberOfWaysToAscendStairCase totalWays = new CountNumberOfWaysToAscendStairCase(10);
    log.info("total ways to reach {} steps is {}", 10, totalWays.countTotalWays());
  }

  private int countTotalWays() {
    return countWays(nSteps);
  }

  private int countWays(int n) {
    if (n < 0) {
      return 0;
    }
    if (n == 0) {
      return 1;
    } else if (memo[n] == -1) {
      memo[n] = Stream.of(countWays(n - 1), countWays(n - 2), countWays(n - 3))
              .mapToInt(Integer::intValue)
              .sum();
    }
    return memo[n];
  }
}
