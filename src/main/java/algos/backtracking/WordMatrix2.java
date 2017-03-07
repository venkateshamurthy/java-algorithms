package algos.backtracking;

import static algos.backtracking.WordMatrix2.State;

/** A word matrix in which a word needs to be searched in all 8 directions.*/
public class WordMatrix2 implements Backtracker<State> {
  /** A backtrack state machine.*/
  private final State state;

  /** Ctor.*/
  WordMatrix2(char[][] matrix, String word){
    this.state = new State(matrix,word);
  }

  /**{@inheritDoc}.*/
  public void print() {
    state.print();
  }

  /**{@inheritDoc}.*/
  @Override
  public boolean search() {
    return state.search(this);
  }

  public static void main(String[] args) {
    char[][] matrix = { 
        { 't', 'z', 'x', 'c', 'd' }, 
        { 'a', 'h', 'n', 'z', 'x' }, 
        { 'h', 'w', 'o', 'i', 'o' },
        { 'o', 'r', 'n', 'r', 'n' }, 
        { 'a', 'b', 'r', 'i', 'n' } };
    
    WordMatrix2 w = new WordMatrix2(matrix, "horizon");
    
    if (w.search()) {
      w.print();
    } else {
      System.out.println("NO PATH FOUND");
    }
  }

  /** A state class for holding Word Matrix search of a word ib tne classic backtrack style.*/
  protected static class State implements BacktrackState<State> {
    char[][] matrix;
    String word;
    int[][] solution;
    int path = 1;
    int row, col, index;

    State(char[][] matrix, String word) {
      this.matrix = matrix;
      this.word = word;
      solution = new int[matrix.length][matrix[0].length];
    }

    boolean validCell() {
      return validCell(row, col);
    }

    boolean validCell(int row, int col) {
      return row >= 0 && row < matrix.length && col >= 0 && col <= matrix[0].length;
    }

    @Override
    public boolean isValid() {
      boolean unsolvedCell = validCell() && solution[row][col] == 0;
      boolean matched = validCell() && index < word.length() && word.charAt(index) == matrix[row][col];
      return unsolvedCell && matched;
    }

    @Override
    public void markAsPossible() {
      if (validCell())
        solution[row][col] += path++;
    }

    @Override
    public void backTrack() {
      if (validCell())
        solution[row][col] += --path;
    }

    @Override
    public boolean isGoal() {
      return index == word.length() - 1;
    }

    @Override
    public State createMemento() {
      State copy = new State(matrix, word);
      copy.col = col;
      copy.row = row;
      copy.index = index;
      copy.path = path;
      for (int i = 0; i < solution.length; i++) {
        System.arraycopy(solution[i], 0, copy.solution[i], 0, solution[i].length);
      }
      return copy;
    }

    @Override
    public State restoreMemento(State copy) {
      col = copy.col;
      row = copy.row;
      index = copy.index;
      path = copy.path;
      for (int i = 0; i < solution.length; i++) {
        System.arraycopy(copy.solution[i], 0, solution[i], 0, solution[i].length);
      }
      return this;
    }

    protected State mutate(int row, int col, int index) {
      this.row = row;
      this.col = col;
      this.index = index;
      return this;
    }

    @Override
    public void print() {
      for (int i = 0; i < solution.length; i++) {
        for (int j = 0; j < solution[0].length; j++) {
          System.out.print(" " + solution[i][j] + matrix[i][j]);
        }
        System.out.println();
      }
    }

    @Override
    public boolean tryReachNextGoal(Backtracker<State> backTracker) {
      State current = createMemento();
      return backTracker.search(restoreMemento(current).mutate(row + 1, col,     index + 1))
          || backTracker.search(restoreMemento(current).mutate(row - 1, col,     index + 1))
          || backTracker.search(restoreMemento(current).mutate(row,     col + 1, index + 1))
          || backTracker.search(restoreMemento(current).mutate(row,     col - 1, index + 1))
          || backTracker.search(restoreMemento(current).mutate(row - 1, col + 1, index + 1))
          || backTracker.search(restoreMemento(current).mutate(row - 1, col - 1, index + 1))
          || backTracker.search(restoreMemento(current).mutate(row + 1, col - 1, index + 1))
          || backTracker.search(restoreMemento(current).mutate(row + 1, col + 1, index + 1));
    }

    @Override
    public boolean search(Backtracker<State> backTracker) {
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (backTracker.search(mutate(i, j, 0))) {
            return true;
          }
        }
      }
      return false;
    }
  }
}
