package coding.offer.day01;

/**
 *
 * 最接近2的n次方
 *
 * @author linzherong
 * @date 2025/7/18 21:19
 */
public class Near2Power {


    // 已知n是正数
    // 返回大于等于，且最接近n的，2的某次方的值
    public static int tableSizeFor(int n) {
        int i = n - 1;
        i |= i >>1;
        i |= i >>2;
        i |= i >>4;
        i |= i >>8;
        i |= i >>16;
        return i<0? 1 : i+1;
    }

    public static void main(String[] args) {
        int cap = 120;
        System.out.println(tableSizeFor(cap));
    }

}
