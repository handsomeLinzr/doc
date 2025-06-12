package coding.tixi.day24;

/**
 *
 * 给定正数数组arr，把 arr 分成两个集合，相差个数最小，且两边的累加和相差尽量小，求得其中小的集合的累加和
 *
 * @author linzherong
 * @date 2025/6/3 00:48
 */
public class SplitSumClosedSizeHalf {

    public static int split(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int N = arr.length;
        if ((N & 1) == 0) {
            return p1(arr, 0, N/2, sum / 2);
        } else {
            return Math.max(p1(arr, 0, N/2, sum/2), p1(arr, 0, N/2 + 1, sum/2));
        }
    }

    public static int p1(int[] arr, int index, int pick, int total) {
        if (index == arr.length) {
            return pick == 0? 0 : -1;
        }
        int p1 = p1(arr, index+1, pick, total);
        int p2 = -1;
        if (total - arr[index] >= 0) {
            p2 = p1(arr, index+1, pick-1, total-arr[index]);
            if (p2 != -1) {
                p2 = p2 + arr[index];
            }
        }
        return Math.max(p1, p2);
    }

    public static int splitDp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int N = arr.length;
        int M = N / 2;
        int T = sum/2;
        int[][][] dp = new int[N+1][M+2][T+1];
        for (int i = 1; i <= M+1; i++) {
            for (int j = 0; j <= T; j++) {
                dp[N][i][j] = -1;
            }
        }

        for (int index = N-1; index >= 0; index--) {
            for (int pick = 0; pick <= M+1 ; pick++) {
                for (int total = 0; total <= T ; total++) {
                    int p1 = dp[index+1][pick][total];
                    int p2 = -1;
                    if (total - arr[index] >= 0 && pick-1 >= 0) {
                        p2 = dp[index+1][pick-1][total-arr[index]];
                        if (p2 != -1) {
                            p2 = p2 + arr[index];
                        }
                    }
                    dp[index][pick][total] = Math.max(p1, p2);
                }
            }
        }

        if ((N & 1) == 0) {
            return dp[0][M][T];
        } else {
            return Math.max(dp[0][M][T], dp[0][M+1][T]);
        }
    }

    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else {
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }

    // arr[i....]自由选择，挑选的个数一定要是picks个，累加和<=rest, 离rest最近的返回
    public static int process(int[] arr, int i, int picks, int rest) {
        if (i == arr.length) {
            return picks == 0 ? 0 : -1;
        } else {
            int p1 = process(arr, i + 1, picks, rest);
            // 就是要使用arr[i]这个数
            int p2 = -1;
            int next = -1;
            if (arr[i] <= rest) {
                next = process(arr, i + 1, picks - 1, rest - arr[i]);
            }
            if (next != -1) {
                p2 = arr[i] + next;
            }
            return Math.max(p1, p2);
        }
    }

    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 100_0000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans = right(arr);
            int ans1 = split(arr);
            int ans2 = splitDp(arr);
            if (ans != ans1 || ans != ans2) {
                printArray(arr);
                System.out.println(ans);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");

    }

}
