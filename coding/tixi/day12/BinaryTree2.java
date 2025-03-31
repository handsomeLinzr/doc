package coding.tixi.day12;

/**
 * 1. 二叉树宽度按层遍历
 *      定义一个队列，有左入左，有右入右
 * 2. 二叉树的序列化和反序列化
 *      先序序列化  preSerial  Queue<String>  递归
 *      先序反序列化  preDserial  递归
 *      中序没有
 *      后序有，可以不管
 *      宽度序列化和反序列化  levelSerial  levelDserial
 * 3. 多叉数的序列化和反序列化（转二叉树）
 * 4. 打印二叉树
 * 5. 求二叉树最宽的层
 * 6. 给一个节点，获取其后继节点[后继节点：中序遍历时，该节点的下一个]（给的节点为二叉树节点，有 val， left， right， parent）
 *      右子树的最左节点 || 父节点是某个节点的左子树，则是那个节点
 * 7. 折纸条
 *
 * @author linzherong
 * @date 2025/3/30 20:37
 */
public class BinaryTree2 {
}
