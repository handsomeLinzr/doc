package coding.tixi.day36;

/**
 *
 * AVL树，平衡
 *
 * @author linzherong
 * @date 2025/6/29 19:59
 */
public class Table_AVLTreeMap {
    // AVL树节点——平衡树
    public static class AVLNode<K extends Comparable<K>,V> {
        public K key;  // key
        public V v;    // value
        public AVLNode<K, V> l;   // 左节点
        public AVLNode<K, V> r;   // 右节点
        public int h;    // 高度
        public AVLNode(K key, V v) {
            this.key = key;
            this.v = v;
            this.h = 1;
        }
    }
    // 平衡树整体
    public static class AVLTreeMap<K extends Comparable<K>, V> {
        // 头结点
        public AVLNode<K,V> root;
        // 节点数
        public int size;
        public AVLTreeMap() {
            this.root = null;
            this.size = 0;
        }
        // 右旋
        public AVLNode<K, V> rightRotate(AVLNode<K,V> node) {
            // 左节点
            AVLNode<K, V> l = node.l;
            node.l = l.r;
            l.r = node;
            node.h = Math.max(node.l == null? 0:node.l.h, node.r == null?0:node.r.h) +1;
            l.h = Math.max(l.l == null?0:l.l.h, node.h) + 1;
            return l;
        }
        // 左旋
        public AVLNode<K, V> leftRotate(AVLNode<K,V> node) {
            AVLNode<K, V> right = node.r;
            node.r = right.l;
            right.l = node;
            // 重新计算高度
            node.h = Math.max(node.l != null? node.l.h:0, node.r != null? node.r.h:0) + 1;
            right.h = Math.max( right.l.h, right.r != null? right.r.h:0) + 1;
            return right;
        }
        // 调整
        public AVLNode<K, V> maintain(AVLNode<K, V> node) {
            if (node == null) {
                return null;
            }
            // 左右高度
            int leftH = node.l == null? 0 : node.l.h;
            int rightH = node.r == null? 0 : node.r.h;
            if (Math.abs(leftH - rightH) <= 1) {
                // 左右高度不大于1，已平衡
                return node;
            }
            if (leftH > rightH) {
                // 左
                AVLNode<K, V> l = node.l;
                int leftLeft = node.l != null && node.l.l == null? 0 : node.l.l.h;
                int leftRight = node.l != null && node.l.r == null? 0 : node.l.r.h;
                if (leftLeft > leftRight) {
                    // LL
                    node = rightRotate(node);
                } else {
                    // LR
                    node.l = leftRotate(node.l);
                    node = rightRotate(node);
                }
            } else {
                // 右
                AVLNode<K, V> r = node.r;
                int rightLeft = node.r != null && node.r.l == null? 0 : node.r.l.h;
                int rightRight = node.r != null && node.r.r == null? 0 : node.r.r.h;
                if (rightRight > rightLeft) {
                    // RR
                    node = leftRotate(node.r);
                } else {
                    // RL
                    node.r = rightRotate(node.r);
                    node = leftRotate(node);
                }
            }
            return node;
        }
        // 获取离key最近的最后一个
        public AVLNode<K, V> findLastIndex(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            AVLNode<K, V> pre = root;
            while (cur != null) {
                pre = cur;
                int compare = key.compareTo(cur.key);
                if (compare == 0) {
                    return cur;
                } else if (compare > 0) {
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }
            return pre;
        }
        public AVLNode<K, V> findLastNoSmallIndex(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K,V> cur = root;
            AVLNode<K,V> ans = null;
            while (cur != null) {
                int compare = key.compareTo(cur.key);
                if (compare == 0) {
                    ans = cur;
                    break;
                } else if (compare > 0) {
                    cur = cur.r;
                } else {
                    ans = cur;
                    cur = cur.l;
                }
            }
            return ans;
        }
        public AVLNode<K, V> findLastNoBigIndex(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            AVLNode<K, V> ans = null;
            while (cur != null) {
                int compare = key.compareTo(cur.key);
                if (compare == 0) {
                    ans = cur;
                    break;
                } else if (compare > 0) {
                    ans = cur;
                    cur = cur.r;
                } else {
                    cur = cur.l;
                }
            }
            return ans;
        }
        // 添加
        public AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AVLNode<>(key, value);
            }
            if (key.compareTo(cur.key) > 0) {
                // 右边
                cur.r = add(cur.r, key, value);
            } else if (key.compareTo(cur.key) < 0) {
                // 左边
                cur.l = add(cur.l, key, value);
            } else {
                // 改值
                cur.v = value;
                return cur;
            }
            // 设置长度
            cur.h = Math.max(cur.l == null? 0 : cur.l.h, cur.r == null? 0 : cur.r.h) + 1;
            // 调整
            return maintain(cur);
        }
        public AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key == null || cur == null) {
                return null;
            }
            int compare = key.compareTo(cur.key);
            if (compare > 0) {
                // key 大于 cur，从右边删除，返回右边的头结点
                cur.r = delete(cur.r, key);
            } else if (compare < 0) {
                cur.l = delete(cur.l, key);
            } else {
                // 相等，删除
                AVLNode<K, V> l = cur.l;
                AVLNode<K, V> r = cur.r;
                if (l == null && r == null) {
                    cur = null;
                } else if (l == null) {
                    cur = r;
                } else if (r == null) {
                    cur = l;
                } else {
                    // 拿左树的最右（也可以拿右树的最左）
                    AVLNode<K, V> rightest = l;
                    while (rightest.r != null) {
                        rightest = rightest.r;
                    }
                    l = delete(l, rightest.key);  // 不要直接用 =null，用 delete则可以包含了调整高度和删除后子节点的后续处理
                    rightest.l = l;
                    rightest.r = r;
                    cur = rightest;
                }
            }
            if (cur != null) {
                // 重新计算cur的高度
                cur.h = Math.max(cur.l == null?0:cur.l.h, cur.r==null?0:cur.r.h) + 1;
            }
            return maintain(cur);
        }

        public int size() {
            return size;
        }
        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> node = findLastIndex(key);
            return node != null && node.key.compareTo(key) == 0;
        }
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> node = findLastIndex(key);
            if (node != null && node.key.compareTo(key) == 0) {
                node.v = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }
        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                delete(root, key);
            }
        }
        public V get(K key) {
            AVLNode<K, V> node = findLastIndex(key);
            if (node != null && node.key.compareTo(key) == 0) {
                return node.v;
            }
            return null;
        }
        // 第一个
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K,V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.key;
        }
        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K,V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.key;
        }
        // 向上取值
        public K floorKey(K key) {
            if (key == null ) {
                return null;
            }
            AVLNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
            return lastNoBigIndex == null? null : lastNoBigIndex.key;
        }
        // 向下取值
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex == null? null : lastNoSmallIndex.key;
        }
    }

}
