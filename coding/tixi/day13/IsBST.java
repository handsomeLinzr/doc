package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 3.判断是否搜索二叉树（每棵树的左子树都小于头节点，右子树都大于头结点）
 * @author linzherong
 * @date 2025/5/5 20:57
 */
public class IsBST {

    public static boolean isBST(TreeNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    static class Info {
        public boolean isBST;
        public int max;
        public int min;
        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info left = process(node.left);
        Info right = process(node.right);
        boolean isBST = true;
        int max = node.value;
        int min = node.value;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
            isBST = left.isBST;
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
            isBST = isBST && right.isBST;
        }
        isBST = isBST && (left == null || node.value > left.max) && (right == null || node.value < right.min);
        return new Info(isBST, max, min);
    }

    public static boolean isBST1(TreeNode head) {
        if (head == null) {
            return true;
        }
        ArrayList<TreeNode> nodes = new ArrayList<>();
        in(head, nodes);
        for (int i = 0; i < nodes.size()-1; i++) {
            if (nodes.get(i).value >= nodes.get(i+1).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(TreeNode node, ArrayList<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        in(node.left, nodes);
        nodes.add(node);
        in(node.right, nodes);
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode node = new TreeNode((int)(Math.random()*maxValue) + 1);
        node.left = generate(level+1, maxLevel, maxValue);
        node.right = generate(level+1, maxLevel, maxValue);
        return node;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode head = generateRandomBST(5, 150);
            if (isBST1(head) != isBST(head)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }


}
