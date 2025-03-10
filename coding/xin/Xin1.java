package coding.xin;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/2/26 22:01
 */
public class Xin1 {

    public static void main(String[] args) {
        Xin1 xin1 = new Xin1();
//        System.out.println(xin1.multiple(5));
        int[] arr = {1,7,5,8,3,-1,-5,12,3,67,36};
//        xin1.selectSort(arr);
        xin1.insertSort(arr);
        System.out.println(Arrays.toString(arr));
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
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

}
