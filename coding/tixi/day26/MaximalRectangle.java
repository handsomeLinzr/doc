package coding.tixi.day26;

/**
 *
 * https://leetcode.cn/problems/maximal-rectangle/
 * 最大矩形
 *
 * @author linzherong
 * @date 2025/6/15 21:31
 */
public class MaximalRectangle {

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int N = matrix[0].length;
        int[] height = new int[N];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < N; j++) {
                height[j] = matrix[i][j] == '0'? 0 : height[j]+1;
            }
            max = Math.max(max, getR(height));
        }
        return max;
    }

    public int getR(int[] height) {
        int N = height.length;
        int max = 0;
        int[] stack = new int[N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            while (index > 0 && height[stack[index-1]] >= height[i]) {
                int cur = stack[--index];
                int left = index == 0? -1 : stack[index-1];
                max = Math.max(max, height[cur]*(i-1-left));
            }
            stack[index++] = i;
        }
        while (index > 0) {
            int cur = stack[--index];
            int left = index == 0? -1 : stack[index-1];
            max = Math.max(max, height[cur]*(N-1-left));
        }
        return max;
    }

}
