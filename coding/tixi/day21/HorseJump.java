package coding.tixi.day21;

/**
 *
 * 象棋马，走K步后到达（x,y）的方法有多少种
 * 10 * 9 的表中
 *
 * @author linzherong
 * @date 2025/5/30 22:44
 */
public class HorseJump {

    public static int jump(int x, int y, int K) {
        if (x < 0 || y < 0 || K < 0) {
            return 0;
        }
        return process(0, 0, x, y, K);
    }

    public static int process(int i, int j, int x, int y, int rest) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            // 出界
            return 0;
        }
        if (rest == 0) {
            return i == x && j == y? 1: 0;
        }
        return process(i+1,j+2, x, y, rest-1)
                + process(i+2,j+1, x, y, rest-1)
                + process(i+2,j-1, x, y, rest-1)
                + process(i+1,j-2, x, y, rest-1)
                + process(i-1,j-2, x, y, rest-1)
                + process(i-2,j-1, x, y, rest-1)
                + process(i-2,j+1, x, y, rest-1)
                + process(i-1,j+2, x, y, rest-1);
    }

    public static int jump2(int x, int y, int K) {
        if (x < 0 || y < 0 || K < 0) {
            return 0;
        }
        int[][][] dp = new int[10][9][K+1];
        return process2(0, 0, x, y, K, dp);
    }

    public static int process2(int i, int j, int x, int y, int rest, int[][][] dp) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            // 出界
            return 0;
        }
        if (dp[i][j][rest] != 0) {
            return dp[i][j][rest];
        }
        int result;
        if (rest == 0) {
            result = i == x && j == y? 1: 0;
        } else {
            result = process2(i + 1, j + 2, x, y, rest - 1, dp)
                    + process2(i + 2, j + 1, x, y, rest - 1, dp)
                    + process2(i + 2, j - 1, x, y, rest - 1, dp)
                    + process2(i + 1, j - 2, x, y, rest - 1, dp)
                    + process2(i - 1, j - 2, x, y, rest - 1, dp)
                    + process2(i - 2, j - 1, x, y, rest - 1, dp)
                    + process2(i - 2, j + 1, x, y, rest - 1, dp)
                    + process2(i - 1, j + 2, x, y, rest - 1, dp);
        }
        dp[i][j][rest] = result;
        return result;
    }

    public static int jump3(int x, int y, int K) {
        if (x < 0 || y < 0 || K < 0) {
            return 0;
        }
        int[][][] dp = new int[10][9][K+1];
        dp[x][y][0] = 1;
        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    dp[i][j][k] = getNum(i+1, j+2,  k-1, dp)
                            + getNum(i+2,j+1,k-1, dp)
                            + getNum(i+2,j-1,k-1, dp)
                            + getNum(i+1,j-2,k-1, dp)
                            + getNum(i-1,j-2,k-1, dp)
                            + getNum(i-2,j-1,k-1, dp)
                            + getNum(i-2,j+1,k-1, dp)
                            + getNum(i-1,j+2,k-1, dp);
                }
            }
        }
        return dp[0][0][K];
    }

    public static int getNum(int i, int j, int k, int[][][] dp) {
        if (i < 0 || i > 9 || j < 0 || j > 8 || k < 0) {
            return 0;
        }
        return dp[i][j][k];
    }

    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }
    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(dp(x, y, step));
        System.out.println(jump(x, y, step));
        System.out.println(jump2(x, y, step));
        System.out.println(jump3(x, y, step));
    }

}
