package coding.tixi.day27;

/**
 *
 * https://leetcode.cn/problems/sum-of-subarray-minimums/description/
 * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
 * 3种解法
 * 1.暴力破解
 * 2.用 leftNearLessEqual2 和 rightNearLess2 两个方法
 * 3.单调栈
 *
 * @author linzherong
 * @date 2025/6/17 00:45
 */
public class SumOfSubarrayMinimums {

    // 单调栈
    public int sumSubarrayMins(int[] arr) {
        long result = 0;
        int N = arr.length;
        int[] stack = new int[N];
        int index = -1;
        for (int i = 0; i < N; i++) {
            while ( index >= 0 && arr[stack[index]] >= arr[i] ) {
                int cur = stack[index--];
                int left = index < 0? -1 : stack[index];
                result += (long) arr[cur] * (cur - left) * (i - cur);
                result %= 1000000007;
            }
            stack[++index] = i;
        }
        while (index >= 0) {
            int cur = stack[index--];
            int left = index < 0? -1 : stack[index];
            result += (long) arr[cur] * (cur - left) * (N - cur);
            result %= 1000000007;
        }
        return (int) result;
    }

    // 暴力破解
    public int sumSubarrayMins1(int[] arr) {
        int N = arr.length;
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int min = arr[i];
                for (int k = i+1; k <= j; k++) {
                    // 获取最小值
                    min = Math.min(arr[k], min);
                }
                result = (result+min) % 1000000007;
            }
        }
        return result;
    }

    // 用 leftNearLessEqual2 和 rightNearLess2 两个方法
    public int sumSubarrayMins2(int[] arr) {
        int N = arr.length;
        long result = 0;
        for (int i = 0; i < N; i++) {
            int left = leftNearLessEqual2(arr, i);
            int right = rightNearLess2(arr, i);
            result = ((long) arr[i] * (i - left + 1) * (right - i + 1)) + result;
        }
        return (int) (result % 1000000007);
    }
    // 左边大于等于
    public int leftNearLessEqual2(int[] arr, int index) {
        int result = index;
        for (int i = index - 1; i >= 0 ; i--) {
            if (arr[i] < arr[index]) {
                return result;
            } else {
                result = i;
            }
        }
        return result;
    }
    // 右边大于不等于
    public int rightNearLess2(int[] arr, int index) {
        int result = index;
        for (int i = index + 1; i < arr.length; i++) {
            if (arr[i] <= arr[index]) {
                return result;
            } else {
                result = i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SumOfSubarrayMinimums minimums = new SumOfSubarrayMinimums();
        System.out.println(minimums.sumSubarrayMins(new int[]{3, 1, 2, 4}));
    }

}
