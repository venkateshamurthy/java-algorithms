package algos.newtrees;

import java.util.*;

public class ReverseLevelOrderTraversal {

    public static void main(String[] a){
        //8, 4, 12, 2, 6,
        BinaryTreeNode root = BinaryTreeNode.fromSortedArray(0,14,1,2, 3,4,5,6,7,8,9,10,11,12,13,14,15);
        reverseLevelOrderTraversal(root);
        Collections.reverse(result);
        System.out.println(result);
    }

    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    static ArrayList<ArrayList<Integer>> reverse_level_order_traversal(BinaryTreeNode root) {
        // Write your code here.
        if (root != null) {
            helper(root, 0);
            Collections.reverse(result);
        }
        return result;
    }

    static void reverseLevelOrderTraversal(BinaryTreeNode root) {
        Queue<BinaryTreeNode> queue = new ArrayDeque<>();
        Deque<Integer> stack = new ArrayDeque<>();
        queue.add(root);

        BinaryTreeNode curr;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0;i<size;i++) {
                curr = queue.poll();
                stack.push(curr.value);
                if (curr.right != null) queue.add(curr.right);
                if (curr.left != null) queue.add(curr.left);
            }
            var l = new ArrayList<>(stack);
            //Collections.reverse(l);
            result.add(l);
            stack.clear();
        }
    }
    static void levelOrderTraversal(BinaryTreeNode root) {
        Queue<BinaryTreeNode> queue = new ArrayDeque<>();
        List<Integer> slate = new ArrayList<>();
        queue.add(root);

        BinaryTreeNode curr;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0;i<size;i++) {
                curr = queue.poll();
                slate.add(curr.value);
                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
            }
            result.add(new ArrayList<>(slate));
            slate.clear();
        }
    }
    //Kushbaronj@gmail.com
    static void helper(BinaryTreeNode node, int level) {
        if (result.size() == level)
            result.add(new ArrayList<Integer>());

        result.get(level).add(node.value);
        if (node.left != null)
            helper(node.left, level + 1);
        if (node.right != null)
            helper(node.right, level + 1);
    }

}
