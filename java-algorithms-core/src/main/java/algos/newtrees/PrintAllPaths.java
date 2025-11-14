package algos.newtrees;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintAllPaths {

    public static void main(String[] a) {
        BinaryTreeNode node = new BinaryTreeNode(100, null, new BinaryTreeNode(200));
        buildPath(node);
        System.out.println(result);
    }

    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    static void buildPath(BinaryTreeNode root) {
        Deque<BinaryTreeNode> slate = new ArrayDeque<>();
        slate.push(root);
        buildPath(root, slate);
    }

    static void buildPath(BinaryTreeNode node, Deque<BinaryTreeNode> slate) {
        if (node == null)
            return;

        if (node.left == null && node.right == null) {
            result.add(
                    Stream.of(0).collect(Collectors.toCollection(ArrayList::new)));
            ArrayList<Integer> res = slate.stream().map(n -> n.value)
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.reverse(res);
            result.add(res);
            return;
        }
        if (node.left != null) {
            slate.push(node.left);
            buildPath(node.left, slate);
            slate.pop();
        }
        if (node.right != null) {
            slate.push(node.right);
            buildPath(node.right, slate);
            slate.pop();
        }
    }
}
