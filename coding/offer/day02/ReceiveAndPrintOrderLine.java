package coding.offer.day02;

import java.util.HashMap;

/**
 *
 * 按顺序打印
 * 用大小两个map表，将节点首尾相连
 * 需要按顺序进行消息传递，如：
 * 来了1，传1
 * 来了2，传2
 * 来了4，存着，
 * 来了5，存着
 * 来了3，传3，4,5
 *
 * @author linzherong
 * @date 2025/7/13 20:34
 */
public class ReceiveAndPrintOrderLine {

    public static class Node {
        String info;
        Node next;
        public Node(String info) {
            this.info = info;
            next = null;
        }
    }

    public static class MessageBox1 {

        int waitPoint = 1;
        HashMap<Integer, Node> headMap = new HashMap<>();
        HashMap<Integer, Node> tailMap = new HashMap<>();

        public void receive(int num, String info) {
            if (num < 1) {
                return;
            }
            Node node = new Node(info);
            // 放到头尾
            headMap.put(num, node);
            tailMap.put(num, node);
            // 后续首尾处理
            if (tailMap.containsKey(num-1)) {
                tailMap.get(num-1).next = node;
                tailMap.remove(num-1);
                headMap.remove(num);
            }
            if (headMap.containsKey(num+1)) {
                // 有 num+1 对应的头结点
                // 接到当前节点后
                node.next = headMap.get(num+1);
                tailMap.remove(num);
                headMap.remove(num+1);
            }
            doPull();
        }

        public void doPull() {
            if (!headMap.containsKey(waitPoint)) {
                return;
            }
            Node node = headMap.get(waitPoint);
            headMap.remove(waitPoint);
            while (node != null) {
                System.out.println("push->>" + node.info + " ");
                node = node.next;
                waitPoint++;
            }
            tailMap.remove(waitPoint-1);
        }
    }

    public static void main(String[] args) {
        MessageBox1 box = new MessageBox1();
        // 1....
        System.out.println("这是2来到的时候");
        box.receive(2,"B"); // - 2"
        System.out.println("这是1来到的时候");
        box.receive(1,"A"); // 1 2 -> print, trigger is 1
        box.receive(4,"D"); // - 4
        box.receive(5,"E"); // - 4 5
        box.receive(7,"G"); // - 4 5 - 7
        box.receive(8,"H"); // - 4 5 - 7 8
        box.receive(6,"F"); // - 4 5 6 7 8
        box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(9,"I"); // 9 -> print, trigger is 9
        box.receive(10,"J"); // 10 -> print, trigger is 10
        box.receive(12,"L"); // - 12
        box.receive(13,"M"); // - 12 13
        box.receive(11,"K"); // 11 12 13 -> print, trigger is 11
    }

}
