package coding.tixi.day26;

import java.util.Stack;

/**
 *  https://leetcode.cn/problems/maximum-subarray-min-product/description
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，一定都可以算出 (sub累加和)*(sub中的最小值) 是什么
 * 那么所有子数组中，这个值最大是多少？
 *
 * @author linzherong
 * @date 2025/6/15 19:21
 */
public class AllTimesMinToMax {

    // 算每个值作为sub最小值的情况下的最大值
    public int maxSumMinProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] pre = preSum(nums);
        long max = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                Integer cur = stack.pop();
                long left = stack.isEmpty()?0:pre[stack.peek()];
                max = Math.max(max, nums[cur] * (pre[i-1]-left));
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Integer cur = stack.pop();
            long left = stack.isEmpty()?0:pre[stack.peek()];
            max = Math.max(max, nums[cur]*(pre[nums.length-1]-left));
        }
        return (int) (max % 1000000007);
    }

    public int maxSumMinProduct2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] pre = preSum(nums);
        long max = 0;
        int[] stack = new int[nums.length];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            while (index>0 && nums[stack[index-1]] >= nums[i]) {
                int cur = stack[--index];
                long left = index==0?0:pre[stack[index-1]];
                max = Math.max(max, nums[cur] * (pre[i-1]-left));
            }
            stack[index++] = i;
        }
        while (index>0) {
            int cur = stack[--index];
            long left = index==0?0:pre[stack[index-1]];
            max = Math.max(max, nums[cur]*(pre[nums.length-1]-left));
        }
        return (int) (max % 1000000007);
    }

    // 前缀和
    public long[] preSum(int[] nums) {
        long[] pre = new long[nums.length];
        pre[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            pre[i] = pre[i-1] + nums[i];
        }
        return pre;
    }

}
