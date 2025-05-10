package coding.tixi.day13;

import java.util.ArrayList;
import java.util.List;

/**
 * 9.多叉树的快乐值 (多叉数，其中每个节点都有一个happy值，父子节点不能同时存在，返回最大的happy和)
 * @author linzherong
 * @date 2025/5/7 22:28
 */
public class MaxHappy {

    public static class Employee {
        public int happy;
        public List<Employee> nexts;
        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }

    public static int maxHappy(Employee boss) {
        Info process = process(boss, false);
        return Math.max(process.yes, process.no);
    }
    // yes —— 去的情况
    // no  —— 不去的情况
    static class Info {
        int yes;
        int no;
        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }
    public static Info process(Employee employee, boolean up) {
        if (employee == null) {
            return new Info(0, 0);
        }
        int yes = 0;
        int no = 0;
        for (Employee next : employee.nexts) {
            if (up) {
                // 上级去，自己不去
                Info process = process(next, false);
                no += Math.max(process.yes, process.no);
            } else {
                Info pTrue = process(next, true);
                Info pFalse = process(next, false);
                yes += Math.max(pTrue.yes, pTrue.no);
                no += Math.max(pFalse.yes, pFalse.no);
            }
        }
        return new Info(up?0:yes+employee.happy, no);
    }


    public static int maxHappy1(Employee boss) {
        return process1(boss, false);
    }

    public static int process1(Employee employee, boolean up) {
        if (employee == null) {
            return 0;
        }
        int yes = employee.happy;
        int no = 0;
        if (up) {
            for (Employee next : employee.nexts) {
                no += process1(next, false);
            }
            return no;
        } else {
            for (Employee next : employee.nexts) {
                yes += process1(next, true);
                no += process1(next, false);
            }
        }
        return Math.max(yes, no);
    }

    public static Employee generalBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.2) {
            return null;
        }
        Employee boss = new Employee((int)(Math.random() * maxHappy + 1));
        boss.nexts = generalNext(1, maxLevel, maxNexts, maxHappy);
        return boss;
    }
    public static List<Employee> generalNext(int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return new ArrayList<>(0);
        }
        int size = (int) (Math.random() * maxNexts + 1);
        List<Employee> nexts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Employee next = new Employee((int)(Math.random() * maxHappy + 1));
            next.nexts = generalNext(level+1, maxLevel, maxNexts, maxHappy);
            nexts.add(next);
        }
        return nexts;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            Employee boss = generalBoss(5, 5, 20);
            if (maxHappy(boss) != maxHappy1(boss)) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
