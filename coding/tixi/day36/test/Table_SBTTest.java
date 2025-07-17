package coding.tixi.day36.test;

import coding.tixi.day36.Table_SBT;
import java.util.TreeMap;
import java.util.Random;

/**
 * SizeBalanceTree测试对数器
 * 
 * @author linzherong
 * @date 2025/7/12 21:35
 */
public class Table_SBTTest {
    
    public static void main(String[] args) {
        System.out.println("开始测试SizeBalanceTree...");
        
        // 测试基本功能
        testBasicOperations();
        
        // 测试边界情况
        testEdgeCases();
        
        // 测试大量数据
        testLargeData();
        
        // 测试按索引操作
        testIndexOperations();
        
        // 性能对比测试
        performanceTest();
        
        System.out.println("所有测试完成！");
    }
    
    /**
     * 测试基本操作
     */
    public static void testBasicOperations() {
        System.out.println("\n=== 测试基本操作 ===");
        
        Table_SBT.SizeBalanceTreeMap<Integer, String> sbt = new Table_SBT.SizeBalanceTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        // 测试插入
        System.out.println("测试插入操作...");
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85};
        String[] values = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        
        for (int i = 0; i < keys.length; i++) {
            sbt.put(keys[i], values[i]);
            treeMap.put(keys[i], values[i]);
            
            // 验证大小
            if (sbt.size() != treeMap.size()) {
                System.out.println("错误：大小不匹配！SBT: " + sbt.size() + ", TreeMap: " + treeMap.size());
                return;
            }
        }
        System.out.println("插入测试通过，大小: " + sbt.size());
        
        // 测试查找
        System.out.println("测试查找操作...");
        for (int key : keys) {
            String sbtValue = sbt.get(key);
            String treeMapValue = treeMap.get(key);
            if (!sbtValue.equals(treeMapValue)) {
                System.out.println("错误：查找结果不匹配！key: " + key + ", SBT: " + sbtValue + ", TreeMap: " + treeMapValue);
                return;
            }
        }
        System.out.println("查找测试通过");
        
        // 测试containsKey
        System.out.println("测试containsKey操作...");
        for (int key : keys) {
            if (sbt.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：containsKey结果不匹配！key: " + key);
                return;
            }
        }
        // 测试不存在的key
        if (sbt.containsKey(999) != treeMap.containsKey(999)) {
            System.out.println("错误：不存在的key测试失败！");
            return;
        }
        System.out.println("containsKey测试通过");
        
        // 测试firstKey和lastKey
        System.out.println("测试firstKey和lastKey...");
        if (!sbt.firstKey().equals(treeMap.firstKey())) {
            System.out.println("错误：firstKey不匹配！SBT: " + sbt.firstKey() + ", TreeMap: " + treeMap.firstKey());
            return;
        }
        if (!sbt.lastKey().equals(treeMap.lastKey())) {
            System.out.println("错误：lastKey不匹配！SBT: " + sbt.lastKey() + ", TreeMap: " + treeMap.lastKey());
            return;
        }
        System.out.println("firstKey和lastKey测试通过");
        
        // 测试floorKey和ceilingKey
        System.out.println("测试floorKey和ceilingKey...");
        int[] testKeys = {15, 25, 35, 50, 65, 90};
        for (int key : testKeys) {
            Integer sbtFloor = sbt.floorKey(key);
            Integer treeMapFloor = treeMap.floorKey(key);
            if ((sbtFloor == null && treeMapFloor != null) || 
                (sbtFloor != null && !sbtFloor.equals(treeMapFloor))) {
                System.out.println("错误：floorKey不匹配！key: " + key + ", SBT: " + sbtFloor + ", TreeMap: " + treeMapFloor);
                return;
            }
            
            Integer sbtCeiling = sbt.ceilingKey(key);
            Integer treeMapCeiling = treeMap.ceilingKey(key);
            if ((sbtCeiling == null && treeMapCeiling != null) || 
                (sbtCeiling != null && !sbtCeiling.equals(treeMapCeiling))) {
                System.out.println("错误：ceilingKey不匹配！key: " + key + ", SBT: " + sbtCeiling + ", TreeMap: " + treeMapCeiling);
                return;
            }
        }
        System.out.println("floorKey和ceilingKey测试通过");
        
