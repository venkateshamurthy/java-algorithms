package algos.patterns.slidingwindow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@ToString(of = {"maxStart", "maxEnd", "maxSum", "runningSum"}, doNotUseGetters = true)
@RequiredArgsConstructor
public class SlidingWindow implements Iterator<Deque<Integer>> {
    final List<Integer> objects;
    final int K;
    int start, end, maxStart, maxEnd, runningSum, maxSum = Integer.MIN_VALUE;
    Deque<Integer> que;

    @PostConstruct
    public void postConstruct() {
        end = start + K;
        que = new ArrayDeque<Integer>(K);
    }

    public static void main(String[] args) {
        var input = new Random()
                .ints(25, -10, 25)
                .boxed()
                .collect(Collectors.toList());

        var slider = new SlidingWindow(input, 3);
        System.out.format("Input:%s\nSlider:%s\n", input, slider.runThrough());
    }

    public SlidingWindow runThrough() {
        postConstruct();
        forEachRemaining(obj -> System.out.println(this));
        return this;
    }

    public Integer runningSum() {
        return que.stream().reduce(Integer::sum).orElse(Integer.MIN_VALUE);
    }

    public boolean hasNext() {
        return end < objects.size();
    }

    public Deque<Integer> next() {
        if (start == 0) {
            que.addAll(objects.subList(start, end));
        } else {
            que.removeFirst();
            que.addLast(objects.get(end));
        }

        runningSum = runningSum();
        if (runningSum > maxSum) {
            maxSum = runningSum;
            maxStart = start;
            maxEnd = end;
        }

        start++;
        end++;
        return que;
    }
}
