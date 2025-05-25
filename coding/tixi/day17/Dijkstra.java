package coding.tixi.day17;

import coding.tixi.day17.vo.Edge;
import coding.tixi.day17.vo.Node;

import java.util.*;
import java.util.stream.Collectors;

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


    // 加强堆
    static class HeapStrong {



    }

    public static HashMap<Node, Integer> dijkstra2(Node from) {
        HashMap<Node, Integer> ans = new HashMap<>();
        ans.put(from, 0);


        return ans;
    }

}
