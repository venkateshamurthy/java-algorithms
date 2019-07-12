package algos.arrays;

import java.util.Arrays;
import java.util.Collection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestFindSecondLargest {
	int[] numbers;
	MaxAndNextMax expected;
	FindSecondLargest finder = new FindSecondLargest();

	@Parameters
	public static Collection<Object[]> init() {
		return Arrays
				.asList(new Object[][] { {
						new int[] { 4, 5, 1, 2, 8, 7, 9, 6 },
						MaxAndNextMax.of(9, 8) } });
	}

	@Test
	public void test() {
		Assert.assertEquals(expected, finder.find(numbers));
	}

}
