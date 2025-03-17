package coding.xin;

/**
 * 双链表
 * @author linzherong
 * @date 2025/3/16 15:56
 */
public class DoubleNode {

    public Integer value;
    public DoubleNode pre;
    public DoubleNode next;

    public DoubleNode(Integer value, DoubleNode pre, DoubleNode next) {
        this.value = value;
        this.pre = pre;
        this.next = next;
    }

}
