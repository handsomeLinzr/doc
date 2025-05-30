package coding.tixi.day20;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linzherong
 * @date 2025/5/28 13:02
 */
public class Leetcode_691 {

    public int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return -1;
        }
        Map<String, Integer> dp = new HashMap<>();
        int result = process2(stickers, target, dp);
        return result == Integer.MAX_VALUE? -1 : result;
    }

    public int process1(String[] stickers, String rest) {
        if (rest.isEmpty()) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            String minus = minus(rest, stickers[i]);
            if (minus.length() == rest.length()) {
                // 无效
                continue;
            }
            int restInt = process1(stickers, minus);
            min = Math.min(min, restInt == Integer.MAX_VALUE?Integer.MAX_VALUE:restInt+1);
        }
        return min;
    }

    // 字符串减法
    public String minus(String s1, String s2) {
        char[] s1Char = s1.toCharArray();
        char[] s2Char = s2.toCharArray();
        int[] counts = new int[26];
        for (char c : s1Char) {
            counts[c - 'a']++;
        }
        for (char c : s2Char) {
            counts[c - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                for (int j = 0; j < counts[i]; j++) {
                    sb.append((char) (i+'a'));
                }
            }
        }
        return sb.toString();
    }

    public int process2(String[] stickers, String rest, Map<String, Integer> dp) {
        if (rest.isEmpty()) {
            return 0;
        }
        if (dp.containsKey(rest)) {
            return dp.get(rest);
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            String minus = minus(rest, stickers[i]);
            if (minus.length() == rest.length()) {
                // 无效
                continue;
            }
            int restInt = process2(stickers, minus, dp);
            min = Math.min(min, restInt == Integer.MAX_VALUE?Integer.MAX_VALUE:restInt+1);
        }
        dp.put(rest, min);
        return min;
    }

    public int minStickers3(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return -1;
        }
        // 转为int二维数组
        int[][] stickerChar = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            char[] charArray = stickers[i].toCharArray();
            for (char c : charArray) {
                stickerChar[i][c-'a'] ++;
            }
        }
        Map<String, Integer> dp = new HashMap<>();
        int result = process3(stickerChar, target, dp);
        return result == Integer.MAX_VALUE? -1 : result;
    }
    public int process3(int[][] stickers, String rest, Map<String, Integer> dp) {
        if (rest.isEmpty()) {
            return 0;
        }
        if (dp.containsKey(rest)) {
            return dp.get(rest);
        }
        // 剩余字符串转字符
        char[] restChar = rest.toCharArray();
        int[] restInt = new int[26];
        for (char c : restChar) {
            restInt[c-'a'] ++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            // 判断是否含有rest的第一个字符，有则继续往下走，没有则直接跳过，因为无论怎么都还需要其他
            if (sticker[restChar[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < restInt.length; j++) {
                    if (restInt[j] > 0 && restInt[j] > sticker[j]) {
                        int count = restInt[j] - sticker[j];
                        for (int k = 0; k < count; k++) {
                            sb.append((char) (j+'a'));
                        }
                    }
                }
                int nextP = process3(stickers, sb.toString(), dp);
                if (nextP != Integer.MAX_VALUE) {
                    min = Math.min(min, nextP+1);
                }
            }
        }
        dp.put(rest, min);
        return min;
    }

    public static void main(String[] args) {
        Leetcode_691 in = new Leetcode_691();
        System.out.println(in.minStickers(new String[]{"with","example","science"}, "thehat"));
        System.out.println(in.minStickers3(new String[]{"with","example","science"}, "thehat"));
    }

}
