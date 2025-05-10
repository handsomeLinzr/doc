package coding.tixi.day13;

import coding.tixi.BinaryTreeNode;
import coding.tixi.day11.BinaryTree;

/**
 * 1.给一个节点，判断是否完全二叉树（按层遍历，两种方式）
 *      1.不存在有右子树但无左子树的情况
 *      2.叶子节点后必须只有叶子节点
 * 2.判断是否平衡二叉树（每棵树的左右子树最大高度差不能大于1）
 * 3.判断是否搜索二叉树（每棵树的左子树都小于头节点，右子树都大于头结点）
 * 4.给定一颗二叉树头结点node，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 *    最大距离 =
 *          1. 左子树做大距离
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

    public boolean isCBT(BinaryTreeNode node) {
        return false;
    }

    public boolean isBalance(BinaryTreeNode node) {
        return false;
    }

    public boolean isBST(BinaryTreeNode node) {
        return false;
    }

    public int maxDistance(BinaryTreeNode node) {
        return 0;
    }

    public boolean isFullBT(BinaryTreeNode node) {
        return false;
    }

    public int maxSubBSTSize(BinaryTreeNode node) {
        return 0;
    }

    public BinaryTreeNode maxSubBST(BinaryTreeNode node) {
        return null;
    }

    public BinaryTreeNode lowerCommon(BinaryTreeNode node, BinaryTreeNode a, BinaryTreeNode b) {
        return null;
    }

    // Employee 多叉树
    public int maxHappy() {
        return 0;
    }
}
