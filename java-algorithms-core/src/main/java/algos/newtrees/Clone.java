package algos.newtrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Clone {

    static BinaryTreeNode[] nodes(BinaryTreeNode a, BinaryTreeNode b) {
        return new BinaryTreeNode[]{a, b};
    }

    static BinaryTreeNode clone_tree(BinaryTreeNode root) {
        if(root==null) return null;
        // Write your code here.
        BinaryTreeNode  root2 =new BinaryTreeNode(root.value);
        // Write your code here.
        Queue<BinaryTreeNode[]> q = new LinkedList<>();
        q.add(nodes(root, root2));

        while (!q.isEmpty()) {
            var nodes = q.remove();
            if (nodes[0].left != null) {
                nodes[1].left = new BinaryTreeNode(nodes[0].left.value);
                q.add(nodes(nodes[0].left, nodes[1].left));
            }
            if (nodes[0].right != null){
                nodes[1].right = new BinaryTreeNode(nodes[0].right.value);
                q.add(nodes(nodes[0].right, nodes[1].right));
            }

        }
        return root2;
    }
}
