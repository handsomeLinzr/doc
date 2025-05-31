package coding.tixi.day17;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * https://www.lintcode.com/problem/topological-sorting
 *
 * @author linzherong
 * @date 2025/5/24 16:10
 */
public class TopologicalOrderBFS2 {

    static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;
        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    class Record {
        DirectedGraphNode node;
        int in;
        int time;
        public Record(DirectedGraphNode node, int in) {
            this.node = node;
            this.in = in;
            this.time = 0;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "node=" + node.label +
                    ", in=" + in +
                    ", time=" + time +
                    '}';
        }
    }

    class HeapGreater {
        ArrayList<Record> head;
        Map<DirectedGraphNode, Integer> index1;
        Map<Record, Integer> index2;
        Comparator<Record> comparator = new RecordComparator();
        int size;
        HeapGreater(int n) {
            this.head = new ArrayList<>(n);
            this.index1 = new HashMap<>(n);
            this.index2 = new HashMap<>(n);
            size = 0;
        }
        boolean isEmpty() {
            return size == 0;
        }
        public void add(Record record) {
            head.add(record);
            index1.put(record.node, size);
            index2.put(record, size);
            heapInsert(size++);
        }
        public Record poll() {
            if (size == 0) {
                return null;
            }
            Record record = head.get(0);
            swap(0, --size);
            heapify(0, size-1);
            return record;
        }
        public void minus(DirectedGraphNode node) {
            Integer index = index1.get(node);
            if (index >= size) {
                System.out.println("出错");
                return;
            }
            Record record = head.get(index);
            record.in--;
            record.time++;
            heapInsert(index);
        }
        public void heapInsert(int index) {
            int parent;
            while ((parent = (index-1)/2) >= 0) {
                if (comparator.compare(head.get(index), head.get(parent)) < 0) {
                    swap(parent, index);
                } else {
                    break;
                }
                index = parent;
            }
        }
        public void heapify(int index, int last) {
            int child;
            while ((child = (index*2)+1) <= last) {
                child = child+1<=last && comparator.compare(head.get(child+1), head.get(child))<0?child+1:child;
                if (comparator.compare(head.get(child), head.get(index))<0) {
                    swap(child, index);
                } else {
                    break;
                }
                index = child;
            }
        }
        public void swap(int i, int j) {
            if (i == j) {
                return;
            }
            Record ri = head.get(i);
            Record rj = head.get(j);
            head.set(i, rj);
            head.set(j, ri);
            index1.put(ri.node, j);
            index2.put(ri, j);
            index1.put(rj.node, i);
            index2.put(rj, i);
        }

    }

    class RecordComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.in != o2.in? o1.in-o2.in:o1.time-o2.time;
        }
    }

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        Map<DirectedGraphNode, Integer> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            map.put(node, 0);
        }
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                map.put(neighbor, map.get(neighbor)+1);
            }
        }
        HeapGreater heapGreater = new HeapGreater(graph.size());
        for (Map.Entry<DirectedGraphNode, Integer> entry : map.entrySet()) {
            heapGreater.add(new Record(entry.getKey(), entry.getValue()));
        }

        while (!heapGreater.isEmpty()) {
            Record poll = heapGreater.poll();
            if (poll.in != 0) {
                break;
            }
            ans.add(poll.node);
            for (DirectedGraphNode neighbor : poll.node.neighbors) {
                heapGreater.minus(neighbor);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        DirectedGraphNode node0 = new DirectedGraphNode(0);
        DirectedGraphNode node1 = new DirectedGraphNode(1);
        DirectedGraphNode node2 = new DirectedGraphNode(2);
        DirectedGraphNode node3 = new DirectedGraphNode(3);
        DirectedGraphNode node4 = new DirectedGraphNode(4);
        DirectedGraphNode node5 = new DirectedGraphNode(5);
        DirectedGraphNode node6 = new DirectedGraphNode(6);
        DirectedGraphNode node7 = new DirectedGraphNode(7);
        DirectedGraphNode node8 = new DirectedGraphNode(8);
        DirectedGraphNode node9 = new DirectedGraphNode(9);

        node0.neighbors.add(node1);
        node0.neighbors.add(node2);
        node0.neighbors.add(node9);
        node0.neighbors.add(node5);
        node0.neighbors.add(node7);

        node1.neighbors.add(node5);

        node2.neighbors.add(node1);
        node2.neighbors.add(node5);

        node3.neighbors.add(node9);
        node3.neighbors.add(node2);
        node3.neighbors.add(node5);
        node3.neighbors.add(node1);

        node4.neighbors.add(node0);
        node4.neighbors.add(node8);
        node4.neighbors.add(node5);
        node4.neighbors.add(node1);

        node6.neighbors.add(node8);
        node6.neighbors.add(node1);
        node6.neighbors.add(node3);
        node6.neighbors.add(node5);
        node6.neighbors.add(node7);

        node7.neighbors.add(node9);
        node7.neighbors.add(node2);
        node7.neighbors.add(node5);

        node8.neighbors.add(node0);
        node8.neighbors.add(node9);
        node8.neighbors.add(node2);
        node8.neighbors.add(node3);
        node8.neighbors.add(node1);

        node9.neighbors.add(node1);
        node9.neighbors.add(node2);
        node9.neighbors.add(node5);

        ArrayList<DirectedGraphNode> arrayList = new ArrayList<>();
        arrayList.add(node0);
        arrayList.add(node1);
        arrayList.add(node2);
        arrayList.add(node3);
        arrayList.add(node4);
        arrayList.add(node5);
        arrayList.add(node6);
        arrayList.add(node7);
        arrayList.add(node8);
        arrayList.add(node9);

        TopologicalOrderBFS2 instance = new TopologicalOrderBFS2();
        ArrayList<DirectedGraphNode> arrayList1 = instance.topSort(arrayList);
        arrayList1.forEach(a -> System.out.println(a.label));

    }

}
