package algos.patterns.twopointers;

class MaximumSubArraySum {

    static class Result {
        long sum;
        int left, right;
        Result(long sum, int left, int right) {
            this.sum = sum;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return String.format("sum: %d, left: %d, right: %d", sum, left, right);
        }
    }

    /** Maximum Subarray for a  given array*/
    static Result findMaxSumSubArray(int[] nums) {
        int left = 0, right = 0;
        long sum = 0;
        long maxSum = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > maxSum) {
                maxSum = sum;
                right = i;
            } else if (sum < 0) {
                sum = 0;
                left = (left < right) ? i + 1 : left;
            }
        }
        return new Result(maxSum, left, right);
    }

    /** Maximum Subarray for a given array but for a fixed window size W*/
    static Result findMaxSumSubArray(int[] nums, int W) {
        int right = 0;
        long sum = 0;
        long maxSum = Long.MIN_VALUE;

        for(int i=0; i<W; i++) sum += nums[i];
        maxSum = sum;
        right = W-1;

        for (int i = W; i < nums.length; i++) {
            sum += nums[i] - nums[i - W];
            if (sum > maxSum) {
                maxSum = sum;
                right = i;
            }
        }
        return new Result(maxSum, right + 1 - W, right);
    }

    public static void main(String[] args) {
        System.out.println(findMaxSumSubArray(new int[]{2, 3, -8, 7, -1, 2, 3}));
        System.out.println(findMaxSumSubArray(new int[]{2, 3, -8, 7, -1, 2, 3}, 4));
    }
}
