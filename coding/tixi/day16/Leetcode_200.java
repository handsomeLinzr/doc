package coding.tixi.day16;

/**
 * 岛屿数量
 * 两种方式  1.递归   2.并查集
 *
 * @author linzherong
 * @date 2025/5/11 22:58
 */
public class Leetcode_200 {

    static class UnionFind {
        int[] parent;
        int[] size;
        int[] help;
        int setSize;
        int n;
        public UnionFind(char[][] grid) {
            int n = grid.length * grid[0].length;
            parent = new int[n];
            size = new int[n];
            help = new int[n];
            this.n = grid.length;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        int index = getIndex(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        setSize++;
                    }
                }
            }
        }

        // 获取定级节点
        public int getParent(int i) {
            int index = 0;
            while (i != parent[i]) {
                help[index++] = i;
                i = parent[i];
            }
            for (int j = 0; j < index; j++) {
                parent[help[j]] = i;
            }
            return i;
        }

        // 是否同个并查集
        public boolean isSame(int a, int b) {
            return getParent(a) == getParent(b);
        }

        // 归并
        public void union(int a, int b) {
            int pa = getParent(a);
            int pb = getParent(b);
            if (pa == pb) {
                return;
            }
            int aSize = size[a];
            int bSize = size[b];
            if (aSize > bSize) {
                parent[pb] = pa;
                size[pa] += size[pb];
            } else {
                parent[pa] = pb;
                size[pb] += size[pa];
            }
            setSize--;
        }
        
        public int size() {
            return setSize;
        }
        
        public int getIndex(int x, int y) {
            return x * n + y;
        }
    }

    public static int numIslands1(char[][] grid) {
        UnionFind unionFind = new UnionFind(grid);
        for (int i = 1; i < grid[0].length ; i++) {
            if (grid[0][i] == '1' && grid[0][i-1] == '1') {
                unionFind.union(unionFind.getIndex(0, i), unionFind.getIndex(0, i-1));
            }
        }
        for (int i = 1; i < grid.length ; i++) {
            if (grid[i][0] == '1' && grid[i-1][0] == '1') {
                unionFind.union(unionFind.getIndex(i, 0), unionFind.getIndex(i-1, 0));
            }
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i-1][j] == '1') {
                        unionFind.union(unionFind.getIndex(i,j), unionFind.getIndex(i-1, j));
                    }
                    if (grid[i][j-1] == '1') {
                        unionFind.union(unionFind.getIndex(i,j), unionFind.getIndex(i, j-1));
                    }
                }
            }
        }
        return unionFind.setSize;
    }

    public int numIslands(char[][] grid) {
        int col = grid.length;
        int row = grid[0].length;
        int size = 0;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (grid[i][j] == '1') {
                    size++;
                    infect(grid, i, j, col-1, row-1);
                }
            }
        }
        return size;
    }

    public void infect(char[][] grid, int i, int j, int maxI, int maxJ) {
        if (i < 0 || i > maxI || j < 0 || j > maxJ) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        infect(grid, i-1, j, maxI, maxJ);
        infect(grid, i+1, j, maxI, maxJ);
        infect(grid, i, j-1, maxI, maxJ);
        infect(grid, i, j+1, maxI, maxJ);
    }

}
