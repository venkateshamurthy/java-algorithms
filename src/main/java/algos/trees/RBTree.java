package algos.trees;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

public class RBTree<T extends Number & Comparable<T>> {
  public static void main(String[] args) {
    int d = 1;
    int e = 0;
    System.out.println("d=" + d + " complement=" + (~d&1) + " e=" + e + " complement=" + (~e&1));
  }
  Node<T> root;

  public Node<T> insert(T data) {
    return insert(root, data).red(false);
  }

  public Node<T> insert(Node<T> parent, T data) {
    Node<T> dataNode=new Node<>(data);
    if (parent.isZombie())
      return parent = dataNode.red(true);
    else {
      int dir = parent.dir(dataNode);
      Node<T> left = parent.link[dir], right = parent.link[~dir&1];
      insert(left, data);
      if (left.hasRed()) {
        if (right.hasRed()) {                  parent.meRedAndMyChildrenBlack();
        } else {
          if      (left.link[dir].hasRed())    parent = rotate(parent, dir);
          else if (left.link[~dir&1].hasRed()) parent = doubleRotate(parent, ~dir&1);
        }
      }
      return parent;
    }
  }

  public Node<T> rotate(Node<T> root, int dir) {
    val sibling = root.link[~dir&1];
    root.link[dir]= sibling.link[dir];
    sibling.link[dir] = root;
    root.red(true);
    sibling.red(false);
    return sibling;
  }

  public Node<T> doubleRotate(Node<T> root, int dir) {
    Node<T> sibling = root.link[~dir&1];
    sibling = rotate(sibling, ~dir&1);
    return rotate(root, dir);
  }

  @Data
  @FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
  @Accessors(fluent = true, chain = true)
  private static class Node<T extends Number & Comparable<T>>  extends BaseNode<Node<T>> implements ComparableNode<T>{
    Comparator<T> comparator = (o1,o2)->o1.compareTo(o2);
    public static Node NULL = new Node(null);
    @NonFinal
    Boolean red = true;
    T value;
    Node<T>[] link = new Node[] { this, this };

    public void meRedAndMyChildrenBlack() {red=true; left.red=false; right.red=false;}
    public boolean isZombie() {
      return link[0] == link[1] && link[0] == this;
    }

    public boolean hasRed() {
      return value != null && red;
    }

    public int dir(Node<T> another) {
      return le(another) ? 0 : 1;
    }
    @Override
    public void setValue(T value) {
      //this.value=value;
    }

  }
}
