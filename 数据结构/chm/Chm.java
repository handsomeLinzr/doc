package 数据结构.chm;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linzherong
 * @date 2025/2/20 13:17
 */
public class Chm {

    public static void main(String[] args) {
        ConcurrentHashMap<A, String> m = new ConcurrentHashMap<>();
        m.put(new A(4), "1");
        m.put(new A(4), "1");
        m.put(new A(4), "1");
        m.put(new A(4), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");
        m.put(new A(4), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");
        m.put(new A(36), "1");


    }

    static class A {
        private int code;
        public A (int code) {
            this.code = code;
        }
    }



}
