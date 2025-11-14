package algos.heaps;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class FindMinimumNumberOfMeetingRooms {

    static int findMinimumRooms (final List<Entry<Integer, Integer>> intervals) {
        final PriorityQueue<Entry<Integer, Integer>> endTimesPQ =
                new PriorityQueue<>(Entry.comparingByValue());//heapify by end times

        Stream.concat(  intervals.stream(),
                        Stream.of(entry(Integer.MAX_VALUE, Integer.MAX_VALUE)))
                .sorted(Entry.comparingByKey()) //sort by Start times
                .forEach(endTimesPQ::add); //add to endTimesPQ

        AtomicInteger minRooms = new AtomicInteger();
        endTimesPQ.stream().reduce((cur, nxt) ->
        {
            System.out.println(cur);
            minRooms.addAndGet(cur.getValue() >= nxt.getKey() ? 1 : 0);
            return nxt; // remember this goes to the next reduction
        });
        return minRooms.get();
    }

    /**
     * Find Maximum Meetings one can attend
     * @param intervals is a list (as against a map) as the start times can repeat in the intervals
     * @return
     */
    static int findMaximumMeetingsOneCanAttend (final List<Entry<Integer, Integer>> intervals) {
        //PriorityQueue to hold by end times (meaning entry's value)
        final PriorityQueue<Entry<Integer, Integer>> endTimesPQ =
                new PriorityQueue<>(Entry.comparingByValue());//heapify by end times

        Stream.concat(  intervals.stream(),
                        Stream.of(entry(Integer.MAX_VALUE, Integer.MAX_VALUE)))
                .sorted(Entry.comparingByKey()) //sort by Start times
                .forEach(endTimesPQ::add); //add to endTimesPQ

        AtomicInteger max = new AtomicInteger();
        System.out.println(endTimesPQ);
        endTimesPQ.stream().reduce((cur, nxt) ->
        {
            System.out.println(cur);
            max.addAndGet(cur.getValue() <= nxt.getKey() ? 1 : 0);
            return nxt; // remember this goes to the next reduction
        });
        return max.get();
    }

    public static void main(String [] args) {
        List<Entry<Integer, Integer>> intervals = List.of(
                entry(1,5), entry(4,5),
                entry(7, 8), entry(10,12), entry(11, 13));
        System.out.println(findMaximumMeetingsOneCanAttend(new ArrayList<>(intervals)));
        System.out.println(findMinimumRooms(new ArrayList<>(intervals)));
    }

   static <K,V> Entry<K,V> entry(K k, V v) {return new AbstractMap.SimpleEntry<>(k,v);}

}
