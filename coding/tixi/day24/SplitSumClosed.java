package coding.tixi.day24;

/**
 *
 * 给定一个正数数组arr，把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 *
 * @author linzherong
 * @date 2025/6/2 22:50
 */
public class SplitSumClosed {

    public static int split1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum+=i;
        }
        return p1(arr, 0, sum / 2);
    }

    // index 遍历到的位置   rest 剩余的大小
    public static int p1(int[] arr, int index, int rest) {
        if (rest == 0 || index == arr.length) {
            return 0;
        }
        int r1 = p1(arr, index+1, rest);
        int r2 = 0;
        if (rest >= arr[index]) {
            r2 = p1(arr, index+1, rest - arr[index]) + arr[index];
        }
        return Math.max(r1, r2);
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum+=i;
        }
        int total = sum / 2;
        int N = arr.length;
        int[][] dp = new int[N+1][total+1];

        // 从下到上
        for (int index = N-1; index >= 0; index--) {
            // 从左到右
            for (int rest = 1; rest <= total ; rest++) {
                int r1 = dp[index+1][rest];
                int r2 = 0;
                if (rest >= arr[index]) {
                    r2 = dp[index+1][rest - arr[index]] + arr[index];
                }
                dp[index][rest] = Math.max(r1, r2);
            }
        }
        return dp[0][total];
    }

    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return process(arr, 0, sum / 2);
    }

    // arr[i...]可以自由选择，请返回累加和尽量接近rest，但不能超过rest的情况下，最接近的累加和是多少？
    public static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return 0;
        } else { // 还有数，arr[i]这个数
            // 可能性1，不使用arr[i]
            int p1 = process(arr, i + 1, rest);
            // 可能性2，要使用arr[i]
            int p2 = 0;
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);
        }
    }
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans = right(arr);
            int ans1 = split1(arr);
            int ans2 = dp(arr);
            if (ans != ans1 || ans != ans2 ) {
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
