package coding.tixi.day04;

import java.util.Stack;

/**
 * 栈实现队列 FIFO
 * @author linzherong
 * @date 2025/3/29 01:14
 */
public class StackToQueue {

    public Stack<Integer> addStack;
    public Stack<Integer> pollStack;
    int size;

    public StackToQueue() {
        addStack = new Stack<>();
        pollStack = new Stack<>();
    }

    public void add(Integer value) {
        size ++;
        addStack.push(value);
    }

    public Integer poll() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        addToPoll();
        return pollStack.pop();
    }

    public Integer peek() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        addToPoll();
        return pollStack.peek();
    }

    private void addToPoll() {
        if (pollStack.isEmpty()) {
            while (!addStack.isEmpty()) {
                pollStack.push(addStack.pop());
            }
        }
    }

}
