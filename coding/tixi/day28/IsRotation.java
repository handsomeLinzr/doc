package coding.tixi.day28;

/**
 *
 * 判断 a串和和b串是否互为循环串 ， 如  abc 和 bca 和 cab 都是
 *
 * @author linzherong
 * @date 2025/6/28 19:51
 */
public class IsRotation {

    public static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        // 符合的情况下，将 a补全两倍，则 b必在其中
        // abcabc bca
        return getIndex(a+a, b) != -1;
    }

    public static int getIndex(String a, String b) {
        int L1 = 0;
        int L2 = 0;
        int pre = 0;
        char[] c1 = a.toCharArray();
        char[] c2 = b.toCharArray();
        int[] next = getNext(b);
        while (L1 < c1.length && L2 < c2.length) {
            if (c1[L1] == c2[L2]) {
                L1++;
                L2++;
            } else if (next[L2] >= 0) {
                L2 = next[L2];
            } else {
                L1++;
            }
        }
        return L2 == c2.length? L1 - c2.length : -1;
    }

    public static int[] getNext(String b) {
        char[] charArray = b.toCharArray();
        if (charArray.length == 0) {
            return new int[]{-1};
        }
        int[] next = new int[charArray.length];
        next[0] = -1;
        next[1] = 0;
        int pre = 0;
        int index = 2;
        while (index < charArray.length) {
            if (charArray[index-1] == charArray[pre]) {
                next[index++] = ++pre;
            } else if (pre > 0) {
                pre = next[pre];
            } else {
                index++;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str1 = "aabbcc";
        String str2 = "ccaabb";
        System.out.println(isRotation(str1, str2));

    }

}
