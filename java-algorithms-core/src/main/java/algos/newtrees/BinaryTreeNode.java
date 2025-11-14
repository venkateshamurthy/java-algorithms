package algos.newtrees;

class BinaryTreeNode {
    Integer value;
    BinaryTreeNode left, right;

    public BinaryTreeNode(Integer value, BinaryTreeNode left, BinaryTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public BinaryTreeNode(Integer value, Integer left, Integer right) {
        this.value = value;
        this.left = new BinaryTreeNode(left);
        this.right = new BinaryTreeNode(right);
    }

    public BinaryTreeNode(Integer value) {
        this.value = value;
        left = right = null;
    }

    public BinaryTreeNode left(Integer left) {
        this.left = new BinaryTreeNode(left);
        return this;
    }

    public BinaryTreeNode right(Integer right) {
        this.right = new BinaryTreeNode(right);
        return this;
    }

    public static BinaryTreeNode fromSortedArray(int start, int end, Integer... arr) {
        /* Base Case */
        if (start > end) {
            return null;
        }
        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        BinaryTreeNode node = new BinaryTreeNode(arr[mid]);

        /* Recursively construct the left subtree and make
         it left child of root */
        node.left = fromSortedArray(start, mid - 1, arr);

        /* Recursively construct the right subtree and make
         it right child of root */
        node.right = fromSortedArray(mid + 1, end, arr);

        return node;
    }

    public static BinaryTreeNode build(Integer... data) {
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
        return root;
    }
}