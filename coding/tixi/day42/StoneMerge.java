package coding.tixi.day42;

/**
 *
 * 给 N 堆石头，每次合并相邻的两堆石头，得到的分数是合并后的数量，解题获得最后一堆石头时，分数最少的解
 *
 * @author linzherong
 * @date 2025/7/2 12:48
 */
public class StoneMerge {

    public static int min(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] s = sum(arr);
        return process(0, N - 1, s);
    }
    public static int[] sum(int[] arr) {
        int N = arr.length;
        int[] s = new int[N + 1];
        s[0] = 0;
        for (int i = 0; i < N; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        return s;
    }
    public static int process(int L, int R, int[] s) {
        if (L == R) {
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int leftEnd = L; leftEnd < R; leftEnd++) {
            next = Math.min(next, process(L, leftEnd, s) + process(leftEnd + 1, R, s));
        }
        return next + w(s, L, R);
    }
    public static int w(int[] s, int l, int r) {
        return s[r + 1] - s[l];
    }

    // 思考：
    // 最大 N
    // 0和0|1到N，0和1|2到N，0和2|3到N
    public static int min1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process1(arr, 0, arr.length-1);
    }
    public static int process1(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = left; i < right; i++) {
            min = Math.min(min, process1(arr, left, i) + process1(arr, i+1, right));
        }
        min += getTotal(arr, left, right);
        return min;
    }
    public static int getTotal(int[] arr, int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static int min2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N][N];
        // L > R 不存在
        // L == R  ==>> 0
        // L+1 == R ==> arr[L] + arr[R]
        for (int i = 0; i < N-1; i++) {
            dp[i][i+1] = arr[i] + arr[i+1];
        }
        for (int L = N-3; L >= 0; L--) {
            for (int R = L+2; R < N; R++) {
                int min = Integer.MAX_VALUE;
                for (int i = L; i < R; i++) {
                    min = Math.min(min, dp[L][i] + dp[i+1][R]);
                }
                dp[L][R] = min + getTotal(arr, L, R);
            }
        }
        return dp[0][N-1];
    }

    public static int min3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N][N];
        // 最好的位置
        int[][] base = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i+1] = arr[i]+arr[i+1];
            base[i][i+1] = i;
        }

        for (int L = N-3; L >= 0; L--) {
            for (int R = L+2; R < N ; R++) {
                int baseIndex = -1;
                int min = Integer.MAX_VALUE;
                for (int i = base[L][R-1]; i <= base[L+1][R]; i++) {
                    int curMin = Math.min(min, dp[L][i] + dp[i + 1][R]);
                    if (min > curMin) {
                        min = curMin;
                        baseIndex = i;
                    }
                }
                dp[L][R] = min+getTotal(arr, L, R);
                base[L][R] = baseIndex;
            }
        }
        return dp[0][N-1];
    }


    public static int[] generalArr(int len, int maxValue) {
        int length;
        do {
            length = (int) (Math.random() * len);
        } while (length == 0);
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)(Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int time = 10_0000;
        int len = 10;
        int maxValue = 50;
        for (int i = 0; i < time; i++) {
            int[] arr = generalArr(len, maxValue);
            int ans = min(arr);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans != ans1 || ans != ans2 || ans != ans3) {
                System.out.println("Oops");
            }
        }
        System.out.println("over");
    }


}
