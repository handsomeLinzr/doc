package coding.tixi.day36.test;

import coding.tixi.day36.Table_AVLTreeMap;

import java.util.TreeMap;
import java.util.Random;

/**
 * AVL树测试对数器
 * 
 * @author linzherong
 * @date 2025/6/29 20:00
 */
public class Table_AVLTreeMapTest {
    
    public static void main(String[] args) {
        System.out.println("开始测试AVL树...");
        
        // 测试基本功能
        testBasicOperations();
        
        // 测试边界情况
        testEdgeCases();
        
        // 测试大量数据
        testLargeData();
        
        // 性能对比测试
        performanceTest();
        
        System.out.println("所有测试完成！");
    }
    
    /**
     * 测试基本操作
     */
    public static void testBasicOperations() {
        System.out.println("\n=== 测试基本操作 ===");
        
        Table_AVLTreeMap.AVLTreeMap<Integer, String> avl = new Table_AVLTreeMap.AVLTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        // 测试插入
        System.out.println("测试插入操作...");
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85};
        String[] values = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        
        for (int i = 0; i < keys.length; i++) {
            avl.put(keys[i], values[i]);
            treeMap.put(keys[i], values[i]);
            
            // 验证大小
            if (avl.size() != treeMap.size()) {
                System.out.println("错误：大小不匹配！AVL: " + avl.size() + ", TreeMap: " + treeMap.size());
                return;
            }
        }
        System.out.println("插入测试通过，大小: " + avl.size());
        
        // 测试查找
        System.out.println("测试查找操作...");
        for (int key : keys) {
            String avlValue = avl.get(key);
            String treeMapValue = treeMap.get(key);
            if (!avlValue.equals(treeMapValue)) {
                System.out.println("错误：查找结果不匹配！key: " + key + ", AVL: " + avlValue + ", TreeMap: " + treeMapValue);
                return;
            }
        }
        System.out.println("查找测试通过");
        
        // 测试containsKey
        System.out.println("测试containsKey操作...");
        for (int key : keys) {
            if (avl.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：containsKey结果不匹配！key: " + key);
                return;
            }
        }
        // 测试不存在的key
        if (avl.containsKey(999) != treeMap.containsKey(999)) {
            System.out.println("错误：不存在的key测试失败！");
            return;
        }
        System.out.println("containsKey测试通过");
        
        // 测试firstKey和lastKey
        System.out.println("测试firstKey和lastKey...");
        if (!avl.firstKey().equals(treeMap.firstKey())) {
            System.out.println("错误：firstKey不匹配！AVL: " + avl.firstKey() + ", TreeMap: " + treeMap.firstKey());
            return;
        }
        if (!avl.lastKey().equals(treeMap.lastKey())) {
            System.out.println("错误：lastKey不匹配！AVL: " + avl.lastKey() + ", TreeMap: " + treeMap.lastKey());
            return;
        }
        System.out.println("firstKey和lastKey测试通过");
        
        // 测试floorKey和ceilingKey
        System.out.println("测试floorKey和ceilingKey...");
        int[] testKeys = {15, 25, 35, 50, 65, 90};
        for (int key : testKeys) {
            Integer avlFloor = avl.floorKey(key);
            Integer treeMapFloor = treeMap.floorKey(key);
            if ((avlFloor == null && treeMapFloor != null) || 
                (avlFloor != null && !avlFloor.equals(treeMapFloor))) {
                System.out.println("错误：floorKey不匹配！key: " + key + ", AVL: " + avlFloor + ", TreeMap: " + treeMapFloor);
                return;
            }
            
            Integer avlCeiling = avl.ceilingKey(key);
            Integer treeMapCeiling = treeMap.ceilingKey(key);
            if ((avlCeiling == null && treeMapCeiling != null) || 
                (avlCeiling != null && !avlCeiling.equals(treeMapCeiling))) {
                System.out.println("错误：ceilingKey不匹配！key: " + key + ", AVL: " + avlCeiling + ", TreeMap: " + treeMapCeiling);
                return;
            }
        }
        System.out.println("floorKey和ceilingKey测试通过");
        
        // 测试删除
        System.out.println("测试删除操作...");
        int[] deleteKeys = {30, 70, 50, 20, 80};
        for (int key : deleteKeys) {
            avl.remove(key);
            treeMap.remove(key);
            
            if (avl.size() != treeMap.size()) {
                System.out.println("错误：删除后大小不匹配！AVL: " + avl.size() + ", TreeMap: " + treeMap.size());
                return;
            }
            
            if (avl.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：删除后containsKey结果不匹配！key: " + key);
                return;
            }
        }
        System.out.println("删除测试通过，剩余大小: " + avl.size());
        
