package coding.codeevery;

import java.util.TreeSet;

/**
 * @author linzherong
 * @date 2025/7/27 16:34
 */
public class Leetcode406 {

    //假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
    //
    //请你重新构造并返回输入数组 people 所表示的队列。返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
    // 输入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
    // 输出：         [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
    // 1 <= people.length <= 2000
    // 0 <= hi <= 106
    // 0 <= ki < people.length
    // [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]

    public int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length == 0) {
            return null;
        }
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> {
            // 优先身高从大到小，再前边人数小到大
            return o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1];
        });
        // 排序
        for (int i = 0; i < people.length; i++) {
            set.add(people[i]);
        }
        int[][] ans = new int[people.length][2];
        int i = ans.length-1;
        // 其实就是1位置第几就排第几
        while (!set.isEmpty()) {
            int[] cur = set.pollFirst();
            ans[i--] = cur;
            for (int j = 0; j < cur[1]; j++) {
                swap(ans, j + i + 1, j + i +2);
            }
        }
        return ans;
    }

    public void swap(int[][] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int[] tmp = arr[left];
        arr[left] = arr[right];
        arr[right] = tmp;
    }

}
