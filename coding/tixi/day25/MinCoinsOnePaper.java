package coding.tixi.day25;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * 有 arr 的面额，有重复，组成 aim 需要的最小张数
 *
 * @author linzherong
 * @date 2025/6/9 00:25
 */
public class MinCoinsOnePaper {

    // O(2^N)
    public static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    public static int process1(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0? 0 : Integer.MAX_VALUE;
        }
        int r1 = process1(arr, index+1, rest);
        int r2 = Integer.MAX_VALUE;
        if (arr[index] <= rest) {
            r2 = process1(arr, index+1, rest-arr[index]);
            if (r2 != Integer.MAX_VALUE) {
                r2 = r2 + 1;
            }
        }
        return Math.min(r1, r2);
    }

    // O(N*aim)
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];
        for (int rest = 1; rest <= aim ; rest ++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N-1; index >= 0 ; index --) {
            for (int rest = 0; rest <= aim; rest++) {
                int r1 = dp[index+1][rest];
                int r2 = arr[index] > rest? Integer.MAX_VALUE : dp[index+1][rest-arr[index]];
                if (r2 != Integer.MAX_VALUE) {
                    r2 = r2+1;
                }
                dp[index][rest] = Math.min(r1, r2);
            }
        }
        return dp[0][aim];
    }

    //  O(2^c.length)*max(z平均)
    public static int minCoins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] z = info.zhang;
        int[] c = info.coins;
        return process2(z, c, 0, aim);
    }

    public static int process2(int[] z, int[] c, int index, int rest) {
        if (index == c.length) {
            return rest == 0? 0 : Integer.MAX_VALUE;
        }
        int r1 = process2(z, c, index+1, rest);
        int r2 = Integer.MAX_VALUE;
        for (int i = 1; i <= z[index] && rest >= c[index]*i; i++) {
            int r = process2(z, c, index+1, rest-(c[index]*i));
            if (r != Integer.MAX_VALUE) {
                r = r+i;
            }
            r2 = Math.min(r2, r);
        }
        return Math.min(r1, r2);
    }

    // O(c.length * aim * z(平均))
    public static int dp2(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim <= 0) {
            return 0;
        }
        Info info = getInfo(coins);
        int[] c = info.coins;
        int[] z = info.zhang;
        int N = c.length;
        int[][] dp = new int[N+1][aim+1];
        for (int rest = 1; rest <= aim ; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N-1; index >= 0 ; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index+1][rest];
                int r2 = Integer.MAX_VALUE;
                for (int i = 1; i <= z[index] && rest >= c[index]*i; i++) {
                    int r = dp[index+1][rest-(c[index]*i)];
                    if (r != Integer.MAX_VALUE) {
                        r = r+i;
                    }
                    r2 = Math.min(r2, r);
                }
                dp[index][rest] = Math.min(dp[index][rest], r2);
            }
        }
        return dp[0][aim];
    }

    // dp3时间复杂度为：O(arr长度) + O(货币种数 * aim)
    public static int dp3(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim <= 0) {
            return 0;
        }
        Info info = getInfo(coins);
        int[] c = info.coins;
        int[] z = info.zhang;
        int N = c.length;
        int[][] dp = new int[N+1][aim+1];
        for (int rest = 1; rest <= aim ; rest++) {
            dp[N][rest] = Integer.MAX_VALUE;
        }
        for (int index = N-1; index >= 0 ; index--) {
            for (int rest = 0; rest < c[index] && rest <= aim; rest++) {
                dp[index][rest] = dp[index+1][rest];
                // 小
                LinkedList<Integer> minList = new LinkedList<>();
                minList.addLast(rest);
                int t = 1;
                while (t * c[index] + rest <= aim) {
                    while (!minList.isEmpty()) {
                        if (dp[index+1][minList.peekLast()] == Integer.MAX_VALUE || dp[index+1][minList.peekLast()] + compensate(c[index], t * c[index] + rest, minList.peekLast()) >= dp[index+1][t * c[index] + rest]) {
                            minList.pollLast();
                        } else {
                            break;
                        }
                    }
                    minList.addLast(t * c[index] + rest);
                    dp[index][t * c[index] + rest] = dp[index+1][minList.peekFirst()] == Integer.MAX_VALUE? Integer.MAX_VALUE : dp[index+1][minList.peekFirst()] + compensate(c[index], t * c[index] + rest, minList.peekFirst());
                    if (minList.peekFirst() <= t * c[index] + rest - (z[index] * c[index])) {
                        minList.pollFirst();
                    }
                    t++;
                }
            }

        }
        return dp[0][aim];
    }

    // 补偿
    public static int compensate(int offset, int rest, int restBefore) {
        return (rest - restBefore) / offset;
    }


    public static class Info {
        int[] coins;
        int[] zhang;
        public Info(int[] coins, int[] zhang) {
            this.coins = coins;
            this.zhang = zhang;
        }
    }
    public static Info getInfo(int[] coins) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int coin : coins) {
            if (map.containsKey(coin)) {
                map.put(coin, map.get(coin)+1);
            } else {
                map.put(coin, 1);
            }
        }
        int[] c = new int[map.size()];
        int[] z = new int[map.size()];
        int index = 0;
        for (Integer coin : map.keySet()) {
            c[index] = coin;
            z[index++] = map.get(coin);
        }
        return new Info(c, z);
    }



    public static int right(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    // dp3时间复杂度为：O(arr长度) + O(货币种数 * aim)
    // 优化需要用到窗口内最小值的更新结构
    public static int dp3Right(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] c = info.coins;
        int[] z = info.zhang;
        int N = c.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 虽然是嵌套了很多循环，但是时间复杂度为O(货币种数 * aim)
        // 因为用了窗口内最小值的更新结构
        for (int i = N - 1; i >= 0; i--) {
            for (int mod = 0; mod < Math.min(aim + 1, c[i]); mod++) {
                // 当前面值 X
                // mod mod + x mod + 2*x mod + 3 * x
                LinkedList<Integer> w = new LinkedList<>();
                w.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                for (int r = mod + c[i]; r <= aim; r += c[i]) {
                    while (!w.isEmpty() && (dp[i + 1][w.peekLast()] == Integer.MAX_VALUE
                            || dp[i + 1][w.peekLast()] + compensate(c[i], r, w.peekLast()) >= dp[i + 1][r])) {
                        w.pollLast();
                    }
                    w.addLast(r);
                    int overdue = r - c[i] * (z[i] + 1);
                    if (w.peekFirst() == overdue) {
                        w.pollFirst();
                    }
                    if (dp[i + 1][w.peekFirst()] == Integer.MAX_VALUE) {
                        dp[i][r] = Integer.MAX_VALUE;
                    } else {
                        dp[i][r] = dp[i + 1][w.peekFirst()] + compensate(c[i], r, w.peekFirst());
                    }
                }
            }
        }
        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int N, int maxValue) {
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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = right(arr, aim);
            int ans2 = minCoins(arr, aim);
            int ans3 = dp1(arr, aim);
            int ans4 = minCoins2(arr, aim);
            int ans5 = dp2(arr, aim);
            int ans6 = dp3(arr, aim);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4 || ans1 != ans5 || ans1 != ans6) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                System.out.println(ans5);
                System.out.println(ans5);
                System.out.println(ans6);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        int aim = 0;
        int[] arr = null;
        long start;
        long end;
        int ans2;
        int ans3;
        int ans4;

        System.out.println("性能测试开始");
        maxLen = 20000000;
        aim = 60000;
        maxValue = 50;
        arr = randomArray(maxLen, maxValue);

        start = System.currentTimeMillis();
        ans2 = dp2(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        ans4 = dp3Right(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3Right答案 : " + ans4 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");

        System.out.println("===========");

        System.out.println("货币大量重复出现情况下，");
        maxLen = 20000000;
        aim = 60000;
        maxValue = 10000;
        arr = randomArray(maxLen, maxValue);
        System.out.println("大数据量测试dp2开始");
        start = System.currentTimeMillis();
        ans2 = dp2(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案: " + ans2+",dp2运行时间 : " + (end - start) + " ms");

        System.out.println("大数据量测试dp3开始");
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案: " +ans3+",dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");

        System.out.println("大数据量测试dp3Right开始");
        start = System.currentTimeMillis();
        ans4 = dp3Right(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3Right答案: " +ans4+",dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");

        System.out.println("===========");

        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }


}
