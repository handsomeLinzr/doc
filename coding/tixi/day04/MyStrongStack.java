package coding.tixi.day04;

import java.util.Stack;

/**
 * @author linzherong
 * @date 2025/3/29 00:48
 */
public class MyStrongStack {

    public Stack<Integer> valueStack;
    public Stack<Integer> minStack;
    public Stack<Integer> maxStack;
    public int size;

    public MyStrongStack() {
        valueStack = new Stack<>();
        minStack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(Integer value) {
        size++;
        valueStack.push(value);
        if (minStack.isEmpty()) {
            minStack.push(value);
        } else {
            minStack.push(Math.min(minStack.peek(), value));
        }
        if (maxStack.isEmpty()) {
            maxStack.push(value);
        } else {
            maxStack.push(Math.max(maxStack.peek(), value));
        }
    }
    public Integer pop() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        size--;
        minStack.pop();
        maxStack.pop();
        return valueStack.pop();
    }
    public Integer peek() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        return valueStack.peek();
    }
    public Integer getMin() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        return minStack.peek();
    }
    public Integer getMax() {
        if (size == 0) {
            throw new RuntimeException("没了");
        }
        return maxStack.peek();
    }

}
