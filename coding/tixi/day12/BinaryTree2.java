package coding.tixi.day12;

import coding.tixi.BinaryTreeNode;
import coding.tixi.ListNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 1. 二叉树宽度按层遍历
 *      定义一个队列，有左入左，有右入右
 * 2. 二叉树的序列化和反序列化
 *      先序序列化  preSerial  Queue<String>  递归
 *      先序反序列化  preDeserial  递归
 *      中序没有
 *      后序有，可以不管
 *      宽度序列化和反序列化  levelSerial  levelDserial
 * 3. 多叉数的序列化和反序列化（转二叉树）
 * 4. 打印二叉树
 * 5. 求二叉树最宽的层
 * 6. 给一个节点，获取其后继节点[后继节点：中序遍历时，该节点的下一个]（给的节点为二叉树节点，有 val， left， right， parent）
 *      右子树的最左节点 || 父节点是某个节点的左子树，则是那个节点
 * 7. 折纸条
 *
 * @author linzherong
 * @date 2025/3/30 20:37
 */
public class BinaryTree2 {

    public static void main(String[] args) {
        BinaryTree2 tree2 = new BinaryTree2();

        BinaryTreeNode head = new BinaryTreeNode(1);
        BinaryTreeNode headL = new BinaryTreeNode(2);
        BinaryTreeNode headR = new BinaryTreeNode(3);
        BinaryTreeNode headLL = new BinaryTreeNode(4);
        BinaryTreeNode headLR = new BinaryTreeNode(5);
        BinaryTreeNode headRL = new BinaryTreeNode(6);
        BinaryTreeNode headRR = new BinaryTreeNode(7);


        head.left = headL;
        headL.parent = head;
        head.right = headR;
        headR.parent = head;
        headL.left = headLL;
        headLL.parent = headL;
        headL.right = headLR;
        headLR.parent = headL;
        headR.left = headRL;
        headRL.parent = headR;
        headR.right = headRR;
        headRR.parent = headR;

        tree2.levelSort(head);
        System.out.println("================");
        Queue<Integer> preSerial = tree2.preSerial(head);
        System.out.println(preSerial);
        BinaryTreeNode node = tree2.preDeserial(preSerial);
        System.out.println(node);
        System.out.println("===============");
        Queue<Integer> postSerial = tree2.postSerial(head);
        System.out.println(postSerial);
        BinaryTreeNode postNode = tree2.postDeserial((LinkedList<Integer>) postSerial);
        System.out.println("================");
        Queue<Integer> levelSerial = tree2.levelSerial(head);
        System.out.println(levelSerial);
        BinaryTreeNode levelDeserial = tree2.levelDeserial(levelSerial);
        System.out.println(levelDeserial);
        System.out.println("================");
        System.out.println(tree2.getLevelMaxNodes(head));
        BinaryTreeNode successorNode = tree2.getSuccessorNode(headL);
        System.out.println(successorNode == null? "null" : successorNode.value);
        System.out.println("================");
        tree2.printAllFolds(3);
    }

