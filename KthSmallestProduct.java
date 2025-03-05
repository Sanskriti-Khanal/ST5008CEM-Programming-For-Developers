class BSTNode {
    int product;
    BSTNode left, right;

    public BSTNode(int i, int j, int product) {
        this.product = product;
        this.left = this.right = null;
    }
}

class BST {
    private BSTNode root;
    private int count;
    private int kthSmallestValue;

    public BST() {
        this.root = null;
        this.count = 0;
        this.kthSmallestValue = -1;
    }

    public void insert(int i, int j, int product) {
        root = insertRec(root, i, j, product);
    }

    private BSTNode insertRec(BSTNode root, int i, int j, int product) {
        if (root == null) {
            return new BSTNode(i, j, product);
        }
        if (product < root.product) {
            root.left = insertRec(root.left, i, j, product);
        } else {
            root.right = insertRec(root.right, i, j, product);
        }
        return root;
    }

    public int kthSmallest(int k) {
        count = 0;
        kthSmallestValue = -1;
        inOrderTraversal(root, k);
        return kthSmallestValue;
    }

    private void inOrderTraversal(BSTNode root, int k) {
        if (root == null || count >= k) {
            return;
        }
        inOrderTraversal(root.left, k);
        count++;
        if (count == k) {
            kthSmallestValue = root.product;
            return;
        }
        inOrderTraversal(root.right, k);
    }
}

public class KthSmallestProduct {
    public static int findKthSmallestProduct(int[] returns1, int[] returns2, int k) {
        BST bst = new BST();
        for (int i = 0; i < returns1.length; i++) {
            for (int j = 0; j < returns2.length; j++) {
                bst.insert(i, j, returns1[i] * returns2[j]);
            }
        }
        return bst.kthSmallest(k);
    }

    public static void main(String[] args) {
        int[] returns1 = {1, 3, 5};
        int[] returns2 = {2, 4, 6};
        int k = 4;
        System.out.println("The " + k + "th smallest product is: " + findKthSmallestProduct(returns1, returns2, k));
    }
}
