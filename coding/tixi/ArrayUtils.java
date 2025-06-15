package coding.tixi;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author linzherong
 * @date 2025/3/29 00:58
 */
public class ArrayUtils {

    public static int[] generalArr(int maxTime) {
        int time = (int) (Math.random() * maxTime) + 1;
        int[] arr = new int[time];
        for (int i = 0; i < time; i++) {
            arr[i] = (int) (Math.random() * 1000000);
        }
        return arr;
    }

    public static int[] generalArrNoRepeat(int maxTime) {
        int time = (int) (Math.random() * maxTime) + 1;
        BolFilter bolFilter = new BolFilter();
        int[] arr = new int[time];
        for (int i = 0; i < time; i++) {
            do {
                arr[i] = (int) (Math.random() * 1000000);
            } while (bolFilter.contain(arr[i]));
            bolFilter.add(arr[i]);
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
