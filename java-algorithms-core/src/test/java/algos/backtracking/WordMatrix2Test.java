package algos.backtracking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class WordMatrix2Test {

    @Test
    public void testWordMatrix2() {
        char[][] matrix = {
                {'t', 'z', 'x', 'c', 'd'},
                {'a', 'h', 'n', 'z', 'x'},
                {'h', 'w', 'o', 'i', 'o'},
                {'o', 'r', 'n', 'r', 'n'},
                {'a', 'b', 'r', 'i', 'n'}};

        int[][] expected = {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 5, 0},
                {0, 0, 2, 4, 6},
                {0, 0, 0, 3, 7},
                {0, 0, 0, 0, 0}};
        WordMatrix2 w = new WordMatrix2(matrix, "horizon");
        if (w.search()) {
            log.info("YES PATH FOUND");
        } else {
            log.info("NO PATH FOUND");
        }
        w.print();
        Assert.assertArrayEquals(expected, w.getSolution());
    }
}
