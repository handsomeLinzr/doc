package coding.xin;

/**
 * @author linzherong
 * @date 2025/3/22 10:32
 */
public class TreeNodeUtils {

    public static TreeNode buildTreeNode(int[][] arr) {
        return process(arr, 0, 0);
    }

    public static TreeNode process(int[][] arr, int i, int j) {
        if (i >= arr.length) {
            return null;
        }
        int[] curArr = arr[i];
        if (j >= curArr.length) {
            return null;
        }
        TreeNode node = new TreeNode(curArr[j]);
        node.left = process(arr, i+1, j*2);
        node.right = process(arr, i+1, j*2+1);
        return node;
    }

}
