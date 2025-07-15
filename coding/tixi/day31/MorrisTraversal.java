package coding.tixi.day31;

/**
 * @author linzherong
 * @date 2025/6/23 13:16
 */
public class MorrisTraversal {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node(int value) {
            this.value = value;
        }
    }

    public static void process(Node root) {
        if (root == null) {
            return;
        }
        // 处理左节点
        process(root.left);
        // 处理右节点
        process(root.right);
    }

    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node rightest;
        while (cur != null) {
            System.out.println(cur.value);
            if (cur.left != null) {
                rightest = cur.left;
                // 获取左边的最右子节点
                // 过程遍历中如果遍历回自己，则说明左子树的最右节点已经被指向了当前节点
                while (rightest.right != null && rightest.right != cur) {
                    rightest = rightest.right;
                }
                if (rightest.right == null) {
                    // 最右指向当前节点
                    rightest.right = cur;
                    // cur节点结束，指向左节点
                    cur = cur.left;
                } else {
                    rightest.right = null;
                }
            }
            cur = cur.right;
        }
    }

    // 先序遍历
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node rightest;
        while (cur != null) {
            if (cur.left != null) {
                rightest = cur.left;
                while (rightest.right != null && rightest.right != cur) {
                    rightest = rightest.right;
                }
                if (rightest.right == null) {
                    rightest.right = cur;
                    // 即将 cur = cur.left，进行打印
                    System.out.println(cur.value);
                    cur = cur.left;
                    continue;
                } else {
                    rightest.right = null;
                }
            } else {
                // 没有左节点的时候打印
                System.out.println(cur.value);
            }
            cur = cur.right;
        }
    }

    // 中序
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node rightest;
        while (cur != null) {
            if (cur.left != null) {
                rightest = cur.left;
                while (rightest.right != null && rightest.right != cur) {
                    rightest = rightest.right;
                }
                if (rightest.right == null) {
                    rightest.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightest.right = null;
                }
            }
            System.out.println(cur.value);
            cur = cur.right;
        }
    }

    // 是否搜索二叉树，左>中>右
    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node rightest;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            if (cur.left != null) {
                rightest = cur.left;
                while (rightest.right != null && rightest.right != cur) {
                    rightest = rightest.right;
                }
                if (rightest.right == null) {
                    rightest.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightest.right = null;
                }
            }
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }

    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }


    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        morrisIn(head);
        System.out.println("=======================");
        morrisPre(head);
        System.out.println(isBST2(head));
        System.out.println(isBST(head));
    }

}
