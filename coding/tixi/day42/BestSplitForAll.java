package coding.tixi.day42;

import coding.tixi.ArrayUtils;

/**
 *
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多种方案中，min{左部分累加和，右部分累加和}的最大值是多少啊？
 * 解题过程要求时间复杂度是O(N)
 *
 * @author linzherong
 * @date 2025/7/2 00:42
 */
public class BestSplitForAll {

    public static int bestSplit(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int ans = 0;
        for (int s = 0; s < N - 1; s++) {
            int sumL = 0;
            for (int L = 0; L <= s; L++) {
                sumL += arr[L];
            }
            int sumR = 0;
            for (int R = s + 1; R < N; R++) {
                sumR += arr[R];
            }
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    public static int bestSplit1(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        // 前缀和
        int[] preSum = new int[arr.length+1];
        preSum[0] = 0;
        for (int i = 0; i < arr.length; i++) {
            preSum[i+1] = preSum[i] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length-1; i++) {
            int leftSum = preSum[i+1];
            int rightSum = preSum[arr.length] - preSum[i+1];
            max = Math.max(max, Math.min(leftSum, rightSum));
        }
        return max;
    }


    public static void main(String[] args) {
        int testTime = 100_0000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtils.generalArr(300, 20);
            int ans = bestSplit(arr);
            int ans1 = bestSplit1(arr);
            if (ans != ans1) {
                System.out.println("Oops");
            }
        }
        System.out.println("over");
    }



}
