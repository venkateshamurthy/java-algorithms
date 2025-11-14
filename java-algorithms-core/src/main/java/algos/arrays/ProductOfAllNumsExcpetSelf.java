package algos.arrays;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;;

@RequiredArgsConstructor
public class ProductOfAllNumsExcpetSelf {

    final int[] numbers;

    public long[] doAction() {
        final long[]    answer  = new long[numbers.length];
        long allProduct = Arrays.stream(numbers)
                .mapToLong(i->(long)i)
                .reduce((a, b) -> a * b).orElseThrow();
        for (int i = 0; i < numbers.length; i++) {
            answer[i] = allProduct/numbers[i];
        }
        return answer;
    }

}
