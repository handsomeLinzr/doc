package coding.tixi.day09;

import coding.tixi.StringUtils;

import java.util.Objects;

/**
 *
 * 两种方式实现 trieTree （int[] 和 hashMap），要求有方法 add/search(是否存在)/remove/countPre(前缀有多少个数)/countSearch(字符数量)
 *
 * @author linzherong
 * @date 2025/3/29 17:30
 */
public class TrieTree {

    private TrieNode root;

    public TrieTree() {
        this.root = new TrieNode();
    }

    public void add(String str) {
        if (str == null || str.equals("")) {
            return;
        }
        int[] charNums = calculateByStr(str);
        TrieNode node = root;
        node.pass++;
        for (int i = 0; i < charNums.length; i++) {
            if (Objects.isNull(node.next[charNums[i]])) {
                node.next[charNums[i]] = new TrieNode();
            }
            node = node.next[charNums[i]];
            node.pass++;
        }
        node.end++;
    }

    public boolean search(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        int[] charNums = calculateByStr(str);
        TrieNode node = root;
        for (int i = 0; i < charNums.length; i++) {
            node = node.next[charNums[i]];
            if (Objects.isNull(node)) {
                return false;
            }
        }
        return node.end > 0;
    }

    public void remove(String str) {
        if (!search(str)) {
            return;
        }
        int[] charNums = calculateByStr(str);
        TrieNode node = root;
        node.pass--;
        for (int i = 0; i < charNums.length; i++) {
            TrieNode cur = node.next[charNums[i]];
            if (Objects.isNull(cur)) {
                return;
            }
            cur.pass--;
            if (cur.pass == 0) {
                node.next[charNums[i]] = null;
                return;
            }
            node = cur;
        }
        node.end--;
    }

    public int countPre(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        int[] charNums = calculateByStr(str);
        TrieNode node = root;
        for (int i = 0; i < charNums.length; i++) {
            node = node.next[charNums[i]];
            if (Objects.isNull(node)) {
                return 0;
            }
        }
        return node.pass;
    }

    public int countSearch(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        int[] charNums = calculateByStr(str);
        TrieNode node = root;
        for (int i = 0; i < charNums.length; i++) {
            node = node.next[charNums[i]];
            if (Objects.isNull(node)) {
                return 0;
            }
        }
        return node.end;
    }

    class TrieNode {
        public int pass;
        public int end;
        public TrieNode[] next = new TrieNode[26];
    }

    public int[] calculateByStr(String str) {
        int[] result = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            result[i] = str.charAt(i) - 'a';
        }
        return result;
    }

}
