package algos.dp;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.IntConsumer;
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
@Slf4j
@RequiredArgsConstructor
public class StepsToOne {
    private final StepsToOneStrategy stepsToOne;

    public StepsToOne() {
        this(new BottomUpUsingUnaryOperator());
    }


    void display(int number) {
        System.out.format("%s->%s\n", stepsToOne.getClass().getSimpleName(),
                           stepsToOne.getMinStepsToOne(number)
        );
    }

    public static void main(String[] args) {
        int number=1000_000;
        val bottomUpUsingUnaryOperator = new StepsToOne(new BottomUpUsingUnaryOperator());
        log.info("Starting...");
        new StepsToOne(new BottomUpIterative()).display(number);
        try {
            new StepsToOne(new TopDownRecursive()).display(number);
        } catch (StackOverflowError e) {
            log.error("TopDownRecursive - stack over flow");
        }
        bottomUpUsingUnaryOperator.display(number);
    }
}

interface StepsToOneStrategy {
    int getMinStepsToOne(final int n);
}

class BottomUpUsingUnaryOperator implements StepsToOneStrategy {
    private interface ConditionalUnaryOperator<T> extends UnaryOperator<T>, Predicate<T> {}

    private static class Decrementer implements ConditionalUnaryOperator<Integer> {
        public Integer apply(Integer i) {return i - 1;}
        public boolean test(Integer i)  {return true;}
        private final String name = "MinusBy1";
    }

    private static class Divider implements ConditionalUnaryOperator<Integer> {
        private final String name;
        private final int divideByN;

        Divider(String name, int divideByN) {
            this.name="DividerBy"+divideByN;
            this.divideByN=divideByN;
        }

        public Integer apply(Integer i) {return i / divideByN;}
        public boolean test(Integer i)  {return i % divideByN == 0;}

        static List<ConditionalUnaryOperator<Integer>> of(Integer... divisors) {
            return Stream.of(divisors).map(divideByN -> new Divider("DivideBy", divideByN)).collect(Collectors.toList());
        }
    }

    private final List<ConditionalUnaryOperator<Integer>> reducers =
            ImmutableList.<ConditionalUnaryOperator<Integer>>builder().add(new Decrementer())
                    .addAll(Divider.of(3, 2)).build();

    //top-down recursvie split
    public int getMinStepsToOne(final int n) {
        final ConcurrentMap<Integer, Integer> stepsMap = new ConcurrentHashMap<>(1000);

        stepsMap.put(0, 0);
        stepsMap.put(1, 0);
        stepsMap.put(2, 1);
        stepsMap.put(3, 1);

        IntConsumer reducing = number -> reducers.stream()
                .filter(reducer -> reducer.test(number))
                .map(reducer -> reducer.apply(number))
                .map(stepsMap::get).filter(Objects::nonNull)
                .min(Integer::compare)
                .ifPresent(result -> stepsMap.putIfAbsent(number, 1 + result));

        IntStream.rangeClosed(1, n)
                //.parallel() --> this is not working
                .forEach(reducing);

        return stepsMap.get(n);
    }
}

class BottomUpIterative implements StepsToOneStrategy {
    //Bottom up - iterative
    public int getMinStepsToOne(int n) {
        int steps[] = new int[n + 1], i = 0;
        IntUnaryOperator[] operation = new IntUnaryOperator[n+1];
        for (i = 2; n > 1 && i <= n; i++) {
            // 1 + Min( F (n-1), F(n/2) iff n%2==0, F(n/3) iff n%3==0)
            int iMinus1 = steps[i - 1];
            int iBy2 = i % 2 == 0 ? steps[i / 2] : Integer.MAX_VALUE;
            int iBy3 = i % 3 == 0 ? steps[i / 3] : Integer.MAX_VALUE;

            int minResult = IntStream.of(iMinus1, iBy2, iBy3).min().orElseThrow(IllegalStateException::new);

            steps[i] = 1 + minResult;

            if(minResult==iMinus1) {
                operation[i] = j->j-1;
            }

            if(minResult==iBy2) {
                operation[i] = j->j/2;
            }

            if(minResult==iBy3) {
                operation[i] = j->j/3;
            }
        }

        for (int k=n;k>1;) {
            //System.out.format("steps[%s]:%s\n",k,steps[k]);
            k = operation[k].applyAsInt(k);
        }
        return steps[n];
    }
}

class TopDownRecursive implements StepsToOneStrategy {

    //top-down recursvie split - however this will get to stackoverflow beyond few thousands
    int[] steps = null;
    public int getMinStepsToOne(int i) {
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
        int minSteps = getMinStepsToOne(i - 1);

        return steps[i - 1] = 1 + IntStream.of(
                minSteps,
                i % 2 == 0 ? getMinStepsToOne(i / 2) : minSteps,
                i % 3 == 0 ? getMinStepsToOne(i / 3) : minSteps)
                .min().orElseThrow(IllegalStateException::new);
    }
}