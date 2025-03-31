package coding.tixi.day08;

import coding.tixi.ArrayUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * 1. 提供几个线段（给出开始和结尾位置），计算出这些线段有重合在一起的最大线段数【可以用系统提供的排序和数据结构】
 * 2.手动改写堆
 *  给定一个整型数组 int[] arr 和 布尔类型数组 boolean[] op, 两个数组一定等长，假设长度为N，arr[i] 表示客户编号，op[i] 表示用户行为，
 *  如 arr = [3, 1, 3, 2]
 *      op = [T, T, F, T]
 *   表示 客户3购买一件商品， 客户1购买一件商品，客户3退回一件商品， 客户2购买一件商品
 *
 *  HeapGreater 加强堆， peek  pop  push  remove(T t)  resign(T t) 将t这个元素重新调整
 *  加强堆，可以加反向索引表hashMap，其中对于进来的value，可以用自定义Inner包一层，避免基础类型在HashMap中比较后被覆盖
 *
 * @author linzherong
 * @date 2025/3/27 00:08
 */
public class HeapStrong {

    public static void main(String[] args) {
        HeapStrong heapStrong = new HeapStrong();
//        int[][] line = new int[4][];
//        line[0] = new int[]{1,5};
//        line[1] = new int[]{2,6};
//        line[2] = new int[]{3,5};
//        line[3] = new int[]{6,7};
//        System.out.println(heapStrong.maxCover1(line));
        heapStrong.testHeapStrong();
    }

    public int maxCover1(int[][] lines) {
        int min = lines[0][0];
        int max = lines[0][1];
        for (int i = 1; i < lines.length; i++) {
            min = Math.min(lines[i][0], min);
            max = Math.max(lines[i][1], max);
        }
        int maxCover = 0;
        for(double v = min + 0.5; v < max; v++) {
            int cover = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < v && lines[i][1] > v) {
                    cover++;
                }
            }
            maxCover = Math.max(maxCover, cover);
        }
        return maxCover;
    }
    public int maxCover2(int[][] lines) {

        return 0;
    }

    public void testHeapStrong() {
        for (int time = 0; time < 200_0000; time++) {
            HeapGreater<Integer> heapGreater = new HeapGreater<>(Integer::compareTo);
            PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
            int[] arr = ArrayUtils.generalArr(50);
            for (int i = 0; i < arr.length; i++) {
                double v = Math.random();
                if (v < 0.5) {
                    heapGreater.add(arr[i]);
                    heap.add(arr[i]);
                } else if (v < 0.75) {
                    if (heapGreater.heapSize > 0) {
                        if (!Objects.equals(heapGreater.peek(), heap.peek())) {
                            System.out.println("出错");
                        }
                    }
                } else {
                    if (heapGreater.heapSize > 0) {
                        if (!Objects.equals(heapGreater.pop(), heap.poll())) {
                            System.out.println("出错");
                        }
                    }
                }
            }
        }
        System.out.println("成功");
    }

}
