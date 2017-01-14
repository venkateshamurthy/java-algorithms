/**
 * Based on http://eternallyconfuzzled.com/tuts/datastructures/jsw_tut_rbtree.aspx. However this is still not complete
 * 
 */
package algos.trees;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.Range;

/**
 * Based on
 * http://eternallyconfuzzled.com/tuts/datastructures/jsw_tut_rbtree.aspx.
 * However this is still not complete
 * 
 * @author vmurthy
 * 
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RBTreeEternallyConfuzzelled<T extends Number & Comparable<T>> {

  public static void main(String[] args) {
    int d = 1;
    int e = 0;
    System.out.println("d=" + d + " complement=" + ~d + " e=" + e + " complement=" + ~e);
  }

  static final Logger log = LogManager.getLogger(StringFormatterMessageFactory.INSTANCE);

  Node<T> root = null;

  public Node<T> insert(T data) {
    return insert(root, data).red(false);
  }

  Node<T> insert(Node<T> root, T data) {
    Node<T> t = Node.of(data);
    if (root == null)
      root = t;
    else if (!root.eq(data)) {

      boolean bDir = root.lt(t);
      int dir = bDir ? 0 : 1, opposite = ~dir;// !bDir ? 0 : 1;

      // Insert child at root.link[dir]
      val inserted = root.link[dir] = insert(root.link[dir], data);

      // if inserted child is red
      if (inserted.red()) {

        // check if sibling is also red
        if (root.link[opposite].hasRed()) {

          /* Case 1 - Double RED CHILDREN - color flip */
          root.red(true);
          root.link[0].red(false);
          root.link[1].red(false);
        } else {

          /* Case 2 - inserted's rotation dir child is red */
          if (inserted.link[dir].hasRed())
            root = rotate(root, !bDir);

          /* Case 3 - inserted's anti-rotation dir child is red */
          else if (inserted.link[opposite].hasRed())
            root = doubleRotate(root, !bDir);
        }
      }
    }
    root.red(false);
    return root;
  }

  /**
   * <pre>
   *      RIGHT                                                     LEFT
   *      _____                                                 ______
   *      |    |                                                |    |
   *     root  V                   L1                           V   root                     R1
   *    /    \                    /  \\                            /    \                  //  \
   *   L1     R1    =====>           root                         L1     R1     =====>   root   R1'   
   *  /  \                          //   \                       /  \   /  \            /    \\
   *      R1'                      R1'    R1                           L1'  R1'        L1     L1'
   * 	 <code>
   *  public Node<T> rotate(Node<T> root, boolean right) {         public Node<T> rotate(Node<T> root, boolean left) {
   * 	  boolean left = !right;                                       boolean right = !left;
   * 	  val L1 = root.link(left);                                    val R1 = root.link(right);
   * 	  root.link(left, L1.link(right));                             root.link(right, R1.link(left));
   * 	  L1.link(right, root);                                        R1.link(left, root);
   * 	  root.red(true);                                              root.red(true);
   * 	  L1.red(false);                                               R1.red(false);
   * 	  return L1;                                                   return R1;
   *  }                                                            }   
   * 	 </code>
   * </pre>
   * 
   * @param root
   * @param dir
   *          The direction of rotation left(false) and right(true) for the
   *          node.
   * @return
   */
  public Node<T> rotate(Node<T> root, boolean dir) {
    boolean opposite = !dir;
    val save = root.link(opposite);
    root.link(opposite, save.link(dir));
    save.link(dir, root);
    root.red(true);
    save.red(false);
    return save;
  }

  public Node<T> doubleRotate(Node<T> root, boolean dir) {
    boolean opposite = !dir;
    root.link(opposite, rotate(root.link(opposite), opposite));
    return rotate(root, dir);
  }

  public int assertRB(Node<T> node) {
    int lh, rh;
    if (node == null)
      return 1;
    else {
      Node<T> ln = node.link[0], rn = node.link[1];

      /* Assert for double red */
      if (node.red())
        if (ln.red() || rn.red()) {
          log.warn("node(%s)=red and ln/rn is red", node.data());
          return 0;
        }

      lh = assertRB(ln);
      rh = assertRB(rn);

      /* Invalid binary search tree */
      if (ln != null && ln.ge(root) || rn != null && rn.le(root)) {
        log.warn("Binary tree violation");
        return 0;
      }

      /* Black height mismatch */
      if (lh != 0 && rh != 0 && lh != rh) {
        log.warn(" Black violations");
        return 0;
      }

      /* Only count black links */
      return lh != 0 && rh != 0 ? lh + root.isBlack() : 0;

    }

  }

  Node<T> swap(Node<T> a, Node<T> b) {
    val save = a;
    a = b;
    b = save;
    return save;
  }

}

@Data // (staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@Accessors(fluent = true, chain = true)
class Node<T extends Number & Comparable<T>> {
  public static <Y extends Number & Comparable<Y>> Node<Y> of(Y data) {
    return new Node<Y>(data);
  }

  public static Node NULL = of(null);
  @NonFinal
  Boolean red = true;
  T data;
  Node<T>[] link = new Node[] { NULL, NULL };

  public int lt(T dataValue) {
    return data.compareTo(dataValue) < 0 ? 0 : 1;
  }

  /**
   * @param dir
   * @return
   */
  public boolean has(@Range(min = 0, max = 1) int dir) {
    return link[dir] != null;
  }

  public int le(T dataValue) {
    return data.compareTo(dataValue) <= 0 ? 0 : 1;
  }

  public boolean eq(T dataValue) {
    return data.compareTo(dataValue) == 0;
  }

  public int ge(T dataValue) {
    return data.compareTo(dataValue) >= 0 ? 0 : 1;
  }

  public boolean lt(Node<T> that) {
    return data.compareTo(that.data) < 0;
  }

  public boolean le(Node<T> that) {
    return data.compareTo(that.data) <= 0;
  }

  public boolean ge(Node<T> that) {
    return data.compareTo(that.data) >= 0;
  }

  public boolean hasRed() {
    return data != null && red;
  }

  public boolean hasBlack() {
    return data != null && !red;
  }

  public int isRed() {
    return red ? 1 : 0;
  }

  public int isBlack() {
    return !red ? 1 : 0;
  }

  public boolean Null() {
    return data == null;
  }

  public Node<T> link(boolean dir) {
    return dir ? link[1] : link[0];
  }

  public Node<T> link(int dir) {
    return dir == 0 ? link[0] : link[1];
  }

  public Node<T> link(boolean dir, Node<T> designate) {
    return link[dir ? 1 : 0] = designate;
  }

  public Node<T> link(boolean dir, T designate) {
    return link(dir, of(designate));
  }
}