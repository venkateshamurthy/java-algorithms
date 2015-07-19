package algos.arrays;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestMinMax{
	FindMinMax2 finder = new FindMinMax2();
	/** Populate this before tests */
	@NonFinal
	List<Integer> list;

	/** A array of numbers containing positive and negative numbers */
	int[] numbers;
	
	MinMax expected;

	@Parameters
	public static Collection<Object[]> init() {
		return Arrays.asList(new Object[][] { 
				{ // First row
						new int[] { 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5,
						-22, 15, -4, 7 } ,new MinMax(-25, 20)
				},
				{
					new int[]{ 4, 3, 5, 1, 2, 6, 9, 2, 10, 11, 12 },
					new MinMax(1,12)
				}
		});
	}
	@Test
	public void test() {
		Assert.assertEquals(expected,finder.findMinMaxRecursive(numbers, 0, numbers.length-1));
	}

}
