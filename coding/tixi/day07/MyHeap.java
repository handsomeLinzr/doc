package coding.tixi.day07;

/**
 * 头节点   (index-1)/2
 * 子节点   (index*2)+1
 * @author linzherong
 * @date 2025/3/26 07:27
 */
public class MyHeap {

    public int heapSize;
    public int[] arr;
    public MyHeap(int size) {
        arr = new int[size];
    }

    public void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index-1)/2]) {
            swap(arr, index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    public void heapify(int[] arr, int index, int heapSize) {
        int compIndex;
        while ((compIndex = (index*2)+1) < heapSize) {
            compIndex = compIndex + 1 < heapSize && arr[compIndex+1] > arr[compIndex]? compIndex+1:compIndex;
            if (arr[index] < arr[compIndex]) {
                swap(arr, index, compIndex);
            } else {
                break;
            }
            index = compIndex;
        }
    }

    public void add(int num) {
        if (heapSize == arr.length) {
            throw new RuntimeException("满了");
        }
        arr[heapSize] = num;
        heapInsert(arr, heapSize++);
    }

    public Integer poll() {
        if (heapSize == 0) {
            throw new RuntimeException("空了");
        }
        int result = arr[0];
        swap(arr, 0, --heapSize);
        heapify(arr, 0, heapSize);
        return result;
    }

    public void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

}
