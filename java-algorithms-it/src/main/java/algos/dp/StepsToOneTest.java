package algos.dp;
import algos.dp.BottomUpUsingUnaryOperator;
import algos.dp.StepsToOneStrategy;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class StepsToOneTest {
    private final StepsToOneStrategy stepsToOne;

    public StepsToOneTest() {
        stepsToOne = new BottomUpUsingUnaryOperator();
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    public void performanceTest(Blackhole bh) {
        int res = stepsToOne.getMinStepsToOne(1_000_000);
        //System.out.format("time taken:%s, steps:%s\n", t2 / 1_000_000, res);
        bh.consume(res);
    }

    void display(int number) {
        System.out.format("%s->%s\n", stepsToOne.getClass().getSimpleName(),
                           stepsToOne.getMinStepsToOne(number)
        );
    }
}
