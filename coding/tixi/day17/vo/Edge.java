package coding.tixi.day17.vo;

/**
 *
 * 图的边
 *
 * @author linzherong
 * @date 2025/5/24 12:18
 */
public class Edge {

    public int weight;
    public Node from;
    public Node to;

    public Edge() {}
    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

}
