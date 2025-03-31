package coding.tixi;

/**
 * @author linzherong
 * @date 2025/3/30 17:47
 */
public class BinaryTreeNode {

    public Integer value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode() {}
    public BinaryTreeNode(Integer value) {
        this.value = value;
    }
    public BinaryTreeNode(Integer value, BinaryTreeNode left, BinaryTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
