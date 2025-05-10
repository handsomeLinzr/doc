package coding.tixi.day14;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 给定一个由字符串组成的数组，必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果
 *
 * @author linzherong
 * @date 2025/5/5 22:07
 */
public class LowestLexicography {

    // 贪心
    public static String lowestString(String[] strs) {
        Arrays.sort(strs, comparator);
        String str = "";
        for (String s : strs) {
            str+=s;
        }
        return str;
    }
    public static Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return (o1+o2).compareTo(o2+o1);
        }
    };

    public static String lowestString1(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        List<List<String>> lists = process1(strs);
        TreeSet<String> treeSet = new TreeSet<>();
        for (List<String> list : lists) {
            treeSet.add(String.join("", list));
        }
        return treeSet.first();
    }
    public static List<List<String>> process1(String[] strs) {
        List<List<String>> ans = new ArrayList<>();
        if (strs.length == 1) {
            ans.add(Collections.singletonList(strs[0]));
            return ans;
        }
        String[] arr = getArrayNotCur(strs, 0);
        List<List<String>> lists = process1(arr);
        for (List<String> list : lists) {
            for (int i = 0; i <= list.size(); i++) {
                ans.add(addIndex(list, i, strs[0]));
            }
        }
        return ans;
    }
    public static String[] getArrayNotCur(String[] arr, int index) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i != index) {
                list.add(arr[i]);
            }
        }
        return list.toArray(new String[0]);
    }
    public static List<String> addIndex(List<String> list, int index, String value) {
        List<String> ans = new ArrayList<>(list.size() + 1);
        int s = 0;
        for (int i = 0; i < list.size() + 1; i++) {
            if (i == index) {
                s = 1;
                ans.add(value);
            } else {
                ans.add(list.get(i-s));
            }
        }
        return ans;
    }

    public static String[] generalArray(int maxSize, int maxLength) {
        int length = (int) (Math.random() * maxSize + 1);
        String[] arr = new String[length];
        for (int i = 0; i < length; i++) {
            int size = (int) (Math.random() * maxLength + 1);
            String value = "";
            for (int j = 0; j < size; j++) {
                value += (char) (97 + (Math.random() * 26));
            }
            arr[i] = value;
        }
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            String[] arr = generalArray(5, 5);
            if (!lowestString(arr).equals(lowestString1(arr))) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
