package coding.tixi.day09;

import coding.tixi.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 1.两种方式实现 trieTree （int[] 和 hashMap），要求有方法 add/search(是否存在)/remove/countPre(前缀有多少个数)/countSearch(字符数量)
 * 2.桶排序
 *    计数排序(定义数据范围内个桶)  countSort
 *    基数排序两种方式(定义10个桶 / 不定义桶) radixSort
 * 排序的稳定性总结
 *  冒泡 有
 *  选择 无（原数组交换两个的动作）
 *  插入 有
 *  堆   无（调整堆的时候，有原数组两个交换动作）
 *  归并 有
 *  快速 无（获取到最大最小值时候，原数组两个进行交换动作）
 *
 * @author linzherong
 * @date 2025/4/1 00:30
 */
public class TrieTreeTest {

    public static void main(String[] args) {
        TrieTreeTest test = new TrieTreeTest();
        test.testTrieTree1();
    }

    public void testTrieTree1() {
        for (int time = 0; time < 200_0000; time++) {
            TrieTree2 tree = new TrieTree2();
            String str1 = StringUtils.generalStr(3,5);
            String str2 = StringUtils.generalStr(6,7);
            String str3 = StringUtils.generalStr(8,9);
            String str4 = StringUtils.generalStr(10,11);
            String str5 = StringUtils.generalStr(12,13);
            tree.add(str1);
            tree.add(str1);
            tree.add(str2);
            tree.add(str2);
            tree.add(str3);
            tree.add(str3);
            tree.add(str4);
            tree.add(str4);
            tree.add(str5);
            tree.add(str5);
            if (!tree.search(str1) || !tree.search(str2) || !tree.search(str3) || !tree.search(str4) || !tree.search(str5)) {
                System.out.println("出错了");
            }
            if (tree.countSearch(str1) != 2
                    || tree.countSearch(str1) != 2
                    || tree.countSearch(str1) != 2
                    || tree.countSearch(str1) != 2
                    || tree.countSearch(str1) != 2) {
                System.out.println("出错了");
            }
            String prefix = str1.substring(0, 2);
            int count = tree.countPre(prefix);
            int calCount = 2;
            if (str2.startsWith(prefix)) {
                calCount+=2;
            }
            if (str3.startsWith(prefix)) {
                calCount+=2;
            }
            if (str4.startsWith(prefix)) {
                calCount+=2;
            }
            if (str5.startsWith(prefix)) {
                calCount+=2;
            }
            if (count != calCount) {
                System.out.println("出错了");
            }
            tree.remove(str1);
            tree.remove(str2);
            tree.remove(str3);
            tree.remove(str4);
            tree.remove(str5);
            if (tree.countSearch(str1) != 1
                    || tree.countSearch(str1) != 1
                    || tree.countSearch(str1) != 1
                    || tree.countSearch(str1) != 1
                    || tree.countSearch(str1) != 1) {
                System.out.println("出错了");
            }
        }
        System.out.println("成功");
    }

}
