package coding.tixi.day22;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * arr 是货币数组，都为正数，给定一个目标 aim 钱
 * arr中货币相同的钱认为是一样的
 * 要求从arr中返回能组成 aim 钱的方法有多少种
 *
 * @author linzherong
 * @date 2025/6/1 21:34
 */
public class CoinsWaySameValueSamePapper {

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo1(arr);
        return process(info.coins, info.zhangs, 0, aim);
    }

    public static int process(int[] coins, int[] num, int index, int rest) {
        if (index == coins.length) {
            return rest == 0? 1 : 0;
        }
        int way = 0;
        for (int time = 0; time <= num[index] && time <= rest/coins[index]; time++) {
            way += process(coins, num, index+1, rest - (coins[index]*time));
        }
        return way;
    }

    public static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo1(arr);
        int[] coins = info.coins;
        int[] num = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int way = 0;
                for (int time = 0; time <= num[i] && time <= j/coins[i]; time++) {
                    way += dp[i+1][j - (coins[i]*time)];
                }
                dp[i][j] = way;
            }
        }
        return dp[0][aim];
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo1(arr);
        int[] coins = info.coins;
        int[] num = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N+1][aim+1];
        dp[N][0] = 1;
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = dp[i+1][j];
                if (j - coins[i] >= 0) {
                    dp[i][j] += dp[i][j-coins[i]];
                }
                if (j >= coins[i]*(num[i]+1)) {
                    dp[i][j] -= dp[i+1][j-coins[i]*(num[i]+1)];
                }
            }
        }
        return dp[0][aim];
    }







    public static Info getInfo1(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        int[] coins = new int[map.size()];
        int[] num = new int[map.size()];
        int index = 0;
        for (Integer i : map.keySet()) {
            coins[index] = i;
            num[index++] = map.get(i);
        }
        return new Info(coins, num);
    }










    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    public static int ans(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                if (rest - coins[index] * (zhangs[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (zhangs[index] + 1)];
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

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans = ans(arr, aim);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp1(arr, aim);
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
