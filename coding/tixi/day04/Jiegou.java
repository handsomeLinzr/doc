package coding.tixi.day04;

import coding.tixi.ArrayUtils;
import coding.tixi.DoubleNode;
import coding.tixi.ListNode;
import coding.tixi.NodeUtils;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

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

//        ListNode listNode = jiegou.reverseBetween(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null)))), 2, 3);
//        jiegou.testDoubleNodeRevert();
//        System.out.println(jiegou.getMax(new int[]{1,7,4,0,2,-4,-7,34,75,2,45,24}));
//        jiegou.testRemoveValue();
//        jiegou.testMyQueue();
//        jiegou.testMyStack();
//        jiegou.testMyStrongStack();
//        jiegou.testStackToQueue();
//        jiegou.testQueueToStack();
        jiegou.testMyArrayToQueue();
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

    public DoubleNode doubleNodeRevert(DoubleNode node) {
        if (Objects.isNull(node)) {
            return null;
        }
        DoubleNode cur = node;
        DoubleNode pre = null;
        DoubleNode next;
        while (Objects.nonNull(cur)) {
            next = cur.next;
            cur.next = pre;
            cur.pre = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public void testDoubleNodeRevert() {
        for (int i = 0; i < 100_0000; i++) {
            DoubleNode node = NodeUtils.generalDoubleNode();
            int[] arr = NodeUtils.getDoubleNodeArr(node);
            int[] rarr = NodeUtils.getDoubleNodeRArr(node);

            node = doubleNodeRevert(node);

            int[] arr1 = NodeUtils.getDoubleNodeArr(node);
            int[] rarr1 = NodeUtils.getDoubleNodeRArr(node);

            if (arr.length != rarr1.length) {
                System.out.println("错误");
            }

            for (int j = 0; j < arr.length; j++) {
                if (arr[j] != rarr1[j]) {
                    System.out.println("错误");
                }
            }

            if (rarr.length != arr1.length) {
                System.out.println("错误");
            }

            for (int j = 0; j < rarr.length; j++) {
                if (rarr[j] != arr1[j]) {
                    System.out.println("错误");
                }
            }
        }
        System.out.println("成功");
    }


    // leetcode 92
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (Objects.isNull(head) || left >= right) {
            return head;
        }
        // 临时节点
        ListNode temp = new ListNode(0, head);
        ListNode pre = temp;
        ListNode begin = head;
        int i = 1;
        for (; i < left; i++) {
            pre = begin;
            begin = begin.next;
        }
        ListNode end = begin;
        for (; i < right; i++) {
            end = end.next;
        }
        pre.next = revert(begin, end);
        return temp.next;
    }
    public ListNode revert(ListNode begin, ListNode end) {
        ListNode last = end.next;
        ListNode cur = begin;
        ListNode pre = end.next;
        ListNode next;
        while (cur != last) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public ListNode removeValue(ListNode node, int value) {
        if (Objects.isNull(node)) {
            return node;
        }
        ListNode temp = new ListNode(0, node);
        ListNode pre = temp;
        while (Objects.nonNull(node)) {
            if (node.value == value) {
                node = node.next;
            }
            pre.next = node;
            pre = node;
            if (Objects.nonNull(node)) {
                node = node.next;
            }
        }
        return temp.next;
    }

    public void testRemoveValue() {
        for (int m = 0; m < 100_0000; m++) {
            ListNode node = NodeUtils.generalListNode();
            int[] arr = NodeUtils.getListArr(node);
            int value = arr[(int) (Math.random() * arr.length)];
            node = removeValue(node, value);
            int[] arr1 = NodeUtils.getListArr(node);

            int j = 0;
            int i = 0;
            for (;i < arr.length && j < arr1.length; i++) {
                if (arr[i] != arr1[j]) {
                    if (arr[i] != value) {
                        System.out.println("错误");
                    } else {
                        j = j-1;
                    }
                }
                j++;
            }
            for (; i < arr.length; i++) {
                if (arr[i] != value) {
                    System.out.println("出错");
                }
            }
        }
        System.out.println("成功");
    }

    public void testMyQueue() {
        for (int i = 0; i < 100_0000; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            MyQueue queue = new MyQueue();
            int time = (int) (Math.random() * 100) + 1;
            for (int j = 0; j < time; j++) {
                double t = Math.random();
                if (t < 0.25) {
                    int value = (int) (Math.random() * 100);
                    list.addFirst(value);
                    queue.addFirst(value);
                } else if (t < 0.5) {
                    int value = (int) (Math.random() * 100);
                    list.addLast(value);
                    queue.addLast(value);
                } else if (t < 0.6) {
                    if (list.size() > 0 && queue.size > 0) {
                        if (!list.pollFirst().equals(queue.pollFirst())) {
                            System.out.println("出错了");
                        }
                    } else if (list.size() != queue.size) {
                        System.out.println("出错了");
                    }
                } else if (t < 0.7) {
                    if (list.size() > 0 && queue.size > 0) {
                        if (!list.pollFirst().equals(queue.pollFirst())) {
                            System.out.println("出错了");
                        }
                    } else if (list.size() != queue.size) {
                        System.out.println("出错了");
                    }
                } else if (t < 0.9) {
                    Integer a = list.peekFirst();
                    Integer b = queue.peekFirst();
                    if (Objects.isNull(a) && Objects.isNull(b)) {
                        continue;
                    }
                    if (Objects.isNull(a) || Objects.isNull(b)) {
                        System.out.println("出错了");
                    } else if (!a.equals(b)) {
                        System.out.println("出错了");
                    }
                } else {
                    Integer a = list.peekLast();
                    Integer b = queue.peekLast();
                    if (Objects.isNull(a) && Objects.isNull(b)) {
                        continue;
                    }
                    if (Objects.isNull(a) || Objects.isNull(b)) {
                        System.out.println("出错了");
                    } else if (!a.equals(b)) {
                        System.out.println("出错了");
                    }
                }
            }
            if (list.size() != queue.size) {
                System.out.println("出错了");
            }
            while (!list.isEmpty()) {
                if (!list.pollFirst().equals(queue.pollFirst())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void testMyStack() {
        for (int i = 0; i < 100_0000; i++) {
            Stack<Integer> stack = new Stack<>();
            MyStack myStack = new MyStack();
            int time = (int) (Math.random() * 100) + 1;
            for (int j = 0; j < time; j++) {
                double t = Math.random();
                if (t < 0.5) {
                    int value = (int) (Math.random() * 100);
                    stack.push(value);
                    myStack.push(value);
                } else if (t < 0.75) {
                    if (stack.size() > 0 && myStack.size > 0) {
                        if (!stack.pop().equals(myStack.pop())) {
                            System.out.println("出错了");
                        }
                    } else if (stack.size() != myStack.size) {
                        System.out.println("出错了");
                    }
                } else {
                    if (stack.size() > 0 && myStack.size > 0) {
                        if (!stack.peek().equals(myStack.peek())) {
                            System.out.println("出错了");
                        }
                    } else if (stack.size() != myStack.size) {
                        System.out.println("出错了");
                    }
                }
            }
            if (stack.size() != myStack.size) {
                System.out.println("出错了");
            }
            while (!stack.isEmpty()) {
                if (!stack.pop().equals(myStack.pop())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void testMyStrongStack() {
        for (int t = 0; t < 100_0000; t++) {
            MyStrongStack stack = new MyStrongStack();
            int[] num = ArrayUtils.generalArr(20);
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < num.length; i++) {
                stack.push(num[i]);
                max = Math.max(num[i], max);
                min = Math.min(num[i], min);
                if (stack.getMax() != max) {
                    System.out.println("错了");
                }
                if (stack.getMin() != min) {
                    System.out.println("错了");
                }
            }
            for (int i = num.length-1; i >= 0; i--) {
                max = getMax(num, i);
                min = getMin(num, i);
                if (stack.getMax() != max) {
                    System.out.println("错了");
                }
                if (stack.getMin() != min) {
                    System.out.println("错了");
                }
                stack.pop();
            }
        }
        System.out.println("成功");
    }

    public int getMax(int[] arr, int end) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i <= end; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }
    public int getMin(int[] arr, int end) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i <= end; i++) {
            min = Math.min(min, arr[i]);
        }
        return min;
    }

    public void testStackToQueue() {
        for (int time = 0; time < 100_0000; time++) {
            int[] num = ArrayUtils.generalArr(20);
            LinkedList<Integer> queue = new LinkedList<>();
            StackToQueue stackToQueue = new StackToQueue();
            for (int i = 0; i < num.length; i++) {
                if (Math.random() > 0.6) {
                    queue.add(num[i]);
                    stackToQueue.add(num[i]);
                } else {
                    if (queue.isEmpty()) {
                        continue;
                    }
                    if (!queue.poll().equals(stackToQueue.poll())) {
                        System.out.println("出错了");
                    }
                }
            }
            for (int i = 0; i < queue.size(); i++) {
                if (!queue.poll().equals(stackToQueue.poll())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void testQueueToStack() {
        for (int time = 0; time < 100_0000; time++) {
            Stack<Integer> stack = new Stack<>();
            QueueToStack queueToStack = new QueueToStack();
            int[] num = ArrayUtils.generalArr(20);
            for (int i = 0; i < num.length; i++) {
                if (Math.random() > 0.6) {
                    stack.push(num[i]);
                    queueToStack.push(num[i]);
                } else {
                    if (stack.isEmpty()) {
                        continue;
                    }
                    if (!stack.pop().equals(queueToStack.pop())) {
                        System.out.println("出错了");
                    }
                }
            }
            for (int i = 0; i < queueToStack.size; i++) {
                if (!stack.pop().equals(queueToStack.pop())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

    public void testMyArrayToQueue() {
        for (int time = 0; time < 100_0000; time++) {
            int[] num = ArrayUtils.generalArr(50);
            LinkedList<Integer> queue = new LinkedList<>();
            MyArrayQueue arrayQueue = new MyArrayQueue(50);
            for (int i = 0; i < num.length; i++) {
                if (Math.random() > 0.6) {
                    queue.add(num[i]);
                    arrayQueue.add(num[i]);
                } else {
                    if (queue.isEmpty()) {
                        continue;
                    }
                    if (!queue.poll().equals(arrayQueue.poll())) {
                        System.out.println("出错了");
                    }
                }
            }
            for (int i = 0; i < queue.size(); i++) {
                if (!queue.poll().equals(arrayQueue.poll())) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("成功");
    }

}
