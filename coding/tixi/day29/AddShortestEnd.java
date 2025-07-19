package coding.tixi.day29;

/**
 *
 * 在给定的字符串中，后面添加字符串，添加多少字符串才能让字符串成为回文串
 *
 * 思路，找到包含最后一个字符在内的最大回文子串
 * 然后把此时最前边没有包含在回文子串的全部内容，翻转放到最后边即可
 *
 * @author linzherong
 * @date 2025/6/28 19:52
 */
public class AddShortestEnd {

    public static String shortestEnd(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        int R = -1;  // 最右
        int C = -1;  // 中心
        char[] charArray = manacherString(s);
        int[] pre = new int[charArray.length];
        int max = 0;
        for (int i = 0; i < charArray.length; i++) {
            // 初始长度
            pre[i] = R > i ? Math.min(pre[2*C-i], R-i) : 1;
            int cL = i - pre[i];
            int cR = i + pre[i];
            while (cL >= 0 && cR < charArray.length && charArray[cL] == charArray[cR]) {
                cL--;
                cR++;
            }
            pre[i] = cR - i;
            if (cR > R) {
                R = cR;
                C = i;
            }
            if (R == charArray.length) {
                max = pre[i];
                break;
            }
        }
        char[] ans = new char[s.length() - max + 1];
        int length = s.length();
        for (int i = 0; i < ans.length; i++) {
            ans[i] = s.charAt(length - max - i);
        }
        return String.valueOf(ans);
    }

    public static char[] manacherString(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        int index = 0;
        char[] ans = new char[s.length() * 2 + 1];
        for (int i = 0; i < ans.length; i++) {
            if ((i & 1) == 0) {
                ans[i] = '#';
            } else {
                ans[i] = s.charAt(index++);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }

}
