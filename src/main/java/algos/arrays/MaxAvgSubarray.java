package algos.arrays;

public class MaxAvgSubarray {

	private static int getMaxAvgSubarrayStartIndex(int input[], int k) {
		int n = input.length;
		if (k > n)
			throw new IllegalArgumentException("k should be less than or equal to n");

		if (k == n) {
			return 0;
		}

		int sum =0;
		for (int i = 0; i < k; i++)
			sum += input[i];

		int maxSum = sum;
		int maxSumIndex = 0;
		for (int i = k; i < n; i++) {
			sum = sum - input[i-k] + input[i];
			if (sum > maxSum) {
				maxSum = sum;
				maxSumIndex = i-k+1;
			}
		}
		return maxSumIndex ;
	}

	public static void printMaxAvgSubarray(int[] input, int k) {
		System.out.print("Maximum average subarray of length " + k + " is:  ");
		int index = getMaxAvgSubarrayStartIndex(input, k);
		for (int i = 0; i < k; i++) {
			System.out.print(input[index++] + " ");
		}
	}

	public static void main(String[] args) {
		int[] input = { 11, -8, 16, -7, 24, -2, 3 };
		int k = 2;
		printMaxAvgSubarray(input, k);
	}
}