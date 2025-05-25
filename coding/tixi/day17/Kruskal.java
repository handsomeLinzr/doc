package coding.tixi.day17;

import coding.tixi.day17.vo.Edge;
import coding.tixi.day17.vo.Graph;
import coding.tixi.day17.vo.Node;

import java.util.*;

/**
 *
 * Kruskal 最小生成树方法
 * 所有边获取最小权重的一条，将 from 和 to 两个点进行合并
 *
 * @author linzherong
 * @date 2025/5/25 15:30
 */
public class Kruskal {

    // 最小生成树
    public static Set<Edge> kruskalMST(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        priorityQueue.addAll(graph.edges);
        HashSet<Node> nodes = new HashSet<>();
        Set<Edge> ans = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Edge poll = priorityQueue.poll();
            if (nodes.contains(poll.from) && nodes.contains(poll.to)) {
                continue;
            }
            nodes.add(poll.from);
            nodes.add(poll.to);
            ans.add(poll);
        }
        return ans;
    }

    static class UnionFound {
        HashMap<Node, Node> parent;
        HashMap<Node, Integer> size;
        public UnionFound(Graph graph) {
            parent = new HashMap<>(graph.nodes.size());
            size = new HashMap<>(graph.nodes.size());
            for (Node node : graph.nodes.values()) {
                parent.put(node, node);
                size.put(node, 1);
            }
        }
        public boolean isSameSet(Node n1, Node n2) {
            return getParent(n1) == getParent(n2);
        }
        public Node getParent(Node node) {
            List<Node> temp = new ArrayList<>();
            while (node != parent.get(node)) {
                temp.add(node);
                node = parent.get(node);
            }
            for (Node node1 : temp) {
                parent.put(node1, node);
            }
            return node;
        }
        public void union(Node n1, Node n2) {
            if (isSameSet(n1, n2)) {
                return;
            }
            Node parent1 = getParent(n1);
            Node parent2 = getParent(n2);
            Integer s1 = size.get(parent1);
            Integer s2 = size.get(parent2);
            if (s1 > s2) {
                parent.put(parent2, parent1);
                size.put(parent1, s1+s2);
            } else {
                parent.put(parent1, parent2);
                size.put(parent2, s1+s2);
            }
        }
    }

    public static Set<Edge> kruskalMST1(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        priorityQueue.addAll(graph.edges);
        UnionFound unionFound = new UnionFound(graph);
        Set<Edge> ans = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Edge poll = priorityQueue.poll();
            if (!unionFound.isSameSet(poll.from, poll.to)) {
                unionFound.union(poll.from, poll.to);
                ans.add(poll);
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

    public static void main(String[] args) {
        Graph graph = GraphGenerator.createGraph(new int[][]{{1, 2, 1}, {7, 2, 4}, {6, 2, 3}, {100, 4, 1}, {101, 3, 1}, {25,2,8},{50,8,3}});
        Set<Edge> edges = kruskalMST(graph);
        Set<Edge> edges1 = kruskalMST1(graph);
        System.out.println(edges.equals(edges1));


        Graph graph1 = GraphGenerator.createGraph(new int[][]{{1, 2, 1}, {7, 2, 4}, {6, 2, 3}, {100, 4, 1}, {101, 3, 1}});
        Set<Edge> edges2 = kruskalMST(graph1);
        Set<Edge> edges21 = kruskalMST1(graph1);
        System.out.println(edges2.equals(edges21));

    }

}
