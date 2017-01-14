package algos.dp;

import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Example : this should get us 19 with 2nd, 3rd, 4th and 6th item picked up
 * 
 * <pre>
 * 	static int[] v = new int[] { 0, 6, 4, 5, 3, 9, 7 };
 * 	static int[] w = new int[] { 0, 4, 2, 3, 1, 6, 4 };
 * Item	0	1	2	3	4	5	6	7	8	9	10
 * -----------------------------------------------
 *    0	0	0	0	0	6	6	6	6	6	6	6
 *    -----------------------------------------------
 *    1	0	0	4	4	6	6	10	10	10	10	10
 *    -----------------------------------------------
 *    2	0	0	4	5	6	9	10	11	11	15	15
 *    -----------------------------------------------
 *    3	0	3	4	7	8	9	12	13	14	15	18
 *    -----------------------------------------------
 *    4	0	3	4	7	8	9	12	13	14	16	18
 *    -----------------------------------------------
 *    5	0	3	4	7	8	10	12	14	15	16	19
 *    -----------------------------------------------
 * </pre>
 * 
 * @author vemurthy
 *
 */
@Slf4j
public class Knapsack {
  static int[] wt = { 6, 4, 5, 1, 9, 7 };
  static int[] val = { 4, 2, 3, 1, 6, 4 };

  static int W = 18, n = wt.length;
  static int[][] K = new int[n + 1][W + 1];
  Map<Integer, Integer> map = new LinkedHashMap<>();

  private static int knapsack() {
    log.info(ArrayUtils.toString(wt));
    log.info(ArrayUtils.toString(val));
    Deque<Integer> take = new ArrayDeque<>();
    for (int i = 1; i <= n; i++)
      for (int w = 1; w <= W; w++) {
        K[i][w] = K[i - 1][w];
        if (wt[i - 1] <= w)
          K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i][w]);
      }
    for (int i = n, w = W; i > 0 && w > 0; i--) {
      if (K[i][w] != K[i - 1][w]) {// if previos row at the same column is
                                   // different then it is in
        take.addFirst(wt[i - 1]);// add that wt[i-1]
        w -= wt[i - 1];// next reduce the weight
      }
    }
    log.info(printMatrix(K));
    log.info(ArrayUtils.toString(take));
    return K[wt.length][W];

  }

  private static String printMatrix(int[][] matrix) {
    StringBuilder sb = new StringBuilder("\n");
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        sb.append(matrix[i][j] + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    log.info("Value={}", knapsack());
  }
}