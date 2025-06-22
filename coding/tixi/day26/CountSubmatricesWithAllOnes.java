package coding.tixi.day26;

/**
 *
 * https://leetcode.cn/problems/count-submatrices-with-all-ones/description/
 * 统一全1子矩形
 * 用数组替换栈
 * @author linzherong
 * @date 2025/6/15 21:32
 */
public class CountSubmatricesWithAllOnes {

    public int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int N = mat.length;
        int M = mat[0].length;
        int[] height = new int[M];
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 高度处理
                height[j] = mat[i][j] == 1? height[j]+1 : 0;
            }
            result += calculate(height);
        }
        return result;
    }

    public int calculate(int[] height) {
        int result = 0;
        int[] stack = new int[height.length];
        int index = 0;
        for (int i = 0; i < height.length; i++) {
            while (index > 0 && height[stack[index-1]] >= height[i]) {
                int cur = stack[--index];
                // 只处理大于的情况，等于的情况直接跳过，后边的等于会处理
                if (height[cur] > height[i]) {
                    int left = index == 0? -1 : stack[index-1];
                    int leftHeight = index == 0? 0 : height[stack[index-1]];
                    int low = Math.max(height[i], leftHeight);
                    //            高度                    等差
                    result += (height[cur] - low) * subSum(cur - left);
                }
            }
            stack[index++] = i;
        }
        // 默认当前 i = arr.length - 1
        while (index > 0) {
            int cur = stack[--index];
            int left = index == 0? -1 : stack[index-1];
            // 左边高度
            int leftHeight = index == 0? 0 : height[stack[index-1]];
            result += (height[cur] - leftHeight) * subSum(height.length-1-left);
        }
        return result;
    }

    /**
     * 等差数列求和 ((a1 + an) * n ) / 2，从 1 开始
     * @param n
     * @return
     */
    public int subSum(int n) {
        return ((1 + n) * n) >> 1;
    }


    //  回文子序列——>动态规划
    public int longestPalindromeSubseq(String s) {
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
        for (int left = charArray.length-3; left >= 0; left--) {
            for (int right = left+2; right < charArray.length; right++) {
                if (charArray[left] == charArray[right]) {
                    dp[left][right] = 2 + dp[left+1][right-1];
                } else {
                    dp[left][right] = Math.max(dp[left+1][right], dp[left][right-1]);
                }
            }
        }
        return dp[0][charArray.length-1];
    }


    public int longestPalindromeSubseq1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        int[][] dp = new int[charArray.length][charArray.length];
        int process = process(charArray, 0, charArray.length - 1, dp);
        return process;
    }

    public int process(char[] charArray, int left, int right, int [][] dp) {
        if (dp[left][right] != 0) {
            return dp[left][right];
        }
        int result;
        if (left == right) {
            result = 1;
        } else if (left == right-1) {
            result = charArray[left] == charArray[right]? 2 : 1;
        } else if (charArray[left] == charArray[right]) {
            result =  2 + process(charArray, left+1, right-1, dp);
        } else {
            int p1 = process(charArray, left+1, right, dp);
            int p2 = process(charArray, left, right-1, dp);
            result = Math.max(p1, p2);
        }
        dp[left][right] = result;
        return result;
    }


    // 暴力遍历方式，时间复杂度 O(N^6)
    public int baoli(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int N = mat.length;
        int M = mat[0].length;
        int result = 0;
        // a点
        for (int i1 = 0; i1 < mat.length; i1++) {
            for (int j1 = 0; j1 < mat[0].length; j1++) {
                // b点
                for (int i2 = i1; i2 < N; i2++) {
                    a:for (int j2 = j1; j2 < M; j2++) {
                        for (int i = i1; i <= i2; i++) {
                            for (int j = j1; j <= j2; j++) {
                                if (mat[i][j] == 0) {
                                    continue a;
                                }
                            }
                        }
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        CountSubmatricesWithAllOnes ones = new CountSubmatricesWithAllOnes();
//        System.out.println(ones.longestPalindromeSubseq1("bbbab"));
//        System.out.println(ones.longestPalindromeSubseq("bbbab"));


        CountSubmatricesWithAllOnes ones = new CountSubmatricesWithAllOnes();
        int[][] a = {{0,1}, {1,1}, {1,0}};
        int i1 = ones.numSubmat(a);
        System.out.println(i1);

    }

}
