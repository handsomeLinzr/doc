package coding.tixi.day12;

import java.util.List;

/**
 * leetcode 多叉数转二叉数
 * @author linzherong
 * @date 2025/3/30 22:46
 */
public class MulTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node(){}

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {}
        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 序列化
     * @param node
     * @return
     */
    public TreeNode ecode(Node node) {
        return null;
    }

    /**
     * 反序列化
     * @param treeNode
     * @return
     */
    public Node decode(TreeNode treeNode) {
        return null;
    }

}
