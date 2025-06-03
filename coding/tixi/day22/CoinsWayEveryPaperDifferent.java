package coding.tixi.day22;

/**
 *
 * arr 数组表示当前有的钱，要求通过 arr数组组成 aim 钱的方式
 *
 * @author linzherong
 * @date 2025/6/1 21:01
 */
public class CoinsWayEveryPaperDifferent {

    public static int coinWays(int[] arr, int aim) {
        if (arr == null || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    public static int process1(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) {
            return rest == 0? 1 : 0;
        }
        // 本次index要
        return process1(arr, index+1, rest-arr[index])
                // 本次 index 不要
                + process1(arr, index+1, rest);
    }

    public static int coinWays2(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int y = j - arr[i];
                if (y < 0 ){
                    dp[i][j] = dp[i+1][j];
                } else {
                    dp[i][j] = dp[i+1][y] + dp[i+1][j];
                }

            }
        }
        return dp[0][aim];

    }



    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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

    public static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 100_0000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = coinWays2(arr, aim);
            if ((ans1 != ans2)   ||  (ans1 != ans3)) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
