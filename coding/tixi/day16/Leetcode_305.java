package coding.tixi.day16;

import java.util.*;

/**
 * 两种方式   1.普通并查集  2.用hashMap+字符串方式（如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法）
 * @author linzherong
 * @date 2025/5/20 22:50
 */
public class Leetcode_305 {

    class UnionFound {
        int[] parent;
        int[] size;
        int[] help;
        int setSize = 0;
        int row;
        int col;
        public UnionFound(int m, int n) {
            this.row = m;
            this.col = n;
            int length = m * n;
            parent = new int[length];
            size = new int[length];
            help = new int[length];
        }

        public int index(int m, int n) {
            return m * col + n;
        }

        public int getParent(int index) {
            int h = 0;
            while (index != parent[index]) {
                help[h++] = index;
                index = parent[index];
            }
            for (int i = 0; i < h; i++) {
                parent[help[i]] = index;
            }
            return index;
        }

        public void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 >= row || c1 < 0 || c1 >= col || r2 < 0 || r2 >= row || c2 < 0 || c2 >= col) {
                return;
            }
            int index1 = index(r1, c1);
            int index2 = index(r2, c2);
            int s1 = size[index1];
            int s2 = size[index2];
            if (s1 == 0 || s2 == 0) {
                return;
            }
            int p1 = getParent(index1);
            int p2 = getParent(index2);
            if (p1 == p2) {
                return;
            }
            s1 = size[p1];
            s2 = size[p2];
            if (s1 > s2) {
                parent[p2] = p1;
                size[p1] += s2;
            } else {
                parent[p1] = p2;
                size[p2] += s1;
            }
            setSize--;
        }

        public boolean add(int r, int c) {
            int index = index(r, c);
            if (size[index] != 0) {
                return false;
            }
            parent[index] = index;
            size[index] = 1;
            setSize++;
            return true;
        }

    }

    public List<Integer> numIsLands21(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<>();
        UnionFound unionFound = new UnionFound(m, n);
        for (int i = 0; i < positions.length; i++) {
            int r = positions[i][0];
            int c = positions[i][1];
            if (!unionFound.add(r, c)) {
                ans.add(unionFound.setSize);
                continue;
            }
            unionFound.union(r, c, r, c-1);
            unionFound.union(r, c, r, c+1);
            unionFound.union(r, c, r-1, c);
            unionFound.union(r, c, r+1, c);
            ans.add(unionFound.setSize);
        }
        return ans;
    }

    class UnionFound2 {
        HashMap<String, String> parent;
        HashMap<String, Integer> size;
        int setSize;
        public UnionFound2(int[][] positions) {
            parent = new HashMap<>(positions.length);
            size = new HashMap<>(positions.length);
        }

        public void union(int r1, int c1, int r2, int c2) {
            String k1 = r1+"_"+c1;
            String k2 = r2+"_"+c2;
            if (!size.containsKey(k1) || !size.containsKey(k2)) {
                return;
            }
            String p1 = getParent(k1);
            String p2 = getParent(k2);
            if (p1 == null || p2 == null || p1.equals(p2)) {
                return;
            }
            Integer s1 = size.get(p1);
            Integer s2 = size.get(p2);
            if (s1 > s2) {
                parent.put(p2, p1);
                size.put(p1, s1+s2);
            } else {
                parent.put(p1, p2);
                size.put(p2, s1+s2);
            }
            setSize--;
        }

        public boolean add(int row, int col) {
            String key = row + "_" + col;
            if (parent.containsKey(key)) {
                return false;
            }
            parent.put(key, key);
            size.put(key, 1);
            setSize++;
            return true;
        }

        public String getParent(String key) {
            List<String> keys = new ArrayList<>(parent.size());
            while (!key.equals(parent.get(key))) {
                keys.add(key);
                key = parent.get(key);
            }
            for (String s : keys) {
                parent.put(s, key);
            }
            return key;
        }

    }

    // 如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
    public List<Integer> numIsLands22(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<>(positions.length);
        UnionFound2 unionFound = new UnionFound2(positions);
        for (int i = 0; i < positions.length; i++) {
            int row = positions[i][0];
            int col = positions[i][1];
            if (!unionFound.add(row, col)) {
                ans.add(unionFound.setSize);
                continue;
            }
            unionFound.union(row, col, row, col-1);
            unionFound.union(row, col, row, col+1);
            unionFound.union(row, col, row-1, col);
            unionFound.union(row, col, row+1, col);
            ans.add(unionFound.setSize);
        }
        return ans;
    }

    static class Postition {
        int m;
        int n;
        int[][] positions;
        public Postition(int m, int n, int[][] positions) {
            this.m = m;
            this.n = n;
            this.positions = positions;
        }
    }

    public static Postition generalPositions(int maxM, int maxN) {
        int m = (int) (Math.random() * maxM) + 1;
        int n = (int) (Math.random() * maxN) + 1;
        int time = (int) (Math.random() * maxM) + 1;
        int[][] positions = new int[time][2];
        for (int i = 0; i < time; i++) {
            int row = (int)(Math.random() * m);
            int col = (int)(Math.random() * n);
            positions[i] = new int[]{row,col};
        }
        return new Postition(m, n, positions);
    }

    public static void main(String[] args) {
        Leetcode_305 instance = new Leetcode_305();
        for (int i = 0; i < 100_0000; i++) {
            Postition postition = generalPositions(6, 6);
            List<Integer> ans1 = instance.numIsLands21(postition.m, postition.n, postition.positions);
            List<Integer> ans2 = instance.numIsLands22(postition.m, postition.n, postition.positions);
            if (!ans1.equals(ans2)) {
                System.out.println("出错");
            }
        }
        System.out.println("成功");
    }


}
