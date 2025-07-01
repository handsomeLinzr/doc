package coding.tixi.day39;

/**
 *
 * 	// int[] d d[i]：i号怪兽的武力
 * 	// int[] p p[i]：i号怪兽要求的钱
 * 	// ability 当前你所具有的能力
 * 	// index 来到了第index个怪兽的面前
 *
 * 	// 目前，你的能力是ability，你来到了index号怪兽的面前，如果要通过后续所有的怪兽，
 * 	// 请返回需要花的最少钱数
 *
 *
 * @author linzherong
 * @date 2025/6/30 14:13
 */
public class MoneyProblem {

    // 暴力递归 O(2^N)
    public static long func1(int[] d, int[] p) {
        if (d.length != p.length) {
            return -1;
        }
        return process1(d, p, 0, 0);
    }
    public static long process1(int[] abilitys, int[] moneys, int index, int ability) {
        if (index == abilitys.length) {
            // 结束，通过index+后续的所有怪兽需要的钱是0
            return 0;
        }
        long money = process1(abilitys, moneys, index+1, ability + abilitys[index]) + moneys[index];
        if (ability >= abilitys[index]) {
            money = Math.min(money, process1(abilitys, moneys, index+1, ability));
        }
        return money;
    }

    // 转动态规划
    public static long func2(int[] d, int[] p) {
        if (d.length != p.length) {
            return -1;
        }
        int N = d.length;
        int abilityTotal = 0;
        for (int num : d) {
            abilityTotal += num;
        }
        long[][] dp = new long[N+1][abilityTotal+1];
        for (int i = 0; i <= abilityTotal; i++) {
            dp[N][i] = 0;
        }
        for (int index = N-1; index >= 0 ; index--) {
            for (int ability = 0; ability <= abilityTotal ; ability++) {
                // 越界不会存在的，不管
                if (ability+d[index] <= abilityTotal) {
                    dp[index][ability] = dp[index+1][ability+d[index]] + p[index];
                    if (ability >= d[index]) {
                        dp[index][ability] = Math.min(dp[index][ability], dp[index+1][ability]);
                    }
                }
            }
        }
        return dp[0][0];
    }

    // func2的情况下，y轴（ability）如果数量级太大，或者是数字太大，则会导致越界
    // 此时需要考虑修改方向，将 money 作为 y轴
    public static long func3(int[] d, int[] p) {
        if (d.length != p.length) {
            return -1;
        }
        int totalMoney = 0;
        for (int num : p) {
            totalMoney += num;
        }
        for (int i = 0; i < totalMoney; i++) {
            if (process3(d, p, d.length-1, i) != -1) {
                return i;
            }
        }
        return totalMoney;
    }
    // 通过 index 的时候，刚好花费 costMoney，能达到的最大能力，如果不存在返回-1
    public static long process3(int[] ability, int[] money, int index, int costMoney) {
        if (index == -1) {
            // 连0号都没遇到，不花钱
            return costMoney == 0? 0 : -1;
        }
        // index 不需要花钱
        long a1 = process3(ability, money, index-1, costMoney);
        if (a1 < ability[index]) {
            a1 = -1;
        }
        // index 花钱
        long a2 = process3(ability, money, index-1, costMoney - money[index]);
        if (a2 != -1) {
            a2 += ability[index];
        }
        return Math.max(a1,a2);
    }
    // func3转动态规划
    public static long func4(int[] d, int[] p) {
        if (d.length != p.length) {
            return -1;
        }
        int N = d.length;
        int totalMoney = 0;
        for (int num : p) {
            totalMoney += num;
        }
        long[][] dp = new long[N][totalMoney+1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= totalMoney; j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][p[0]] = d[0];
        for (int index = 1; index < N; index++) {
            for (int costMoney = 0; costMoney <= totalMoney; costMoney++) {
                long a1 = dp[index-1][costMoney];
                if (a1 < d[index]) {
                    a1 = -1;
                }
                dp[index][costMoney] = a1;
                if (costMoney-p[index] >= 0) {
                    long a2 = dp[index-1][costMoney-p[index]];
                    if (a2 != -1) {
                        a2 += d[index];
                    }
                    dp[index][costMoney] = Math.max(a1, a2);
                }
            }
        }
        for (int costMoney = 0; costMoney < totalMoney; costMoney++) {
            if (dp[N-1][costMoney] != -1) {
                return costMoney;
            }
        }
        return totalMoney;
    }

    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 100_0000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
            long ans4 = func4(d,p);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("oops!");
            }
        }

    }


}
