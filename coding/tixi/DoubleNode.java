package coding.tixi;

/**
 * @author linzherong
 * @date 2025/3/26 13:45
 */
public class DoubleNode {

    public Integer value;
    public DoubleNode pre;
    public DoubleNode next;

    public DoubleNode() {
    }

    public DoubleNode(Integer value) {
        this.value = value;
    }

    public DoubleNode(Integer value, DoubleNode pre, DoubleNode next) {
        this.value = value;
        this.pre = pre;
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public DoubleNode getPre() {
        return pre;
    }

    public void setPre(DoubleNode pre) {
        this.pre = pre;
    }

    public DoubleNode getNext() {
        return next;
    }

    public void setNext(DoubleNode next) {
        this.next = next;
    }
}
