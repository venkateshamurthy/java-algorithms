package algos.patterns.recursion;

import java.util.*;
import java.util.stream.IntStream;

public class TowersOfHanoi {
/**
 * [
 * [1, 3], [1, 2], [3, 2], [1, 3], [2, 1], [2, 3],
 * [1, 3], [1, 2], [3, 2], [3, 1], [2, 1], [3, 2],
 * [1, 3], [1, 2], [3, 2]
 * ]
 */
/**
 * [1, 3], [1, 2], [3, 2], [1, 3], [2, 1], [2, 3],
 * [1, 3], [1, 2], [3, 2], [3, 1], [2, 1], [3, 2],
 * [1, 3], [1, 2], [3, 2]
 * ]
 * @param args
 */

public static void main(String[] ra){
    System.out.println(tower_of_hanoi(4));
}
static ArrayList<ArrayList<Integer>> result = new ArrayList<>();
static ArrayList<ArrayList<Integer>> tower_of_hanoi(Integer n) {
    // Write your code here.
    Lst<Integer> in = new Lst<>(1), out =  new Lst<>(3),  aux = new Lst<>(2);
    IntStream.rangeClosed(1, n)
            .boxed()
            .sorted()
            .forEach(in::add);
    move(n, in, aux, out);
    return result;
}

    static void move(int N, Lst<Integer> in, Lst<Integer> aux, Lst<Integer> out) {
        if (N <= 1) {
            out.add(0,in.remove(0));
            result.add(new ArrayList(Arrays.asList(in.i, out.i)));
        } else {
            move(N - 1, in, out, aux);
            out.add(0, in.remove(0));
            result.add(new ArrayList(Arrays.asList(in.i, out.i)));
            move(N - 1, aux, in, out);
        }
    }

    static class Lst<T> extends ArrayList<T>{
        final Integer i;
        Lst(int i){super();this.i = i;}

    }

}
