package algos.patterns.combinations;

import java.util.ArrayList;
import java.util.List;

public class PermutateOnLetterCase {

    public static void main(String[] ags) {
        System.out.println(permutateOnLetterCase("a1b2c3"));
        System.out.println(get_binary_strings(4));
    }
    static List<String> permutateOnLetterCase(String S) {
        List<String> ans = new ArrayList<>();
        permutateOnLetterCase(ans, S, 0, new StringBuilder());
        return ans;
    }

    /**
     *
     * @param ans overall answers list
     * @param in the string input
     * @param i the ith Character of the string being in question (for case changes)
     * @param slate is the intermediate solution held in a slate
     */
    static void permutateOnLetterCase(List<String> ans, String in, int i, StringBuilder slate) {
        if (i == in.length()) { //if (slate.length() == in.length())
            ans.add(slate.toString());
        } else {
            char ch = in.charAt(i);
            if (Character.isDigit(ch)) {
                permutateOnLetterCase(ans, in, i+1, slate.append(ch));
                slate.deleteCharAt(slate.length() - 1); // pop the last character
            } else {
                permutateOnLetterCase(ans, in, i+1, slate.append(Character.toLowerCase(ch)));
                slate.deleteCharAt(slate.length() - 1); //pop the last character

                permutateOnLetterCase(ans, in, i+1, slate.append(Character.toUpperCase(ch)));
                slate.deleteCharAt(slate.length() - 1); //pop the last character
            }
        }
    }

    static ArrayList<String> get_binary_strings(Integer n) {
        // Write your code here.
        ArrayList<String> result =  new ArrayList<>();
        getBinaryString(result, n, new StringBuilder());
        return result;
    }

    static void getBinaryString(ArrayList<String> ans, int n, StringBuilder slate) {
        if (n == 0) { //if (slate.length() == in.length())
            ans.add(slate.toString());
        } else {
            getBinaryString(ans, n - 1, slate.append('0'));
            slate.deleteCharAt(slate.length() - 1);

            getBinaryString(ans, n - 1, slate.append('1'));
            slate.deleteCharAt(slate.length() - 1);
        }
    }

}
