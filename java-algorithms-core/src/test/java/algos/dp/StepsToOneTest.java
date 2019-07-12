package algos.dp;

import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class StepsToOneTest {
    private final StepsToOneStrategy stepsToOne;

    @Parameterized.Parameters()
    public static Collection input (){
        return Arrays.asList(new Object[][]{
                {new BottomUpUsingUnaryOperator()},
                {new BottomUpIterative()}
            });
    }

    @Test
    public void testStepsToOne() {
        Assert.assertEquals(19, stepsToOne.getMinStepsToOne(1_000_000));
    }

}
