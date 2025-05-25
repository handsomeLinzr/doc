package coding.tixi.day17;

import java.util.*;

/**
 * @author linzherong
 * @date 2025/5/24 16:27
 */
public class TopologicalOrderDFS2 {

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
        long nodes;  // 后续经过的节点数
        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }

    public Record f(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        long nodes = 0;
        // 获取到下边节点的所有经过的节点数量，可重复
        for (DirectedGraphNode neighbor : node.neighbors) {
            Record record = f(neighbor, map);
            nodes += record.nodes;
        }
        Record record = new Record(node, nodes + 1);
        map.put(node, record);
        return record;
    }

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> ans = new ArrayList<>(graph.size());
        HashMap<DirectedGraphNode, Record> map = new HashMap<>(graph.size());
        for (DirectedGraphNode node : graph) {
            f(node, map);
        }
        List<Record> recordList = new ArrayList<>();
        for (Record value : map.values()) {
            recordList.add(value);
        }
        recordList.sort((o1, o2) -> o2.nodes - o1.nodes > 0 ? 1:-1);
        for (Record record : recordList) {
            ans.add(record.node);
        }
        return ans;
    }

    public static void main(String[] args) {
        DirectedGraphNode node1 = new DirectedGraphNode(1);
        DirectedGraphNode node2 = new DirectedGraphNode(2);
        DirectedGraphNode node3 = new DirectedGraphNode(3);
        DirectedGraphNode node4 = new DirectedGraphNode(4);
        DirectedGraphNode node5 = new DirectedGraphNode(5);
        DirectedGraphNode node6 = new DirectedGraphNode(6);
        DirectedGraphNode node7 = new DirectedGraphNode(7);

        node1.neighbors.add(node2);
        node1.neighbors.add(node3);
        node1.neighbors.add(node4);

        node2.neighbors.add(node5);
        node3.neighbors.add(node5);

        node4.neighbors.add(node6);

        node6.neighbors.add(node3);
        node6.neighbors.add(node7);
        node5.neighbors.add(node7);

        ArrayList<DirectedGraphNode> arrayList = new ArrayList<>();
        arrayList.add(node1);
        arrayList.add(node2);
        arrayList.add(node3);
        arrayList.add(node4);
        arrayList.add(node5);
        arrayList.add(node6);
        arrayList.add(node7);


    }


}
