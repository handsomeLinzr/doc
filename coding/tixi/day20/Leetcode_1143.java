package coding.tixi.day20;

/**
 * 最长公共子序列
 * @author linzherong
 * @date 2025/5/29 21:29
 */
public class Leetcode_1143 {

    public int longestCommonSubsequence1(String text1, String text2) {
        if (text1.isEmpty() || text2.isEmpty()) {
            return 0;
        }
        int[][] dp = new int[text1.toCharArray().length][text2.toCharArray().length];
        return process1(text1.toCharArray(), text2.toCharArray(), 0, 0, dp);
    }

    public int process1(char[] tc1, char[] tc2, int index1, int index2, int[][] dp) {
        if (index1 == tc1.length || index2 == tc2.length) {
            return 0;
        }
        if (dp[index1][index2] != 0) {
            return dp[index1][index2];
        }
        int result;
        if (tc1.length-1 == index1) {
            if (tc1[index1] == tc2[index2]) {
                result = 1;
            } else {
                result = process1(tc1, tc2, index1, index2+1, dp);
            }
        } else if (tc2.length - 1 == index2) {
            if (tc1[index1] == tc2[index2]) {
                result = 1;
            } else {
                result = process1(tc1, tc2, index1+1, index2, dp);
            }
        } else {
            int p1 = process1(tc1, tc2, index1+1, index2,dp);
            int p2 = process1(tc1, tc2, index1, index2+1, dp);
            int p3 = tc1[index1] ==tc2[index2]? (1+process1(tc1,tc2, index1+1, index2+1, dp)) : 0;
            result = Math.max(p1, Math.max(p2, p3));
            dp[index1][index2] = result;
        }
        return result;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.isEmpty() || text2.isEmpty()) {
            return 0;
        }
        char[] char1 = text1.toCharArray();
        char[] char2 = text2.toCharArray();
        int N1 = char1.length;
        int N2 = char2.length;
        int[][] dp = new int[N1+1][N2+1];
        for (int i = N1-1; i >= 0; i--) {
            for (int j = N2-1; j >= 0; j--) {
                if (char1[i] == char2[j]) {
                    // 有 char1[i] 和 char2[j] 这两个公共字节，则两个字符串继续往前移，结果为移动后的结果+1
                    dp[i][j] = dp[i+1][j+1] + 1;
                } else {
                    // char1[i] 和 char2[j] 不相等，则为单个往前，两种情况获取最大值
                    int p1 = dp[i+1][j];
                    int p2 = dp[i][j+1];
                    dp[i][j] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        Leetcode_1143 in = new Leetcode_1143();
        System.out.println(in.longestCommonSubsequence("abcde", "ace"));
        System.out.println(in.longestCommonSubsequence1("abcde", "ace"));
    }

}
