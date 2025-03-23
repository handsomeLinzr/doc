package coding.xin;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * 归并排序 ===>> 左有序+右有序+左右合并
 *  mergeSort1 递归    mergeSort2 非递归
 * 快排 ===>> 分成 小于  等于  大于
 *  quickSort1 递归    quickSort2 非递归（任务）
 * @author linzherong
 * @date 2025/3/22 13:40
 */
public class Day8 {

    public static void main(String[] args) {
        Day8 day8 = new Day8();
        for (int i = 0; i < 100_0000; i++) {
            int[] array = ArrayUtils.generalArray(25, 100);
            int[] a1 = ArrayUtils.copy(array);
            int[] a2 = ArrayUtils.copy(array);
            int[] a3 = ArrayUtils.copy(array);
            int[] a4 = ArrayUtils.copy(array);
            Arrays.sort(array);
            day8.mergeSort1(a1);
            day8.mergeSort2(a2);
            day8.quickSort1(a3);
            day8.quickSort2(a4);
            if (array.length != a1.length || array.length != a2.length || array.length != a3.length || array.length != a4.length ) {
                System.out.println("出错");
                return;
            }
            for (int j = 0; j < array.length; j++) {
                int num = array[j];
                if (a1[j] != num || a2[j] != num || a3[j] != num || a4[j] != num ) {
                    System.out.println("出错");
                    return;
                }
            }
        }
        System.out.println("成功");
    }

    /**
     * 归并排序递归方式
     */
    public void mergeSort1(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }
        processMergeSort(arr, 0, arr.length-1);
    }
    public void processMergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        // 中间
        int middle = l + ((r-l) >> 1);
        processMergeSort(arr, l, middle);
        processMergeSort(arr, middle+1, r);
        merge(arr, l, middle, r);
    }

    public void merge(int[] arr, int l, int middle, int r) {
        // 左边起始位置
        int lb = l;
        // 右边起始位置
        int rb = middle + 1;
        int[] help = new int[r - l + 1];
        int i = 0;
        while (lb <= middle && rb <= r) {
            if (arr[lb] <= arr[rb]) {
                help[i++] = arr[lb++];
            } else {
                help[i++] = arr[rb++];
            }
        }
        while (lb <= middle) {
            help[i++] = arr[lb++];
        }
        while (rb <= r) {
            help[i++] = arr[rb++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[l+j] = help[j];
        }
    }

    /**
     * 归并排序步长方式
     */
    public void mergeSort2(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }
        int max = arr.length - 1;
        int length = arr.length;
        int step = 1;
        while (step < length) {
            int begin = 0;
            int right = 0;
            int middle = 0;
            while (begin < max) {
                if (max - begin < step) {
                    break;
                } else {
                    middle = begin + step - 1;
                }
                if (max - middle < step) {
                    right = max;
                } else {
                    right = middle + step;
                }
                merge(arr, begin, middle, right);
                if (right == max) {
                    break;
                }
                begin = right + 1;
            }
            if (step > length / 2) {
                break;
            }
            step *= 2;
        }
    }

    /**
     * 把数据分组，以最后一个数作为分层标准
     */
    public void part(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }
        int less = -1;
        int cur = 0;
        int more = arr.length - 1;
        int num = arr[arr.length-1];
        while (cur < more) {
            if (arr[cur] < num) {
                less++;
                swap(arr, cur, less);
                cur++;
            } else if (arr[cur] == num) {
                cur ++;
            } else {
                more --;
                swap(arr, cur, more);
            }
        }
        swap(arr, more, arr.length-1);
    }

    public void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    /**
     * 快排递归方式
     */
    public void quickSort1(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }
        processQuickSort(arr, 0, arr.length-1);
    }

    public void processQuickSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int[] partition = partition(arr, l, r);
        processMergeSort(arr, l, partition[0]);
        processMergeSort(arr, partition[1], r);
    }

    /**
     * 返回相等的左右边界
     */
    public int[] partition(int[] arr, int l, int r) {
        int num = arr[r];
        // 先指向前一个位置
        int less = l - 1;
        int cur = l;
        // 指向最后一个位置
        int more = r;
        while (cur < more) {
            if (arr[cur] < num) {
                less++;
                swap(arr, cur, less);
                cur ++;
            } else if (arr[cur] == num) {
                cur++;
            } else {
                more --;
                swap(arr, cur, more);
            }
        }
        swap(arr, more, r);
        return new int[]{less+1, more};
    }


    /**
     * 快排任务方式
     */
    public void quickSort2(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }
        Stack<Task> stack = new Stack<>();
        stack.push(new Task(0, arr.length-1));
        while (!stack.isEmpty()) {
            Task pop = stack.pop();
            // 分层
            int[] partition = partition(arr, pop.left, pop.right);
            if (pop.left < partition[0]) {
                stack.push(new Task(pop.left, partition[0]-1));
            }
            if (partition[1] < pop.right) {
                stack.push(new Task(partition[1]+1, pop.right));
            }
        }
    }

    static class Task {
        int left;
        int right;

        public Task(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

}
