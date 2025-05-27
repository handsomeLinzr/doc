package coding.tixi.day18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * 打印全排列
 * 1.
 *
 * @author linzherong
 * @date 2025/5/25 18:33
 */
public class PrintAllPermutation {

    public static List<String> allPermutation1(String word) {
        List<String> ans = new ArrayList<>();
        char[] charArray = word.toCharArray();
        ArrayList<Character> chList = new ArrayList<>(charArray.length);
        for (int i = 0; i < charArray.length; i++) {
            chList.add(charArray[i]);
        }
        f1("", chList, ans);
        return ans;
    }

    public static void f1(String path, ArrayList<Character> charList, List<String> ans) {
        if (charList.isEmpty()) {
            ans.add(path);
            return;
        }
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < charList.size(); i++) {
            Character c = charList.get(i);
            if (!set.add(c)) {
                continue;
            }
            // 将 i 位置的数据放到前边
            charList.remove(i);
            f1(path+c, charList, ans);
            charList.add(i, c);
        }
    }

    public static List<String> allPermutation2(String word) {
        char[] chars = word.toCharArray();
        ArrayList<String> ans = new ArrayList<>();
        f2("", 0, ans, chars);
        return ans;
    }

    public static void f2(String path, int index, ArrayList<String> ans, char[] chars) {
        if (index == chars.length) {
            ans.add(path);
            return;
        }
        for (int i = index; i < chars.length; i++) {
            // 交换
            swap(chars, index, i);
            // index前固定，继续调用后边的
            f2(path+chars[index], index+1, ans, chars);
            // 恢复
            swap(chars, index, i);
        }
    }
    public static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    public static void main(String[] args) {
//        List<String> strings = allPermutation1("cabc");
        List<String> strings = allPermutation1("abc");
        for (String string : strings) {
            System.out.print(string + "  ");
        }
        System.out.println();
        System.out.println("========================================");
        List<String> strings2 = allPermutation2("abc");
        for (String string : strings2) {
            System.out.print(string + "  ");
        }
    }

}
