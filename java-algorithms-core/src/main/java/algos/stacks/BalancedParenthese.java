package algos.stacks;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BalancedParenthese {
    private final Map<Character, Character> openClosers = ImmutableMap
            .of(')', '(', '}', '{', ']', '[');
    private final Set<Character> allOpenClosers = openClosers.entrySet().stream()
            .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
            .collect(Collectors.toSet());

    boolean isBalanced(String s) {
        final Deque<Character> stack = new ArrayDeque<>();

        final boolean allCharsProcessed = s.chars().boxed()
                .map(i->Character.valueOf((char)i.intValue()))
                //.peek(c->System.out.println("c:"+c+" stack:"+stack))
                .filter(allOpenClosers::contains)
                .allMatch(c ->
                        openClosers.containsKey(c) && openClosers.get(c)==stack.peek() ?
                        stack.poll() != null : stack.add(c));
        return allCharsProcessed && stack.isEmpty();

    }
}
