package algos.sort;

import java.util.Comparator;
import java.util.List;

public enum Partitioner implements PartitionStrategy {
    LOMUTO(new Lomuto()), HOARE(new Hoare());

    final PartitionStrategy partitionStrategy;

    private Partitioner(PartitionStrategy partionStrategy) {
        this.partitionStrategy = partionStrategy;
    }

    @Override
    public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator, List<T> numbers, int start, int end) {
        return partitionStrategy.partition(pivotingStrategy, comparator, numbers, start, end);
    }

    @Override
    public <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator, T[] numbers, int start, int end) {
        return partitionStrategy.partition(pivotingStrategy, comparator, numbers, start, end);
    }

    @Override
    public int partition(PivotingStrategy pivotingStrategy, int[] numbers, int start, int end) {
        return partitionStrategy.partition(pivotingStrategy, numbers, start, end);
    }
}