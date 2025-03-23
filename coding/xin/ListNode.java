package coding.xin;

import java.util.Objects;

/**
 * 单链表
 * @author linzherong
 * @date 2025/3/16 14:39
 */
public class ListNode {

    public Integer value;
    public ListNode next;

    public ListNode(Integer value, ListNode next) {
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

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }
}
