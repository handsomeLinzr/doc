package coding.tixi.day13;

import coding.tixi.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 4.给定一颗二叉树头结点node，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 *
 * @author linzherong
 * @date 2025/5/5 21:29
 */
public class MaxDistance {

    public static int maxDistance(TreeNode head) {
        return process(head).max;
    }
    // 最大距离在左边
    // 最大距离在右边
    // 最大记录经过最中间节点 = 左高度 + 右高度 + 1
    static class Info {
        public int h;
        public int max;
        public Info(int h, int max) {
            this.h = h;
            this.max = max;
        }
    }
    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }
        int h;
        int max;
        Info left = process(node.left);
        Info right = process(node.right);
        h = Math.max(left.h, right.h) + 1;
        max = Math.max(Math.max(left.max, right.max), left.h+right.h+1);
        return new Info(h, max);
    }

    public static int maxDistance1(TreeNode node) {
        if (node == null) {
            return 0;
        }
        ArrayList<TreeNode> nodes = new ArrayList<>();
        fillNodes(node, nodes);
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(node, null);
        fillParent(node, parentMap);
        int max = 0;
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i; j < nodes.size(); j++) {
                max = Math.max(max, getDistance(parentMap, nodes.get(i), nodes.get(j)));
            }
        }
        return max;
    }
    public static void fillNodes(TreeNode node, ArrayList<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(node);
        fillNodes(node.left, nodes);
        fillNodes(node.right, nodes);
    }
    public static void fillParent(TreeNode node, Map<TreeNode, TreeNode> parentMap) {
        if (node.left != null) {
            parentMap.put(node.left, node);
            fillParent(node.left, parentMap);
        }
        if (node.right != null) {
            parentMap.put(node.right, node);
            fillParent(node.right, parentMap);
        }
    }
    public static int getDistance(Map<TreeNode, TreeNode> parentMap, TreeNode a, TreeNode b) {
        HashSet<TreeNode> nSet = new HashSet<>();
        // 自身存入
        nSet.add(a);
        TreeNode cur = a;
        // 到头结点前一直循环处理
        while (parentMap.get(cur) != null) {
            TreeNode p = parentMap.get(cur);
            nSet.add(p);
            cur = p;
        }
        cur = b;
        while (!nSet.contains(cur) && cur != null) {
            cur = parentMap.get(cur);
        }
        if (cur == null) {
            return 0;
        }
        int distance = 1;
        // cur 为相交点
        while (a != cur) {
            a = parentMap.get(a);
            distance++;
        }
        while (b != cur) {
            b = parentMap.get(b);
            distance++;
        }
        return distance;
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
    
    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TreeNode head = generalRandomTreeNode(5, 150);
            if (maxDistance(head) != maxDistance1(head)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }


}
