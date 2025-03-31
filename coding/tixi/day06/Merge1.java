package coding.tixi.day06;

import coding.tixi.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 *
 * 1. leetcode 327（前缀和，转为看前边有多少在范围内，最后窗口圈定范围）
 * 2. 快排
 *
 * @author linzherong
 * @date 2025/3/25 12:50
 */
public class Merge1 {

    public static void main(String[] args) {
        Merge1 merge1 = new Merge1();
//        merge1.testQuickSort();
        merge1.testCountRange();
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (Objects.isNull(nums) || nums.length <=0) {
            return 0;
        }
        // 前缀和
        long[] sum = new long[nums.length];
        long curSum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum[i] = curSum +nums[i];
            curSum = sum[i];
        }
        return processRangeSum(sum, 0, sum.length-1, lower, upper);
    }
    public int processRangeSum(long[] sums, int start, int end, int lower, int upper) {
        if (start == end) {
            return sums[start] <= upper && sums[start] >= lower? 1 : 0;
        }
        int middle = start + ((end - start) >> 1);
        // 递归+归并
        return processRangeSum(sums, start, middle, lower, upper)
                + processRangeSum(sums, middle+1, end, lower, upper)
                + mergeRangeSum(sums, start, middle, end, lower, upper);
    }
    public int mergeRangeSum(long[] sums, int start, int middle, int end, int lower, int upper) {
        int windowL = start;
        int result = 0;
        for (int i = middle+1; i <= end; i++) {
            long max = sums[i] - lower;
            long min = sums[i] - upper;
            while (windowL <= middle && sums[windowL] < min) {
                windowL++;
            }
            int windowR = windowL;
            while (windowR <= middle && sums[windowR] <= max ) {
                windowR ++;
            }
            result = result + (windowR - windowL);
        }
        // 归并排序
        long[] help = new long[end-start+1];
        int L = start;
        int R = middle+1;
        int i = 0;
        while (L <= middle && R <= end) {
            if (sums[L] <= sums[R]) {
                help[i++] = sums[L++];
            } else {
                help[i++] = sums[R++];
            }
        }
        while (L <= middle) {
            help[i++] = sums[L++];
        }
        while (R <= end) {
            help[i++] = sums[R++];
        }
        for (int j = 0; j < i; j++) {
            sums[start+j] = help[j];
        }
        return result;
    }
    public void testCountRange() {
        int[] arr = {-2147483647,0,-2147483647,2147483647};
        int i = countRangeSum(arr, -564, 3864);
        System.out.println(i);
    }

    public int countRangeSumBL(int[] nums, int lower, int upper) {
        if (Objects.isNull(nums) || nums.length <= 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                long sum = 0;
                for (int k = i; k <= j; k++) {
                    sum+=nums[k];
                }
                if (sum >= lower && sum <= upper) {
                    result++;
                }
            }
        }
        return result;
    }


    public void quickSort1(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        processQuickSort(arr, 0 ,arr.length-1);
    }
    public void processQuickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int[] partition = partition(arr, start, end);
        processQuickSort(arr, start, partition[0]-1);
        processQuickSort(arr, partition[1]+1, end);
    }
    public int[] partition(int[] arr, int start, int end) {
        swap(arr, (int) (Math.random() * (end-start)) + start, end);
        int target = arr[end];
        int less = start-1;
        int large = end;
        int cur = start;
        while (cur < large) {
            if (arr[cur] < target) {
                swap(arr, cur++, ++less);
            } else if (arr[cur] > target) {
                swap(arr, cur, --large);
            } else {
                cur++;
            }
        }
        swap(arr, large, end);
        return new int[]{less+1, large};
    }
    public void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public void quickSort2(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        Stack<Job> jobs = new Stack<>();
        jobs.push(new Job(0, arr.length-1));
        while (!jobs.isEmpty()) {
            Job job = jobs.pop();
            int[] partition = partition(arr, job.left, job.right);
            if (partition[0]-1 > job.left) {
                jobs.push(new Job(job.left, partition[0] - 1));
            }
            if (partition[1]+1 < job.right) {
                jobs.push(new Job(partition[1] + 1, job.right));
            }
        }
    }

    public void testQuickSort() {
        for (int i = 0; i < 100_0000; i++) {
            int[] arr = ArrayUtils.generalArr(50);
            int[] copy = new int[arr.length];
            System.arraycopy(arr, 0, copy, 0, arr.length);
            Arrays.sort(arr);
            quickSort2(copy);
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] != copy[j]) {
                    System.out.println("错了");
                }
            }
        }
        System.out.println("成功");
    }

}
