package algos.dp;

public class NumberOfUniqueBSTs {

	public int computePossibilities(int n, int[] solutions) {
		if ((n == 1) || (n == 0))
			return 1;

		int numAllPossibleBSTs = 0;

		for (int i = 1; i <= n; i++) {
			if (solutions[i - 1] == -1)
				solutions[i - 1] = computePossibilities(i - 1, solutions);

			if (solutions[n - i] == -1)
				solutions[n - i] = computePossibilities(n - i, solutions);

			numAllPossibleBSTs += solutions[i - 1] * solutions[n - i];
		}
		return numAllPossibleBSTs;
	}

	public int numTrees(int n) {
		int[] solutions = new int[n + 1];

		for (int i = 0; i <= n; i++)
			solutions[i] = -1;

		return computePossibilities(n, solutions);
	}

	public static void main(String[] args) {
		NumberOfUniqueBSTs solution = new NumberOfUniqueBSTs();
		System.out.println("Total number of BSTs possible for 2 distinct keys: " + solution.numTrees(2));
		System.out.println("Total number of BSTs possible for 3 distinct keys: " + solution.numTrees(3));
		System.out.println("Total number of BSTs possible for 4 distinct keys: " + solution.numTrees(4));
		System.out.println("Total number of BSTs possible for 5 distinct keys: " + solution.numTrees(5));
		System.out.println("Total number of BSTs possible for 6 distinct keys: " + solution.numTrees(6));
		System.out.println("Total number of BSTs possible for 7 distinct keys: " + solution.numTrees(7));
	}
}