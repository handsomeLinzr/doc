package coding.xin;

import java.util.*;

/**
 * @author linzherong
 * 单链表逆序  reverseLinkedList
 * 双链表逆序  reverseDoubleList
 *
 * 根据 node
 * 单链表实现队列  linkedListToQueue  offer  poll  peek
 * 单链表实现栈  linkedListToStack  offer  poll  peek
 *
 * 双链表实现双端队列 doubleListToDqueue offerHead offerTail pollHead pollTail
 * leetcode 25   k个元素的翻转
 * 链表相加,从左到右是从低位到高位， 如 2->3->5  +    2->4->6   => 532+642=1174  => 4->7->1->1
 * 两个有序链表合并，将两个有序链表合并成一个，依然有序
 * @date 2025/3/13 08:06
 */
public class Day4 {

    public static void main(String[] args) {
        Day4 day4 = new Day4();
        for (int i = 0; i < 1; i++) {
//            LinkedNode linkedNode = NodeUtils.generalLinkedNode(20, 20);
//            LinkedNode copy = linkedNode;
//            List<Integer> list = new ArrayList<>();
//            do {
//                list.add(linkedNode.getValue());
//                linkedNode = linkedNode.next;
//            } while (Objects.nonNull(linkedNode));
//
//            LinkedNode linkedNode1 = day4.reverseLinkedList(copy);
//            List<Integer> list1 = new ArrayList<>();
//            do {
//                list1.add(linkedNode1.getValue());
//                linkedNode1 = linkedNode1.next;
//            } while (Objects.nonNull(linkedNode1));
//            if (list.size() != list1.size()) {
//                System.out.println("出错了");
//            }
//            for (int j = 0; j < list.size(); j++) {
//                if (!list.get(j).equals(list1.get(list1.size() - j-1))) {
//                    System.out.println("出错了");
//                }
//            }
//            DoubleNode standNode = NodeUtils.generalDoubleNode(20, 20);
//            DoubleNode node = standNode;
//            // next数组
//            List<Integer> list1 = new ArrayList<>();
//            list1.add(node.value);
//            while (Objects.nonNull(node.next)) {
//                node = node.next;
//                list1.add(node.value);
//            }
//            // pre数组
//            List<Integer> list2 = new ArrayList<>();
//            list2.add(node.value);
//            while (Objects.nonNull(node.pre)) {
//                node = node.pre;
//                list2.add(node.value);
//            }
//            if (list1.size() != list2.size()) {
//                System.out.println("出错了");
//            }
//            for (int j = 0; j < list1.size(); j++) {
//                if (!list1.get(j).equals(list2.get(list2.size() - 1 - j))) {
//                    System.out.println("出错了");
//                }
//            }
//            // 翻转
//            DoubleNode node1 = day4.reverseDoubleList(standNode);
//            // next数组
//            List<Integer> list3 = new ArrayList<>();
//            list3.add(node1.value);
//            while (Objects.nonNull(node1.next)) {
//                node1 = node1.next;
//                list3.add(node1.value);
//            }
//            // pre数组
//            List<Integer> list4 = new ArrayList<>();
//            list4.add(node1.value);
//            while (Objects.nonNull(node1.pre)) {
//                node1 = node1.pre;
//                list4.add(node1.value);
//            }
//
//            if (list1.size() != list3.size()) {
//                System.out.println("出错了");
//            }
//            for (int j = 0; j < list1.size(); j++) {
//                if (!list1.get(j).equals(list3.get(list3.size() - j - 1))) {
//                    System.out.println("出错了");
//                }
//            }
//            if (list2.size() != list4.size()) {
//                System.out.println("出错了");
//            }
//            for (int j = 0; j < list2.size(); j++) {
//                if (!list2.get(j).equals(list4.get(list4.size() - j - 1))) {
//                    System.out.println("出错了");
//                }
//            }
            // 单链表——>队列
//            LinkedListToQueue queue = new LinkedListToQueue();
//            Queue<Integer> queue1 = new LinkedList<>();
//            LinkedListToStack stack = new LinkedListToStack();
//            Stack<Integer> stack1 = new Stack<>();
//            DoubleListToDqueue dqueue = new DoubleListToDqueue();
//            Deque<Integer> deque1 = new ArrayDeque<>();
//            for (int j = 0; j < 20; j++) {
//                double r = Math.random();
//                if (r < 0.2) {
//                    int random = (int) (Math.random() * 20);
//                    dqueue.offerHead(random);
//                    deque1.offerFirst(random);
//                } else if (r < 0.4) {
//                    int random = (int) (Math.random() * 20);
//                    dqueue.offerTail(random);
//                    deque1.offerLast(random);
//                } else if (r < 0.6) {
//                    Integer integer = dqueue.pollHead();
//                    Integer integer1 = deque1.pollFirst();
//                    if (Objects.isNull(integer)) {
//                        if (Objects.nonNull(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    } else {
//                        if (!integer.equals(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    }
//                } else if (r < 0.8) {
//                    Integer integer = dqueue.pollTail();
//                    Integer integer1 = deque1.pollLast();
//                    if (Objects.isNull(integer)) {
//                        if (Objects.nonNull(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    } else {
//                        if (!integer.equals(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    }
//                } else if (r < 0.9) {
//                    Integer integer = dqueue.peekHead();
//                    Integer integer1 = deque1.peekFirst();
//                    if (Objects.isNull(integer)) {
//                        if (Objects.nonNull(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    } else {
//                        if (!integer.equals(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    }
//                } else {
//                    Integer integer = dqueue.peekTail();
//                    Integer integer1 = deque1.peekLast();
//                    if (Objects.isNull(integer)) {
//                        if (Objects.nonNull(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    } else {
//                        if (!integer.equals(integer1)) {
//                            System.out.println("出错了");
//                        }
//                    }
//                }
//            }
//            for (int j = 0; j < dqueue.size; j++) {
//                if ((j & 1) == 0) {
//                    if (!dqueue.pollHead().equals(deque1.pollFirst())) {
//                        System.out.println("出错了");
//                    }
//                } else {
//                    if (!dqueue.pollTail().equals(deque1.pollLast())) {
//                        System.out.println("出错了");
//                    }
//                }
//            }

//            LinkedNode a = NodeUtils.generalLinkedNode(5, 9);
//            LinkedNode b = NodeUtils.generalLinkedNode(5, 9);
//            int sum1 = day4.getNum(a) + day4.getNum(b);
//            int sum2 = day4.getNum(day4.linkedAdd(a, b));
//            if (sum1 != sum2) {
//                System.out.println("出错了");
//            }
//            int[] array1 = ArrayUtils.generalArray(6, 20);
//            Arrays.sort(array1);
//            LinkedNode node1 = NodeUtils.generalNode(array1);
//            int[] array2 = ArrayUtils.generalArray(6, 20);
//            Arrays.sort(array2);
//            LinkedNode node2 = NodeUtils.generalNode(array2);
//
//            // 组合，排序
//            int[] arr = new int[array1.length + array2.length];
//            for (int j = 0; j < array1.length; j++) {
//                arr[j] = array1[j];
//            }
//            for (int j = 0; j < array2.length; j++) {
//                arr[j + array1.length] = array2[j];
//            }
//            Arrays.sort(arr);
//
//            // 排序
//            LinkedNode node = day4.nodeMerge(node1, node2);
//            Integer[] integers = NodeUtils.toArr(node);
//            if (arr.length != integers.length) {
//                System.out.println("出错了");
//            }
//            for (int j = 0; j < arr.length; j++) {
//                if (arr[j] != integers[j]) {
//                    System.out.println("出错了");
//                }
//            }
            ListNode node1 = NodeUtils.generalNode(new int[]{1, 2, 5, 6, 3, 5, 7, 4, 2,3,5});
            NodeUtils.printLinkedNode(node1);
            ListNode node2 = day4.reverseKGroup(node1, 3);
            NodeUtils.printLinkedNode(node2);
        }
        System.out.println("成功");
    }

