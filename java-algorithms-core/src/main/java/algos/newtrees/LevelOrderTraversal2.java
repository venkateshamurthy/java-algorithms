package algos.newtrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal2 {

    public static void main (String[] ar) {

    }
    static class BinaryTreeNode {
        Integer value;
        BinaryTreeNode left;
        BinaryTreeNode right;
        BinaryTreeNode next_right;
        BinaryTreeNode(Integer value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.next_right = null;
        }
    }
    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    static BinaryTreeNode level_order_traversal(BinaryTreeNode root) {
        // Write your code here.
        Queue<BinaryTreeNode> q = new LinkedList<>();
        q.add(root);
        ArrayList<BinaryTreeNode> slate = new ArrayList<>();
        while( ! q.isEmpty()) {
            int size = q.size();
            for (int i=0; i<size; i++){
                var node = q.remove();
                slate.add(node);
                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            for(int i=1;i<slate.size();i++)
                slate.get(i-1).next_right=slate.get(i);
            slate.clear();
        }
        return root;
    }
}
