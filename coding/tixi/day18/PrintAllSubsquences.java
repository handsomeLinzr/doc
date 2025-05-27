package coding.tixi.day18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * 打印一个字符串所有子序列
 * 打印一个字符串所有子序列，不重复
 *
 * @author linzherong
 * @date 2025/5/25 18:29
 */
public class PrintAllSubsquences {

    public static List<String> sub1(String word) {
        List<String> ans = new ArrayList<>();
        char[] charArray = word.toCharArray();
        f1(ans, 0, "", charArray);
        return ans;
    }

    // ans 答案， path 前边过程的结果,    rest 剩余的字符
    public static void f1(List<String> ans, int index, String path, char[] array) {
        if (array.length == index) {
            ans.add(path);
            return;
        }
        // 当前在处理的字符
        char i = array[index];
        // 加上的情况
        f1(ans, index + 1, path + i, array);
        // 跳过的情况
        f1(ans, index + 1, path, array);
    }
    public static char[] remove(char[] arr, int i) {
        char[] ans = new char[arr.length-1];
        int m = 0;
        for (int j = 0; j < arr.length; j++) {
            if (j == i) {
                continue;
            }
            ans[m++] = arr[j];
        }
        return ans;
    }

    public static char[] add(char[] arr, char i) {
        char[] ans = new char[arr.length+1];
        ans[0] = i;
        int m = 1;
        for (int j = 0; j < arr.length; j++) {
            ans[m++] = arr[j];
        }
        return ans;
    }

    public static List<String> sub2(String word) {
        List<String> ans = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        f2(set, 0, "", word.toCharArray());
        ans.addAll(set);
        return ans;
    }

    public static void f2(HashSet<String> ans, int index, String path, char[] array) {
        if (array.length == index) {
            ans.add(path);
            return;
        }
        char i = array[index];
        f2(ans, index + 1, path + i, array);
        f2(ans, index + 1, path, array);
    }

    public static void main(String[] args) {
        List<String> strings = sub1("abcc");
        System.out.println(strings);

        List<String> strings2 = sub2("abcc");
        System.out.println(strings2);
    }


}
