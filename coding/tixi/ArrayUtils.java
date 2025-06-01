package coding.tixi;

/**
 * @author linzherong
 * @date 2025/3/29 00:58
 */
public class ArrayUtils {

    public static int[] generalArr(int maxTime) {
        int time = (int) (Math.random() * maxTime) + 1;
        int[] arr = new int[time];
        for (int i = 0; i < time; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        return arr;
    }

    public static int[] generalArr(int maxTime, int minTime) {
        int time = (int) (Math.random() * maxTime) + minTime;
        int[] arr = new int[time];
        for (int i = 0; i < time; i++) {
            arr[i] = (int) (Math.random() * 100) ;
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }

}
