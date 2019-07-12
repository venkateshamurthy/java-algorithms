package algos.graphs;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.UnaryOperator;

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
 * Added a JMH benchmark for {@link Shortest}
 * @author murthyv
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
public class ShortestTest {
    private static final double LOG3 = Math.log(3);
    private static final UnaryOperator<Long> reduceOne = x -> x > 1 ? x - 1 : null;
    private static final UnaryOperator<Long> byTwo = x -> x % 2 == 0 ? x / 2 : null;
    private static final UnaryOperator<Long> byThree = x -> x % 3 == 0 ? x / 3 : null;
    private static final Function<Long, Integer> lowerBound = x -> (int) Math.floor(Math.log(x) / LOG3);

    @Benchmark
    public void performanceTest(Blackhole bh){
        final List<Long> soln = Shortest.reduce(1_000_000L, 1L, lowerBound,
                reduceOne, byTwo, byThree);
        bh.consume(soln);
    }
}
