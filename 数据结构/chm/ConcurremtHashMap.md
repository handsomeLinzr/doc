# ConcurrentHashMap源码解析
- 源码解析  
- new 一个CHM的时候，传入一个初始大小，实际上生成的大小是大于这个数的最近接2幂次方的数
```java
    public ConcurrentHashMap(int initialCapacity) {
        // 判断传入的初始大小不能小于0
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        // 大于等于 1<<<29, 则直接用最大容量  1 <<< 30，否则获取大于当前数的最近接近2的幂次方
        int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
            MAXIMUM_CAPACITY : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
        this.sizeCtl = cap;
    }
```
