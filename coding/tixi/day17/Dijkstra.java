package coding.tixi.day17;

import coding.tixi.ArrayUtils;
import coding.tixi.day17.vo.Edge;
import coding.tixi.day17.vo.Graph;
import coding.tixi.day17.vo.Node;

import java.util.*;

/**
 *
 * 点 A 到每个点的最小路径
 *
 * @author linzherong
 * @date 2025/5/25 16:53
 */
public class Dijkstra {

    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> ans = new HashMap<>();
        ans.put(from, 0);
        HashSet<Node> selectSet = new HashSet<>();
        Node next;
        while ((next = getNext(ans, selectSet)) != null) {
            Integer distinct = ans.get(next);
            for (Edge edge : next.edges) {
                if (!ans.containsKey(edge.to)) {
                    ans.put(edge.to, distinct + edge.weight);
                } else {
                    Integer i = ans.get(edge.to);
                    ans.put(edge.to, Math.min(distinct + edge.weight, i));
                }
            }
            selectSet.add(next);
        }
        return ans;
    }

    // 获取没被选择中的，长度最小的一个
    public static Node getNext(HashMap<Node, Integer> map, HashSet<Node> selectSet) {
        Node minNode = null;
        for (Map.Entry<Node, Integer> nodeEntry : map.entrySet()) {
            if (selectSet.contains(nodeEntry.getKey())) {
                continue;
            }
            if (minNode == null) {
                minNode = nodeEntry.getKey();
            } else {
                minNode = map.get(minNode) > nodeEntry.getValue()? nodeEntry.getKey() : minNode;
            }
        }
        return minNode;
    }

    static class Record {
        Node node;
        int distinct;
        public Record(Node node, int distinct) {
            this.node = node;
            this.distinct = distinct;
        }
    }

    // 加强堆
    static class HeapStrong {
        ArrayList<Record> heap;
        Map<Node, Integer> index;
        Comparator<Record> comparator;
        Set<Node> select;
        int size = 0;
        public HeapStrong() {
            this.heap = new ArrayList<>();
            this.index = new HashMap<>();
            select = new HashSet<>();
            comparator = Comparator.comparingInt(o -> o.distinct);
        }
        public void add(Node node, int instance) {
            if (select.contains(node)) {
                return;
            }
            Integer ix;
            if (index.containsKey(node)) {
                ix = index.get(node);
                Record record = heap.get(ix);
                if (record.distinct <= instance) {
                    return;
                }
                record.distinct = instance;
            } else {
                if (heap.size() > size) {
                    heap.set(size, new Record(node, instance));
                } else {
                    heap.add(new Record(node, instance));
                }
                index.put(node, size++);
                ix = size-1;
            }
            heapInsert(ix);
        }
        public void heapInsert(Integer index) {
            int parent;
            while ((parent = (index-1)/2) >= 0) {
                if (comparator.compare(heap.get(parent), heap.get(index))>0) {
                    swap(parent, index);
                } else {
                    break;
                }
                index = parent;
            }
        }
        public void heapify(int index) {
            int child;
            while ((child = index*2+1)<size) {
                child = child+1<size && comparator.compare(heap.get(child+1), heap.get(child))<0? child+1 : child;
                if (comparator.compare(heap.get(index), heap.get(child))>0) {
                    swap(index, child);
                } else {
                    break;
                }
                index = child;
            }
        }
        public Record pop() {
            if (size == 0) {
                return null;
            }
            swap(0, --size);
            heapify(0);
            Record record = heap.get(size);
            select.add(record.node);
            return record;
        }
        public void swap(int i, int j) {
            if (i == j) {
                return;
            }
            Record ri = heap.get(i);
            Record rj = heap.get(j);
            heap.set(i, rj);
            heap.set(j, ri);
            index.put(ri.node, j);
            index.put(rj.node, i);
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public static HashMap<Node, Integer> dijkstra2(Node from) {
        HashMap<Node, Integer> ans = new HashMap<>();
        HeapStrong heap = new HeapStrong();
        // 存放
        heap.add(from, 0);
        while (!heap.isEmpty()) {
            Record pop = heap.pop();
            Node node = pop.node;
            if (ans.containsKey(node)) {
                continue;
            }
            ans.put(node, pop.distinct);
            for (Edge edge : node.edges) {
                heap.add(edge.to, pop.distinct + edge.weight);
            }
        }
        return ans;
    }

    public static Node generalNode() {
        Node node = new Node(1);

        int length = (int)(Math.random() * 8);

        int[] arr1 = ArrayUtils.generalArr(5,15);
        Arrays.sort(arr1);
        length = Math.min(length, arr1.length);


        int[][] arr = new int[length][3];
        for (int i = 0; i < length; i++) {
            arr[i][0] = (int) (Math.random() * 20);
            int a = (int) (Math.random() * arr1.length);
            int b = (int) (Math.random() * arr1.length);
            while (b == a) {
                b = (int) (Math.random() * arr1.length);
            }
            arr[i][1] = arr1[Math.min(a, b)];
            arr[i][2] = arr1[Math.max(a, b)];
        }
        Graph graph = GraphGenerator.createGraph(arr);
        graph.nodes.values().forEach(node1 -> {
            if (Math.random() > 0.6) {
                return;
            }
            node.edges.add(new Edge(node, node1, (int)(Math.random()*20)));
        });
        return node;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            Node node = generalNode();
            HashMap<Node, Integer> nodeIntegerHashMap1 = dijkstra1(node);
            HashMap<Node, Integer> nodeIntegerHashMap2 = dijkstra2(node);
            for (Map.Entry<Node, Integer> nodeIntegerEntry : nodeIntegerHashMap1.entrySet()) {
                if (!Objects.equals(nodeIntegerHashMap2.get(nodeIntegerEntry.getKey()), nodeIntegerEntry.getValue())) {
                    System.out.println("Oops");
                }
            }
        }
        System.out.println("finish");


    }

}
