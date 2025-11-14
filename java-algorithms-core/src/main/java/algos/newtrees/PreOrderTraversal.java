package algos.newtrees;

import java.util.ArrayList;
import java.util.Iterator;

public class PreOrderTraversal {
    static ArrayList<Integer> result = new ArrayList<>();
    static void helpwr(BinaryTreeNode node) {
        result.add(node.value);
        if(node.left!=null) helpwr(node.left);
        if(node.right!=null) helpwr(node.right);
    }
}

