package algos.newtrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal {

    public static void main (String[] ar) {

    }
    static class BinaryTreeNode {
        Integer value;
        BinaryTreeNode left;
        BinaryTreeNode right;

        BinaryTreeNode(Integer value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> level_order_traversal(BinaryTreeNode root) {
        // Write your code here.
        Queue<BinaryTreeNode> q = new LinkedList<>();
        q.add(root);;
        ArrayList<Integer> slate = new ArrayList<>();
        while( ! q.isEmpty()) {
            int size = q.size();
            for (int i=0; i<size; i++){
                var node = q.remove();
                slate.add(node.value);
                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            result.add(new ArrayList<>(slate));
            slate.clear();
        }
        return result;
    }
}
