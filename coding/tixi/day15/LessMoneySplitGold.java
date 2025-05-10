package coding.tixi.day15;

import coding.xin.ArrayUtils;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 分金问题
 * 要求将一块金条切分成 arr 数组，每次切割都会消耗切割前金条的money，如 5——>2,3 消耗 5，要求返回最小损耗
 * @author linzherong
 * @date 2025/5/5 23:24
 */
public class LessMoneySplitGold {

    // 每次都获取最小的两个进行合并
    public static int lessMoney(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            queue.add(arr[i]);
        }
        int ans = 0;
        while (queue.size() > 1) {
            int cur = queue.poll() + queue.poll();
            ans += cur;
            queue.add(cur);
        }
        return ans;
    }

    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process1(arr);
    }
    public static int process1(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                int[] mergeArray = mergeTwoIndex(arr, i, j);
                ans = Math.min(ans, arr[i] + arr[j] + process1(mergeArray));
            }
        }
        return ans;
    }
    public static int[] mergeTwoIndex(int[] arr, int a, int b) {
        int[] array = new int[arr.length-1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != a && i != b) {
                array[index++] = arr[i];
            }
        }
        array[index] = arr[a] + arr[b];
        return array;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            int[] arr = ArrayUtils.generalArrayPositive(5, 20);
            if (lessMoney1(arr) != lessMoney(arr)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
