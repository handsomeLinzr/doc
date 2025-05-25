package coding.tixi.day17;

import coding.tixi.day17.vo.Edge;
import coding.tixi.day17.vo.Graph;
import coding.tixi.day17.vo.Node;

import java.util.*;

/**
 *
 * Prim 最小生成树方法
 * 点解锁边，边解锁点
 *
 * @author linzherong
 * @date 2025/5/25 16:27
 */
public class Prim {


    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> ans = new HashSet<>();
        Set<Node> nodes = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Node node : graph.nodes.values()) {
            if (!nodes.add(node)) {
                continue;
            }
            // 获取对一个节点，所有边放入堆
            priorityQueue.addAll(node.edges);
            while (!priorityQueue.isEmpty()) {
                Edge poll = priorityQueue.poll();
                if (!nodes.add(poll.to)) {
                    continue;
                }
                ans.add(poll);
                priorityQueue.addAll(poll.to.edges);
            }
        }
        return ans;
    }

    static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }


    static class Edge1 {
        public int from;
        public int to;
        public int length;
        public Edge1(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }
    }

    public static int prim(int[][] graph) {
        PriorityQueue<Edge1> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.length));
        HashSet<Integer> set = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = i+1; j < graph[0].length; j++) {
                priorityQueue.add(new Edge1(i, j, graph[i][j]));
            }
        }
        while (!priorityQueue.isEmpty()) {
            Edge1 poll = priorityQueue.poll();
            if (set.contains(poll.from) && set.contains(poll.to)) {
                continue;
            }
            set.add(poll.from);
            set.add(poll.to);
            sum += poll.length;
        }
        return sum;
    }



    public static void main(String[] args) {
        Graph graph = GraphGenerator.createGraph(new int[][]{{1, 2, 1}, {7, 2, 4}, {6, 2, 3}, {100, 4, 1}, {101, 3, 1}, {25,2,8},{50,8,3}});
        Set<Edge> edges = primMST(graph);
        Set<Edge> edges1 = primMST(graph);
        System.out.println(edges.equals(edges1));

        Graph graph1 = GraphGenerator.createGraph(new int[][]{{1, 2, 1}, {7, 2, 4}, {6, 2, 3}, {100, 4, 1}, {101, 3, 1}});
        Set<Edge> edges2 = primMST(graph1);
        Set<Edge> edges21 = primMST(graph1);
        System.out.println(edges2.equals(edges21));


    }

}
