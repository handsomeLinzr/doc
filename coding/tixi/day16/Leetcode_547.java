package coding.tixi.day16;

/**
 *
 *
 * @author linzherong
 * @date 2025/5/11 21:09
 */
public class Leetcode_547 {

    public int findCircleNum(int[][] isConnected) {
        Union union = new Union(isConnected);
        for (int i = 0; i < isConnected.length; i++) {
            for (int j = i+1; j < isConnected.length; j++) {
                if (isConnected[i][j] == 1) {
                    union.union(i, j);
                }
            }
        }
        return union.pSize;
    }
    
    static class Union {
        public int[] parent;
        public int[] size;
        public int[] help;
        int pSize;
        public Union(int[][] isConnected) {
            int length = isConnected.length;
            parent = new int[length];
            size = new int[length];
            help = new int[length];
            pSize = length;
            for (int i = 0; i < length; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        // 获取父节点
        public int getParent(int i) {
            int index = 0;
            while (parent[i] != i) {
                help[index++] = i;
                i = parent[i];
            }
            while (index > 0) {
                parent[help[--index]] = i;
            }
            return i;
        }
        // 合并
        public void union(int i, int j) {
            int pi = getParent(i);
            int pj = getParent(j);
            if (pi == pj) {
                return;
            }
            int ai = size[pi];
            int aj = size[pj];
            if (ai > aj) {
                parent[pj] = pi;
                size[pi] += size[pj];
            } else {
                parent[pi] = pj;
                size[pj] += size[pi];
            }
            pSize--;
        }
    }

}
