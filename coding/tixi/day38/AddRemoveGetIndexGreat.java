package coding.tixi.day38;

/**
 *
 * add/remove/get(index)都很快
 *
 * @author linzherong
 * @date 2025/7/14 12:28
 */
public class AddRemoveGetIndexGreat {

    /**
     *  insert 、get、remove fast
     *  用 SBT 改造
     * @param <V>
     */
    public static class FastNode<V> {
        public V value;
        public FastNode<V> left;
        public FastNode<V> right;
        public int size;

        public FastNode(V value) {
            this.value = value;
            this.size = 1;
        }
    }

    public static class FastList<V> {
        public FastNode<V> root;
        public int size = 0;

        // 新增
        public void add(int index, V value) {
            if (index <= size) {
                size++;
                root = add(index, root, new FastNode<>(value));
            }
        }

        // 获取
        public V get(int index) {
            FastNode<V> node = get(root, index);
            return node == null ? null : node.value;
        }

        // 删除
        public void remove(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            size--;
            root = remove(root, index);
        }

        // 左旋
        private FastNode<V> leftRotate(FastNode<V> node) {
            // 右节点
            FastNode<V> right = node.right;
            node.right = right.left;
            right.left = node;
            right.size = node.size;
            node.size = (node.left == null?0:node.left.size)+(node.right==null?0:node.right.size) + 1;
            return right;
        }

        //右旋
        private FastNode<V> rightRotate(FastNode<V> node) {
            FastNode<V> left = node.left;
            node.left = left.right;
            left.right = node;
            left.size = node.size;
            node.size = (node.left == null?0:node.left.size)+(node.right==null?0:node.right.size) + 1;
            return left;
        }

        private FastNode<V> maintain(FastNode<V> node) {
            if (node == null) {
                return null;
            }
            // 左右节点数量
            int leftSize = node.left == null? 0: node.left.size;
            int rightSize = node.right == null? 0 : node.right.size;
            int leftLeftSize = node.left == null || node.left.left == null? 0 : node.left.left.size;
            int leftRightSize = node.left == null || node.left.right == null? 0 : node.left.right.size;
            int rightLeftSize = node.right == null || node.right.left == null? 0 : node.right.left.size;
            int rightRightSize = node.right == null || node.right.right == null? 0 : node.right.right.size;
            if (leftLeftSize > rightSize) {
                // LL
                node = rightRotate(node);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (leftRightSize > rightSize) {
                // LR
                node.left = leftRotate(node.left);
                node = rightRotate(node);
                node.left = maintain(node.left);
                node.right = maintain(node.right);
                node = maintain(node);
            } else if (rightLeftSize > leftSize) {
                // RL
                node.right = rightRotate(node.right);
                node = leftRotate(node);
                node.left = maintain(node.left);
                node.right =maintain(node.right);
                node = maintain(node);
            } else if (rightRightSize > leftSize) {
                // RR
                node = leftRotate(node);
                node.left = maintain(node.left);
                node = maintain(node);
            }
            return node;
        }

        private FastNode<V> add(int index, FastNode<V> cur, FastNode<V> node) {
            if (cur == null) {
                return node;
            }
            cur.size++;
            int leftHeadSize = cur.left == null ? 1 : cur.left.size+1;
            if (index < leftHeadSize) {
                cur.left = add(index, cur.left, node);
            } else {
                cur.right = add(index - leftHeadSize, cur.right, node);
            }
            return maintain(cur);
        }

        private FastNode<V> get(FastNode<V> node, int index) {
            if (node == null) {
                return null;
            }
            // node的位置
            int nodeIndex = node.left == null? 0 : node.left.size;
            if (nodeIndex == index) {
                return node;
            } else if (nodeIndex > index) {
                return get(node.left, index);
            } else {
                return get(node.right, index - nodeIndex - 1);
            }
        }

        private FastNode<V> remove(FastNode<V> node, int index) {
            if (node == null) {
                return null;
            }
            node.size--;
            int nodeIndex = node.left == null? 0 : node.left.size;
            if (index < nodeIndex) {
                node.left = remove(node.left, index);
            } else if (index > nodeIndex) {
                node.right = remove(node.right, index - nodeIndex - 1);
            } else {
                // nodeIndex == index，移除当前
                if (node.left == null && node.right == null) {
                    return null;
                } else if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    // left  right 都不为null， 获取 right 的 最左
                    FastNode<V> pre = null;
                    FastNode<V> right = node.right;
                    while (right.left != null) {
                        pre = right;
                        // 当前层数量减1
                        right.size--;
                        // 获取右边的最左
                        right = right.left;
                    }
                    if (pre != null) {
                        // pre 为 right 的父节点
                        pre.left = right.right;
                        right.right = node.right;
                    }
                    right.left = node.left;
                    right.size = right.left.size + (right.right == null? 0 : right.right.size) + 1;
                    node = right;
                }
            }
            return node;
        }
        public int size() {
            return size;
        }
    }

}
