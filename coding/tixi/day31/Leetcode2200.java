package coding.tixi.day31;

import coding.tixi.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzherong
 * @date 2025/6/24 09:28
 */
public class Leetcode2200 {

    public List<Integer> findKDistantIndices(int[] nums, int key, int k) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        int start;
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == key) {
                start = Math.max(i-k, end);
                end = Math.min(i+k, nums.length-1);
                for (int j = start; j <= end; j++) {
                    result.add(j);
                }
                end ++;
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        Leetcode2200 leetcode2200 = new Leetcode2200();
//        int[] arr = {2,2,2,2,2};
//        List<Integer> a = leetcode2200.findKDistantIndices(arr, 2, 2);
//        System.out.println(a);
    }


    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        return isSame(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
    public boolean isSame(TreeNode root, TreeNode suRoot) {
        if (root == null && suRoot == null) {
            return true;
        }
        if (root == null || suRoot == null) {
            return false;
        }
        return root.value == suRoot.value && isSame(root.left, suRoot.left) && isSame(root.right, suRoot.right);
    }


}
