package algos.backtracking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        if (! nQueens.search().isEmpty()) {
            log.info("YES QUEENS PLACED");
        } else {
            log.info("NO QUEENS FOUND");
        }
        nQueens.print();
        List<String> expectedList = Arrays.stream(expected)
                .map(Arrays::toString)
                .collect(Collectors.toList());
        Assert.assertEquals(expectedList, nQueens.getChessBoard());
    }
}
