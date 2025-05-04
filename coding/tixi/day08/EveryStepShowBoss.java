package coding.tixi.day08;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 给定一个整型数组 int[] arr 和 布尔类型数组 boolean[] op, 两个数组一定等长，假设长度为N，arr[i] 表示客户编号，op[i] 表示用户行为，
 * 如 arr = [3, 1, 3, 2]
 *     op = [T, T, F, T]
 *  表示 客户3购买一件商品， 客户1购买一件商品，客户3退回一件商品， 客户2购买一件商品
 *
 * @author linzherong
 * @date 2025/4/22 01:06
 */
public class EveryStepShowBoss {

    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;
        public Customer(int id, int buy) {
            this.id = id;
            this.buy = buy;
        }
    }

    // 候奖区排序
    public static class WaitComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy? o2.buy - o1.buy:o1.enterTime - o2.enterTime;
        }
    }
    // 得奖区排序
    public static class DaddyComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy? o1.buy-o2.buy:o1.enterTime-o2.enterTime;
        }
    }

    public static class WhoIsDaddy {
        public int limit;
        public Map<Integer, Customer> customerMap;
        public HeapGreater<Customer> waitHeap;
        public HeapGreater<Customer> daddyHeap;
        public WhoIsDaddy(int limit) {
            this.limit = limit;
            waitHeap = new HeapGreater<>(new WaitComparator());
            daddyHeap = new HeapGreater<>(new DaddyComparator());
            customerMap = new HashMap<>();
        }

        /** 事件处理 */
        public void operate(int time, int id, boolean buy) {
            if (!customerMap.containsKey(id) && !buy) {
                // 不存在，且退货，直接返回
                return;
            }
            if (!customerMap.containsKey(id)) {
                // 添加，此时buy不设置，后续统一处理
                customerMap.put(id, new Customer(id, 0));
            }
            Customer customer = customerMap.get(id);
            customer.enterTime = time;
            if (buy) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            if (customer.buy == 0) {
                customerMap.remove(id);
            }
            if (daddyHeap.contain(customer)) {
                // 在得奖区
                if (customer.buy == 0) {
                    // 购买为0，移除
                    daddyHeap.remove(customer);
                } else {
                    // 重新移动
                    daddyHeap.resign(customer);
                }
            } else if (waitHeap.contain(customer)) {
                // 在候奖区
                if (customer.buy == 0) {
                    // 购买为0，移除
                    waitHeap.remove(customer);
                } else {
                    // 重新移动
                    waitHeap.resign(customer);
                }
            } else {
                // 都不在，先 放候奖区
                if (customer.buy > 0) {
                    waitHeap.add(customer);
                }
            }
            resignDaddy(time);
        }

        //** 重新调整得奖区和候奖区 */
        private void resignDaddy(int time) {
            if (waitHeap.heapSize == 0) {
                return;
            }
            if (daddyHeap.heapSize < limit) {
                Customer pop = waitHeap.pop();
                pop.enterTime = time;
                daddyHeap.add(pop);
                return;
            }
            if (daddyHeap.heapSize>0 && daddyHeap.peek().buy < waitHeap.peek().buy) {
                Customer daddy = daddyHeap.pop();
                Customer wait = waitHeap.pop();
                daddy.enterTime = time;
                wait.enterTime = time;
                daddyHeap.add(wait);
                waitHeap.add(daddy);
            }
        }

        public List<Integer> whoIsDaddy() {
            return daddyHeap.heap.stream().map(c->c.id).collect(Collectors.toList());
        }

    }

    /** 获取每次操作的前k个中奖名单 */
    public List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>(arr.length);
        WhoIsDaddy whoIsDaddy = new WhoIsDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whoIsDaddy.operate(i, arr[i], op[i]);
            ans.add(whoIsDaddy.whoIsDaddy());
        }
        return ans;
    }

    public static void main(String[] args) {
        EveryStepShowBoss boss = new EveryStepShowBoss();
        int[] arr = {   1,    1,   2,    1,   3,   2,    3,   4,   5,    5,   3,   7};
        boolean[] op = {true,true,true,false,true,false,true,true,true,true,false,true};
        List<List<Integer>> lists = boss.topK(arr, op, 2);
        System.out.println(lists);
    }

}
