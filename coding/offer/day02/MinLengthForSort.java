package coding.offer.day02;

/**
 *
 * https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/description/
 * 从左到右，依次根据前缀最大，找到最后不需要调整的位置
 * 从右到左，依次根据后缀最小，找到最前不需要调整的位置
 * 调整中间的位置即可
 *
 * @author linzherong
 * @date 2025/7/13 18:57
 */
public class MinLengthForSort {

    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        // 前缀最大
        int max = Integer.MIN_VALUE;
        int right = -1;
        for (int i = 0; i < nums.length; i++) {
            if (max > nums[i]) {
                // 如果当前的值小于前边的最大值，则当前这个位置之前都要调整
                right = i;
            } else {
                max = nums[i];
            }
        }
        int min = Integer.MAX_VALUE;
        int left = nums.length-1;
        for (int i = nums.length-1; i >= 0; i--) {
            if (min < nums[i]) {
                // 当前位置的值大于后边的最大值，则说明当前位置的后边都需要调整
                left = i;
            } else {
                min = nums[i];
            }
        }
        return Math.max(0, right - left + 1);
    }

    public static void main(String[] args) {
        int[] arr = {1,3,2,2,2};
        MinLengthForSort test = new MinLengthForSort();
        System.out.println(test.findUnsortedSubarray(arr));
    }

}
