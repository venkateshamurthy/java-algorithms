package algos.sort;

import java.util.*;

public class OverlappingIntervals {
    public static void main(String[] args){
        ArrayList<Integer> evenOdd=new ArrayList<>();
        evenOdd.addAll(Arrays.asList(1,2,3,4,5));
        System.out.println(
                segregate_evens_and_odds(evenOdd)
        );
        ArrayList<ArrayList<Integer>> a= new ArrayList<>();
        a.add(new ArrayList<>());
        a.add(new ArrayList<>());
        a.add(new ArrayList<>());
        a.get(0).addAll(Arrays.asList(1,5));
        a.get(1).addAll(Arrays.asList(5,8));
        a.get(2).addAll(Arrays.asList(8,9, 10));
        System.out.println(can_attend_all_meetings(a));
    }
    static Integer can_attend_all_meetings(ArrayList<ArrayList<Integer>> intervals) {
        // Write your code here.
        intervals.sort(Comparator.comparingInt((l -> l.get(0))));
        int result= 1;
        for(int i=1; result!=0 && i<intervals.size(); i++) {
            result &= intervals.get(i).size() == 2 &&
                    (intervals.get(i-1).get(1) <= intervals.get(i).get(0))
                            ? 1 : 0;
        }
        return result;
    }

    static ArrayList<Integer> segregate_evens_and_odds(ArrayList<Integer> numbers) {
        System.out.println(numbers.subList(0,2));
        System.out.println(numbers.subList(2,4));
        // Write your code here.
        int left=0, right=numbers.size()-1;
        System.out.println(numbers.subList(left, 1+left+(right-left)/2));
        System.out.println(numbers.subList(1+left+(right-left)/2, right+1));
        while(left < right) {
            if(numbers.get(left) % 2 == 0) left++;
            else if(numbers.get(right) % 2 == 1) right--;
            else Collections.swap(numbers, left++, right--);
        }
        return numbers;
    }
}
