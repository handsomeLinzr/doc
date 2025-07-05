package coding.tixi.day42;

import coding.tixi.ArrayUtils;

/**
 *
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 右边界从0到最后的过程中，每一次的最优划分
 * 解题过程要求时间复杂度是O(N)
 *
 * @author linzherong
 * @date 2025/7/2 08:11
 */
public class BestSplitForEveryPosition {

    public static int[] bestSplit(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        for (int range = 1; range < N; range++) {
            for (int s = 0; s < range; s++) {
                int sumL = 0;
                for (int L = 0; L <= s; L++) {
                    sumL += arr[L];
                }
                int sumR = 0;
                for (int R = s + 1; R <= range; R++) {
                    sumR += arr[R];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    public static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        // 前缀和
        int[] help = new int[arr.length+1];
        for (int i = 0; i < arr.length; i++) {
            help[i+1] = help[i] + arr[i];
        }
        int[] res = new int[arr.length];
        res[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            int max = 0;
            // j ==> 切分的位置
            for (int j = 0; j < i; j++) {
                max = Math.max(max, Math.min(help[j+1], help[i+1]-help[j+1]));
            }
            res[i] = max;
        }
        return res;
    }

    public static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] help = new int[arr.length+1];
        for (int i = 0; i < arr.length; i++) {
            help[i+1] = help[i] + arr[i];
        }
        int[] ans = new int[arr.length];
        // 最好的切割位置
        int[] base = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int max = 0;
            int baseIndex = base[i];
            for (int j = baseIndex; j < arr.length; j++) {
                int curMax = Math.min(help[j + 1], help[i+1] - help[j + 1]);
                if (max < curMax) {
                    max = curMax;
                    baseIndex = j;
                }
            }
            ans[i] = max;
            base[i] = baseIndex;
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100_0000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtils.generalArr(20);
            int[] ans = bestSplit(arr);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            if (!isSameArray(ans, ans1) || !isSameArray(ans, ans2)) {
                System.out.println("Oops");
            }
        }
        System.out.println("over");
    }


}
