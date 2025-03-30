package coding.tixi.day08;

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

    }

    public int maxCover1(int[][] lines) {

        return 0;
    }

}
