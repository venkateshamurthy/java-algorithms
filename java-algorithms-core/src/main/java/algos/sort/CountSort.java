package algos.sort;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountSort {

    public static void main(String[] args) {
        CountSort cs = new CountSort();
        List<Integer> list = Lists.newArrayList(400000,0,-400000,0,-390000);
                //5, 8, 3, 3,9, 4, 1, 7,1,7);
        //(ArrayList)IntStream.generate(RandomUtils::nextInt).limit(100).boxed().collect(Collectors.toList());
            System.out.println(cs.count_sort(list) +"\n");
    }

    List<Integer> count_sort(List<Integer> a) {
        int max = a.stream().max(Integer::compareTo).orElseThrow();
        int min = a.stream().min(Integer::compareTo).orElseThrow();
        Map<Integer, Integer> c = new HashMap<>(a.size());
        for (int val : a)
            c.put(val, c.getOrDefault(val, 0) + 1);

        Integer count;
        for (int j = min, k = 0; j <= max; j++) {
            if ((count = c.get(j)) != null) {
                for(int x = 0; x < count; x++)
                    a.set(k++, j);
            }
        }
        return  a;
    }
}
