package coding.tixi.day36.test;

import coding.tixi.day36.Table_SkipListMap;
import java.util.TreeMap;
import java.util.Random;

/**
 * 跳表测试对数器
 * 
 * @author linzherong
 * @date 2025/7/12 22:03
 */
public class Table_SkipListMapTest {
    
    public static void main(String[] args) {
        System.out.println("开始测试跳表...");
        
        // 测试基本功能
        testBasicOperations();
        
        // 测试边界情况
        testEdgeCases();
        
        // 测试大量数据
        testLargeData();
        
        // 测试跳表特性
        testSkipListFeatures();
        
        // 性能对比测试
        performanceTest();
        
        System.out.println("所有测试完成！");
    }
    
    /**
     * 测试基本操作
     */
    public static void testBasicOperations() {
        System.out.println("\n=== 测试基本操作 ===");
        
        Table_SkipListMap.SkipListMap<Integer, String> skipList = new Table_SkipListMap.SkipListMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        // 测试插入
        System.out.println("测试插入操作...");
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85};
        String[] values = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        
        for (int i = 0; i < keys.length; i++) {
            skipList.put(keys[i], values[i]);
            treeMap.put(keys[i], values[i]);
            
            // 验证大小
            if (skipList.size() != treeMap.size()) {
                System.out.println("错误：大小不匹配！SkipList: " + skipList.size() + ", TreeMap: " + treeMap.size());
                return;
            }
        }
        System.out.println("插入测试通过，大小: " + skipList.size());
        
        // 测试查找
        System.out.println("测试查找操作...");
        for (int key : keys) {
            String skipListValue = skipList.get(key);
            String treeMapValue = treeMap.get(key);
            if (!skipListValue.equals(treeMapValue)) {
                System.out.println("错误：查找结果不匹配！key: " + key + ", SkipList: " + skipListValue + ", TreeMap: " + treeMapValue);
                return;
            }
        }
        System.out.println("查找测试通过");
        
        // 测试containsKey
        System.out.println("测试containsKey操作...");
        for (int key : keys) {
            if (skipList.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：containsKey结果不匹配！key: " + key);
                return;
            }
        }
        // 测试不存在的key
        if (skipList.containsKey(999) != treeMap.containsKey(999)) {
            System.out.println("错误：不存在的key测试失败！");
            return;
        }
        System.out.println("containsKey测试通过");
        
        // 测试firstKey和lastKey
        System.out.println("测试firstKey和lastKey...");
        if (!skipList.firstKey().equals(treeMap.firstKey())) {
            System.out.println("错误：firstKey不匹配！SkipList: " + skipList.firstKey() + ", TreeMap: " + treeMap.firstKey());
            return;
        }
        if (!skipList.lastKey().equals(treeMap.lastKey())) {
            System.out.println("错误：lastKey不匹配！SkipList: " + skipList.lastKey() + ", TreeMap: " + treeMap.lastKey());
            return;
        }
        System.out.println("firstKey和lastKey测试通过");
        
        // 测试floorKey和ceilingKey
        System.out.println("测试floorKey和ceilingKey...");
        int[] testKeys = {15, 25, 35, 50, 65, 90};
        for (int key : testKeys) {
            Integer skipListFloor = skipList.floorKey(key);
            Integer treeMapFloor = treeMap.floorKey(key);
            if ((skipListFloor == null && treeMapFloor != null) || 
                (skipListFloor != null && !skipListFloor.equals(treeMapFloor))) {
                System.out.println("错误：floorKey不匹配！key: " + key + ", SkipList: " + skipListFloor + ", TreeMap: " + treeMapFloor);
                return;
            }
            
            Integer skipListCeiling = skipList.ceilingKey(key);
            Integer treeMapCeiling = treeMap.ceilingKey(key);
            if ((skipListCeiling == null && treeMapCeiling != null) || 
                (skipListCeiling != null && !skipListCeiling.equals(treeMapCeiling))) {
                System.out.println("错误：ceilingKey不匹配！key: " + key + ", SkipList: " + skipListCeiling + ", TreeMap: " + treeMapCeiling);
                return;
            }
        }
        System.out.println("floorKey和ceilingKey测试通过");
        
        // 测试删除
        System.out.println("测试删除操作...");
        int[] deleteKeys = {30, 70, 50, 20, 80};
        for (int key : deleteKeys) {
            skipList.remove(key);
            treeMap.remove(key);
            
            if (skipList.size() != treeMap.size()) {
                System.out.println("错误：删除后大小不匹配！SkipList: " + skipList.size() + ", TreeMap: " + treeMap.size());
                return;
            }
            
            if (skipList.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：删除后containsKey结果不匹配！key: " + key);
                return;
            }
        }
        System.out.println("删除测试通过，剩余大小: " + skipList.size());
        
