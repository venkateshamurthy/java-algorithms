/**
 * 
 */
package algos.arrays;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
//Using lombok annotation for log4j handle

/**
 * Maximum Sub array problem. To find maximum sums of Left part, right part and
 * crossing part Left part: [0-mid], Right Part: [mid+1,high] [0,...i...mid]
 * [mid+1,.....j...high]
 * 
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FindMaximumSubArray {
	
	private MaxSum findMaximumCrossingSubArray(List<Integer> list, 
			final int low, final int mid, final int high) {

		int leftSum = Integer.MIN_VALUE, rightSum = Integer.MIN_VALUE, 
				cLow = low, cHigh = high;
		// Left Part
		for (int i = mid, sum = 0; i >= low; i--) {
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
		log.info("Cross:{}" , ms);
		return ms;
	}

	public MaxSum findMaximumSubArray(List<Integer> list, int l, int h) {
		if (l >= h) {
			MaxSum ms = MaxSum.of(l,h,list.get(l));
			log.info("NoCross:{}" , ms);
			return ms;
		} else {
			int low = l, high = h, mid = ((l + h) / 2);
			MaxSum lMaxSum =  findMaximumSubArray(list, low,     mid), 
					rMaxSum = findMaximumSubArray(list, mid + 1, high);
			MaxSum cMaxSum = findMaximumCrossingSubArray(list, low, mid, high);
			return MaxSum.findMax(lMaxSum, rMaxSum, cMaxSum);
		}
	}
	
	public static MaxSum findMaxSumSequence (int inputArray[])
	{
	    if (inputArray == null || inputArray.length == 0)
	        throw new IllegalArgumentException("Array is empty");
	 
	    int size = inputArray.length;
	 
	    int maxSum = inputArray[0];
	    int maxStartIndex = 0;
	    int maxEndIndex = 0;
	 
	    int curSum = inputArray[0];
	    int curStartIndex = 0;
	 
	 
	    for (int i = 1; i < size; i++)
	    {
	        if (curSum < 0) {
	            curSum = 0;
	            curStartIndex = i;
	        }
	 
	        curSum += inputArray[i];
	 
	        if (curSum > maxSum) {
	            maxSum = curSum;
	            maxStartIndex = curStartIndex;
	            maxEndIndex = i;
	        }
	    } 
	 
	    return MaxSum.of(maxStartIndex, maxEndIndex, maxSum);
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