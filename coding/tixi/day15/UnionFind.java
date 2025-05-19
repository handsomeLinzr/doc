package coding.tixi.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 并查集   952   547    305
 *
 * @author linzherong
 * @date 2025/5/11 15:16
 */
public class UnionFind {

    public static class UnionFindList<V> {
        public Map<V, V> parentMap;
        public Map<V, Integer> sizeMap;

        public UnionFindList(List<V> list) {
            parentMap = new HashMap<>(list.size());
            sizeMap = new HashMap<>(list.size());
            list.forEach(v -> {
                parentMap.put(v, v);
                sizeMap.put(v, 1);
            });
        }

        public boolean isSameParent(V a, V b) {
            return findParent(a) == findParent(b);
        }

        public void union(V a, V b) {
            V aF = findParent(a);
            V bF = findParent(b);
            if (aF == bF) {
                return;
            }
            Integer aSize = sizeMap.get(aF);
            Integer bSize = sizeMap.get(bF);
            // 小合并到大
            V small = aSize > bSize? bF : aF;
            V big = small == bF? aF : bF;
            parentMap.put(small, big);
            sizeMap.put(big, aSize + bSize);
            sizeMap.remove(small);
        }

        public int sets() {
            return sizeMap.size();
        }

        /**
         * 获取顶级节点
         * @param v
         * @return
         */
        private V findParent(V v) {
            V p = parentMap.get(v);
            List<V> tempList = new ArrayList<>();
            while (v != p) {
                tempList.add(v);
                v = p;
                p = parentMap.get(v);
            }
            // 优化点，沿途的数据的节点都指向最后一个parent
            for (V v1 : tempList) {
                parentMap.put(v1, p);
            }
            return p;
        }

    }

}
