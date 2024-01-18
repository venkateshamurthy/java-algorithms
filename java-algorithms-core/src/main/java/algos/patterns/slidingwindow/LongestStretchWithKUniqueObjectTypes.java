package algos.patterns.slidingwindow;

import lombok.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This pattern can be used to detect the Maximum fruits of two types in a basket when
 * gathering fruits froma sequence of trees.
 * OR
 * Maximum consecutive characters
 *
 * @param <T>
 */
@RequiredArgsConstructor(staticName = "of")
class LongestStretchWithKUniqueObjectTypes<T> {
    private static <X, Y, Z> BiFunction<X, Y, Z> toBiFunction(BiFunction<X, Y, Z> function) {
        return function;
    }

    private static <X, Y> Function<X, Y> toFunction(Function<X, Y> function) {
        return function;
    }

    //"abcbdbdbbdcdabd";
    private T[] find(T[] t, int k) {
        Map<T, Integer> window = new LinkedHashMap<>();
        int end = 0, begin = 0;
        for (int low=0, high = 0; high < t.length; high++) {
            window.merge(t[high], 1, Integer::sum);
            while (window.size() > k) {
                window.merge(t[low++], -1,
                        toBiFunction(Integer::sum).andThen(v->v>0?v:null));
            }
            // update the maximum window size if necessary
            if (end - begin < high - low) {
                end = high;
                begin = low;
            }
        }
        return ArrayUtils.subarray(t, begin, end + 1);
    }
    public static void main(String[] args) {
        String s = "abcbdbdbbdcdabd";
        int k = 3;
        System.out.printf("Longest substring with %d unique characters = %s%n",
                k, ArrayUtils.toString(of().find(ArrayUtils.toObject(s.toCharArray()), k)));
    }
}