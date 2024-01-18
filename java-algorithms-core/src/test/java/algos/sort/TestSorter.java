package algos.sort;
/**
 * 
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)

public class TestSorter {
	int a[]=new int[10];
	int expected[]=new int[a.length];
	@Before public void before() {
		//intlist.stream().toArray(sz-> new int[sz])
		for (int i = 0; i < a.length; i++) {
			a[i] = 9 - i;
			expected[i]=i;
		}
		//log.info("Before sort a:{}",ArrayUtils.toString(a));
	}
	private void testSort(SortInterface s) {
		int[] sorted=s.sort();
		//log.info("After sorting through {}:{}",s.getClass().getSimpleName(),ArrayUtils.toString(sorted));
		Assert.assertArrayEquals( "sorted:"+ Arrays.toString(sorted),expected,sorted);
	}
	@Test public void testMerge() {
		testSort(Sorter.of("merge", a));
	}
	@Test public void testQuick() {
		testSort(Sorter.of("quick", a));
	}

	@Test public void testCount() {
		testSort(Sorter.of("count", a));
	}
	
	@Test public void testInsertion() {
		testSort(Sorter.of("insertion", a));
	}

	@Test public void testBinaryInsertion() {
		testSort(Sorter.of("binsertion", a));
	}
	@Test public void testSelection() {
		testSort(Sorter.of("selection", a));
	}
	
	@Test public void testHeap() {
    testSort(Sorter.of("heap", a));
  }
}
