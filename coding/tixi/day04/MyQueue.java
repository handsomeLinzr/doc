package coding.tixi.day04;

import coding.tixi.DoubleNode;

import javax.swing.plaf.PanelUI;
import java.util.Objects;

/**
 * 双向链表实现队列
 * @author linzherong
 * @date 2025/3/28 21:46
 */
public class MyQueue {

    public int size;

    public DoubleNode head;

    public DoubleNode tail;

    public MyQueue() {

    }

    public void addFirst(int value) {
        DoubleNode node = new DoubleNode(value);
        if (Objects.isNull(head)) {
            head = tail = node;
        } else {
            node.next = head;
            head.pre = node;
            head = node;
        }
        size++;
    }
    public void addLast(int value) {
        DoubleNode node = new DoubleNode(value);
        if (Objects.isNull(head)) {
            head = tail = node;
        } else {
            node.pre = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }
    public Integer peekFirst() {
        if (size == 0) {
            return null;
        }
        return head.value;
    }
    public Integer peekLast() {
        if (size == 0) {
            return null;
        }
        return tail.value;
    }
    public Integer pollFirst() {
        if (size == 0) {
            throw new RuntimeException("不够了");
        }
        size--;
        Integer result = head.value;
        DoubleNode next = head.next;
        if (Objects.isNull(next)) {
            head = tail = null;
        } else {
            next.pre = null;
            head = next;
        }
        return result;
    }
    public Integer pollLast() {
        if (size == 0) {
            throw new RuntimeException("不够了");
        }
        size--;
        Integer result = tail.value;
        DoubleNode pre = tail.pre;
        if (Objects.isNull(pre)) {
            head = tail = null;
        } else {
            tail.pre.next = null;
            tail = null;
        }
        return result;
    }

}
