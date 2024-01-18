package algos.stacks;

import com.google.common.base.Preconditions;
import lombok.NonNull;

import java.math.BigInteger;
import java.util.*;
import java.lang.*;

public class StackedNumberProblem {
    /**
     * UUID to Base62 encoded string
     * @param uuid that takes a string form such as b9e052ef-a53b-4e77-a8a0-d58e9ec3a164
     * @return a base62 encoded string
     */
    public static String uuidToBase62(@NonNull final UUID uuid) {
        final String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final BigInteger base62 = BigInteger.valueOf(chars.length());
        // Maximum string length of base62 expected is of 22 characters; hence initializing with capacity
        final StringBuilder sb = new StringBuilder(22);
        // Get the bg integer form of the UUID passed
        var biUid = new BigInteger(uuid.toString().replace("-", ""), 16);
        // Get the  encoding after successive division by base62
        while (biUid.compareTo(base62) >= 0) {
            int index = biUid.mod(base62).intValue();
            sb.append(chars.charAt(index));
            biUid = biUid.divide(base62);
        }
        sb.append(chars.charAt(biUid.intValue()));
        return sb.reverse().toString();
    }

    /**
     * @param String[] ops - List of operations
     * @return int - Sum of scores after performing all operations
     */
    public static int calPoints(String[] ops) {
        int result = Integer.MIN_VALUE;
        Set<String> operators = Set.of("C", "D", "+");
        Deque<Integer> stack = new ArrayDeque<>();
        for (String s : ops) {
            try {
                stack.push(Integer.parseInt(s));
            } catch (NumberFormatException nfe) {
                if (!stack.isEmpty() && operators.contains(s)) {
                    if (s.equals("C")) {
                        stack.pop();
                    } else if (s.equals("D")) {
                        stack.push(2 * stack.peek());
                    } else if (stack.size() >= 2 && s.equals("+")) {
                        int sum = stack.stream().limit(2).reduce(0, Integer::sum);
                        stack.push(sum);
                    }
                }
            }
        }
        result = stack.stream().peek(i -> System.out.print(i + " ")).reduce(0, Integer::sum);
        System.out.println();
        return result;
    }


    public static void main(String args[]) {
        System.out.println(calPoints("5,-2,4,C,D,9,+,+".split(",")));
        System.out.println(uuidToBase62(UUID.fromString("00000000-0000-0000-0000-000000000000")));
        System.out.println(uuidToBase62(UUID.fromString("00000000-0000-0000-0000-00000000003e")));
        System.out.println(uuidToBase62(UUID.fromString("00000000-0000-0000-0000-00000000007d")));
        System.out.println(uuidToBase62(UUID.fromString("b9e052ef-a53b-4e77-a8a0-d58e9ec3a164")));
        System.out.println(uuidToBase62(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff")));
    }
}
