package coding.offer.day01;

import java.util.Arrays;

/**
 *
 * 给定一个有序数组arr，代表坐落在x轴上的点，
 * 给定一个正数k，代表绳子的长度，
 * 返回绳子最多压中几个点
 *
 * @author linzherong
 * @date 2025/7/18 21:18
 */
public class CordCoverMaxPoint {

    public static int maxPoint(int[] arr, int L) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            int nearest = getNearest(arr, i, arr[i] - L);
            max = Math.max(max, i - nearest + 1);
        }
        return max;
    }

    public static int getNearest(int[] arr, int R, int vale) {
        int L = 0;
        int M;
        int ans = 0;
        while (L <= R) {
            M = (L+R)>>1;
            int m = arr[M];
            if (m >= vale) {
                ans = M;
                R = M - 1;
            } else {
                L = M + 1;
            }
        }
        return ans;
    }

    public static int maxPoint2(int[] arr, int L) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            int pre = i-1;
            int last = arr[i] - L;
            while (pre >= 0 && arr[pre] >= last) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // test
    public static int test(int[] arr, int L) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int left = arr[i] - L;
            int index = getLeftNear(arr, left);
            max = Math.max(max, i - index + 1);
        }
        return max;
    }

    public static int getLeftNear(int[] arr, int i) {
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] >= i) {
                return j;
            }
        }
        return 0;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans3 = test(arr, L);
            int ans = maxPoint(arr, L);
            int ans2 = maxPoint2(arr, L);
            if (ans != ans3 || ans != ans2) {
                System.out.println("oops!");
                break;
            }
        }
        System.out.println("finish");
    }

}
