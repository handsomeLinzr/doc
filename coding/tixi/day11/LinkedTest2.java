package coding.tixi.day11;

import coding.tixi.ListNode;

import java.util.Objects;

/**
 * 1.给定一个链表，可能有环也可能没环，返回第一个入环节点（有的情况）或者null（没环的情况）  getLoopNode
 *      1. 利用容器 hashSet，当放不进的时候说明有环且是第一个入环节点
 *      2. 快慢指针，快指针一次两步，慢指针一次一步，
 *          快指针为null，则没有环
 *          当快指针和慢指针重合，说明有环，
 *          这时候将慢指针回到头节点，快指针改为一次一步
 *          当快慢指针重合时，则肯定为入环节点（不用证明）
 * 2. 给定两个链表，可能有环也可能没环，返回相交的第一个节点，没有则返回null
 *      1. 获取每个链表的第一个入环节点
 *      2. 如果都没环
 *          1. 如果尾节点一样，则不相交
 *          2. 尾节点一样，则看两个链表各自的长度，将长的减去短的得到长度差，让长的先走长度差的步，之后一起走，当两个指针一样时为第一个相交
 *      3. 一个有环一个没环，肯定不相交
 *      4. 两个都有环
 *          1. 当两个入环节点相等，则将入环节点当做尾节点，按照2-2方式方式获取
 *          2. 两个入环节点不相等
 *              1. 从 loop1 开始走，走到loop1（走一圈），没遇到loop2，则不想交
 *              2. 走一圈过程遇到loop2，则loop1和loop2都是相交节点
 *
 * @author linzherong
 * @date 2025/3/30 15:36
 */
public class LinkedTest2 {

    public static void main(String[] args) {
        LinkedTest2 test = new LinkedTest2();

        ListNode node4 = new ListNode(4, null);
        ListNode node5 = new ListNode(5, node4);
        node4.next = node5;
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        ListNode loopNode = test.getLoopNode(node1);
        System.out.println(Objects.isNull(loopNode)? "null" : loopNode.value);


        ListNode n1 = new ListNode(11, new ListNode(12, new ListNode(13, new ListNode(14, null))));
        ListNode node11 = new ListNode(1, new ListNode(15, new ListNode(17, n1)));
        ListNode node12 = new ListNode(1, n1);
        ListNode x = test.getX(node12, node11);
        System.out.println(x == null? "null" : x.value);

    }

    public ListNode getLoopNode(ListNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        ListNode fast = node;
        ListNode slow = node;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            // 快慢指针
            fast = fast.next.next;
            slow = slow.next;
            if (Objects.nonNull(fast) && fast.equals(slow)) {
                break;
            }
        }
        if (Objects.isNull(fast) || Objects.isNull(fast.next)) {
            return null;
        }
        // 快指针改为1步长，慢指针从头开始走，再次重合则为循环点
        slow = node;
        while (!fast.equals(slow)) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    // 两个链表的相交节点
    public ListNode getX(ListNode node1, ListNode node2) {
        if (node1 == null && node2 == null) {
            return null;
        }
        ListNode loopNode1 = getLoopNode(node1);
        ListNode loopNode2 = getLoopNode(node2);
        if ((loopNode1 == null) ^ (loopNode2 == null)) {
            return null;
        }
        if (loopNode1 == null) {
            return noLoop(node1, node2);
        } else {
            return bothLoop(node1, node2);
        }
    }
    // 没有环
    private ListNode noLoop(ListNode node1, ListNode node2) {
        int n = 1;
        ListNode curNode1 = node1;
        while (curNode1.next != null) {
            curNode1 = curNode1.next;
            n++;
        }
        ListNode curNode2 = node2;
        n--;
        while (curNode2.next != null) {
            curNode2 = curNode2.next;
            n--;
        }
        // 链尾不一致，则不相交
        if (curNode1 != curNode2) {
            return null;
        }
        // 获取较长的那个链表
        curNode1 = n >= 0? node1 : node2;
        // 获取短的
        curNode2 = curNode1 == node1?node2:node1;
        n = Math.abs(n);
        // 长的先走n的长度
        while (n>0) {
            curNode1 = curNode1.next;
            n--;
        }
        while (curNode1 != curNode2) {
            // 长度相等后，一起移动
            curNode1 = curNode1.next;
            curNode2 = curNode2.next;
        }
        return curNode1;
    }
    // 有环
    private ListNode bothLoop(ListNode node1, ListNode node2) {

        return null;
    }



}
