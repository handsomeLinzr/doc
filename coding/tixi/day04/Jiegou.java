package coding.tixi.day04;

import coding.tixi.ListNode;

import java.util.Objects;

/**
 * 结构
 * 1. 单链表翻转
 * 2. 双链表翻转
 * 3. 单链表 删除一个目标节点  removeValue(Node, value)
 * 4. 双向链表实现队列和栈
 * 5. 数组实现栈
 * 6. 数组实现队列 （用变量 size 来便利实现）
 * 7. 实现一个栈，要求 push、pop、getMin 都是 O(N) 的时间复杂度 (两个栈实现)
 * 8. 如何使用栈结构实现队列结构、如何使用队列结构实现栈结构  （不能用队列只能FIFO,不是双端队列）
 * 9. 递归取最大值
 * @author linzherong
 * @date 2025/3/23 23:13
 */
public class Jiegou {

    public static void main(String[] args) {
        Jiegou jiegou = new Jiegou();

        ListNode listNode = jiegou.reverseBetween(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null)))), 2, 3);
        System.out.println(jiegou.getMax(new int[]{1,7,4,0,2,-4,-7,34,75,2,45,24}));
    }

    public int getMax(int[] arr) {
        if (arr.length == 1) {
            return arr[0];
        }
        int[] arr1 = new int[arr.length-1];
        System.arraycopy(arr, 1, arr1, 0, arr.length-1);
        return Math.max(arr[0], getMax(arr1));
    }

    public ListNode ListNodeRevert(ListNode listNode) {
        if (Objects.isNull(listNode)) {
            return null;
        }
        ListNode cur = listNode;
        ListNode pre = null;
        ListNode next;
        while (Objects.nonNull(cur)) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // leetcode 92
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (Objects.isNull(head) || left >= right) {
            return head;
        }
        int i = 1;
        ListNode cur = head;
        ListNode node1 = null;
        while (i < left) {
            node1 = cur;
            cur = cur.next;
            i++;
        }
        ListNode node2 = cur;
        ListNode pre = cur;
        ListNode next;
        cur = cur.next;
        i++;
        while (i <= right) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            i++;
        }
        node2.next = cur;
        if (Objects.nonNull(node1)) {
            node1.next = pre;
            return head;
        } else {
            return pre;
        }
    }

}
