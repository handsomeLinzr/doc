package coding.tixi.day07;

import coding.tixi.ArrayUtils;

import java.util.*;

/**
 *
 * 1. 大根堆，任何一个节点的二叉树，当前节点都是最大值（父节点大于子节点）
 *      父节点 = (子节点 - 1) / 2
 *      左子节点 = (父节点 * 2) +1, 右子节点 = （父节点 * 2） + 2
 * 2. heapInsert、heapSize、heapify（删除最大时，拿最后一个和堆顶替换，同时heapSize--，最后进行数据调整）
 * 3. PriorityQueue 默认是 小根堆
 * 4. 堆排序(大根堆，之后0和N-1交换，再把0到N-2调大根堆，周而复始)  O(N*logN)
 * 5. 一个几乎有序的数组（数组上每个位置，顺序调整后的位置和原位置相比，不超过K）要求用最快的方式进行排序
 *
 * @author linzherong
 * @date 2025/3/30 12:24
 */
public class HeapTest {

    public static void main(String[] args) {
        HeapTest test = new HeapTest();
//        test.testToBigHeap();
//        test.testToBigHeap();
//        test.testMyQueue();
//        test.testHeapSort();
        test.testSortByK();
    }

    public void toBigHeap(int[] arr) {
        if (Objects.isNull(arr) || arr.length <=1) {
            return;
        }
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr, i);
//        }
        for (int i = arr.length-1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
    }

    public void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1)/2]) {
            swap(arr, index, (index - 1)/2);
            index = (index - 1)/2;
        }
    }

    public void heapify(int[] arr, int index, int heapSize) {
        int left;
        while ((left = (index * 2) + 1) < heapSize) {
            int compIndex = left == heapSize-1? left : arr[left]>arr[left+1]? left:left+1;
            if (arr[compIndex] > arr[index]) {
                swap(arr, compIndex, index);
            } else {
                break;
            }
            index = compIndex;
        }
    }

    public void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public void testToBigHeap() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50);
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                max = Math.max(max, arr[i]);
            }
            toBigHeap(arr);
            if (max != arr[0]) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }
    public void testPriorityQueue() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50);
            PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
            int max = arr[0];
            queue.add(arr[0]);
            for (int i = 0; i < arr.length; i++) {
                max = Math.max(max, arr[i]);
                queue.add(arr[i]);
            }
            if (queue.poll() != max) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }
    public void testMyQueue() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50);
            PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
            MyHeap myHeap = new MyHeap(50);
            for (int i = 0; i < arr.length; i++) {
                heap.add(arr[i]);
                myHeap.add(arr[i]);
            }
            if (heap.size() != myHeap.heapSize) {
                System.out.println("出错了");
            }
            for (int i = 0; i < myHeap.heapSize; i++) {
                if (!Objects.equals(heap.poll(), myHeap.poll())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void heapSort(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        for (int i = arr.length-1; i >= 0; i--) {
            heapSortHeapify(arr, i, arr.length);
        }
        swap(arr, 0, arr.length-1);
        int heapSize = arr.length-1;
        while (heapSize > 1) {
            heapSortHeapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }
    public void heapSortHeapify(int[] arr, int index, int heapSize) {
        int compIndex;
        while ((compIndex = index*2+1) < heapSize) {
            compIndex = compIndex+1 < heapSize && arr[compIndex+1] < arr[compIndex]? compIndex+1:compIndex;
            if (arr[compIndex] < arr[index]) {
                swap(arr, compIndex, index);
            } else {
                break;
            }
            index = compIndex;
        }
    }
    public void testHeapSort() {
        for (int i = 0; i < 100_0000; i++) {
            int[] arr = ArrayUtils.generalArr(50);
            int[] copy = ArrayUtils.copyArray(arr);
            Arrays.sort(arr);
            heapSort(copy);
            for (int j = 0; j < copy.length; j++) {
                if (arr[j] != copy[copy.length-j-1]) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void sortByK(int[] arr, int K) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(K+1);
        int pushIndex = 0;
        for (; pushIndex < Math.min(arr.length, K + 1); pushIndex++) {
            queue.add(arr[pushIndex]);
        }
        int i = 0;
        for (; i < arr.length && pushIndex < arr.length; i++, pushIndex++) {
            arr[i] = queue.poll();
            queue.add(arr[pushIndex]);
        }
        while (!queue.isEmpty()) {
            arr[i++] = queue.poll();
        }
    }
    public void testSortByK() {
        for (int time = 0; time < 200_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50, 10);
            Set<Integer> set = new HashSet<>();
            Arrays.sort(arr);
            int[] array = ArrayUtils.copyArray(arr);
            for (int i = 0; i < arr.length / 2; i++) {
                if (!set.add(i)) {
                    continue;
                }
                int compIndex;
                do {
                    compIndex = i + ((int) (Math.random() * 3) + 1);
                } while (!set.add(compIndex));
                swap(arr, i, compIndex);
            }
            sortByK(arr, 3);
            for (int i = 0; i < arr.length; i++) {
                if (array[i] != arr[i]) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

}
