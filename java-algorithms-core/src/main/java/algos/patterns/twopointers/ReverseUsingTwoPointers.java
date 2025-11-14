package algos.patterns.twopointers;

import algos.utils.Utils;

public class ReverseUsingTwoPointers {
    static class Solution {
        public char[] reverseChars(char[] chars) {
            int left = 0, right = chars.length - 1;
            while (left < right) {
                Utils.swap(chars, left, right);
                left++; right--;
            }
            return chars;
        }

        public String reverse(String s) {
            char[] chars = s.toCharArray();
            return new String(reverseChars(chars));
        }
    }
}
