package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 8.给定一棵二叉树头结点，还有两个节点，返回这两个节点的最低公共父节点（没有则返回null）
 *
 * @author linzherong
 * @date 2025/5/7 21:14
 */
public class LowerCommon {

    public static TreeNode lowerCommon(TreeNode head, TreeNode a, TreeNode b) {
        return process(head, a, b).ans;
    }
    static class Info {
        public boolean findA;
        public boolean findB;
        public TreeNode ans;
        public Info(boolean findA, boolean findB, TreeNode ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }
    public static Info process(TreeNode node, TreeNode a, TreeNode b) {
        if (node == null) {
            return new Info(false, false, null);
        }
        Info left = process(node.left, a, b);
        Info right = process(node.right, a, b);
        boolean findA = (a == node) || left.findA || right.findA;
        boolean findB = (b == node) || left.findB || right.findB;
        TreeNode ans;
        if (left.findA && left.findB) {
            ans = left.ans;
        } else if (right.findA && right.findB) {
            ans = right.ans;
        } else {
            ans = findA && findB? node : null;
        }
        return new Info(findA, findB, ans);
    }

    public static TreeNode lowerCommon1(TreeNode head, TreeNode a, TreeNode b) {
        if (head == null || a == null || b == null) {
            return null;
        }
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParent(head, parentMap);
        HashSet<TreeNode> nodes = new HashSet<>();
        while (a != null) {
            nodes.add(a);
            a = parentMap.get(a);
        }
        TreeNode cur = b;
        while (!nodes.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }
    public static void fillParent(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
        if (head == null) {
            return;
        }
        parentMap.put(head.left, head);
        parentMap.put(head.right, head);
        fillParent(head.left, parentMap);
        fillParent(head.right, parentMap);
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
    public static TreeNode pickRandomOne(TreeNode node) {
        if (node == null) {
            return null;
        }
        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        fillParent(node, map);
        List<TreeNode> nodes = new ArrayList<>(map.keySet());
        return nodes.get((int) (Math.random() * nodes.size()));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode head = generalRandomTreeNode(2, 150);
            TreeNode a = pickRandomOne(head);
            TreeNode b = pickRandomOne(head);
            if (head != null && (lowerCommon1(head, a, b) != lowerCommon(head, a, b))) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
