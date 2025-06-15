package coding.tixi.day25;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * // 测试链接：https://leetcode.com/problems/gas-station
 *
 * @author linzherong
 * @date 2025/6/9 00:24
 */
public class GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 将 gas 和 cost 数组进行相减处理得到净数组
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < good.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        int[] help = new int[N*2];
        // 净耗存
        for (int i = 0; i < N; i++) {
            help[i] = help[N+i] = g[i] - c[i];
        }
        // 净耗存累加
        for (int i = 1; i < help.length; i++) {
            help[i] = help[i-1] + help[i];
        }
        // 窗口大小为N，进行填充处理
        LinkedList<Integer> min = new LinkedList<>();
        boolean[] ans = new boolean[N];
        int L = 0;
        int R = 0;
        int comp = 0;
        while (R < help.length-1) {
            while (!min.isEmpty() && help[min.peekLast()] >= help[R]) {
                min.pollLast();
            }
            min.addLast(R);
            if (R >= N-1) {
                ans[L] = help[min.peekFirst()] >= comp;
                comp = help[L];
                if (L == min.peekFirst()) {
                    min.pollFirst();
                }
                L ++;
            }
            R++;
        }

        return ans;
    }

}
