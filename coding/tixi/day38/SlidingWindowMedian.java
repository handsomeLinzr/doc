package coding.tixi.day38;

import java.security.Key;
import java.util.Comparator;

/**
 *
 * 有一个窗口，L 是最左，R是最右
 * 每个时刻都可能R往右，表示某个数进入窗口
 * 每个时刻都可能L往右，表示某个数出了窗口
 * 想知道每个窗口状态的中位数
 *
 * @author linzherong
 * @date 2025/7/14 01:35
 */
public class SlidingWindowMedian {

    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return null;
        }
        int length = nums.length;
        double[] ans = new double[length-k+1];
        SBTTable<Node> map = new SBTTable<>();
        // 先放入k之前的所有数据
        for (int i = 0; i < k-1; i++) {
            map.add(new Node(i, nums[i]));
        }
        int index = 0;
        for (int i = k-1; i < nums.length; i++) {
            // 放入表
            map.add(new Node(i, nums[i]));
            if ((map.size & 1) == 0) {
                double up = map.getIndexKey(map.size/2-1).value;
                double down = map.getIndexKey(map.size/2).value;
                ans[index++] = (up + down)/2;
            } else {
                ans[index++] = map.getIndexKey(map.size/2).value;
            }
            map.remove(new Node(i-k+1, nums[i-k+1]));
        }
        return ans;
    }

    public static class Node implements Comparable<Node> {
        private int index;
        private int value;
        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }
        @Override
        public int compareTo(Node o) {
            return value != o.value? value - o.value:index - o.index;
        }
    }

    public static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> left;
        public SBTNode<K> right;
        public int size;

        public SBTNode(K key) {
            this.key = key;
            this.size = 1;
        }
    }

    public static class SBTTable<K extends Comparable<K>> {
        public SBTNode<K> root;
        public int size;

        public int size() {
            return size;
        }

        // 增加
        public void add(K key) {
            size++;
            if (root == null) {
                root = new SBTNode<>(key);
            } else {
                root = add(root, key);
            }
        }

        // 移除
        public void remove(K key) {
            if (!contains(key)) {
                return;
            }
            size--;
            root = remove(root, key);
        }

        // 根据index获取
        public K getIndexKey(int index) {
            if (size <= index) {
                return null;
            }
            return getIndexKey(root, index+1).key;
        }

        // 左旋
        private SBTNode<K> leftRotate(SBTNode<K> node) {
            SBTNode<K> right = node.right;
            node.right = right.left;
            right.left = node;
            right.size = node.size;
            node.size = (node.left == null?0:node.left.size) + (node.right == null?0:node.right.size) + 1;
            return right;
        }
        // 右旋
        private SBTNode<K> rightRotate(SBTNode<K> node) {
            SBTNode<K> left = node.left;
            node.left = left.right;
            left.right = node;
            left.size = node.size;
            node.size = (node.left == null?0:node.left.size) + (node.right == null?0:node.right.size) + 1;
            return left;
        }
        // 调整
        private SBTNode<K> maintain(SBTNode<K> node) {
            int L = node.left == null?0:node.left.size;
            int R = node.right == null?0:node.right.size;
            int LL = node.left==null||node.left.left==null?0:node.left.left.size;
            int LR = node.left==null||node.left.right==null?0:node.left.right.size;
            int RL = node.right==null||node.right.left==null?0:node.right.left.size;
            int RR = node.right==null||node.right.right==null?0:node.right.right.size;
            if (LL > R) {
                node = rightRotate(node);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (LR > R) {
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (RL > L) {
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (RR > L) {
                node = leftRotate(node);
                node.left = maintain(node.left);
                node = maintain(node);
            }
            return node;
        }
        private SBTNode<K> add(SBTNode<K> node, K key) {
            if (node == null) {
                return new SBTNode<>(key);
            }
            node.size++;
            if (key.compareTo(node.key) < 0) {
                node.left = add(node.left, key);
            } else {
                node.right = add(node.right, key);
            }
            return maintain(node);
        }
        // 是否包含
        public boolean contains(K key) {
            if (root == null) {
                return false;
            }
            SBTNode<K> lastNode = findLastIndex(key);
            return lastNode != null && lastNode.key.compareTo(key) == 0;
        }
        // 获取最近一个
        public SBTNode<K> findLastIndex(K key) {
            SBTNode<K> ans = root;
            SBTNode<K> cur = root;
            while (cur != null) {
                ans = cur;
                int i = key.compareTo(ans.key);
                if (i == 0) {
                    break;
                } else if (i > 0) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return ans;
        }
        private SBTNode<K> remove(SBTNode<K> node, K key) {
            node.size--;
            int i = key.compareTo(node.key);
            if (i > 0) {
                node.right = remove(node.right, key);
            } else if (i < 0) {
                node.left = remove(node.left, key);
            } else {
                if (node.left == null && node.right == null) {
                    return null;
                } else if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    SBTNode<K> right = node.right;
                    SBTNode<K> pre = null;
                    while (right.left != null) {
                        right.size--;
                        pre = right;
                        right = right.left;
                    }
                    if (pre != null) {
                        pre.left = right.right;
                        pre.size = (pre.left == null?0:pre.left.size)+(pre.right == null?0:pre.right.size) + 1;
                        right.right = node.right;
                    }
                    // BST 移除不需要调整maintain
                    right.left = node.left;
                    right.size = right.left.size + (right.right == null ?0:right.right.size) + 1;
                    node = right;
                }
            }
            return node;
        }
        // 根据kth（位置，1开始）获取数据
        private SBTNode<K> getIndexKey(SBTNode<K> node, int kth) {
            int curKth = node.left == null?1:node.left.size+1;
            if (curKth == kth) {
                return node;
            }
            if (kth > curKth) {
                return getIndexKey(node.right, kth - curKth);
            } else {
                return getIndexKey(node.left, kth);
            }
        }
    }

}
