package coding.tixi.day10;

import coding.tixi.ListNode;

import java.util.Objects;

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
 *      如   6->3->7->6->7->4, 6    ==>>   3->4->6->->7->7
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
        System.out.println(upMiddleNode);
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

}
