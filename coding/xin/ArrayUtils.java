package coding.xin;

/**
 * @author linzherong
 * @date 2025/3/15 18:56
 */
public class ArrayUtils {
    public static int[] generalArray(int length, int max) {
        length = (int) (Math.random() * length) + 1;
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            int value = (int) (Math.random() * max) + 1;
            arr[i] = random() == 0? value : -value;
        }
        return arr;
    }

    /**
     * 相邻不相等
     * @param length
     * @param max
     * @return
     */
    public static int[] nextNoEqArray(int length, int max) {
        length = (int) (Math.random() * length) + 1;
        int[] array = new int[length];
        int last = 0;
        for (int i = 0; i < length; i++) {
            int num;
            do {
                num = (int) ((Math.random() * max) - (Math.random() * max));
            } while (num == last);
            array[i] = num;
            last = num;
        }
        return array;
    }

    private static int random() {
        return Math.random() > 0.5? 1:0;
    }

    public static int[] copy(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

}
