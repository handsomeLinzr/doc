package coding.xin;

import java.util.Arrays;

/**
 * @author linzherong
 * @date 2025/3/11 17:06
 */
public class Day2 {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
//        double x = 0.34;
//        int count2 = 0;
//        int count3 = 0;
//        for (int i = 0; i < 10000000; i++) {
//            if (day2.xToXPower2(x)) {
//                count2++;
//            }
//            if (day2.xToXPower3(x)) {
//                count3++;
//            }
//        }
//        System.out.println((double) count2 / 10000000);
//        System.out.println(Math.pow(x, 2));
//
//        System.out.println((double) count3 / 10000000);
//        System.out.println(Math.pow(x, 3));
//        int[] arr = new int[40];
//        for (int i = 0; i < 100000000; i++) {
//            arr[day2.f4()]++;
//        }
        int[] arr = new int[2];
        for (int i = 0; i < 100_0000; i++) {
            arr[day2.y()] ++ ;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println((double)arr[i] / 100_0000);
        }
    }

    /**
     * 生成随机数组
     */
    public static int[] generArr(int minLength, int maxLength, int min, int max) {
        int[] arr = new int[random(minLength, maxLength)];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random(min, max);
        }
        return arr;
    }

    /**
     * 获取范围内的随机数
     */
    private static int random(int min, int max) {
        int n = max - min;
        return (int) (Math.random() * (n+1)) + min;
    }


    /**
     * 范围和
     */
    static class RangeSum {
        // 临时数组
        int[] help;
        public RangeSum(int[] arr) {
            help = new int[arr.length];
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                help[i] = sum = arr[i] + sum;
            }
        }

        /**
         * 获取范围和
         */
        public int rangeSum(int left, int right) {
            return left == 0? help[right] : help[right] - help[left-1];
        }
    }

    /**
     * 概率 x平方
     */
    private boolean xToXPower2(double x) {
        return Math.max(Math.random(), Math.random()) <= x;

//        return Math.min(Math.random(), Math.random()) <= x;

    }

    /**
     * 概率 x立方
     */
    private boolean xToXPower3(double x) {
        return Math.max(Math.random(), Math.max(Math.random(), Math.random())) <= x;
    }

    /**
     * 等概览返回1到5
     * @return
     */
    private int f1() {
        return (int) (Math.random() * 5) + 1;
    }

    /**
     * 利用f1生成的0——1发生器
     * @return
     */
    private int f1_to_0_1() {
        int s;
        do {
            s = f1();
        } while (s == 3);
        return s <= 2?0:1;
    }

    /**
     * 利用f1，等概览返回1到7
     * @return
     */
    private int f2() {
        int result;
        do {
            result = f1_to_0_1() << 2 | f1_to_0_1()<<1 | f1_to_0_1();
        } while (result == 0);
        return result;
    }

    /**
     * 等概率返回 11到25
     * @return
     */
    private int f3() {
        return (int) (Math.random() * 15) + 11;
    }

    private int f3_to_0_1() {
        int result;
        do {
            result = f3();
        } while (result == 18);
        return result <= 17? 0:1;
    }

    /**
     * 利用 f3 等概率返回 17到38
     * @return
     */
    private int f4() {
        int result;
        do {
            result = (f3_to_0_1()<<4)+(f3_to_0_1()<<3)+(f3_to_0_1()<<2)+(f3_to_0_1()<<1)+f3_to_0_1();
        } while (result > 21);
        return result + 17;
    }

    /**
     * 固定（不等）概览返回 0 和 1
     * 80% 返回 0 ， 20% 返回 1
     * @return
     */
    private int x() {
        return Math.random() < 0.8? 0 : 1;
    }

    /**
     * 利用 x 等概率返回 0和1
     * @return
     */
    private int y() {
        int result;
        do {
            result = x();
        } while (result == x());
        return result;
    }


}
