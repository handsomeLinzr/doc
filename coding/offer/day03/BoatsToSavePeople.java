package coding.offer.day03;

import java.util.Arrays;

/**
 *
 * // 给定一个正数数组arr，代表若干人的体重
 * // 再给定一个正数limit，表示所有船共同拥有的载重量
 * // 每艘船最多坐两人，且不能超过载重
 * // 想让所有的人同时过河，并且用最好的分配方法让船尽量少
 * // 返回最少的船数
 * // 测试链接 : https://leetcode.com/problems/boats-to-save-people/
 *
 * 从中间分两个指针 L 和 R， 中间是指 limit / 2
 *
 * @author linzherong
 * @date 2025/7/18 00:43
 */
public class BoatsToSavePeople {

    public int numRescueBoats(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }
        int ans = 0;
        Arrays.sort(people);
        int L = 0;
        int R = people.length-1;
        while (L < R) {
            if (people[L] + people[R] > limit) {
                R--;
                ans++;
            } else {
                L++;
                R--;
                ans++;
            }
        }
        ans = L == R? ans+1 : ans;
        return ans;
    }
}
