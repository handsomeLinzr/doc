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
public class PrintAllSubsquencesTest {

    public static List<String> sub(String word) {
        char[] charArray = word.toCharArray();
        List<String> ans = new ArrayList<>();
        f(ans, charArray, 0, "");
        return ans;
    }
    public static void f(List<String> ans, char[] charArray, int index, String path) {
        if (index == charArray.length) {
            ans.add(path);
        } else {
            HashSet<Character> set = new HashSet<>();
            f(ans, charArray, index+1, path);
            f(ans, charArray, index+1, path+charArray[index]);
        }
    }

    public static void main(String[] args) {
        List<String> abcc = sub("abc");
        abcc.forEach(System.out::println);
    }

}
