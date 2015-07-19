package algos.katemats;

public class FirstOccurenceInSortedArray {

	public int firstOccurrenceBinarySearch(int[] source, int key) {
		int low = 0;
		int high = source.length - 1;
		int firstOccurrence = Integer.MIN_VALUE;
		while (low <= high) {
			int pivot = low + ((high - low) >>> 1);
			if (source[pivot] == key) {
				firstOccurrence = pivot;
				high = pivot - 1;
			} else if (source[pivot] < key) {
				low = pivot + 1;
			} else {
				high = pivot - 1;
			}
		}

		return firstOccurrence != Integer.MIN_VALUE ? firstOccurrence
				: -(low + 1); // key not found
	}
}
