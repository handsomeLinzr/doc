package coding.tixi.day38;

import java.util.*;

/**
 * 滑动窗口中位数算法测试类
 * 用于验证算法正确性
 * 
 * @author linzherong
 * @date 2025/7/14
 */
public class SlidingWindowMedianTest {

    public static void main(String[] args) {
        // 测试用例1：基本测试
        testCase1();
        
        // 测试用例2：边界情况
        testCase2();
        
        // 测试用例3：随机数据测试
        testCase3();
        
        // 测试用例4：大数据量测试
        testCase4();
        
        System.out.println("所有测试完成！");
    }

    /**
     * 测试用例1：基本测试
     */
    public static void testCase1() {
        System.out.println("=== 测试用例1：基本测试 ===");
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        
        System.out.println("输入数组: " + Arrays.toString(nums));
        System.out.println("窗口大小: " + k);
        
        double[] result = SlidingWindowMedian.medianSlidingWindow(nums, k);
        double[] expected = {1.0, -1.0, -1.0, 3.0, 5.0, 6.0};
        
        System.out.println("算法结果: " + Arrays.toString(result));
        System.out.println("期望结果: " + Arrays.toString(expected));
        
        boolean isCorrect = Arrays.equals(result, expected);
        System.out.println("测试结果: " + (isCorrect ? "✓ 通过" : "✗ 失败"));
        
        // 用暴力解法验证
        double[] bruteForce = bruteForceMedian(nums, k);
        System.out.println("暴力解法: " + Arrays.toString(bruteForce));
        boolean bruteForceCorrect = Arrays.equals(result, bruteForce);
        System.out.println("暴力验证: " + (bruteForceCorrect ? "✓ 通过" : "✗ 失败"));
        System.out.println();
    }

    /**
     * 测试用例2：边界情况
     */
    public static void testCase2() {
        System.out.println("=== 测试用例2：边界情况 ===");
        
        // 测试窗口大小为1的情况
        int[] nums1 = {1, 2, 3, 4, 5};
        int k1 = 1;
        System.out.println("窗口大小为1的测试:");
        System.out.println("输入: " + Arrays.toString(nums1) + ", k=" + k1);
        double[] result1 = SlidingWindowMedian.medianSlidingWindow(nums1, k1);
        double[] expected1 = {1.0, 2.0, 3.0, 4.0, 5.0};
        System.out.println("结果: " + Arrays.toString(result1));
        System.out.println("期望: " + Arrays.toString(expected1));
        System.out.println("测试: " + (Arrays.equals(result1, expected1) ? "✓ 通过" : "✗ 失败"));
        
        // 测试窗口大小等于数组长度的情况
        int[] nums2 = {1, 2, 3, 4, 5};
        int k2 = 5;
        System.out.println("\n窗口大小等于数组长度的测试:");
        System.out.println("输入: " + Arrays.toString(nums2) + ", k=" + k2);
        double[] result2 = SlidingWindowMedian.medianSlidingWindow(nums2, k2);
        double[] expected2 = {3.0};
        System.out.println("结果: " + Arrays.toString(result2));
        System.out.println("期望: " + Arrays.toString(expected2));
        System.out.println("测试: " + (Arrays.equals(result2, expected2) ? "✓ 通过" : "✗ 失败"));
        
        // 测试偶数长度窗口
        int[] nums3 = {1, 2, 3, 4, 5, 6};
        int k3 = 4;
        System.out.println("\n偶数长度窗口的测试:");
        System.out.println("输入: " + Arrays.toString(nums3) + ", k=" + k3);
        double[] result3 = SlidingWindowMedian.medianSlidingWindow(nums3, k3);
        double[] expected3 = {2.5, 3.5, 4.5};
        System.out.println("结果: " + Arrays.toString(result3));
        System.out.println("期望: " + Arrays.toString(expected3));
        System.out.println("测试: " + (Arrays.equals(result3, expected3) ? "✓ 通过" : "✗ 失败"));
        System.out.println();
    }

