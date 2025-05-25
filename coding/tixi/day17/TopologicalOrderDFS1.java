package coding.tixi.day17;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author linzherong
 * @date 2025/5/24 16:27
 */
public class TopologicalOrderDFS1 {

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
        int deep;  // 最大深度
        Record(DirectedGraphNode node) {
            this.node = node;
        }
        Record(DirectedGraphNode node, int deep) {
            this.node = node;
            this.deep = deep;
        }
    }


    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        HashMap<DirectedGraphNode, Record> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            f(node, map);
        }
        List<DirectedGraphNode> collect = map.values().stream().sorted((o1, o2) -> o2.deep - o1.deep).map(r -> r.node).collect(Collectors.toList());
        ans.addAll(collect);
        return ans;
    }

    public Record f(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        int deep = 0;
        // 获取后续节点中最大的深度
        for (DirectedGraphNode neighbor : node.neighbors) {
            deep = Math.max(deep, f(neighbor, map).deep);
        }
        // 后续的最大深度+1
        Record r = new Record(node, deep+1);
        map.put(node, r);
        return r;
    }

    public static void main(String[] args) {
        TopologicalOrderDFS1 instance = new TopologicalOrderDFS1();


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
