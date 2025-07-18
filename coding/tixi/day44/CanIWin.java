package coding.tixi.day44;

/**
 *
 * https://leetcode.cn/problems/can-i-win/description/
 *
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和 达到或超过  100 的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），若先出手的玩家能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 *
 * 1 <= maxChoosableInteger <= 20
 * 0 <= desiredTotal <= 300
 *
 *
 * @author linzherong
 * @date 2025/7/5 21:50
 */
public class CanIWin {

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 累加和
        if (maxChoosableInteger * (maxChoosableInteger+1) >> 1 < desiredTotal) {
            return false;
        }
        // 数组，-1为已经拿过，非-1为具体的数
        int[] arr = new int[maxChoosableInteger];
        for (int i = 0; i < maxChoosableInteger; i++) {
            arr[i] = i+1;
        }
        return process(arr, desiredTotal);
    }

    // 思考， arr的剩余情况，和 rest ，有一样的情况
    public boolean process(int[] arr, int rest) {
        if (rest <= 0) {
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                // arr[i] 未被处理过
                int cur = arr[i];
                arr[i] = -1;
                // 当前的后手
                boolean last = process(arr, rest - cur);
                // 数据恢复，不能影响下一次尝试
                arr[i] = cur;
                if (!last) {
                    // 后手返回false，后手输了
                    return true;
                }
            }
        }
        // 所有情况后手都是true赢了，则没办法先手输了
        return false;
    }


    public boolean canIWin1(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 累加和
        if (maxChoosableInteger * (maxChoosableInteger+1) >> 1 < desiredTotal) {
            return false;
        }
        return process1(maxChoosableInteger, 0, desiredTotal);
    }

    // status=>>0未处理，1已处理
    // 用 status 来做缓存
    public boolean process1(int choose, int status, int rest) {
        if (rest <= 0) {
            return false;
        }
        for (int i = 1; i <= choose; i++) {
            if ((status & (1 << i)) == 0) {
                // status 的 choose 位上为0，还没处理
                boolean next = process1(choose, (status | (1 << i)), rest - i);
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 累加和
        if (maxChoosableInteger * (maxChoosableInteger+1) >> 1 < desiredTotal) {
            return false;
        }
        int[] dp = new int[1<<maxChoosableInteger+1];
        return process2(maxChoosableInteger, 0, desiredTotal, dp);
    }
    // rest 能用 status 算出来，所以只需要一维数组
    // 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
    public boolean process2(int choose, int status, int rest, int[] dp) {
        if (rest <= 0) {
            return false;
        }
        if (dp[status] != 0) {
            return dp[status] == 1;
        }
        boolean result = false;
        for (int i = 1; i <= choose; i++) {
            if (((1<<i) & status) == 0) {
                boolean next = process2(choose, ((1 << i) | status), rest - i, dp);
                if (!next) {
                    result = true;
                    break;
                }
            }
        }
        dp[status] = result?1:-1;
        return result;
    }

    public static void main(String[] args) {
        CanIWin canIWin = new CanIWin();
        System.out.println(canIWin.canIWin(10, 40));
        System.out.println(canIWin.canIWin2(10, 40));
    }


}
