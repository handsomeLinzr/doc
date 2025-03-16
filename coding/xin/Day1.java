package coding.xin;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/2/26 22:01
 */
public class Day1 {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        for (int i = 0; i < 100_0000; i++) {
            int[] array = ArrayUtils.generalArray(20, 100);
            int[] bubbleSort1 = day1.bubbleSort1(array);
            int[] selectSort1 = day1.selectSort1(array);
            int[] insertSort1 = day1.insertSort1(array);
            Arrays.sort(array);
            if (!day1.equalSort(bubbleSort1, array)) {
                System.out.println("冒泡出错");
                return;
            }
            if (!day1.equalSort(selectSort1, array)) {
                System.out.println("选择出错");
                return;
            }
            if (!day1.equalSort(insertSort1, array)) {
                System.out.println("插入出错");
                return;
            }
        }
        System.out.println("正确");
    }

    /**
     * 阶乘累加
     * @param n
     */
    public int multiple(int n) {
        if (n < 0) {
            return 0;
        }
        int mul = 1;
        int ans = 1;
        for (int i = 2; i <= n; i++) {
            mul *= i;
            ans += mul;
        }
        return ans;
    }

    /**
     * 选择排序, 从小到大
     * @param arr
     */
    public void selectSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        // index =>> 目标位置
        for(int index = 0; index < arr.length - 1; index++) {
            int min = index;
            // comp ==>> 每一轮当前正在比较的值
            for(int comp = index+1; comp < arr.length; comp++) {
                if (arr[min] > arr[comp]) {
                    min = comp;
                }
            }
            if (index != min) {
                swap(arr, index, min);
            }
        }
    }

    /**
     * 冒泡排序, 从大到小
     * @param arr
     */
    public void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        // end =>> 截止的位置
        for(int end = arr.length-1; end >= 1; end--) {
            // index =>> 每一轮里要比较两个值的后边那个
            for(int index = 1; index <= end; index++) {
                if (arr[index] > arr[index-1]) {
                    swap(arr, index, index-1);
                }
            }
        }
    }

    /**
     * 插入排序，从大到小
     * @param arr
     */
    public void insertSort(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        // end => 开始的位置
        for(int end = 1; end < arr.length;end++) {
            // 当前的目标值
            int cur = arr[end];
            // index 为每轮在比较的位置
            int index = end-1;
            for(; index >= 0 && arr[index]<cur; index--) {
                arr[index+1] = arr[index];
            }
            arr[index+1] = cur;
        }
    }


    private void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    /**
     * 排序是否相同
     */
    private boolean equalSort(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    //-----------------------------------------------------------

    public int[] bubbleSort1(int[] arr) {
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
        }
        if (array.length <= 1) {
            return array;
        }
        // 排序
        for (int right = arr.length - 1; right > 0; right--) {
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i+1]) {
                    swap(array, i, i+1);
                }
            }
        }
        return array;
    }

    public int[] selectSort1(int[] arr) {
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
        }
        if (array.length <= 1) {
            return array;
        }
        for (int index = 0; index < array.length-1; index++) {
            int min = index;
            for (int i = index + 1; i < array.length; i++) {
                if (array[min] > array[i]) {
                    min = i;
                }
            }
            swap(array, index, min);
        }
        return array;
    }

    public int[] insertSort1(int[] arr) {
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
        }
        if (array.length <= 1) {
            return array;
        }
        for (int index = 1; index < array.length; index++) {
            int num = arr[index];
            int i = index;
            for (; i > 0 && num < array[i-1]; i--) {
                array[i] = array[i-1];
            }
            array[i] = num;
        }
        return array;
    }


}
