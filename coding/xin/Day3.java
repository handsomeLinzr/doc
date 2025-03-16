package coding.xin;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author linzherong
 * 二分法查找
 * 最左不小于num的数值位置
 * 最右不大于num的数值位置
 * arr无序，相邻不等，获取局部最小
 * @date 2025/3/12 12:35
 */
public class Day3 {

    public static void main(String[] args) {
        Day3 day3 = new Day3();
        for (int i = 0; i < 100_0000; i++) {
//            int[] array = ArrayUtils.generalArray(20, 10);
//            Arrays.sort(array);
//            int num = (int) (Math.random() * 10) + 1;
//            int index = day3.halfSearch(array, num);
//            if (index == -1) {
//                for (int j = 0; j < array.length; j++) {
//                    if (array[j] == num) {
//                        System.out.println("出错了");
//                    }
//                }
//            } else if (array[index] != num) {
//                System.out.println("出错了");
//            }

//            int[] array = ArrayUtils.generalArray(30, 15);
//            int num = (int) (Math.random() * 10) + 1;
//            Arrays.sort(array);
//            int index = day3.rightNoLargeNum(array, num);
//            if (index == -1) {
//                for (int j = 0; j < array.length; j++) {
//                    if (array[j] <= num) {
//                        System.out.println("出错了");
//                    }
//                }
//            } else if (index == array.length - 1){
//                if (array[index] > num) {
//                    System.out.println("出错了");
//                }
//            } else {
//                if (array[index] > num || array[index + 1] <= num) {
//                    System.out.println("出错了");
//                }
//            }
            int[] array = ArrayUtils.nextNoEqArray(20, 50);
            int index = day3.rangeMin(array);
            if (index == -1) {
                if (array.length > 0) {
                    System.out.println("出错了");
                }
            } else if (index == 0) {
                if (array.length != 1 && array[0] > array[1]) {
                    System.out.println("出错了");
                }
            } else if (index == array.length - 1) {
                if (array[array.length - 2] < array[array.length - 1]) {
                    System.out.println("出错了");
                }
            } else {
                if (array[index] > array[index - 1] || array[index] > array[index+1]) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("正确");
    }

    /**
     * 二分法
     */
    public int halfSearch(int[] arr, int num) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            // 中间
            int middle = (left+right)>>1;
            if (arr[middle] == num) {
                return middle;
            }
            if (arr[middle] > num) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 从小到大
     * 最左不小于num的数
     */
    public int leftNoLessNum(int[] arr, int num) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        int index = -1;
        while (left <= right) {
            int middle = (left+right) >> 1;
            if (arr[middle] >= num) {
                index = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

    /**
     * 最右不大于
     */
    public int rightNoLargeNum(int[] arr, int num) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        int index = -1;
        while (left <= right) {
            int middle = (left + right) >> 1;
            if (arr[middle] > num) {
                right = middle - 1;
            } else {
                index = middle;
                left = middle+1;
            }
        }
        return index;
    }

    /**
     * 相邻不等，获取局部最小
     * @param arr
     * @return
     */
    public int rangeMin(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1) {
            return 0;
        }
        // 校验头尾
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length-1] < arr[arr.length-2]) {
            return arr.length-1;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left < right - 1) {
            int middle = (left + right) >> 1;
            if (arr[middle] < arr[middle - 1] && arr[middle] < arr[middle + 1]) {
                return middle;
            }
            if (arr[middle] < arr[middle - 1]) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return arr[left] < arr[right]? left : right;
    }



}
