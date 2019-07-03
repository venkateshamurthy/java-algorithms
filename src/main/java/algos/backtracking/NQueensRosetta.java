package algos.backtracking;

import java.util.stream.IntStream;

public class NQueensRosetta {

    private static int[] b = new int[8];
    private static int s = 0;

    static boolean unsafe(int y) {
        return IntStream.rangeClosed(1,y).boxed()
                .anyMatch(i -> b[y-i]-b[y]==0||Math.abs(b[y-i]-b[y])==i);
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