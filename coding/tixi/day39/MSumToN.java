package coding.tixi.day39;

/**
 *
 * 给定一个数字，判断能否拆分成多个连续的正整数相加，如 103=51+52
 *
 * @author linzherong
 * @date 2025/6/30 14:14
 */
public class MSumToN {

    public static boolean isMSum1(int num) {
        for (int i = 1; i <= num/2 ; i++) {
            int start = i;
            int sum = 0;
            while (sum <= num) {
                if (sum == num) {
                    return true;
                }
                sum += start;
                start++;
            }
        }
        return false;
    }

    // 对数器观察规律，只要对应的数，二进制下 1 的个数大于1，则为true
    public static boolean isMSum2(int num) {
        return (num & num-1) != 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            if (isMSum1(i) != isMSum2(i)) {
                System.out.println("Oops");
            }
        }
        System.out.println("结束");
    }


}
