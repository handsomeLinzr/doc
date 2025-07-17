package coding.tixi.day40;

import java.util.*;

/**
 *
 * 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
 *
 * @author linzherong
 * @date 2025/6/30 12:50
 */
public class SubsquenceMaxModM {

    public static int max2(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, 0, m);
    }

    public static int process(int[] arr, int index, int mod, int m) {
        if (index == arr.length) {
            return mod;
        }
        int p1 = process(arr, index+1, (mod+arr[index])%m, m);
        int p2 = process(arr, index+1, mod, m);
        return Math.max(p1, p2);
    }

    public static int max3(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][m];
        for (int j = 0; j < m; j++) {
            dp[N][j] = j;
        }
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = Math.max(dp[i+1][j], dp[i+1][(j+arr[i])%m]);
            }
        }
        return dp[0][0];
    }

    // 如果m很大，则不适合简单dp，如果arr长度不大，则用分治
    public static int max1(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int middle = (N-1) >> 1;
        // 左右两边分别处理
        TreeSet<Integer> leftTree = new TreeSet<>();
        process1(leftTree, arr, 0, middle, 0, m);
        TreeSet<Integer> rightTree = new TreeSet<>();
        process1(rightTree, arr, middle+1, N-1, 0, m);
        int max = 0;
        // 这种情况其实不用把 left 和 right 都单独比较
        // 因为 left 和 right 中都必有 0 的情况
        // 当选到了0， 则也能间接处理了单独另一边的情况
//        for (Integer i : leftTree) {
//            max = Math.max(max, i);
//        }
//        for (Integer i : rightTree) {
//            max = Math.max(max, i);
//            max = Math.max(max, i + (leftTree.floor(m-1-i)));
//        }
        for (Integer left : leftTree) {
            max = Math.max(max, left + rightTree.floor(m - 1 - left));
        }
        return max;
    }

    public static void process1(TreeSet<Integer> set, int[] arr, int start, int end, int mod, int m) {
        if (start > end) {
            set.add(mod % m);
            return;
        }
        process1(set, arr, start+1, end, mod+arr[start], m);
        process1(set, arr, start+1, end, mod, m);
    }

    /**
     * 暴力解法：生成所有子序列，计算每个子序列的累加和%m，返回最大值
     */
    public static int maxByViolence(int[] arr, int m) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        List<List<Integer>> allSubsequences = generateAllSubsequences(arr);
        int maxMod = 0;
        
        for (List<Integer> subsequence : allSubsequences) {
            int sum = 0;
            for (int num : subsequence) {
                sum += num;
            }
            maxMod = Math.max(maxMod, sum % m);
        }
        
        return maxMod;
    }
    
    /**
     * 生成所有子序列
     */
    private static List<List<Integer>> generateAllSubsequences(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        generateSubsequences(arr, 0, new ArrayList<>(), result);
        return result;
    }
    
    /**
     * 递归生成子序列
     */
    private static void generateSubsequences(int[] arr, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == arr.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // 不选择当前元素
        generateSubsequences(arr, index + 1, current, result);
        
        // 选择当前元素
        current.add(arr[index]);
        generateSubsequences(arr, index + 1, current, result);
        current.remove(current.size() - 1);
    }
    
    /**
     * 生成随机测试用例
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        Random random = new Random();
        int size = random.nextInt(maxSize) + 1;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(maxValue + 1); // 非负数组
        }
        return arr;
    }
    
    /**
     * 生成随机模数
     */
    public static int generateRandomM(int maxM) {
        Random random = new Random();
        return random.nextInt(maxM) + 1; // 正数
    }
    
    /**
     * 对数器：比较两个方法的计算结果
     */
    public static boolean isEqual(int[] arr, int m) {
        int result1 = max1(arr, m);
        int result2 = maxByViolence(arr, m);
        return result1 == result2;
    }
    
    /**
     * 打印数组
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 10;
        int maxValue = 100;
        int maxM = 50;
        
        System.out.println("开始测试对数器...");
        
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int m = generateRandomM(maxM);
            
            if (!isEqual(arr, m)) {
                System.out.println("测试失败！");
                System.out.print("数组: ");
                printArray(arr);
                System.out.println("模数: " + m);
                System.out.println("max1结果: " + max1(arr, m));
                System.out.println("暴力解法结果: " + maxByViolence(arr, m));
                return;
            }
        }
        
        System.out.println("所有测试通过！");
        
        // 额外测试一些边界情况
        System.out.println("\n测试边界情况:");
        
        // 空数组
        int[] emptyArr = {};
        int m1 = 5;
        System.out.println("空数组测试: " + isEqual(emptyArr, m1));
        
        // null数组
        int[] nullArr = null;
        int m2 = 3;
        System.out.println("null数组测试: " + isEqual(nullArr, m2));
        
        // 小数组测试
        int[] smallArr = {1, 2, 3};
        int m3 = 4;
        System.out.print("小数组: ");
        printArray(smallArr);
        System.out.println("模数: " + m3);
        System.out.println("max1结果: " + max1(smallArr, m3));
        System.out.println("暴力解法结果: " + maxByViolence(smallArr, m3));
    }
}
