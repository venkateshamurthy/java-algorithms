/**
 * 
 */
package algos.lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;//Using lombok annotation for log4j handle

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

/**
 * Maximum Sub array problem. To find maximum sums of Left part, right part and
 * crossing part Left part: [0-mid], Right Part: [mid+1,high] [0,...i...mid]
 * [mid+1,.....j...high]
 * 
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FindMaximumSubArray {
	static final Logger log = LogManager
			.getLogger(StringFormatterMessageFactory.INSTANCE);

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(new Integer[] { 13, -3, -25, 20, -3,
				-16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7 });
		FindMaximumSubArray problem = new FindMaximumSubArray();
		MaxSum ms = problem.findMaximumSubArray(list, 0, list.size() - 1);
		log.debug("Final:" + ms);
	}

	private MaxSum findMaximumCrossingSubArray(List<Integer> list, int low,
			int mid, int high) {

		int leftSum = Integer.MIN_VALUE, rightSum = Integer.MIN_VALUE, cLow = low, cHigh = high;
		// Left Part
		for (int i = mid, sum = 0; i >= 0; i--) {
			sum += list.get(i);
			if (sum > leftSum) {
				leftSum = sum;
				cLow = i;
			}
		}

		// Right Part
		for (int sum = 0, i = mid + 1; i < high; i++) {
			sum += list.get(i);
			if (sum > rightSum) {
				rightSum = sum;
				cHigh = i;
			}
		}

		MaxSum ms = MaxSum
				.of(cLow,cHigh,rightSum != Integer.MIN_VALUE // remember leftSum+rightSum
													// may overflow when both
													// are MIN_VALUE
						&& leftSum != Integer.MIN_VALUE ? (rightSum + leftSum)
						: Integer.MIN_VALUE);
		log.debug("Cross:" + ms);
		return ms;
	}

	public MaxSum findMaximumSubArray(List<Integer> list, int l, int h) {
		if (l >= h) {
			MaxSum ms = MaxSum.of(l,h,list.get(l));
			log.debug("NoCross:" + ms);
			return ms;
		} else {
			int low = l, high = h, mid = ((l + h) / 2);
			MaxSum lMaxSum = findMaximumSubArray(list, low, mid), rMaxSum = findMaximumSubArray(
					list, mid + 1, high);
			MaxSum cMaxSum = findMaximumCrossingSubArray(list, low, mid, high);
			return MaxSum.findMax(lMaxSum, rMaxSum, cMaxSum);
		}
	}

}

@Data(staticConstructor = "of")
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal =true)
class MaxSum {
	int low, high;
	int sum;

	static MaxSum findMax(MaxSum left, MaxSum right, MaxSum cross) {
		if (left.sum > right.sum && left.sum > cross.sum)
			return left;
		else if (right.sum > left.sum && right.sum > cross.sum)
			return right;
		else
			return cross;
	}
}