package algos.dp;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MakeChange {
	static Map<Map.Entry<Integer, Integer>, BigInteger> nums  = new ConcurrentHashMap<>();
	public static void main(String args[]) {
		makeChange(new Integer[] {75, 8, 71, 87, 82, 10, 2, 64, 65, 17, 56, 23, 26, 63, 93, 51, 58, 45, 41, 32, 52, 88,
				1, 31, 54, 4, 91, 92, 35, 39, 55, 79, 69, 38, 80, 90, 47, 21, 62, 5, 77, 83, 20, 15, 16, 61, 18, 44, 72,
				70, 67, 59, 3, 96, 43, 29, 30, 19, 9, 60, 33, 78, 46, 68, 24, 7, 27, 34, 6, 74, 11, 84, 76, 49, 42, 86,
				73, 94, 85, 48, 66, 25, 28, 36, 12, 37, 97, 50, 89, 40, 57, 22, 13, 81, 14, 53, 95}, 10000); //answer:  556414648
	}

	public static void makeChange(Integer d[], int n) {
		BigInteger number = number_of_ways(
				new ArrayList<>(List.of(d)), d.length, n);
		BigInteger m = BigInteger.valueOf(1000000007);
		System.out.println("number_of_ways=" + number+ "  mod="+ number.mod(m).intValue() );
	}

	static BigInteger number_of_ways(ArrayList<Integer> coins, Integer amount, int n) {
		return count(coins, coins.size(), amount);
	}

	static BigInteger count(ArrayList<Integer> coins, int n, int sum) {
		if (sum == 0) return BigInteger.ONE;
		if (sum < 0 || n <= 0) return BigInteger.ZERO;

		var en_1 = Map.entry(n - 1, sum);
		var en = Map.entry(n, sum - coins.get(n - 1));
		if (! nums.containsKey(en_1)) {
			nums.put(en_1, count(coins, en_1.getKey(), en_1.getValue()));
		}
		if (! nums.containsKey(en)) {
			nums.put(en, count(coins, en.getKey(), en.getValue()));
		}

		return nums.get(en_1).add(nums.get(en));

	}

}
