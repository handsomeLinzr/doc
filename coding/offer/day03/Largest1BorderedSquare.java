package coding.offer.day03;

import coding.tixi.day25.GasStation;

/**
 *
 * https://leetcode.com/problems/largest-1-bordered-square/
 *
 * @author linzherong
 * @date 2025/7/17 00:46
 */
public class Largest1BorderedSquare {

    /**
     *
     * 给你一个由若干 0 和 1 组成的二维网格 grid，请你找出边界全部由 1 组成的最大 正方形 子网格，并返回该子网格中的元素数量。如果不存在，则返回 0。
     * 示例 1：
     * 输入：grid = [[1,1,1],[1,0,1],[1,1,1]]
     * 输出：9
     * 示例 2：
     * 输入：grid = [[1,1,0,0]]
     * 输出：1
     *
     * 提示：
     * 1 <= grid.length <= 100
     * 1 <= grid[0].length <= 100
     * grid[i][j] 为 0 或 1
     *
     * @param grid
     * @return
     */
    public int largest1BorderedSquare(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        // 长和宽
        int N = grid.length;
        int M = grid[0].length;
        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int l = 1; l <= Math.min(N-i, M-j); l++) {
                    max = Math.max(max, getSize(grid, i, j, l));
                }
            }
        }
        return max;
    }

    public int getSize(int[][] grid, int x, int y, int l) {
        for (int i = 0; i < l; i++) {
            if (grid[x+i][y] != 1 || grid[x][y+i] != 1) {
                return 0;
            }
        }
        x += (l-1);
        y += (l-1);
        for (int i = 0; i < l; i++) {
            if (grid[x-i][y] != 1 || grid[x][y-i] != 1) {
                return 0;
            }
        }
        return l * l;
    }

    public int largest1BorderedSquare2(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int N = grid.length;
        int M = grid[0].length;
        // 右边最长
        int[][] right = new int[N][M];
        // 下边最长
        int[][] down = new int[N][M];
        makeRight(grid, right);
        makeDown(grid, down);
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int l = 1; l <= Math.min(N-i, M-j); l++) {
                    ans = Math.max(ans, getSize2(right, down, l, i, j));
                }
            }
        }
        return ans;
    }

    public int getSize2(int[][] right, int[][] down, int l, int x, int y) {
        if (x + l > down.length || y + l > right[0].length) {
            return 0;
        }
        int rightL = right[x][y];
        int downRightL = right[x+l-1][y];
        int downL = down[x][y];
        int rightDownL = down[x][y+l-1];
        if (rightL >= l && downRightL >= l && downL >= l && rightDownL >= l) {
            return l * l;
        } else {
            return 0;
        }
    }

    public void makeRight(int[][] grid, int[][] right) {
        for (int i = 0; i < right.length; i++) {
            int pre = 0;
            for (int j = right[0].length-1; j >= 0 ; j--) {
                if (grid[i][j] == 1) {
                    right[i][j] = 1 + pre;
                    pre = right[i][j];
                } else {
                    right[i][j] = 0;
                    pre = 0;
                }
            }
        }
    }

    public void makeDown(int[][] grid, int[][] down) {
        for (int j = 0; j < down[0].length; j++) {
            int pre = 0;
            for (int i = down.length-1; i >= 0 ; i--) {
                if (grid[i][j] == 1) {
                    down[i][j] = 1 + pre;
                    pre = down[i][j];
                } else {
                    down[i][j] = 0;
                    pre = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Largest1BorderedSquare square = new Largest1BorderedSquare();
        int i = square.largest1BorderedSquare(new int[][]{{1,1,1},{1,0,1},{1,1,1}});
        int i1 = square.largest1BorderedSquare2(new int[][]{{1,1,1},{1,0,1},{1,1,1}});
        System.out.println(i);
    }

}