    private int getNum(ListNode node) {
        int sum = 0;
        int mul = 1;
        while (Objects.nonNull(node)) {
            sum = sum + (node.value * mul);
            mul *= 10;
            node = node.next;
        }
        return sum;
    }

    /**
     * 单链表逆转
     * 1.先记录next节点
     * 2.处理当前节点的next
     */
    public ListNode reverseLinkedList(ListNode node) {
        // 头节点
        ListNode head = node;
        // 下一个节点
        ListNode next = node.next;
        // 首个节点的下一个节点指向空
        node.next = null;
        while (Objects.nonNull(next)) {
            node = next;
            next = node.next;
            node.next = head;
            head = node;
        }
        return head;
    }

    /**
     * 双向链表翻转
     */
    public DoubleNode reverseDoubleList(DoubleNode node) {
        // 下一个节点
        DoubleNode next = node.next;
        // 上一个节点
        node.next = null;
        node.pre = next;
        DoubleNode head = node;
        while (Objects.nonNull(next)) {
            node = next;
            next = node.next;
            node.next = head;
            node.pre = next;
            head = node;
        }
        return head;
    }

    /**
     * 单链表实现队列
     */
    public static class LinkedListToQueue {
        // 头节点
        ListNode head;
        // 尾节点
        ListNode tail;
        public int size;

