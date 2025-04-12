package coding.tixi.day10;

import coding.tixi.ListNode;
import coding.tixi.NodeUtils;

import java.util.*;

/**
 * 1.获取链表中点（快慢指针）
 *     奇数返回中点，偶数返回上中电
 *     奇数返回中电，偶数返回下中点
 *     奇数返回中点前一个，偶数返回上中点前一个
 *     奇数返回中点前一个，偶数返回下中点前一个
 * 2.给定一个链表头，返回这个链表是否是回文结构（回文结构：正念和反念都一样，对称，如果 1->2->3->2->1）
 *      获取中点(奇数)或上中点(偶数)，然后将这个点后的点都进行逆向指，接着两个指针分别指向首尾节点，边比较边向中间靠拢，
 *      当指向了同个节点或者其中一个节点为空则结束，过程中两个指针指向的值都一样则是，最后调整还原返回
 * 3.给定链表 L1 L2 L3 L4 R1 R2 R3 R4，要求转成 L1 R4 L2 R3 L3 R2 L4 R1（解法）
 * 4.给定一个链表和一个value，要求将链表调整，要求调整为小于vlue->等于value->大于value
 *      如   6->3->7->6->7->4, 6    ==>>   3->4->6->7->7
 *      （小头 小尾 等头 等尾 大头 大尾  6个变量，最后连接要注意是否为空）
 * 5.克隆链表，链表是一个变种(ListNodeDay10)，有next 和 rand，next指向下一个，rand 指向链表中的随机一个，要求克隆这个链表
 *       1. hashMap解法
 *       2. 不用hashMap，在原链表的两个节点直接都插入一个 克隆节点，如 a->b->c   ==>> a->a1->b->b1->c->c1
 *
 * @author linzherong
 * @date 2025/3/29 22:30
 */
public class Linked {

