package algos.newtrees;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

//https://justamonad.com/binary-tree-postorder-iterator/#:~:text=In%20Postorder%20traversal%2C%20we%20first,should%20insert%20the%20right%20nodes.
public class POstOrderIterative {
    public static void main(String[] ar) {

        int[] data = new int[]{13, 3, 4, 12, 14, 10, 5, 1, 8, 2, 7, 9, 11, 6, 18};
        final BinaryTreeNode root = new BinaryTreeNode(data[0]);

        for (int i = 1; i < data.length; i++) {
            // check where does data[i[ fit
            BinaryTreeNode n = root;
            while (n != null) {
                if (data[i] < n.value) {
                    if (n.left == null) {
                        n.left(data[i]);
                        break;
                    }
                    n = n.left;
                } else {
                    if (n.right == null) {
                        n.right(data[i]);
                        break;
                    }
                    n = n.right;
                }
            }
        }
        var result =  StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                        new BSTPostorderIterator(root), Spliterator.ORDERED)
                , false)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println(result);
    }



    static class BSTPostorderIterator implements Iterator<Integer> {
        private final Deque<BinaryTreeNode> stack;

        public BSTPostorderIterator(BinaryTreeNode root) {
            stack = new ArrayDeque<>();
            init(root);
        }

        private void init(BinaryTreeNode root) {
            while (root != null) {
                stack.push(root);
                root = root.left != null ? root.left : root.right;
            }
        }

        public Integer next() {
            BinaryTreeNode node = stack.pop();
            if (!stack.isEmpty()) {
                if (node == stack.peek().left) {
                    init(stack.peek().right);
                }
            }
            return node.value;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }
}
