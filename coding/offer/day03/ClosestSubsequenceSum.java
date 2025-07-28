package coding.offer.day03;

import java.util.Arrays;

/**
 *
 * // 本题测试链接 : https://leetcode.com/problems/closest-subsequence-sum/
 * // 本题数据量描述:
 * // 1 <= nums.length <= 40
 * // -10^7 <= nums[i] <= 10^7
 * // -10^9 <= goal <= 10^9
 * // 通过这个数据量描述可知，需要用到分治，因为数组长度不大
 * // 而值很大，用动态规划的话，表会爆
 *
 * @author linzherong
 * @date 2025/7/18 00:48
 */
public class  ClosestSubsequenceSum {

    public static int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return goal;
        }
        int[] l = new int[1<<20];
        int[] r = new int[1<<20];
        int middle = nums.length >> 1;
        // 左右分治, left 和 right 分别为数量
        int left = process(nums, 0, middle, l, 0, 0);
        int right = process(nums, middle, nums.length, r, 0, 0);
        // 从小到大排序
        Arrays.sort(l, 0, left);
        Arrays.sort(r, 0, right);
        // 结果，为0时候的结果
        int ans = Math.abs(goal);
        right--;
        for (int i = 0; i < left; i++) {
            // l[i] 从小到大， rest 从大到小（剩余数）
            int rest = goal - l[i];
            while (right > 0 && Math.abs(rest - r[right]) >= Math.abs(rest - r[right-1])) {
                right -- ;
            }
            ans = Math.min(ans, Math.abs(rest - r[right]));
        }
        return ans;
    }

    public static int process(int[] nums, int start, int end, int[] sums, int sum, int fill) {
        if (start == end) {
            sums[fill++] = sum;
        } else {
            fill = process(nums, start+1, end, sums,sum+nums[start], fill);
            fill = process(nums, start+1, end, sums, sum, fill);
        }
        return fill;
    }

    public static void main(String[] args) {
        System.out.println(minAbsDifference(new int[]{7,-9,15,-2}, -5));
    }

}
