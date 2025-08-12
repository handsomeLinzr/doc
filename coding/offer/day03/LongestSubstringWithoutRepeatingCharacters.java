package coding.offer.day03;

/**
 *
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * 子串、数组方式：解题技巧，固定某个值为结尾，获取极限值，最终的答案一定在其中
 *
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 *
 * @author linzherong
 * @date 2025/7/17 00:38
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int ans = 1;
        int begin = 0;
        char[] charArray = s.toCharArray();
        for (int i = 1; i < charArray.length; i++) {
            int length = 1;
            int tmp = i;
            for (int j = i-1; j >= begin && charArray[j] != charArray[i]; j--) {
                tmp = j;
                length++;
            }
            begin = tmp;
            ans = Math.max(ans, length);
        }
        return ans;
    }






}
