package coding.tixi.day07;

/**
 * 1. 大根堆，任何一个节点的二叉树，当前节点都是最大值（父节点大于子节点）
 * 2. heapInsert、heapSize、heapify（删除最大时，拿最后一个和堆顶替换，同时heapSize--，最后进行数据调整）
 * 3. PriorityQueue 默认是 小根堆
 * 4. 堆排序(大根堆，之后0和N-1交换，再把0到N-2调大根堆，周而复始)  O(N*logN)
 * 5. 一个几乎有序的数组（数组上每个位置，顺序调整后的位置和原位置相比，不超过K）要求用最快的方式进行排序
 *
 * @author linzherong
 * @date 2025/3/26 07:27
 */
public class Heap {

    public static void main(String[] args) {

    }

}
