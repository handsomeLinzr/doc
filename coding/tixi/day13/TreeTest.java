package coding.tixi.day13;

import coding.tixi.BinaryTreeNode;
import coding.tixi.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.给一个节点，判断是否完全二叉树（按层遍历，两种方式）
 *      1.不存在有右子树但无左子树的情况
 *      2.叶子节点后必须只有叶子节点
 * 2.判断是否平衡二叉树（每棵树的左右子树最大高度差不能大于1）
 * 3.判断是否搜索二叉树（每棵树的左子树都小于头节点，右子树都大于头结点）
 * 4.给定一颗二叉树头结点node，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 *    最大距离 =
 *          1. 左子树最大距离
 *          2. 右子树最大距离
 *          3. 左子树高度+右子树高度+1
 * 5.判断是否满二叉树（高度为n，节点数一定是 2^n - 1）
 * 6.获取最大的二叉搜索子树的节点
 *      x 做头结点    左是否BST  右是否BST   左最大   右最小   左size   右size
 *      x 不做头节点   左最大BST size    右最大BST size
 *      （左是否BST 和 右是否BST 可以通过   左size 是否等于 左最大BST size 和 右size 是否等于 右最大BST size 替代）
 * 7.给定一棵二叉树头结点，返回其中最大的二叉搜索子树头节点
 * 8.给定一棵二叉树头结点，还有两个节点，返回这两个节点的最低公共父节点（没有则返回null）
 * 9.多叉树的快乐值 (多叉数，其中每个节点都有一个happy值，父子节点不能同时存在，返回最大的happy和)
 *
 *
 * @author linzherong
 * @date 2025/3/31 13:08
 */
public class TreeTest {

    public static class CBTInfo {
        public boolean isCBT;
        public boolean isFull;
        public int h;

        public CBTInfo(boolean isCBT, boolean isFull, int h) {
            this.isCBT = isCBT;
            this.isFull = isFull;
            this.h = h;
        }
    }

    public boolean isCBT(BinaryTreeNode node) {
        if (node == null) {
            return true;
        }
        return processXBT(node).isCBT;
    }
    public CBTInfo processXBT(BinaryTreeNode node) {
        if (node == null) {
            return new CBTInfo(true, true, 0);
        }
        CBTInfo leftInfo = processXBT(node.left);
        CBTInfo rightInfo = processXBT(node.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.h == rightInfo.h;
        int h = Math.max(leftInfo.h, rightInfo.h) + 1;
        boolean isCBT = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.h - rightInfo.h <= 1) {
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.h == rightInfo.h) {
            isCBT = true;
        } else if (leftInfo.isCBT && leftInfo.isFull && leftInfo.h == rightInfo.h + 1) {
            isCBT = true;
        }
        return new CBTInfo(isCBT, isFull, h);
    }

    public static class BalanceInfo {
        public boolean isBalance;
        public int h;

        public BalanceInfo(boolean isBalance, int h) {
            this.isBalance = isBalance;
            this.h = h;
        }
    }

    public boolean isBalance(BinaryTreeNode node) {
        if (node == null) {
            return true;
        }
        return processBalance(node).isBalance;
    }
    public BalanceInfo processBalance(BinaryTreeNode node) {
        if (node == null) {
            return new BalanceInfo(true, 0);
        }
        BalanceInfo left = processBalance(node.left);
        BalanceInfo right = processBalance(node.right);
        boolean isBalance = left.isBalance && right.isBalance && Math.abs(left.h - right.h) <= 1;
        return new BalanceInfo(isBalance, Math.max(left.h, right.h) + 1);
    }

    public static class BSTInfo {
        public boolean isBST;
        public int max;
        public int min;

        public BSTInfo(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }
    public boolean isBST(BinaryTreeNode node) {
        if (node == null) {
            return true;
        }
        return processBST(node).isBST;
    }
    public BSTInfo processBST(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }
        BSTInfo left = processBST(node.left);
        BSTInfo right = processBST(node.right);
        boolean isBST = true;
        int min = node.value;
        int max = node.value;
        if (left != null) {
            min = Math.min(min, left.min);
            max = Math.max(max, left.max);
            if (!left.isBST || left.max >= node.value) {
                isBST = false;
            }
        }
        if (right != null) {
            min = Math.min(min, right.min);
            max = Math.max(max, right.max);
            if (!right.isBST || right.min <= node.value) {
                isBST = false;
            }
        }
        return new BSTInfo(isBST, max, min);
    }

