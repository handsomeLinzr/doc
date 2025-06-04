package coding.tixi.day23;

/**
 *
 * arr 面值，每种无限
 * 最后能组成 aim 钱，返回最小张数
 *
 * @author linzherong
 * @date 2025/6/2 21:23
 */
public class MinCoinsNoLimit {

    public static int minCoins(int[] coins, int aim) {
        if (aim == 0) {
            return 0;
        }
        if (coins == null || coins.length == 0 || aim < 0) {
            return Integer.MAX_VALUE;
        }
        return p1(coins, 0, aim);
    }

    public static int p1(int[] coins, int index, int rest) {
        if (index == coins.length) {
            return rest == 0? 0 : Integer.MAX_VALUE;
        }
        int min = Integer.MAX_VALUE;
        for (int times = 0; times * coins[index] <= rest; times++) {
            int restNum = p1(coins, index + 1, rest - (coins[index] * times));
            if (restNum != Integer.MAX_VALUE) {
                min = Math.min(min, restNum + times);
            }
        }
        return min;
    }

    public static int dp(int[] coins, int aim) {
        if (aim == 0) {
            return 0;
        }
        if (coins == null || coins.length == 0 || aim < 0) {
            return Integer.MAX_VALUE;
        }
        int N = coins.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 0;
        for (int rest = 1; rest <= aim ; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N-1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {

                int min = Integer.MAX_VALUE;
                for (int times = 0; times * coins[index] <= rest; times++) {
                    int restNum = dp[index+1][rest - (coins[index] * times)];
                    if (restNum != Integer.MAX_VALUE) {
                        min = Math.min(min, restNum + times);
                    }
                }
                dp[index][rest] = min;
            }
        }
        return dp[0][aim] ;
    }

    public static int   dp2(int[] coins, int aim) {
        if (aim == 0) {
            return 0;
        }
        if (coins == null || coins.length == 0 || aim < 0) {
            return Integer.MAX_VALUE;
        }
        int N = coins.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 0;
        for (int rest = 1; rest <= aim ; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N-1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int result = dp[index+1][rest];
                if (rest >= coins[index] && dp[index][rest - coins[index]] != Integer.MAX_VALUE) {
                    result = Math.min(result, dp[index][rest - coins[index]] + 1);
                }
                dp[index][rest] = result;
            }
        }
        return dp[0][aim];

    }




    public static int ans(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        if (arr == null || arr.length == 0) {
            return Integer.MAX_VALUE;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0
                        && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans = ans(arr, aim);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp2(arr, aim);
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
        System.out.println("功能测试结束");
    }


}
