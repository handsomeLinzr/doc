package coding.tixi.day19;

/**
 * 两个人玩游戏，从 int[] arr 纸牌中，每次只能拿第一张或者最后一张，要求返回最后最大的值
 * @author linzherong
 * @date 2025/5/27 01:50
 */
public class CardsInLine {

    // 暴力递归
    // 每次只能从头尾拿
    public static int win1(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        return Math.max(f1(arr, 0, arr.length-1), g1(arr, 0, arr.length-1));
    }
    // 先手
    public static int f1(int[] arr, int start, int end) {
        if (start == end) {
            return arr[start];
        }
        // 拿start的情况
        return Math.max(arr[start] + g1(arr, start+1, end), arr[end] + g1(arr, start, end-1));
    }
    // 后手
    public static int g1(int[] arr, int start, int end) {
        if (start == end) {
            return 0;
        }
        return Math.min(f1(arr, start, end-1), f1(arr, start+1, end));
    }

    // 暴力递归+缓存
    public static int win2(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int[][] dp1 = new int[arr.length][arr.length];
        int[][] dp2 = new int[arr.length][arr.length];
        int f = f2(arr, 0, arr.length-1, dp1, dp2);
        int g = g2(arr, 0, arr.length-1, dp1, dp2);
        return Math.max(f, g);
    }
    public static int f2(int[] arr, int start, int end, int[][] dp1, int[][] dp2) {
        if (dp1[start][end] != 0) {
            return dp1[start][end];
        }
        int result;
        if (start == end) {
            result = arr[start];
        } else {
            int p1 = arr[start] + g2(arr, start+1, end, dp1, dp2);
            int p2 = arr[end] + g2(arr, start, end-1, dp1, dp2);
            result = Math.max(p1, p2);
        }
        dp1[start][end] = result;
        return result;
    }
    public static int g2(int[] arr, int start, int end, int[][] dp1, int[][] dp2) {
        if (dp2[start][end] != 0) {
            return dp2[start][end];
        }
        int result;
        if (start == end) {
            result = 0;
        } else {
            int p1 = f2(arr, start+1, end, dp1, dp2);
            int p2 = f2(arr, start, end-1, dp1, dp2);
            result = Math.min(p1, p2);
        }
        dp2[start][end] = result;
        return result;
    }

    // 动态规划
    public static int win3(int[] arr) {
        int length = arr.length;
        int[][] dp1 = new int[length][length];
        int[][] dp2 = new int[length][length];
        for (int i = 0; i < length; i++) {
            dp1[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < length; startCol++) {
//            for (int j = 0; j+i < length; j++) {
//                dp1[j][j+i] = Math.max(arr[j] + dp2[j+1][j+i], arr[j+i] + dp2[j][j+i-1]);
//                dp2[j][j+i] = Math.min(dp1[j+1][j+i], dp1[j][j+i-1]);
//            }
            int L = 0;
            int R = startCol;
            while (R < length) {
                dp1[L][R] = Math.max(arr[L] + dp2[L+1][R], arr[R] + dp2[L][R-1]);
                dp2[L][R] = Math.min(dp1[L+1][R], dp1[L][R-1]);
                L++;
                R++;
            }

        }
        return Math.max(dp1[0][arr.length-1], dp2[0][arr.length-1]);
    }

    public static void main(String[] args) {
        int[] arr = { 1,53,5,2,1,56,4,32,66,4,2,4 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }


}
