package coding.tixi.day28;

/**
 * @author linzherong
 * @date 2025/6/28 19:49
 */
public class KMP {

    public static int getIndexOf(String s1, String s2) {
        int start1 = 0;
        int start2 = 0;
        int[] nextArray = getNextArray(s2.toCharArray());
        while (start1 < s1.length() && start2 < s2.length()) {
            if (s1.charAt(start1) == s2.charAt(start2)) {
                start1++;
                start2++;
            } else {
                if (nextArray[start2] >= 0) {
                    start2 = nextArray[start2];
                } else {
                    start1++;
                }
            }
        }
        return start2 == s2.length() ? start1 - s2.length() : -1;
    }

    // 获取next数组
    public static int[] getNextArray(char[] chars) {
        if (chars.length == 1) {
            return new int[]{-1};
        }
        int[] ans = new int[chars.length];
        // 固定的，0为-1，1为0
        ans[0] = -1;
        ans[1] = 0;
        int index = 0;
        int i = 2;
        while (i < chars.length) {
            if (chars[i-1] == chars[index]) {
                // 上一个位置，和index位置一样的情况下，则i-1和index一样，i指向index的下一个
                ans[i++] = ++index;
            } else if (index > 0) {
                // i-1 和 index不匹配，则 继续让 i-1 和 index 的上一个位置匹配
                index = ans[index];
            } else {
                // 不匹配，上一个位置为 0 或 -1
                ans[i++] = 0;
            }
        }
        return ans;
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
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


}
