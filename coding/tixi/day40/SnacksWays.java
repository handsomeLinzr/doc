package coding.tixi.day40;

import coding.tixi.ArrayUtils;

/**
 *
 * 一共有 n 袋零食，第 i 袋零食的体积是 v[i]，背包容量是 w
 * 要求在总体积不超过 w 的情况下，一共有多少种零食的放法，体积 0 也是一种
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9
 * 0 <= v[i] <= 10^9
 *
 * @author linzherong
 * @date 2025/6/30 21:53
 */
public class SnacksWays {

    public static int ways(int[] arr, int w) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, w);
    }
    public static int process(int[] v, int index, int rest) {
        if (index == v.length) {
            return 1;
        }
        int p1 = 0;
        if (rest >= v[index]) {
            p1 = process(v, index + 1, rest - v[index]);
        }
        int p2 = process(v, index + 1, rest);
        return p1 + p2;
    }

    public static int ways1(int[] arr, int w) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][w+1];
        for (int i = 0; i <= w; i++) {
            dp[N][i] = 1;
        }
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = dp[i+1][j];
                if (j >= arr[i]) {
                    dp[i][j] += dp[i+1][j-arr[i]];
                }
            }
        }
        return dp[0][w];
    }


    public static void main(String[] args) {
        System.out.println("start");
        for (int i = 0; i < 10000; i++) {
            int[] arr = ArrayUtils.generalArr(20);
            int w = (int) (Math.random() * 30 * arr.length);
            int ways = ways(arr, w);
            int ways1 = ways1(arr, w);
            if (ways != ways1) {
                System.out.println("Oops");
                return;
            }
        }
        System.out.println("finish");
    }

}
