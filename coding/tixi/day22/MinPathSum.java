package coding.tixi.day22;

/**
 *
 * 从左上角走到右下角，走过的路径和最小
 *
 * @author linzherong
 * @date 2025/6/1 16:37
 */
public class MinPathSum {

    public static int minPathSum(int[][] m) {
        if (m == null || m.length == 0) {
            return 0;
        }
        int M = m.length;
        int N = m[0].length;
        return process1(m);
    }

    public static int process1(int[][] m) {
        int M = m.length;
        int N = m[0].length;
        int[][] dp = new int[M][N];
        dp[0][0] = m[0][0];
        for (int i = 1; i < N; i++) {
            dp[0][i] = dp[0][i-1] + m[0][i];
        }
        for (int i = 1; i < M; i++) {
            dp[i][0] = dp[i-1][0] + m[i][0];
        }
        for (int i = 1; i < M; i++) {
            for (int j = 1; j < N; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + m[i][j];
            }
        }
        return dp[M-1][N-1];
    }

    // 空间压缩
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0) {
            return 0;
        }
        int N = m[0].length;
        int[] dp = new int[N];
        dp[0] = m[0][0];
        for (int i = 1; i < N; i++) {
            dp[i] = dp[i-1] + m[0][i];
        }
        for (int i = 1; i < m.length; i++) {
            // 下一行
            dp[0] += m[i][0];
            for (int j = 1; j < N; j++) {
                // 下一行调整
                dp[j] = Math.min(dp[j-1], dp[j]) + m[i][j];
            }
        }
        return dp[N-1];
    }

    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum(m));
        System.out.println(minPathSum2(m));
    }

}
