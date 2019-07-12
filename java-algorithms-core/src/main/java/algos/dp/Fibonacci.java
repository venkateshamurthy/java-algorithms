package algos.dp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Fibonacci {
	// Iteration method
	static int fibIteration(int n) {
		int x = 0, y = 1, z = 1;
		for (int i = 0; i < n; i++) {
			x = y;
			y = z;
			z = x + y;
			log.info("{}",x);
		}
		return x;
	}

	// Recursive method
	static int fibRecursion(int n) {
		return n==0||n==1? n: fibRecursion(n - 1) + fibRecursion(n - 2);
	}

	public static void main(String[] args) {
		log.info(fibIteration(10) + "  " + fibRecursion(10));
	}
}
