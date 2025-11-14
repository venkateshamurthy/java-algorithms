package algos.sort;

import java.util.*;

/**
 * Enum implementation of {@link PivotingStrategy}
 *
 * @author vmurthy
 *
 */
public enum PIVOT_STRATEGY implements PivotingStrategy {

    MEDIAN() {
        @Override
        public int pivot(int[] numbers, int low, int high) {
            return low + (high - low) / 2;
        }
        public <T> int pivot(List<T> numbers, int low, int high) {
            return low + (high - low) / 2;
        }

        @Override
        public <T> int pivot(T[] numbers, int low, int high) {
            return low + (high - low) / 2;
        }

    },
    RANDOM() {
        @Override
        public int pivot(int[] numbers, int low, int high) {
            return low + random.nextInt(high - low);
        }
        public <T> int pivot(List<T> numbers, int low, int high) {
            return low + random.nextInt(high - low);
        }

        @Override
        public <T> int pivot(T[] numbers, int low, int high) {
            return low + random.nextInt(high - low);
        }

    },
    MEDIAN_OF_3() {
        public <T> int pivot(List<T> numbers, int low, int high) {
            final int middle = low + (high - low) / 2;
            Map.Entry<Integer, Integer>[] pivots = new AbstractMap.SimpleEntry[]{
                    new AbstractMap.SimpleEntry<>(numbers.get(low), low),
                    new AbstractMap.SimpleEntry<>(numbers.get(middle), middle),
                    new AbstractMap.SimpleEntry<>(numbers.get(high), high)};
            Arrays.sort(pivots, Map.Entry.comparingByKey());
            return pivots[1].getValue();
        }

        @Override
        public <T> int pivot(T[] numbers, int low, int high) {
            final int middle = low + (high - low) / 2;
            Map.Entry<Integer, Integer>[] pivots = new AbstractMap.SimpleEntry[]{
                    new AbstractMap.SimpleEntry<>(numbers[low], low),
                    new AbstractMap.SimpleEntry<>(numbers[middle], middle),
                    new AbstractMap.SimpleEntry<>(numbers[high], high)};
            Arrays.sort(pivots, Map.Entry.comparingByKey());
            return pivots[1].getValue();
        }

        @Override
        public int pivot(int[] numbers, int low, int high) {
            final int middle = low + (high - low) / 2;
            Map.Entry<Integer, Integer>[] pivots = new AbstractMap.SimpleEntry[]{
                    new AbstractMap.SimpleEntry<>(numbers[low], low),
                    new AbstractMap.SimpleEntry<>(numbers[middle], middle),
                    new AbstractMap.SimpleEntry<>(numbers[high], high)};
            Arrays.sort(pivots, Map.Entry.comparingByKey());
            return pivots[1].getValue();
			/*
			final double wBegin = numbers[low];
			final double wMiddle = numbers[middle];
			final double wEnd = numbers[high];
			if (wBegin < wEnd) {
				if (wMiddle < wBegin)
					return low;
				else if (wMiddle > wEnd)
					return high;
				else
					return middle;
			} else { // if(wEnd>wBegin)
				if (wMiddle < wBegin)
					return low;
				else if (wMiddle > wEnd)
					return high;
				else
					return middle;
			}
			*/
        }

    };

    final Random random = new Random();
}