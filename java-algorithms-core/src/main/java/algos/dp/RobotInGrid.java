package algos.dp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

public class RobotInGrid {
  // A robot needs to get from a left top corner to bottom right corner in a
  // columnar matrix consisting r rows and c columns. There might be few cells
  // off limits; design an algorithm to see if it can find a path

  Map<Pair<Integer, Integer>, Boolean> cache = new HashMap<>();
  boolean[][] offLimits = {};
  Set<Pair<Integer, Integer>> pathSet = new HashSet<>();

  int rows, columns;

  RobotInGrid(int rows, int columns, boolean[][] offLimits) {
    this.rows = rows;
    this.columns = columns;
    this.offLimits = offLimits;
    findTopToBottom(0, 0);
  }

  public boolean findTopToBottom(int row, int column) {
    boolean withinLimits = row >= 0 && row < rows && column >= 0 && column < columns &&
        offLimits[row][column];
    Pair<Integer, Integer> point = Pair.create(row, column);
    if (withinLimits && !cache.containsKey(point)) {
        boolean exists = row == 0 && column == 0 ||
            findTopToBottom(row + 1, column) ||findTopToBottom(row, column + 1);
        if(exists) {
          pathSet.add(point);
        }
        cache.put(point, exists);
    }
    return cache.getOrDefault(point,false);
  }
}
