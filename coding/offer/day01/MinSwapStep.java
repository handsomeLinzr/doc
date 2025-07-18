package coding.offer.day01;

/**
 * @author linzherong
 * @date 2025/7/18 21:27
 */
public class MinSwapStep {

    // 一个数组中只有两种字符'G'和'B'，
    // 可以让所有的G都放在左侧，所有的B都放在右侧
    // 或者可以让所有的G都放在右侧，所有的B都放在左侧
    // 但是只能在相邻字符之间进行交换操作，请问请问至少需要交换几次，
    public static int minSteps(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        // 从左到右，把 G 都放到左边
        int step1 = 0;
        int gi = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'G') {
                step1 += (i - (gi++));
            }
        }
        int bi = 0;
        int step2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'B') {
                step2 += (i - (bi++));
            }
        }
        return Math.min(step1, step2);
    }

    public static int minSteps1(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        int gi = 0;
        int bi = 0;
        int step1 = 0;
        int step2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'G') {
                step1 += (i - (gi++));
            } else {
                step2 += (i - (bi++));
            }
        }
        return Math.min(step1, step2);
    }

    // 为了测试
    public static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minSteps(str);
            int ans2 = minSteps1(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
