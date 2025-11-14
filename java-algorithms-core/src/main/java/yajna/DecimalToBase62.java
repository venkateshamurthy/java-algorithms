package yajna;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class DecimalToBase62 {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String decimalToBase62(BigInteger decimal) {
        Deque<Character> result = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        StringJoiner sj = new StringJoiner("");
        while (decimal.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = decimal.divideAndRemainder(base);
            Character chara = CHARACTERS.charAt(divRem[1].intValue());
            //System.out.println();
            //result.insert(0, CHARACTERS.charAt(divRem[1].intValue()));
            result.push(chara);
            decimal = divRem[0];
        }

        result.forEach(sb::append);
        return sb.toString();
    }

    public static void main(String[] args) {
        BigInteger decimal = new BigInteger("1234567890");
        String base62 = decimalToBase62(decimal);
        System.out.println(base62);
    }
}