        System.out.println("基本操作测试全部通过！");
    }
    
    /**
     * 测试边界情况
     */
    public static void testEdgeCases() {
        System.out.println("\n=== 测试边界情况 ===");
        
        Table_SkipListMap.SkipListMap<Integer, String> skipList = new Table_SkipListMap.SkipListMap<>();
        
        // 测试空跳表
        System.out.println("测试空跳表...");
        if (skipList.size() != 0) {
            System.out.println("错误：空跳表大小不为0！");
            return;
        }
        if (skipList.firstKey() != null) {
            System.out.println("错误：空跳表firstKey不为null！");
            return;
        }
        if (skipList.lastKey() != null) {
            System.out.println("错误：空跳表lastKey不为null！");
            return;
        }
        if (skipList.get(1) != null) {
            System.out.println("错误：空跳表get不为null！");
            return;
        }
        System.out.println("空跳表测试通过");
        
        // 测试null key
        System.out.println("测试null key...");
        skipList.put(null, "test");
        if (skipList.size() != 0) {
            System.out.println("错误：null key应该被忽略！");
            return;
        }
        skipList.remove(null);
        if (skipList.size() != 0) {
            System.out.println("错误：删除null key后大小应该为0！");
            return;
        }
        System.out.println("null key测试通过");
        
        // 测试单个节点
        System.out.println("测试单个节点...");
        skipList.put(1, "single");
        if (skipList.size() != 1) {
            System.out.println("错误：单个节点大小不为1！");
            return;
        }
        if (!skipList.firstKey().equals(skipList.lastKey())) {
            System.out.println("错误：单个节点的firstKey和lastKey应该相等！");
            return;
        }
        System.out.println("单个节点测试通过");
        
        // 测试重复key
        System.out.println("测试重复key...");
        skipList.put(1, "updated");
        if (skipList.size() != 1) {
            System.out.println("错误：重复key不应该增加大小！");
            return;
        }
        if (!skipList.get(1).equals("updated")) {
            System.out.println("错误：重复key应该更新值！");
            return;
        }
        System.out.println("重复key测试通过");
        
        // 测试删除不存在的key
        System.out.println("测试删除不存在的key...");
        skipList.remove(999);
        if (skipList.size() != 1) {
            System.out.println("错误：删除不存在的key不应该改变大小！");
            return;
        }
        System.out.println("删除不存在的key测试通过");
        
        System.out.println("边界情况测试全部通过！");
    }
    
    /**
     * 测试大量数据
     */
    public static void testLargeData() {
        System.out.println("\n=== 测试大量数据 ===");
        
        Table_SkipListMap.SkipListMap<Integer, String> skipList = new Table_SkipListMap.SkipListMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        Random random = new Random(42); // 固定种子保证可重复
        int testSize = 10000;
        
        System.out.println("插入 " + testSize + " 个随机数据...");
        
        // 插入随机数据
        for (int i = 0; i < testSize; i++) {
            int key = random.nextInt(100000);
            String value = "value_" + key;
            
            skipList.put(key, value);
            treeMap.put(key, value);
            
            if (i % 1000 == 0) {
                System.out.println("已插入 " + i + " 个数据，当前大小: " + skipList.size());
            }
        }
        
        System.out.println("插入完成，最终大小: " + skipList.size());
        
        // 验证大小
        if (skipList.size() != treeMap.size()) {
            System.out.println("错误：大量数据测试中大小不匹配！");
            return;
        }
        
        // 随机查找测试
        System.out.println("进行随机查找测试...");
        int searchCount = 1000;
        for (int i = 0; i < searchCount; i++) {
            int key = random.nextInt(100000);
            String skipListValue = skipList.get(key);
            String treeMapValue = treeMap.get(key);
            
            if ((skipListValue == null && treeMapValue != null) || 
                (skipListValue != null && !skipListValue.equals(treeMapValue))) {
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
            skipList.remove(key);
            treeMap.remove(key);
            
            if (skipList.size() != treeMap.size()) {
                System.out.println("错误：随机删除后大小不匹配！");
                return;
            }
        }
        System.out.println("随机删除测试通过，剩余大小: " + skipList.size());
        
        System.out.println("大量数据测试通过！");
    }
    
    /**
     * 测试跳表特性
     */
    public static void testSkipListFeatures() {
        System.out.println("\n=== 测试跳表特性 ===");
        
        Table_SkipListMap.SkipListMap<String, String> skipList = new Table_SkipListMap.SkipListMap<>();
        
        // 测试跳表的层级结构
        System.out.println("测试跳表层级结构...");
        String[] keys = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] values = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        
        for (int i = 0; i < keys.length; i++) {
            skipList.put(keys[i], values[i]);
        }
        
        System.out.println("插入 " + keys.length + " 个数据，跳表大小: " + skipList.size());
        
        // 测试跳表的查找性能（应该比普通链表快）
        System.out.println("测试跳表查找性能...");
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            skipList.get("A");
            skipList.get("J");
            skipList.get("E");
        }
        long skipListTime = System.nanoTime() - startTime;
        System.out.println("跳表查找耗时: " + skipListTime / 1000000 + "ms");
        
        // 测试跳表的范围查询
        System.out.println("测试跳表范围查询...");
        if (!skipList.floorKey("C").equals("C")) {
            System.out.println("错误：floorKey('C')应该返回'C'！");
            return;
        }
        if (!skipList.ceilingKey("C").equals("C")) {
            System.out.println("错误：ceilingKey('C')应该返回'C'！");
            return;
        }
        if (!skipList.floorKey("B").equals("B")) {
            System.out.println("错误：floorKey('B')应该返回'B'！");
            return;
        }
        if (!skipList.ceilingKey("B").equals("B")) {
            System.out.println("错误：ceilingKey('B')应该返回'B'！");
            return;
        }
        System.out.println("范围查询测试通过");
        
        // 测试跳表的删除和层级调整
        System.out.println("测试跳表删除和层级调整...");
        skipList.remove("A");
        skipList.remove("J");
        skipList.remove("E");
        
        if (skipList.containsKey("A") || skipList.containsKey("J") || skipList.containsKey("E")) {
            System.out.println("错误：删除后仍然包含已删除的key！");
            return;
        }
        
        if (!skipList.firstKey().equals("B")) {
            System.out.println("错误：删除后firstKey应该是'B'！");
            return;
        }
        
        if (!skipList.lastKey().equals("I")) {
            System.out.println("错误：删除后lastKey应该是'I'！");
            return;
        }
        System.out.println("删除和层级调整测试通过");
        
        System.out.println("跳表特性测试全部通过！");
    }
    
    /**
     * 性能对比测试
     */
    public static void performanceTest() {
        System.out.println("\n=== 性能对比测试 ===");
        
        Table_SkipListMap.SkipListMap<Integer, String> skipList = new Table_SkipListMap.SkipListMap<>();
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
        
        // 跳表插入性能测试
        System.out.println("测试跳表插入性能...");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            skipList.put(testKeys[i], testValues[i]);
        }
        long skipListInsertTime = System.currentTimeMillis() - startTime;
        System.out.println("跳表插入 " + testSize + " 个数据耗时: " + skipListInsertTime + "ms");
        
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
            skipList.get(testKeys[random.nextInt(testSize)]);
        }
        long skipListSearchTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < searchCount; i++) {
            treeMap.get(testKeys[random.nextInt(testSize)]);
        }
        long treeMapSearchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("跳表查找 " + searchCount + " 次耗时: " + skipListSearchTime + "ms");
        System.out.println("TreeMap查找 " + searchCount + " 次耗时: " + treeMapSearchTime + "ms");
        
        // 删除性能测试
        System.out.println("测试删除性能...");
        int deleteCount = 10000;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < deleteCount; i++) {
            skipList.remove(testKeys[random.nextInt(testSize)]);
        }
        long skipListDeleteTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < deleteCount; i++) {
            treeMap.remove(testKeys[random.nextInt(testSize)]);
        }
        long treeMapDeleteTime = System.currentTimeMillis() - startTime;
        
        System.out.println("跳表删除 " + deleteCount + " 次耗时: " + skipListDeleteTime + "ms");
        System.out.println("TreeMap删除 " + deleteCount + " 次耗时: " + treeMapDeleteTime + "ms");
        
        // 性能对比
        System.out.println("\n性能对比结果:");
        System.out.println("插入性能比: 跳表/TreeMap = " + String.format("%.2f", (double)skipListInsertTime/treeMapInsertTime));
        System.out.println("查找性能比: 跳表/TreeMap = " + String.format("%.2f", (double)skipListSearchTime/treeMapSearchTime));
        System.out.println("删除性能比: 跳表/TreeMap = " + String.format("%.2f", (double)skipListDeleteTime/treeMapDeleteTime));
        
        System.out.println("性能测试完成！");
    }
    
    /**
     * 打印跳表结构（用于调试）
     */
    public static void printSkipList(Table_SkipListMap.SkipListMap<String, String> skipList) {
        System.out.println("跳表结构:");
        for (int i = skipList.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            Table_SkipListMap.SkipListNode<String, String> cur = skipList.head;
            while (cur.nextNodes.get(i) != null) {
                Table_SkipListMap.SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.value + ") ");
                cur = next;
            }
            System.out.println();
        }
    }
} 