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
public class TestMoveNumberToRight {
	int[] numbers;
	int[] expecteds;
	int numberToMove;
	MoveNumberToRight mover = new MoveNumberToRight();
	@Parameters
	public static Collection<Object[]> init() {
		return Arrays.asList(new Object[][] {{
				new int[] { 0, 5, 0, 2, 3, 0, 3, 5, 0 },
				new int[] {  5, 5, 3, 2, 3, 0, 0, 0, 0 },
				0 }});
	}

	@Test
	public void test() {
		Assert.assertArrayEquals(expecteds, mover.moveXToRight(numbers, numberToMove));
	}

}
