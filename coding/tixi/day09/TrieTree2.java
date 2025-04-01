package coding.tixi.day09;

import coding.tixi.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/4/2 00:09
 */
public class TrieTree2 {

    public TrieNode2 root;

    public TrieTree2() {
        this.root = new TrieNode2();
    }

    public void add(String str) {
        if (StringUtils.isBlack(str)) {
            return;
        }
        TrieNode2 node = root;
        TrieNode2 currentNode;
        node.pass++;
        for (int i = 0; i < str.length(); i++) {
            currentNode = node.next.getOrDefault(str.charAt(i), new TrieNode2());
            currentNode.pass++;
            node.next.put(str.charAt(i), currentNode);
            node = currentNode;
        }
        node.end++;
    }

    public boolean search(String str) {
        if (StringUtils.isBlack(str)) {
            return false;
        }
        TrieNode2 node = root;
        for (int i = 0; i < str.length(); i++) {
            node = node.next.get(str.charAt(i));
            if (Objects.isNull(node)) {
                return false;
            }
        }
        return node.end > 0;
    }

    public void remove(String str) {
        if (StringUtils.isBlack(str)) {
            return;
        }
        TrieNode2 node = root;
        node.pass--;
        for (int i = 0; i < str.length(); i++) {
            TrieNode2 cur = node.next.get(str.charAt(i));
            if (Objects.isNull(cur)) {
                return;
            }
            cur.pass--;
            if (cur.pass == 0) {
                node.next.remove(str.charAt(i));
            }
            node = cur;
        }
        node.end--;
    }

    public int countPre(String str) {
        if (StringUtils.isBlack(str)) {
            return 0;
        }
        TrieNode2 node = root;
        for (int i = 0; i < str.length(); i++) {
            TrieNode2 cur = node.next.get(str.charAt(i));
            if (Objects.isNull(cur)) {
                return 0;
            }
            node = cur;
        }
        return node.pass;
    }

    public int countSearch(String str) {
        if (StringUtils.isBlack(str)) {
            return 0;
        }
        TrieNode2 node = root;
        for (int i = 0; i < str.length(); i++) {
            TrieNode2 cur = node.next.get(str.charAt(i));
            if (Objects.isNull(cur)) {
                return 0;
            }
            node = cur;
        }
        return node.end;
    }

    class TrieNode2 {
        public int pass;
        public int end;
        public Map<Character, TrieNode2> next = new HashMap<>(26);
    }

}
