package algos.arrays;

import java.util.Arrays;

import static java.lang.Integer.max;

class KadaneAlgorithm {
    public static int[] maxSubArraySum(int[] input) {
        int localMax = input[0];
        int globalMax = input[0];
        int start = 0, end = 0;

        for (int i = 1; i < input.length; i++) {
            localMax =  max(localMax + input[i], input[i]);
            if(localMax <= input[i]) start = i;
            if(localMax > globalMax) end = i;
            globalMax = max(localMax, globalMax);
        }

        if(start > end) start = end;

        return Arrays.copyOfRange(input, start, end + 1);
    }

    public static void main(String[] args) {
        int[] arr = //{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        {2, 3, -8, 7, -1, 2, 3};
        var maxSum = maxSubArraySum(arr);
        System.out.format("Maximum contiguous array:%s and max sum is:%d\n",
                Arrays.toString(maxSum), Arrays.stream(maxSum).sum());
    }
}