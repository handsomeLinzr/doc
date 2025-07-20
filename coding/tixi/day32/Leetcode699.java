package coding.tixi.day32;

import java.util.*;

/**
 * @author linzherong
 * @date 2025/6/26 00:35
 */
public class Leetcode699 {

    public static class SegmentTree1 {
        private int[] max;
        private int[] change;
        private boolean[] update;
        public SegmentTree1(int size) {
            size++;
            max = new int[size << 2];
            change = new int[size << 2];
            update = new boolean[size << 2];
        }
        public void update(int L, int R, int C, int left, int right, int node) {
            if (L <= left && R >= right) {
                update[node] = true;
                change[node] = C;
                max[node] = C;
                return;
            }
            int middle = (left + right) >> 1;
            pushDown(node);
            if (L <= middle) {
                update(L, R, C, left, middle, node << 1);
            }
            if (R > middle) {
                update(L, R, C, middle+1, right, node << 1 | 1);
            }
            pushUp(node);
        }

        private void pushUp(int cur){
            max[cur] = Math.max(max[cur<<1], max[cur<<1|1]);
        }

        private void pushDown(int node) {
            if (update[node]) {
                int left = node << 1;
                int right = node << 1 | 1;
                update[left] = true;
                change[left] = change[node];
                update[right] = true;
                change[right] = change[node];
                update[node] = false;
                max[left] = change[node];
                max[right] = change[right];
            }
        }

        public int query(int L, int R, int left, int right, int node) {
            if (L <= left && R >= right) {
                return max[node];
            }
            int middle = (left + right) >> 1;
            pushDown(node);
            int ans = 0;
            if (L <= middle) {
                ans = Math.max(ans, query(L, R, left, middle, node << 1));
            }
            if (R > middle) {
                ans = Math.max(ans, query(L, R, middle+1, right, node << 1 | 1));
            }
            return ans;
        }

    }

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> index = getLast(positions);
        List<Integer> ans = new ArrayList<>(positions.length);
        int max = 0;
        SegmentTree1 tree1 = new SegmentTree1(index.size());
        for (int i = 0; i < positions.length; i++) {
            int L = index.get(positions[i][0]);
            int R = index.get(positions[i][1] + positions[i][0]-1);
            int query = tree1.query(L, R, 1, index.size(), 1);
            int next = query + positions[i][1];
            max = Math.max(max, next);
            ans.add(max);
            tree1.update(L, R, next, 1, index.size(), 1);
        }
        return ans;
    }

    public HashMap<Integer, Integer> getLast(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] position : positions) {
            pos.add(position[0]);  // 位置
            pos.add(position[0] + position[1] - 1);   // 延伸后的长度
        }
        // key=>>位置，value=>>数量
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer po : pos) {
            map.put(po, ++count);
        }
        return map;
    }

    public static void main(String[] args) {
        Leetcode699 leetcode699 = new Leetcode699();
        System.out.println(leetcode699.fallingSquares(new int[][]{{9,10},{4,1},{2,1},{7,4},{6,10}}));

    }


}
