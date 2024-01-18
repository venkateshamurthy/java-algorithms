package algos.katemats;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


@RequiredArgsConstructor
public class MatrixSpiralPrint {
    static List<Pair<Integer,Integer>> cells = new ArrayList<>();
    static int[][] matrix = new int [9][7];

    static int M = matrix.length;
    static int N = matrix[0].length;

    public static void main(String[] args){
        /*
        System.out.println("Input:");
        print();
        int ms=0, me = M-1, ns=0, ne = N-1;
        int k=1;
        while (ms <= me && ns <= ne) {
            for(int i=ms,   j=ns;   j<=ne && matrix[i][j]==0; j++) matrix[i][j]=k++;
            for(int i=ms+1, j=ne;   i<=me && matrix[i][j]==0; i++) matrix[i][j]=k++;
            for(int i=me,   j=ne-1; j>=ns && matrix[i][j]==0; j--) matrix[i][j]=k++;
            for(int i=me-1, j=ns;   i> ms && matrix[i][j]==0; i--) matrix[i][j]=k++;
            ms++;me--;ns++;ne--;
            //System.out.println();
            //print();
        }
        System.out.println("\nOutput:");
        print();
        */
        iter();
    }

    public static void iter(){
        BitSet bits = new BitSet(M*N);
        int ms=0, me = M-1, ns=0, ne = N-1;
        int k=1;

        while (ms <= me && ns <= ne) {
            for(int i=ms,   j=ns;   j<=ne && !bits.get(i*N+j); j++) {bits.set(i*N+j); matrix[i][j]=k++;cells.add(Pair.of(i,j));}
            for(int i=ms+1, j=ne;   i<=me && !bits.get(i*N+j); i++) {bits.set(i*N+j); matrix[i][j]=k++;cells.add(Pair.of(i,j));}
            for(int i=me,   j=ne-1; j>=ns && !bits.get(i*N+j); j--) {bits.set(i*N+j); matrix[i][j]=k++;cells.add(Pair.of(i,j));}
            for(int i=me-1, j=ns;   i> ms && !bits.get(i*N+j); i--) {bits.set(i*N+j); matrix[i][j]=k++;cells.add(Pair.of(i,j));}
            ms++;me--;ns++;ne--;
        }
        print();
    }


    public static void print(){
        for(int i=0; i < M; i++) {
            System.out.println();
            for (int j = 0; j < N; j++)
                System.out.format(" %02d",matrix[i][j]);
        }
        System.out.println(cells);
    }
}

/**
 * {
 *  {1,1,1,1,1,1,1},
 *  {1,2,2,2,2,2,1},
 *  {1,2,3,3,3,2,1},
 *  {1,2,3,4,3,2,1},
 *  {1,2,3,4,3,2,1},
 *  {1,2,3,4,3,2,1},
 *  {1,2,3,3,3,2,1},
 *  {1,2,2,2,2,2,1},
 *  {1,1,1,1,1,1,1}
 *  }
 */