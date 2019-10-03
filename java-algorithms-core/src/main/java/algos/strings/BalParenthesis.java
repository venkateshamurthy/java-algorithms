package algos.strings;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Stack;

class BalParenthesis {
    static String openers = "{([";
    static String closers = "})]";
    static Map<Character, Character> charMaps=
            ImmutableMap.of('(',')','[',']','{','}');
    // Function to check if given expression is balanced or not
    public static boolean balParenthesis(String exp) {
        Stack<Character> stack = new Stack();

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            if (StringUtils.contains(openers,ch)) {
                stack.push(ch);
            }

            if (StringUtils.contains(closers,ch)) {
                if (stack.empty() || ch != charMaps.get(stack.pop())) {
                    return false;
                }
            }
        }

        return stack.empty();
    }

    public static void main(String[] args) {
        String exp = "{()}[{}]";

        if (balParenthesis(exp)) {
            System.out.println("The expression is balanced");
        } else {
            System.out.println("The expression is not balanced");
        }
    }
}