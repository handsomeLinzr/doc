package coding.tixi.day03;

import java.util.*;

/**
 * 异或
 *
 * 1. 将 int 的最右的 1 提取出来
 * 2. 一个数组，有1个数出现奇数次，其他都是偶数次，找出这个数
 * 3. 同2，有两个数出现奇数次，找出这两个数
 * 4. 一个数组， 有1个数出现K次，其他数都出现M次， K < M， 求出现 K 次的数（用 额外空间 int[32]，然后每个数的每个位都进行统计，放到 int 上）
 * @author linzherong
 * @date 2025/3/23 21:20
 */
public class Yihuo {

    public static void main(String[] args) {
        Yihuo yihuo = new Yihuo();
        for (int i = 0; i < 100_0000; i++) {
            yihuo.testjishuOne();
        }
        System.out.println("成功");
    }

    public int rightestOne(int num) {
        return num & (-num);
    }

    public int jishuOne(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            result ^= arr[i];
        }
        return result;
    }

    public void testjishuOne() {
        int target = (int) (Math.random() * 1000) - (int) (Math.random() * 1000);
        int other = (int) (Math.random() * 10);
        int time;
        do {
            time = (int) (Math.random() * 10);
        } while ((time & 1) == 0);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < time; i++) {
            list.add(target);
        }
        for (int i = 0; i < other; i++) {
            int num = (int) (Math.random() * 1000) - (int) (Math.random() * 1000);
            int time1 = (int) (Math.random() * 10);
            for (int j = 0; j < time1 * 2; j++) {
                list.add(num);
            }
        }
        int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
        for (int i = 0; i < arr.length; i++) {
            swap(arr, (int) (Math.random() * arr.length), (int) (Math.random() * arr.length));
        }
        int j1 = jishuOne(arr);
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == j1) {
                count ++ ;
            }
        }
        if (count == 0 || (count&1) == 0) {
            System.out.println("错了");
        }

    }

    public void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public int[] jiTwo(int[] arr) {
        return null;
    }


}
