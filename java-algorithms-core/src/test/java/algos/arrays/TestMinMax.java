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

public class TestMinMax{
	FindMinMax2 finder = new FindMinMax2();

	@Test
	public void test() {
	  int[] inputArray = new int[] { -22, -15, 6, -2, -3, 1, 5, -6, -8, 9 };
    int[] minMax = finder.findMinMaxRecursive(inputArray, 0, inputArray.length - 1);
    Assert.assertArrayEquals(new int[] {-22, 9}, minMax);
    minMax = finder.findMinMaxNaive(inputArray);
    Assert.assertArrayEquals(new int[] {-22, 9}, minMax);
	}

}
