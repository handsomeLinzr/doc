package coding.tixi.day04;

import java.util.LinkedList;

/**
 * 队列实现栈
 *
 * @author linzherong
 * @date 2025/3/29 01:14
 */
public class QueueToStack {

    public LinkedList<Integer> valueQueue;
    public int size;

    public QueueToStack() {
        valueQueue = new LinkedList<>();
    }

    public void push(Integer value) {
        if (size == valueQueue.size()) {
            valueQueue.add(value);
        } else {
            valueQueue.set(size, value);
        }
        size++;

    }

    public Integer pop() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        return valueQueue.get(--size);
    }

    public Integer peek() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        return valueQueue.get(size-1);
    }

}
