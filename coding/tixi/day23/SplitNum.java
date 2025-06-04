package coding.tixi.day23;

import java.util.ArrayList;

/**
 *
 * 给定一个数字，要求将这个数进行裂开成多个正数
 * 要求裂开的数必须从小到大，不能后边的小于前边的
 * 有多少种裂开方式
 *
 * @author linzherong
 * @date 2025/6/2 22:13
 */
public class SplitNum {

    public static int splitNum(int num) {
        if (num < 0) {
            return 0;
        }
        return process(1, num);
    }

    // curSplit 当前往前分裂的数
    // rest  剩余的数
    public static int process(int curSplit, int rest) {
        if (rest == 0 || curSplit == rest) {
            return 1;
        }
        if (curSplit > rest) {
            return 0;
        }
        int way = 0;
        for (int i = curSplit; i <= rest; i++) {
            way += process(i, rest-i);
        }
        return way;
    }

    public static int splitNumDp(int num) {
        if (num < 0) {
            return 0;
        }
        int[][] dp = new int[num+1][num+1];
        for (int split = 1; split <= num ; split++) {
            dp[split][0] = 1;
            dp[split][split] = 1;
        }
        for (int split = num-1; split >= 1; split--) {
            for (int rest = split+1; rest <= num ; rest++) {
                int way = 0;
                for (int i = split; i <= rest; i++) {
                    way += dp[i][rest-i];
                }
                dp[split][rest] = way;
            }
        }
        return dp[1][num];
    }

    public static int splitNumDp1(int num) {
        if (num < 0) {
            return 0;
        }
        int[][] dp = new int[num+1][num+1];
        for (int split = 1; split <= num ; split++) {
            dp[split][0] = 1;
            dp[split][split] = 1;
        }
        for (int split = num-1; split >= 1; split--) {
            for (int rest = split+1; rest <= num ; rest++) {
                dp[split][rest] = dp[split+1][rest] + dp[split][rest-split];
            }
        }
        return dp[1][num];
    }



    public static int dp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        for (int i = 0; i < 10_0000; i++) {
            int test = (int) (Math.random() * 30)+1;
            int ans = dp2(test);
            int ans1 = splitNum(test);
            int ans2 = splitNumDp(test);
            int ans3 = splitNumDp1(test);
            if (ans != ans1 || ans != ans2 || ans != ans3) {
                System.out.println("Oops");
                System.out.println(test);
                System.out.println(ans);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                return;
            }
        }
        System.out.println("测试结束");
    }

}
