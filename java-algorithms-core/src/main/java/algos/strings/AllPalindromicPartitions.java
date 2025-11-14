package algos.strings;

import java.util.ArrayList;

public class AllPalindromicPartitions {
    static boolean isPalindrome(String input, int begin, int end) {
        while (begin < end) {
            if (input.charAt(begin) != input.charAt(end))
                return false;
            begin++;
            end--;
        }
        return true;
    }

    static void storePalindrome(String input, ArrayList<String> list, StringBuilder slate, int begin, int end) {
        if (begin == end) {
            System.out.println(slate);
            list.add(slate.toString());
            return;
        }
        int n = input.length();
        String delimiter = "|";
        for (int i = begin; i < end; i++) {
            if (isPalindrome(input, begin, i)) {
                if (i+1 == n) {
                    delimiter = "";
                }
                int length = slate.length();
                storePalindrome(input, list, slate.append(input, begin, i + 1).append(delimiter), i + 1, end);
                slate.delete(length, slate.length());
            }
        }
    }

    public static void main(String[] args) {
        String input = "abcb";
        StringBuilder slate = new StringBuilder();
        int begin = 0;
        int end = input.length();
        ArrayList<String> list = new ArrayList<>();
        storePalindrome(input, list, slate, begin, end);
        System.out.println("list:"+list);
    }
}