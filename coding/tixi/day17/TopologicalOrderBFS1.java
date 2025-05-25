package coding.tixi.day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * https://www.lintcode.com/problem/topological-sorting
 *
 * @author linzherong
 * @date 2025/5/24 16:10
 */
public class TopologicalOrderBFS1 {

    static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;
        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> ans = new ArrayList<>(graph.size());
        if (graph.isEmpty()) {
            return ans;
        }
        HashMap<DirectedGraphNode, Integer> map = new HashMap<>(graph.size());
        for (DirectedGraphNode node : graph) {
            map.put(node, 0);
        }
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                map.put(neighbor, map.get(neighbor)+1);
            }
        }
        LinkedList<DirectedGraphNode> zeroSet = new LinkedList<>();
        for (DirectedGraphNode node : map.keySet()) {
            if (map.get(node) == 0) {
                zeroSet.add(node);
            };
        }
        while (!zeroSet.isEmpty()) {
            DirectedGraphNode poll = zeroSet.poll();
            ans.add(poll);
            for (DirectedGraphNode neighbor : poll.neighbors) {
                map.put(neighbor, map.get(neighbor)-1);
                if (map.get(neighbor) == 0) {
                    zeroSet.add(neighbor);
                }
            }
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

        ArrayList<DirectedGraphNode> arrayList1 = topSort(arrayList);
        System.out.println(arrayList1);

    }

}
