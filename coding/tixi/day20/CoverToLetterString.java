package coding.tixi.day20;

/**
 *
 * 1对应A，2对应B ... 11对应K...26对应Z，
 * 如：111 对应  AAA/AK/KA
 * 给一个str，获取对应有多少种组合
 *
 * @author linzherong
 * @date 2025/5/27 13:13
 */
public class CoverToLetterString {

    public static int number(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }
    // 将 arr 中，从 index 位置开始计算处理
    public static int process(char[] arr, int index) {
        if (arr.length == index) {
            return 1;
        }
        if (arr[index] == '0') {
            return 0;
        }
        // 获取index字符的情况
        int p1 = process(arr, index + 1);
        int p2 = 0;
        if (arr.length > index+1 && 10*(arr[index] - '0') + (arr[index+1] - '0') <= 26) {
            p2 = process(arr, index+2);
        }
        return p1 + p2;
    }

    public static int dp(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        char[] arr = str.toCharArray();
        int N = arr.length;
        int[] dp = new int[N+1];
        dp[N] = 1;
        for (int i = N-1; i >= 0; i--) {
            if (arr[i] == '0') {
                continue;
            }
            int p1 = dp[i+1];
            int p2 = 0;
            if (N > i+1 && 10*(arr[i] - '0') + (arr[i+1] - '0') <= 26) {
                p2 = dp[i+2];
            }
            dp[i] = p1 + p2;
        }
        return dp[0];
    }


    public static void main(String[] args) {
        System.out.println(number("3241212211213121212121"));
        System.out.println(dp("3241212211213121212121"));
    }

}
