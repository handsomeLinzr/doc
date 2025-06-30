package coding.codeevery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 和谐数组是指一个数组里元素的最大值和最小值之间的差别 正好是 1 。
 * 给你一个整数数组 nums ，请你在所有可能的 子序列 中找到最长的和谐子序列的长度。
 * 数组的 子序列 是一个由数组派生出来的序列，它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。
 *
 *
 * @author linzherong
 * @date 2025/6/30 14:49
 */
public class Leetcode594 {
    // 复杂度过高
    public int findLHS(int[] nums) {
        return length(nums, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }
    public int length(int[] nums, int index, int max, int min, int maxLength) {
        if (index == nums.length) {
            return max == min+1? maxLength:-1;
        }
        int p1 = length(nums, index+1, max, min, maxLength);
        int p2 = length(nums, index+1, Math.max(max, nums[index]), Math.min(min, nums[index]), maxLength+1);
        return Math.max(p1, p2);
    }

    // O(N^2)，数据量 2*10^4， 合起来超过 4*10^8，超过
    public int findLHS2(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            Info info = getCount(nums, nums[i], nums[i] + 1);
            if (info.has) {
                max = Math.max(max, info.count);
            }
        }
        return max;
    }

    public class Info {
        int count;
        boolean has;
        public Info(int count, boolean has) {
            this.count = count;
            this.has = has;
        }
    }

    // O(2*N*logN)
    public int findLHS3(int[] nums) {
        Arrays.sort(nums);
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 1;
            int j = i+1;
            while (j < nums.length && nums[j] <= nums[i]+1) {
                count++;
                j++;
            }
            if (nums[j-1] == nums[i]+1) {
                max = Math.max(max, count);
            }
        }
        return max;
    }

    public int findLHS4(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0)+1);
        }
        int max = 0;
        for (Integer num : map.keySet()) {
            if (map.containsKey(num+1)) {
                max = Math.max(map.get(num) + map.get(num+1), max);
            }
        }
        return max;
    }




    public Info getCount(int[] arr, int less, int large) {
        int count = 0;
        boolean has = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= less && arr[i] <= large) {
                if (arr[i] == large) {
                    has = true;
                }
                count++;
            }
        }
        return new Info(count, has);
    }




    public static void main(String[] args) {
        Leetcode594 leetcode594 = new Leetcode594();
        System.out.println(leetcode594.findLHS(new int[]{1,3,2,2,5,2,3,7}));
        System.out.println(leetcode594.findLHS2(new int[]{1,3,2,2,5,2,3,7}));
        System.out.println(leetcode594.findLHS3(new int[]{1,3,2,2,5,2,3,7}));
        System.out.println(leetcode594.findLHS4(new int[]{1,3,2,2,5,2,3,7}));
    }

}
