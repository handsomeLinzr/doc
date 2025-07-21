package coding.offer.day01;

import java.util.Arrays;

/**
 * @author linzherong
 * @date 2025/7/19 03:02
 */
public class AOE {

    /*
     * 给定两个数组x和hp，长度都是N。
     * x数组一定是有序的，x[i]表示i号怪兽在x轴上的位置
     * hp数组不要求有序，hp[i]表示i号怪兽的血量
     * 为了方便起见，可以认为x数组和hp数组中没有负数。
     * 再给定一个正数range，表示如果法师释放技能的范围长度(直径！)
     * 被打到的每只怪兽损失1点血量。
     * 返回要把所有怪兽血量清空，至少需要释放多少次aoe技能？
     * 三个参数：int[] x, int[] hp, int range
     * 返回：int 次数
     * */
    public static int right(int[] x, int[] hp, int range) {
        if (x == null || hp == null || x.length == 0 || hp.length == 0) {
            return 0;
        }
        int length = x.length;
        int ans = 0;
        int i = 0;
        int curRight;
        while (i < length) {
            while (i < length && hp[i] <= 0) {
                i++;
            }
            if (i == length) {
                return ans;
            }
            // 当前范围
            curRight = x[i] + range;
            // 次数
            int minus = hp[i];
            // 答案
            ans += minus;
            int j = i;
            while (j < length && x[j] <= curRight) {
                hp[j] -= minus;
                j++;
            }
            i++;
        }
        return ans;
    }

    public static int minAoe(int[] x, int[] hp, int range) {
        int n = x.length;
        int[] cover = cover(x, range);
        SegmentTree segmentTree = new SegmentTree(hp);
        segmentTree.build(1, n, 1);
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            int h = segmentTree.query(i, i, 1, n, 1);
            if (h > 0) {
                ans += h;
                segmentTree.add(i, cover[i-1]+1, -h, 1, n, 1);
            }
        }
        return ans;
    }

    // 返回范围内的最远受影响距离
    public static int[] cover(int[] x, int range) {
        int n = x.length;
        int[] cover = new int[n];
        int r = 1;
        for (int i = 0; i < n; i++) {
            while (r < n && x[r] - x[i] <= range) {
                r++;
            }
            cover[i] = r-1;
        }
        return cover;
    }

    // 线段树
    public static class SegmentTree {
        private int max;
        private int[] arr;
        private int[] sum;
        private int[] lazy;

        public SegmentTree(int[] origins) {
            this.max = origins.length + 1;
            arr = new int[max];
            for (int i = 1; i < max; i++) {
                arr[i] = origins[i-1];
            }
            sum = new int[max << 2];
            lazy = new int[max << 2];
        }

        public void build(int left, int right, int n) {
            if (left == right) {
                sum[n] = arr[left];
                return;
            }
            int middle = (left + right) >> 1;
            build(left, middle, n << 1);
            build(middle+1, right, n << 1 | 1);
            pushUp(n);
        }

        public void add(int L, int R, int C, int left, int right, int n) {
            if (L <= left && R >= right) {
                lazy[n] += C;
                sum[n] += (right - left + 1) * C;
                return;
            }
            int middle = (right + left) >> 1;
            pushDown(n, middle - left + 1, right - middle);
            if (L <= middle) {
                add(L, R, C, left, middle, n<<1);
            }
            if (R > middle) {
                add(L, R, C, middle+1, right, n<<1|1);
            }
            pushUp(n);
        }

        public int query(int L, int R, int left, int right, int n) {
            if (L <= left && R >= right) {
                return sum[n];
            }
            int middle = (left + right) >> 1;
            pushDown(n, middle - left + 1, right - middle);
            int ans = 0;
            if (L <= middle) {
                ans += query(L, R, left, middle, n << 1);
            }
            if (R > middle) {
                ans += query(L, R, middle+1, right, n << 1 | 1);
            }
            return ans;
        }

        public void pushUp(int n) {
            sum[n] = sum[n << 1] + sum[n << 1 | 1];
        }

        private void pushDown(int n, int ln, int rn) {
            if (lazy[n] != 0) {
                lazy[n<<1] += lazy[n];
                lazy[n<<1|1] += lazy[n];
                sum[n<<1] += ln * lazy[n];
                sum[n<<1 | 1] += rn * lazy[n];
                lazy[n] = 0;
                sum[n] = 0;
            }
        }
    }


    // 为了测试
    public static int[] randomArray(int n, int valueMax) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * valueMax) + 1;
        }
        return ans;
    }

    // 正式方法
    // 关键点就是：
    // 1) 线段树
    // 2) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
    // 3) 然后向右找下一个没死的怪物，重复步骤2)
    public static int minAoe3(int[] x, int[] hp, int range) {
        int n = x.length;
        int[] cover = new int[n];
        int r = 0;
        // cover[i] : 如果i位置是技能的最左侧，技能往右的range范围内，最右影响到哪
        for (int i = 0; i < n; i++) {
            while (r < n && x[r] - x[i] <= range) {
                r++;
            }
            cover[i] = r - 1;
        }
        SegmentTree st = new SegmentTree(hp);
        st.build(1, n, 1);
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            int leftHP = st.query(i, i, 1, n, 1);
            if (leftHP > 0) {
                ans += leftHP;
                st.add(i, cover[i - 1] + 1, -leftHP, 1, n, 1);
            }
        }
        return ans;
    }

    public static class SegmentTree1 {
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;

        public SegmentTree1(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int N = arr.length;
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 50;
        int X = 500;
        int H = 60;
        int R = 10;
        int testTime = 50000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] x2 = randomArray(len, X);
            Arrays.sort(x2);
            int[] hp2 = randomArray(len, H);
            int[] x3 = copyArray(x2);
            int[] hp3 = copyArray(hp2);
            int range = (int) (Math.random() * R) + 1;
            int ans2 = minAoe(x2, hp2, range);
            int ans3 = right(x3, hp3, range);
            if (ans2 != ans3) {
                System.out.println("出错了！");
                System.out.println(Arrays.toString(x2));
                System.out.println(Arrays.toString(hp2));
            }
        }
        System.out.println("测试结束");

        N = 500000;
        long start;
        long end;
        int[] x2 = randomArray(N, N);
        Arrays.sort(x2);
        int[] hp2 = new int[N];
        for (int i = 0; i < N; i++) {
            hp2[i] = i * 5 + 10;
        }
        int[] x3 = copyArray(x2);
        int[] hp3 = copyArray(hp2);
        int range = 10000;

        start = System.currentTimeMillis();
        System.out.println(minAoe3(x2, hp2, range));
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");

        start = System.currentTimeMillis();
        System.out.println(minAoe(x2, hp2, range));
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");

        start = System.currentTimeMillis();
        System.out.println(right(x3, hp3, range));
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
    }

}
