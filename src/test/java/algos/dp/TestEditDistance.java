package algos.dp;

import static java.lang.Math.*;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/** 
 * A simple unit test for {@link EditDistance}
 * @author vemurthy
 *
 */
@RunWith(Parameterized.class)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestEditDistance {

	String firstString;   //first 
	String secondString;  //second
	int expectedDistance; //expected distance between first and second

	@Parameters
	public static Collection<Object[]> init() {
		return Arrays.asList(new Object[][] { 
				{ "infosys", "invensys", 3 },
				{ "qualcom", "broadcom", 4 }, 
				{ "alcatel", "nortel",   4 },
				{ "abcd",    "pqrst",    5 },
				{ "",        "pqrst",    5 },
				{ "pqrst",   "pqrst",    0 }
				
		});
	}

	@Test
	public void testDistance() {
		Assert.assertEquals(expectedDistance, ed.computeLevenshteinDistance(firstString, secondString));
	}

	static EditDistance ed= new EditDistance();
}
