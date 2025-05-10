package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 *  6.获取最大的二叉搜索子树的节点
 *  *      x 做头结点    左是否BST  右是否BST   左最大   右最小   左size   右size
 *  *      x 不做头节点   左最大BST size    右最大BST size
 *  *      （左是否BST 和 右是否BST 可以通过   左size 是否等于 左最大BST size 和 右size 是否等于 右最大BST size 替代）
 *
 * @author linzherong
 * @date 2025/5/6 22:43
 */
public class MaxBST {

    public static int maxBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSize;
    }
    static class Info {
        public TreeNode maxBST;
        public boolean isBST;
        public int maxBSTSize;
        public int max;
        public int min;
        public Info(boolean isBST, int maxBSTSize, int max, int min) {
            this.isBST = isBST;
            this.maxBSTSize = maxBSTSize;
            this.max = max;
            this.min = min;
        }
        public Info(TreeNode maxBST, boolean isBST, int maxBSTSize, int max, int min) {
            this.maxBST = maxBST;
            this.isBST = isBST;
            this.maxBSTSize = maxBSTSize;
            this.max = max;
            this.min = min;
        }
    }
    public static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info left = process(node.left);
        Info right = process(node.right);
        boolean isBST = true;
        int maxBSTSize = 0;
        int max = node.value;
        int min = node.value;

        if (left != null) {
            isBST = left.isBST;
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
            maxBSTSize = Math.max(maxBSTSize, left.maxBSTSize);
        }
        if (right != null) {
            isBST = isBST && right.isBST;
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
            maxBSTSize = Math.max(maxBSTSize, right.maxBSTSize);
        }
        isBST = isBST && (left == null || node.value > left.max) && (right == null || node.value < right.min);
        maxBSTSize = isBST? (left == null? 0: left.maxBSTSize)+(right == null? 0: right.maxBSTSize)+1:maxBSTSize;
        return new Info(isBST, maxBSTSize, max, min);
    }
    public static int maxBSTSize1(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int h;
        if ((h = getBSTSize(head)) != 0) {
            return h;
        }
        return Math.max(maxBSTSize1(head.left), maxBSTSize1(head.right));
    }
    public static int getBSTSize(TreeNode node) {
        ArrayList<TreeNode> list = new ArrayList<>();
        fill(node, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).value >= list.get(i).value) {
                return 0;
            }
        }
        return list.size();
    }
    public static void fill(TreeNode node, ArrayList<TreeNode> list) {
        if (node == null) {
            return;
        }
        fill(node.left, list);
        list.add(node);
        fill(node.right, list);
    }


    public static TreeNode maxBST(TreeNode head) {
        if (head == null) {
            return null;
        }
        return process1(head).maxBST;
    }
    public static Info process1(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode maxBST = null;
        boolean isBST = true;
        int maxBSTSize = 0;
        int max = node.value;
        int min = node.value;

        Info left = process1(node.left);
        Info right = process1(node.right);
        if (left != null) {
            maxBST = left.maxBST;
            isBST = left.isBST;
            maxBSTSize = Math.max(maxBSTSize, left.maxBSTSize);
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            maxBST = maxBSTSize < right.maxBSTSize? right.maxBST : maxBST;
            isBST = isBST && right.isBST;
            maxBSTSize = Math.max(maxBSTSize, right.maxBSTSize);
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);

        }
        int leftMaxBSTSize = left == null? 0 : left.maxBSTSize;
        int rightMaxBSTSize = right == null? 0 : right.maxBSTSize;
        isBST = isBST && (left == null || left.max < node.value) && (right == null || right.min > node.value);
        maxBSTSize = isBST? leftMaxBSTSize+rightMaxBSTSize+1 : maxBSTSize;
        maxBST = isBST ? node : maxBST;
        return new Info(maxBST, isBST, maxBSTSize, max, min);
    }

    public static TreeNode maxBST1(TreeNode head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        return getMaxBST1(head).maxBST;
    }

    public static Info getMaxBST1(TreeNode node) {
        if (node == null) {
            return new Info(null, true, 0, 0, 0);
        }
        int h = getBSTSize(node);
        if (h > 0) {
            return new Info(node, true, h, 0, 0);
        }
        Info left = getMaxBST1(node.left);
        Info right = getMaxBST1(node.right);
        TreeNode maxBST = left.maxBSTSize < right.maxBSTSize? right.maxBST : left.maxBST;
        h = Math.max(left.maxBSTSize, right.maxBSTSize);
        return new Info(maxBST, true, h, 0, 0);
    }



    public static TreeNode generalRandomTreeNode(int maxLevel, int maxValue) {
        return general(1, maxLevel, maxValue);
    }
    public static TreeNode general(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode node = new TreeNode((int)(Math.random() * maxValue) + 1);
        node.left = general(level+1, maxLevel, maxValue);
        node.right = general(level+1, maxLevel, maxValue);
        return node;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode node = generalRandomTreeNode(5, 150);
            if (maxBSTSize1(node) != maxBSTSize(node)) {
                System.out.println("Oops");
            }
            if (maxBST1(node) != maxBST(node)) {
                System.out.println("Oops2");
            }
        }
        System.out.println("finish");
    }

}
