package coding.tixi.day29;

/**
 * 最长回文子串
 *
 * @author linzherong
 * @date 2025/6/28 19:52
 */
public class Manacher {

    // for test
    public static int manacher(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] chars = manacherString(s);
        int R = -1; // 当前扩的最右位置
        int C = -1; // 当前扩最右位置对应的中心点
        int[] pre = new int[chars.length];
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            pre[i] = R > i? Math.min(pre[2 * C - i], R - i):1;
            int cL = i - pre[i];
            int cR = i + pre[i];
            while (cR < chars.length && cL  >= 0 && chars[cL] == chars[cR]) {
                cL --;
                cR ++;
            }
            pre[i] = cR - i;
            if (i + pre[i] > R) {
                R = i + pre[i];
                C = i;
            }
            max = Math.max(max, pre[i]);
        }
        return max - 1;  // 注意要减1
    }

    public static int right(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        char[] chars = manacherString(str);
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            int L = i-1;
            int R = i+1;
            while (L >= 0 && R < chars.length && chars[L] == chars[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max >> 1;
    }

    public static char[] manacherString(String str) {
        char[] charArray = str.toCharArray();
        char[] res = new char[ 2 * charArray.length + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            if ((i & 1) == 0) {
                res[i] = '#';
            } else {
                res[i] = charArray[index++];
            }
        }
        return res;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
