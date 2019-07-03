package algos.backtracking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class NQueensTest {
    int[][] expected = {
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 7, 0},
            {0, 0, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 8},
            {0, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 6, 0, 0,},
            {0, 0, 3, 0, 0, 0, 0, 0}
    };

    @Test
    public void testEightQueens() {
        final NQueens nQueens = new NQueens(8);

        if (nQueens.search()) {
            log.info("YES QUEENS PLACED");
        } else {
            log.info("NO QUEENS FOUND");
        }
        nQueens.print();
        Assert.assertArrayEquals(expected, nQueens.getChessBoard());
    }
}
