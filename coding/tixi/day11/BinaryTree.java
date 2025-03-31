package coding.tixi.day11;

import coding.tixi.BinaryTreeNode;

import java.util.Objects;
import java.util.Stack;

/**
 * 1.递归序，每个节点都有3次到自己
 * 先序遍历，X左边，和后序遍历，X后边，两个区域相交，得到的是 X 的头节点
 * 2.非递归方式实现先序、中序、后序（栈实现）
 *      先序遍历，打印后放入栈（先右后左）
 *      后续遍历，两个栈，按中右左顺序放入第二个栈，后续打印
 *      中序遍历，
 *
 * @author linzherong
 * @date 2025/3/30 17:14
 */
public class BinaryTree {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        BinaryTreeNode head = new BinaryTreeNode(1);
        BinaryTreeNode left = new BinaryTreeNode(2, new BinaryTreeNode(3), new BinaryTreeNode(4));
        BinaryTreeNode right = new BinaryTreeNode(5, new BinaryTreeNode(6), new BinaryTreeNode(7));
        head.left = left;
        head.right = right;

    }

    /**
     * 递归序
     * @param node
     */
    public void processOrder(BinaryTreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        System.out.print(node.value + " ");
        processOrder(node.left);
        System.out.print(node.value + " ");
        processOrder(node.right);
        System.out.print(node.value + " ");
    }

}
