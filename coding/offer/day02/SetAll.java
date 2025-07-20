package coding.offer.day02;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 自定义Map，要求log(1) 的复杂度，实现 setAll方法，将每个元素设置为同一个值
 *
 * 属性 time value
 *
 * @author linzherong
 * @date 2025/7/13 20:23
 */
public class SetAll {

    public static class ValueWrapper<V> {
        V value;
        long time;
        public ValueWrapper(V value, long time) {
            this.value = value;
            this.time = time;
        }
    }

    public static class StrongMap<K, V> {
        HashMap<K, ValueWrapper<V>> map;
        ValueWrapper<V> setAll = null;
        long current;

        public StrongMap() {
            map = new HashMap<>(16);
            current = 0;
        }

        public void put(K key, V value) {
            map.put(key, new ValueWrapper<>(value, current));
        }

        public void setAll(V value) {
            current++;
            setAll = new ValueWrapper<>(value, current);
        }

        public V get(K key) {
            ValueWrapper<V> vw = map.get(key);
            if (vw.time == current) {
                return vw.value;
            } else {
                return setAll.value;
            }
        }

        public String getAll() {
            if (map.isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<K, ValueWrapper<V>> entry : map.entrySet()) {
                ValueWrapper<V> value = entry.getValue();
                if (value.time < current) {
                    value = setAll;
                }
                sb.append(entry.getKey()).append(":").append(value.value).append(";");
            }
            return sb.substring(0, sb.length()-1);
        }

    }


    public static void main(String[] args) {
        StrongMap<Integer, String> myMap = new StrongMap<>();
        myMap.put(1, "zz1");
        myMap.put(1, "zz1");
        myMap.put(2, "zz2");
        myMap.put(3, "zz3");
        System.out.println(myMap.getAll());
        myMap.setAll("ALL");
        System.out.println(myMap.getAll());
        myMap.put(3, "another");
        System.out.println(myMap.getAll());
        myMap.put(4,"new");
        System.out.println(myMap.getAll());

    }


}
