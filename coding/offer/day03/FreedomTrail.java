package coding.offer.day03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * // 本题测试链接 : https://leetcode.com/problems/freedom-trail/
 *
 * @author linzherong
 * @date 2025/7/18 00:49
 */
public class FreedomTrail {

    public int findRotateSteps(String ring, String key) {
        char[] ringCharArray = ring.toCharArray();
        HashMap<Character, ArrayList<Integer>> ringMap = new HashMap<>();
        // 构建字符环
        int[][] dp = new int[ring.length()+1][key.length()+1];
        for (int i = 0; i <= ring.length(); i++) {
            for (int j = 0; j <= key.length(); j++) {
                dp[i][j] = -1;
            }
        }
        buildRing(ringCharArray, ringMap);
        char[] keyArray = key.toCharArray();
        return process(keyArray, 0, 0, ringMap, ring.length(), dp);
    }

    public int process(char[] keyArray, int from, int start, HashMap<Character, ArrayList<Integer>> map, int ringLength, int[][] dp) {
        if (dp[from][start] != -1) {
            return dp[from][start];
        }
        int ans = Integer.MAX_VALUE;
        if (keyArray.length == start) {
            ans = 0;
        } else {
            ArrayList<Integer> list = map.get(keyArray[start]);
            for (int i = 0; i < list.size(); i++) {
                Integer index = list.get(i);
                int baseWay = getBaseWay(from, index, ringLength);
                ans = Math.min(ans, baseWay + process(keyArray, index, start+1, map, ringLength, dp));
            }
        }
        dp[from][start] = ans;
        return ans;
    }

    // 根据字符环构建字符集
    private void buildRing(char[] ring, HashMap<Character, ArrayList<Integer>> map) {
        for (int i = 0; i < ring.length; i++) {
            if (!map.containsKey(ring[i])) {
                map.put(ring[i], new ArrayList<>());
            }
            map.get(ring[i]).add(i);
        }
    }

    // 获取每次的最短路径，圆环
    private int getBaseWay(int from, int target, int length) {
        if (from == target) {
            return 1;
        }
        int a1 = Math.abs(from - target);
        int a2 = length - a1;
        return Math.min(a1, a2) + 1;
    }

    public int findRotateSteps1(String ring, String key) {
        char[] ringCharArray = ring.toCharArray();
        HashMap<Character, TreeSet<Integer>> ringMap = new HashMap<>();
        // 构建字符环
        int[][] dp = new int[ring.length()+1][key.length()+1];
        for (int i = 0; i <= ring.length(); i++) {
            for (int j = 0; j <= key.length(); j++) {
                dp[i][j] = -1;
            }
        }
        buildRing1(ringCharArray, ringMap);
        char[] keyArray = key.toCharArray();
        return process1(keyArray, 0, 0, ringMap, ring.length(), dp);
    }

    // 根据字符环构建字符集
    private void buildRing1(char[] ring, HashMap<Character, TreeSet<Integer>> ringMap) {
        for (int i = 0; i < ring.length; i++) {
            if (!ringMap.containsKey(ring[i])) {
                ringMap.put(ring[i], new TreeSet<>());
            }
            ringMap.get(ring[i]).add(i);
        }
    }

    private int process1(char[] keyArray, int from, int start, HashMap<Character, TreeSet<Integer>> ringMap, int ringSize, int[][] dp) {
        if (dp[from][start] != -1) {
            return dp[from][start];
        }
        int ans = Integer.MAX_VALUE;
        if (start == keyArray.length) {
            ans = 0;
        } else {
            TreeSet<Integer> set = ringMap.get(keyArray[start]);
            Integer floor = set.floor(from);
            Integer ceiling = set.ceiling(from);
            if (floor == null) {
                // from前边没有，则直接获取第一个
                floor = set.last();
            }
            if (ceiling == null) {
                // from后边没有，则直接获取最后一个
                ceiling = set.first();
            }
            if (floor != null) {
                ans = Math.min(ans, getBaseWay(from, floor, ringSize) + process1(keyArray, floor, start+1, ringMap, ringSize, dp));
            }
            if (ceiling != null && !ceiling.equals(floor)) {
                ans = Math.min(ans, getBaseWay(from, ceiling, ringSize) + process1(keyArray, ceiling, start+1, ringMap, ringSize, dp));
            }
        }
        dp[from][start] = ans;
        return ans;
    }

    public static void main(String[] args) {
        FreedomTrail trail = new FreedomTrail();
        System.out.println(trail.findRotateSteps1("zvyii", "iivyz"));
    }

}
