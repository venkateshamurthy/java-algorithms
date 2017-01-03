/**
 * 
 */
package algos.trees.miscellanious;

import java.util.Collections;
import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

import algos.trees.Element;
import algos.trees.NaturalComparator;
import algos.trees.Tree;
import algos.trees.visitors.InOrderPrinter;
import algos.trees.visitors.Visitor;
//Using lombok annotation for log4j handle

/**
 * @author vmurthy
 * 
 */
// Log4j Handle creator (from lombok)
@Slf4j
@Data()
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MakeBST {

	Comparator<Integer> comparator = new NaturalComparator<Integer>();

	@NonFinal Element<Integer> root;

	/**
	 * Basically get a binary tree. Make an in order traversal with what u have.
	 * Then sort the numbers collected and re-use the inorder visitor to re-set
	 * the value
	 * <p>
	 * Refer http://www.geeksforgeeks.org/binary-tree-to-binary-search-tree-conversion/
	 */
	@Test public void doMakeBST() {
		//Use this data as asserting reference
		Integer[] data = new Integer[] { 2, 4, 7, 8, 10 };
		//build the tree in haphazard manner (so no BST now)
		Element.Factory<Integer, Element<Integer>> factory=new Element.Factory<>(comparator);
		root = factory.create(10);
		
		root
		.left(factory.create( 2))
		.right(factory.create( 7));
		
		root
		.left()
		.left(factory.create( 8))
		.right(factory.create( 4));

		//Next,  in-order traverse and collect the numbers
		InOrderPrinter<Integer> inOrder = new InOrderPrinter<Integer>();
		inOrder.visit(root);
		val list = inOrder.collection();
		
		//SORT the results....
		Collections.sort(list);
		
		//Next, Build a setting visitor and use the sorted values
		Visitor<Integer, Integer, Void> inOrderSetter = new Visitor<Integer, Integer, Void>() {
			{
				for (val i : list)
					log.debug("After Sort:" + i);
			}

			@Override public Integer visit(Tree<Integer> t) {
				return visit(t.root());
			}

			int i = 0;

			@Override public Integer visit(Element<Integer> e) {
				// L-N-R
				if (e.hasLeft()) {
					visit(e.left());
				}
				val value = doSomethingOnElement(e);

				if (e.hasRight()) {
					visit(e.right());
				}
				return value;
			}

			@Override public Integer doSomethingOnElement(Element<Integer> e) {
				e.value(list.get(i++));
				return e.value();
			}

			@Override public Void collection() {
				return null;
			}

		};
		inOrderSetter.visit(root);
		InOrderPrinter<Integer> afterSettingTraverser = new InOrderPrinter<Integer>();
		afterSettingTraverser.visit(root);
		for (Integer i : afterSettingTraverser.collection())
			log.debug("After Setting:" + i);
		Assert.assertArrayEquals(data, afterSettingTraverser.collection()
				.toArray());

	}

}
