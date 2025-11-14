package algos.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumCoins {
    static int [] coinsArray;
    static Integer minimum_coins(List<Integer> coins, Integer value) {
        // Write your code here.
        // f(n) = min()
        coinsArray = new int[value + 1];
        Arrays.fill(coinsArray, Integer.MAX_VALUE);
        coinsArray[0] = 0;
        return countCoins(coins, value);
    }

    private static int countCoins(List<Integer> coins, int n) {
        if (n < 0) return 0;
        coinsArray[n] = coinsArray[n] != Integer.MAX_VALUE ?
                coinsArray[n]                              :
                coins.stream().filter(c -> n >= c)
                        .map(c -> 1 + countCoins(coins, n - c))
                        .reduce(Integer::min).orElse(0);
        return coinsArray[n] ;
    }

    public static void main(String [] args ) {
        System.out.println(minimum_coins(List.of (3, 2, 1, 7, 5, 6, 4), 999));
        System.out.println(minimum_coins(List.of (8, 10, 3, 7, 9, 1, 2), 455));
        System.out.println(minimum_coins(List.of (1, 5, 3,7),888));
        //System.out.println((Arrays.toString(coinsArray)));
    }
}
