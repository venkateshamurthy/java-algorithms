package algos.newtrees;

import lombok.*;
import org.apache.commons.collections4.ComparatorUtils;

@RequiredArgsConstructor(staticName = "node")
@Getter @Setter
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    final T data;
    Node<T> parent, left, right;

    public Node<T> minimum() {
        var node =  this;
        while (node.left != null)
            node = node.left;
        return node;
    }

    public  Node<T> maximum() {
        var node =  this;
        while (node.right != null)
            node = node.right;
        return node;
    }

    public Node<T> successor() {
        if ( right != null )
            return right.minimum();
        var node =  this;
        while (node.isRightChild())
            node = node.parent;
        return node.parent;
    }

    public  Node<T> predecessor() {
        if ( left != null )
            return left.maximum();
        var node =  this;
        while (node.isLeftChild())
            node = node.parent;
        return node.parent;
    }

    /**
     *        y := NIL
     * 3      x := T.root
     * 4      while x ≠ NIL do
     * 5        y := x
     * 6        x:= z.key < x.key ? x.left:x.right
     * 11     repeat
     * 12     z.parent := y
     * 13     if y = NIL then
     * 14       T.root := z
     * 15     else if z.key < y.key then
     * 16       y.left := z
     * 17     else
     * 18       y.right := z
     * 19     end if
     * @param z
     */
    public void add(final Node<T> z) {
        Node<T> y = null;
        var x = this;
        while(x != null) {
            y = x;
            x = z.lt(x) ? x.left : x.right;
        }
        z.parent = y;
        if (z.lt(y))
            y.left = z;
        else
            y.right = z;
    }

    public boolean isRightChild() {return parent != null && parent.right == this;}
    public boolean isLeftChild() {return parent != null && parent.left == this;}
    public int compareTo(Node<T> other) {return data.compareTo(other.data);}
    public int compareWith(T other) {return data.compareTo(other);}
    public boolean gt(Node<T> other) {return ComparatorUtils.max(this, other, Node::compareTo) == this;}
    public boolean lt(Node<T> other) {return ComparatorUtils.min(this, other, Node::compareTo) == this;}
}
