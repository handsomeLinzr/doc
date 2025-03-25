package coding.xin;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * 冒泡排序
 * 选择排序
 * 插入排序
 * 归并排序 * 2
 * 快速排序 * 2
 *
 * 自定义对数器
 *
 * @author linzherong
 * @date 2025/3/23 16:20
 */
public class SortTest {

    public static void main(String[] args) {
        SortTest sortTest = new SortTest();

        for (int i = 0; i < 100_0000; i++) {
            int maxLength = 100;
            int max = 100;
            int length = (int) (Math.random() * maxLength);

            int[] arr = new int[length];
            for (int j = 0; j < length; j++) {
                int left = (int) (Math.random() * (max + 1));
                int right = (int) (Math.random() * (max + 1));
                arr[j] = left - right;
            }

            int[] arr1 = new int[length];
            int[] arr2 = new int[length];
            int[] arr3 = new int[length];
            int[] arr41 = new int[length];
            int[] arr42 = new int[length];
            int[] arr51 = new int[length];
            int[] arr52 = new int[length];
            System.arraycopy(arr, 0, arr1, 0, length);
            System.arraycopy(arr, 0, arr2, 0, length);
            System.arraycopy(arr, 0, arr3, 0, length);
            System.arraycopy(arr, 0, arr41, 0, length);
            System.arraycopy(arr, 0, arr42, 0, length);
            System.arraycopy(arr, 0, arr51, 0, length);
            System.arraycopy(arr, 0, arr52, 0, length);
            Arrays.sort(arr);
            sortTest.insertSort(arr1);
            sortTest.bubbleSort(arr2);
            sortTest.selectSort(arr3);
            sortTest.quickSort1(arr41);
            sortTest.quickSort2(arr42);
            sortTest.mergeSort1(arr51);
            sortTest.mergeSort2(arr52);
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] != arr1[j]) {
                    System.out.println("出错了1");
                }
                if (arr[j] != arr2[j]) {
                    System.out.println("出错了2");
                }
                if (arr[j] != arr3[j]) {
                    System.out.println("出错了3");
                }
                if (arr[j] != arr41[j]) {
                    System.out.println("出错了41");
                }
                if (arr[j] != arr42[j]) {
                    System.out.println("出错了42");
                }
                if (arr[j] != arr51[j]) {
                    System.out.println("出错了51");
                }
                if (arr[j] != arr52[j]) {
                    System.out.println("出错了52");
                }
            }
        }
        System.out.println("成功");
    }


    public void insertSort(int[] arr) {
        if (validata(arr)) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            int insert = i;
            int num = arr[i];
            for (; insert > 0 && arr[insert-1] > num; insert--) {
                arr[insert] = arr[insert-1];
            }
            arr[insert] = num;
        }
    }

    public void bubbleSort(int[] arr) {
        if (validata(arr)) {
            return;
        }
        for (int desc = arr.length-1; desc > 0; desc --) {
            for (int i = 0; i < desc; i++) {
                if (arr[i] > arr[i+1]) {
                    swap(arr, i, i+1);
                }
            }
        }
    }

    public void selectSort(int[] arr) {
        if (validata(arr)) {
            return;
        }
        for (int desc = 0; desc < arr.length-1; desc++) {
            int min = desc;
            for (int i = desc+1; i < arr.length; i++) {
                if (arr[i] < arr[min]) {
                    min = i;
                }
            }
            swap(arr, desc, min);
        }
    }
    public void quickSort1(int[] arr) {
        if (validata(arr)) {
            return;
        }
        processQuickSort(arr, 0, arr.length-1);
    }
    public void processQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] partition = partition(arr, left, right);
        processQuickSort(arr, left, partition[0]-1);
        processQuickSort(arr, partition[1]+1, right);
    }
    public int[] partition(int[] arr, int left, int right) {
        // 随机取一个标准数
        int num = arr[((int) (Math.random() * (right - left) + 1)) + left];
        int less = left - 1;
        int large = right + 1;
        int cur = left;
        while (cur < large) {
            if (arr[cur] < num ) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > num) {
                swap(arr, --large, cur);
            } else {
                cur++;
            }
        }
        return new int[]{less+1, large-1};
    }

    public void quickSort2(int[] arr) {
        if (validata(arr)) {
            return;
        }
        Stack<Task> stack = new Stack<>();
        stack.push(new Task(0, arr.length-1));
        while (!stack.isEmpty()) {
            Task pop = stack.pop();
            int[] partition = partition(arr, pop.left, pop.right);
            if (pop.left < partition[0]-1) {
                stack.push(new Task(pop.left, partition[0]-1));
            }
            if (pop.right > partition[1] + 1) {
                stack.push(new Task(partition[1] + 1, pop.right));
            }
        }
    }

    class Task {
        int left;
        int right;
        public Task(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public void mergeSort1(int[] arr) {
        if (validata(arr)) {
            return;
        }
        partSort(arr, 0, arr.length-1);
    }
    public void partSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = left + ((right - left) >> 1);
        partSort(arr, left, middle);
        partSort(arr, middle+1, right);
        merge(arr, left, middle, right);
    }
    public void merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int l = left;
        int r = middle+1;
        int i = 0;
        while (l <= middle && r <= right) {
            if (arr[l] <= arr[r]) {
                help[i++] = arr[l++];
            } else {
                help[i++] = arr[r++];
            }
        }
        while (l <= middle) {
            help[i++] = arr[l++];
        }
        while (r <= right) {
            help[i++] = arr[r++];
        }
        // help 合并到 arr 上
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
    }

    public void mergeSort2(int[] arr) {
        if (validata(arr)) {
            return;
        }
        int length = arr.length;
        int step = 1;
        while (step < length) {
            int begin = 0;
            int last = length - 1;
            int middle;
            int right;
            while (begin <= last) {
                // 中间
                if (last - (begin - 1) <= step) {
                    // 剩下的已经不够或者刚好一个步长，不用再循环了
                    // 提前判断，避免 begin + step 越界
                    break;
                }
                middle = begin + step - 1;
                if (last - middle <= step) {
                    // 这里提前判断，避免 middle + step 越界
                    right = last;
                } else {
                    right = middle + step;
                }
                merge(arr, begin, middle, right);
                // right + 1 不需要考虑越界，因为 right + 1 == length， length 是一个 int 类型的数字
                begin = right + 1;
            }
            if (step > length / 2) {
                // 提前判断，避免 step * 2 越界
                break;
            }
            step *= 2;
        }
    }

    public void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public boolean validata(int[] arr) {
        return Objects.isNull(arr) || arr.length < 2;
    }


}