    public static void main(String[] args) {
        Linked linked = new Linked();
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, null))))));
        ListNode upMiddleNode = linked.getUpMiddleNode(node);
        ListNode downMiddleNode = linked.getDownMiddleNode(node);
        ListNode upUpMiddleNode = linked.getUpUpMiddleNode(node);
        ListNode downDownMiddleNode = linked.getDownDownMiddleNode(node);

        ListNode node1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(2, new ListNode(1, null))))));
        boolean rever = linked.isRever(node1);
        System.out.println(rever);

        ListNode node2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(11, new ListNode(12, new ListNode(13, new ListNode(14, new ListNode(15, null)))))))));
        linked.reverInsert2(node2);
        System.out.println(node2);

        ListNode node3 = new ListNode(5, new ListNode(3, new ListNode(5, new ListNode(7, new ListNode(1, new ListNode(2, new ListNode(6, new ListNode(6, new ListNode(3, null)))))))));
        node3 = linked.partitionListNode(node3, 5);
        System.out.println(node3);

        ListNodeDay10 nodeDay1 = new ListNodeDay10(1);
        ListNodeDay10 nodeDay2 = new ListNodeDay10(2);
        ListNodeDay10 nodeDay3 = new ListNodeDay10(3);
        ListNodeDay10 nodeDay4 = new ListNodeDay10(4);
        ListNodeDay10 nodeDay5 = new ListNodeDay10(5);
        nodeDay1.next = nodeDay2;
        nodeDay2.next = nodeDay3;
        nodeDay3.next = nodeDay4;
        nodeDay4.next = nodeDay5;
        nodeDay1.rand = nodeDay4;
        nodeDay2.rand = nodeDay5;
        nodeDay3.rand = nodeDay1;
        nodeDay4.rand = nodeDay2;
        nodeDay5.rand = nodeDay3;
        ListNodeDay10 clone = linked.clone2(nodeDay1);
        while (Objects.nonNull(clone)) {
            if (clone.val != nodeDay1.val) {
                System.out.println("出错了");
            }
            if (clone.rand.val != nodeDay1.rand.val) {
                System.out.println("出错了");
            }
            clone = clone.next;
            nodeDay1 = nodeDay1.next;
        }
    }

    public ListNode getUpMiddleNode(ListNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public ListNode getDownMiddleNode(ListNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode getUpUpMiddleNode(ListNode node) {
        if (Objects.isNull(node) || Objects.isNull(node.next) || Objects.isNull(node.next.next)) {
            return node;
        }
        ListNode fast = node.next.next;
        ListNode slow = node;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    public ListNode getDownDownMiddleNode(ListNode node) {
        if (Objects.isNull(node) || Objects.isNull(node.next) || Objects.isNull(node.next.next)) {
            return node;
        }
        ListNode fast = node.next.next;
        ListNode slow = node;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public boolean isRever(ListNode node) {
        if (Objects.isNull(node) || Objects.isNull(node.next)) {
            return true;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // slow 为中点或者中上线
        ListNode r = slow.next;
        // 中点下一个节点指向null
        slow.next = null;
        ListNode pre = slow;
        ListNode next;
        // 后半部分翻转
        while (Objects.nonNull(r)) {
            next = r.next;
            r.next = pre;
            pre = r;
            r = next;
        }
        // 往中间遍历
        ListNode left = node;
        ListNode right = pre;
        boolean result = true;
        while (Objects.nonNull(left) && Objects.nonNull(right)) {
            if (!left.value.equals(right.value)) {
                result = false;
                break;
            }
            left = left.next;
            right = right.next;
        }
        // 还原链表
        ListNode pre1;
        next = null;
        while (Objects.nonNull(pre)) {
            pre1 = pre.next;
            pre.next = next;
            next = pre;
            pre = pre1;
        }
        return result;
    }

    /**
     * L1L2L3L4R1R2R3R4 -> L1R4L2R3L3R2L4R1
     * @param node
     * @return
     */
    public void reverInsert(ListNode node) {
        if (Objects.isNull(node) || Objects.isNull(node.next)) {
            return;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        LinkedList<ListNode> queue = new LinkedList<>();
        Stack<ListNode> stack = new Stack<>();
        int i = 0;
        while (node != slow) {
            queue.add(node);
            node = node.next;
        }
        while (Objects.nonNull(node)) {
            stack.push(node);
            node = node.next;
        }
        node = queue.poll();
        ListNode cur;
        i = 1;
        while (!queue.isEmpty() && !stack.isEmpty()) {
            if ((i++ & 1) ==1) {
                cur = stack.pop();
            } else {
                cur = queue.poll();
            }
            node.next = cur;
            node = cur;
        }
        if (!queue.isEmpty()) {
            cur = queue.poll();
            node.next = cur;
            node = cur;
        }
        if (!stack.isEmpty()) {
            cur = stack.pop();
            node.next = cur;
            node = cur;
        }
        node.next = null;
    }

    public void reverInsert2(ListNode node) {
        if (Objects.isNull(node) || Objects.isNull(node.next)) {
            return;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 后半部翻转
        ListNode right = slow.next;
        slow.next = null;
        ListNode pre = null;
        ListNode next;
        while (Objects.nonNull(right)) {
            next = right.next;
            right.next = pre;
            pre = right;
            right = next;
        }
        ListNode preNext;
        while (Objects.nonNull(node) && Objects.nonNull(pre)) {
            next = node.next;
            preNext = pre.next;
            node.next = pre;
            pre.next = next;
            node = next;
            pre = preNext;
        }
    }
    public ListNode partitionListNode(ListNode node, int value) {
        if (Objects.isNull(node) || Objects.isNull(node.next)) {
            return node;
        }
        ListNode ltH = null;
        ListNode ltT = null;
        ListNode eqH = null;
        ListNode eqT = null;
        ListNode gtH = null;
        ListNode gtT = null;
        while (Objects.nonNull(node)) {
            if (node.value < value) {
                if (Objects.isNull(ltH)) {
                    ltH = ltT = node;
                } else {
                    ltT.next = node;
                }
                ltT = node;
            } else if (node.value == value) {
                if (Objects.isNull(eqH)) {
                    eqH = eqT = node;
                } else {
                    eqT.next = node;
                }
                eqT = node;
            } else {
                if (Objects.isNull(gtH)) {
                    gtH = gtT = node;
                } else {
                    gtT.next = node;
                }
                gtT = node;
            }
            node = node.next;
        }
        ltT.next = null;
        eqT.next = null;
        gtT.next = null;
        ListNode head;
        if (Objects.nonNull(ltH)) {
            head = ltH;
            if (Objects.nonNull(eqH)) {
                ltT.next = eqH;
                ltT = eqT;
            }
            ltT.next = gtH;
        } else {
            head = Objects.nonNull(eqH)? eqH : gtH;
            if (Objects.nonNull(eqH)) {
                eqT.next = gtH;
            }
        }
        return head;
    }

    public ListNodeDay10 clone1(ListNodeDay10 node) {
        if (Objects.isNull(node)) {
            return null;
        }
        // 复制
        ListNodeDay10 cur = node;
        HashMap<ListNodeDay10, ListNodeDay10> map = new HashMap<>(10);
        while (Objects.nonNull(cur)) {
            map.put(cur, new ListNodeDay10(cur.val));
            cur = cur.next;
        }
        // 一一对应获取连接
        ListNodeDay10 head = map.get(node);
        ListNodeDay10 link = head;
        while (Objects.nonNull(node)) {
            if (Objects.nonNull(node.getNext())) {
                link.next = map.get(node.getNext());
            }
            link.rand = map.get(node.getRand());
            node = node.next;
            link = link.next;
        }
        return head;
    }

    public ListNodeDay10 clone2(ListNodeDay10 node) {
        if (Objects.isNull(node)) {
            return null;
        }
        ListNodeDay10 cur = node;
        while (Objects.nonNull(cur)) {
            // 复制一个节点在当前节点后
            ListNodeDay10 newNode = new ListNodeDay10(cur.val);
            ListNodeDay10 next = cur.next;
            cur.next = newNode;
            newNode.next = next;
            cur = next;
        }
        // 随机节点
        cur = node;
        while (Objects.nonNull(cur)) {
            ListNodeDay10 copy = cur.next;
            // 随机指针
            copy.rand = cur.rand.next;
            // cur的next
            cur = copy.next;
        }
        // 拆分
        cur = node;
        ListNodeDay10 ano = cur.next;
        while (Objects.nonNull(cur)) {
            ListNodeDay10 copy = cur.next;
            cur.next = copy.next;
            copy.next = Objects.nonNull(cur.next)? cur.next.next:null;
            cur = cur.next;
        }
        return ano;
    }

}
