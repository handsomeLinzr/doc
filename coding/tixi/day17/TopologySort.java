package coding.tixi.day17;

import coding.tixi.day17.vo.Graph;
import coding.tixi.day17.vo.Node;

import java.util.*;

/**
 *
 * 拓扑排序
 *
 * @author linzherong
 * @date 2025/5/21 22:40
 */
public class TopologySort {

    public static List<Node> sortedTopology(Graph graph) {
        List<Node> ans = new ArrayList<>();
        if (graph == null || graph.nodes.isEmpty()) {
            return ans;
        }
        HashMap<Node, Integer> map = new HashMap<>();
        LinkedList<Node> zeroIn = new LinkedList<>();
        for (Node value : graph.nodes.values()) {
            map.put(value, value.in);
            if (value.in == 0) {
                zeroIn.offer(value);
            }
        }
        while (!zeroIn.isEmpty()) {
            Node poll = zeroIn.poll();
            ans.add(poll);
            for (Node next : poll.nexts) {
                map.put(next, map.get(next)-1);
                if (map.get(next) == 0) {
                    zeroIn.offer(next);
                }
            }
        }
        return ans;
    }

}
