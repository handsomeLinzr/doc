package coding.tixi.day38;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 对数器测试类
 * 验证FastList的正确性并与ArrayList、LinkedList进行性能对比
 * 
 * @author linzherong
 * @date 2025/7/14
 */
public class AddRemoveGetIndexGreatTest {

    public static void main(String[] args) {
        // 验证正确性
        System.out.println("=== 开始验证FastList正确性 ===");
        if (testCorrectness()) {
            System.out.println("✅ FastList正确性验证通过");
        } else {
            System.out.println("❌ FastList正确性验证失败");
            return;
        }

        // 性能对比测试
        System.out.println("\n=== 开始性能对比测试 ===");
        performanceComparison();
    }

    /**
     * 验证FastList的正确性
     */
    public static boolean testCorrectness() {
        Random random = new Random(42); // 固定种子保证可重复性
        int testCount = 10000;
        int maxSize = 1000;

        for (int test = 0; test < testCount; test++) {
            AddRemoveGetIndexGreat.FastList<Integer> fastList = new AddRemoveGetIndexGreat.FastList<>();
            List<Integer> arrayList = new ArrayList<>();
            List<Integer> linkedList = new LinkedList<>();

            int operations = random.nextInt(100) + 50; // 50-150次操作

            for (int op = 0; op < operations; op++) {
                int operation = random.nextInt(3); // 0:add, 1:get, 2:remove
                
                try {
                    switch (operation) {
                        case 0: // add
                            int addIndex = random.nextInt(arrayList.size() + 1);
                            int addValue = random.nextInt(1000);
                            
                            fastList.add(addIndex, addValue);
                            arrayList.add(addIndex, addValue);
                            linkedList.add(addIndex, addValue);
                            break;
                            
                        case 1: // get
                            if (!arrayList.isEmpty()) {
                                int getIndex = random.nextInt(arrayList.size());
                                Integer fastValue = fastList.get(getIndex);
                                Integer arrayValue = arrayList.get(getIndex);
                                Integer linkedValue = linkedList.get(getIndex);
                                
                                if (!fastValue.equals(arrayValue) || !fastValue.equals(linkedValue)) {
                                    System.out.println("Get操作不一致: index=" + getIndex + 
                                        ", fast=" + fastValue + ", array=" + arrayValue + ", linked=" + linkedValue);
                                    return false;
                                }
                            }
                            break;
                            
                        case 2: // remove
                            if (!arrayList.isEmpty()) {
                                int removeIndex = random.nextInt(arrayList.size());
                                
                                fastList.remove(removeIndex);
                                arrayList.remove(removeIndex);
                                linkedList.remove(removeIndex);
                            }
                            break;
                    }
                    
                    // 验证大小一致性
                    if (fastList.size != arrayList.size() || fastList.size != linkedList.size()) {
                        System.out.println("大小不一致: fast=" + fastList.size + 
                            ", array=" + arrayList.size() + ", linked=" + linkedList.size());
                        return false;
                    }
                    
                    // 验证所有元素一致性
                    for (int i = 0; i < arrayList.size(); i++) {
                        Integer fastValue = fastList.get(i);
                        Integer arrayValue = arrayList.get(i);
                        if (!fastValue.equals(arrayValue)) {
                            System.out.println("元素不一致: index=" + i + 
                                ", fast=" + fastValue + ", array=" + arrayValue);
                            return false;
                        }
                    }
                    
                } catch (Exception e) {
                    System.out.println("操作异常: " + e.getMessage());
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * 性能对比测试
     */
    public static void performanceComparison() {
        int[] testSizes = {1000, 5000, 10000, 20000};
        int operationCount = 10000;
        
        for (int size : testSizes) {
            System.out.println("\n--- 测试数据量: " + size + " ---");
            
            // 初始化数据
            AddRemoveGetIndexGreat.FastList<Integer> fastList = new AddRemoveGetIndexGreat.FastList<>();
            List<Integer> arrayList = new ArrayList<>();
            List<Integer> linkedList = new LinkedList<>();
            
            Random random = new Random(42);
            
            // 预填充数据
            for (int i = 0; i < size; i++) {
                int value = random.nextInt(1000);
                fastList.add(i, value);
                arrayList.add(i, value);
                linkedList.add(i, value);
            }
            
            // 测试add操作
            testAddPerformance(fastList, arrayList, linkedList, operationCount, random);
            
            // 测试get操作
            testGetPerformance(fastList, arrayList, linkedList, operationCount, random);
            
            // 测试remove操作
            testRemovePerformance(fastList, arrayList, linkedList, operationCount, random);
        }
    }

    private static void testAddPerformance(AddRemoveGetIndexGreat.FastList<Integer> fastList, List<Integer> arrayList, 
                                         List<Integer> linkedList, int operationCount, Random random) {
        System.out.println("Add操作性能测试 (" + operationCount + "次):");
        
        // 测试FastList
        long startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(fastList.size + 1);
            fastList.add(index, random.nextInt(1000));
        }
        long fastTime = System.nanoTime() - startTime;
        
        // 测试ArrayList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(arrayList.size() + 1);
            arrayList.add(index, random.nextInt(1000));
        }
        long arrayTime = System.nanoTime() - startTime;
        
        // 测试LinkedList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(linkedList.size() + 1);
            linkedList.add(index, random.nextInt(1000));
        }
        long linkedTime = System.nanoTime() - startTime;
        
        System.out.printf("FastList: %.2f ms\n", fastTime / 1_000_000.0);
        System.out.printf("ArrayList: %.2f ms\n", arrayTime / 1_000_000.0);
        System.out.printf("LinkedList: %.2f ms\n", linkedTime / 1_000_000.0);
        System.out.printf("FastList vs ArrayList: %.2fx\n", (double) arrayTime / fastTime);
        System.out.printf("FastList vs LinkedList: %.2fx\n", (double) linkedTime / fastTime);
    }

    private static void testGetPerformance(AddRemoveGetIndexGreat.FastList<Integer> fastList, List<Integer> arrayList, 
                                         List<Integer> linkedList, int operationCount, Random random) {
        System.out.println("Get操作性能测试 (" + operationCount + "次):");
        
        // 测试FastList
        long startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(fastList.size);
            fastList.get(index);
        }
        long fastTime = System.nanoTime() - startTime;
        
        // 测试ArrayList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(arrayList.size());
            arrayList.get(index);
        }
        long arrayTime = System.nanoTime() - startTime;
        
