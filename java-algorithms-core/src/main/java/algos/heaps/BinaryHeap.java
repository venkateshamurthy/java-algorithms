package algos.heaps;

import java.util.*;

public class BinaryHeap<T> {
    final Comparator<T> comparator;
    final List<T> a;
    final Map<T, Integer> map = new HashMap<>();

    int boundedSize = Integer.MAX_VALUE;

    public BinaryHeap(final List<T> numbers, final Comparator<T> comparator) {
        this.comparator = comparator;
        this.a = numbers;
        arrangeHeap();
    }

    public BinaryHeap(final Comparator<T> comparator) {
        this(new ArrayList<>(), comparator);
    }
    public BinaryHeap(final Comparator<T> comparator, int k) {
        this(new ArrayList<>(), comparator);
        bound(k);
    }

    public BinaryHeap<T> bound(int k) {
        boundedSize = k;
        while (a.size() > k)
            poll();
        return this;
    }

    public void add(T... ts) {
        add(Arrays.asList(ts));
    }

    public T peek(){
        return a.get(0);
    }

    public T poll() {
        if (a.isEmpty())
            return null;
        else {
            T val = a.get(0);
            map.remove(val);
            map.put(a.get(a.size() - 1), 0);
            a.set(0, a.get(a.size() - 1) );
            a.remove(a.size() - 1);
            heapifyDown(0);
            return val;
        }
    }

    public int indexOf(T element) {
        return map.getOrDefault(element, -1);
    }

    public int changeKey(int index, T changedPriorityElement) {
        int comparisonResult = comparator.compare(changedPriorityElement, a.get(index));
        map.remove(a.get(index));
        a.set(index, changedPriorityElement);
        map.put(a.get(index), index);
        return comparisonResult == 0
                ? index
                : (comparisonResult < 0 ? heapifyUp(index) : heapifyDown(index));
    }

    public void add(Collection<T> ts) {
        ts.forEach(this::offer);
    }

    public boolean offer(T t){
        boolean result = a.add(t);
        heapifyUp(a.size() - 1);
        while (boundedSize < a.size())
            poll();
        return result;
    }

    private void arrangeHeap() {
        for (int i = (a.size() - 1) >> 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private boolean compare(int current, int other) {
        return current >= 0 && current < a.size() && other >= 0 && other < a.size()
                && comparator.compare(a.get(current), a.get(other)) == -1;
    }

    public int heapifyUp(int location) {
        while (compare(location, location >> 1)) {
            map.put(a.get(location), location >> 1);
            map.put(a.get(location >> 1), location);
            Collections.swap(a, location, location >> 1);
            location >>= 1;
        }
        return location;
    }

    public int heapifyDown(int location) {
        return heapifyDown(location, a.size() - 1);
    }

    public int heapifyDown(int location, int limit) {
        int left = (location << 1) + 1; // index of node i's left child
        int right = (location << 1) + 2; // index of node i's right child
        int extreme = location; // Find out which is real extreme(i.e min/max)
        if (left <= limit && compare(left, location))
            extreme = left;

        if (right <= limit && compare(right, extreme))
            extreme = right;

        if (extreme != location) {
            map.put(a.get(location), extreme);
            map.put(a.get(extreme), location);
            Collections.swap(a, location, extreme);
            heapifyDown(extreme, limit);
        }
        return extreme;
    }

    public boolean isEmpty() {
        return a.isEmpty();
    }
}