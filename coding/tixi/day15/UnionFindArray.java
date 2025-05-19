package coding.tixi.day15;

/**
 *
 * 数组方式实现的并查集
 *
 * @author linzherong
 * @date 2025/5/12 22:22
 */
public class UnionFindArray {

    /**
     * 并查集
     */
    public static class UnionFindArr {
        public int[] parent;
        public int[] size;
        public int[] help;
        public int setSize;

        public UnionFindArr(int n) {
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            setSize = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public void union(int x, int y) {
            int xp = findParent(x);
            int yp = findParent(y);
            if (xp != yp) {
                int xs = size[x];
                int ys = size[y];
                if (xs > ys) {
                    parent[yp] = xp;
                    size[yp] += size[xp];
                } else {
                    parent[xp] = yp;
                    size[xp] += size[yp];
                }
                setSize--;
            }
        }

        public boolean isSameSet(int x, int y) {
            return findParent(x) == findParent(y);
        }

        public int setSize() {
            return setSize;
        }

        // 获取头节点
        private int findParent(int x) {
            int hi = 0;
            while (x != parent[x]) {
                help[hi++] = x;
                x = parent[x];
            }
            while (hi>0) {
                parent[help[--hi]] = x;
            }
            return x;
        }
    }

}
