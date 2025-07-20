package coding.tixi.day32;

/**
 *
 * 线段树
 *
 * @author linzherong
 * @date 2025/7/20 14:59
 */
public class Code01_SegmentTree {

    // 线段树
    // change 和 update 是一起用的
    public static class SegmentTree {
        private int MAXN;     // 长度
        private int[] arr;    // 原数组
        private int[] sum;    // 累加和
        private int[] lazy;   // 懒增加
        private int[] change; // 懒更新的值
        private boolean[] update;   // 是否被更新
        public SegmentTree(int[] origins) {
            this.MAXN = origins.length+1;
            // 辅助数组都乘以4
            arr = new int[MAXN];
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
            for (int i = 0; i < origins.length; i++) {
                arr[i+1] = origins[i];
            }
        }

        public void build(int l, int r, int head) {
            if (l == r) {
                sum[head] = arr[l];
                return;
            }
            int middle = (l + r) >> 1;
            build(l, middle, head << 1);
            build(middle+1, r, head << 1 | 1);
            pushUp(head);
        }

        // 从L 到 R 范围内每个数增加C，当前对应处理的范围是left 和 right
        public void add(int L, int R, int C, int left, int right, int cur) {
            if (L <= left && R >= right) {
                // 当前累加的范围能盖住整个 left 到 right，则直接懒加即可
                // 累加和
                sum[cur] += (right-left+1)*C;
                // 当前值
                lazy[cur] += C;
                return;
            }
            // 没能在 left 和 right 全部覆盖 L 到 R，则需要往下发
            int middle = (left + right) >> 1;
            pushDown(cur, middle-left+1, right-middle);
            if (L <= middle) {
                add(L, R, C, left, middle, cur << 1);
            }
            if (R > middle) {
                add(L, R, C, middle+1, right, cur << 1 | 1);
            }
            pushUp(cur);
        }

        // 从L到R，数据更新为C，需要注意的是，更新操作要处理掉懒加操作
        public void update(int L, int R, int C, int left, int right, int cur) {
            if (L <= left && R >= right) {
                update[cur] = true;
                change[cur] = C;
                sum[cur] = (right - left + 1) * C;
                lazy[cur] = 0;
                return;
            }
            int middle = (left + right) >> 1;
            // 任务下发(这里一定要任务下发，不能直接去掉，因为有可能本次的更新不会把下边所有都覆盖)
            pushDown(cur, middle-left+1, right-middle);
            if (L <= middle) {
                update(L, R, C, left, middle, cur << 1);
            }
            if (R > middle) {
                update(L, R, C, middle + 1, right, cur << 1 | 1);
            }
            pushUp(cur);
        }

        // 查询在 left 到 right范围内，L 到 R 的累加和
        public long query(int L, int R, int left, int right, int cur) {
            if (L <= left && R >= right) {
                return sum[cur];
            }
            int middle = (left + right) >> 1;
            pushDown(cur, middle - left +1, right - middle);
            long ans = 0;
            if (L <= middle) {
                ans += query(L, R, left, middle, cur << 1);
            }
            if (R > middle) {
                ans += query(L, R, middle+1, right, cur << 1 | 1);
            }
            return ans;
        }

        // 统计当前节点的累加和，为下边两个子节点的和
        private void pushUp(int head) {
            sum[head] = sum[head<<1] + sum[head<<1|1];
        }

        // ln:左边的数量   rn：右边的数量
        private void pushDown(int head, int ln, int rn) {
            int left = head << 1;
            int right = head << 1|1;
            if (update[head]) {
                // 有懒更新操作
                update[left] = true;
                update[right] = true;
                change[left] = change[head];
                change[right] = change[head];
                lazy[left] = 0;
                lazy[right] = 0;
                update[head] = false;
                sum[left] = change[left] * ln;
                sum[right] = change[right] * rn;
            }
            if (lazy[head]!=0) {
                lazy[left] += lazy[head];
                lazy[right] += lazy[head];
                sum[left] += lazy[head] * ln;
                sum[right] += lazy[head] * rn;
                lazy[head] = 0;
            }
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
