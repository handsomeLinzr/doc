package coding.tixi.day17.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 图的每个点
 *
 * @author linzherong
 * @date 2025/5/24 12:12
 */
public class Node {

    public int value;
    public int in;
    public int out;

    public List<Node> nexts;
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }

}
