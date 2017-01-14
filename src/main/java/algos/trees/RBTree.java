package algos.trees;

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
    if (parent.isZombie())
      return parent = new Node<T>(data).red(true);
    else {
      int dir = parent.le(data);
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
  private static class Node<T extends Number & Comparable<T>> {

    public static Node NULL = new Node(null);
    @NonFinal
    Boolean red = true;
    T data;
    Node<T>[] link = new Node[] { this, this };

    public int lt(T dataValue) {
      return data.compareTo(dataValue) < 0 ? 0 : 1;
    }

    public boolean isZombie() {
      return link[0] == link[1] && link[0] == this;
    }

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
      return link(dir, new Node(designate));
    }

    public Node<T> sibling(int dir) {
      return link[~dir&1];
    }
    
    public Node<T> sibling(int dir, Node<T> node) {
      return link[~dir&1]=node;
    }

  }
}
