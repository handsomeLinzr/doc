package coding.tixi;

/**
 * @author linzherong
 * @date 2025/6/15 13:43
 */
public class BolFilter {

    // 一个long可以存放 8 * 8 = 64位，一共有 8*8*8个位置
    public static int BIT = (1 << 9) - 1;
    public static Integer length = 1 << 9;
    public long[] words = new long[8];

    public void add(Integer value) {
        int h;
        int index = ((value == null) ? 0 : (h = value.hashCode()) ^ (h >>> 16)) & BIT;
        int wordIndex = index / 64;
        int offset = index & BIT;
        words[wordIndex] = words[wordIndex] | 1L<<offset;
    }

    public boolean contain(Integer value) {
        int h;
        int index = ((value == null) ? 0 : (h = value.hashCode()) ^ (h >>> 16)) & BIT;
        int wordIndex = index / 64;
        int offset = index & 63;
        return (words[wordIndex] & 1L<<offset) != 0;
    }

}
