package coding.tixi.day36;

import java.util.ArrayList;

/**
 *
 * 跳表
 *
 * @author linzherong
 * @date 2025/7/12 22:02
 */
public class Table_SkipListMap {

    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        // 每一层的下一个数据
        public ArrayList<SkipListNode<K, V>> nextNodes;
        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.nextNodes = new ArrayList<>();
        }

        // key的比较  是否比other小
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }
        // 是否和key相等
        public boolean isKeyEqual(K otherKey) {
            if (key == null && otherKey == null) {
                return true;
            }
            return key != null && otherKey != null && key.compareTo(otherKey) == 0;
        }

    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        public static final double PROBABILITY = 0.5; //  < 0.5 继续做， >= 0.5 停
        private SkipListNode<K, V> head;  // 跳表头节点，初始化为null
        private int size;
        private int maxLevel;
        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);  // 0
            size = 0;
            maxLevel = 0;
        }

        // 从最高层开始，一路搜索
        // 最终，找到第0层的 < key 的最右的节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        // 在 level 层里，如何往右移动
        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到 < key的最后一个节点
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> nextNode = cur.nextNodes.get(level);
            while (nextNode != null && nextNode.isKeyLess(key)) {
                cur = nextNode;
                nextNode = nextNode.nextNodes.get(level);
            }
            return cur;
        }

        // 是否包含
        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            // 获取小于key的最后一个
            SkipListNode<K, V> node = mostRightLessNodeInTree(key);
            // 获取这个在0层的下一个
            SkipListNode<K, V> cur = node.nextNodes.get(0);
            return cur != null && cur.isKeyEqual(key);
        }

        // 新增、修改
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            SkipListNode<K, V> node = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = node.nextNodes.get(0);
            if (next != null && next.key.compareTo(key) == 0) {
                // 替换
                next.value = value;
            } else {
                // 新增
                size++;
                int nodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    nodeLevel++;
                }
                while (nodeLevel > maxLevel) {
                    // 拔高最大层级，给每层设置head的下一个节点指向null
                    head.nextNodes.add(null);
                    maxLevel++;
                }
                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= nodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }
                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    if (level <= nodeLevel) {
                        // level <= nodeLevel，则是命中的newNode需要记录的层级范围
                        newNode.nextNodes.set(level, cur.nextNodes.get(level));
                        cur.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        // 获取
        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> node = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = node.nextNodes.get(0);
            return next != null && next.isKeyEqual(key)? next.value:null;
        }

        // 删除
        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> node = head;
                SkipListNode<K, V> next = null;
                while (level >= 0) {
                    // 当前层下，< key 的最近一个
                    node = mostRightLessNodeInLevel(key, node, level);
                    // node 的下一个
                    next = node.nextNodes.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        // 同一level，前边的节点next指向node的next节点
                        node.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    if (level != 0 && node == head && head.nextNodes.get(level) == null) {
                        // 当前level上没有节点了，移除
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        // 第一个key
        public K firstKey() {
            return head.nextNodes.get(0) != null? head.nextNodes.get(0).key:null;
        }

        // 最后一个key
        public K lastKey() {
            // 最高层
            int level = maxLevel;
            SkipListNode<K, V> node = head;
            while (level >= 0) {
                while (node.nextNodes.get(level) != null) {
                    node = node.nextNodes.get(level);
                }
                level--;
            }
            return node.key;
        }

        // 向下取数
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> node = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = node.nextNodes.get(0);
            return next == null? null:next.key;
        }

        // 向上取数
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> node = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = node.nextNodes.get(0);
            return next != null && next.isKeyEqual(key)? next.key:node.key;
        }

        public int size() {
            return size;
        }

    }


    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.value + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));


    }

}
