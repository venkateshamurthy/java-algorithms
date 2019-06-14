package algos.backtracking;

import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// queue node used in BFS
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true, fluent = true)
class Node
{
    // (x, y) represents chess board coordinates
	// dist represent its minimum distance from the source
	final int x, y;
	@Setter int dist;
	public Node(Pair<Integer, Integer> p){this(p.getLeft(),p.getRight());}
}

class Main
{
	// Below arrays details all 8 possible movements
	// for a knight
	private static int row[] = { 2, 2, -2, -2, 1, 1, -1, -1 };
	private static int col[] = { -1, 1, 1, -1, 2, -2, 2, -2 };
	private static Range<Integer> validCells = Range.closedOpen(0, row.length);
	// Check if (x, y) is valid chess board coordinates
	// Note that a knight cannot go out of the chessboard
	private static boolean valid(int x, int y)
	{
		return  Stream.of(x,y).allMatch(validCells::contains);
	}

	// Find minimum number of steps taken by the knight
	// from source to reach destination using BFS
	public static int BFS(Node src, Node dest, int N)
	{
		// map to check if matrix cell is visited before or not
		Set<Node> visited = new HashSet<>();

		// create a queue and enqueue first node
		Queue<Node> q = new ArrayDeque<>();
		q.add(src);

		// run till queue is not empty
		while (!q.isEmpty())
		{
			// pop front node from queue and process it
			Node node = q.poll();

			int x = node.x;
			int y = node.y;
			int dist = node.dist;

			// if destination is reached, return distance
			if (x == dest.x && y == dest.y)
				return dist;

			// Skip if location is visited before
			if ( ! visited.contains(node))
			{
				// mark current node as visited
				visited.add(node);
				IntStream.range(0,8)
						.mapToObj(i-> Pair.of(x + row[i], y+col[i]))
						.filter(p->valid(p.getLeft(), p.getRight()))
						.map(Node::new)
						.map(n->n.dist(dist+1))
						.forEach(q::add);
			}
		}

		// return INFINITY if path is not possible
		return Integer.MAX_VALUE;
	}

	public static void main(String[] args)
	{
		int N = 8;

		// source coordinates
		Node src = new Node(0, 7);

		// destination coordinates
		Node dest = new Node(7, 0);

		System.out.println("Minimum number of steps required is " + BFS(src, dest, N));
	}
}
