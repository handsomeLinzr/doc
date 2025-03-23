package coding.xin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/3/16 14:42
 */
public class NodeUtils {

    public static ListNode generalLinkedNode(int length, int max) {
        // 尾节点
        ListNode head = new ListNode((int) (Math.random() * max) + 1, null);
        length = (int) (Math.random() * length) + 1;
        for (int i = 0; i < length; i++) {
            int value = (int) (Math.random() * max) + 1;
            head = new ListNode(value, head);
        }
        return head;
    }

    public static DoubleNode generalDoubleNode(int length, int max) {
        DoubleNode head = new DoubleNode((int) (Math.random() * max) + 1, null, null);
        length = (int) (Math.random() * length) + 1;
        for (int i = 0; i < length; i++) {
            int value = (int) (Math.random() * max) + 1;
            DoubleNode pre = new DoubleNode(value, null, head);
            head.pre = pre;
            head = pre;
        }
        return head;
    }

    public static void printLinkedNode(ListNode node) {
        if (node == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(node.getValue()));
        while (node.hasNext()) {
            node = node.getNext();
            sb.append("=>").append(node.getValue());
        }
        System.out.println(sb);
    }

    public static ListNode generalNode(int[] arr) {
        ListNode head = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            head = new ListNode(arr[i], head);
        }
        return head;
    }

    public static Integer[] toArr(ListNode node) {
        List<Integer> list = new ArrayList<>();
        while (Objects.nonNull(node)) {
            list.add(node.value);
            node = node.next;
        }
        return list.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        int[] arr = {1,5,3};
        ListNode listNode = generalNode(arr);
        Integer[] integers = toArr(listNode);
        System.out.println(Arrays.toString(integers));
    }

}
