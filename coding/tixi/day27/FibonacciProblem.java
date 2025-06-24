package coding.tixi.day27;

/**
 *
 * 第一年农场有1只母牛，往后每年
 * 1. 每1只成熟的母牛都会生出1只母牛
 * 2. 每1只新出生的母牛都会在出生的第3年成熟
 * 3. 每1只母牛永远不会死
 * 求N年后母牛的数量
 *
 * @author linzherong
 * @date 2025/6/17 00:47
 */
public class FibonacciProblem {

    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n-1) + f1(n-2);
    }

    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int resp = 0;
        int t1 = 1;
        int t2 = 1;
        for (int i = 3; i <= n; i++) {
            resp = t1 + t2;
            t1 = t2;
            t2 = resp;
        }
        return resp;
    }

    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // |fn, fn-1| = |f2,f1| * |{{1,1,}{1,0}}| ^ n-2
        int[][] base = {
                {1,1},
                {1,0}
        };
        int[][] power = matrixPower(base, n - 2);
        return power[0][0] + power[1][0];
    }

    /**
     * 矩阵平方
     */
    public static int[][] matrixPower(int[][] m, int p) {
        int N = m.length;
        int M = m[0].length;
        int[][] result = new int[N][M];
        // 矩阵 1
        for (int i = 0; i < N; i++) {
            result[i][i] = 1;
        }
        int[][] cur = m;
        while (p != 0) {
            if ((p & 1) != 0) {
                // 二进制位为1，则相乘
                result = product(result, cur);
            }
            cur = product(cur,  cur);
            p >>>= 1;
        }
        return result;
    }

    /**
     * 矩阵乘法
     */
    public static int[][] product(int[][] a, int [][] b) {
        int N = a.length;
        int M = b[0].length;
        int[][] result = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < M; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 100_0000; i++) {
//            int random = (int) (Math.random() * 20);
//            if (f1(random) != f2(random) || f1(random) != f3(random)) {
//                System.out.println("Oops");
//            }
//        }
//        System.out.println("success");
        System.out.println("性能测试开始");
//        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < 42; i++) {
//            int i1 = f1(i);
//            System.out.println(i1);
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("f1计算1到42用时：" + (end1 - start1) + " ms");
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            f2(i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("f2计算1到42用时：" + (end2 - start2) + " ms");
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            f3(i);
        }
        long end3 = System.currentTimeMillis();
        System.out.println("f3计算1到42用时：" + (end3 - start3) + " ms");


        System.out.println("性能测试结束");

    }

}
