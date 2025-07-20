package coding.tixi.day33;

/**
 *
 * 二维index tree
 *
 * @author linzherong
 * @date 2025/6/28 18:01
 */
public class IndexTree2D {

    private int[][] help;
    private int[][] nums;  // 原值
    private int M;
    private int N;

    public IndexTree2D(int[][] matrix) {
        N = matrix.length;
        M = matrix[0].length;
        help = new int[N+1][M+1];
        nums = matrix;
    }

    public int sum(int row, int col) {
        int sum = 0;
        for (int i = row+1; i > 0; i -= (i & (-i))) {
            for (int j = col+1; j > 0; j -= (j & (-j))) {
                sum += help[i][j];
            }
        }
        return sum;
    }

    public void update(int row, int col, int val) {
        int add = val - nums[row][col];
        nums[row][col] = val;
        for (int i = col; i <= N; i += (i & (-i))) {
            for (int j = 0; j <= M; j+= (j & (-j))) {
                help[i][j] += val;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        return sum(row2, col2) - sum(row1, col2) - sum(row2, col1) + sum(row1, col1);
    }

}
