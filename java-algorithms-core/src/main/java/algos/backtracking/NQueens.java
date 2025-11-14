package algos.backtracking;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NQueens {//implements BackTracker<NQueens.Position> {

    private final int queens;
    private final String[][] chessBoard;
    private final Range validRange;
    private final boolean[] rows;
    private final boolean[] cols;
    // Sum and Diff diagonals
    private final Map<Integer, Boolean> sumDiagonal = new HashMap<>();
    private final Map<Integer, Boolean> diffDiagonal = new HashMap<>();
    static Position MAX = null;


    NQueens(int queenCount){
        queens= queenCount;
        chessBoard = new String[queens][queens];
        validRange = new Range(0, queens);
        rows = new boolean[queens];
        cols = new boolean[queens];
        MAX = new Position(queens-1,queens-1, 0);
        clear();
    }

    List<String> getChessBoard() {

        List<String> result = new ArrayList<>();

        for (String[] strings : chessBoard) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < chessBoard[0].length; j++) {
                temp.append(strings[j]);
            }
            result.add(temp.toString());
        }
        return result;
    }

    void print() {
        getChessBoard().forEach(System.out::println);;
    }

    public List<String> search(int p, int q) {
        for (int i = p; i < chessBoard.length; i++) {
            for (int j = q; j < chessBoard[0].length; j++) {
                if (search(new Position(i, j, 0))) {
                    return getChessBoard();
                }
            }
        }
        return new ArrayList<>();
    }

    void clear() {
        for(int i=0;i<queens;i++) {
            rows[i] = cols[i] = false;
            for (int j = 0; j < queens; j++)
                chessBoard[i][j] = "-";
        }
        sumDiagonal.clear();
        diffDiagonal.clear();
    }

    public void markSolution(final Position p) {
        chessBoard[p.x][p.y] = "q";
        rows[p.x] = cols[p.y] = true;
        sumDiagonal.put(p.x + p.y, true);
        diffDiagonal.put(p.x - p.y, true);
    }

    public void unmarkSolution(final Position p) {
        chessBoard[p.x][p.y] = "-";
        rows[p.x] = cols[p.y] = false;
        sumDiagonal.put(p.x + p.y, false);
        diffDiagonal.put(p.x - p.y, false);
    }

    public boolean isStateGoal(final Position p) {
        return p.index == queens - 1;
    }

    public boolean isStateValid(final Position p) {
        return p.index < queens &&
                validRange.test(p.x) && validRange.test(p.y) &&
                !rows[p.x] && !cols[p.y] &&
                !sumDiagonal.getOrDefault(p.x + p.y, false) &&
                !diffDiagonal.getOrDefault(p.x - p.y, false);
    }

    public Stream<Position> nextPossibleStates(Position p) {
        return IntStream.range(0, queens).boxed()
                .filter(r->r != p.x)
                .map(r -> new Position(r, 1 + p.y, p.index + 1))
                .filter(Position::valid);
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

    static final class Position {
        int x, y;
        int index;
        String value="-";
        Position(int x, int y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }

        public boolean valid() {
            return x<= MAX.x && y<=MAX.y ;
        }
    }

    public boolean tryReachNextGoal(final Position state) {
        return nextPossibleStates(state).anyMatch(this::search);
    }
    public boolean search(final Position state) {
        if (isStateValid(state)) {
            markSolution(state);
            if (isStateGoal(state) || tryReachNextGoal(state)) {
                return true;
            }
            unmarkSolution(state);
        }
        return false;
    }

    public static void main(String[] args) {
        final NQueens nQueens = new NQueens(5);
        List<List<String>> result = nQueens.search();
        if(!result.isEmpty()) {
            System.out.println("YES QUEENS PLACED:\n"+result);
        } else {
            System.out.println("NO QUEENS FOUND");
        }

        //nQueens.print();
    }

    public List<List<String>> search() {
        List<List<String>> result= new ArrayList<>();
        for (int p = 0; p < queens; p++) {
            for (int q = 0; q < queens; q++) {
                clear();
                List<String> temp = search(p, q);
                if ( ! temp.isEmpty() && !result.contains(temp)) {
                    result.add(temp);
                    temp.forEach(System.out::println);
                }
            }
        }
        return result;
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

    static class Range implements Predicate<Integer> {
        int min, max;
        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
        @Override
        public boolean test(Integer i) {
            return i>=min && i<=max;
        }
    }
}