        // 测试LinkedList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount; i++) {
            int index = random.nextInt(linkedList.size());
            linkedList.get(index);
        }
        long linkedTime = System.nanoTime() - startTime;
        
        System.out.printf("FastList: %.2f ms\n", fastTime / 1_000_000.0);
        System.out.printf("ArrayList: %.2f ms\n", arrayTime / 1_000_000.0);
        System.out.printf("LinkedList: %.2f ms\n", linkedTime / 1_000_000.0);
        System.out.printf("FastList vs ArrayList: %.2fx\n", (double) arrayTime / fastTime);
        System.out.printf("FastList vs LinkedList: %.2fx\n", (double) linkedTime / fastTime);
    }

    private static void testRemovePerformance(AddRemoveGetIndexGreat.FastList<Integer> fastList, List<Integer> arrayList, 
                                            List<Integer> linkedList, int operationCount, Random random) {
        System.out.println("Remove操作性能测试 (" + operationCount + "次):");
        
        // 测试FastList
        long startTime = System.nanoTime();
        for (int i = 0; i < operationCount && fastList.size > 0; i++) {
            int index = random.nextInt(fastList.size);
            fastList.remove(index);
        }
        long fastTime = System.nanoTime() - startTime;
        
        // 测试ArrayList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount && arrayList.size() > 0; i++) {
            int index = random.nextInt(arrayList.size());
            arrayList.remove(index);
        }
        long arrayTime = System.nanoTime() - startTime;
        
        // 测试LinkedList
        startTime = System.nanoTime();
        for (int i = 0; i < operationCount && linkedList.size() > 0; i++) {
            int index = random.nextInt(linkedList.size());
            linkedList.remove(index);
        }
        long linkedTime = System.nanoTime() - startTime;
        
        System.out.printf("FastList: %.2f ms\n", fastTime / 1_000_000.0);
        System.out.printf("ArrayList: %.2f ms\n", arrayTime / 1_000_000.0);
        System.out.printf("LinkedList: %.2f ms\n", linkedTime / 1_000_000.0);
        System.out.printf("FastList vs ArrayList: %.2fx\n", (double) arrayTime / fastTime);
        System.out.printf("FastList vs LinkedList: %.2fx\n", (double) linkedTime / fastTime);
    }
} 