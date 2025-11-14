package algos.backtracking;

import java.util.ArrayList;

public class EvaluateAllExpressions {

    static ArrayList<String> res = new ArrayList<>();
    static String input;
    static int target;

    static void buildExpression(StringBuilder slate, int pos, long curVal, long last) {
        if (pos >= input.length()) {
            if (curVal == target) res.add(slate.toString());
            return;
        }
        for (int i = pos; i < input.length(); i++) {
            String part = input.substring(pos, i+1);
            long cur = Long.parseLong(part);
            int currentSlateLength = slate.length();
            if (pos == 0) {
                buildExpression(slate.append(part), i+1,
                        cur, cur);
                slate.setLength(currentSlateLength);
            } else {
                buildExpression(slate.append("+").append(part), i+1,
                        curVal + cur, cur);
                slate.setLength(currentSlateLength);
                buildExpression(slate.append("*").append(part), i+1,
                        (curVal - last) + (last * cur), last * cur);
                slate.setLength(currentSlateLength);
            }
        }
    }

    // method to print result
    static void printResult(ArrayList<String> res) {
        System.out.print("[");
        for (int i = 0; i < res.size(); i++)
            System.out.print(res.get(i) + " ");
        System.out.println("]");
    }

    // Driver code to test above methods
    public static void main(String[] args) {
        input = "202";
        target = 4;
        res.clear();
        buildExpression(new StringBuilder(), 0, 0, 0);
        printResult(res);

        input = "6666666666";
        target = 6;
        res.clear();
        buildExpression(new StringBuilder(), 0, 0, 0);
        printResult(res);
    }

}
