package algos.backtracking;

import com.google.common.collect.Range;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class NQueens implements BackTracker<NQueens.Position> {

    private final int queens;

    private final int[][] chessBoard = new int[queens][queens];

    private final Range<Integer> validRange = Range.closedOpen(0, queens);
    private final boolean[] rows = new boolean[queens];
    private final boolean[] cols = new boolean[queens];
    // Sum and Diff diagonals
    private final Map<Integer, Boolean> sumDiagonal = new HashMap<>();
    private final Map<Integer, Boolean> diffDiagonal = new HashMap<>();

    private void print() {
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
}
