package coding.tixi.day25;

import java.util.LinkedList;

/**
 *
 * w窗口内最大值
 *
 * @author linzherong
 * @date 2025/6/9 00:18
 */
public class SlidingWindowMaxArray {

    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        int R = 0;
        LinkedList<Integer> list = new LinkedList<>();
        // 填满窗口
        while (R < w-1) {
            while (!list.isEmpty() && arr[list.peekLast()]<arr[R]) {
                list.pollLast();
            }
            list.addLast(R);
            R++;
        }
        // 从窗口开始往下执行
        int L = 0;
        int N = arr.length;
        int[] resp = new int[N - w + 1];
        while (R < N) {
            while (!list.isEmpty() && arr[list.peekLast()]<arr[R]) {
                list.pollLast();
            }
            list.addLast(R);
            resp[L] = arr[list.peekFirst()];
            if (list.peekFirst() == L) {
                list.pollFirst();
            }
            L++;
            R++;
        }
        return resp;
    }

    public static int[] getMaxWindow2(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        int N = arr.length;
        int index = 0;
        int[] resp = new int[N-w+1];
        LinkedList<Integer> list = new LinkedList<>();
        for (int R = 0; R < N; R++) {
            while (!list.isEmpty() && arr[list.peekLast()]<=arr[R]) {
                list.pollLast();
            }
            list.addLast(R);
            if (list.peekFirst() == R-w) {
                list.pollFirst();
            }
            if (R >= w-1) {
                resp[index++] = arr[list.peekFirst()];
            }
        }
        return resp;
    }


    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        int N = arr.length;
        int index = 0;
        int[] resp = new int[N-w+1];
        int L = 0;
        int R = w-1;
        while (R < N) {
            int max = arr[L];
            for (int i = L+1; i <= R; i++) {
                max = Math.max(max, arr[i]);
            }
            resp[index++] = max;
            L++;
            R++;
        }
        return resp;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100_0000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans = right(arr, w);
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = getMaxWindow(arr, w);
            if (!isEqual(ans, ans1) || !isEqual(ans, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