        System.out.println("基本操作测试全部通过！");
    }
    
    /**
     * 测试边界情况
     */
    public static void testEdgeCases() {
        System.out.println("\n=== 测试边界情况 ===");
        
        Table_AVLTreeMap.AVLTreeMap<Integer, String> avl = new Table_AVLTreeMap.AVLTreeMap<>();
        
        // 测试空树
        System.out.println("测试空树...");
        if (avl.size() != 0) {
            System.out.println("错误：空树大小不为0！");
            return;
        }
        if (avl.firstKey() != null) {
            System.out.println("错误：空树firstKey不为null！");
            return;
        }
        if (avl.lastKey() != null) {
            System.out.println("错误：空树lastKey不为null！");
            return;
        }
        if (avl.get(1) != null) {
            System.out.println("错误：空树get不为null！");
            return;
        }
        System.out.println("空树测试通过");
        
        // 测试null key
        System.out.println("测试null key...");
        avl.put(null, "test");
        if (avl.size() != 0) {
            System.out.println("错误：null key应该被忽略！");
            return;
        }
        avl.remove(null);
        if (avl.size() != 0) {
            System.out.println("错误：删除null key后大小应该为0！");
            return;
        }
        System.out.println("null key测试通过");
        
        // 测试单个节点
        System.out.println("测试单个节点...");
        avl.put(1, "single");
        if (avl.size() != 1) {
            System.out.println("错误：单个节点大小不为1！");
            return;
        }
        if (!avl.firstKey().equals(avl.lastKey())) {
            System.out.println("错误：单个节点的firstKey和lastKey应该相等！");
            return;
        }
        System.out.println("单个节点测试通过");
        
        // 测试重复key
        System.out.println("测试重复key...");
        avl.put(1, "updated");
        if (avl.size() != 1) {
            System.out.println("错误：重复key不应该增加大小！");
            return;
        }
        if (!avl.get(1).equals("updated")) {
            System.out.println("错误：重复key应该更新值！");
            return;
        }
        System.out.println("重复key测试通过");
        
        System.out.println("边界情况测试全部通过！");
    }
    
    /**
     * 测试大量数据
     */
    public static void testLargeData() {
        System.out.println("\n=== 测试大量数据 ===");
        
        Table_AVLTreeMap.AVLTreeMap<Integer, String> avl = new Table_AVLTreeMap.AVLTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        Random random = new Random(42); // 固定种子保证可重复
        int testSize = 10000;
        
        System.out.println("插入 " + testSize + " 个随机数据...");
        
        // 插入随机数据
        for (int i = 0; i < testSize; i++) {
            int key = random.nextInt(100000);
            String value = "value_" + key;
            
            avl.put(key, value);
            treeMap.put(key, value);
            
            if (i % 1000 == 0) {
                System.out.println("已插入 " + i + " 个数据，当前大小: " + avl.size());
            }
        }
        
        System.out.println("插入完成，最终大小: " + avl.size());
        
        // 验证大小
        if (avl.size() != treeMap.size()) {
            System.out.println("错误：大量数据测试中大小不匹配！");
            return;
        }
        
        // 随机查找测试
        System.out.println("进行随机查找测试...");
        int searchCount = 1000;
        for (int i = 0; i < searchCount; i++) {
            int key = random.nextInt(100000);
            String avlValue = avl.get(key);
            String treeMapValue = treeMap.get(key);
            
            if ((avlValue == null && treeMapValue != null) || 
                (avlValue != null && !avlValue.equals(treeMapValue))) {
                System.out.println("错误：随机查找结果不匹配！key: " + key);
                return;
            }
        }
        System.out.println("随机查找测试通过");
        
        // 随机删除测试
        System.out.println("进行随机删除测试...");
        int deleteCount = 1000;
        for (int i = 0; i < deleteCount; i++) {
            int key = random.nextInt(100000);
            avl.remove(key);
            treeMap.remove(key);
            
            if (avl.size() != treeMap.size()) {
                System.out.println("错误：随机删除后大小不匹配！");
                return;
            }
        }
        System.out.println("随机删除测试通过，剩余大小: " + avl.size());
        
        System.out.println("大量数据测试通过！");
    }
    
    /**
     * 性能对比测试
     */
    public static void performanceTest() {
        System.out.println("\n=== 性能对比测试 ===");
        
        Table_AVLTreeMap.AVLTreeMap<Integer, String> avl = new Table_AVLTreeMap.AVLTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        Random random = new Random(42);
        int testSize = 100000;
        
        // 准备测试数据
        int[] testKeys = new int[testSize];
        String[] testValues = new String[testSize];
        for (int i = 0; i < testSize; i++) {
            testKeys[i] = random.nextInt(1000000);
            testValues[i] = "value_" + testKeys[i];
        }
        
        // AVL树插入性能测试
        System.out.println("测试AVL树插入性能...");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            avl.put(testKeys[i], testValues[i]);
        }
        long avlInsertTime = System.currentTimeMillis() - startTime;
        System.out.println("AVL树插入 " + testSize + " 个数据耗时: " + avlInsertTime + "ms");
        
        // TreeMap插入性能测试
        System.out.println("测试TreeMap插入性能...");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            treeMap.put(testKeys[i], testValues[i]);
        }
        long treeMapInsertTime = System.currentTimeMillis() - startTime;
        System.out.println("TreeMap插入 " + testSize + " 个数据耗时: " + treeMapInsertTime + "ms");
        
        // 查找性能测试
        System.out.println("测试查找性能...");
        int searchCount = 10000;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < searchCount; i++) {
            avl.get(testKeys[random.nextInt(testSize)]);
        }
        long avlSearchTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < searchCount; i++) {
            treeMap.get(testKeys[random.nextInt(testSize)]);
        }
        long treeMapSearchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("AVL树查找 " + searchCount + " 次耗时: " + avlSearchTime + "ms");
        System.out.println("TreeMap查找 " + searchCount + " 次耗时: " + treeMapSearchTime + "ms");
        
        // 性能对比
        System.out.println("\n性能对比结果:");
        System.out.println("插入性能比: AVL树/TreeMap = " + String.format("%.2f", (double)avlInsertTime/treeMapInsertTime));
        System.out.println("查找性能比: AVL树/TreeMap = " + String.format("%.2f", (double)avlSearchTime/treeMapSearchTime));
        
        System.out.println("性能测试完成！");
    }
    
    /**
     * 打印AVL树结构（用于调试）
     */
    public static void printAVLTree(Table_AVLTreeMap.AVLNode<Integer, String> node, String prefix, boolean isLeft) {
        if (node == null) {
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.key + "(" + node.h + ")");
        printAVLTree(node.l, prefix + (isLeft ? "│   " : "    "), true);
        printAVLTree(node.r, prefix + (isLeft ? "│   " : "    "), false);
    }
} 