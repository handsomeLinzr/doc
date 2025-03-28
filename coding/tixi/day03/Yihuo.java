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
//            yihuo.testjishuOne();
            yihuo.testJiTwo();
//            yihuo.testGetKNum();
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
        int temp = 0;
        // 将所有值异或，得到的结果是两个奇数值的异或结果
        for (int num : arr) {
            temp ^= num;
        }
        // 得到最后一位为1的数，这个位为1，说明这两个奇数在这个位是不一样的
        int lastOne = temp & (-temp);
        int num1 = 0;
        for (int num : arr) {
            // 将所有在这个位为1的数进行异或，由于其他都是偶数，只有其中一个为奇数，最后能得到该数
            if ((num & lastOne) != 0) {
                num1 ^= num;
            }
        }
        return new int[]{num1, temp^num1};
    }

    public void testJiTwo() {
        int target1 = (int) (Math.random() * 1000) - (int) (Math.random() * 1000);
        int target2;
        do {
            target2 = (int) (Math.random() * 1000) - (int) (Math.random() * 1000);
        } while (target1 == target2);
        int other = (int) (Math.random() * 10);
        int time;
        do {
            time = (int) (Math.random() * 10);
        } while ((time & 1) == 0);
        int otherTime = (int) (Math.random() * 10);
        int[] arr = new int[(time * 2) + (otherTime*other*2)];
        int m = 0;
        for (int i = 0; i < time; i++) {
            arr[m++] = target1;
        }
        for (int i = 0; i < time; i++) {
            arr[m++] = target2;
        }
        for (int i = 0; i < other; i++) {
            int o = (int) (Math.random() * 1000) - (int) (Math.random() * 1000);
            for (int j = 0; j < otherTime * 2; j++) {
                arr[m++] = o;
            }
        }
        for (int i = 0; i < m; i++) {
            swap(arr, (int) (Math.random() * m), (int) (Math.random() * m));
        }
        int[] ints = jiTwo(arr);
        int a = ints[0];
        int b = ints[1];
        if (!((a == target1 && b == target2) || (a == target2 && b == target1))) {
            System.out.println(a);
            System.out.println(b);
            System.out.println(target1);
            System.out.println(target2);
            System.out.println("出错了");
        }
    }

    public int getKNum(int[] arr, int K, int M) {
        int[] temp = new int[32];
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            int offset = 0;
            while (num != 0) {
                if ((num & 1) != 0) {
                    temp[offset]++;
                }
                offset++;
                num >>>= 1;
            }
        }
        int result = 0;
        for (int i = 0; i < temp.length; i++) {
            if ((temp[i] % M) != 0) {
                result |= (1<<i);
            }
        }
        return result;
    }

    public void testGetKNum() {
        int K = (int) (Math.random() * 10) + 1;
        int M = K + (int) (Math.random() * 10) + 1;
        int A = (int) (Math.random() * 100) + 1;
        int other = (int) (Math.random() * 10) + 1;
        int[] arr = new int[K+(other*M)];
        int i = 0;
        for (; i < K; i++) {
            arr[i] = A;
        }
        for (int j = 0; j < other; j++) {
            int O = (int) (Math.random() * 100) + 1;
            for (int k = 0; k < M; k++) {
                arr[i++] = O;
            }
        }
        int kNum = getKNum(arr, K, M);
        if (kNum != A) {
            System.out.println("出错了");
        }
    }


}
