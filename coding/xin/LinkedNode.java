package coding.xin;

import java.util.Objects;

/**
 * 单链表
 * @author linzherong
 * @date 2025/3/16 14:39
 */
public class LinkedNode {

    public Integer value;
    public LinkedNode next;

    public LinkedNode(Integer value, LinkedNode next) {
        this.value = value;
        this.next = next;
    }

    public boolean hasNext() {
        return Objects.nonNull(next);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }
}
