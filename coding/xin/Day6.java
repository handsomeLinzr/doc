package coding.xin;

import java.util.*;

/**
 * Student(id, name) 比较器  Arrays.sort(,)  arrayList.sort()
 * PriorityQueue 小根堆
 * leetcode 23
 * 二叉树  先序  中序  后序
 * 相同树  sameTree  leetcode 100
 * 镜面树  isSymmetric 101
 * 最大深度   maxDepth   104
 * 根据先序+中序构建树   105   buildTree
 * @author linzherong
 * @date 2025/3/20 22:14
 */
public class Day6 {

    public static void main(String[] args) {
//        List<S> list = new ArrayList<>();
//        list.add(new S("wangwu", 3));
//        list.add(new S("zhangsan", 1));
//        list.add(new S("lisi", 2));
//        list.add(new S("zhaoliu", 4));
//        list.sort(Comparator.comparing(o -> o.name));
//        list.forEach(System.out::println);

//        PriorityQueue<S> priorityQueue = new PriorityQueue<>(Comparator.comparing(o -> o.name));
//        priorityQueue.add(new S("wangwu", 3));
//        priorityQueue.add(new S("zhangsan", 1));
//        priorityQueue.add(new S("lisi", 2));
//        priorityQueue.add(new S("zhaoliu", 4));
//        while (!priorityQueue.isEmpty()) {
//            System.out.println(priorityQueue.poll());
//        }

        Day6 day6 = new Day6();
//        ListNode[] lists = new ListNode[3];
//        lists[0] = NodeUtils.generalNode(new int[]{1,4,5});
//        lists[1] = NodeUtils.generalNode(new int[]{1,3,4});
//        lists[2] = NodeUtils.generalNode(new int[]{2,6});
//        ListNode listNode = day6.mergeKLists(lists);
//        NodeUtils.printLinkedNode(listNode);
//        int[][] arr = new int[3][7];
//        arr[0][0] = 1;
//        arr[1][0] = 2;
//        arr[1][1] = 3;
//        arr[2][0] = 4;
//        arr[2][1] = 5;
//        arr[2][2] = 6;
//        arr[2][3] = 7;
//        TreeNode treeNode = TreeNodeUtils.buildTreeNode(arr);
//        List<Integer> integers = day6.preorderTraversal(treeNode);
        int[] a = {1,2,3};
        int[] b = {3,2,1};
        TreeNode treeNode = day6.buildTree(a, b);
        System.out.println(treeNode);

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (Objects.isNull(lists) || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparing(l->l.value));
        for (ListNode list : lists) {
            if (Objects.isNull(list)) {
                continue;
            }
            priorityQueue.add(list);
        }
        if (priorityQueue.isEmpty()) {
            return null;
        }
        ListNode head = priorityQueue.poll();
        if (Objects.nonNull(head.next)) {
            priorityQueue.add(head.next);
        }
        ListNode cur = head;
        while (!priorityQueue.isEmpty()) {
            ListNode node = priorityQueue.poll();
            if (Objects.nonNull(node.next)) {
                priorityQueue.add(node.next);
            }
            cur.next = node;
            cur = node;
        }
        cur.next = null;
        return head;
    }

    /**
     * 先序
     */
    private List<Integer> preorderTraversal(TreeNode treeNode) {
        List<Integer> result = new ArrayList<>();
        if (Objects.isNull(treeNode)) {
            return null;
        }
        preorderRecord(result, treeNode);
        return result;
    }
    private void preorderRecord(List<Integer> list, TreeNode treeNode) {
        if (Objects.isNull(treeNode)) {
            return;
        }
        list.add(treeNode.val);
        preorderRecord(list, treeNode.left);
        preorderRecord(list, treeNode.right);
    }

    /**
     * 中序
     */
    private List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderProcess(result, root);
        return result;
    }
    private void inorderProcess(List<Integer> result, TreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        inorderProcess(result, node.left);
        result.add(node.val);
        inorderProcess(result, node.right);
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderProcess(result, root);
        return result;
    }
    public void postorderProcess(List<Integer> result, TreeNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        postorderProcess(result, node.left);
        postorderProcess(result, node.right);
        result.add(node.val);
    }

    /**
     * 相同树
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (Objects.isNull(p) && Objects.isNull(q)) {
            return true;
        }
        if (Objects.isNull(p) ^ Objects.isNull(q)) {
            return false;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public boolean isSymmetric(TreeNode root) {
        return isMirrorTree(root, root);
    }

    public boolean isMirrorTree(TreeNode left, TreeNode right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            return true;
        }
        if (Objects.isNull(left) ^ Objects.isNull(right)) {
            return false;
        }
        return left.val == right.val && isMirrorTree(left.left, right.right) && isMirrorTree(left.right, right.left);
    }

    public int maxDepth(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 先序 + 中序 构建树
     * 先序第一个节点是头结点，中序中头结点左边是左子树，右边是右子树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (Objects.isNull(preorder) || Objects.isNull(inorder) || preorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> helpMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            helpMap.put(inorder[i], i);
        }
        return build(helpMap, preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }


    public TreeNode build(Map<Integer, Integer> helpMap, int[] preorder, int[] inorder, int l1, int r1, int l2, int r2) {
        if (l1 > r1) {
            return null;
        }
        int val = preorder[l1];
        TreeNode node = new TreeNode(val);
        if (l1 == r1) {
            // 只有自己节点
            return node;
        }
        Integer inorderIndex = helpMap.get(val);
        // 左节点占用的数据长度
        node.left = build(helpMap, preorder, inorder, l1+1, l1+inorderIndex-l2, l2, inorderIndex-1);
        node.right = build(helpMap, preorder, inorder, l1+inorderIndex-l2+1, r1, inorderIndex+1, r2);
        return node;
    }

    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        if (Objects.isNull(postorder) || Objects.isNull(inorder) || postorder.length == 0 || postorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> helpMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            helpMap.put(inorder[i], i);
        }
        return build1(helpMap, inorder, postorder, 0, inorder.length-1, 0, postorder.length-1);
    }

    public TreeNode build1(Map<Integer, Integer> helpMap, int[] inorder, int[] postorder, int l1, int r1, int l2, int r2) {
        if (l2 > r2) {
            return null;
        }
        int val = postorder[r2];
        TreeNode node = new TreeNode(val);
        if (l2 == r2) {
            return node;
        }
        Integer index = helpMap.get(val);
        // 左节点占用的长度
        int leftLength = index - l1;
        node.left = build1(helpMap, inorder, postorder, l1, index-1, l2, l2+leftLength-1);
        node.right = build1(helpMap, inorder, postorder, index+1, r1, l2+leftLength, r2-1);
        return node;
    }


    static class S {
        private String name;
        private Integer age;
        public S(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "name:"+name + ";age:" + age;
        }
    }

}