    public static class MaxDistanceInfo {
        public int h;
        public int max;

        public MaxDistanceInfo(int h, int max) {
            this.h = h;
            this.max = max;
        }
    }
    public int maxDistance(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        return processMaxDistance(node).max;
    }
    public MaxDistanceInfo processMaxDistance(BinaryTreeNode node) {
        if (node == null) {
            return new MaxDistanceInfo(0, 0);
        }
        MaxDistanceInfo left = processMaxDistance(node.left);
        MaxDistanceInfo right = processMaxDistance(node.right);
        int h = Math.max(left.h, right.h) + 1;
        int max = Math.max(Math.max(left.max, right.max), left.h + right.h + 1);
        return new MaxDistanceInfo(h, max);
    }

    public static class FullBT {
        int h;
        int size;
        boolean isFullBT;
        public FullBT(int h, int size, boolean isFullBT) {
            this.h = h;
            this.size = size;
            this.isFullBT = isFullBT;
        }
    }
    public boolean isFullBT(BinaryTreeNode node) {
        return processFullBT(node).isFullBT;
    }
    public FullBT processFullBT(BinaryTreeNode node) {
        if (node == null) {
            return new FullBT(0, 0, true);
        }
        FullBT left = processFullBT(node.left);
        FullBT right = processFullBT(node.right);
        int h = Math.max(left.h, right.h) + 1;
        int size = left.size + right.size + 1;
        boolean isFullBST = size == Math.pow(2, h)-1;
        return new FullBT(h, size, isFullBST);
    }

    public static class MaxSubBST {
        public boolean isBST;
        public int maxSize;
        public int max;
        public int min;
        public MaxSubBST(boolean isBST, int maxSize, int max, int min) {
            this.isBST = isBST;
            this.maxSize = maxSize;
            this.max = max;
            this.min = min;
        }
    }
    public int maxSubBSTSize(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        return processMaxSubBST(node).maxSize;
    }
    public MaxSubBST processMaxSubBST(BinaryTreeNode node) {
        MaxSubBST left = node.left == null? null : processMaxSubBST(node.left);
        MaxSubBST right = node.right == null? null : processMaxSubBST(node.right);
        int maxSize = 0;
        int max = node.value;
        int min = node.value;
        boolean isBST = true;
        if (left != null) {
            isBST = left.isBST && left.max < node.value;
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
            maxSize = Math.max(maxSize, left.maxSize);
        }
        if (right != null) {
            isBST = isBST && right.isBST && right.min > node.value;
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
            maxSize = Math.max(maxSize, right.maxSize);
        }
        if (isBST) {
            maxSize = (left == null?0:left.maxSize) + (right == null?0:right.maxSize) + 1;
        }
        return new MaxSubBST(isBST, maxSize, max, min);
    }

