package coding.tixi.day33;

import coding.tixi.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author linzherong
 * @date 2025/7/20 20:08
 */
public class AC1 {

    public static class Node {
        public String end;  // 字符串结尾
        public boolean endUse;  // 是否已被手机
        public Node[] nexts;  // next节点
        public Node fail;   // 快速失败节点
        public Node() {
            end = null;
            endUse = false;
            nexts = new Node[26];
            fail = null;
        }
    }

    public static class ACAutomation {
        public Node root;
        public ACAutomation() {
            this.root = new Node();
        }

        // 字符串插入
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node current = root;
            for (int i = 0; i < str.length; i++) {
                // next数组下标
                int index = str[i] - 'a';
                if (current.nexts[index] == null) {
                    current.nexts[index] = new Node();
                }
                current = current.nexts[index];
            }
            // 字符串结尾
            current.end = s;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node current;
            Node cFail;
            while (!queue.isEmpty()) {
                Node poll = queue.poll();
                cFail = poll.fail;
                for (int i = 0; i < poll.nexts.length; i++) {
                    if (poll.nexts[i] == null) {
                        continue;
                    }
                    // 第 i 号子节点
                    current = poll.nexts[i];
                    // 先将子节点的fail指向root
                    current.fail = root;
                    while (cFail != null) {
                        if (cFail.nexts[i] != null) {
                            current.fail = cFail.nexts[i];
                            break;
                        }
                        cFail = cFail.fail;
                    }
                    queue.add(current);
                }
            }
        }

        // 对content的内容进行AC自动机扫描，并将命中的字符串搜集
        public List<String> containWords(String content) {
            List<String> ans = new ArrayList<>();
            char[] str = content.toCharArray();
            Node cur = root;
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null? cur.nexts[index]:root;
                Node follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    if (!StringUtils.isBlack(follow.end)) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 从 follow 节点沿着 fail 节点看一遍
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }

}
