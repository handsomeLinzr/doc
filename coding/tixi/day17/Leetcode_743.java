package coding.tixi.day17;

import java.util.*;

/**
 *
 * 有 n 个网络节点，标记为 1 到 n。
 * 给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
 * 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
 *
 * @author linzherong
 * @date 2025/5/24 18:40
 */
public class Leetcode_743 {

    public int networkDelayTime(int[][] times, int n, int k) {
        if (times == null || times.length == 0 || n <= 0) {
            return -1;
        }
        Map<Integer, Node> nodeMap = new HashMap<>(n);
        HashSet<Node> nodes = new HashSet<>();
        for (int i = 0; i < times.length; i++) {
            int f = times[i][0];
            int t = times[i][1];
            int w = times[i][2];
            Node from;
            if (nodeMap.containsKey(f)) {
                from = nodeMap.get(f);
            } else {
                from = new Node(f);
                nodeMap.put(f, from);
            }
            nodes.add(from);
            Node to;
            if (nodeMap.containsKey(t)) {
                to = nodeMap.get(t);
            } else {
                to = new Node(t);
                nodeMap.put(t, to);
            }
            nodes.add(to);
            Edge edge = new Edge(from, to, w);
            from.edges.add(edge);
        }
        return getDistinct(nodeMap.get(k), n);
    }
    class Node {
        int label;
        ArrayList<Edge> edges;
        Node(int label) {
            this.label = label;
            edges = new ArrayList<>();
        }
    }
    class Edge {
        int weight;
        Node from;
        Node to;
        Edge(Node from, Node to, int weight) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }
    class MyHeap {
        Node[] heap;
        Map<Node, Integer> indexMap;
        Map<Node, Integer> distanceMap;
        int size;
        MyHeap(int n) {
            heap = new Node[n];
            indexMap = new HashMap<>(n);
            distanceMap = new HashMap<>(n);
            size = 0;
        }
        void add(Node node, int distance) {
            int index;
            if (indexMap.containsKey(node)) {
                index = indexMap.get(node);
                if (index >= size || distanceMap.get(node) <= distance) {
                    return;
                }
                distanceMap.put(node, distance);
            } else {
                heap[size] = node;
                indexMap.put(node, size);
                distanceMap.put(node, distance);
                index = size++;
            }
            headInsert(index);
        }
        Node pop() {
            if (size == 0) {
                return null;
            }
            Node result = heap[0];
            swap(0, --size);
            heapify(0);
            return result;
        }
        int getDistance(Node node) {
            return distanceMap.get(node);
        }

        boolean isEmpty() {
            return size == 0;
        }
        void headInsert(int index) {
            int parent;
            while ((parent = (index-1)/2) >= 0) {
                if (distanceMap.get(heap[parent]) > distanceMap.get(heap[index])) {
                    swap(parent, index);
                } else {
                    break;
                }
                index = parent;
            }
        }
        void heapify(int index) {
            int child;
            while ((child = index*2+1) < size) {
                child = child+1 < size && distanceMap.get(heap[child+1]) < distanceMap.get(heap[child])? child+1 : child;
                if (distanceMap.get(heap[index]) > distanceMap.get(heap[child])) {
                    swap(index, child);
                } else {
                    break;
                }
                index  = child;
            }
        }
        void swap(int i, int j) {
            if (i == j) {
                return;
            }
            Node n1 = heap[i];
            Node n2 = heap[j];
            heap[i] = n2;
            heap[j] = n1;
            indexMap.put(n1, j);
            indexMap.put(n2, i);
        }
    }
    public int getDistinct(Node node, int n) {
        HashSet<Node> nodes = new HashSet<>(n);
        MyHeap heap = new MyHeap(n);
        heap.add(node, 0);
        int min = 0;
        while (!heap.isEmpty()) {
            Node pop = heap.pop();
            nodes.add(pop);
            int distance = heap.getDistance(pop);
            min = Math.max(distance, min);
            for (Edge edge : pop.edges) {
                if (!nodes.contains(edge.to)) {
                    heap.add(edge.to, distance + edge.weight);
                }
            }
        }
        return nodes.size() == n? min : -1;
    }

    public int networkDelayTime1(int[][] times, int n, int k) {
        if (times == null || times.length == 0) {
            return 0;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        heap.add(new int[]{k, 0});

        ArrayList<ArrayList<int[]>> list = new ArrayList<>(n+1);
        for (int i = 0; i < n + 1; i++) {
            list.add(new ArrayList<>());
        }
        for (int[] time : times) {
            list.get(time[0]).add(new int[]{time[1], time[2]});
        }

        boolean[] select = new boolean[n+1];
        int max = 0;
        int num = 0;
        while (!heap.isEmpty()) {
            int[] poll = heap.poll();
            int to = poll[0];
            int length = poll[1];
            if (select[to]) {
                continue;
            }
            num++;
            select[to] = true;
            max = Math.max(max, length);
            for (int[] ints : list.get(to)) {
                heap.add(new int[]{ints[0], length + ints[1]});
            }
        }
        return num == n? max : -1;
    }

    public static void main(String[] args) {
        Leetcode_743 instance = new Leetcode_743();
        int[][] arr = {{2,1,1},{2,3,1},{3,4,1}};
        int i1 = instance.networkDelayTime(arr, 4, 2);
        int i2 = instance.networkDelayTime1(arr, 4, 2);
        System.out.println(i1);
        System.out.println(i2);
    }

}
