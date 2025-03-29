package coding.tixi.day04;

/**
 * 数组实现队列
 * @author linzherong
 * @date 2025/3/28 23:38
 */
public class MyArrayQueue {

    public int[] arr;
    public int addIndex;
    public int pollIndex;
    int length;

    public MyArrayQueue(int size) {
        arr = new int[size];
    }

    public void add(int num) {
        if (length == arr.length) {
            throw new RuntimeException("满了");
        }
        length++;
        arr[addIndex++] = num;
    }

    public int poll() {
        if (length == 0) {
            throw new RuntimeException("没了");
        }
        length--;
        return arr[pollIndex++];
    }

    public int peek() {
        if (length == 0) {
            throw new RuntimeException("没了");
        }
        return arr[pollIndex];
    }


}
