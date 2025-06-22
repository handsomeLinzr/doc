package coding.tixi.day26;

/**
 *
 * https://leetcode.cn/problems/largest-rectangle-in-histogram/
 * 柱状图中最大的矩形
 * @author linzherong
 * @date 2025/6/15 21:29
 */
public class LargestRectangleInHistogram {

    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int N = heights.length;
        int[] stack = new int[N];
        int index = 0; // 即将放入的位置
        int maxArea = 0;
        for (int i = 0; i < N; i++) {
            while (index > 0 && heights[stack[index-1]] >= heights[i]) {
                int cur = stack[--index];
                int left = index == 0? -1 : stack[index-1];
                maxArea = Math.max(maxArea, heights[cur] * (i - 1 - left));
            }
            stack[index++] = i;
        }
        while (index > 0) {
            int cur = stack[--index];
            int left = index == 0? -1 : stack[index-1];
            maxArea = Math.max(maxArea, heights[cur] * (N-1-left));
        }
        return maxArea;
    }

}
