package coding.tixi.day33;

/**
 *
 * index tree
 *
 * @author linzherong
 * @date 2025/6/28 18:00
 */
public class IndexTree1D {

    public static class IndexTree {
        int N;
        int[] help;
        public IndexTree(int n) {
            N = n;  // 这里就不用加1了，这里当做最后一个位置就好了
            this.help = new int[N+1];
        }

        // 增加
        public void add(int index, int value) {
            while (index <= N) {
                help[index] += value;
                // 从 index 开始，每次都增加一个 最右1 的位置的数
                // 因为当前 index 也是被这个位置管着
                // 一个位置管的范围是：把这个位置拆分成二进制，然后最右的1放到第0位，从这个位置开始到自己
                index += (index & (-index));
            }
        }

        // 求从1到index的累加和
        public int sum(int index) {
            int ans = 0;
            while (index != 0) {
                ans += help[index];
                // 去掉index最右边的1，也等于直接减去这个数
                index -= index & (-index);
            }
            return ans;
        }
    }


    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }

}
