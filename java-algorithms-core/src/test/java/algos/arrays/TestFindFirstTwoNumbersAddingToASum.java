package algos.arrays;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

@Slf4j
public class TestFindFirstTwoNumbersAddingToASum {

    @Test
    public void test() {
        int[] input = {3, 4, 5, 9};
        final FindFirstTwoNumbersAddingToASum test = new  FindFirstTwoNumbersAddingToASum(input);
        log.info("Pair:{}", test.findPair(9));

        Assert.assertEquals(Pair.of(2,1), test.findPair(9).get());
        Assert.assertEquals(Pair.of(3,1), test.findPair(13).get());
        Assert.assertEquals(Optional.empty(), test.findPair(4));
        Assert.assertEquals(Optional.empty(), test.findPair(100));

    }
}
