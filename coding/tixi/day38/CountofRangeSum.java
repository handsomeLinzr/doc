package coding.tixi.day38;

/**
 *
 *  https://leetcode.com/problems/count-of-range-sum/
 *
 * @author linzherong
 * @date 2025/7/13 23:23
 */
public class CountofRangeSum {

    public int countRangeSum1(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 前缀和
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i-1] + nums[i];
        }
        return process(sum, 0, nums.length-1, lower, upper);
    }

    public int process(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return sum[left] <= upper && sum[left] >= lower? 1:0;
        }
        int middle = (left + right) >> 1;
        return process(sum, left, middle, lower, upper)
                + process(sum, middle+1, right, lower, upper)
                + mergeRangeSum(sum, left, middle, right, lower, upper);
    }
    public int mergeRangeSum(long[] sum, int left, int middle, int right, int lower, int upper) {
        int result = 0;
        for (int i = middle+1; i <= right; i++) {
            long max = sum[i] - lower;
            long min = sum[i] - upper;
            int windowL = left;
            while (windowL <= middle && sum[windowL] < min) {
                // 左边界
                windowL++;
            }
            int windowR = windowL;
            while (windowR <= middle && sum[windowR] <= max) {
                // 右边界
                windowR ++;
            }
            result += (windowR - windowL);
        }
        long[] help = new long[right - left + 1];
        int L = left;
        int R = middle+1;
        int index = 0;
        while (L <= middle && R <= right) {
            if (sum[L] <= sum[R]) {
                help[index++] = sum[L++];
            } else {
                help[index++] = sum[R++];
            }
        }
        while (L <= middle) {
            help[index++] = sum[L++];
        }
        while (R <= right) {
            help[index++] = sum[R++];
        }
        for (int i = 0; i < index; i++) {
            sum[left+i] = help[i];
        }
        return result;
    }


    public int countRangeSum(int[] nums, int lower, int upper) {
        return 0;
    }


}
