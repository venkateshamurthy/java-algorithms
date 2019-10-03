package algos.arrays;

import static java.lang.Math.*;
import lombok.Data;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindMinMax2 {
	static int comparisons = 0;

	public static int[] findMinMaxRecursive(int[] arr, int i, int j) {
		IntStream.rangeClosed(i,j)
				.filter(num -> (num & 0x1) == 0x1)
				.boxed().collect(Collectors.toList());
		int minMax[] = new int[2];
		if (i == j)
			minMax[0] = minMax[1] = arr[i];
		else if (i + 1 == j) {
			if (arr[i] < arr[j]) {
				minMax[0] = arr[i];
				minMax[1] = arr[j];
			} else {
				minMax[0] = arr[j];
				minMax[1] = arr[i];
			}
			comparisons += 1;
		} else {
			int mid = (i+j) >> 1;
			int[] left = findMinMaxRecursive(arr, i, mid);
			int[] right = findMinMaxRecursive(arr, mid + 1, j);
			minMax[0] = left[0] < right[0] ? left[0] : right[0];
			minMax[1] = left[1] > right[1] ? left[1] : right[1];
			comparisons += 2;
		}
		return minMax;
	}

	public static int[] findMinMaxNaive(int[] arr) {
		comparisons = 0;
		int minMax[] = new int[2];
		minMax[0] = minMax[1] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			minMax[0] = min(minMax[0], arr[i]);
			minMax[1] = max(minMax[1], arr[i]);
			comparisons += 2;
		}
		return minMax;
	}

	public static void main(String[] args) {
		int[] inputArray = new int[] { -22, -15, 6, -2, -3, 1, 5, -6, -8, 9 };
		int[] minMax = findMinMaxRecursive(inputArray, 0, inputArray.length - 1);
		System.out.format("\n%d %d %d", minMax[0], minMax[1], comparisons);
		minMax = findMinMaxNaive(inputArray);
		System.out.format("\n%d %d %d", minMax[0], minMax[1], comparisons);
	}
}