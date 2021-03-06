package algos.arrays;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class FindFirstTwoNumbersAddingToASum {
    private final int[] input;

    public Optional<Pair<Integer,Integer>> findPair(final int sum) {
        final Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < input.length; i++) {
            if (map.containsKey(sum - input[i])) {
                return Optional.of(ImmutablePair.of(i, map.get(sum - input[i])));
            } else {
                map.put(input[i], i);
            }
        }
        return Optional.empty();
    }
}
