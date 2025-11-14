package algos.newtrees;

import java.util.ArrayList;
import java.util.Arrays;

public class BuildBST {
    //For your reference:
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
    static BinaryTreeNode build_a_bst(ArrayList<Integer> values) {
        // Write your code here.
        if (values == null || values.size() == 0)
            return null;

        final BinaryTreeNode root = new BinaryTreeNode(values.get(0));

        if (values.size() == 1) {
            return root;
        }

        BinaryTreeNode curr = root, prev=curr;

        for(Integer val : values.subList(1, values.size())) {
            while( curr != null) {
                prev = curr;
                if( val < curr.value) curr = curr.left;
                else if (val > curr.value) curr= curr.right;
            }
            BinaryTreeNode valNode = new BinaryTreeNode(val);;
            if (val < prev.value) prev.left = valNode;
            else if (val > prev.value) prev.right = valNode;
            curr = root; //to make search start again from root
        }
        return root;
    }


    public static void main(String[] a){

        ArrayList<Integer> arr = new ArrayList<>(){{
            addAll(Arrays.asList(7, 5, 9, 13,2, 4));
        }};
        var node = build_a_bst(arr)   ;

    }

}
