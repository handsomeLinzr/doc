package coding.tixi.day42;

import coding.tixi.ArrayUtils;

import java.util.Arrays;

/**
 *
 * https://leetcode.com/problems/split-array-largest-sum/
 *
 * 四边形不等式
 * 二分法
 *
 * @author linzherong
 * @date 2025/7/2 21:42
 */
public class SplitArrayLargestSum {

    // 0|0-N   0-0|1-N   0-1|2-N  ...  0-N|0
    public static int splitArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 累加和
        int[] preSum = new int[nums.length+1];
        for (int i = 0; i < nums.length ; i++) {
            preSum[i+1] = preSum[i] + nums[i];
        }
        return process(nums, preSum, nums.length-1, k);
    }
    // 从左到右的范围模型
    public static int process(int[] nums, int[] preSum, int limit, int k) {
        if (limit == 0) {
            return nums[0];
        }
        if (k == 1) {
            return preSum[limit+1];
        }
        int min = Integer.MAX_VALUE;
        for (int split = 0; split < limit; split++) {
            int left = process(nums, preSum, split, k-1);
            int right = preSum[limit+1] - preSum[split+1];
            min = Math.min(min, Math.max(left, right));
        }
        return min;
    }

    // 以k-1 和 k 作左右分割
    public static int splitArray1(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // 前缀和
        int[] preSum = new int[N+1];
        for (int i = 0; i < N; i++) {
            preSum[i+1] = preSum[i] + nums[i];
        }
        int[][] dp = new int[N][k+1];
        for (int j = 1; j <= k; j++) {
            dp[0][j] = nums[0];
        }
        for (int limit = 1; limit < N; limit++) {
            dp[limit][1] = preSum[limit+1];
        }
        for (int limit = 1; limit < N; limit++) {
            for (int j = 2; j <= k; j++) {
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < limit; i++) {
                    int left = dp[i][j-1];
                    int right = preSum[limit+1] - preSum[i+1];
                    min = Math.min(min, Math.max(left, right));
                }
                dp[limit][j] = min;
            }
        }
        return dp[N-1][k];
    }

    public static int splitArray2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int[][] dp = new int[N][k+1];
        int[] preSum = new int[N+1];
        int[][] base = new int[N][k+1];
        for (int i = 0; i < N; i++) {
            preSum[i+1] = preSum[i] + nums[i];
        }
        // 只到0号数据，其他都没得延伸
        for (int i = 1; i <= k; i++) {
            dp[0][i] = nums[0];
        }
        // 全部都合成一个的情况
        for (int i = 0; i < N; i++) {
            dp[i][1] = preSum[i+1];
        }

        for (int j = 2; j <= k; j++) {
            for (int limit = N-1; limit >= 1 ; limit--) {
                int down = base[limit][j-1];
                int up = limit == N-1? N-1:base[limit+1][j];
                int min = Integer.MAX_VALUE;
                int baseIndex = -1;
                for (int i = down; i <= up; i++) {
                    int left = dp[i][j-1];
                    int right = preSum[limit+1] - preSum[i+1];
                    if (min > Math.max(left, right)) {
                        min = Math.max(left, right);
                        baseIndex = i;
                    }
                }
                dp[limit][j] = min;
                base[limit][j] = baseIndex;
            }
        }
        return dp[N-1][k];
    }

    // 二分法
    public static int splitArray3(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // nums累加
        long total = nums[0];
        for (int i = 1; i < nums.length; i++) {
            total += nums[i];
        }
        // 开始进行二分尝试，对半尝试结果
        long left = 0;
        long right = total;
        long ans = 0;
        while (left <= right) {
            // 当前的尝试
            long middle = (left+right) >> 1;
            // 需要的最少分组
            int part = getNeedParts(nums, middle);
            // part <= k 则满足条件，继续往小的方向压缩
            if (part <= k) {
                ans = middle;
                // 继续压缩
                right = middle-1;
            } else {
                // part > k 不满足条件了，需要将结果放大
                left = middle+1;
            }
        }
        return (int) ans;
    }

    public static int getNeedParts(int[] nums, long middle) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > middle) {
                return Integer.MAX_VALUE;
            }
        }
        int part = 1;
        long all = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (all + nums[i] > middle) {
                all = nums[i];
                part++;
            } else {
                all += nums[i];
            }
        }
        return part;
    }

    public static int splitArray4(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // 前缀和
        int[] preSum = new int[N+1];
        int[] preMax = new int[N];
        preMax[0] = nums[0];
        for (int i = 0; i < nums.length; i++) {
            preSum[i+1] = preSum[i] + nums[i];
            if (i > 0) {
                preMax[i] = Math.max(nums[i], preMax[i - 1]);
            }
        }
        int[][] dp = new int[N][k+1];
        for (int i = 0; i < N; i++) {
            dp[i][1] = preSum[i+1];
        }
        for (int i = 1; i < Math.min(N, k); i++) {
            dp[i][i+1] = preMax[i];
        }
        for (int j = 2; j <= k ; j++) {
            for (int i = j; i < N; i++) {
                int min = Integer.MAX_VALUE;
                for (int l = j-2; l <= i-1; l++) {
                    min = Math.min(min, Math.max(dp[l][j-1], preSum[i+1]-preSum[l+1]));
                }
                dp[i][j] = min;
            }
        }
        return dp[N-1][k];
    }

    public static int splitArray5(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // 前缀和
        int[] preSum = new int[N+1];
        int[] preMax = new int[N];
        preMax[0] = nums[0];
        for (int i = 0; i < nums.length; i++) {
            preSum[i+1] = preSum[i] + nums[i];
            if (i > 0) {
                preMax[i] = Math.max(nums[i], preMax[i - 1]);
            }
        }
        int[][] dp = new int[N][k+1];
        // 最好位置
        int[][] base = new int[N][k+1];
        // j == i-1，极限位置，每个人都负责一个位置
        for (int i = 0; i < N; i++) {
            dp[i][1] = preSum[i+1];
            base[i][1] = 0;
        }
        // 第 1 列，只能有一个分组的情况
        for (int i = 1; i < Math.min(N, k); i++) {
            dp[i][i+1] = preMax[i];
            base[i][i+1] = i-1;
        }
        // 从第2列开始
        for (int j = 2; j <= k ; j++) {
            for (int i = N-1; i >= j ; i--) {
                int min = Integer.MAX_VALUE;
                int baseIndex = 0;
                // j-2 ==>> i能向左的极限，左边必须最少一个数据一个分组
                int baseBegin = Math.max(base[i][j-1], j-2);
                // i-1 ==>> i能向右的极限，必须最少剩下一个数据给最后一个分组
                int baseEnd = i == N-1? i-1 : Math.min(i-1, base[i+1][j]);
                for (int l = baseBegin; l <= baseEnd; l++) {
                    if (min > Math.max(dp[l][j-1], preSum[i+1]-preSum[l+1])) {
                        min = Math.max(dp[l][j-1], preSum[i+1]-preSum[l+1]);
                        baseIndex = l;
                    }
                }
                dp[i][j] = min;
                base[i][j] = baseIndex;
            }
        }
        return dp[N-1][k];
    }


    public static void main(String[] args) {
        System.out.println(splitArray1(new int[]{8, 4, 7, 8}, 3));
        System.out.println(splitArray(new int[]{8, 4, 7, 8}, 3));
        System.out.println(splitArray2(new int[]{8, 4, 7, 8}, 3));
        System.out.println(splitArray3(new int[]{8, 4, 7, 8}, 3));
        System.out.println(splitArray4(new int[]{8, 4, 7, 8}, 3));
        System.out.println(splitArray5(new int[]{8, 4, 7, 8}, 3));

    }


}
