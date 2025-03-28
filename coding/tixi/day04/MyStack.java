package coding.tixi.day04;

import coding.tixi.DoubleNode;

import java.util.Objects;

/**
 * 双向链表实现栈
 * @author linzherong
 * @date 2025/3/28 21:47
 */
public class MyStack {

    public int size;

    public DoubleNode head;

    public void push(int value) {
        size++;
        DoubleNode node = new DoubleNode(value);
        if (Objects.isNull(head)) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }

    public Integer peek() {
        if (size == 0) {
            throw new RuntimeException("不够了");
        }
        return head.value;
    }

    public Integer pop() {
        Integer result = peek();
        size -- ;
        head = head.next;
        return result;
    }

}
