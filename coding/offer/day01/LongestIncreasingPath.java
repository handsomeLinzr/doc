package coding.offer.day01;

/**
 *
 * https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/description/
 *
 * @author linzherong
 * @date 2025/7/12 18:24
 */
public class LongestIncreasingPath {

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        // O(N^2)，获取每个位置开始的递增长度，其中最长的就是答案
        int max = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                max = Math.max(max, process(matrix, i, j, N, M, dp));
            }
        }
        return max;
    }

    //4个反向走
    public int process(int[][] matrix, int i, int j, int N, int M, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int value = matrix[i][j];
        int up = i > 0 && matrix[i-1][j] > value? process(matrix, i-1, j, N, M, dp) + 1 : 1;
        int down = i < N-1 && matrix[i+1][j] > value? process(matrix, i+1, j, N, M, dp) + 1 : 1;
        int left = j > 0 && matrix[i][j-1] > value? process(matrix, i, j-1, N, M, dp) + 1 : 1;
        int right = j < M-1 && matrix[i][j+1] > value? process(matrix, i, j+1, N, M, dp) + 1 : 1;
        dp[i][j] = Math.max(up, Math.max(down, Math.max(left, right)));
        return dp[i][j];
    }

}
