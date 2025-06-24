package coding.tixi.day27;

/**
 *
 * 给定一个数N，只由 0 和 1 组成，组成长度为 N 的字符串，
 * 如果这个字符串中，所有 0 的左边都有 1 挨着， 则达标
 * 返回有多少个达标字符串
 *
 * @author linzherong
 * @date 2025/6/18 01:04
 */
public class ZeroLeftOneStringNumber {

    public static int f1(int N) {
        if (N < 1) {
            return 0;
        }
        if (N == 1 || N == 2) {
            return N;
        }
        return f1(N-1) + f1(N-2);
    }

    public static int f2(int N) {
        if (N < 1) {
            return 0;
        }
        if (N == 1 || N == 2) {
            return N;
        }
        int p1 = 1;
        int p2 = 2;
        int resp = 0;
        for (int i = 3; i <= N; i++) {
            resp = p1 + p2;
            p1 = p2;
            p2 = resp;
        }
        return resp;
    }

    public static int f3(int N) {
        if (N < 1) {
            return 0;
        }
        if (N == 1 || N == 2) {
            return N;
        }
        int[][] base = {{1,1},{1,0}};
        int[][] resp = power(base, N - 2);
        return 2*resp[0][0] + resp[1][0];
    }

    public static int[][] power(int[][] a, int p) {
        int N = a.length;
        int M = a[0].length;
        int[][] resp = new int[N][M];
        for (int i = 0; i < Math.min(N, M); i++) {
            resp[i][i] = 1;
        }
        while (p != 0) {
            if ((p & 1) == 1) {
                resp = mul(resp, a);
            }
            a = mul(a, a);
            p >>>= 1;
        }
        return resp;
    }

    public static int[][] mul(int[][] a, int[][] b) {
        int N = a.length;
        int M = b[0].length;
        int[][] resp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < M; k++) {
                    resp[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resp;
    }

    //===================================================================


    // 板砖 =》1X2
    // 地板 =》2xL
    // 有多少种铺砖方式
    public static int b1(int L) {
        if (L < 1) {
            return 0;
        }
        if (L == 1 || L == 2 ) {
            return L;
        }
        return b1(L-1) + b1(L-2);
    }



    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            int m = (int) (Math.random() * 20);
            int i1 = f1(m);
            int i2 = f2(m);
            int i3 = f3(m);
            if (i1 != i2 || i1 != i3) {
                System.out.println("Oops");
            }
        }
        System.out.println("测试结束");
        System.out.println("=========================");
        System.out.println("新能测试");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            f2(i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("f2耗时：" + (end1 - start1));

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            f3(i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("f3耗时：" + (end2 - start2));
    }

}
