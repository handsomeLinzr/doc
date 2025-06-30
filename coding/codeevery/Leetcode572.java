package coding.codeevery;

import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/6/30 09:33
 */
public class Leetcode572 {

    // 常规解法
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        return process(root, subRoot);
    }

    public Boolean process(TreeNode node, TreeNode compare) {
        if (node == null && compare == null) {
            return true;
        }
        if (node == null || compare == null) {
            return false;
        }
        return (isSame(node, compare))
                || process(node.left, compare)
                || process(node.right, compare);
    }
    public Boolean isSame(TreeNode node, TreeNode compare) {
        if (node == null && compare == null) {
            return true;
        }
        if (node == null || compare == null || node.val != compare.val) {
            return false;
        }
        return isSame(node.left, compare.left) && isSame(node.right, compare.right);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // kmp算法实现
    public boolean isSubtreeKMP(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        String rootOrder = getPreOrder(root);
        String subOrder = getPreOrder(subRoot);
        return rootOrder.contains(subOrder);
    }
    public String getPreOrder(TreeNode node) {
        String result = "#" + node.val;
        if (Objects.nonNull(node.left)) {
            result += getPreOrder(node.left);
        } else {
            result += "#null";
        }
        if (Objects.nonNull(node.right)) {
            result += getPreOrder(node.right);
        } else {
            result += "#null";
        }
        return result;
    }


    public static void main(String[] args) {
        Leetcode572 leetcode572 = new Leetcode572();
        System.out.println(leetcode572.isSubtreeKMP(new TreeNode(1, new TreeNode(1), null), new TreeNode(1)));
    }

}
