package algos.sort;

import java.util.List;

interface PivotingStrategy {
    int pivot(int[] numbers, int low, int high);
    //int pivot(List<Integer> numbers, int low, int high);

    <T> int pivot(List<T> numbers, int low, int high);

    <T> int pivot(T[] numbers, int low, int high);
}