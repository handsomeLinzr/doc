package coding.tixi.day20;

/**
 *
 * 背包问题
 *
 * @author linzherong
 * @date 2025/5/27 13:10
 */
public class Bag {

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
        System.out.println(maxValue3(weights, values, bag));
        System.out.println(dp3(weights, values, bag));
    }

    public static int maxValue(int[] w, int[] v, int bag) {
        if (w.length == 0 || v.length == 0 || w.length != v.length || bag <= 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    public static int process(int[] w, int[] v, int index, int rest ) {
        if (index == w.length) {
            return 0;
        }
        if (w[index] > rest) {
            return process(w, v, index+1, rest);
        } else {
            return Math.max(process(w, v, index+1, rest), v[index] + process(w, v, index+1, rest-w[index]));
        }
    }

    public static int dp(int[] w, int[] v, int bag) {
        if (w.length == 0 || v.length == 0 || w.length != v.length || bag <= 0) {
            return 0;
        }
        int[][] dp = new int[w.length+1][bag+1];
        for (int i = w.length-1; i >= 0; i--) {
            for (int j = 0; j < bag+1; j++) {
                if (w[i] > j) {
                    dp[i][j] = dp[i+1][j];
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], v[i] + dp[i+1][j-w[i]]);
                }
            }
        }
        return dp[0][bag];
    }

    public static int maxValue3(int[] w, int[] v, int bag) {
        if (w.length == 0 || v.length == 0 || w.length != v.length || bag <= 0) {
            return 0;
        }
        return process3(w, v, 0, bag);
    }

    public static int process3(int[] w, int[] v, int index, int rest ) {
        if (rest < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        int p1 = process(w, v, index+1, rest);
        int p2 = process(w, v, index+1, rest-w[index]);
        // 如果-1，则为0，0表示没有放入
        p2 = p2 != -1? v[index] + p2 : 0;
        return Math.max(p1, p2);
    }

    public static int dp3(int[] w, int[] v, int bag) {
        if (w.length == 0 || v.length == 0 || w.length != v.length || bag <= 0) {
            return 0;
        }
        int[][] dp = new int[w.length+1][bag+1];
        // 因为 w.length 位置已知值（0），所以从 w.length - 1 位置开始
        // 从下往上
        for (int i = w.length-1; i >= 0; i--) {
            // 从左往右
            for (int j = 0; j <= bag; j++) {
                int p1 = dp[i+1][j];
                // 如果-1，则为0，0表示没有放入
                int p2 = w[i] > j? 0 : v[i] + dp[i+1][j-w[i]];
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

}
