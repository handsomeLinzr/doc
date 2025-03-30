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
        merge1.testQuickSort();
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        return 0;
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
