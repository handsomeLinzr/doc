package coding.tixi.day04;

/**
 * 数组实现栈
 * @author linzherong
 * @date 2025/3/28 23:24
 */
public class MyArrayStack {

    public int[] arr;
    int size;

    public MyArrayStack(int size) {
        arr = new int[size];
    }

    public void push(int value) {
        if (arr.length == size) {
            throw new RuntimeException("满了");
        }
        arr[size] = value;
        size++;
    }

    public Integer pop() {
        if (size == 0) {
            return null;
        }
        return arr[--size];
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return arr[size-1];
    }


}
