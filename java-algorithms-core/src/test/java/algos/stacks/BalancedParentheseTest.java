package algos.stacks;
import org.junit.Assert;
import org.junit.Test;

public class BalancedParentheseTest {
    @Test
    public void test(){
        BalancedParenthese instance = new BalancedParenthese();
        Assert.assertTrue("Failing on : ((((()))))",  instance.isBalanced("((((()))))"));
        Assert.assertTrue("Failing on : (((()())))",  instance.isBalanced("(((()())))"));
        Assert.assertFalse("Failing on : ()()()()()(",instance.isBalanced("()()()()()("));
    }
}
