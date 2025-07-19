package coding.offer.day02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 *
 * 给定数组 hard 和 money，长度都为 N
 * hard[i] 表示 i号的难度，money[i] 表示i号工作的收入
 * 给定数组 ability，长度 M，ability[j] 表示j号人的能力，每一号工作，都可以提供无数的岗位，难度和收入也一样，但是必须>=这份工作的难度，才能上班
 * 返回一个长度为M 的数组ans，ans[i]表示j号人能获得的最好收入
 *
 * @author linzherong
 * @date 2025/7/13 18:51
 */
public class ChooseWork {

    public static class Job {
        int hard;
        int money;
        public Job(int hard, int money) {
            this.hard = hard;
            this.money = money;
        }
    }

    public static int[] getMoneys(int[] hard, int[] money, int[] ability) {
        // 相同难度的，只拿报酬最多的；相同报酬的，那难度最小的
        Job[] jobs = new Job[hard.length];
        for (int i = 0; i < hard.length; i++) {
            jobs[i] = new Job(hard[i], money[i]);
        }
        // 优先难度排序，难度一样的，钱越多越靠前
        Arrays.sort(jobs, (o1, o2) -> o1.hard != o2.hard? o1.hard - o2.hard : o2.money - o1.money);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int preHard = -1;
        for (int i = 0; i < jobs.length; i++) {
            if (jobs[i].hard > preHard) {
                // 难度相同的就不用进有序表了，有序表只留一个钱最多的即可
                Job job = jobs[i];
                preHard = job.hard;
                map.put(job.hard, job.money);
            }
        }
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = map.floorKey(ability[i]);
            ans[i] = key == null? 0 : map.get(key);
        }
        return ans;
    }

}
