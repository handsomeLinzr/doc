package coding.tixi.day05;

import coding.tixi.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 1. 归并排序两种解法
 * 2. 小和求解（一个数组，每个数字的左边比它小的都加起来，叫做小和，然后把所有小和相加得到结果）
 * 3. 逆序对求解（一个数组，左边的数大于右边的任何一个数都能组成一个逆序对，求一个数组中有多少个逆序对）
 * 4. 一个数据组，求得有多少个这样的数（num的右边的有多少个数*2后，依然小于num）
 *
 * @author linzherong
 * @date 2025/3/24 23:49
 */
public class Merge {

    public static void main(String[] args) {
        Merge merge = new Merge();
//        merge.testMergeSort();
//        merge.testSmallSum();
//        merge.testReverseNum();
        merge.smallMul2();
    }

    public void mergeSor11(int[] arr) {
         if (Objects.isNull(arr) || arr.length <= 1) {
             return;
         }
         mergeProcess(arr, 0, arr.length-1);
    }
    public void mergeProcess(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = left + ((right-left)>>1);
        mergeProcess(arr, left, middle);
        mergeProcess(arr, middle+1, right);
        merge(arr, left, middle, right);
    }

    public void mergeSort2(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int maxIndex = length-1;
        int step = 1;
        while (step < length) {
            int left = 0;
            int middle;
            int right;
            while (left < maxIndex - step + 1) {
                middle = left + step - 1;
                right = maxIndex - middle < step? maxIndex : middle + step;
                merge(arr, left, middle, right);
                left = right + 1;
            }
            if (step > length >> 1) {
                return;
            }
            step *= 2;
        }
    }

    public void merge(int[] arr, int left, int middle, int right) {
        int[] temp = new int[right-left+1];
        int leftBegin = left;
        int rightBegin = middle+1;
        int offset = 0;
        while (leftBegin <= middle && rightBegin <= right) {
            if (arr[leftBegin] <= arr[rightBegin]) {
                temp[offset++] = arr[leftBegin++];
            } else {
                temp[offset++] = arr[rightBegin++];
            }
        }
        while (leftBegin <= middle) {
            temp[offset++] = arr[leftBegin++];
        }
        while (rightBegin <= right) {
            temp[offset++] = arr[rightBegin++];
        }
        for (int i = 0; i < offset; i++) {
            arr[left+i] = temp[i];
        }
    }

    public void testMergeSort() {
        for (int time = 0; time < 100_0000; time++) {
            int[] num = ArrayUtils.generalArr(50);
            int[] copy = new int[num.length];
            System.arraycopy(num, 0, copy, 0, num.length);
            Arrays.sort(num);
            mergeSort2(copy);
            for (int i = 0; i < num.length; i++) {
                if (num[i] != copy[i]) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public int smallSum(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return 0;
        }
        return processSmallSum(arr, 0, arr.length-1);
    }

    public int processSmallSum(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int middle = left + ((right-left)>>1);
        return processSmallSum(arr, left, middle)
                + processSmallSum(arr, middle+1, right)
                + smallSumMerge(arr, left, middle, right);
    }

    public int smallSumMerge(int[] arr, int left, int middle, int right) {
        int sum = 0;
        int[] temp = new int[right - left + 1];
        int offset = 0;
        int leftBegin = left;
        int rightBegin = middle+1;
        while (leftBegin <= middle && rightBegin <= right) {
            if (arr[leftBegin] < arr[rightBegin]) {
                sum += arr[leftBegin] * (right - rightBegin + 1);
                temp[offset++] = arr[leftBegin++];
            } else {
                temp[offset++] = arr[rightBegin++];
            }
        }
        while (leftBegin <= middle) {
            temp[offset++] = arr[leftBegin++];
        }
        while (rightBegin <= right) {
            temp[offset++] = arr[rightBegin++];
        }
        for (int i = 0; i < offset; i++) {
            arr[left+i] = temp[i];
        }
        return sum;
    }
    public void testSmallSum() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50);
            int sum1 = 0;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[i] > arr[j]) {
                        sum1 += arr[j];
                    }
                }
            }
            int sum2 = smallSum(arr);
            if (sum1 != sum2) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }

    public int reverseNum(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return 0;
        }
        return processReverseNum(arr, 0, arr.length - 1);
    }
    public int processReverseNum(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return processReverseNum(arr, left, middle)
                + processReverseNum(arr, middle+1, right)
                + mergeReverseNum(arr, left, middle, right);
    }
    public int mergeReverseNum(int[] arr, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int result = 0;
        int leftT = left;
        int rightT = middle+1;
        int offset = 0;
        while (leftT <= middle && rightT <= right) {
            if (arr[leftT] > arr[rightT]) {
                result += (right - rightT + 1);
                temp[offset++] = arr[leftT++];
            } else {
                temp[offset++] = arr[rightT++];
            }
        }
        while (leftT <= middle) {
            temp[offset++] = arr[leftT++];
        }
        while (rightT <= right) {
            temp[offset++] = arr[rightT++];
        }
        for (int i = 0; i < offset; i++) {
            arr[left+i] = temp[i];
        }
        return result;
    }
    public void testReverseNum() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(5);
            int num1 = 0;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[i] < arr[j]) {
                        num1++;
                    }
                }
            }
            int num2 = reverseNum(arr);
            if (num1 != num2) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }

    public int smallMul2(int[] arr) {
        if (Objects.isNull(arr) || arr.length <= 1) {
            return 0;
        }
        return processSmallMul2(arr, 0, arr.length-1);
    }
    public int processSmallMul2(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return processSmallMul2(arr, left, middle)
                + processSmallMul2(arr, middle+1, right)
                + mergeSmallMul2(arr, left, middle, right);
    }
    public int mergeSmallMul2(int[] arr, int left, int middle, int right) {
        int result = 0;
        int[] temp = new int[right - left + 1];
        int leftT = left;
        int rightT = middle + 1;
        int offset = 0;
        while (leftT <= middle && rightT <= right) {
            if (arr[leftT] > arr[rightT] * 2) {
                result += (right - rightT + 1);
                leftT++;
            } else {
                rightT++;
            }
        }
        leftT = left;
        rightT = middle + 1;
        while (leftT <= middle && rightT <= right) {
            if (arr[leftT] > arr[rightT]) {
                temp[offset++] = arr[leftT++];
            } else {
                temp[offset++] = arr[rightT++];
            }
        }
        while (leftT <= middle) {
            temp[offset++] = arr[leftT++];
        }
        while (rightT <= right) {
            temp[offset++] = arr[rightT++];
        }
        for (int i = 0; i < offset; i++) {
            arr[left+i] = temp[i];
        }
        return result;
    }

    public void smallMul2() {
        for (int time = 0; time < 100_0000; time++) {
            int[] arr = ArrayUtils.generalArr(50);
            int count1 = 0;
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[j] > arr[i] * 2) {
                        count1++;
                    }
                }
            }
            int count2 = smallMul2(arr);
            if (count1 != count2) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }

}
