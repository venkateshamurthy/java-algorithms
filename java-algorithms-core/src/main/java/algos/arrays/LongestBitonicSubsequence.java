package algos.arrays;

public class LongestBitonicSubsequence {

	public static int longestBitonicSubsequence(int[] input) {
		if (input == null || input.length == 0) {
			return 0;
		}
		int n = input.length;
		int[] lisLength = new int[n];
		int[] ldsLength = new int[n];
		int maxLength = 1;

		lisLength[0] = 1;
		for (int i = 1; i < n; i++) {
			lisLength[i] = 1;
			for (int j = 0; j < i; j++) {
				if (input[i] > input[j] && lisLength[i] < lisLength[j] + 1) {
					lisLength[i] = lisLength[j] + 1;
				}
			}
		}

		// Find lengths of longest decreasing subsequence for all sub arrays
		// [i..n-1]
		ldsLength[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--) {
			ldsLength[i] = 1;
			for (int j = n - 1; j > i; j--) {
				if (input[i] > input[j] && ldsLength[i] < ldsLength[j] + 1) {
					ldsLength[i] = ldsLength[j] + 1;
				}
			}
		}

		for (int i = 1; i < n; i++) {
			if (maxLength < lisLength[i] + ldsLength[i] - 1)
				maxLength = lisLength[i] + ldsLength[i] - 1;
		}

		return maxLength;
	}

	public static void main(String[] args) {
		int[] array = { 12, 18, 7, 34, 30, 28, 90, 88 };
		System.out.println("Length of Longest Bitonic Subsequence: " + longestBitonicSubsequence(array));
	}
}