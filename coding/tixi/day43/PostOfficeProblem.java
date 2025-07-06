package coding.tixi.day43;

import java.util.Arrays;

/**
 *
 * 有一个数组，表示每个家庭的位置，再给出一个K，表示邮局数量
 * 解题得到最小的家庭邮局路径累加和
 *
 * @author linzherong
 * @date 2025/7/4 21:33
 */
public class PostOfficeProblem {

    public static int min(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int N = arr.length;
        int[][] w = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) >> 1];
            }
        }
        int[][] dp = new int[N][num + 1];
        for (int i = 0; i < N; i++) {
            dp[i][1] = w[0][i];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= Math.min(i, num); j++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k <= i; k++) {
                    ans = Math.min(ans, dp[k][j - 1] + w[k + 1][i]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[N - 1][num];
    }

    public static int min1(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num <= 0 || arr.length <= num) {
            return 0;
        }
        // 考虑1到num-1个邮局负责前边的家庭，num自己负责后边的家庭
        // 先处理一个邮局的情况，邮局是建在最中间的时候总记录最短, L...R 范围内，建在 （L+R）>> 1 最合适，如果偶数的情况，则中间两个位置一样
        int N = arr.length;
        int[][] w = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                // 中间位置，即邮局位置
                int middle = (i+j) >> 1;
                // 等于前一个的距离，再加上当前位置到中间位置的距离
                w[i][j] = w[i][j-1] + arr[j]-arr[middle];
            }
        }
        // 处理多个的情况,0到i==>> num-1个邮局， i+1到N-1==>>第num号邮局
        int[][] dp = new int[N][num+1];
        int[][] base = new int[N][num+1];
        // 一个邮局的情况
        for (int i = 1; i < N; i++) {
            dp[i][1] = w[0][i];
        }
        // 其他情况
        for (int j = 2; j <= num ; j++) {
            for (int i = N-1; i >= j ; i--) {
                int baseIndex = base[i][j-1];
                int rightIndex = i == N-1? N-2 : base[i+1][j];
                int min = Integer.MAX_VALUE;
                for (int k = baseIndex; k <= rightIndex ; k++) {
                    int curResult = dp[k][j-1] + w[k+1][i];
                    if (min > curResult) {
                        baseIndex = k;
                        min = curResult;
                    }
                }
                dp[i][j] = min;
                base[i][j] = baseIndex;
            }
        }
        return dp[N-1][num];
    }

    public static int[] generalArr(int time, int max) {
        int length = (int)(Math.random() * time) + 1;
        int[] arr  = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)(Math.random()*max) + 1;
        }
        Arrays.sort(arr);
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_0000; i++) {
            int[] arr = generalArr(20, 100);
            int num = (int) (Math.random() * arr.length) + 1;
            int ans = min(arr, num);
            int ans1 = min1(arr, num);
            if (ans != ans1) {
                System.out.println("Oops");
            }
        }
        System.out.println("Over");
    }


}
