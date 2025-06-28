package coding.tixi.day30;

import coding.tixi.ArrayUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * bfprt算法
 * 找打第k小的数
 *
 * @author linzherong
 * @date 2025/6/22 21:59
 */
public class FindMinKth {

    public static int minKth1(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return 0;
        }
        // 排序
        int[] copy = ArrayUtils.copyArray(arr);
        // O（N*logN）
        quickSort(copy);
        return copy[k-1];
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        process(arr, 0, arr.length-1);
    }
    public static void process(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] p = partition(arr, left, right);
        process(arr, left, p[0]-1);
        process(arr, p[1]+1, right);
    }
    public static int[] partition(int[] arr, int left, int right) {
        // 随机数
        int num = arr[ (int) (Math.random() * (right-left)) + left];
        int small = left-1;
        int large = right+1;
        int index = left;
        while (index < large) {
            if (arr[index] < num) {
                swap(arr, ++small, index++);
            } else if (arr[index] > num) {
                swap(arr, --large, index);
            } else {
                index++;
            }
        }
        return new int[]{small+1, large-1};
    }
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int a = arr[i];
        arr[i] = arr[j];
        arr[j] = a;
    }

    public static int minKth2(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return 0;
        }
        // O(N*logK)
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> o2-o1);
        for (int i = 0; i < k; i++) {
            heap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (heap.peek() > arr[i]) {
                heap.poll();
                heap.add(arr[i]);
            }
        }
        return heap.peek();
    }

    // O(N)
    public static int minKth3(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return 0;
        }
        int[] copy = ArrayUtils.copyArray(arr);
        return getMinK(copy, 0, arr.length-1, k);
    }
    public static int getMinK(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        int[] p = processMinK(arr, left, right);
        if (p[0] > k-1) {
            return getMinK(arr, left, p[0]-1, k);
        } else if (p[1] < k -1 ) {
            return getMinK(arr, p[1]+1, right, k);
        } else {
            return arr[k-1];
        }
    }

    public static int[] processMinK(int[] arr, int left, int right) {
        int num = arr[(int)(Math.random() * (right-left)) + left];
        int less = left-1;
        int large = right+1;
        int index = left;
        while (index < large) {
            if (arr[index] < num) {
                swap(arr, ++less, index++);
            } else if (arr[index] > num) {
                swap(arr, --large, index);
            } else {
                index++;
            }
        }
        return new int[]{less+1, large-1};
    }

    public static int minKthByBfprt(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return 0;
        }
        int[] copy = ArrayUtils.copyArray(arr);
        return getMinKByBfprt(copy, 0, arr.length-1, k);
    }
    public static int getMinKByBfprt(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        // 获取中位数的中位数
        int middle = getMiddleOfMiddle(arr, left, right);
        // 分组归类
        int[] p = partitionBfprt(arr, left, right, middle);
        if (p[0] > k-1) {
            return getMinKByBfprt(arr, left, p[0]-1, k);
        } else if (p[1] < k - 1)  {
            return getMinKByBfprt(arr, p[1] +1, right, k);
        } else {
            return arr[k-1];
        }
    }
    public static int getMiddleOfMiddle(int[] arr, int left, int right) {
        // 5个数字一组
        int length = (right - left + 1) % 5 == 0? (right - left + 1) / 5 : (right - left + 1) / 5 + 1;
        int[] p = new int[length];
        for (int i = 0; i < p.length; i++) {
            // 获取每5个为一组中的中间值
            p[i] = getMiddleBy5(arr, left + i * 5, Math.min(left + i * 5 + 4, right));
        }
        return getMinKByBfprt(p, 0, p.length-1, p.length / 2);
    }
    public static int getMiddleBy5(int[] arr, int left, int right) {
        // 插入排序
        insertSort(arr, left, right);
        return arr[(right + left) / 2];
    }
    public static void insertSort(int[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            int cur = i;
            int num = arr[cur];
            while (cur > left && num < arr[cur-1]) {
                arr[cur] = arr[cur-1];
                cur--;
            }
            arr[cur] = num;
        }
    }
    public static int[] partitionBfprt(int[] arr, int left, int right, int pivot) {
        int less = left - 1;
        int large = right+1;
        int index = left;
        while (index < large) {
            if (arr[index] < pivot) {
                swap(arr, ++less, index++);
            } else if (arr[index] > pivot) {
                swap(arr, -- large, index);
            } else {
                index++;
            }
        }
        // less => 小于，  large =>大于  ，则 less+1 和 large-1 => 等于
        return new int[]{less+1, large-1};
    }




    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 10;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            int ans4 = minKthByBfprt(arr, k);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("出错了");
            }
        }
        System.out.println("test finish");

    }

}
