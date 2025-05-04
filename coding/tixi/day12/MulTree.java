package coding.tixi.day12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
        TreeNode head = new TreeNode(node.val);
        build(node.children, head);
        return head;
    }
    private TreeNode build(List<Node> nodes, TreeNode node) {
        if (nodes == null || nodes.isEmpty()) {
            return node;
        }
        TreeNode treeNode = null;
        TreeNode h = null;
        for (Node mNodes : nodes) {
            TreeNode curNode = new TreeNode(mNodes.val);
            TreeNode n = build(mNodes.children, curNode);
            if (h == null) {
                h = n;
            }
            if (treeNode != null) {
                treeNode.right = n;
            }
            treeNode = n;
        }
        node.left = h;
        return node;
    }


    /**
     * 反序列化
     * @param treeNode
     * @return
     */
    public Node decode(TreeNode treeNode) {
        Node h = new Node(treeNode.val);
        h.children = dBuild(treeNode.left);
        return h;
    }

    public List<Node> dBuild(TreeNode node) {
        List<Node> child = new ArrayList<>();
        while (node != null) {
            Node cur = new Node(node.val);
            cur.children = dBuild(node.left);
            child.add(cur);
            node = node.right;
        }
        return child;
    }


    public static void main(String[] args) {
        Node myNode = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        List<Node> c1 = new ArrayList<>();
        c1.add(node2);
        c1.add(node3);
        c1.add(node4);
        myNode.children = c1;
        List<Node> c2 = new ArrayList<>();
        c2.add(node5);
        c2.add(node6);
        c2.add(node7);
        node3.children = c2;
        List<Node> c3 = new ArrayList<>();
        c3.add(node8);
        node4.children = c3;

        MulTree mulTree = new MulTree();
        TreeNode ecode = mulTree.ecode(myNode);
        System.out.println(ecode);

        Node decode = mulTree.decode(ecode);
        System.out.println(decode);


    }

}