        // offer  poll  peek
        public void offer(Integer num) {
            size++;
            if (Objects.isNull(head)) {
                head = tail = new ListNode(num, null);
            } else {
                ListNode node = new ListNode(num, null);
                tail.next = node;
                tail = node;
            }
        }

        public Integer poll() {
            if (Objects.isNull(head)) {
                return null;
            }
            size --;
            Integer value = head.value;
            head = head.next;
            if (Objects.isNull(head)) {
                tail = null;
            }
            return value;
        }

        public Integer peek() {
            if (Objects.isNull(head)) {
                return null;
            }
            return head.value;
        }
    }

    /**
     * 单链表实现栈
     */
    public static class LinkedListToStack {
        ListNode head;
        public int size;
        public void push(Integer num) {
            size ++;
            if (Objects.isNull(head)) {
                head =  new ListNode(num, null);
            } else {
                head = new ListNode(num, head);
            }
        }

        public Integer peek() {
            if (Objects.isNull(head)) {
                throw new EmptyStackException();
            }
            return head.value;
        }

        public Integer pop() {
            if (Objects.isNull(head)) {
                throw new EmptyStackException();
            }
            size --;
            Integer value = head.value;
            head = head.next;
            return value;
        }
    }

    /**
     * 双链表实现双端队列 doubleListToDqueue offerHead offerTail pollHead pollTail
     */
    static class DoubleListToDqueue {
        DoubleNode head;
        DoubleNode tail;
        public int size;

        public void offerHead(Integer num) {
            size ++;
            if (Objects.isNull(head)) {
                head = tail = new DoubleNode(num, null, null);
            } else {
                DoubleNode node = new DoubleNode(num, null, head);
                head.pre = node;
                head = node;
            }
        }
        public void offerTail(Integer num) {
            size ++;
            if (Objects.isNull(tail)) {
                head = tail = new DoubleNode(num, null, null);
            } else {
                DoubleNode node = new DoubleNode(num, tail, null);
                tail.next = node;
                tail = node;
            }
        }
        public Integer pollHead() {
            if (Objects.isNull(head)) {
                return null;
            }
            size --;
            Integer value = head.value;
            head = head.next;
            if (Objects.isNull(head)) {
                tail = null;
            } else {
                head.pre = null;
            }
            return value;
        }
        public Integer pollTail() {
            if (Objects.isNull(tail)) {
                return null;
            }
            Integer value = tail.value;
            tail = tail.pre;
            if (Objects.isNull(tail)) {
                head = null;
            } else {
                tail.next = null;
            }
            size --;
            return value;
        }
        public Integer peekHead() {
            if (Objects.isNull(head)) {
                return null;
            }
            return head.value;
        }
        public Integer peekTail() {
            if (Objects.isNull(tail)) {
                return null;
            }
            return tail.value;
        }
    }

