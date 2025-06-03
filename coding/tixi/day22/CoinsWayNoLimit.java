package coding.tixi.day22;

/**
 *
 * arr 表示面额，每个面额可以有无数张，要求能获得 aim 钱的组合有多少
 *
 * @author linzherong
 * @date 2025/6/1 21:11
 */
public class CoinsWayNoLimit {

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0? 1 : 0;
        }
        if (rest < arr[index]) {
            // 不能选
            return process(arr, index+1, rest);
        } else {
            int ways = 0;
            for (int times = 0; times <= (rest / arr[index]); times++) {
                ways += process(arr, index+1, rest - (arr[index] * times));
            }
            return ways;
        }
    }

    public static int myDp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                if (j < arr[i]) {
                    dp[i][j] = dp[i+1][j];
                } else {
                    int ways = 0;
                    for (int times = 0; times <= (j / arr[i]); times++) {
                        ways += dp[i+1][j-(arr[i]*times)];
                    }
                    dp[i][j] = ways;
                }
            }
        }
        return dp[0][aim];
    }

    public static int myDp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                if (j < arr[i]) {
                    dp[i][j] = dp[i+1][j];
                } else {
                    dp[i][j] = dp[i+1][j] + dp[i][j-arr[i]];
                }
            }
        }
        return dp[0][aim];
    }










    // test
    public static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans = dp(arr, aim);
            int ans1 = coinsWay(arr, aim);
            int ans2 = myDp(arr, aim);
            int ans3 = myDp1(arr, aim);
            if (ans != ans1 || ans != ans2 || ans != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }


}