    /**
     * 按层遍历
     */
    public void levelSort(BinaryTreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        LinkedList<BinaryTreeNode> list = new LinkedList<>();
        list.add(node);
        while (!list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode current = list.poll();
                System.out.print(current.value);
                if (Objects.nonNull(current.left)) {
                    list.add(current.left);
                }
                if (Objects.nonNull(current.right)) {
                    list.add(current.right);
                }
            }
        }
        System.out.println();
    }

    // 先序序列化
    public Queue<Integer> preSerial(BinaryTreeNode node) {
        Queue<Integer> result = new LinkedList<>();
        if (node == null) {
            return result;
        }
        preSerialProcess(node, result);
        return result;
    }
    private void preSerialProcess(BinaryTreeNode node, Queue<Integer> result) {
        if (node == null) {
            result.add(null);
        } else {
            result.add(node.value);
            preSerialProcess(node.left, result);
            preSerialProcess(node.right, result);
        }
    }
    // 反序列化
    public BinaryTreeNode preDeserial(Queue<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        return preBuildProcess(list);
    }
    private BinaryTreeNode preBuildProcess(Queue<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        Integer poll = list.poll();
        if (Objects.isNull(poll)) {
            return null;
        }
        BinaryTreeNode node = new BinaryTreeNode(poll);
        node.left = preBuildProcess(list);
        node.right = preBuildProcess(list);
        return node;
    }

    // 后序——序列化和反序列化
    public Queue<Integer> postSerial(BinaryTreeNode node) {
        Queue<Integer> result = new LinkedList<>();
        if (node == null) {
            return result;
        }
        postSerialProcess(node, result);
        return result;
    }
    private void postSerialProcess(BinaryTreeNode node, Queue<Integer> result) {
        if (node == null) {
            result.add(null);
        } else {
            postSerialProcess(node.left, result);
            postSerialProcess(node.right, result);
            result.add(node.value);
        }
    }

    // 构建二叉树
    public BinaryTreeNode postDeserial(LinkedList<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        return postBuildProcess(list);
    }
    private BinaryTreeNode postBuildProcess(LinkedList<Integer> list) {
        // 头节点
        Integer value = list.pollLast();
        if (list.isEmpty() || value == null) {
            return null;
        }
        BinaryTreeNode node = new BinaryTreeNode(value);
        node.right  = postBuildProcess(list);
        node.left = postBuildProcess(list);
        return node;
    }

    public Queue<Integer> levelSerial(BinaryTreeNode node) {
        Queue<Integer> result = new LinkedList<>();
        if (node == null) {
            return result;
        }
        Queue<BinaryTreeNode> levelQuery = new LinkedList<>();
        levelQuery.add(node);
        while (!levelQuery.isEmpty()) {
            int size = levelQuery.size();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode poll = levelQuery.poll();
                if (poll == null) {
                    result.add(null);
                } else {
                    result.add(poll.value);
                    levelQuery.add(poll.left);
                    levelQuery.add(poll.right);
                }
            }
        }
        return result;
    }
    public BinaryTreeNode levelDeserial(Queue<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        Integer poll = list.poll();
        BinaryTreeNode head = new BinaryTreeNode(poll);
        Queue<BinaryTreeNode> nodes = new LinkedList<>();
        nodes.add(head);
        while (!nodes.isEmpty()) {
            BinaryTreeNode cur = nodes.poll();
            Integer lVal = list.poll();
            if (lVal != null) {
                BinaryTreeNode left = new BinaryTreeNode(lVal);
                cur.left = left;
                nodes.add(left);
            }
            Integer rVal = list.poll();
            if (rVal != null) {
                BinaryTreeNode right = new BinaryTreeNode(rVal);
                cur.right = right;
                nodes.add(right);
            }
        }
        return head;
    }

    // 二叉树最宽的层
    public int getLevelMaxNodes(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        Queue<BinaryTreeNode> nodes = new LinkedList<>();
        nodes.add(node);
        int max = nodes.size();
        while (!nodes.isEmpty()) {
            int size = nodes.size();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode poll = nodes.poll();
                if (poll.left != null) {
                    nodes.add(poll.left);
                }
                if (poll.right != null) {
                    nodes.add(poll.right);
                }
            }
            max = Math.max(max, nodes.size());
        }
        return max;
    }

    // 获取后继节点（中序遍历情况下的下一个）
    public BinaryTreeNode getSuccessorNode(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return getLeftestNode(node.right);
        } else {
            return getParentLeft(node);
        }
    }

    // 获取最左边节点
    private BinaryTreeNode getLeftestNode(BinaryTreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    private BinaryTreeNode getParentLeft(BinaryTreeNode node) {
        // 父节点的父节点
        BinaryTreeNode parent = null;
        while (node != null) {
            // 父节点
            parent = node.parent;
            if (parent != null && parent.left == node) {
                return parent;
            }
            node = parent;
        }
        return parent;
    }

    // 折纸条
    public void printAllFolds(int n) {

    }

    // 多叉树和二叉树的格式化转换
    public BinaryTreeNode encode(MulTree tree) {
        return null;
    }
    public MulTree decode(BinaryTreeNode treeNode) {
        return null;
    }

    // 打印二叉树
    public void printBinaryTree(BinaryTreeNode node) {

    }

}
