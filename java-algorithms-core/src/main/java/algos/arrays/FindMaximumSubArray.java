/**
 * 
 */
package algos.arrays;
import static java.lang.Integer.MIN_VALUE;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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
	    MaxSum.Builder maxSumBuilder = MaxSum.createBuilder();
		maxSumBuilder.sum(inputArray[0]);
		int curSum = inputArray[0];

	    for (int i = 1; i < inputArray.length; i++)  {
	    	//If running sum < 0 ; open a new window
	        if (curSum < 0) {
	            curSum = 0;
				maxSumBuilder.low(i);
	        }

	        // Keep adding
	        curSum += inputArray[i];

	        // is curSum > maxSum
	        maxSumBuilder.withCurrentIndexAndSum(curSum, i);

	    } 
	    return maxSumBuilder.build();
	}
	
	/** A Logarithmic algorithm.*/
	public static MaxSum findMaximumSubArray(int[] list, int l, int h) {
		if (l >= h) {
			MaxSum ms = MaxSum.builder().low(l).high(h).sum(Integer.MIN_VALUE).build();
			log.info("NoCross:{}" , ms);
			return ms;
		} else {
			int low = l, high = h, mid = (l + (h-l) / 2);
			MaxSum  lMaxSum =  findMaximumSubArray(list, low,         mid),
					rMaxSum =  findMaximumSubArray(list, mid + 1,   high),
			        cMaxSum =  findMaximumSubArray(list, low,  mid,   high);
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

		MaxSum ms = MaxSum.builder().low(cLow).high(cHigh)
				.sum(
				rightSum != MIN_VALUE && leftSum != MIN_VALUE ?
						(rightSum + leftSum) : MIN_VALUE).build();
		log.info("Cross:{}" , ms);
		return ms;
	}
	
}

@Builder
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal =true)
class MaxSum  implements Comparable<MaxSum>{
	int low, high;
	int sum;
	static final MaxSum INVALID=builder().high(MIN_VALUE).low(MIN_VALUE).sum(MIN_VALUE).build();

	static MaxSum findMax(MaxSum left, MaxSum right, MaxSum cross) {
		return Stream.of(left, right, cross)
				.max(MaxSum::compareTo)
				.orElse(INVALID);
	}

	@Override
	public int compareTo(@NonNull final MaxSum other) {
		return Integer.compare(this.sum, other.sum);
	}
	public static Builder createBuilder(){
		return new Builder();
	}
	public static class Builder extends MaxSumBuilder {
		public MaxSumBuilder withCurrentIndexAndSum(int currentIndex, int currentSum) {
			if (currentSum > super.sum) {
				sum(currentSum).high(currentIndex);
			}
			return this;
		}
	}
}