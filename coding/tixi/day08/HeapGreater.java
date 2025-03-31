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

    ArrayList<T> heap;
    Map<T, Integer> indexMap;
    int heapSize = 0;
    Comparator<T> comparator;
    public HeapGreater(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.comparator = comparator;
    }

    // O(1)
    public T peek() {
        return heapSize == 0? null : heap.get(0);
    }

    // O(logN)
    public T pop() {
        if (heapSize == 0) {
            throw new RuntimeException("空了");
        }
        T result = heap.get(0);
        swap(0, heapSize-1);
        heap.remove(--heapSize);
        indexMap.remove(result);
        heapify(0);
        return result;
    }

    // O(logN)
    public void add(T value) {
        heap.add(heapSize, value);
        indexMap.put(value, heapSize);
        heapInsert(heapSize++);
    }

    // O(logN)
    public void remove(T value) {
        if (!indexMap.containsKey(value)) {
            return;
        }
        Integer index = indexMap.get(value);
        T replace = heap.get(heapSize - 1);
        indexMap.remove(value);
        heap.remove(--heapSize);
        if (value != replace) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(index);
        }
    }

    public void resign(int index) {
        if (heapSize > 1) {
            heapInsert(index);
            heapify(index);
        }
    }

    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index-1)/2)) > 0) {
            swap(index, (index-1)/2);
            index = (index-1)/2;
        }
    }
    public void heapify(int index) {
        int compIndex;
        while ((compIndex = index*2+1) < heapSize) {
            compIndex = compIndex+1 < heapSize && comparator.compare(heap.get(compIndex+1), heap.get(compIndex)) > 0? compIndex+1:compIndex;
            if (comparator.compare(heap.get(compIndex), heap.get(index)) > 0) {
                swap(compIndex, index);
            } else {
                break;
            }
            index = compIndex;
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
        indexMap.put(t1, b);
        indexMap.put(t2, a);
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