    public static class MaxSubBSTInfo {
        BinaryTreeNode maxNode;
        int maxBSTSize;
        int max;
        int min;
        boolean isBST;
        public MaxSubBSTInfo(BinaryTreeNode maxNode, int max, int min, boolean isBST, int maxBSTSize) {
            this.maxNode = maxNode;
            this.max = max;
            this.min = min;
            this.isBST = isBST;
            this.maxBSTSize = maxBSTSize;
        }
    }
    public BinaryTreeNode maxSubBST(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }
        return processMaxSubBSTNode(node).maxNode;
    }
    public MaxSubBSTInfo processMaxSubBSTNode(BinaryTreeNode node) {
        MaxSubBSTInfo left = node.left == null? null : processMaxSubBSTNode(node.left);
        MaxSubBSTInfo right = node.right == null? null : processMaxSubBSTNode(node.right);
        int max = node.value;
        int min = node.value;
        boolean isBST = true;
        int maxBSTSize = 0;
        BinaryTreeNode maxNode = null;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.max(min, left.min);
            isBST = left.isBST && left.max < node.value;
            maxBSTSize = Math.max(maxBSTSize, left.maxBSTSize);
            maxNode = left.maxNode;
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.max(min, right.min);
            isBST = isBST && right.isBST && right.min > node.value;
            maxNode = maxBSTSize < right.maxBSTSize? right.maxNode:maxNode;
            maxBSTSize = Math.max(maxBSTSize, right.maxBSTSize);
        }
        if (isBST) {
            maxNode = node;
        }
        return new MaxSubBSTInfo(maxNode, max, min, isBST, maxBSTSize);
    }

    public static class LowerCommonInfo {
        public boolean findA;
        public boolean findB;
        public BinaryTreeNode lowerCommon;
        public LowerCommonInfo(BinaryTreeNode lowerCommon, boolean findA, boolean findB) {
            this.lowerCommon = lowerCommon;
            this.findA = findA;
            this.findB = findB;
        }
    }
    public BinaryTreeNode lowerCommon(BinaryTreeNode node, BinaryTreeNode a, BinaryTreeNode b) {
        if (node == null || a == null || b == null) {
            return null;
        }
        return processLowerCommon(node, a, b).lowerCommon;
    }
    public LowerCommonInfo processLowerCommon(BinaryTreeNode node, BinaryTreeNode a, BinaryTreeNode b) {
        if (node == null) {
            return new LowerCommonInfo(null, false, false);
        }
        LowerCommonInfo left = processLowerCommon(node.left, a, b);
        LowerCommonInfo right = processLowerCommon(node.right, a, b);
        boolean findA = left.findA || right.findA || node == a;
        boolean findB = left.findB || right.findB || node == b;
        BinaryTreeNode ans = null;
        if (left.findA && left.findB) {
            ans = left.lowerCommon;
        } else if (right.findA && right.findB) {
            ans = right.lowerCommon;
        } else if (findA && findB) {
            ans = node;
        }
        return new LowerCommonInfo(ans, findA, findB);
    }

    public static class Employee {
        public int happy;
        public List<Employee> nexts;
        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }
    // Employee 多叉树
    public static int maxHappy(Employee boss) {
        return process(boss, 0, true);
    }

    public static int process(Employee employee, int happy, boolean yes) {
        // 不选当前
        if (employee == null) {
            return happy;
        }
        int max1 = happy;
        for (Employee next : employee.nexts) {
            max1 = process(next, max1, true);
        }
        if (yes) {
            int max2 = happy + employee.happy;
            for (Employee next : employee.nexts) {
                max2 = process(next, max2, false);
            }
            max1 = Math.max(max1, max2);
        }
        return max1;
    }
    
    public static class EmployeeHappyInfo {
        int yes;
        int no;
        public EmployeeHappyInfo(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }
    // Employee 多叉树
    public static int maxHappy2(Employee boss) {
        EmployeeHappyInfo info = processHappy(boss);
        return Math.max(info.yes, info.no);
    }
    public static EmployeeHappyInfo processHappy(Employee employee) {
        if (employee == null) {
            return new EmployeeHappyInfo(0, 0);
        }
        int yes = employee.happy;
        int no = 0;
        for (Employee next : employee.nexts) {
            EmployeeHappyInfo info = processHappy(next);
            yes += info.no;
            no += Math.max(info.yes, info.no);
        }
        return new EmployeeHappyInfo(yes, no);
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


    public static Employee generalBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.2) {
            return null;
        }
        Employee boss = new Employee((int)(Math.random() * maxHappy + 1));
        boss.nexts = generalNext(1, maxLevel, maxNexts, maxHappy);
        return boss;
    }
    public static List<Employee> generalNext(int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return new ArrayList<>(0);
        }
        int size = (int) (Math.random() * maxNexts + 1);
        List<Employee> nexts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Employee next = new Employee((int)(Math.random() * maxHappy + 1));
            next.nexts = generalNext(level+1, maxLevel, maxNexts, maxHappy);
            nexts.add(next);
        }
        return nexts;
    }

    public static void main(String[] args) {
//        TreeTest t = new TreeTest();
//        BinaryTreeNode a = new BinaryTreeNode(12);
//        a.left = new BinaryTreeNode(10);
//        a.left.left = new BinaryTreeNode(5);
//        a.left.right = new BinaryTreeNode(8);
//        a.right = new BinaryTreeNode(15);
//        a.right.left = new BinaryTreeNode(13);
//        a.right.right = new BinaryTreeNode(16);
//        System.out.println(t.maxSubBSTSize(a));


        for (int i = 0; i < 100_000; i++) {
            Employee boss = generalBoss(5, 5, 20);
            int ans1 = maxHappy(boss);
            int ans3 = maxHappy2(boss);
            if (ans1 != ans3) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
        
        
    }

}
