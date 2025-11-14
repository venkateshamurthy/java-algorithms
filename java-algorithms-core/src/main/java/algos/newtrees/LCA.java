package algos.newtrees;

import java.util.Objects;
//https://aaronice.gitbook.io/lintcode/trees/lowest-common-ancestor-of-a-binary-tree
public class LCA {
    public static void main(String[] ar) {

        int[] data = new int[]{8, 5, 10, 2, 6, -1, -1, -1, -1, -1, 7, -1, -1};
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
        System.out.println(lowestCommonAncestor(root, 2, 10).value);
    }

    static BinaryTreeNode lowestCommonAncestor(BinaryTreeNode root, Integer p, Integer q) {
        return lowestCommonAncestor(root, new BinaryTreeNode(p), new BinaryTreeNode(q));
    }

    static BinaryTreeNode lowestCommonAncestor(BinaryTreeNode root, BinaryTreeNode p, BinaryTreeNode q) {
        if (root == null || Objects.equals(root.value, p.value) || Objects.equals(root.value, q.value)) return root;
        BinaryTreeNode left = lowestCommonAncestor(root.left, p, q);
        BinaryTreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
}
