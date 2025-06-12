package coding.tixi.day24;

/**
 *
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列，也不再同一条斜线上
 *
 * 给定一个正数n, 返回n皇后的摆法有多少种
 * n=1, 返回1
 * n=2或3， 2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 *
 * @author linzherong
 * @date 2025/6/4 00:09
 */
public class NQueens {

    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] arr = new int[n];
        return process1(arr, 0);
    }

    public static int process1(int[] arr, int i) {
        if (arr.length == i) {
            return 1;
        }
        int result = 0;
        for (int j = 0; j < arr.length; j++) {
            if (valid(arr, j, i)) {
                arr[i] = j;
                result += process1(arr, i+1);
            }
        }
        return result;
    }
    public static boolean valid(int[] arr, int j, int index) {
        for (int i = 0; i < index; i++) {
            if (arr[i] == j || Math.abs(arr[i]-j) == Math.abs(index-i)) {
                return false;
            }
        }
        return true;
    }


    public static int num2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n > 31) {
            return -1;
        }
        // 1的位置就是需要存放的数量
        int limit = (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    public static int process2(int limit, int cur, int left, int right) {
        if (limit == cur) {
            // 放满了
            return 1;
        }
        // post 中 1的位置 则为当前能放置的位置
        int post = limit & ~(cur | left | right);
        int result = 0;
        while (post != 0) {
            // 获取最后边的1，尝试
            int rightest = post & (~post+1);
            post = post - rightest;
            result += process2(limit, cur | rightest, (left|rightest) << 1, (right | rightest) >>> 1);
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 15;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }


}
