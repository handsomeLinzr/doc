package coding.tixi.day19;

/**
 * 机器人走路，要求获取走路的路径：有N个点，机器人从start开始走，通过K个移动后，到aim目的，一共有多少种路径
 * 1.普通暴力递归
 * 2.暴力递归+缓存（动态规划）
 *
 * @author linzherong
 * @date 2025/5/25 21:01
 */
public class RobotWalk {

    // 递归版本
    public static int ways1(int N, int start, int aim, int K) {
        if (N<2 || start<1 || aim<1 || start>N || aim>N || K<0 ) {
            return -1;
        }
        return process1(N, start, aim, K);
    }

    public static int process1(int all, int start, int aim, int rest) {
        // 边界条件
        if (rest == 0) {
            return start == aim? 1 : 0;
        }
        if (start == 1) {
            return process1(all, 2, aim, rest-1);
        }
        if (start == all) {
            return process1(all, all-1, aim, rest-1);
        }
        return process1(all, start-1, aim, rest-1) + process1(all, start+1, aim, rest-1);
    }

    // 递归+缓存
    public static int ways2(int N, int start, int aim, int K) {
        if (N<2 || start<1 || aim<1 || K<0 || start>N || aim>N) {
            return -1;
        }
        int[][] dp = new int[N+1][K+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(N, start, aim, K, dp);
    }
    public static int process2(int all, int start, int aim, int rest, int[][] dp) {
        if (dp[start][rest] != -1) {
            return dp[start][rest];
        }
        int result;
        if (rest == 0) {
            result = start == aim? 1 : 0;
        } else if (start == 1) {
            result = process2(all, start+1, aim, rest-1, dp);
        } else if (start == all) {
            result = process2(all, all-1, aim, rest-1, dp);
        } else {
            result = process2(all, start-1, aim, rest-1, dp) + process2(all, start+1, aim, rest-1, dp);
        }
        dp[start][rest] = result;
        return result;
    }

    // 直接生成缓存 = 动态规划
    public static int ways3(int N, int start, int aim, int K) {
        if (N<2 || start<1 || aim<1 || K<0 || start>N || aim>N) {
            return -1;
        }
        int[][] dp = new int[N+1][K+1];
        // start == aim时， K == 0 则为1
        dp[aim][0] = 1;
        for (int i = 1; i <= K; i++) {
            // start = 1, 则等于  start=2,K=K-1 情况
            dp[1][i] = dp[2][i-1];
            for (int j = 2; j < N; j++) {
                // 其他，等于 start+1,K-1   +    start-1,K-1   两种的和
                dp[j][i] = dp[j-1][i-1] + dp[j+1][i-1];
            }
            // start = N, 则等于 start=N-1,K=K-1 情况
            dp[N][i] = dp[N-1][i-1];
        }
        return dp[start][K];
    }
    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }

}
