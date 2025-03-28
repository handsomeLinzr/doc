package coding.tixi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author linzherong
 * @date 2025/3/27 13:16
 */
public class NodeUtils {

    public static DoubleNode buildDoubleNode(int[] arr) {
        if (arr.length == 0) {
            return null;
        }
        DoubleNode head = new DoubleNode(arr[0]);
        DoubleNode pre = head;
        DoubleNode node;
        for (int i = 1; i < arr.length; i++) {
             node = new DoubleNode(arr[i]);
             node.pre = pre;
             pre.next = node;
             pre = node;
        }
        return head;
    }

    public static DoubleNode generalDoubleNode() {
        int length = (int) (Math.random() * 10) + 1;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) ((Math.random() * 100) - (Math.random() * 100));
        }
        return buildDoubleNode(arr);
    }

    public static int[] getDoubleNodeArr(DoubleNode node) {
        List<Integer> list = new ArrayList<>();
        while (Objects.nonNull(node)) {
            list.add(node.getValue());
            node = node.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] getDoubleNodeRArr(DoubleNode node) {
        List<Integer> list = new ArrayList<>();
        DoubleNode pre = null;
        while (Objects.nonNull(node)) {
            pre = node;
            node = node.next;
        }
        while (Objects.nonNull(pre)) {
            list.add(pre.getValue());
            pre = pre.pre;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static ListNode buildListNode(int[] arr) {
        ListNode temp = new ListNode(0, null);
        ListNode node = temp;
        for (int i = 0; i < arr.length; i++) {
            node.next = new ListNode(arr[i], null);
            node = node.next;
        }
        return temp.next;
    }

    public static ListNode generalListNode() {
        int length = (int) (Math.random() * 10) + 1;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) ((Math.random() * 100) - (Math.random() * 100));
        }
        return buildListNode(arr);
    }

    public static int[] getListArr(ListNode node) {
        List<Integer> list = new ArrayList<>();
        while (Objects.nonNull(node)) {
            list.add(node.value);
            node = node.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        DoubleNode node = buildDoubleNode(new int[]{1, 4, 3, 5});
        System.out.println(node);
    }

}
