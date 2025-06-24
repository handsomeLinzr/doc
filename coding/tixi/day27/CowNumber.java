package coding.tixi.day27;

/**
 *
 * 一个农场中，每一头成年母牛每年生下一头母牛
 * 每头刚生下来的母牛在第三年成熟
 * 农场中第一年有一头成熟母牛
 * 第N年有多少头母牛？
 *
 * @author linzherong
 * @date 2025/6/18 01:06
 */
public class CowNumber {

    // 斐波那契数列  O(N)
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        // 1  2  3
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return f1(n-1) + f1(n-3);
    }

    // O(N)
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        // 1  2  3
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int p1 = 3;
        int p2 = 2;
        int p3 = 1;
        int resp = 0;
        for (int i = 4; i <= n; i++) {
            resp = p1 + p3;
            p3 = p2;
            p2 = p1;
            p1 = resp;
        }
        return resp;
    }

    // 斐波那契数列套路
    // 只有要 fn = i*f(n-1) + j*f(n-2) + ... 即可
    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 对应三阶矩阵
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}
        };
        int[][] res = matrixPower(base, n-3);
        return 3 *res[0][0] + 2 * res[1][0] + res[2][0];
    }

    /**
     * 矩阵平方
     */
    public static int[][] matrixPower(int[][] m, int p) {
        int N = m.length;
        int M = m[0].length;
        int[][] resp = new int[N][M];
        // 1
        for (int i = 0; i < Math.max(N, M); i++) {
            resp[i][i] = 1;
        }
        int[][] mul = m;
        while (p != 0) {
            if ((p & 1) == 1) {
                resp = product(resp, mul);
            }
            mul = product(mul, mul);
            p >>>= 1;
        }
        return resp;
    }

    /**
     * 矩阵乘法
     */
    public static int[][] product(int[][] a, int [][] b) {
        int N = a.length;
        int M = b[0].length;
        int[][] resp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    resp[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resp;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            int random = (int) (Math.random() * 20);
            int f1 = f1(random);
            int f2 = f2(random);
            int f3 = f3(random);
            if (f1 != f2 || f1 != f3) {
                System.out.println("Oops");
            }
        }
        System.out.println("success");

        System.out.println("================================");
        System.out.println("性能测试开始");

        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 100_0000; i++) {
            f2(i);
        }
        long l2 = System.currentTimeMillis();
        System.out.println("f2耗时：" + (l2-l1));

        long l3 = System.currentTimeMillis();
        for (int i = 0; i < 100_0000; i++) {
            f3(i);
        }
        long l4 = System.currentTimeMillis();
        System.out.println("f3耗时：" + (l4-l3));

        System.out.println("性能测试结束");
    }


}
