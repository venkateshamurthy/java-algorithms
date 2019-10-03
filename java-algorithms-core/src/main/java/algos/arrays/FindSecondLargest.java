/**
 *
 */
package algos.arrays;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vmurthy
 */

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FindSecondLargest {

    public MaxAndNextMax find(int[] args) {
        int max = Integer.MIN_VALUE, nextMax = Integer.MIN_VALUE;
        for (int i : args) {
            if (max < i) {
                nextMax = max;
                max = i;
            } else if (max > nextMax && nextMax < i)
                nextMax = i;
        }
        log.info("Max={} nextMax={}", max, nextMax);
        return MaxAndNextMax.of(max, nextMax);
    }
}

/**
 * A simple holder class for holding the result of interest
 *
 * @author vemurthy
 */
@Data(staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MaxAndNextMax {
    int max, nextMax;
}
