package algos.backtracking;


public class WordMatrix {
  int[][] solution;
  int path = 1;
  char[][] matrix;
  String word;

  // initialize the solution matrix in constructor.
  public WordMatrix(char[][] matrix, String word) {
    solution = new int[matrix.length][matrix[0].length];
    this.matrix = matrix;
    this.word = word;
  }

  public boolean searchWord() {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (search(i, j, 0)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean stateValid(int row, int col, int index) {
    boolean validCell = row >= 0 && row < matrix.length && col >= 0 && col <= matrix[0].length;
    boolean unsolvedCell = validCell && solution[row][col] == 0;
    boolean matched = validCell && index < word.length() && word.charAt(index) == matrix[row][col];
    return unsolvedCell && matched;
  }

  public boolean search(int row, int col,  int index) {
    if(stateValid(row, col, index)) {
      solution[row][col] += path++;
      if (isGoal(row, col, index)  || tryReachNextGoal(row, col, index)) {
        return true;
      }
      solution[row][col] -= --path;
    }
    return false;
  }

  public boolean isGoal(int row, int col, int index) {
    return index == word.length() - 1;
  }

  public boolean tryReachNextGoal(int row, int col, int index) {
    return
    search(row + 1, col,     index + 1)||
    search(row - 1, col,     index + 1)||
    search(row,     col + 1, index + 1)||
    search(row,     col - 1, index + 1)||
    search(row - 1, col + 1, index + 1)||
    search(row - 1, col - 1, index + 1)||
    search(row + 1, col - 1, index + 1)||
    search(row + 1, col + 1, index + 1);
  }
  public void print() {
    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution.length; j++) {
        System.out.print(" " + solution[i][j]+matrix[i][j]);
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    char[][] matrix = { 
        { 't', 'z', 'x', 'c', 'd' },
        { 'a', 'h', 'n', 'z', 'x' }, 
        { 'h', 'w', 'o', 'i', 'o' },
        { 'o', 'r', 'n', 'r', 'n' }, 
        { 'a', 'b', 'r', 'i', 'n' } };
    WordMatrix w = new WordMatrix(matrix,"horizon");
    if (w.searchWord()) {
      w.print();
    } else {
      System.out.println("NO PATH FOUND");
    }

  }

}