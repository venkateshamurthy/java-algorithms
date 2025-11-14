package algos.dp;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class CoinPlay {
    static List<Integer> fuse(List<Integer> coins, int l, int r) {
        if (r - l < 2) {
            return List.copyOf(coins);
        }

        var stack = new ArrayDeque<Integer>();

        for (int i = l; i <= r; i++) {
            stack.push(coins.get(i));
            while(stack.size() >= 3) {
                var y = stack.pop();
                var M = stack.pop();
                var x = stack.pop();

                if (x <= M && y <= M) {
                    stack.push(x - M + y); // fuse coins and continue with the while loop
                } else {
                    stack.push(x);
                    stack.push(M);
                    stack.push(y);
                    break;
                }
            }
        }
        return toList(stack);
    }

    static List<Integer> toList(Deque<Integer> stack) {
        List<Integer> list = new ArrayList<>();
        while(!stack.isEmpty())list.add(stack.pop());
         Collections.reverse(list);
         return list;
    }

    // Standard DP solution
    static Integer max_win(List<Integer> v) {
        // Write your code here.
        Integer[] A = v.toArray(new Integer[0]);
        int[][] MV = new int[A.length][A.length];

        for (int interval = 0; interval < A.length; interval++) {
            for (int i = 0, j = interval; j < A.length; i++, j++) {
                int a=0, b=0, c=0;
                if (i + 2 <= j)     a = MV[i + 2][j];
                if (i + 1 <= j - 1) b = MV[i + 1][j - 1];
                if (i <= j - 2)     c = MV[i][j - 2];
                MV[i][j] = Math.max(A[i] + Math.min(a, b), A[j] + Math.min(b, c));
            }
        }
        return MV[0][A.length - 1];
    }

    public static void main(String[] args) {
//		int[] coins = {9, 7, 8, 2, 1, 3, 20, 8, 19};
        List<Integer> coins = List.of(20, 30, 2, 3, 4, 10, 234, 345, 12, 24, 45, 34, 1, 3, 6, 87, 26, 45, 89,
                89, 23, 52,63,87, 80, 43, 22, 12, 45, 12, 1, 3);
        System.out.println("main:" + CoinPlay.fuse(coins, 0, coins.size()-1));
        System.out.println("main:" + CoinPlay.max_win(coins));
    }
}
