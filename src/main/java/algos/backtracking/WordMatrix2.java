package algos.backtracking;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Slf4j
public class WordMatrix2 implements BackTracker<WordMatrix2.WordState> {
    private final int[][] solution;
    private final AtomicInteger path = new AtomicInteger();
    private final char[][] matrix;
    private final String word;

    // initialize the solution matrix in constructor.
    private WordMatrix2(final char[][] matrix, final String word) {
        solution = new int[matrix.length][matrix[0].length];
        this.matrix = matrix;
        this.word = word;
    }

    private void print() {
        final StringBuilder sb = new StringBuilder("Data Matrix\n");
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution.length; j++) {
                sb.append(" ").append(solution[i][j]).append(matrix[i][j]);
            }
            sb.append("\n");
        }
        log.info("{}", sb);
    }

    public boolean search() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (search(new WordState(i, j, 0))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void markSolution(final WordState state) {
        solution[state.row][state.col] += path.incrementAndGet();
    }

    @Override
    public void unmarkSolution(final WordState state) {
        solution[state.row][state.col] += path.decrementAndGet();
    }

    @Override
    public boolean isStateGoal(final WordState state) {
        return state.index == word.length() - 1;
    }

    @Override
    public boolean isStateValid(final WordState state) {
        boolean validCell = state.row >= 0 && state.row < matrix.length && state.col >= 0 && state.col < matrix[0].length;
        boolean unsolvedCell = validCell && solution[state.row][state.col] == 0;
        boolean matched = validCell && state.index < word.length() && word.charAt(state.index) == matrix[state.row][state.col];
        return unsolvedCell && matched;
    }

    /**
     * <pre>
     * |-----|-----|-----|
     * |-1,-1|-1,0 |-1,1 |
     * |-----|-----|-----|
     * |0,-1 | 0,0 |0,1  |
     * |-----|-----|-----|
     * |1,-1 | 1,0 |1,1  |
     * |-----|-----|-----|
     * </pre>
     * @return Stream of next possible states
     */
    @Override
    public Stream<WordState> nextPossibleStates(final  WordState state) {
        int row = state.row;
        int col = state.col;
        int index = state.index;

        return Stream.of(
                new WordState(row + 0, col - 1, index + 1),
                new WordState(row - 1, col - 1, index + 1),
                new WordState(row - 1, col + 0, index + 1),
                new WordState(row - 1, col + 1, index + 1),
                new WordState(row + 0, col + 1, index + 1),
                new WordState(row + 1, col + 1, index + 1),
                new WordState(row + 1, col + 0, index + 1),
                new WordState(row + 1, col - 1, index + 1));
    }

    @RequiredArgsConstructor
    static class WordState {
        private final int row;
        private final int col;
        private final int index;
    }

    public static void main(String[] args) {
        char[][] matrix = {
                {'t', 'z', 'x', 'c', 'd'},
                {'a', 'h', 'n', 'z', 'x'},
                {'h', 'w', 'o', 'i', 'o'},
                {'o', 'r', 'n', 'r', 'n'},
                {'a', 'b', 'r', 'i', 'n'}};
        WordMatrix2 w = new WordMatrix2(matrix, "horizon");
        if (w.search()) {
            log.info("YES PATH FOUND");
        } else {
            log.info("NO PATH FOUND");
        }
        w.print();
    }

}