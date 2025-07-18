package coding.offer.day01;

/**
 *
 * https://leetcode.cn/problems/target-sum/
 *
 * @author linzherong
 * @date 2025/7/10 23:07
 */
public class TargetSum {

    public int findTargetSumWays1(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }
        return process(nums, 0, target);
    }
    public int process(int[] nums, int index, int rest) {
        if (index == nums.length) {
            return rest == 0? 1 : 0;
        }
        return process(nums, index+1, rest+nums[index]) + process(nums, index+1, rest-nums[index]);
    }


    public int findTargetSumWays2(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }
        // y的偏移量
        int offset = 0;
        for (int num : nums) {
            offset += num;
        }
        if (offset < target || -offset > target || ((offset & 1) ^ (target & 1)) != 0) {
            return 0;
        }
        int N = nums.length;
        int[][] dp = new int[N+1][offset*2+1];
        dp[N][offset] = 1;
        for (int i = N-1; i >= 0 ; i--) {
            for (int j = -offset ; j <= offset; j++) {
                if (j+nums[i]+offset <= 2*offset) {
                    dp[i][j + offset] += dp[i + 1][j + nums[i] + offset];
                }
                if (j-nums[i]+offset >= 0) {
                    dp[i][j+offset] += dp[i+1][j-nums[i]+offset];
                }
            }
        }
        return dp[0][offset+target];
    }

    // 技巧解法：（把目标当条件，改为获取另一个目标）
    // 假设把num区分成正数N和负数P区域, nums累加为sum
    // N - P = sum
    // N - P + N + P = sum + N + P
    // 2N = sum + N + P = sum + N + P，而目标是 P 和 N 组成 target，则可以等价于  2N=sum+target
    // N = (sum+target)/2，而sum和target都是已知，则N已知，只要获取sum中能组成N的组合即可,
    // 要注意的是，由于 sum 和 target 这两个数的组成数字是一样的，只是符号不同，所以两个数相加一定是偶数，也就是这两个数的奇偶性一定相同
    // 否则，如果不提前判断，可能会导致 N 算的不对(N必须是整除后得到)，不能整除则不对
    // 表示 N的都是正的，其他都是负的
    public int findTargetSumWays3(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < target || -sum > target || ((sum & 1)^(target&1)) != 0) {
            return 0;
        }
        int P = (sum + target) >> 1;
        int i = process1(nums, 0, P);
        return i;
    }
    public int process1(int[] nums, int index, int target) {
        if (index == nums.length) {
            return target == 0? 1 : 0;
        }
        return process1(nums, index+1, target) + process1(nums, index+1, target-nums[index]);
    }

    public int findTargetSumWays4(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < target || -sum > target || ((sum & 1)^(target & 1)) == 1) {
            return 0;
        }
        int N = nums.length;
        int P = (sum + target) >> 1;
        int[][] dp = new int[N+1][P+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0 ; i--) {
            for (int j = 0; j <= P ; j++) {
                dp[i][j] = dp[i+1][j];
                if (j >= nums[i]) {
                    dp[i][j] += dp[i+1][j-nums[i]];
                }
            }
        }
        return dp[0][P];
    }

    public int findTargetSumWays5(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < target || -sum > target || ((sum & 1)^(target & 1)) == 1) {
            return 0;
        }
        int N = nums.length;
        int P = (sum + target) >> 1;
        int[] dp = new int[P+1];
        dp[0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = P; j >= 0; j--) {
                // 空间压缩的形式，要考虑到取值的方向，避免同一行里，前边刚设置的值会被后边的计算用到
                if (j >= nums[i]) {
                    dp[j] += dp[j-nums[i]];
                }
            }
        }
        return dp[P];
    }


    public static void main(String[] args) {
        TargetSum sum = new TargetSum();
        System.out.println(sum.findTargetSumWays2(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(sum.findTargetSumWays1(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(sum.findTargetSumWays3(new int[]{7,9,3,8,0,2,4,8,3,9}, 3));
        System.out.println(sum.findTargetSumWays4(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(sum.findTargetSumWays4(new int[]{7,9,3,8,0,2,4,8,3,9}, 0));
        System.out.println(sum.findTargetSumWays5(new int[]{1, 1, 1, 1, 1}, 3));
    }

}
