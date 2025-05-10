package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.LinkedList;

/**
 *  * 1.给一个节点，判断是否完全二叉树（按层遍历，两种方式）
 *  *      1.不存在有右子树但无左子树的情况
 *  *      2.叶子节点后必须只有叶子节点
 * @author linzherong
 * @date 2025/5/5 19:29
 */
public class IsCBT {

    public static boolean isCBT1(TreeNode node) {
        if (node == null) {
            return true;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if (poll.left == null && poll.right != null) {
                return false;
            }
            if (leaf && (poll.left != null || poll.right != null)) {
                return false;
            }
            if (poll.left == null || poll.right == null) {
                leaf = true;
            }
            if (poll.left != null) {
                queue.add(poll.left);
            }
            if (poll.right != null) {
                queue.add(poll.right);
            }
        }
        return true;
    }


    public static boolean isCBT2(TreeNode node) {
        return process(node).isCBT;
    }
    // 1.左右都是满二叉树，左高度 = 右高度 或者 左高度 = 右高度 + 1
    // 2.左完全二叉树，右满二叉树，左高度 = 右高度 + 1
    // 3.左满二叉树，右完全二叉树，左高度 = 右高度
    static class Info {
        public int h;
        public boolean isFull;
        public boolean isCBT;

        public Info(int h, boolean isFull, boolean isCBT) {
            this.h = h;
            this.isFull = isFull;
            this.isCBT = isCBT;
        }
    }
    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, true, true);
        }
        Info left = process(node.left);
        Info right = process(node.right);
        int h = Math.max(left.h, right.h) + 1;
        boolean isFull = left.isFull && right.isFull && left.h == right.h;
        boolean isCBT = false;
        // 判断条件
        if (left.isFull && right.isFull && left.h == right.h) {
            isCBT = true;
        } else if (left.isFull && right.isFull && left.h == right.h + 1) {
            isCBT = true;
        } else if (left.isCBT && right.isFull && left.h == right.h + 1) {
            isCBT = true;
        } else if (left.isFull && right.isCBT && left.h == right.h) {
            isCBT = true;
        }
        return new Info(h, isFull, isCBT);
    }

    // for test
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode treeNode = generateRandomBST(5, 100);
            if (isCBT1(treeNode) != isCBT2(treeNode)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish");
    }

}
