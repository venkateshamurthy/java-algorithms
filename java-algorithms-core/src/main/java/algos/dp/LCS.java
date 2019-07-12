package algos.dp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LCS {
	String a, b;
	//Recursive solution
	int[][] matrix =null;
	public LCS(String a, String b) {
		this.a = a;
		this.b = b;
		matrix = new int[a.length()][b.length()];
	}

	public static void main(String[] args) {
		// turagarahanaipn
		String a = "bnmcqdbxa", b = "bnmcdb";
		LCS l = new LCS(a, b);
		log.info(l.lcs(a, b));
		log.info("{}",l.recursive(a.length() - 1, b.length() - 1));
	}


	public String lcs(String a, String b) {
		int [][] matrix = new int[a.length() + 1][b.length() + 1];

		// row 0 and column 0 are initialized to 0 already
		for (int i = 0; i < a.length(); i++)
			for (int j = 0; j < b.length(); j++)
				matrix[i + 1][j + 1] = a.charAt(i) == b.charAt(j) ? matrix[i][j] + 1
						: Math.max(matrix[i + 1][j], matrix[i][j + 1]);
		
		// read the substring out from the matrix
		StringBuffer sb = new StringBuffer(), sb1 = new StringBuffer();

		for (int i = a.length(), j = b.length(); i != 0 && j != 0;) {
			if (matrix[i][j] == matrix[i - 1][j])
				i--;
			else if (matrix[i][j] == matrix[i][j - 1])
				j--;
			else {
				assert a.charAt(i - 1) == b.charAt(j - 1);
				sb1.setLength(0);
				sb1.append("[i=" + (i - 1) + " j=" + (j - 1) + " "
						+ matrix[i][j] + "]");
				sb.append(sb1).append(a.charAt(i - 1)).append("\n");
				i--;
				j--;
			}
		}
		return sb.toString();
	}
	
	
	
	public int recursive(int i, int j) {
		int result = 0;
		if (i == 0 || j == 0)
			result = 0;
		else if (matrix[i][j] > 0)
			result = matrix[i][j];
		else if (a.charAt(i) == b.charAt(j))
			result = 1 + recursive(i - 1, j - 1);
		else
			result = Math.max(recursive(i, j - 1), recursive(i - 1, j));
		return matrix[i][j] = result;
	}
}
