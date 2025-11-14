package algos.newtrees;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class InOrderIterator {

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
        var result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                                new BSTInorderIterator(root), Spliterator.ORDERED)
                        , false)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println(result);
    }


    static class BSTInorderIterator implements Iterator<Integer> {
        private final Stack<BinaryTreeNode> stack = new Stack<>();
        private final BinaryTreeNode root;
        private BinaryTreeNode nextNode;

        public BSTInorderIterator(BinaryTreeNode root) {
            this.root = root;
            nextNode = root;
        }
        public Integer next() {
            Integer result = null;
            while (nextNode != null) {
                stack.push(nextNode);
                nextNode = nextNode.left;
            }

            nextNode = stack.isEmpty()?null:stack.pop();
            if (nextNode!=null) {
                result = nextNode.value;
                nextNode = nextNode.right;
            }
            return result;
        }

        public boolean hasNext() {
            return nextNode != null || stack.size() > 0;
        }
    }
}
