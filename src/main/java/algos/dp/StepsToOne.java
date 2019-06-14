package algos.dp;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * REf: https://www.codechef.com/wiki/tutorial-dynamic-programming
 * Problem : Minimum Steps to One Problem Statement: On a positive integer, you
 * can perform any one of the following 3 steps. 1.) Subtract 1 from it. ( n = n
 * - 1 ) , 2.) If its divisible by 2, divide by 2. ( if n % 2 == 0 , then n = n
 * / 2 ) , 3.) If its divisible by 3, divide by 3. ( if n % 3 == 0 , then n = n
 * / 3 ).
 * <p>
 * Now the question is, given a positive integer n, find the minimum
 * number of steps that takes n to 1 eg: 1.)For n = 1 , output: 0 2.) For n = 4
 * , output: 2 ( 4 /2 = 2 /2 = 1 ) 3.) For n = 7 , output: 3 ( 7 -1 = 6 /3 = 2
 * /2 = 1 )
 * <p>
 * Approach / Idea: One can think of greedily choosing the step, which
 * makes n as low as possible and conitnue the same, till it reaches 1. If you
 * observe carefully, the greedy strategy doesn't work here. Eg: Given n = 10 ,
 * Greedy --> 10 /2 = 5 -1 = 4 /2 = 2 /2 = 1 ( 4 steps ). But the optimal way is
 * --> 10 -1 = 9 /3 = 3 /3 = 1 ( 3 steps ).
 * <p>
 * So, we need to try out all possible
 * steps we can make for each possible value of n we encounter and choose the
 * minimum of these possibilities. It all starts with recursion :). F(n) = 1 +
 * min{ F(n-1) , F(n/2) , F(n/3) } if (n>1) , else 0 ( i.e., F(1) = 0 ) .
 * <p>
 * Now
 * that we have our recurrence equation, we can right way start coding the
 * recursion. Wait.., does it have over-lapping subproblems ? YES. Is the
 * optimal solution to a given input depends on the optimal solution of its
 * subproblems ? Yes... Bingo ! its DP :) So, we just store the solutions to the
 * subproblems we solve and use them later on, as in memoization.. or we start
 * from bottom and move up till the given n, as in dp. As its the very first
 * problem we are looking at here, lets see both the codes.
 *
 * @author murthyv
 */
public class StepsToOne {
    int[] steps = null;
    PriorityQueue<Integer> pq = new PriorityQueue<>();

    private static interface ConditionalUnaryOperator<T> extends UnaryOperator<T>, Predicate<T> {
        UnaryOperator<T> getReducer();

        Predicate<T> getPredicate();

        default T apply(final T t) {
            return getReducer().apply(t);
        }

        default boolean test(final T t) {
            return getPredicate().test(t);
        }
    }

    @Getter @NoArgsConstructor
    private static class Decrementer implements ConditionalUnaryOperator<Integer> {
        private final UnaryOperator<Integer> reducer = i -> i-1;
        private final Predicate<Integer> predicate = i->true;
    }

    @Getter @RequiredArgsConstructor
    private static class Divider implements ConditionalUnaryOperator<Integer> {
        private final UnaryOperator<Integer> reducer;
        private final Predicate<Integer> predicate;

        Divider(int divideByN) {
            this(integer -> integer / divideByN,
                    integer -> integer % divideByN == 0);
        }

        static List<ConditionalUnaryOperator<Integer>> of(Integer... divisors) {
            return Stream.of(divisors)
                    .map(Divider::new)
                    .collect(Collectors.toList());
        }
    }

    private final List<ConditionalUnaryOperator<Integer>> reducers =
            ImmutableList.<ConditionalUnaryOperator<Integer>>builder()
                    .add(new Decrementer())
                    .addAll(Divider.of(5, 3, 2))
                    .build();

    //top-down recursvie split
    int getMinStepsMemoizedWithFunctions(int n) {
        //Basically create an array and fill it with -1
        if (steps == null) {
            steps = new int[n + 1];
            Arrays.fill(steps, -1);
            steps[0] = steps[1] = 0; //except the first as 0.
        }

        pq.clear();
        for (int i = 2; n > 1 && i <= n; i++, pq.clear()) {
            final int num = i;
            reducers.stream()
                    .filter(r -> r.test(num))
                    .map(r -> r.apply(num))
                    .map(k -> steps[k])
                    //.min(Integer::compareTo) // ==> isnt this o(n) instead should we use heap
                    //.orElse(Integer.MAX_VALUE);// ==> just a default
                    .forEach(pq::add);//==> this is at worst o(log(n)) but on average less than logarithmic
            steps[i] = 1 + pq.poll(); //==> o(1)
        }
        return steps[n];

    }

    //top-down recursvie split - however this will get to stackoverflow beyond few thousands
    int getMinStepsMemoize(int i) {
        //Basically create an array and fill it with -1
        if (steps == null) {
            steps = new int[i];
            Arrays.fill(steps, -1);
            steps[0] = 0; //except the first as 0.
            steps[1] = 1;
            steps[2] = 1;
        }

        //if ith step is available then dont enter
        if (steps[i - 1] != -1)
            return steps[i - 1]; // ith step is done!

        // 1 + Min( F (n-1), F(n/2) iff n%2==0, F(n/3) iff n%3==0)
        int minSteps = getMinStepsMemoize(i - 1);

        return steps[i - 1] = 1 + IntStream.of(
                minSteps,
                i % 5 == 0 ? getMinStepsMemoize(i / 5) : minSteps,
                i % 2 == 0 ? getMinStepsMemoize(i / 2) : minSteps,
                i % 3 == 0 ? getMinStepsMemoize(i / 3) : minSteps)
                .min().orElse(Integer.MAX_VALUE);
    }

    //Bottom up - iterative
    int getMinSteps(int n) {
        int steps[] = new int[n + 1], i = 0;
        for (i = 2; n > 1 && i <= n; i++) {
            // 1 + Min( F (n-1), F(n/2) iff n%2==0, F(n/3) iff n%3==0)
            steps[i] = 1 + IntStream.of(
                    steps[i - 1],
                    i % 5 == 0 ? steps[i / 5] : steps[i - 1],
                    i % 2 == 0 ? steps[i / 2] : steps[i - 1],
                    i % 3 == 0 ? steps[i / 3] : steps[i - 1])
                    .min().orElse(Integer.MAX_VALUE);
        }
        return steps[n];
    }

    public static void main(String[] args) {
        int number = 15;
        System.out.println(
                "getMinStepsMemoizedWithFunctions->" + new StepsToOne().getMinStepsMemoizedWithFunctions(number)
        );
        try {
            System.out.println(
                    "getMinStepsMemoize->" + new StepsToOne().getMinStepsMemoize(number)
            );
        } catch (StackOverflowError e) {
        }
        System.out.println(
                "getMinSteps->" + new StepsToOne().getMinSteps(number)
        );
    }
}
