package coding.offer.day03;

import java.util.*;

/**
 *
 * 给一个头节点 head ，一个节点 target 和  K， 查出离target距离为K 的所有节点
 * 从父节点过去也算
 *
 * @author linzherong
 * @date 2025/7/14 13:11
 */
public class DistanceKNodes {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 获取距离target节点距离K的所有节点
     * @param root
     * @param target
     * @param K
     * @return
     */
    public static List<Node> distanceKNodes(Node root, Node target, int K) {
        Queue<Node> queue = new LinkedList<>();
        List<Node> list = new ArrayList<>();
        HashSet<Node> contains = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        parentMap.put(root, null);
        putParent(root, parentMap);
        // 放入队列target
        queue.offer(target);
        contains.add(target);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node poll = queue.poll();
                if (level == K) {
                    list.add(poll);
                }
                if (poll.left != null && !contains.contains(poll.left)) {
                    contains.add(poll.left);
                    queue.add(poll.left);
                }
                if (poll.right != null && !contains.contains(poll.right)) {
                    contains.add(poll.right);
                    queue.add(poll.right);
                }
                if (parentMap.get(poll) != null && !contains.contains(parentMap.get(poll))) {
                    contains.add(parentMap.get(poll));
                    queue.add(parentMap.get(poll));
                }
            }
            level++;
            if (level > K) {
                break;
            }
        }
        return list;
    }

    // 设置父节点
    public static void putParent(Node parent, Map<Node, Node> parentMap) {
        if (parent.left != null) {
            parentMap.put(parent.left, parent);
            putParent(parent.left, parentMap);
        }
        if (parent.right != null) {
            parentMap.put(parent.right, parent);
            putParent(parent.right, parentMap);
        }
    }

    public static void main(String[] args) {
        // 测试用例1：原始测试用例
        System.out.println("=== 测试用例1：原始测试用例 ===");
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        System.out.println("距离节点5为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：7 4 0 8");
        System.out.println();

        // 测试用例2：测试K=0的情况
        System.out.println("=== 测试用例2：K=0的情况 ===");
        ans = distanceKNodes(root, target, 0);
        System.out.println("距离节点5为0的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：5");
        System.out.println();

        // 测试用例3：测试K=1的情况
        System.out.println("=== 测试用例3：K=1的情况 ===");
        ans = distanceKNodes(root, target, 1);
        System.out.println("距离节点5为1的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：6 2 3");
        System.out.println();

        // 测试用例4：测试目标节点是叶子节点
        System.out.println("=== 测试用例4：目标节点是叶子节点 ===");
        ans = distanceKNodes(root, n6, 2);
        System.out.println("距离节点6为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：2 3");
        System.out.println();

        // 测试用例5：测试目标节点是根节点
        System.out.println("=== 测试用例5：目标节点是根节点 ===");
        ans = distanceKNodes(root, n3, 2);
        System.out.println("距离节点3为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：6 2 0 8");
        System.out.println();

        // 测试用例6：测试K值很大的情况
        System.out.println("=== 测试用例6：K值很大的情况 ===");
        ans = distanceKNodes(root, n5, 5);
        System.out.println("距离节点5为5的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：空列表");
        System.out.println();

        // 测试用例7：测试单节点树
        System.out.println("=== 测试用例7：单节点树 ===");
        Node singleNode = new Node(10);
        ans = distanceKNodes(singleNode, singleNode, 0);
        System.out.println("单节点树，距离节点10为0的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：10");
        System.out.println();

        // 测试用例8：测试左偏树
        System.out.println("=== 测试用例8：左偏树 ===");
        Node left1 = new Node(1);
        Node left2 = new Node(2);
        Node left3 = new Node(3);
        Node left4 = new Node(4);
        left1.left = left2;
        left2.left = left3;
        left3.left = left4;
        
        ans = distanceKNodes(left1, left2, 2);
        System.out.println("左偏树，距离节点2为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：4");
        System.out.println();

        // 测试用例9：测试右偏树
        System.out.println("=== 测试用例9：右偏树 ===");
        Node right1 = new Node(1);
        Node right2 = new Node(2);
        Node right3 = new Node(3);
        Node right4 = new Node(4);
        right1.right = right2;
        right2.right = right3;
        right3.right = right4;
        
        ans = distanceKNodes(right1, right2, 2);
        System.out.println("右偏树，距离节点2为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：4");
        System.out.println();

        // 测试用例10：测试完全二叉树
        System.out.println("=== 测试用例10：完全二叉树 ===");
        Node complete1 = new Node(1);
        Node complete2 = new Node(2);
        Node complete3 = new Node(3);
        Node complete4 = new Node(4);
        Node complete5 = new Node(5);
        Node complete6 = new Node(6);
        Node complete7 = new Node(7);
        
        complete1.left = complete2;
        complete1.right = complete3;
        complete2.left = complete4;
        complete2.right = complete5;
        complete3.left = complete6;
        complete3.right = complete7;
        
        ans = distanceKNodes(complete1, complete2, 2);
        System.out.println("完全二叉树，距离节点2为2的所有节点：");
        for (Node node : ans) {
            System.out.print(node.value + " ");
        }
        System.out.println();
        System.out.println("期望结果：6 7");
        System.out.println();

        System.out.println("=== 所有测试用例执行完成 ===");
    }

}
