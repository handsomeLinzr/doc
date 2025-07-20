package coding.offer.day02;

/**
 *
 * 现有司机 N*2 人，调度中心会将所有司机平分给A B 两个区域
 * 第i个司机去A可以得到收入为income[i][0]
 * 第i个司机去B可以得到收入为income[i][1]
 * 返回所有调度中能让所有司机总收入最高的方案，是多少钱
 *
 * @author linzherong
 * @date 2025/7/13 22:45
 */
public class Drive {

    public static int maxMoney(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) != 0 || income[0].length != 2) {
            return 0;
        }
        return process(income, 0, income.length >> 1);
    }

    public static int process(int[][] incomes, int index, int rest) {
        if (index == incomes.length) {
            return 0;
        }
        if (rest == incomes.length - index) {
            return process(incomes, index+1, rest-1) + incomes[index][0];
        }
        if (rest == 0) {
            return process(incomes, index+1, rest) + incomes[index][1];
        }
        int a = process(incomes, index+1, rest-1) + incomes[index][0];
        int b = process(incomes, index+1, rest) + incomes[index][1];
        return Math.max(a, b);
    }

    public static int maxMoneyDp(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) != 0 ) {
            return 0;
        }
        int M = income.length;
        int N = income.length >> 1;
        int[][] dp = new int[M+1][N+1];
//        for (int rest = 0; rest <= N; rest++) {
//            dp[M][rest] = 0;
//        }
        for (int index = M-1; index >= 0 ; index--) {
            for (int rest = 0; rest <= N; rest++) {
                if (rest == M - index) {
                    // 给A区
                    dp[index][rest] = dp[index+1][rest-1] + income[index][0];
                } else if (rest == 0) {
                    // 给B区
                    dp[index][rest] = dp[index+1][rest] + income[index][1];
                } else {
                    dp[index][rest] = Math.max(dp[index+1][rest-1] + income[index][0], dp[index+1][rest] + income[index][1]);
                }
            }
        }
        return dp[0][N];
    }

    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }



    public static void main(String[] args) {
        int N = 10;
        int value = 100;
//        int testTime = 500;
//        System.out.println("测试开始");
//        for (int i = 0; i < testTime; i++) {
//            int len = (int) (Math.random() * N) + 1;
//            int[][] matrix = randomMatrix(len, value);
//            int ans1 = minMoney(matrix);
//            int ans2 = twoCitySchedCost(matrix);
//            if (ans1 != ans2 ) {
//                System.out.println(ans1);
//                System.out.println(ans2);
//                System.out.println("Oops!");
//            }
//        }

        int[][] a = {{259,770},{448,54},{926,667},{184,139},{840,118},{577,469}};
        System.out.println(minMoney(a));
        System.out.println(twoCitySchedCost(a));

        System.out.println("测试结束");
    }


    //公司计划面试 2n 人。给你一个数组 costs ，其中 costs[i] = [aCosti, bCosti] 。第 i 人飞往 a 市的费用为 aCosti ，飞往 b 市的费用为 bCosti 。
    //
    //返回将每个人都飞到 a 、b 中某座城市的最低费用，要求每个城市都有 n 人抵达。

    // 2 * n == costs.length
    // 2 <= costs.length <= 100
    // costs.length 为偶数
    // 1 <= aCosti, bCosti <= 1000

    // https://leetcode.cn/problems/two-city-scheduling/
    public static int twoCitySchedCost(int[][] costs) {
        if (costs == null || costs.length == 0) {
            return 0;
        }
        int M = costs.length;
        int N = costs.length >> 1;
        // 从第 i 位置开始到最后，还剩A城市rest，最小花费
        int[][] dp = new int[M+1][N+1];
        // baseCase
//        for (int rest = 0; rest <= N ; rest++) {
//            dp[M][rest] = 0;
//        }
        for (int i = M-1; i >= 0; i--) {
            for (int rest = 0; rest <= N; rest++) {
                if (rest == 0) {
                    // A city 没有名额了
                    dp[i][rest] = dp[i+1][rest] + costs[i][1];
                } else if (rest == M - i) {
                    // rest 名额都是A city 的
                    dp[i][rest] = dp[i+1][rest-1] + costs[i][0];
                } else {
                    dp[i][rest] = Math.min(dp[i+1][rest] + costs[i][1], dp[i+1][rest-1] + costs[i][0]);
                }
            }
        }
        return dp[0][N];
    }

    public static int minMoney(int[][] income) {
        if (income == null || income.length == 0 || (income.length & 1) != 0 || income[0].length != 2) {
            return 0;
        }
        return processMin(income, 0, income.length >> 1);
    }

    public static int processMin(int[][] incomes, int index, int rest) {
        if (index == incomes.length) {
            return 0;
        }
        if (rest == incomes.length - index) {
            return processMin(incomes, index+1, rest-1) + incomes[index][0];
        }
        if (rest == 0) {
            return processMin(incomes, index+1, rest) + incomes[index][1];
        }
        int a = processMin(incomes, index+1, rest-1) + incomes[index][0];
        int b = processMin(incomes, index+1, rest) + incomes[index][1];
        return Math.min(a, b);
    }

}
