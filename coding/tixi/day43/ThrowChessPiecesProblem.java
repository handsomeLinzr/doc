package coding.tixi.day43;

/**
 *
 * https://leetcode.com/problems/super-egg-drop
 *
 * @author linzherong
 * @date 2025/7/5 11:51
 */
public class ThrowChessPiecesProblem {

    public int superEggDrop(int k, int n) {
        if (k <= 0 || n <= 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        // 有n层楼，有k个鸡蛋，需要的最少次数
        int[][] dp = new int[n+1][k+1];
        int[][] base = new int[n+1][k+1];
        // 只有一个鸡蛋，必须从低层往高层依次试验
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
            base[i][1] = 1;
        }
        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
            base[1][j] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = k; j >= 2 ; j--) {
                int min = Integer.MAX_VALUE;
                int baseIndex = base[i-1][j];
                int rightIndex = j == k? i : base[i][j+1];
                for (int l = baseIndex; l <= rightIndex ; l++) {
                    int result = Math.max(dp[l-1][j-1], dp[i-l][j]);
                    if (min >= result) {
                        min = result;
                        baseIndex = l;
                    }
                }
                dp[i][j] = min+1;
                base[i][j] = baseIndex;
            }
        }
        return dp[n][k];
    }

    public int superEggDrop1(int k, int n) {
        if (k <= 0 || n <= 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        // k颗鸡蛋仍l次能解决的楼层数
        int[] dp = new int[k+1];
        int res = 0;
        while (true) {
            res ++;
            // 左上角
            int pre = 0;
            for (int i = 1; i <= k; i++) {
                // dp[i]要作为下次的左上角，先缓存
                int tmp = dp[i];
                dp[i] = dp[i] + pre + 1;
                if (dp[i] >= n) {
                    return res;
                }
                pre = tmp;
            }
        }
    }

    public static void main(String[] args) {
        ThrowChessPiecesProblem piecesProblem = new ThrowChessPiecesProblem();
        System.out.println(piecesProblem.superEggDrop(5, 200));
        System.out.println(piecesProblem.superEggDrop1(5, 200));
    }

}
