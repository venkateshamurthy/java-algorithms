package algos.trees;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Stack;

@RequiredArgsConstructor
public class BST <T extends Comparable<T>> {
    final Node root;
    void add(T... ts) {
        Arrays.stream(ts).forEach( t -> {

        });
    }

    @Setter  @Getter  @Accessors(fluent = true) @ToString
    class Node implements Comparable<Node> {
        T value;
        Node left, right;
        public Node(T t) {
            value = t;
        }
        public void child(T child){
            int cmp=child.compareTo(value);
            if (cmp!=0) {
                var c = new Node(child);
                var link = cmp==-1 ? left : right;
                link.value=child;
            }
        }
        public Node left(T t){return left.value(t);}
        public Node right(T t){return right.value(t);}
        public boolean hasLeft() { return left!=null;}
        public boolean hasRight() { return right!=null;}
        public boolean isLeaf() { return left==null && right==null; }
        @Override
        public int compareTo(Node o) {return value.compareTo(o.value);}
    }
}
