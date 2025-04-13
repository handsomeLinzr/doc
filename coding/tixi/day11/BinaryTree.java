package coding.tixi.day11;

import coding.tixi.BinaryTreeNode;
import coding.tixi.ListNode;
import org.omg.PortableServer.POA;

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
        tree.processOrder(head);
        System.out.println();
        System.out.println("====================");
        tree.preSort1(head);
        System.out.println();
        System.out.println("===================");
        tree.preSort2(head);
        System.out.println();
        System.out.println("====================");
        tree.middleSort(head);
        System.out.println();
        System.out.println("====================");
        tree.postSor1(head);
        System.out.println();
        System.out.println("====================");
        tree.postSort2(head);


    }

    /**
     * 递归序 上 下左 下右，每个点有3次到自己
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

    public void preSort1(BinaryTreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        System.out.print(node.value + " ");
        preSort1(node.left);
        preSort1(node.right);
    }
    public void preSort2(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            BinaryTreeNode pop = stack.pop();
            if (Objects.isNull(pop)) {
                continue;
            }
            System.out.print(pop.value + " ");
            stack.push(pop.right);
            stack.push(pop.left);
        }
    }
    public void middleSort(BinaryTreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        middleSort(node.left);
        System.out.print(node.value + " ");
        middleSort(node.right);
    }
    public void postSor1(BinaryTreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        postSor1(node.left);
        postSor1(node.right);
        System.out.print(node.value + " ");
    }
    public void postSort2(BinaryTreeNode node) {
        Stack<BinaryTreeNode> stack = new Stack<>();
        Stack<BinaryTreeNode> stack1 = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            BinaryTreeNode pop = stack.pop();
            stack1.push(pop);
            if (Objects.nonNull(pop.left)) {
                stack.push(pop.left);
            }
            if (Objects.nonNull(pop.right)) {
                stack.push(pop.right);
            }
        }
        while (!stack1.isEmpty()) {
            System.out.print(stack1.pop().value + " ");
        }
    }

}
