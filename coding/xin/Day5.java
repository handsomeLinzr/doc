package coding.xin;

import java.util.HashSet;
import java.util.Set;

/**
 * bitmap 位图，用 long[] 来存储需要的数字，用于查询这个数字是否存在 （add/delete/contains）
 * 用 位运算 实现 +-*'/  leetcode 29
 * @author linzherong
 * @date 2025/3/14 07:29
 */
public class Day5 {

    public static void main(String[] args) {
        Day5 day5 = new Day5();
        for (int i = 0; i < 100_0000; i++) {
            int a = (int) (Math.random()*20 - Math.random()*10);
            int b = (int) (Math.random()*20 - Math.random()*10);
            int add = day5.add(a, b);
            if (add != (a + b)) {
                System.out.println("出错了");
            }
            int subtract = day5.subtract(a, b);
            if (subtract != (a - b)) {
                System.out.println("出错了");
            }
            int multiply = day5.multiply(a, b);
            if (multiply != (a * b)) {
                System.out.println("出错了");
            }
            if (b != 0) {
                int divide = day5.divide(a, b);
                if (divide != (a / b)) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("完成");
    }

    static class MyBitMap {
        private int minValue;
        private int maxValue;
        private long[] values;
        public MyBitMap(int minValue, int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            // 获取数量
            int num = maxValue - minValue;
            values = new long[(num >>> 6) + 1];
        }
        public void add(int num) {
            if (num < minValue || num > maxValue) {
                throw new RuntimeException("有误");
            }
            // 获取应该存放的位置
            int targetNum = num - minValue;
            // 获取在long中的偏移位置
            int longOffset = targetNum >>> 6;
            // (long) 1) << (targetNum & 63 获取在对应的long中当前数据的位置
            values[longOffset] |= (((long) 1) << (targetNum & 63));
        }

        public void delete(int num) {
            if (num < minValue || num > maxValue) {
                throw new RuntimeException("有误");
            }
            int targetNum = num - minValue;
            int longOffset = targetNum >>> 6;
            values[longOffset] &= (~(((long) 1) << (targetNum & 63)));
        }

        public boolean contain(int num) {
            int targetNum = num - minValue;
            int longOffset = targetNum >>> 6;
            return (values[longOffset] & (((long) 1) << (targetNum & 63))) != 0;
        }
    }

    /**
     * 位运算实现 加减乘除
     */
    public int add(int a, int b) {
        int sum = a;
        int carry = b;
        do {
            int tmp = sum;
            sum = sum ^ carry;
            carry = (tmp & carry) << 1;
        } while (carry != 0);
        return sum;
    }
    public int subtract(int a, int b) {
        return add(a, add(~b, 1));
    }
    public int multiply(int a, int b) {
        int sum = 0;
        while (b != 0) {
            if ((b & 1) == 1) {
                sum = add(sum, a);
            }
            b >>>= 1;
            a <<= 1;
        }
        return sum;
    }
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        }
        if (divisor == Integer.MIN_VALUE) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == nega(1)) {
                return Integer.MAX_VALUE;
            } else {
                int c = div(add(dividend, 1), divisor);
                return add(c, div(subtract(dividend, multiply(c, divisor)), divisor));
            }
        }
        return div(dividend, divisor);
    }

    private int div(int dividend, int divisor) {
        int a = isNega(dividend)? add(~dividend, 1) : dividend;
        int b = isNega(divisor)? add(~divisor, 1) : divisor;
        int result = 0;
        for (int i = 30; i >= 0 && a >= b; i--) {
            if ((a >> i) >= b) {
                result = add(result, 1 << i);
                a = subtract(a, b << i);
            }
        }
        return isNega(dividend) ^ isNega(divisor)? add(~result, 1) : result;
    }

    private boolean isNega(int num) {
        return num < 0;
    }

    private int nega(int num) {
        return add(~num, 1);
    }

}
