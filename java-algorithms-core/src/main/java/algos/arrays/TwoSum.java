package algos.arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TwoSum {
    public static void main(String[] args) {
        int[] arr = {2, 7, 10, 15};
        Scanner in = new Scanner(System.in);
        int target = in.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                System.out.println(i + " " + map.get(target - arr[i]));
                return;
            }
            map.put(arr[i], i);
        }
        System.out.println("target sum is absent ");
    }
}
