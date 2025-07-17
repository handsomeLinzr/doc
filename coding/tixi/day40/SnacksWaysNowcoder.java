package coding.tixi.day40;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

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
public class SnacksWaysNowcoder {
    
    public static int size = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            size = (int) in.nval;
            in.nextToken();
            int bag = (int) in.nval;
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            long ways = ways(arr, bag);
            out.println(ways);
            out.flush();
        }
    }

    public static long ways(int[] arr, int w) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int middle = (arr.length-1) >> 1;
        TreeMap<Long, Long> leftMap = new TreeMap<>();
        long way = p(leftMap, arr, 0, middle, 0, w);
        TreeMap<Long, Long> rightMap = new TreeMap<>();
        way +=p(rightMap, arr, middle+1, arr.length-1, 0, w);
        TreeMap<Long, Long> preMap = new TreeMap<>();
        Long pre = 0L;
        for (Map.Entry<Long, Long> entry : leftMap.entrySet()) {
            // 前缀和，表示小于 K 的有 VALUE 个
            pre += entry.getValue();
            preMap.put(entry.getKey(), pre);
        }
        for (Map.Entry<Long, Long> entry : rightMap.entrySet()) {
            Long rest = w - entry.getKey();
            Long value = entry.getValue();
            Long key = preMap.floorKey(rest);
            if (key != null) {
                way += (value * preMap.get(key));
            }
        }
        return way + 1;
    }

    public static long p(TreeMap<Long, Long> map, int[] arr, int left, int right, long total, long w) {
        if (total > w) {
            return 0;
        }
        if (left > right) {
            if (total != 0) {
                // w == 0 先不统计，因为如果统计了，可能会有多个 w==0的情况，而实际上w==0只会有一种，就是什么都不选的时候
                if (map.containsKey(total)) {
                    map.put(total, map.get(total) + 1);
                } else {
                    map.put(total, 1L);
                }
                return 1;
            } else {
                return 0;
            }
        }
        return p(map, arr, left+1, right, total+arr[left], w) + p(map, arr, left+1, right, total, w);
    }
}
