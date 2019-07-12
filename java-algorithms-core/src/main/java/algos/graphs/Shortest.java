package algos.graphs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

// Problem from: @murthyve
// Shortest way to reach 1, from a given number, given following operations
//  1. Subtract 1.
//  2. If even divide by 2.
//  3. If multiple of 3 divide by 3.
//
// Performance for 1,000,000
//  Took 2403ms
//   Size: 20
//   [1, 2, 4, 8, 24, 72, 216, 217, 434, 868, 1736,
//    5208, 15624, 15625, 31250, 62500, 125000, 250000,
//    500000, 1000000]
//
// Performance for 10,000,000
//  Took 33644ms
//   Size: 23
//   [1, 2, 6, 7, 14, 42, 126, 127, 381, 1143, 3429,
//    10287, 10288, 20576, 61728, 185184, 185185,
//    370370, 1111110, 1111111, 3333333, 9999999,
//    10000000]

/**
 * A graph representation of the popular dynamic programming question on {@link algos.dp.StepsToOne}.
 * @author Anita Vasu
 */
public class Shortest {

    static class Vertex<V> {
        private final V idx;
        private final int dist;

        Vertex(V idx, int dist) {
            this.idx = idx;
            this.dist = dist;
        }

        public String toString() {
            return idx + "[" + dist + "]";
        }
    }

    public static <V extends Comparable<V>>

    List<V> reduce(V a,
                   V target,
                   Function<V, Integer> lowerBound,
                   Function<V, V>... edges) {

        Map<V, Integer> distMap = new TreeMap<>();

        SortedMap<Vertex<V>, V> currentParent = new TreeMap<>(Comparator
                .<Vertex<V>>comparingInt(v -> v.dist + lowerBound.apply(v.idx))
                .thenComparing(v -> v.idx));

        Map<V, V> finalParent = new TreeMap<V, V>();

        distMap.put(a, 0);

        currentParent.put(new Vertex<V>(a, 0), null);

        while (!currentParent.isEmpty()) {
            Vertex<V> v = currentParent.firstKey();
            V vParent = currentParent.get(v);
            currentParent.remove(v);
            finalParent.put(v.idx, vParent);

            if (v.equals(target)) {
                break;
            }
            int newDist = 1 + distMap.get(v.idx);

            for (Function<V, V> e : edges) {
                V u = e.apply(v.idx);
                if (u == null) {
                    continue;
                }
                Integer oldDist = distMap.get(u);
                if (oldDist == null || newDist < oldDist) {
                    if (oldDist != null) {
                        currentParent.remove(new Vertex<V>(u, oldDist));
                    }
                    distMap.put(u, newDist);
                    currentParent.put(new Vertex<V>(u, newDist), v.idx);
                }
            }
        }

        List<V> solution = new ArrayList<>();
        V cursor = target;
        while (cursor != null) {
            solution.add(cursor);
            cursor = finalParent.get(cursor);
        }
        return solution;
    }

    private static final double LOG3 = Math.log(3);

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        Function<Long, Long> reduceOne = x -> x > 1 ? x - 1 : null;
        Function<Long, Long> byTwo = x -> x % 2 == 0 ? x / 2 : null;
        Function<Long, Long> byThree = x -> x % 3 == 0 ? x / 3 : null;

        Function<Long, Integer> lowerBound = x -> (int) Math.floor(Math.log(x) / LOG3);
        for(int j=0;j<10;j++) {
            long startNanos = System.nanoTime();
            List<Long> soln = reduce(1_000_000L, 1L, lowerBound,
                    reduceOne, byTwo, byThree);

            long timeMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);

            System.out.println("Took " + timeMillis + "ms");

            System.out.println("Size: " + soln.size());
            System.out.println(soln);
        }
    }
}