package coding.tixi.day25;

import java.util.LinkedList;

/**
 *
 * 子数组中最大值减最小值不大于num的数量
 *
 * @author linzherong
 * @date 2025/6/9 00:21
 */
public class AllLessNumSubArray {

    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length < 1 || sum < 0) {
            return 0;
        }
        LinkedList<Integer> maxList = new LinkedList<>();
        LinkedList<Integer> minList = new LinkedList<>();
        int N = arr.length;
        int R = 0;
        int result = 0;
        // 左边从0到N
        for (int i = 0; i < N; i++) {
            // 窗口右边
            while (R < N) {
                // 最大值
                while (!maxList.isEmpty() && arr[maxList.peekLast()] <= arr[R]) {
                    maxList.pollLast();
                }
                maxList.addLast(R);
                // 最小值
                while (!minList.isEmpty() && arr[minList.peekLast()] >= arr[R]) {
                    minList.pollLast();
                }
                minList.addLast(R);
                if (arr[maxList.peekFirst()] - arr[minList.peekFirst()] <= sum) {
                    R++;
                } else {
                    break;
                }
            }
            // 累计数量，起点是i，右是i 到 R-1，组成的每个子序列都符合
            result += R - i;
            // 左边窗口往前缩进
            if (maxList.peekFirst() == i) {
                maxList.pollFirst();
            }
            if (minList.peekFirst() == i) {
                minList.pollFirst();
            }
        }
        return result;
    }

    // 暴力的对数器方法
    public static int right(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (getMax(arr, i, j) - getMin(arr, i, j) <= num) {
                    result++;
                }
            }
        }
        return result;
    }

    public static int getMax(int[] arr, int start, int end) {
        int max = arr[start];
        for (int i = start+1; i <= end; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }
    public static int getMin(int[] arr, int start, int end) {
        int min = arr[start];
        for (int i = start+1; i <= end; i++) {
            min = Math.min(min, arr[i]);
        }
        return min;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 50;
        int maxValue = 200;
        int testTime = 100_0000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
