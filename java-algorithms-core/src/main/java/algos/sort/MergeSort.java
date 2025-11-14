package algos.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MergeSort {

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(8, 5, 9, 3, 4, 1, 7, 10, 12, 13, 11));
        System.out.println(merge_sort(arr));
    }

    static ArrayList<Integer> merge_sort(ArrayList<Integer> arr) {
        // Write your code here.
        mergeSort(arr, 0, arr.size() - 1);
        return  arr;
    }

    static void mergeSort(ArrayList<Integer> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left)/2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    static void merge (ArrayList<Integer> arr, int left, int mid, int right) {
        List<Integer> lArr = new ArrayList<>(arr.subList(left, mid + 1));
        List<Integer> rArr = arr.subList(mid + 1, right + 1);

        System.out.println("LARR:"+lArr);
        System.out.println("RARR:"+rArr);

        int i=0, j=0, k=left;

        while (i<lArr.size() && j<rArr.size()) {
            arr.set(k++, lArr.get(i) <= rArr.get(j) ? lArr.get(i++) : rArr.get(j++));
        }

        while(i<lArr.size()) arr.set(k++, lArr.get(i++));
        while(j<rArr.size()) arr.set(k++, rArr.get(j++));
        System.out.println("ARR:"+arr);
    }
}
