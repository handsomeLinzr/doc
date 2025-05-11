package coding.tixi.day15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最大利润
 * 最多做k个项目，初始资金是M，profits[] 所有项目的利润，capital[] 所有项目的花费
 * 要求返回最大利润
 *
 * @author linzherong
 * @date 2025/5/6 00:26
 */
public class IPO {

    public static class Project {
        // 花费
        public int cost;
        // 利润
        public int win;
        public Project(int cost, int win) {
            this.cost = cost;
            this.win = win;
        }
    }
    public static int maxIPO(int K, int M, int[] profits, int[] capital) {
        PriorityQueue<Project> costQueue = new PriorityQueue<>((o1,o2)->o2.win-o1.win);
        ArrayList<Project> addition = new ArrayList<>();
        // 入堆
        for (int i = 0; i < profits.length; i++) {
            costQueue.add(new Project(capital[i], profits[i]));
        }
        for (int i = 0; i < K; i++) {
            Project p = null;
            while (!costQueue.isEmpty()) {
                Project poll = costQueue.poll();
                if (poll.cost > M) {
                    addition.add(poll);
                } else {
                    p = poll;
                    costQueue.addAll(addition);
                    addition.clear();
                    M += p.win;
                    break;
                }
            }
            if (costQueue.isEmpty()) {
                return M;
            }
        }
        return M;
    }

    // 暴力解法
    public static int maxIPO1(int K, int M, int[] profits, int[] capital) {
        if (profits.length == 0) {
            return M;
        }
        return process1(1, K, M, profits, capital);
    }
    public static int process1(int curK, int K, int M, int[] profits, int[] capital) {
        if (curK > K) {
            return M;
        }
        int max = M;
        for (int i = 0; i < capital.length; i++) {
            int cur = M;
            if (capital[i] <= cur) {
                int[] p = removeIndex(profits, i);
                int[] c = removeIndex(capital, i);
                cur = process1(curK + 1, K, cur + profits[i], p, c);
                max = Math.max(max, cur);
            }
        }
        return max;
    }

    public static int maxIPO2(int K, int M, int[] profits, int[] capital) {
        PriorityQueue<Project> profitQueue = new PriorityQueue<>((o1,o2)->o2.win-o1.win);
        PriorityQueue<Project> capitalQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        for (int i = 0; i < profits.length; i++) {
            capitalQueue.add(new Project(capital[i], profits[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!capitalQueue.isEmpty() && capitalQueue.peek().cost <= M) {
                profitQueue.add(capitalQueue.poll());
            }
            if (profitQueue.isEmpty()) {
                return M;
            }
            M += profitQueue.poll().win;
        }
        return M;
    }

    public static int[] removeIndex(int[] arr, int index) {
        int[] ans = new int[arr.length-1];
        int m = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != index) {
                ans[m++] = arr[i];
            }
        }
        return ans;
    }

    public static class TestInfo {
        public int K;
        public int M;
        public int[] profits;
        public int[] capital;
        public TestInfo(int k, int m, int[] profits, int[] capital) {
            K = k;
            M = m;
            this.profits = profits;
            this.capital = capital;
        }
    }
    public static TestInfo generalTestInfo(int maxK, int maxM, int maxLength, int maxCost, int maxWin) {
        int K = (int) (Math.random() * maxK + 1);
        int M = (int) (Math.random() * maxM + 1);
        int length = (int) (Math.random() * maxLength);
        int[] capital = new int[length];
        int[] profits = new int[length];
        for (int i = 0; i < length; i++) {
            int c = (int) (Math.random() * maxCost + 1);
            int w = (int) (Math.random() * maxWin + 1);
            capital[i] = c;
            profits[i] = w;
        }
        return new TestInfo(K, M, profits, capital);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100_0000; i++) {
            TestInfo testInfo = generalTestInfo(5, 25, 10, 30, 10);
            int a1 = maxIPO(testInfo.K, testInfo.M, testInfo.profits, testInfo.capital);
            int a2 = maxIPO1(testInfo.K, testInfo.M, testInfo.profits, testInfo.capital);
            int a3 = maxIPO2(testInfo.K, testInfo.M, testInfo.profits, testInfo.capital);
            if (a1 != a2 || a1 != a3) {
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

}
