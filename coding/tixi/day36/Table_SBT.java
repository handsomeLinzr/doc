package coding.tixi.day36;

/**
 *
 * 自平衡二叉搜索树
 *
 * @author linzherong
 * @date 2025/7/12 21:34
 */
public class Table_SBT {

    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public SBTNode<K, V> left;
        public SBTNode<K, V> right;
        public int size;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            size = 1;
        }
    }

    public static class SizeBalanceTreeMap<K extends Comparable<K>, V> {
        public SBTNode<K, V> root;
        public int size;
        // 左旋
        public SBTNode<K, V> leftRotate(SBTNode<K, V> node) {
            if (node == null) {
                return null;
            }
            SBTNode<K, V> right = node.right;
            node.right = right.left;
            right.left = node;
            right.size = node.size;
            node.size = (node.left == null?0:node.left.size) + (node.right == null?0:node.right.size) + 1;
            return right;
        }

        // 右璇
        public SBTNode<K, V> rightRotate(SBTNode<K, V> node) {
            if (node == null) {
                return null;
            }
            SBTNode<K, V> left = node.left;
            node.left = left.right;
            left.right = node;
            left.size = node.size;
            node.size = (node.left == null? 0 : node.left.size) + (node.right == null? 0 : node.right.size) + 1;
            return left;
        }

        // 调整
        public SBTNode<K, V> maintain(SBTNode<K, V> node) {
            if (node == null) {
                return null;
            }
            int leftSize = node.left == null?0:node.left.size;
            int rightSize = node.right == null?0:node.right.size;
            int leftLeftSize = node.left == null || node.left.left == null? 0 : node.left.left.size;
            int leftRightSize = node.left == null || node.left.right == null? 0:node.left.right.size;
            int rightLeftSize = node.right == null || node.right.left == null? 0 : node.right.left.size;
            int rightRightSize = node.right == null || node.right.right == null? 0:node.right.right.size;
            if (leftLeftSize > rightSize) {
                // LL
                node = rightRotate(node);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (leftRightSize > rightSize) {
                // LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightLeftSize > leftSize) {
                // RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightRightSize > leftSize) {
                // RR
                node = leftRotate(node);
                node.left = maintain(node.left);
                node = maintain(node);
            }
            return node;
        }

        // 获取最近一个
        public SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> pre = root;
            while (cur != null) {
                int compare = key.compareTo(cur.key);
                if (compare == 0) {
                    pre = cur;
                    break;
                } else if (compare > 0) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return pre;
        }

        // 获取大于等于的最近一个
        public SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;
            while (cur != null) {
                int compare = key.compareTo(cur.key);
                if (compare == 0) {
                    ans = cur;
                    break;
                } else if (compare > 0) {
                    cur = cur.right;
                } else {
                    ans = cur;
                    cur = cur.left;
                }
            }
            return ans;
        }

        // 获取小于等于的最近一个
        public SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                int compare = cur.key.compareTo(key);
                if (compare == 0) {
                    ans = cur;
                    break;
                } else if (compare > 0) {
                    cur = cur.left;
                } else {
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }

        // 新增
        public SBTNode<K, V> add(SBTNode<K, V> node, K key, V value) {
            if (node == null) {
                return new SBTNode<>(key, value);
            }
            node.size++;
            int compare = node.key.compareTo(key);
            if (compare > 0) {
                node.left = add(node.left, key, value);
            } else {
                node.right = add(node.right, key, value);
            }
            return maintain(node);
        }

        // 删除
        public SBTNode<K, V> delete(SBTNode<K, V> node, K key) {
            node.size--;
            int compare = node.key.compareTo(key);
            if (compare > 0) {
                node.left = delete(node.left, key);
            } else if (compare < 0) {
                node.right = delete(node.right, key);
            } else {
                // 删除node节点
                if (node.left == null && node.right == null) {
                    node = null;
                } else if (node.left == null) {
                    node = node.right;
                } else if (node.right == null) {
                    node = node.left;
                } else {
//                    SBTNode<K, V> l = node.left;
//                    // 左子树数量减1
//                    l.size--;
//                    // 左子节点的最右
//                    SBTNode<K, V> rightest = l;
//                    // 左子树最右的父节点
//                    SBTNode<K, V> rightestParent = null;
//                    while (rightest.right != null) {
//                        rightestParent = rightest;
//                        rightest = rightest.right;
//                        // 数量每层都减1
//                        rightest.size--;
//                    }
//                    if (rightestParent != null) {
//                        rightestParent.right = rightest.left;
//                        rightest.left = node.left;
//                    }
//                    rightest.right = node.right;
//                    rightest.size = (rightest.left == null?0:rightest.left.size) + rightest.right.size + 1;
//                    node = rightest;


                    SBTNode<K, V> l = node.left;
                    // 左子节点的最右
                    SBTNode<K, V> rightest = l;
                    while (rightest.right != null) {
                        rightest = rightest.right;
                    }

                    l = delete(l, rightest.key);
                    rightest.left = l;
                    rightest.right = node.right;
                    rightest.size = (rightest.left == null?0:rightest.left.size) + rightest.right.size + 1;
                    node = rightest;
                }
            }
            return node;
        }

        // 根据位置获取数据
        public SBTNode<K, V> getIndex(SBTNode<K, V> node, int kth) {
            int leftSize = node.left == null?0:node.left.size;
            if (leftSize+1 == kth) {
                return node;
            } else if (leftSize >= kth) {
                return getIndex(node.left, kth);
            } else {
                int rightRest = kth - leftSize - 1;
                return getIndex(node.right, rightRest);
            }
        }

        // 节点数
        public int size() {
            return size;
        }

        // 是否存在
        public boolean containsKey(K key) {
            SBTNode<K, V> lastIndex = findLastIndex(key);
            return lastIndex != null && lastIndex.key.compareTo(key) == 0;
        }

        // 设置
        public void put(K key, V value) {
            SBTNode<K, V> lastIndex = findLastIndex(key);
            if (lastIndex != null && lastIndex.key.compareTo(key) == 0) {
                // 直接替换
                lastIndex.value = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        // 移除
        public void remove(K key) {
            if (!containsKey(key)) {
                return;
            }
            size--;
            root = delete(root, key);
        }

        // 获取第index位key
        public K getIndexKey(int index) {
            if (index < 0 || index >= size) {
                return null;
            }
            return getIndex(root, index+1).key;
        }

        // 获取第index位value
        public V getIndexValue(int index) {
            if (index < 0 || index >= size) {
                return null;
            }
            return getIndex(root, index+1).value;
        }

        // 获取节点key
        public V get(K key) {
            SBTNode<K, V> lastIndex = findLastIndex(key);
            if (lastIndex != null && lastIndex.key.compareTo(key) == 0) {
                return lastIndex.value;
            }
            return null;
        }

        // 获取第一个key
        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        // 获取最后一个key
        public K lastKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        // 向上取整获取Key
        public K floorKey(K key) {
            SBTNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
            if (lastNoBigIndex != null) {
                return lastNoBigIndex.key;
            }
            return null;
        }

        // 向下取整获取Key
        public K ceilingKey(K key) {
            SBTNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex == null? null : lastNoSmallIndex.key;
        }
    }


    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + "(" + head.key + "," + head.value + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SizeBalanceTreeMap<String, Integer> sbt = new SizeBalanceTreeMap<>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }



}
