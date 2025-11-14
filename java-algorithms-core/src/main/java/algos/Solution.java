package algos;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    static PartitionStrategy partitioner = Partitioner.LOMUTO;;

    static ArrayList<Integer> find_top_k_frequent_elements(ArrayList<Integer> numbers, Integer k) {
        Map<Integer, Integer> freq = new HashMap<>();
        numbers.forEach(key -> freq.merge(key, 1, Integer::sum));
        List<Map.Entry<Integer, Integer>> input = new ArrayList<>(freq.entrySet());
        Collections.shuffle(input);
        System.out.println("Before Quick Select:" + input);
        quickSelect(Map.Entry.comparingByValue(), input, 0, input.size() - 1, input.size() - k);
        System.out.println("After Quick Select:" + input);
        System.out.println("Top "+k+" occurRing elements:"+input.subList(input.size() - k, input.size()));
        return  input.subList(input.size() - k, input.size()).stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static <T> T quickSelect(Comparator<T> comparator, List<T> numbers, int low, int high, int index) {
        if (low >= high)
            return numbers.get(low);
        int q = partitioner.partition(Solution.PIVOT_STRATEGY.RANDOM, comparator, numbers, low, high);
        // kth smallest means k-1; n-k means kth largest
        if (index == q)
            return numbers.get(q);
        else if (index < q)
            return quickSelect(comparator, numbers, low, q - 1, index);
        else
            return quickSelect(comparator, numbers, q + 1, high, index);
    }


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
    }

    interface PivotingStrategy {
        <T> int pivot(List<T> numbers, int low, int high);

    }

    interface PartitionStrategy {
        <T> int partition(PivotingStrategy pivotingStrategy, Comparator<T> comparator,
                          List<T> numbers, int start, int end);

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
                        Collections.swap(numbers, smaller, bigger);
                    }
                }
                Collections.swap(numbers, start, smaller);
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
                    else Collections.swap(numbers, smaller++, bigger--);
                }
                Collections.swap(numbers, start, bigger);
                return bigger;
            }
        }
    }

    enum PIVOT_STRATEGY implements PivotingStrategy {

        MEDIAN() {

            public <T> int pivot(List<T> numbers, int low, int high) {
                return low + (high - low) / 2;
            }
        },
        RANDOM() {
            @Override
            public <T> int pivot(List<T> numbers, int low, int high) {
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

        };

        Random random = new Random();
    }

    static class Utils {
        public static <T> void swap(T[] t,int i,int j) {
            if(i!=j) {
                T temp=t[i];
                t[i]=t[j];
                t[j]=temp;
            }
        }
        public static  void swap(int[] t,int i,int j) {
            if(i!=j && t[i] != t[j]) {
                int temp=t[i];
                t[i]=t[j];
                t[j]=temp;
            }
        }
    }

    static ArrayList<Integer> kth_largest(Integer k, ArrayList<Integer> initial_stream, ArrayList<Integer> append_stream) {
        // Write your code here.

        PriorityQueue<Integer> q = new PriorityQueue<>();
        initial_stream.forEach(i->{
            q.add(i);
            while(q.size() > k) q.poll();
        });
        ArrayList<Integer> result = new ArrayList<>();
        //result.add(q.peek());
        append_stream.forEach(a -> {
            q.add(a);
            while(q.size() > k) q.poll();
            result.add(q.peek());
        });
        return  result;
    }

    public static void main(String[] args) {
        System.out.println(kth_largest(2, (ArrayList<Integer>) Lists.newArrayList(4, 6),
                (ArrayList<Integer>) Lists.newArrayList(5, 2, 20)));
    }
}
