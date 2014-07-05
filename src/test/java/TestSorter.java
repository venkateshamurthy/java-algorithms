/**
 * 
 */
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.junit.Before;
import org.junit.Test;

import algos.sort.SortInterface;
import algos.sort.Sorter;

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
public class TestSorter {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);
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
		log.debug(ArrayUtils.toString(a));
	}
}
