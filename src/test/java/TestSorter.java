/**
 * 
 */
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
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
	}
	@Test public void testMerge() {
		SortInterface s=Sorter.of("merge", a);
		int[] b=s.sort();
		log.info(ArrayUtils.toString(a));
	}
}
