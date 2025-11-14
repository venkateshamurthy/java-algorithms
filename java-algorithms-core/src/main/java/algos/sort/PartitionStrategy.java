package algos.sort;

import algos.utils.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static algos.sort.PIVOT_STRATEGY.RANDOM;
import static java.util.Collections.swap;

public interface PartitionStrategy {
    <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                      List<T> numbers, int start, int end);

    <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                      T[] numbers, int start, int end);

    int partition(PivotingStrategy pivotingStrategy, int[] numbers, int start, int end) ;


    default int partition(int[] numbers, int start, int end) {
        return partition(RANDOM, numbers, start, end);
    }

    default <T> int partition(Comparator<T> comparator, List<T> numbers, int start, int end){
        return partition(RANDOM, comparator, numbers, start, end);
    }

    default <T> int partition( Comparator<T> comparator, T[] numbers, int start, int end) {
        return partition(RANDOM, comparator, numbers, start, end);
    }
    class Lomuto implements PartitionStrategy {
        public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                                 List<T> numbers, int start, int end) {
            //pivot                                                                                end
            // 0      1      2      3      4      5      6      7      8      9      10      11    12
            // 5,     1,     8,     3,     9,     4,     1,     7,     5,     13,    19,     15,   16
            //smal    big-------------------------------------------------------------------------->|
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Collections.swap(numbers, start, pivotIndex);
            int smaller = start; // start is the pivot
            for (int bigger = start + 1; bigger <= end; bigger++) {
                if (comparator.compare(numbers.get(bigger), numbers.get(start)) <= 0) {
                    smaller++;
                    swap(numbers, smaller, bigger);
                }
            }
            swap(numbers, start, smaller);
            return smaller;
        }

        public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                                 T[] numbers, int start, int end) {
            //pivot                                                                                end
            // 0      1      2      3      4      5      6      7      8      9      10      11    12
            // 5,     1,     8,     3,     9,     4,     1,     7,     5,     13,    19,     15,   16
            //smal    big-------------------------------------------------------------------------->|
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Utils.swap(numbers, start, pivotIndex);
            int smaller = start; // start is the pivot
            for (int bigger = start + 1; bigger <= end; bigger++) {
                if (comparator.compare(numbers[bigger], numbers[start]) <= 0) {
                    smaller++;
                    Utils.swap(numbers, smaller, bigger);
                }
            }
            Utils.swap(numbers, start, smaller);
            return smaller;
        }

        public int partition(PivotingStrategy pivotingStrategy, int[] numbers, int start, int end) {
            //pivot                                                                                end
            // 0      1      2      3      4      5      6      7      8      9      10      11    12
            // 5,     1,     8,     3,     9,     4,     1,     7,     5,     13,    19,     15,   16
            //smal    big-------------------------------------------------------------------------->|
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Utils.swap(numbers, start, pivotIndex);
            int smaller = start; // start is the pivot
            for (int bigger = start + 1; bigger <= end; bigger++) {
                if (numbers[bigger] <= numbers[start]) {
                    smaller++;
                    Utils.swap(numbers, smaller, bigger);
                }
            }
            Utils.swap(numbers, start, smaller);
            return smaller;
        }
    }

    class Hoare implements PartitionStrategy {
        public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                                 List<T> numbers, int start, int end) {
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Collections.swap(numbers, start, pivotIndex);
            int bigger = end; // start is the pivot
            for (int smaller = start + 1; smaller <= bigger; ) {
                if (comparator.compare(numbers.get(smaller), numbers.get(start)) < 0) smaller++;
                else if (comparator.compare(numbers.get(bigger), numbers.get(start)) >= 0) bigger--;
                else swap(numbers, smaller++, bigger--);
            }
            swap(numbers, start, bigger);
            return bigger;
        }

        public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                                 T[] numbers, int start, int end) {
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Utils.swap(numbers, start, pivotIndex);
            int bigger = end; // start is the pivot
            for (int smaller = start + 1; smaller <= bigger; ) {
                if (comparator.compare(numbers[smaller], numbers[start]) < 0) smaller++;
                else if (comparator.compare(numbers[bigger], numbers[start]) >= 0) bigger--;
                else Utils.swap(numbers, smaller++, bigger--);
            }
            Utils.swap(numbers, start, bigger);
            return bigger;
        }

        public int partition(PivotingStrategy pivotingStrategy,
                                 int[] numbers, int start, int end) {
            int pivotIndex = pivotingStrategy.pivot(numbers, start, end);
            Utils.swap(numbers, start, pivotIndex);
            int bigger = end; // start is the pivot
            for (int smaller = start + 1; smaller <= bigger; ) {
                if (numbers[smaller] < numbers[start]) smaller++;
                else if (numbers[bigger] >= numbers[start]) bigger--;
                else Utils.swap(numbers, smaller++, bigger--);
            }
            Utils.swap(numbers, start, bigger);
            return bigger;
        }
    }
}

