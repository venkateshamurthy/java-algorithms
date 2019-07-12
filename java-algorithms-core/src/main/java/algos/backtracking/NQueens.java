package algos.backtracking;

import com.google.common.collect.Range;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class NQueens implements BackTracker<NQueens.Position> {

    private final int queens;
    @Getter private final int[][] chessBoard;
    private final Range<Integer> validRange;
    private final boolean[] rows;
    private final boolean[] cols;
    // Sum and Diff diagonals
    private final Map<Integer, Boolean> sumDiagonal = new HashMap<>();
    private final Map<Integer, Boolean> diffDiagonal = new HashMap<>();

    NQueens(int queenCount){
        queens= queenCount;
        chessBoard = new int[queens][queens];
        validRange = Range.closed(0, queens);
        rows = new boolean[queens];
        cols = new boolean[queens];
    }

    void print() {
        final StringBuilder sb = new StringBuilder("Data Matrix\n");

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                sb.append(" ").append(chessBoard[i][j]);
            }
            sb.append("\n");
        }

        log.info("{}", sb);
    }

    public boolean search() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                if (search(new Position(i, j, 0))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void markSolution(final Position p) {
        chessBoard[p.x][p.y] = p.index + 1;
        rows[p.x] = cols[p.y] = true;
        sumDiagonal.put(p.x + p.y, true);
        diffDiagonal.put(p.x - p.y, true);
    }

    @Override
    public void unmarkSolution(final Position p) {
        chessBoard[p.x][p.y] = 0;
        rows[p.x] = cols[p.y] = false;
        sumDiagonal.put(p.x + p.y, false);
        diffDiagonal.put(p.x - p.y, false);
    }

    @Override
    public boolean isStateGoal(final Position p) {
        return p.index == queens - 1;
    }

    @Override
    public boolean isStateValid(final Position p) {
        return p.index < queens &&
                validRange.contains(p.x) && validRange.contains(p.y) &&
                !rows[p.x] && !cols[p.y] &&
                !sumDiagonal.getOrDefault(p.x + p.y, false) &&
                !diffDiagonal.getOrDefault(p.x - p.y, false);
    }

    @Override
    public Stream<Position> nextPossibleStates(Position p) {
        return IntStream.range(0, queens).boxed()
                .filter(r->r!=p.x)
                .map(r -> Position.of(r, 1 + p.y, p.index + 1));
    }

    /**
     * <pre>
     * 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 7 0
     * 0 0 0 0 5 0 0 0
     * 0 0 0 0 0 0 0 8
     * 0 2 0 0 0 0 0 0
     * 0 0 0 4 0 0 0 0
     * 0 0 0 0 0 6 0 0
     * 0 0 3 0 0 0 0 0
     * </pre>
     */
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    @RequiredArgsConstructor(staticName = "of")
    static final class Position {
        int x, y;
        int index;
    }

    public static void main(String[] args) {
        final NQueens nQueens = new NQueens(8);

        if (nQueens.search()) {
            log.info("YES QUEENS PLACED");
        } else {
            log.info("NO QUEENS FOUND");
        }

        nQueens.print();
    }

    // From rosetta website
    static class NQueensRosetta {

        private static int[] b = new int[8];
        private static int s = 0;

        static boolean unsafe(int y) {
            return IntStream.rangeClosed(1, y).boxed()
                    .anyMatch(i -> b[y - i] - b[y] == 0 || Math.abs(b[y - i] - b[y]) == i);
        }

        public static void putboard() {
            System.out.println("\n\nSolution " + (++s));
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    System.out.print((b[y] == x) ? "|Q" : "|_");
                }
                System.out.println("|");
            }
        }

        public static void main(String[] args) {
            int y = 0;
            b[0] = -1;
            while (y >= 0) {
                do {
                    b[y]++;
                } while (b[y] < 8 && unsafe(y));
                if (b[y] < 8) {
                    if (y < 7) {
                        b[++y] = -1;
                    } else {
                        putboard();
                    }
                } else {
                    y--;
                }
            }
        }
    }
}
