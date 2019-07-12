package algos.arrays;



/**
 * Move a perticular number to right in the array.
 * @author vemurthy
 *
 */
public class MoveNumberToRight {
	/**
	 * This method modifies the array in place with moving x to the right
	 * @param numbers is the array of input that gets modified
	 * @param x is the number to move to right
	 * @return input array after modification
	 */
	public int[] moveXToRight(int[] numbers, int x) {
		int start = 0;
		int end = numbers.length - 1;
		while (start < end) {
			if (numbers[start] == x && numbers[end] != x)
				swap(numbers, start++, end--);
			else if (numbers[start] != x)
				start++;
			else if (numbers[end] == x)
				end--;
		}
		return numbers;
	}

	/**
	 * Swapping of elements in the array. TODO: Check some good library method!
	 * @param numbers
	 * @param start
	 * @param end
	 */
	private static void swap(int[] numbers, int start, int end) {
		int temp = numbers[start];
		numbers[start] = numbers[end];
		numbers[end] = temp;
	}
}
