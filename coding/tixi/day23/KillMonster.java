package coding.tixi.day23;

/**
 *
 * 砍怪兽
 * N  M  K
 * 怪兽有N滴血，英雄每次砍都会让怪兽流失 [0~M] 滴血（等概率）
 * 求K次砍后，把怪兽砍死的概率
 *
 * @author linzherong
 * @date 2025/6/2 17:19
 */
public class KillMonster {

    public static double kill(int N, int M, int K) {
        if (N <= 0 || M <= 0 || K <= 0) {
            return 0;
        }
        double total = Math.pow(M+1, K);
        double k = p1(N, M, K);
        return k/total;
    }

    // restN 剩余血量    restK 剩余次数
    public static double p1(int restN, int M, int restK) {
        if (restK == 0) {
            // 没有次数
            return restN == 0? 1 : 0;
        }
        if (restN == 0) {
            return Math.pow(M+1, restK);
        }
        double way = 0;
        for (int i = 0; i <= M; i++) {
            if (restN < i) {
                way += Math.pow(M+1, restK-1);
            } else {
                way += p1(restN - i, M, restK - 1);
            }
        }
        return way;
    }

    public static double dp1(int N, int M, int K) {
        if (N <= 0 || M <= 0 || K <= 0) {
            return 0;
        }
        double total = Math.pow(M+1, K);

        long[][] dp = new long[N+1][K+1];
        dp[0][0] = 1;
        for (int j = 1; j <= K ; j++) {
            dp[0][j] = (long) Math.pow(M+1, j);
            for (int i = 1; i <= N ; i++) {
                long way = 0;
                for (int m  = 0; m <= M; m++) {
                    if (m > i) {
                        way += (long) Math.pow(M+1, j-1);
                    } else {
                        way += dp[i-m][j-1];
                    }
                }
                dp[i][j] = way;
            }
        }
        return (double) dp[N][K]/total;
    }


    public static double dp2(int N, int M, int K) {
        if (N <= 0 || M <= 0 || K <= 0) {
            return 0;
        }
        double total = Math.pow(M+1, K);

        long[][] dp = new long[N+1][K+1];
        dp[0][0] = 1;
        for (int j = 1; j <= K ; j++) {
            dp[0][j] = (long) Math.pow(M+1, j);
            for (int i = 1; i <= N ; i++) {
                dp[i][j] = dp[i][j-1] + dp[i-1][j];
                if (M+1 <= i) {
                    // 每个数据一共需要累加 M+1 次，当 i 大于等于 M+1，则可以通过推送直接减去对应位置的数据
                    dp[i][j] -= dp[i-M-1][j-1];
                } else {
                    // 当 i 已经不足以减去 M+1，则减去两个数相差的 Math.pow(M+1, j-1)
                    dp[i][j] -= (long) Math.pow(M+1, j-1);
                }
            }
        }
        return (double) dp[N][K]/total;
    }

    public static double ans(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 20000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans = ans(N, M, K);
            double ans1 = kill(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans != ans1 || ans != ans2 || ans != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
