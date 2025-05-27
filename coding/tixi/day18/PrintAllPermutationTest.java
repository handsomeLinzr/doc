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
public class PrintAllPermutationTest {

    public static List<String> allPermutation1(String word) {
        char[] charArray = word.toCharArray();
        List<String> ans = new ArrayList<>();
        f1(ans, charArray, "");
        return ans;
    }
    public static void f1(List<String> ans, char[] charArray, String path) {
        if (charArray.length == 0) {
            ans.add(path);
        } else {
            HashSet<Character> set = new HashSet<>();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (!set.add(c)) {
                    continue;
                }
                char[] remove = remove(charArray, i);
                f1(ans, remove, path+c);
            }
        }
    }
    public static char[] remove(char[] arr, int index) {
        char[] array = new char[arr.length-1];
        int m = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            array[m++] = arr[i];
        }
        return array;
    }


    public static List<String> allPermutation2(String word) {
        List<String> ans = new ArrayList<>();
        char[] charArray = word.toCharArray();
        f2(ans, 0, charArray, "");
        return ans;
    }
    public static void f2(List<String> ans, int index, char[] charArray, String path) {
        if (index == charArray.length) {
            ans.add(path);
        } else {
            HashSet<Character> set = new HashSet<>();
            for (int i = index; i < charArray.length; i++) {
                char c = charArray[i];
                if (!set.add(c)) {
                    continue;
                }
                // 当前位置和index交换
                swap(charArray, i, index);
                // 当前位置则是表示在下一个
                f2(ans, index+1, charArray, path+c);
                swap(charArray, i, index);
            }
        }
    }
    public static void swap(char[] arr, int i, int j) {
        char c = arr[i];
        arr[i] = arr[j];
        arr[j] = c;
    }

    public static void main(String[] args) {
        List<String> strings1 = allPermutation1("abcc");
        strings1.forEach(System.out::println);
        System.out.println("==================================");
        List<String> strings2 = allPermutation2("abcc");
        strings2.forEach(System.out::println);
    }

}
