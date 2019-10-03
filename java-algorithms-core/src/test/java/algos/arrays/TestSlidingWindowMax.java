package algos.arrays;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

@Slf4j
public class TestSlidingWindowMax {

    @Test
    public void testSlidingWindowMax(){
        final int[] arr = {1, 3, 2, 6, 5, 4, 1, 7, 8};
        final Map<Integer, Integer> result = SlidingWindowMax.solveEfficient(arr, 4);
        log.info("SldingWindowMax:{}", result);
    }
}
