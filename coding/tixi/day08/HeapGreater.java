package coding.tixi.day08;

import coding.tixi.ArrayUtils;

import java.util.*;

/**
 * 加强堆
 * HeapGreater 加强堆， peek  pop  push  remove(T t)  resign(T t) 将t这个元素重新调整
 * @author linzherong
 * @date 2025/3/30 16:23
 */
public class HeapGreater<T> {

    public Comparator<T> comparator;
    public int heapSize;
    public List<T> heap;
    public Map<T, Integer> heapIndex;

    public HeapGreater (Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.heapIndex = new HashMap<>();
    }

    // O(1)
    public T peek() {
        return heapSize == 0? null : heap.get(0);
    }

    // O(logN)
    public T pop() {
        if (heapSize == 0) {
            return null;
        }
        swap(0, heapSize-1);
        heapify(0, heapSize-1);
        T remove = heap.remove(--heapSize);
        heapIndex.remove(remove);
        return remove;
    }

    // O(logN)
    public void add(T value) {
        heap.add(value);
        heapIndex.put(value, heapSize);
        heapInsert(heapSize);
        heapSize++;
    }

    public boolean contain(T value) {
        return heapIndex.containsKey(value);
    }

    // O(logN)
    public void remove(T value) {
        if (!heapIndex.containsKey(value)) {
            return;
        }
        Integer index = heapIndex.get(value);
        T last = heap.remove(--heapSize);
        heapIndex.remove(value);
        if (index == heapSize) {
            return;
        }
        heap.set(index, last);
        heapIndex.put(last, index);
        resign(last);
    }

    public void resign(T value) {
        if (heapSize > 1) {
            Integer index = heapIndex.get(value);
            heapInsert(index);
            heapify(index, heapSize);
        }
    }

    /**
     * 上浮
     * @param index
     */
    public void heapInsert(int index) {
        int parent = (index - 1) / 2;
        while (parent >= 0) {
            if (comparator.compare(heap.get(parent), heap.get(index)) > 0) {
                swap(parent, index);
            } else {
                break;
            }
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    /**
     * 下沉
     * @param index
     * @param size
     */
    public void heapify(int index, int size) {
        int child = index*2+1;
        while (child < size) {
            // 获取子节点更小的一个
            child = child + 1 < size && comparator.compare(heap.get(child+1), heap.get(child)) > 0? child+1 : child;
            if (comparator.compare(heap.get(index), heap.get(child)) > 0) {
                swap(index, child);
            } else {
                break;
            }
            index = child;
            child = index*2+1;
        }
    }
    public void swap(int a, int b) {
        if (a == b) {
            return;
        }
        T t1 = heap.get(a);
        T t2 = heap.get(b);
        heap.set(a, t2);
        heap.set(b, t1);
        heapIndex.put(t2, a);
        heapIndex.put(t1, b);
    }

    public static void main(String[] args) {
        for (int t = 0; t < 100_0000; t++) {
            HeapGreater<Integer> heapGreater = new HeapGreater<>(Integer::compareTo);
            int[] arr = ArrayUtils.generalArr(10);
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < arr.length; i++) {
                if (set.add(arr[i])) {
                    heapGreater.add(arr[i]);
                }
            }
            for (int i = 0; i < arr.length; i++) {
                int value = arr[i];
                heapGreater.remove(value);
            }
        }
    }

}