    /**
     * 测试用例3：随机数据测试
     */
    public static void testCase3() {
        System.out.println("=== 测试用例3：随机数据测试 ===");
        
        Random random = new Random(42); // 固定种子保证可重复
        int[] nums = new int[20];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(100) - 50; // -50到49的随机数
        }
        int k = 7;
        
        System.out.println("随机数组: " + Arrays.toString(nums));
        System.out.println("窗口大小: " + k);
        
        double[] result = SlidingWindowMedian.medianSlidingWindow(nums, k);
        double[] bruteForce = bruteForceMedian(nums, k);
        
        System.out.println("算法结果: " + Arrays.toString(result));
        System.out.println("暴力解法: " + Arrays.toString(bruteForce));
        
        boolean isCorrect = Arrays.equals(result, bruteForce);
        System.out.println("随机测试: " + (isCorrect ? "✓ 通过" : "✗ 失败"));
        System.out.println();
    }

    /**
     * 测试用例4：大数据量测试
     */
    public static void testCase4() {
        System.out.println("=== 测试用例4：大数据量测试 ===");
        
        int[] nums = new int[1000];
        Random random = new Random(123);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(10000);
        }
        int k = 100;
        
        System.out.println("数组长度: " + nums.length);
        System.out.println("窗口大小: " + k);
        
        long startTime = System.currentTimeMillis();
        double[] result = SlidingWindowMedian.medianSlidingWindow(nums, k);
        long endTime = System.currentTimeMillis();
        
        System.out.println("算法执行时间: " + (endTime - startTime) + "ms");
        System.out.println("结果数组长度: " + result.length);
        
        // 验证前几个结果
        System.out.println("前5个结果: " + Arrays.toString(Arrays.copyOf(result, Math.min(5, result.length))));
        System.out.println();
    }

    /**
     * 暴力解法：用于验证算法正确性
     * 对每个窗口，将窗口内的数字排序后取中位数
     */
    public static double[] bruteForceMedian(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return new double[0];
        }
        
        int n = nums.length;
        double[] result = new double[n - k + 1];
        
        for (int i = 0; i <= n - k; i++) {
            // 复制当前窗口的数据
            int[] window = new int[k];
            for (int j = 0; j < k; j++) {
                window[j] = nums[i + j];
            }
            
            // 排序
            Arrays.sort(window);
            
            // 计算中位数
            if (k % 2 == 0) {
                // 偶数长度，取中间两个数的平均值
                result[i] = (window[k/2 - 1] + window[k/2]) / 2.0;
            } else {
                // 奇数长度，取中间的数
                result[i] = window[k/2];
            }
        }
        
        return result;
    }

    /**
     * 详细验证函数：比较算法结果和暴力解法结果
     */
    public static void detailedVerification(int[] nums, int k) {
        System.out.println("=== 详细验证 ===");
        System.out.println("数组: " + Arrays.toString(nums));
        System.out.println("窗口大小: " + k);
        
        double[] algorithmResult = SlidingWindowMedian.medianSlidingWindow(nums, k);
        double[] bruteForceResult = bruteForceMedian(nums, k);
        
        System.out.println("算法结果: " + Arrays.toString(algorithmResult));
        System.out.println("暴力结果: " + Arrays.toString(bruteForceResult));
        
        if (Arrays.equals(algorithmResult, bruteForceResult)) {
            System.out.println("✓ 验证通过：算法结果与暴力解法一致");
        } else {
            System.out.println("✗ 验证失败：算法结果与暴力解法不一致");
            
            // 找出第一个不同的位置
            for (int i = 0; i < Math.min(algorithmResult.length, bruteForceResult.length); i++) {
                if (Math.abs(algorithmResult[i] - bruteForceResult[i]) > 1e-9) {
                    System.out.println("第一个不同的位置: " + i);
                    System.out.println("算法结果: " + algorithmResult[i]);
                    System.out.println("暴力结果: " + bruteForceResult[i]);
                    break;
                }
            }
        }
        System.out.println();
    }
} 