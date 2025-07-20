package coding.tixi.day33;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author linzherong
 * @date 2025/7/20 20:08
 */
public class AC2 {

    public static class ACNode {
        int end; // 以这个节点为尾的数量
        ACNode fail;  // fail指针
        ACNode[] nexts;
        public ACNode() {
            end = 0;
            fail = null;
            nexts = new ACNode[26];  // 默认26条路径
        }

    }

    public static class ACAutomation {
        private ACNode root; // 头节点

        public ACAutomation() {
            this.root = new ACNode();
        }

        // 构建前缀树
        public void insert(String s) {
            // 拆分成字符
            char[] str = s.toCharArray();
            ACNode node = root;
            for (int i = 0; i < str.length; i++) {
                // 位置
                int index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    ACNode current = new ACNode();
                    node.nexts[index] = current;
                }
                // node指向下一个
                node = node.nexts[index];
            }
            // 以当前的node为结尾，node.end 要自增
            node.end++;
        }

        /**
         * 构建AC自动机
         */
        public void build() {
            // 队列，用来宽度优先遍历
            Queue<ACNode> queue = new LinkedList<>();
            queue.add(root);
            ACNode cFail;
            ACNode current;
            while (!queue.isEmpty()) {
                // 当前遍历到的父节点
                current = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (current.nexts[i] == null) {
                        continue;
                    }
                    // 先指向头节点
                    current.nexts[i].fail = root;
                    // 当前节点的fail节点
                    cFail = current.fail;
                    while (cFail != null) {
                        // 如果当前节点的fail节点不为null
                        if (cFail.nexts[i] != null) {
                            // 且fail节点有指向和next一样的路，则直接挂钩上
                            current.nexts[i].fail = cFail.nexts[i];
                            break;
                        }
                        cFail = cFail.fail;
                    }
                    queue.add(current.nexts[i]);
                }
            }
        }

        public int containNum(String content) {
            char[] str = content.toCharArray();
            ACNode cur = root;
            int ans = 0;
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                ACNode follow = cur;
                while (follow != root) {
                    if (follow.end == -1) {
                        break;
                    }
                    ans += follow.end;
                    follow.end = -1;  // 下次就不统计这个位置了
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
        ac.insert("c");
        ac.build();
        System.out.println(ac.containNum("cdhe"));
    }

}
