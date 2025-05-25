package coding.tixi.day17;

import coding.tixi.day17.vo.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * 图深度优先遍历
 *
 * @author linzherong
 * @date 2025/5/21 13:21
 */
public class Day17_DFS {

    // 深度优先
    public static void dfs(Node start) {
        if (start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.push(start);
        set.add(start);
        System.out.println(start.value);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            for (Node next : pop.nexts) {
                if (set.add(next)) {
                    stack.push(pop);
                    stack.push(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Node start = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        start.nexts.add(node2);
        start.nexts.add(node3);
        start.nexts.add(node4);

        node2.nexts.add(node5);
        node3.nexts.add(node5);

        node4.nexts.add(node6);

        node5.nexts.add(node7);
        node6.nexts.add(node7);

        dfs(start);

    }

}
