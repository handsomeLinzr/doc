package coding.tixi.day31;

/**
 * @author linzherong
 * @date 2025/6/23 14:15
 */
public class Leetcode2081 {

    // k进制回文
    // 从右往左， 1 —— k-1, 11——>  k-1,k-1,
    public long kMirror(int k, int n) {
        return process(k, 0, n, 0, 1);
    }

    public long process(int k, int count, int num, long start, int bit) {
        int m = (bit-1) >> 1;
        int[] c = new int[bit];
        c[0] = 1;
        c[bit-1] = 1;
        Info info = getR(c, k);
        if (info.math) {
            start += info.result;
            count++;
            if (count == num ) {
                return start;
            }
        }
        int i1 = 1;
        while (true) {
            while (c[m] < k-1) {
                c[m]++;
                info = getR(c, k);
                if (info.math) {
                    start += info.result;
                    count++;
                    if (count == num ) {
                        return start;
                    }
                }
            }
            c[m] = 0;
            while (i1 < (bit-m) && (c[m+i1] == k-1)) {
                c[m+i1] = 0;
                c[m-i1] = 0;
                i1++;
            }
            if (i1 == bit-m) {
                break;
            } else {
                c[m+i1-1] = 0;
                c[m-i1+1] = 0;
                c[m + i1]++;
                c[m - i1]++;
                i1 = 1;
                info = getR(c, k);
                if (info.math) {
                    start += info.result;
                    count++;
                    if (count == num ) {
                        return start;
                    }
                }
            }
        }
        return process2(k, count, num, start, bit+1);
    }

    public long process2(int k, int count, int num, long start, int bit) {
        int ml = (bit-1) >> 1;
        int mr = ml+1;
        int[] c = new int[bit];
        c[0] = 1;
        c[bit-1] = 1;
        Info info = getR(c, k);
        if (info.math) {
            start += info.result;
            count++;
            if (count == num ) {
                return start;
            }
        }
        int i1 = 1;
        while (true) {
            while (c[ml] < k-1) {
                c[ml]++;
                c[mr]++;
                info = getR(c, k);
                if (info.math) {
                    start += info.result;
                    count++;
                    if (count == num ) {
                        return start;
                    }
                }
            }
            c[ml] = 0;
            c[mr] = 0;
            while (i1 < (bit-mr) && (c[mr+i1] == k-1)) {
                c[mr+i1] = 0;
                c[ml-i1] = 0;
                i1++;
            }
            if (i1 == bit-mr) {
                break;
            } else {
                c[mr+i1-1] = 0;
                c[ml-i1+1] = 0;
                c[mr + i1]++;
                c[ml - i1]++;
                i1 = 1;
                info = getR(c, k);
                if (info.math) {
                    start += info.result;
                    count++;
                    if (count == num ) {
                        return start;
                    }
                }
            }
        }
        return process(k, count, num, start, bit+1);
    }

    public Info getR(int[] c, int k) {
        long result = 0;
        for (int i = c.length-1; i >= 0; i--) {
            result = result + (long) (c[i] * Math.pow(k, i));
        }
        long a = result;
        return new Info(result, huiwen(a));
    }


    public boolean huiwen(long num) {
        long i = 1;
        while (num >= i*10) {
            i *= 10;
        }
        while (num != 0) {
            long l = num % 10;
            long l1 = num / i;
            if (l != l1) {
                return false;
            }
            num = num % i;
            num /= 10;
            i /= 100;
        }
        return true;
    }


    public class Info {
        public long result;
        public boolean math;
        public Info (long result, boolean math) {
            this.result = result;
            this.math = math;
        }
    }


    public static void main(String[] args) {
        Leetcode2081 leetcode2081 = new Leetcode2081();
        System.out.println(leetcode2081.kMirror(7, 17));
    }

}
