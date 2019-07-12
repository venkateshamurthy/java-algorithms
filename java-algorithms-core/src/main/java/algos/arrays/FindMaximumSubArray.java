/**
 * 
 */
package algos.arrays;
import static java.lang.Integer.MIN_VALUE;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.stream.Stream;
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
	public static void main(String[] args){
		 int[] inputArray=new int[]{1, -2, 3, 10, -4, 7, 2, -5};
		 log.info("{}",findMaxSumSequence(inputArray));
		 log.info("{}",findMaximumSubArray(inputArray,0,inputArray.length-1));
	}
	
	/** A Linear time algorithm. */
	public static MaxSum findMaxSumSequence (int inputArray[]) {
	    if (inputArray == null || inputArray.length == 0)
	        throw new IllegalArgumentException("Array is empty");
	    //assume a max sub sum segment with start and end index and a max sum value
	    int maxSum = inputArray[0];
	    int maxStartIndex = 0;
	    int maxEndIndex = 0;
	 
	    int curSum = inputArray[0];
	 
	    for (int i = 1; i < inputArray.length; i++)  {
	    	//If running sum < 0 ; open a new window
	        if (curSum < 0) {
	            curSum = 0;
	            maxStartIndex = i;
	        }
	        // Keep adding
	        curSum += inputArray[i];
	        // is curSum > maxSum; 
	        if (curSum > maxSum) {
	            maxSum = curSum;
	            maxEndIndex = i;
	        }
	    } 
	    return MaxSum.of(maxStartIndex, maxEndIndex, maxSum);
	}
	
	/** A Logarithmic algorithm.*/
	public static MaxSum findMaximumSubArray(int[] list, int l, int h) {
		if (l >= h) {
			MaxSum ms = MaxSum.of(l,h,list[l]);
			log.info("NoCross:{}" , ms);
			return ms;
		} else {
			int low = l, high = h, mid = (l +(h-l) / 2);
			MaxSum  lMaxSum =  findMaximumSubArray(list, low,      mid), 
					rMaxSum =  findMaximumSubArray(list, mid + 1,  high),
			        cMaxSum =  findMaximumSubArray(list, low, mid, high);
			return MaxSum.findMax(lMaxSum, rMaxSum, cMaxSum);
		}
	}
	
	private static MaxSum findMaximumSubArray(int[] list,  int low, int mid, int high) {

		int leftSum = MIN_VALUE, rightSum = MIN_VALUE, 
				cLow = low, cHigh = high;
		// Left Part
		for (int i = mid, sum = 0; i >= low; i--) {
			sum += list[i];
			if (sum > leftSum) {
				leftSum = sum;
				cLow = i;
			}
		}

		// Right Part
		for (int sum = 0, i = mid + 1; i < high; i++) {
			sum += list[i];
			if (sum > rightSum) {
				rightSum = sum;
				cHigh = i;
			}
		}

		MaxSum ms = MaxSum.of(cLow, cHigh,
				rightSum != MIN_VALUE && leftSum != MIN_VALUE ? (rightSum + leftSum) : MIN_VALUE);
		log.info("Cross:{}" , ms);
		return ms;
	}
	
}

@Data(staticConstructor = "of")
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal =true)
class MaxSum  {
	int low, high;
	int sum;
	static MaxSum INVALID=of(MIN_VALUE, MIN_VALUE, MIN_VALUE);

	static MaxSum findMax(MaxSum left, MaxSum right, MaxSum cross) {
		return Stream.of(left, right, cross)
				.min((a,b)->Integer.compare(a.sum,b.sum))
				.orElse(INVALID);
	}
}