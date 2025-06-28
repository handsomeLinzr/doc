package coding.tixi.day30;

import coding.tixi.ArrayUtils;

/**
 *
 * 给定一个无序数组，长度N，给定正数K，返回topK个最大的数
 * 1. O(N*logN)  正常排序，获取前K个
 * 2. O(N + K*logN)  O(N)建大根堆，循环K次弹出堆 O(K*logN)
 * 3. O(N + K*logK)  O(N) bfprt 算法获取 第 K个大的数（第 N-K小的数），K*logK 将 topK个数进行排序
 *
 * @author linzherong
 * @date 2025/6/22 22:01
 */
public class MaxTopK {

    // O(N*logN)
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] copy = ArrayUtils.copyArray(arr);
        sort(copy);
        k = Math.min(arr.length, k);
        int[] ans = new int[k];
        int max = Math.min(k, copy.length);
        for (int i = 0; i < max; i++) {
            ans[i] = copy[i];
        }
        return ans;
    }
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        process(arr, 0, arr.length-1);
    }
    public static void process(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = left + right >> 1;
        process(arr, left, middle);
        process(arr, middle+1, right);
        merge(arr, left, middle, right);
    }
    public static void merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right-left+1];
        int LB = left;
        int RB = middle+1;
        int index = 0;
        while (LB <= middle && RB <= right) {
            if (arr[LB] >= arr[RB]) {
                help[index++] = arr[LB++];
            } else {
                help[index++] = arr[RB++];
            }
        }
        while (LB <= middle) {
            help[index++] = arr[LB++];
        }
        while (RB <= right) {
            help[index++] = arr[RB++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[left+i] = help[i];
        }
    }

    // O(N + K*logN)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] copy = ArrayUtils.copyArray(arr);
        k = Math.min(arr.length, k);
        int N = copy.length;
        // O(N)
        for (int i = N-1; i >= 0; i--) {
            heapify(copy, i, N-1);
        }
        // O(K*logN)
        for (int i = 0; i < k; i++) {
            swap(copy, 0, N-1-i);
            heapify(copy, 0, N-2-i);
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = copy[N-1-i];
        }
        return ans;

    }
    public static void heapify(int[] arr, int index, int last) {
        int child;
        while ((child = index * 2  + 1)  <= last) {
            child = child + 1 <= last && arr[child] < arr[child+1]? child+1:child;
            if (arr[index] < arr[child]) {
                swap(arr, index, child);
            } else {
                break;
            }
            index = child;
        }
    }
    public static void heapInsert(int[] arr, int index) {
        int parent;
        while ( (parent = (index-1)/ 2) >= 0 ) {
            if (arr[index] > arr[parent]) {
                swap(arr, index, parent);
            } else {
                break;
            }
            index = parent;
        }
    }
    public static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        // N-k 小的位置就是 第 k 大的数字
        int num = getMinK(arr, 0, N-1, N-k);
        int[] ans = new int[k];
        int index = 0;
        for (int i = 0; i < N; i++) {
            if (arr[i] > num) {
                ans[index++] = arr[i];
            }
        }
        for (; index < k; index++) {
            ans[index] = num;
        }
        sort(ans);
        return ans;
    }
    public static int getMinK(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        int middle = getMiddleOfMiddle(arr, left, right);
        int[] p = partition(arr, left, right, middle);
        if (p[0] > k) {
            return getMinK(arr, left, p[0]-1, k);
        } else if (p[1] < k) {
            return getMinK(arr, p[1]+1, right, k);
        } else {
            return arr[k];
        }
    }
    // 获取中位数
    public static int getMiddleOfMiddle(int[] arr, int left, int right) {
        int length = right - left + 1;
        int eLength = length % 5 == 0? length / 5 : length / 5 + 1;
        int[] mArr = new int[eLength];
        for (int i = 0; i < mArr.length; i++) {
            mArr[i] = getMiddleBySort(arr, i*5+left, Math.min(i*5+left+5, right));
        }
        return getMinK(mArr, 0, mArr.length-1, mArr.length/2);
    }
    public static int[] partition(int[] arr, int left, int right, int pivot) {
        int less = left-1;
        int large = right+1;
        int index = left;
        while (index < large) {
            if (arr[index] < pivot) {
                swap(arr, ++less, index++);
            } else if (arr[index] > pivot) {
                swap(arr, --large, index);
            } else {
                index++;
            }
        }
        return new int[]{less+1, large-1};
    }
    public static int getMiddleBySort(int[] arr, int left, int right) {
        for (int i = left+1; i <= right; i++) {
            int cur = i;
            int num = arr[cur];
            while (cur > 0 && arr[cur] < arr[cur-1]) {
                arr[cur] = arr[cur-1];
                cur--;
            }
            arr[cur] = num;
        }
        return arr[(left+right) / 2];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] ans1 = maxTopK1(arr, k);
            int[] ans2 = maxTopK2(arr, k);
            int[] ans3 = maxTopK3(arr, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }

}
