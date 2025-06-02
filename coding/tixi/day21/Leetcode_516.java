package coding.tixi.day21;

/**
 *
 * 最长回文子序列
 *
 * @author linzherong
 * @date 2025/5/30 22:36
 */
public class Leetcode_516 {


    public int longestPalindromeSubseq(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        return getRevertCount(charArray, 0, s.length()-1);
    }

    public int getRevertCount(char[] strArr, int left, int right) {
        if (left == right) {
            return 1;
        }
        if (left == right-1) {
            // 相邻时，查看是否一样，一样则2，不一样则1（因为起码中间某一个自己都是一个回文子序列）
            return strArr[left] == strArr[right]? 2 : 1;
        }
        int p1 = getRevertCount(strArr, left+1, right);
        int p2 = getRevertCount(strArr, left, right-1);
        int p3 = getRevertCount(strArr, left + 1, right - 1);
        p3 = strArr[left] == strArr[right]? 2+p3:p3;
        return Math.max(p1, Math.max(p2, p3));
    }

    public int longestPalindromeSubseq1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        int[][] dp = new int[charArray.length][charArray.length];
        return getRevertCount1(charArray, 0, s.length()-1,dp);
    }

    public int getRevertCount1(char[] strArr, int left, int right,int[][] dp) {
        if (dp[left][right] != 0) {
            return dp[left][right];
        }
        int result;
        if (left == right) {
            result = 1;
        } else if (left == right-1) {
            // 相邻时，查看是否一样，一样则2，不一样则1（因为起码中间某一个自己都是一个回文子序列）
            result = strArr[left] == strArr[right]? 2 : 1;
        } else {
            int p1 = getRevertCount1(strArr, left + 1, right, dp);
            int p2 = getRevertCount1(strArr, left, right - 1, dp);
            int p3 = getRevertCount1(strArr, left + 1, right - 1, dp);
            p3 = strArr[left] == strArr[right] ? 2 + p3 : p3;
            result = Math.max(p1, Math.max(p2, p3));
            dp[left][right] = result;
        }
        return result;
    }

    public int longestPalindromeSubseq2(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        int[][] dp = new int[charArray.length][charArray.length];
        dp[charArray.length-1][charArray.length-1] = 1;
        for (int i = 0; i < charArray.length-1; i++) {
            dp[i][i] = 1;
            dp[i][i+1] = charArray[i] == charArray[i+1]? 2 : 1;
        }
        for (int i = charArray.length-3; i >= 0; i--) {
            int j = i+2;
            while (j < charArray.length) {
                int p1 = dp[i+1][j];
                int p2 = dp[i][j-1];
                int p3 = dp[i+1][j-1];
                p3 = charArray[i] == charArray[j] ? 2 + p3 : p3;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
                j++;
            }
        }
        return dp[0][charArray.length-1];
    }

}
