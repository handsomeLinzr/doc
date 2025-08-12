package coding.offer.day03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * // 给定一个数组arr，代表每个人的能力值。再给定一个非负数k。
 * // 如果两个人能力差值正好为k，那么可以凑在一起比赛，一局比赛只有两个人
 * // 返回最多可以同时有多少场比赛
 *
 * @author linzherong
 * @date 2025/7/17 00:37
 */
public class MaxPairNumber {

    // 暴力解
    public static int maxPairNum1(int[] arr, int k) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        return process(arr, 0, k);
    }

    // 全排列，把每个位置的数据都和其他位置交换一遍
    private static int process(int[] arr, int index, int k) {
        int ans = 0;
        if (index == arr.length) {
            for (int i = 1; i < arr.length; i+=2) {
                if (arr[i] - arr[i-1] == k) {
                    ans++;
                }
            }
        } else {
            for (int i = index; i < arr.length; i++) {
                swap(arr, index, i);
                ans = Math.max(ans, process(arr, index+1, k));
                swap(arr, index, i);
            }
        }
        return ans;
    }

    // 解法2
    public static int maxPairNum2(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        boolean[] visited = new boolean[arr.length];
        return process2(arr, 0, k, visited, new ArrayList<>());
    }

    private static int process2(int[] arr, int index, int k, boolean[] visited, List<Integer> list) {
        int ans = 0;
        if (index == arr.length) {
            for (int i = 1; i < arr.length; i+=2) {
                if (list.get(i) - list.get(i-1) == k) {
                    ans++;
                }
            }
        } else {
            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    list.add(arr[i]);
                    ans = Math.max(ans, process2(arr, index+1, k, visited, list));
                    visited[i] = false;
                    list.remove(list.size()-1);
                }
            }
        }
        return ans;
    }

    private static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }



    public static int maxPairNum(int[] arr, int k) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        // 排序
        Arrays.sort(arr);
        int L = 0;
        int R = 1;
        int ans = 0;
        boolean[] visited = new boolean[arr.length];
        while (L < arr.length && R < arr.length) {
            if (visited[L]) {
                L++;
                continue;
            }
            if (L == R) {
                R++;
                continue;
            }
            if (arr[R] - arr[L] == k) {
                ans++;
                visited[L] = true;
                visited[R] = true;
                L++;
                R++;
            } else if (arr[R] - arr[L] < k) {
                R++;
            } else {
                L++;
            }
        }
        return ans;
    }

    // 为了测试
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int maxK = 5;
        int testTime = 1000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * (maxLen + 1));
            int[] arr = randomArray(N, maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int k = (int) (Math.random() * (maxK + 1));
            int ans1 = maxPairNum1(arr1, k);
            int ans2 = maxPairNum(arr2, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

}