    /**
     * 链表相加
     * @return
     */
    public ListNode linkedAdd(ListNode a, ListNode b) {
//        LinkedNode result;
//        LinkedNode a1 = a;
//        LinkedNode b1 = b;
//        // 长度
//        int lLength = 1;
//        int rLength = 1;
//        while (a1.hasNext()) {
//            lLength ++;
//            a1 = a1.next;
//        }
//        while (b1.hasNext()) {
//            rLength ++;
//            b1 = b1.next;
//        }
//        int cur = 0;
//        int num ;
//        while (lLength-- > 0 && rLength-- > 0) {
//            num = (a.value + b.value + cur) % 10;
//            cur = (a.value + b.value + cur) / 10;
//            a = a.next;
//            b = b.next;
//            LinkedNode node = new LinkedNode(num, null);
//        }


        // 进位
        int carry = 0;
        int num;
        ListNode node = new ListNode(0, null);
        ListNode head = node;
        // 两个链都在的情况
        while (Objects.nonNull(a) && Objects.nonNull(b)) {
            num = (a.value + b.value + carry) % 10;
            carry = (a.value + b.value + carry) / 10;
            a = a.next;
            b = b.next;
            ListNode listNode = new ListNode(num, null);
            node.next = listNode;
            node = listNode;
        }
        // 剩下一个链的情况
        ListNode last = Objects.nonNull(a)? a : b;
        while (Objects.nonNull(last)) {
            num = (last.value + carry) % 10;
            carry = (last.value + carry) / 10;
            last = last.next;
            ListNode listNode = new ListNode(num, null);
            node.next = listNode;
            node = listNode;
        }
        if (carry > 0) {
            node.next = new ListNode(carry, null);
        }
        return head.next;
    }

    /**
     * 有序链表合并
     * @return
     */
    public ListNode nodeMerge(ListNode a, ListNode b) {
        if (Objects.isNull(a)) {
            return b;
        }
        if (Objects.isNull(b)) {
            return a;
        }
        // 用首个元素最小的一条作为根基
        ListNode last = a.value <= b.value? a : b;
        ListNode another = a == last? b : a;
        ListNode result = last;
        ListNode pre = last;
        // 两条链都存在的情况
        while (Objects.nonNull(last) && Objects.nonNull(another)) {
            if (last.value <= another.value) {
                pre = last;
                last = last.next;
            } else {
                // 记录 another 的 next
                ListNode next = another.next;
                // 将 ano 插入到 last的 pre 后边
                pre.next = another;
                another.next = last;
                // pre 往前移动
                pre = another;
                another = next;
            }
        }
        if (Objects.isNull(last)) {
            pre.next = another;
        }
        return result;
    }

    /**
     * k个元素翻转
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 这里第一次获取到的，就是最后的头结点
        ListNode result = getNextKNode(head, k);
        // 一次翻转都不够，直接返回
        if (Objects.isNull(result)) {
            return head;
        }
        ListNode endNode= result;
        ListNode begin = head;
        // 第一次，头部的前部一个为null
        ListNode pre = null;
        // 符合翻转规则
        while (Objects.nonNull(endNode)) {
            // 翻转
            reverseNode(pre, begin, endNode);
            // 翻转后这一段的最开始节点就是之前的开始节点
            pre = begin;
            // 下次翻转从endNodo的下一个节点开始
            begin = begin.next;
            // 获取下一次翻转的最后节点
            endNode = getNextKNode(begin, k);
        }
        return result;
    }

    /**
     * 获取到偏移k之后的node节点
     * @param node
     * @return
     */
    public ListNode getNextKNode(ListNode node, int k) {
        while (--k > 0 && Objects.nonNull(node)) {
            node = node.next;
        }
        return node;
    }

    /**
     * 翻转
     */
    public ListNode reverseNode(ListNode begin1, ListNode begin, ListNode end) {
        // 当前处理节点
        ListNode current = begin;
        // 记录end的下一个节点
        ListNode finalNode = end.next;
        // 记录下一个节点
        ListNode next;
        // 上一个节点（一开始指定为end的下一个节点，因为需要将第一个节点的next指向它）
        ListNode pre = finalNode;
        while (current != finalNode) {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        // 最后把前边的尾巴接住
        if (Objects.nonNull(begin1)) {
            begin1.next = end;
        }
        return end;
    }


}
