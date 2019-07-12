package algos.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;

/**
 * 
 * @author vemurthy
 *         <p>
 *         Refer for an <a href=
 *         "http://t2a.co/blog/index.php/spell-checking-or-search-engine-suggestions-using-bk-trees"
 *         >example of Burkhard Keller Tree</a>
 */
@Slf4j
@Data(staticConstructor = "of")
@EqualsAndHashCode(of = { "root" })
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BKTree {
	/** Root seed of the BK tree */
	@NotNull
	BKNode root;
	EditDistance ed=new EditDistance();
	/**
	 * Search a given string with max range of distance
	 * 
	 * @param searchString
	 * @param maxDistanceRange
	 * @return List of {@code BKNode}s
	 */
	public List<BKNode> search(@NotNull String searchString,
			@Range(min = 1, max = 100) int maxDistanceRange) {
		return root.search(new BKNode(searchString, maxDistanceRange));
	}

	/**
	 * Exact word search, same as {@link search(String, 1)}
	 * 
	 * @param searchString
	 * @return match or empty string.
	 */
	public String search(@NotNull String searchString) {
		List<BKNode> list = root.search(new BKNode(searchString, 1));
		return list.isEmpty() ? "" : list.iterator().next().name;
	}

	/**
	 * add a string node to the tree.
	 * 
	 * @param text
	 */
	public BKTree add(@NotNull String text) {
		root.addNode(BKNode.of(text,
				ed.computeLevenshteinDistance(root.name, text)));
		return this;
	}

	public static void main(String[] args) {
		BKTree tree = BKTree.of(BKNode.of("Leeds", 0));
		//tree.add("Leeds");
		int maxD=1;
		tree.add("Bristol").add("York").add("Leicester").add("Hull").add("Durham");
		log.info("Leaves->"+tree.search("Leaves", maxD));
		log.info("Brisbane->"+tree.search("Brisbane", maxD));
		log.info("Yorker->"+tree.search("Yorker", maxD));
		log.info("Leicense->"+tree.search("Leicense", maxD));
		log.info("Hulk->"+tree.search("Hulk", maxD));
		log.info("Durham->"+tree.search("Durham", maxD));
		log.info("Hill->"+tree.search("Hill", maxD));
	}

	@Data(staticConstructor = "of")
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	@ToString(of = { "name", "distance" }, doNotUseGetters = true, includeFieldNames = false)
	@EqualsAndHashCode(of = { "name", "distance" })
	private static class BKNode {
		/** node name which is basically a word */
		@NotNull String name;
		
		/** children is a map of nodes keyed on by edit-distance it has to this node*/
		@NotNull Map<Integer, BKNode> children = new HashMap<>();
		
		EditDistance ed = new EditDistance();
		
		/** distance of this node to its parent */
		int distance;

		public void addNode(@NotNull BKNode newNode) {
			if (this.equals(newNode))
				return;
			final int distance = newNode.getDistance();
			final BKNode childNode = children.get(distance);
			if (childNode == null)
				children.put(distance, newNode);
			else
				childNode.addNode(BKNode.of(newNode.name, ed.computeLevenshteinDistance(childNode.name,newNode.name)));
		}

		public List<BKNode> search(BKNode givenNode) {
			final int maxDistance = givenNode.getDistance();
			final int gap = ed.computeLevenshteinDistance(name, givenNode.getName());
			final List<BKNode> matches = new ArrayList<>();
			if (gap <= maxDistance)
				matches.add(this);
			if (!children.isEmpty())
				for (int i = gap - maxDistance; i <= gap
						+ maxDistance; i++) {
					BKNode child = children.get(i);
					if (child != null)
						matches.addAll(child.search(givenNode));
				}
			return matches;
		}
	}
}