package algos.dp;

import java.util.Arrays;
import java.util.Collections;

/**
 * REf: https://www.codechef.com/wiki/tutorial-dynamic-programming
 * Problem : Minimum Steps to One Problem Statement: On a positive integer, you
 * can perform any one of the following 3 steps. 1.) Subtract 1 from it. ( n = n
 * - 1 ) , 2.) If its divisible by 2, divide by 2. ( if n % 2 == 0 , then n = n
 * / 2 ) , 3.) If its divisible by 3, divide by 3. ( if n % 3 == 0 , then n = n
 * / 3 ). 
 * 
 * Now the question is, given a positive integer n, find the minimum
 * number of steps that takes n to 1 eg: 1.)For n = 1 , output: 0 2.) For n = 4
 * , output: 2 ( 4 /2 = 2 /2 = 1 ) 3.) For n = 7 , output: 3 ( 7 -1 = 6 /3 = 2
 * /2 = 1 ) 
 * 
 * Approach / Idea: One can think of greedily choosing the step, which
 * makes n as low as possible and conitnue the same, till it reaches 1. If you
 * observe carefully, the greedy strategy doesn't work here. Eg: Given n = 10 ,
 * Greedy --> 10 /2 = 5 -1 = 4 /2 = 2 /2 = 1 ( 4 steps ). But the optimal way is
 * --> 10 -1 = 9 /3 = 3 /3 = 1 ( 3 steps ). 
 * 
 * So, we need to try out all possible
 * steps we can make for each possible value of n we encounter and choose the
 * minimum of these possibilities. It all starts with recursion :). F(n) = 1 +
 * min{ F(n-1) , F(n/2) , F(n/3) } if (n>1) , else 0 ( i.e., F(1) = 0 ) . 
 * 
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
 *
 */
public class StepsToOne {
	int[] steps = null;

	//top-down recursvie split
	int getMinStepsMemoize(int i) {
		//Basically create an array and fill it with -1 
		if (steps == null) {
			steps = new int[i];
			Arrays.fill(steps, -1);
			steps[0] = 0; //except the first as 0.
		}
		
		//if ith step is available then dont enter
		if (steps[i - 1] != -1)
			return steps[i - 1]; // ith step is done!

		// 1 + Min( F (n-1), F(n/2) iff n%2==0, F(n/3) iff n%3==0)
		int minSteps = getMinStepsMemoize(i - 1);
		
		return steps[i - 1] = 1 + Collections.min(Arrays.asList(minSteps,
				i % 2 == 0 ? getMinStepsMemoize(i / 2) : minSteps, i % 3 == 0 ? getMinStepsMemoize(i / 3) : minSteps));
	}

	//Bottom up - iterative
	int getMinSteps(int n) {
		int steps[] = new int[n], i = 0;
		for (i = 1; i < n; i++) {
			// 1 + Min( F (n-1), F(n/2) iff n%2==0, F(n/3) iff n%3==0)
			int minSteps = steps[i - 1];
			steps[i] = 1 + Collections.min(Arrays.asList(minSteps, i % 2 == 0 ? steps[i / 2] : minSteps,
					i % 3 == 0 ? steps[i / 3] : minSteps));
		}
		return steps[n - 1];
	}

	public static void main(String[] args) {
		StepsToOne stepsToOne = new StepsToOne();
		System.out.println(stepsToOne.getMinSteps(43) + " " + stepsToOne.getMinStepsMemoize(43));
	}
}
