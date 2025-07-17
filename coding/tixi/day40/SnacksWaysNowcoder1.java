package coding.tixi.day40;

import java.io.*;
import java.util.Arrays;

/**
 *
 * 一共有 n 袋零食，第 i 袋零食的体积是 v[i]，背包容量是 w
 * 要求在总体积不超过 w 的情况下，一共有多少种零食的放法，体积 0 也是一种
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9
 * 0 <= v[i] <= 10^9
 * w 和 v[i] 都大，不能简单dp和暴力递归
 * n小，可以进行分治
 *
 * @author linzherong
 * @date 2025/7/17 13:14
 */
public class SnacksWaysNowcoder1 {

    public static long[] arr = new long[31];
    public static int size = 0;
    // 左累加的各种情况
    public static long[] lArray = new long[1<<16];
    // 右累加的各种情况
    public static long[] rArray = new long[1<<16];
    // 左累加的数量
    public static int leftSize = 0;
    // 右数量的数量
    public static int rightSize = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            size = (int) in.nval;
            in.nextToken();
            int bag = (int) in.nval;
            for (int i = 0; i < size; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            long ways = ways(bag);
            out.println(ways);
            out.flush();
        }
    }

    public static long ways(long w) {
        if (arr == null || arr.length == 0 || w < 0) {
            return 0;
        }
        int middle = (size-1) >> 1;
        // 手机左边的所有累加情况
        dfsLeft(0, middle, 0, w);
        // 收集右边的所有累加情况
        dfsRight(middle+1, size-1, 0, w);
        // 从小到大排序
        Arrays.sort(lArray, 0, leftSize);
        Arrays.sort(rArray, 0, rightSize);
        long ways = leftSize + rightSize;
        int count = 1;
        // 从 1 位置开始
        // 这里 i 可以等于 leftSize，因为lArray数量肯定大于leftSize（起码还有1个累加和为0的没算），所以肯定不会越界
        for (int i = 1; i <= leftSize; i++) {
            if (lArray[i] == lArray[i-1]) {
                count ++;
            } else {
                long rest = w - lArray[i-1];
                int rCount = findCount(rest);
                ways += (long) count * rCount;
                count = 1;
            }
        }
        return ways + 1;
    }

    // 收集累加和
    public static void dfsLeft(int start, int end, long sum, long w) {
        if (start > end) {
            if (sum <= w && sum != 0) {
                lArray[leftSize++] = sum;
            }
            return;
        }
        dfsLeft(start+1, end, sum, w);
        dfsLeft(start+1, end, sum+arr[start], w);
    }
    public static void dfsRight(int start, int end, long sum, long w) {
        if (start > end) {
            if (sum <= w && sum != 0) {
                rArray[rightSize++] = sum;
            }
            return;
        }
        dfsRight(start+1, end, sum, w);
        dfsRight(start+1, end, sum+arr[start], w);
    }

    // 获取rArray中小于等于rest的个数
    public static int findCount(long rest) {
        int size = rightSize;
        int L = 0;
        int R = size-1;
        int M = 0;
        int ans = -1;
        while (L <= R) {
            M = (R+L) / 2;
            long middle = rArray[M];
            if (middle <= rest) {
                ans = M;
                L = M+1;
            } else {
                R = M-1;
            }
        }
        return ans + 1;
    }



}
