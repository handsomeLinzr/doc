package coding.tixi.day38;

import java.util.HashSet;

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


    public class SBTNode {
        public long key;
        public SBTNode left;
        public SBTNode right;
        public long size;
        public long all;
        public SBTNode(long key) {
            this.key = key;
            this.size = 1;
            this.all = 1;
        }
    }

    public class SizeBalancedTreeSet {
        public SBTNode root;
        public HashSet<Long> set = new HashSet<>();

        public void add(long value) {
            boolean contains = set.contains(value);
            if (root == null) {
                root = new SBTNode(value);
            } else {
                root = add(root, value, contains);
            }
            set.add(value);
        }

        // 获取小于key的数量
        public long getLessKeySize(long key) {
            long result = 0;
            SBTNode cur = root;
            while (cur != null) {
                if (cur.key == key) {
                    return result + (cur.left == null? 0 : cur.left.all);
                } else if (cur.key < key) {
                    result += (cur.all - (cur.right == null?0:cur.right.all));
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return result;
        }

        // 获取大于key的数量
        public long getMoreKeySize(long key) {
            return root == null?0:root.all - getLessKeySize(key+1);
        }

        private SBTNode add(SBTNode node, long key, boolean contain) {
            if (node == null) {
                return new SBTNode(key);
            } else {
                node.all++;
                if (node.key == key) {
                    return node;
                }
                if (!contain) {
                    node.size++;
                }
                if (key < node.key) {
                    node.left = add(node.left, key, contain);
                } else {
                    node.right = add(node.right, key, contain);
                }
                return maintain(node);
            }
        }

        private SBTNode leftRotate(SBTNode node) {
            // 自己节点产生的same
            long nodeSame = node.all - (node.left == null ? 0 : node.left.all) - (node.right == null ? 0 : node.right.all);
            SBTNode right = node.right;
            node.right = right.left;
            right.left = node;
            right.size = node.size;
            right.all = node.all;
            node.size = (node.left == null ? 0 : node.left.size) + (node.right == null ? 0 : node.right.size) + 1;
            node.all = (node.left == null ? 0 : node.left.all) + (node.right == null ? 0 : node.right.all) + nodeSame;
            return right;
        }

        private SBTNode rightRotate(SBTNode node) {
            long nodeSame = node.all - (node.left == null ? 0 : node.left.all) - (node.right == null ? 0 : node.right.all);
            SBTNode left = node.left;
            node.left = left.right;
            left.right = node;
            left.size = node.size;
            left.all = node.all;
            node.size = (node.left == null ? 0 : node.left.size) + (node.right == null ? 0 : node.right.size) + 1;
            node.all = (node.left == null ? 0 : node.left.all) + (node.right == null ? 0 : node.right.all) + nodeSame;
            return left;
        }

        private SBTNode maintain(SBTNode node) {
            long left = node.left == null ? 0 : node.left.size;
            long right = node.right == null ? 0 : node.right.size;
            long leftLeft = node.left == null || node.left.left == null ? 0 : node.left.left.size;
            long leftRight = node.left == null || node.left.right == null ? 0 : node.left.right.size;
            long rightLeft = node.right == null || node.right.left == null ? 0 : node.right.left.size;
            long rightRight = node.right == null || node.right.right == null ? 0 : node.right.right.size;
            if (leftLeft > right) {
                // LL
                node = rightRotate(node);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (leftRight > right) {
                // LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightLeft > left) {
                // RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightRight > left) {
                // RR
                node = leftRotate(node);
                node.left = maintain(node.left);
                node = maintain(node);
            }
            return node;
        }
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        int ans = 0;
        long sum = 0;
        treeSet.add(0);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            long a = treeSet.getLessKeySize(sum - lower + 1);
            long b = treeSet.getLessKeySize(sum - upper);
            ans += (a-b);
            treeSet.add(sum);
        }
        return ans;
    }

    public static void main(String[] args) {
        CountofRangeSum countofRangeSum = new CountofRangeSum();
        System.out.println(countofRangeSum.countRangeSum(new int[]{-2, 5, -1}, -2, 2));
    }


}
