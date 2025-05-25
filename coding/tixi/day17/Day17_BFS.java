package coding.tixi.day17;

import coding.tixi.day17.vo.Node;

import java.util.*;

/**
 *
 * 图宽度优先遍历
 *
 * @author linzherong
 * @date 2025/5/21 13:20
 */
public class Day17_BFS {

    // 宽度优先遍历
    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        Set<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            System.out.println(poll.value);
            for (Node next : poll.nexts) {
                if (set.add(next)) {
                    queue.add(next);
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

        bfs(start);

    }


}
