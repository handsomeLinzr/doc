package coding.tixi.day26;

import coding.tixi.ArrayUtils;

import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * 给定一个数组，获取其中每个数字最近的左右两边小于的位置
 *
 * @author linzherong
 * @date 2025/6/9 00:56
 */
public class MonotonousStack {

    // 没有重复的情况
    public static int[][] getNearLessNoRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int N = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[][] ans = new int[N][2];
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >  arr[i]) {
                Integer pop = stack.pop();
                ans[pop][0] = stack.isEmpty()? -1 : stack.peek();
                ans[pop][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            // 压栈结束，则此栈中的数据右边都没有小于的数
            Integer pop = stack.pop();
            ans[pop][0] = stack.isEmpty()? -1 : stack.peek();
            ans[pop][1] = -1;
        }
        return ans;
    }

    public static int[][] getNearLess(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int N = arr.length;
        int[][] ans = new int[N][2];
        Stack<LinkedList<Integer>> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && arr[stack.peek().getLast()] > arr[i]) {
                LinkedList<Integer> pop = stack.pop();
                for (Integer cIndex : pop) {
                    ans[cIndex][0] = stack.isEmpty()? -1 : stack.peek().getLast();
                    ans[cIndex][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().getLast()] == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.addLast(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            LinkedList<Integer> pop = stack.pop();
            for (Integer cIndex : pop) {
                ans[cIndex][0] = stack.isEmpty()? -1 : stack.peek().getLast();
                ans[cIndex][1] = -1;
            }
        }
        return ans;
    }


    public static int[][] right(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int N = arr.length;
        int[][] result = new int[N][2];
        for (int i = 0; i < N; i++) {
            int leftI = i-1;
            while (leftI >= 0 && arr[leftI] >= arr[i]) {
                leftI --;
            }
            result[i][0] = leftI;
            int rightI = i+1;
            while (rightI < N && arr[rightI] >= arr[i]) {
                rightI++;
            }
            result[i][1] = rightI >= N? -1 : rightI;
        }
        return result;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1 == res2) {
            return true;
        }
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int time = 100_0000;
        System.out.println("测试开始");
        for (int i = 0; i < time; i++) {
            int[] arr = ArrayUtils.generalArrNoRepeat(10);
            int[][] ans = right(arr);
            int[][] ans1 = getNearLessNoRepeat(arr);
            if (!isEqual(ans, ans1)) {
                System.out.println("Oops!");
                break;
            }
            int[] arr2 = ArrayUtils.generalArr(10);
            int[][] ans3 = right(arr2);
            int[][] ans4 = getNearLess(arr2);
            if (!isEqual(ans3, ans4)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
