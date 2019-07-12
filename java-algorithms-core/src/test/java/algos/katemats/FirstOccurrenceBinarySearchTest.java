package algos.katemats;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FirstOccurrenceBinarySearchTest {
	private int[] array;
	private int key;
	private int expectedIndex;

	public FirstOccurrenceBinarySearchTest(int[] array, int key,
			int expectedIndex) {
		this.array = array;
		this.key = key;
		this.expectedIndex = expectedIndex;
	}

	@Parameters
	public static Collection<Object[]> init() {
		return Arrays.asList(new Object[][] { { new int[] {}, 0, -1 },

		{ new int[] { 1 }, 0, -1 }, { new int[] { 1 }, 1, 0 },
				{ new int[] { 1 }, 2, -2 },

				{ new int[] { 1, 2 }, 0, -1 }, { new int[] { 1, 2 }, 1, 0 },
				{ new int[] { 1, 2 }, 2, 1 }, { new int[] { 1, 2 }, 3, -3 },

				{ new int[] { 1, 1, 1, 1, 2, 2, 3, 3, 3 }, 1, 0 },
				{ new int[] { 1, 1, 1, 1, 2, 2, 3, 3, 3 }, 2, 4 },
				{ new int[] { 1, 1, 1, 1, 2, 2, 3, 3, 3 }, 3, 6 },
				{ new int[] { 1, 1, 1, 1, 2, 2, 3, 3, 3 }, 4, -10 } });
	}

	@Test
	public void binarySearchTest() throws Exception {
		FirstOccurenceInSortedArray t = new FirstOccurenceInSortedArray();
		assertThat(t.firstOccurrenceBinarySearch(array, key), is(expectedIndex));
	}
}
