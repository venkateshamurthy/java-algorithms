package algos.sort;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RadixSort {
    static int base = 10, min, max;

    public static void radixSort(ArrayList<Integer> arr) {
        max = arr.stream().max(Integer::compareTo).orElseThrow();
        min = arr.stream().min(Integer::compareTo).orElseThrow();
        System.out.println("max = "+max+" , min ="+min+" log="+Math.log(max)/Math.log(base));
        for (long exp = 1; (max / exp) > 0; exp *= base) {
            countSort(arr,  (int) exp);
            System.out.println("SortedOrig:exp:"+exp+" arr:" + arr);
        }
    }

    private static ArrayList<Integer> countSort(ArrayList<Integer> arr, final int exp) {
        Map<Integer, List<Integer>> c = new TreeMap<>();
        for (int j : arr) {
            int k = (j / exp) % base;
            List<Integer> values = c.computeIfAbsent(k, ig-> new ArrayList<>());
            values.add(j);
        }

        int k = 0;
        for(int p : c.keySet()) {
            List<Integer> values = c.get(p);
            c.put(p, null);//not required as keys wont repeat in any type of map
            for (int value : values) {
                arr.set(k++, value);
            }
        }
        return arr;
    }
    public static void main(String[] args) {
        List<Integer> arr =
                //Lists.newArrayList(181, 51, 11, 33, 11, 39, 60, 2, 27, 24, 12);
        new Random().ints().limit(1000L).boxed().collect(Collectors.toList());
        System.out.println("Orig:" + arr);
        List<Integer> copy = new ArrayList<>(arr);
        copy.sort(Integer::compareTo);

        // Call Radix Sort on the array
        radixSort((ArrayList) arr);
        System.out.println("SortedOrig:" + arr);
        System.out.println(arr.equals(copy));
    }
}
