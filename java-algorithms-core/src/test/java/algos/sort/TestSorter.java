package algos.sort;
/**
 * 
 */
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algos.sort.SortInterface;
import algos.sort.Sorter;
//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
public class TestSorter {
	int a[]=new int[10];
	int expected[]=new int[a.length];
	@Before public void before() {
		for (int i = 0; i < a.length; i++) {
			a[i] = 9 - i;
			expected[i]=i;
		}
		log.info("Before sort a:{}",ArrayUtils.toString(a));
	}
	private void testSort(SortInterface s) {
		int[] sorted=s.sort();
		log.info("After sorting through {}:{}",s.getClass().getSimpleName(),ArrayUtils.toString(sorted));
		Assert.assertArrayEquals(expected,sorted);
	}
	@Test public void testMerge() {
		testSort(Sorter.of("merge", a));
	}
	@Test public void testQuick() {
		testSort(Sorter.of("quick", a));
	}
	
	@Test public void testInsertion() {
		testSort(Sorter.of("insertion", a));
	}
	@Test public void testSelection() {
		testSort(Sorter.of("selection", a));
	}
	
	@Test public void testHeap() {
    testSort(Sorter.of("heap", a));
  }
}
