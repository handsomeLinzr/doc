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

    public static LinkedNode generalLinkedNode(int length, int max) {
        // 尾节点
        LinkedNode head = new LinkedNode((int) (Math.random() * max) + 1, null);
        length = (int) (Math.random() * length) + 1;
        for (int i = 0; i < length; i++) {
            int value = (int) (Math.random() * max) + 1;
            head = new LinkedNode(value, head);
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

    public static void printLinkedNode(LinkedNode node) {
        StringBuilder sb = new StringBuilder(String.valueOf(node.getValue()));
        while (node.hasNext()) {
            node = node.getNext();
            sb.append("=>").append(node.getValue());
        }
        System.out.println(sb);
    }

    public static LinkedNode generalNode(int[] arr) {
        LinkedNode head = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            head = new LinkedNode(arr[i], head);
        }
        return head;
    }

    public static Integer[] toArr(LinkedNode node) {
        List<Integer> list = new ArrayList<>();
        while (Objects.nonNull(node)) {
            list.add(node.value);
            node = node.next;
        }
        return list.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        int[] arr = {1,5,3};
        LinkedNode linkedNode = generalNode(arr);
        Integer[] integers = toArr(linkedNode);
        System.out.println(Arrays.toString(integers));
    }

}
