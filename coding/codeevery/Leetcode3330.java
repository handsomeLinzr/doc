package coding.codeevery;

/**
 * @author linzherong
 * @date 2025/7/1 09:39
 */
public class Leetcode3330 {

    public int possibleStringCount(String word) {
        int count = 1;
        char pre = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == pre) {
                count++;
            }
            pre = word.charAt(i);
        }
        return count;
    }

    public static void main(String[] args) {
        Leetcode3330 leetcode3330 = new Leetcode3330();
        System.out.println(leetcode3330.possibleStringCount("aaaa"));
    }

}
