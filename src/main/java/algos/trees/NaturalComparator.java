/**
 * 
 */
package algos.trees;
import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle
/**
 * @author vmurthy
 *
 */
//Log4j Handle creator (from lombok)
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NaturalComparator<T extends Comparable<T>> implements	Comparator<T> {
	@Override public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}
}