        // 测试删除
        System.out.println("测试删除操作...");
        int[] deleteKeys = {30, 70, 50, 20, 80};
        for (int key : deleteKeys) {
            sbt.remove(key);
            treeMap.remove(key);
            
            if (sbt.size() != treeMap.size()) {
                System.out.println("错误：删除后大小不匹配！SBT: " + sbt.size() + ", TreeMap: " + treeMap.size());
                return;
            }
            
            if (sbt.containsKey(key) != treeMap.containsKey(key)) {
                System.out.println("错误：删除后containsKey结果不匹配！key: " + key);
                return;
            }
        }
        System.out.println("删除测试通过，剩余大小: " + sbt.size());
        
        System.out.println("基本操作测试全部通过！");
    }
    
    /**
     * 测试边界情况
     */
    public static void testEdgeCases() {
        System.out.println("\n=== 测试边界情况 ===");
        
        Table_SBT.SizeBalanceTreeMap<Integer, String> sbt = new Table_SBT.SizeBalanceTreeMap<>();
        
        // 测试空树
        System.out.println("测试空树...");
        if (sbt.size() != 0) {
            System.out.println("错误：空树大小不为0！");
            return;
        }
        if (sbt.firstKey() != null) {
            System.out.println("错误：空树firstKey不为null！");
            return;
        }
        if (sbt.lastKey() != null) {
            System.out.println("错误：空树lastKey不为null！");
            return;
        }
        if (sbt.get(1) != null) {
            System.out.println("错误：空树get不为null！");
            return;
        }
        System.out.println("空树测试通过");
        
        // 测试单个节点
        System.out.println("测试单个节点...");
        sbt.put(1, "single");
        if (sbt.size() != 1) {
            System.out.println("错误：单个节点大小不为1！");
            return;
        }
        if (!sbt.firstKey().equals(sbt.lastKey())) {
            System.out.println("错误：单个节点的firstKey和lastKey应该相等！");
            return;
        }
        System.out.println("单个节点测试通过");
        
        // 测试重复key
        System.out.println("测试重复key...");
        sbt.put(1, "updated");
        if (sbt.size() != 1) {
            System.out.println("错误：重复key不应该增加大小！");
            return;
        }
        if (!sbt.get(1).equals("updated")) {
            System.out.println("错误：重复key应该更新值！");
            return;
        }
        System.out.println("重复key测试通过");
        
        // 测试删除不存在的key
        System.out.println("测试删除不存在的key...");
        sbt.remove(999);
        if (sbt.size() != 1) {
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
        
        Table_SBT.SizeBalanceTreeMap<Integer, String> sbt = new Table_SBT.SizeBalanceTreeMap<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        
        Random random = new Random(42); // 固定种子保证可重复
        int testSize = 10000;
        
        System.out.println("插入 " + testSize + " 个随机数据...");
        
        // 插入随机数据
        for (int i = 0; i < testSize; i++) {
            int key = random.nextInt(100000);
            String value = "value_" + key;
            
            sbt.put(key, value);
            treeMap.put(key, value);
            
            if (i % 1000 == 0) {
                System.out.println("已插入 " + i + " 个数据，当前大小: " + sbt.size());
            }
        }
        
        System.out.println("插入完成，最终大小: " + sbt.size());
        
        // 验证大小
        if (sbt.size() != treeMap.size()) {
            System.out.println("错误：大量数据测试中大小不匹配！");
            return;
        }
        
        // 随机查找测试
        System.out.println("进行随机查找测试...");
        int searchCount = 1000;
        for (int i = 0; i < searchCount; i++) {
            int key = random.nextInt(100000);
            String sbtValue = sbt.get(key);
            String treeMapValue = treeMap.get(key);
            
            if ((sbtValue == null && treeMapValue != null) || 
                (sbtValue != null && !sbtValue.equals(treeMapValue))) {
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
            sbt.remove(key);
            treeMap.remove(key);
            
            if (sbt.size() != treeMap.size()) {
                System.out.println("错误：随机删除后大小不匹配！");
                return;
            }
        }
        System.out.println("随机删除测试通过，剩余大小: " + sbt.size());
        
        System.out.println("大量数据测试通过！");
    }
    
    /**
     * 测试按索引操作
     */
    public static void testIndexOperations() {
        System.out.println("\n=== 测试按索引操作 ===");
        
        Table_SBT.SizeBalanceTreeMap<Integer, String> sbt = new Table_SBT.SizeBalanceTreeMap<>();
        
        // 插入有序数据
        int[] keys = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        String[] values = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        
        for (int i = 0; i < keys.length; i++) {
            sbt.put(keys[i], values[i]);
        }
        
        System.out.println("插入 " + keys.length + " 个有序数据");
        
        // 测试getIndexKey
        System.out.println("测试getIndexKey...");
        for (int i = 0; i < keys.length; i++) {
            Integer indexKey = sbt.getIndexKey(i);
            if (indexKey == null || !indexKey.equals(keys[i])) {
                System.out.println("错误：getIndexKey结果不匹配！index: " + i + ", expected: " + keys[i] + ", actual: " + indexKey);
                return;
            }
        }
        System.out.println("getIndexKey测试通过");
        
        // 测试getIndexValue
        System.out.println("测试getIndexValue...");
        for (int i = 0; i < values.length; i++) {
            String indexValue = sbt.getIndexValue(i);
            if (indexValue == null || !indexValue.equals(values[i])) {
                System.out.println("错误：getIndexValue结果不匹配！index: " + i + ", expected: " + values[i] + ", actual: " + indexValue);
                return;
            }
        }
        System.out.println("getIndexValue测试通过");
        
        // 测试边界索引
        System.out.println("测试边界索引...");
        if (sbt.getIndexKey(-1) != null) {
            System.out.println("错误：负索引应该返回null！");
            return;
        }
        if (sbt.getIndexKey(keys.length) != null) {
            System.out.println("错误：超出范围的索引应该返回null！");
            return;
        }
        if (sbt.getIndexValue(-1) != null) {
            System.out.println("错误：负索引应该返回null！");
            return;
        }
        if (sbt.getIndexValue(keys.length) != null) {
            System.out.println("错误：超出范围的索引应该返回null！");
            return;
        }
        System.out.println("边界索引测试通过");
        
        // 测试删除后的索引
        System.out.println("测试删除后的索引...");
        sbt.remove(30); // 删除中间的元素
        sbt.remove(80); // 删除后面的元素
        
        // 验证删除后的索引
        Integer firstKey = sbt.getIndexKey(0);
        Integer lastKey = sbt.getIndexKey(sbt.size() - 1);
        if (firstKey == null || lastKey == null) {
            System.out.println("错误：删除后索引获取失败！");
            return;
        }
        System.out.println("删除后第一个key: " + firstKey + ", 最后一个key: " + lastKey);
        
        System.out.println("按索引操作测试全部通过！");
    }
    
    /**
     * 性能对比测试
     */
    public static void performanceTest() {
        System.out.println("\n=== 性能对比测试 ===");
        
        Table_SBT.SizeBalanceTreeMap<Integer, String> sbt = new Table_SBT.SizeBalanceTreeMap<>();
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
        
        // SBT插入性能测试
        System.out.println("测试SBT插入性能...");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testSize; i++) {
            sbt.put(testKeys[i], testValues[i]);
        }
        long sbtInsertTime = System.currentTimeMillis() - startTime;
        System.out.println("SBT插入 " + testSize + " 个数据耗时: " + sbtInsertTime + "ms");
        
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
            sbt.get(testKeys[random.nextInt(testSize)]);
        }
        long sbtSearchTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < searchCount; i++) {
            treeMap.get(testKeys[random.nextInt(testSize)]);
        }
        long treeMapSearchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("SBT查找 " + searchCount + " 次耗时: " + sbtSearchTime + "ms");
        System.out.println("TreeMap查找 " + searchCount + " 次耗时: " + treeMapSearchTime + "ms");
        
        // 索引访问性能测试
        System.out.println("测试索引访问性能...");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < searchCount; i++) {
            sbt.getIndexKey(random.nextInt(sbt.size()));
        }
        long sbtIndexTime = System.currentTimeMillis() - startTime;
        System.out.println("SBT索引访问 " + searchCount + " 次耗时: " + sbtIndexTime + "ms");
        
        // 性能对比
        System.out.println("\n性能对比结果:");
        System.out.println("插入性能比: SBT/TreeMap = " + String.format("%.2f", (double)sbtInsertTime/treeMapInsertTime));
        System.out.println("查找性能比: SBT/TreeMap = " + String.format("%.2f", (double)sbtSearchTime/treeMapSearchTime));
        System.out.println("SBT索引访问性能: " + sbtIndexTime + "ms");
        
        System.out.println("性能测试完成！");
    }
    
    /**
     * 打印SBT树结构（用于调试）
     */
    public static void printSBTTree(Table_SBT.SBTNode<Integer, String> node, String prefix, boolean isLeft) {
        if (node == null) {
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.key + "(" + node.size + ")");
        printSBTTree(node.left, prefix + (isLeft ? "│   " : "    "), true);
        printSBTTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
    }
} 