package algos.patterns.slidingwindow.twopinters;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ThreeWayPartition {

    public static void main(String[] args) {
        ArrayList<Character> arr = new ArrayList<>();
        "GBRBBRRRRGBGRBBGG".chars()
                .forEach((int c) ->arr.add((char) c));
        count_sort(arr,"RGB");
        System.out.println(arr);
    }

    public static void threeWayPartition(ArrayList<Integer> A) {
        int start = 0, mid = 0;
        int pivot = 1;
        int end = A.size() - 1;
        while (mid <= end) {
            if (A.get(mid) < pivot)         // if current element is 0
            {
                Collections.swap(A, start++, mid++);
            } else if (A.get(mid) > pivot)    // if current element is 2
            {
                Collections.swap(A, mid, end--);
            } else {                      // if current element is 1
                mid++;
            }
        }
    }

    public static void threeWayPartitionigForChar(ArrayList<Character> A) {
        int start = 0, mid = 0;
        int end = A.size() - 1;
        while (mid <= end) {
            switch(A.get(mid)) {
                case 'R':{Collections.swap(A, start++, mid++);break;}
                case 'G':{mid++; break;}
                case 'B':{Collections.swap(A, mid, end--);break;}
            }
        }
    }

    static List<Character> count_sort(List<Character> a, String ballString) {
        List<Character> colors = ballString.chars()
                .mapToObj(i->(char)i).collect(Collectors.toList());
        Map<Character, Integer> c = new HashMap<>(a.size());
        for (Character val : a) c.put(val, c.getOrDefault(val, 0) + 1);
        AtomicInteger k = new AtomicInteger(0);
        colors.forEach (j -> {
            Integer count;
            if ((count = c.get(j)) != null) {
                for(int x = 0; x < count; x++)
                    a.set(k.getAndIncrement(), j);
            }
        });
        return  a;
    }
}
