package algos.patterns.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Sudoku {
    static ArrayList<ArrayList<Integer>> board = new ArrayList<>();

    public static void main(String[] args) {

        board.add(new ArrayList<>(Arrays.asList(0, 7, 0, 0, 0, 0, 0, 0, 9 )));
        board.add(new ArrayList<>(Arrays.asList(5, 1, 0, 4, 2, 0, 6, 0, 0 )));
        board.add(new ArrayList<>(Arrays.asList(0, 8, 0, 3, 0, 0, 7, 0, 0)));
        board.add(new ArrayList<>(Arrays.asList(0, 0, 8, 0, 0, 1, 3, 7, 0)));
        board.add(new ArrayList<>(Arrays.asList(0, 2, 3, 0, 8, 0, 0, 4, 0)));
        board.add(new ArrayList<>(Arrays.asList(4, 0, 0, 9, 0, 0, 1, 0, 0)));
        board.add(new ArrayList<>(Arrays.asList(9, 6, 2, 8, 0, 0, 0, 3, 0)));
        board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 0, 4, 0, 0)));
        board.add(new ArrayList<>(Arrays.asList(7, 0, 0, 2, 0, 3, 0, 9, 6)));

        sudoku(0, 0 );
        display(board);

    }

    static void display(ArrayList<ArrayList<Integer>> listBoard) {
        System.out.println("LIST_OF_BOARD:");
        for (ArrayList<Integer> arr : listBoard) {
            System.out.println(arr);
        }
    }

    static boolean isSafe(final int row, final int col, final int i) {
        //check row
        for (int a = 0; a < board.size(); a++) {
            if (board.get(a).get(col) == i) {
                return false;
            }
        }
        //check col
        for (int b = 0; b < board.size(); b++) {
            if (board.get(row).get(b) == i) {
                return false;
            }
        }
        //check cell
        int strow = row - (row % 3);
        int stcol = col - (col % 3);

        for (int x = strow; x < strow + 3; x++) {
            for (int y = stcol; y < stcol + 3; y++) {
                if (board.get(x).get(y) == i) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean sudoku(int row, int col) {
        if (row == 8 && col >= 9) return true;
        if (col >= 9) {row++; col = 0;}
        if (board.get(row).get(col) != 0) return sudoku(row, col + 1);

        for (int i = 0; i <= 9; i++) {
            if (isSafe(row, col, i)) {
                board.get(row).set(col, i);
                if (sudoku(row, col + 1)) return true;
            }
            board.get(row).set(col, 0);
        }
        return false;
    }
}
