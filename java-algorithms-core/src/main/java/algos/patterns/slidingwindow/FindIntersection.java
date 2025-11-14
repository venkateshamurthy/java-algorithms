package algos.patterns.slidingwindow;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindIntersection {

    public static void main(String[] args) {
        ArrayList<Integer> a3 = Lists.newArrayList(Arrays.asList(
                100000, 100001, 100002, 100003, 100004, 100005,
                100006, 100007, 100008, 100009, 100010, 100011,
                100012, 100013, 100014, 100015, 100016, 100017,
                100018, 100019, 100020, 100021, 100022, 100023,
                100024, 100025, 100026, 100027, 100028, 100029,
                100030, 100031, 100032, 100033, 100034, 100035,
                100036, 100037, 100038, 100039, 100040, 100041,
                100042, 100043, 100044));
        ArrayList<Integer> a2 = Lists.newArrayList(Arrays.asList(
                100000, 100001, 100002, 100003, 100004, 100005, 100006, 100007,
                100008, 100009, 100010, 100011, 100012, 100013, 100014, 100015,
                100016, 100017, 100018, 100019, 100020, 100021, 100022, 100023,
                100024, 100025, 100026, 100027, 100028, 100029, 100030, 100031,
                100032, 100033, 100034, 100035, 100036, 100037, 100038, 100039,
                100040, 100041, 100042, 100043, 100044, 100045, 100046, 100047));

        ArrayList<Integer> a1 = Lists.newArrayList(Arrays.asList(
                100000, 100001, 100002, 100003, 100004, 100005, 100006, 100007,
                100008, 100009, 100010, 100011, 100012, 100013, 100014, 100015,
                100016, 100017, 100018, 100019, 100020, 100021, 100022, 100023, 100024,
                100025, 100026, 100027, 100028, 100029, 100030, 100031, 100032, 100033,
                100034, 100035, 100036, 100037, 100038,
                100039, 100040, 100041, 100042, 100043, 100044, 100045, 100046, 100047));

        System.out.println(find_intersection(a1, a2, a3));
    }
    static ArrayList<Integer> find_intersection(ArrayList<Integer> arr1, ArrayList<Integer> arr2, ArrayList<Integer> arr3) {
        // Write your code here.
        ArrayList<Integer> result = new ArrayList<>();
        int i=0,j=0,k=0;

        while(i < arr1.size() && j < arr2.size() && k < arr3.size()) {
            if ((arr1.get(i).intValue() == arr2.get(j).intValue())  && (arr2.get(j).intValue() == arr3.get(k).intValue())) {
                result.add(arr1.get(i));
                i++;j++;k++;
            } else if (arr1.get(i) < arr2.get(j)) i++;
            else if (arr2.get(j) < arr3.get(k)) j++;
            else k++;
        }
        if (result.isEmpty()) result.add(-1);
        return result;
    }
}
