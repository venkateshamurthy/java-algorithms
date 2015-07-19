package algos.dp;

import java.util.ArrayDeque;
import java.util.Deque;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Example : this should get us 19 with 2nd, 3rd, 4th and 6th item picked up
 * 
 * <pre>
 * 	static int[] v = new int[] { 0, 6, 4, 5, 3, 9, 7 };
 * 	static int[] w = new int[] { 0, 4, 2, 3, 1, 6, 4 };
 * Item	0	1	2	3	4	5	6	7	8	9	10
 * -----------------------------------------------
 *    0	0	0	0	0	6	6	6	6	6	6	6
 *    -----------------------------------------------
 *    1	0	0	4	4	6	6	10	10	10	10	10
 *    -----------------------------------------------
 *    2	0	0	4	5	6	9	10	11	11	15	15
 *    -----------------------------------------------
 *    3	0	3	4	7	8	9	12	13	14	15	18
 *    -----------------------------------------------
 *    4	0	3	4	7	8	9	12	13	14	16	18
 *    -----------------------------------------------
 *    5	0	3	4	7	8	10	12	14	15	16	19
 *    -----------------------------------------------
 * </pre>
 * 
 * @author vemurthy
 *
 */
@Slf4j
public class Knapsack {
	@Accessors(fluent = true)
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString(of= {"v", "w"})
	static class Item {
		int v, w;

		Item(int v, int w) {
			this.v = v;
			this.w = w;
		}

		static Item[] getItems(int[][] vw) {
			assert vw.length > 0;
			Item[] items = new Item[vw.length];
			for (int i = 0; i < items.length; i++)
				items[i] = new Item(vw[i][0], vw[i][1]);
			return items;
		}
	}

	static Item[] items = Item.getItems(new int[][] { { 0, 0 }, { 6, 4 },
			{ 4, 2 }, { 5, 3 }, { 3, 1 }, { 9, 6 }, { 7, 4 } });

	static int W = 10;
	static int[][] V = new int[items.length][W + 1];
	static boolean[][] keep = new boolean[items.length][W + 1];

	private static int knapsack() {
		for (int i = 1; i < items.length; i++)
			for (int m = 0; m <= W; m++) {
				computeIthItem(i, m);
			}

		Deque<Item> take = new ArrayDeque<>();
		for (int i = items.length - 1, m = W; m > 0 && i > 0; i--)
			if (keep[i][m]) {
				take.addFirst(items[i]);
				m = m - items[i].w;
			}
		log.info(ArrayUtils.toString(take));

		return V[items.length - 1][W];

	}

	private static void computeIthItem(int i, int m) {
		V[i][m] = V[i - 1][m]; //First assign previous row
		if (items[i].w <= m	&& 
			items[i].v + V[i - 1][m - items[i].w] > V[i][m]) {
			V[i][m] = items[i].v + V[i - 1][m - items[i].w];
			keep[i][m] = true;
		}
	}

	public static void main(String[] args) {
		log.info("Value={}" , knapsack());
	}
}