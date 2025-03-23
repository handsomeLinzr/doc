package coding.xin;

import java.util.*;

/**
 * 107 节点遍历
 *     是否平衡二叉树（左右都是平衡树，且左右树高度差最大为1）
 *  98   是否搜索二叉树（左节点都小于自己，右节点都大于自己）
 *       平衡搜索二叉树
 *  112  路径和，路径为头到叶子节点
 *  113  达标路径和
 * @author linzherong
 * @date 2025/3/21 13:10
 */
public class Day7 {

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        TreeNode node = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        List<List<Integer>> lists = day7.levelOrderBottom(node);
        System.out.println(lists);
        day7.inorder(node);

        TreeNode node1 = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3));
        List<List<Integer>> lists1 = day7.pathSum(node1, 8);
        System.out.println(lists1);


    }

    /**
     * 节点遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (Objects.nonNull(poll)) {
                    list.add(poll.val);
                    queue.add(poll.left);
                    queue.add(poll.right);
                }
            }
            if (list.size() > 0) {
                result.add(0, list);
            }
        }
        return result;
    }

    public boolean isBalanced(TreeNode root) {
        return getInfo(root).isBalance;
    }

    public Info getInfo(TreeNode node) {
        if (node == null) {
            return new Info(0, true);
        }
        Info lInfo = getInfo(node.left);
        Info rInfo = getInfo(node.right);
        int length = Math.max(lInfo.length, rInfo.length) + 1;
        boolean isBalance = lInfo.isBalance && rInfo.isBalance && Math.abs(lInfo.length - rInfo.length) < 2;
        return new Info(length, isBalance);
    }

    static class Info {
        public int length;
        public boolean isBalance;
        public Info() {}
        public Info(int length, boolean isBalance) {
            this.length = length;
            this.isBalance = isBalance;
        }
    }

    public boolean isBalanced2(TreeNode root) {
        return depth(root) >= 0;
    }

    /**
     * 深度
     */
    public int depth(TreeNode node) {
        if (Objects.isNull(node)) {
            return 0;
        }
        int lDepth = depth(node.left);
        int rDepth = depth(node.right);
        if (lDepth == -1 || rDepth == -1 || Math.abs(lDepth - rDepth) > 1) {
            return -1;
        }
        return Math.max(lDepth, rDepth) + 1;
    }

    public void inorder(TreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        inorder(node.left);
        System.out.println(node.val);
        inorder(node.right);
    }

    public boolean isValidBST(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }
        return processBST(root).isBST;
    }

    public BSTInfo processBST(TreeNode node) {
        BSTInfo l = null;
        BSTInfo r = null;
        int max = node.val;
        int min = node.val;
        if (Objects.nonNull(node.left)) {
            l = processBST(node.left);
            max = Math.max(max, l.max);
            min = Math.min(min, l.min);
        }
        if (Objects.nonNull(node.right)) {
            r = processBST(node.right);
            max = Math.max(max, r.max);
            min = Math.min(min, r.min);
        }
        boolean isBST = (Objects.isNull(l) || (l.isBST && l.max < node.val)) && (Objects.isNull(r) || (r.isBST && r.min > node.val));
        return new BSTInfo(min, max, isBST);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return false;
        }
        return processHasPathSum(root, 0, targetSum);
    }

    public boolean processHasPathSum(TreeNode node, int sum, int targetSum) {
        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return sum == (targetSum - node.val);
        }
        if (Objects.nonNull(node.left) && processHasPathSum(node.left, sum + node.val, targetSum)) {
            return true;
        }
        if (Objects.nonNull(node.right) && processHasPathSum(node.right, sum + node.val, targetSum)) {
            return true;
        }
        return false;
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (Objects.isNull(root)) {
            return result;
        }
        List<Integer> list = new ArrayList<>();
        process(result, list, root, 0, targetSum);
        return result;
    }

    public void process(List<List<Integer>> result, List<Integer> curList, TreeNode node, int preSum, int targetSum) {
        curList.add(node.val);
        preSum += node.val;
        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            if (preSum == targetSum) {
                result.add(copyList(curList));
            }
        }
        if (Objects.nonNull(node.left)) {
            process(result, curList, node.left, preSum, targetSum);
        }
        if (Objects.nonNull(node.right)) {
            process(result, curList, node.right, preSum, targetSum);
        }
        // 移除最后一个
        curList.remove(curList.size()-1);
    }

    public List<Integer> copyList(List<Integer> list) {
        List<Integer> copy = new ArrayList<>(list.size());
        copy.addAll(list);
        return copy;
    }


    static class BSTInfo {
        int min;
        int max;
        boolean isBST;

        public BSTInfo(int min, int max, boolean isBST) {
            this.min = min;
            this.max = max;
            this.isBST = isBST;
        }
    }

}
