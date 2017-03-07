package algos.backtracking;

import static algos.backtracking.MazeConstants.CORRIDOR;
import static algos.backtracking.MazeConstants.DEAD_END;
import static algos.backtracking.MazeConstants.PATH;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

/**
 * Courtesy: Data structures and Java collections framework by William Collins
 * <p>
 * I have used these classes from the above book but have moulded to make use of
 * succinct annotations and with few other changes.
 * 
 * @author vemurthy
 *
 */
@Slf4j
public class MazeUser<P extends Position> {
	public static void main(String[] args) {
		new MazeUser<Position>().run();
	} // method main

	public void run() {
		final String INPUT_PROMPT = "\n\nPlease enter the path for the file whose first line contains the "
				+ "number of rows and columns,\nwhose 2nd line the start row and column, "
				+ "whose 3rd line the finish row and column, and then the maze, row-by-row: ";
		final String INITIAL_STATE = "\nThe initial state is as follows (0 = WALL, 1 = CORRIDOR):\n";
		final String START_INVALID = "The start position is invalid.";
		final String FINISH_INVALID = "The finish position is invalid.";
		final String FINAL_STATE = "The final state is as follows (2 = DEAD END, 9 = PATH):\n";
		final String SUCCESS = "\n\nA solution has been found:";
		final String FAILURE = "\n\nThere is no solution:";
		Scanner fileScanner = null;
		try (Scanner keyboardScanner = new Scanner(System.in)) {
			while (true) {
				System.out.print(INPUT_PROMPT);
				String fileName = keyboardScanner.next();
				fileScanner = new Scanner(new File(fileName));
				break;
			}
		} catch (IOException e) {
			log.info("\n" + e);
		}
		
		int rows = fileScanner.nextInt(), columns = fileScanner.nextInt();
		P start=Position.<P>createPosition(fileScanner);
		P finish=Position.<P>createPosition(fileScanner);
		byte[][] grid = new byte[rows][columns];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				grid[i][j] = fileScanner.nextByte(CORRIDOR + 1);
		
		Maze<P> maze = new Maze<P>(start,finish,grid);
		try {
			
			log.info(INITIAL_STATE + maze);
			if (!maze.isOK(maze.start))
				log.info(START_INVALID);
			else if (!maze.isOK(maze.finish))
				log.info(FINISH_INVALID);
			else {
				if (maze.search())
					log.info(SUCCESS);
				else
					log.info(FAILURE);
				log.info(FINAL_STATE + maze);
			} 
		} 
		catch (InputMismatchException e) {
			log.info("\n" + e + ": " + fileScanner.nextLine());
		}
		catch (NumberFormatException e) {
			log.info("\n" + e);
		} 
		catch (RuntimeException e) {
			log.info("\n" + e);
			log.info(FINAL_STATE + maze);
		} 
	} // method run

}
@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
abstract class BackTrack<P> {
	/** a start and finish position */
	P start, finish;
	
	abstract boolean isOK(P position);
	abstract void markAsPossible(P position);
	abstract boolean isGoal(P position);
	abstract void markAsDeadEnd(P position);
	abstract Iterable<P> iterator(P position);
	
	public boolean tryToReachGoal(P position) {
    if (isOK(position)) {
      for (P nextPos : iterator(position)) {
        incrementState(position);
        if (isOK(nextPos)) return search(nextPos);
        else               decrementState(position);
      }
    }
    markAsDeadEnd(position);
		return false;
	}
	public abstract void incrementState(P position);
	public abstract void decrementState(P position);
	
	public boolean search() {
		return search(start);
	}

	
	public boolean search(P position) {
	  markAsPossible(position);
		if (isGoal(position) || tryToReachGoal(position)) {
		  log.debug(position+" is good!");
			return true;
		}
		markAsDeadEnd(position);
		return false;
	}
}


@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
class Maze<P extends Position> extends BackTrack<P> {

	byte[][] grid;

	public Maze(P start, P finish, byte[][] grid) {
		super(start, finish);
		this.grid=grid;
	}

	public boolean isOK(P pos) {
		return     pos.x >= 0 && pos.x < grid.length 
				&& pos.y >= 0 && pos.y < grid[0].length 
				&& grid[pos.x][pos.y] == CORRIDOR;
	}

	public void markAsPossible(P pos) {
		grid[pos.x][pos.y] =  PATH;
	}

	public boolean isGoal(P pos) {
		return finish.equals(pos);
	}

	public void markAsDeadEnd(P pos) {
		grid[pos.x][pos.y] = DEAD_END;
	}

	public Iterable<P> iterator(P pos) {
		return new MazeIterator<P>(pos);
	}

	@Data
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private static class MazeIterator<P extends Position> implements
			Iterator<P>, Iterable<P> {
		static int MAX_MOVES = 4;
		P pos;
		@NonFinal
		int count;

		public boolean hasNext() {
			return count < MAX_MOVES;
		}

		public P next() {
			return pos.nextPosition(count++);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterator<P> iterator() {
			return this;
		}
	}
} // class Maze

/** All constant */
interface MazeConstants {
	byte WALL = 0, CORRIDOR = 1, PATH = 9, DEAD_END = 2;
}

@Data
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
class Position {
	int x, y;
	
	public <P extends Position> P nextPosition(int count) {
		switch (count) {
			case 0:	return createPosition(x - 1, y);
			case 1:	return createPosition(x + 1, y);
			case 2:	return createPosition(x, y + 1);
			case 3:	return createPosition(x, y - 1);
			default:return createPosition(0, 0);
		}
	}

	static <P extends Position> P createPosition(Scanner scanner) {
		return createPosition(scanner.nextInt(), scanner.nextInt());
	}

	@SuppressWarnings("unchecked")
	static <P extends Position> P createPosition(int row, int column) {
		return ((P) new Position(row, column));
	}
}
