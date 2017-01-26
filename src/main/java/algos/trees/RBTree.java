package algos.trees;

import java.util.Comparator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.sf.oval.constraint.Range;

public class RBTree<T extends Number & Comparable<T>> {
  public static void main(String[] args) {
    int d = 1;
    int e = 0;
    System.out.println("d=" + d + " complement=" + ~d + " e=" + e + " complement=" + ~e);
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
      int dir = parent.le(dataNode)?0:1;
      int other=~dir&1;
      Node<T> first = parent.link[dir], second = parent.sibling(dir);
      insert(first, data);
      if (first.hasRed()) {
        if (second.hasRed()) {
          parent.red(true);
          first.red(false);
          second.red(false);
        } else {
          if (first.link[dir].hasRed())
            parent = rotate(parent, dir);
          else if (first.link[other].hasRed())
            parent = doubleRotate(parent, other);
        }
      }
      return parent;
    }
  }

  public Node<T> rotate(Node<T> root, int dir) {
    val sibling = root.sibling(dir);
    root.sibling(dir, sibling.link[dir]);
    sibling.link[dir] = root;
    root.red(true);
    sibling.red(false);
    return sibling;
  }

  public Node<T> doubleRotate(Node<T> root, int dir) {
    Node<T> sibling = root.link[~dir];
    sibling = rotate(sibling, ~dir);
    return rotate(root, dir);
  }

  public Node<T> rotate(Node<T> root, boolean dir) {
    return rotate(root, dir ? 0 : 1);
  }

  public Node<T> doubleRotate(Node<T> root, boolean dir) {
    return doubleRotate(root, dir ? 0 : 1);
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


    public boolean isZombie() {
      return link[0] == link[1] && link[0] == this;
    }

    public boolean hasRed() {
      return value != null && red;
    }

    public Node<T> link(boolean dir, Node<T> designate) {
      return link[dir ? 1 : 0] = designate;
    }

    public Node<T> sibling(int dir) {
      return link[~dir&1];
    }
    
    public Node<T> sibling(int dir, Node<T> node) {
      return link[~dir&1]=node;
    }

    @Override
    public void setValue(T value) {
      //this.value=value;
    }

  }
}
