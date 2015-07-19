/**
 * 
 */
package algos.arrays;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 * 
 */
@Slf4j
public class SeggregateAsAndBs {
	/**
	 * A method to segregate instances of two classes in an array in place. Written currently as utility static method
	 * 
	 * @param objects
	 *            is an Array containing the instances of the classA and classB
	 * @param classA
	 *            is the first type of class
	 * @param classB
	 *            is the second type of class
	 */
	public static void doSegregate(Object[] objects, Class<?> classA,
			Class<?> classB) {
		if (objects != null && objects.length > 0) {
			int left = 0, right = objects.length - 1;
			while (left < right) {
				if (objects[left] != null
						&& objects[left].getClass().equals(classA)) {
					left++;
					continue;
				}
				if (objects[right] != null
						&& objects[right].getClass().equals(classB)) {
					right--;
					continue;
				}
				System.out.format("%s",objects.toString());
				swap(objects, left++, right--);
			}
		}
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		Object[] objects = createStringIntegerObjectArray();
		log.info(arrayToString(objects, ","));
		doSegregate(objects, String.class, Integer.class);
		log.info(arrayToString(objects, ","));
	}

	/**
	 * Creates a simple object array of strings and integer
	 * 
	 * @return an array of objects
	 */
	private static Object[] createStringIntegerObjectArray() {
		String[] s = new String[] { "a", "b", "c", "d" };
		Integer[] i = new Integer[] { 1, 2, 3, 4 };
		Object[] objects = new Object[s.length + i.length];
		System.arraycopy(s, 0, objects, 0, s.length);
		System.arraycopy(i, 0, objects, s.length, i.length);
		// Shuffle it to get randomness
		shuffle(objects);
		return objects;
	}
	/**
	 * Random number instance
	 */
	private static final Random random = new Random();
	/**
	 * Shuffle objects within array
	 * @param objects
	 */
	private static void shuffle(Object[] objects) {
		if (objects != null) {
			for (int i = objects.length; i > 1; i--)
				swap(objects, random.nextInt(i), i - 1);
		}
	}
	/**
	 * Creates a stringified representation
	 * @param objects
	 * @param delimiter
	 * @return
	 */
	private static String arrayToString(Object[] objects, String delimiter) {
		if (objects != null) {
			StringBuilder sb = new StringBuilder();
			String formatterElement = "%s" + delimiter;
			for (int i = 0; i < objects.length; i++)
				sb.append(formatterElement);
			return String.format(sb.toString(), objects);
		}
		return "";
	}
	/**
	 * Swaps elements of left and right indices in passed in array
	 * @param objects
	 * @param left
	 * @param right
	 */
	private static void swap(Object[] objects, int left, int right) {
		if (objects != null && left < right && left >= 0
				&& right < objects.length) {
			Object temp = objects[left];
			objects[left] = objects[right];
			objects[right] = temp;
		}
	}
	

}
