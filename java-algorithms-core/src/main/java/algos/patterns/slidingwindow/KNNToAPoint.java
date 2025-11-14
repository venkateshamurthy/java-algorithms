package algos.patterns.slidingwindow;

import com.google.common.collect.Lists;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class KNNToAPoint {

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add (Lists.newArrayList(-1000000000, -1000000000));
        result.add (Lists.newArrayList(0, 0));
        result.add (Lists.newArrayList(-1000000000, 1000000000));
        result.add (Lists.newArrayList(1000000000, -1000000000));

        int p_x = 1000000000,p_y= 1000000000, k = 3;
        System.out.println(nearest_neighbours(p_x,p_y,k,result));

    }

    static ArrayList<ArrayList<Integer>> nearest_neighbours(Integer p_x, Integer p_y, Integer k, ArrayList<ArrayList<Integer>> n_points) {
        // Write your code here.
        Point p = new Point(p_x, p_y, 0d);
        List<Point> distancesToP = n_points.stream()
                .map(point -> new Point(point, p))
                .collect(Collectors.toList());
        System.out.println(distancesToP);
        quickSelect(Point::compareTo, distancesToP, 0, distancesToP.size() - 1, k);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        distancesToP.subList(0, k).stream()
                .peek(System.out::println)
                .map(q-> new ArrayList<>(Arrays.asList(q.x, q.y)))
                .forEach(result::add);
        return result;
    }

    @ToString
    static class Point implements Comparable<Point>{
        final int x, y;
        final double d;

        Point(int x, int y, double d) {
            this.x=x;
            this.y=y;
            this.d=d;
        }
        Point(int x, int y, Point p) {
            this.x = x;
            this.y = y;
            int delta_x = p.x - x;
            int delta_y = p.y - y;
            if ((p.x == x) && (p.y == y)) d = 0d;
            else d = Math.sqrt(  ((long) delta_x * delta_x) + ((long) delta_y * delta_y));
        }

        Point( ArrayList<Integer> q, Point p) {
            this(q.get(0), q.get(1), p);
        }

        @Override
        public int compareTo(Point q) {
            return Double.compare(d, q.d);
        }
    }

    static <T> T quickSelect(Comparator<T> comparator, List<T> numbers, int low, int high, int index) {
        if (low >= high)
            return numbers.get(low);
        int q = partition(comparator, numbers, low, high);
        // kth smallest means k-1; n-k means kth largest
        if (index == q)
            return numbers.get(q);
        else if (index < q)
            return quickSelect(comparator, numbers, low, q - 1, index);
        else
            return quickSelect(comparator, numbers, q + 1, high, index);
    }

    static Random random = new Random();
    static <T> int partition(Comparator<T> comparator, List<T> numbers, int start, int end) {
        int pivotIndex = start + random.nextInt(end - start);
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
