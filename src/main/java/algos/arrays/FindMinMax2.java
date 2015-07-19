package algos.arrays;
import static java.lang.Math.*;
import lombok.Data;

@Data
class MinMax {
	public int min;
	public int max;

	public MinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
}

public class FindMinMax2 {
	public  MinMax findMinMaxRecursive(int[] arr, int i, int j) {
		if (i > j)
			return null;
		if (i == j)
			return new MinMax(arr[i], arr[i]);
		else {
			MinMax left = findMinMaxRecursive(arr, i, (i + j) / 2);
			MinMax right = findMinMaxRecursive(arr, (i + j) / 2 + 1, j);
			if (left == null)
				return right;
			else if (right == null)
				return left;
			else 
				return new MinMax(min(left.min, right.min), max(left.max, right.max));
		}
	}
}