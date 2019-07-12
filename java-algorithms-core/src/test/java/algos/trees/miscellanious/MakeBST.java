/**
 * 
 */
package algos.trees.miscellanious;

import java.util.Collections;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import algos.trees.BSTNode;
import algos.trees.Tree;
import algos.trees.visitors.BSTVisitor;
import algos.trees.visitors.InOrderPrinter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
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

  Comparator<Integer> comparator = (o1, o2) -> o1.compareTo(o2);

  @NonFinal
  BSTNode<Integer> root;

  /**
   * Basically get a binary tree. Make an in order traversal with what u have.
   * Then sort the numbers collected and re-use the inorder visitor to re-set
   * the value
   * <p>
   * Refer
   * http://www.geeksforgeeks.org/binary-tree-to-binary-search-tree-conversion/
   */
  @Test
  public void doMakeBST() {
    // Use this data as asserting reference
    Integer[] data = new Integer[] { 2, 4, 7, 8, 10 };
    // build the tree in haphazard manner (so no BST now)
    root = new BSTNode<>(10);

    root.left(new BSTNode<>(2)).right(new BSTNode<>(7));

    root.left().left(new BSTNode<>(8)).right(new BSTNode<>(4));

    // Next, in-order traverse and collect the numbers
    InOrderPrinter<Integer> inOrder = new InOrderPrinter<Integer>();
    inOrder.visit(root);
    val list = inOrder.collection();

    // SORT the results....
    Collections.sort(list);

    // Next, Build a setting visitor and use the sorted values
    BSTVisitor<Integer, Integer, Void> inOrderSetter = new BSTVisitor<Integer, Integer, Void>() {
      {
        for (val i : list)
          log.debug("After Sort:" + i);
      }

      @Override
      public Integer visit(Tree<Integer, BSTNode<Integer>> t) {
        return visit(t.root());
      }

      int i = 0;

      @Override
      public Integer visit(BSTNode<Integer> e) {
        // L-N-R
        if (e.hasLeft()) {
          visit(e.left());
        }
        e.value(list.get(i++));
        val value = e.value();

        if (e.hasRight()) {
          visit(e.right());
        }
        return value;
      }


      @Override
      public Void collection() {
        return null;
      }

    };
    inOrderSetter.visit(root);
    InOrderPrinter<Integer> afterSettingTraverser = new InOrderPrinter<Integer>();
    afterSettingTraverser.visit(root);
    for (Integer i : afterSettingTraverser.collection())
      log.debug("After Setting:" + i);
    Assert.assertArrayEquals(data, afterSettingTraverser.collection().toArray());

  }

}
