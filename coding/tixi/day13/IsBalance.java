package coding.tixi.day13;

import coding.tixi.TreeNode;

/**
 *  * 2.判断是否平衡二叉树（每棵树的左右子树最大高度差不能大于1）
 * @author linzherong
 * @date 2025/5/5 20:42
 */
public class IsBalance {

    public static boolean isBalanced1(TreeNode head) {
        Ref<Boolean> ans = new Ref<>(true);
        process1(head, ans);
        return ans.get();
    }
    public static int process1(TreeNode node, Ref<Boolean> ans) {
        if (!ans.get() || node == null) {
            return 0;
        }
        int left = process1(node.left, ans);
        int right = process1(node.right, ans);

        ans.set(ans.get() && Math.abs(left - right) < 2);
        return Math.max(left, right) + 1;
    }

    static class Ref<T> {
        T value;
        public Ref(T value) { this.value = value; }
        public T get() { return value; }
        public void set(T value) { this.value = value; }
    }


    public static boolean isBalance(TreeNode head) {
        return process(head).isBalance;
    }

    // 左平衡  右平衡  左右高度差不大于1
    static class Info {
        public int h;
        public boolean isBalance;

        public Info(int h, boolean isBalance) {
            this.h = h;
            this.isBalance = isBalance;
        }
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, true);
        }
        Info left = process(node.left);
        Info right = process(node.right);
        int h = Math.max(left.h, right.h) + 1;
        boolean isBalance = left.isBalance && right.isBalance && Math.abs(left.h - right.h) < 2;
        return new Info(h, isBalance);
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }
    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.3) {
            return null;
        }
        TreeNode node = new TreeNode((int) (Math.random() * maxValue)+1);
        node.left = generate(level+1, maxLevel, maxValue);
        node.right = generate(level+1, maxLevel, maxValue);
        return node;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode head = generateRandomBST(5, 150);
            if (isBalanced1(head) != isBalance(head)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
