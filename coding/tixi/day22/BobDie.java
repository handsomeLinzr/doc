package coding.tixi.day22;

/**
 *
 * 从 row,col 位置出发，每次都能 上下左右 四个方向走，当走出 N,M 空间则失败，求得 k 步后还在 N,M 内的概率
 *
 * @author linzherong
 * @date 2025/6/2 11:54
 */
public class BobDie {

    public static double livePosibility1(int row, int col, int k, int N, int M) {
        return (double) alive(row, col, k, N, M) / Math.pow(4, k);
    }

    // 还在棋盘中的情况
    public static int alive(int x, int y, int rest, int N, int M) {
        // base case
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        int a1 = alive(x+1, y, rest-1, N, M);
        int a2 = alive(x-1, y, rest-1, N, M);
        int a3 = alive(x, y+1, rest-1, N, M);
        int a4 = alive(x, y-1, rest-1, N, M);
        return a1 + a2 + a3 + a4;
    }

    public static double livePosibility2(int row, int col, int K, int N, int M) {
        int[][][] dp = new int[N][M][K+1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    dp[i][j][k] = pick(i+1, j, k-1, N, M, dp)
                            + pick(i-1, j, k-1, N, M, dp)
                            + pick(i, j+1, k-1, N, M, dp)
                            + pick(i, j-1, k-1, N, M, dp);
                }
            }
        }
        return (double) dp[row][col][K] / Math.pow(4, K);
    }

    public static int pick(int x, int y, int k, int N, int M, int[][][] dp) {
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return 0;
        }
        return dp[x][y][k];
    }









    public static double livePosibility(int row, int col, int k, int N, int M) {
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    // 目前在row，col位置，还有rest步要走，走完了如果还在棋盘中就获得1个生存点，返回总的生存点数
    public static long process(int row, int col, int rest, int N, int M) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        // 还在棋盘中！
        if (rest == 0) {
            return 1;
        }
        // 还在棋盘中！还有步数要走
        long up = process(row - 1, col, rest - 1, N, M);
        long down = process(row + 1, col, rest - 1, N, M);
        long left = process(row, col - 1, rest - 1, N, M);
        long right = process(row, col + 1, rest - 1, N, M);
        return up + down + left + right;
    }

    public static void main(String[] args) {
        System.out.println(livePosibility(6, 6, 10, 50, 50));
        System.out.println(livePosibility1(6, 6, 10, 50, 50));
        System.out.println(livePosibility2(6, 6, 10, 50, 50));
    }

}
