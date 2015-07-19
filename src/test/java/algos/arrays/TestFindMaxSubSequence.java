package algos.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestFindMaxSubSequence {
	/** Populate this before tests */
	@NonFinal
	List<Integer> list;

	/** A array of numbers containing positive and negative numbers */
	int[] numbers;

	/**
	 * an expected {@link MaxSum} object indicating lower index, higher index in
	 * the list with max sum
	 */
	MaxSum expectedMaxSum;

	@Parameters
	public static Collection<Object[]> init() {
		return Arrays.asList(new Object[][] { { // First row
				new int[] { 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5,
						-22, 15, -4, 7 }, MaxSum.of(7, 10, 43) } });
	}

	@Before
	public void before() {
		list = new ArrayList<>();
		for (int i : numbers)
			list.add(i);
	}

	@Test
	public void test() {
		FindMaximumSubArray problem = new FindMaximumSubArray();
		Assert.assertEquals(expectedMaxSum,
				problem.findMaximumSubArray(list, 0, list.size() - 1));
		Assert.assertEquals(expectedMaxSum, problem.findMaxSumSequence(numbers));
	}
}
