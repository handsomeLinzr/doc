package coding.tixi.day17.vo;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * 图
 *
 * @author linzherong
 * @date 2025/5/21 13:09
 */
public class Graph {

    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;
    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

}
