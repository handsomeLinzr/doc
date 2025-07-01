package coding.tixi.day39;

/**
 *
 * n 个苹果，两种袋子  6号 和 8号， 可以装 6个 和 8个
 * 输入 n 个苹果，输出 最少的袋子数量，如果没法装满则 返回-1
 *
 * @author linzherong
 * @date 2025/6/30 00:20
 */
public class AppleMinBags {

    /**
     * n个苹果，返回最少袋子数
     * 思路：尽量用8号袋子装，装不满的部分再一个一个挪给6号袋子，直到刚好能装满，或者8号袋子没了，结束
     * @param n
     * @return
     */
    public static int bag(int n) {
        int bag8 = n >>> 3;
        int rest = n % 8;
        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + (rest / 6);
            } else {
                bag8 --;
                rest += 8;
            }
        }
        return -1;
    }

    // 对数器推出的规律
    public static int bag2(int n) {
        if ((n & 1) == 1) {
            return -1;
        }
        if (n < 18) {
            return n == 0? 0 : n == 6 || n == 8? 1 : n == 12 || n == 14 || n == 16? 2 : -1;
        } else {
            return (n-2>>>3) + 1;
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 20000; i++) {
            if (bag(i) != bag2(i)) {
                System.out.println("Oops");
            }
        }
        System.out.println("成功");
    }

}
