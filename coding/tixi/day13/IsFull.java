package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.LinkedList;

/**
 * 判断是否是满二叉树
 * @author linzherong
 * @date 2025/5/6 22:25
 */
public class IsFull {

    public static boolean isFull(TreeNode head) {
        return process(head).isFull;
    }

    static class Info {
        public int h;
        public boolean isFull;
        public Info(int h, boolean isFull) {
            this.h = h;
            this.isFull = isFull;
        }
    }
    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, true);
        }
        int h;
        boolean full;
        Info left = process(node.left);
        Info right = process(node.right);
        h = Math.max(left.h, right.h) + 1;
        full = left.isFull && right.isFull && left.h == right.h;
        return new Info(h, full);
    }

    public static boolean isFull1(TreeNode node) {
        if (node == null) {
            return true;
        }
        int height = 0;
        int nodes = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            height++;
            int size = queue.size();
            nodes = nodes+size;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
        }
        return nodes == (1<<height)-1;
    }

    public static TreeNode generalTreeNode(int maxLevel, int maxValue) {
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

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode head = generalTreeNode(5, 150);
            if (isFull1(head) != isFull(head)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
