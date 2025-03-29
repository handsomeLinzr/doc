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
            arr[i] = (int) (Math.random() * 100) - (int) (Math.random() * 100);
        }
        return arr;
    }

}
