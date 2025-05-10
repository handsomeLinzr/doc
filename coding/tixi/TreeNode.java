package coding.tixi;

/**
 * @author linzherong
 * @date 2025/5/5 19:42
 */
public class TreeNode {

    public Integer value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {}
    public TreeNode(Integer value) {
        this.value = value;
    }
    public TreeNode(Integer value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }


}
