# ConcurrentHashMap源码解析
- 源码解析  
- new 一个CHM的时候，传入一个初始大小，实际上生成的大小是大于这个数的最近接2幂次方的数
- sizeCtl含义
  - -1：正在初始化/扩容转移数据，在初始化的时候会设置
- ForwardingNode 含义
  - 正在扩容
```
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
- put 源码注释解析
```
    final V putVal(K key, V value, boolean onlyIfAbsent) {
        // key 和 value 都不能为空
        if (key == null || value == null) throw new NullPointerException();
        // 计算特定计算的 hash 值，这里用的 hash ^ hash的高16位，再获取低31位，可以使得分布更加均匀
        int hash = spread(key.hashCode());
        int binCount = 0;
        
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            if (tab == null || (n = tab.length) == 0)
                // tab为空或者长度为0，进行初始化
                tab = initTable();
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                // n-1 刚好为每个位都是 1 的数据，与上 hash 得到当前这个值对应在 tab 的位置，tabAt 则获取到这个数据，为空的情况，直接CAS赋值即可
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))   // 不用加锁
                    break;                   // no lock when adding to empty bin
            }
            else if ((fh = f.hash) == MOVED)   // MOVED = -1， hash = -1 说明当前正在转移数据
                tab = helpTransfer(tab, f);   // 帮忙转移数据
            else {
                V oldVal = null;
                synchronized (f) {  // 表中下标位置头部加锁
                    if (tabAt(tab, i) == f) {   // synchronized 双重判断
                        if (fh >= 0) {   // 平常情况 hash > 0
                            binCount = 1;  // 记录层级
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {  // 判断 e.key 和 传入的 key 一毛一样
                                    oldVal = e.val;  // 缓存旧的value
                                    if (!onlyIfAbsent)   // 默认 onlyIfAbsent 为false，如果传入 true，则不替换，只有在没设置过这个key的情况才设置
                                        e.val = value;    // 替换
                                    break;  // 走到这里，则此时覆盖旧数据了，跳出循环
                                }
                                Node<K,V> pred = e;
                                if ((e = e.next) == null) {  // 没有下一个了，则说明之前没有设置过
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);   // 直接追加到链尾，next = null
                                    break;  // 走到这里，已经追加到链尾，跳出循环
                                }
                            }
                        }
                        else if (f instanceof TreeBin) {    // 该节点属于红黑树节点
                            Node<K,V> p;
                            binCount = 2;     // 标识
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {   // 获取红黑树对应的节点
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
```
- 初始化  
```
    private final Node<K,V>[] initTable() {
        Node<K,V>[] tab; int sc;
        while ((tab = table) == null || tab.length == 0) {
            // 自旋处理
            if ((sc = sizeCtl) < 0)
                // 如果 sizeCtl < 0 说明要么在初始化，要么在转移
                Thread.yield(); // 让出cpu执行权
            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                // cas 设置 sizeCtl 的值为 -1成功，-1 表示正在初始化
                try {
                    if ((tab = table) == null || tab.length == 0) {  // 再次判断
                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;  // 默认 DEFAULT_CAPACITY = 16
                        @SuppressWarnings("unchecked")
                        Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];   // 创建node数组，作为table
                        table = tab = nt;
                        sc = n - (n >>> 2);   // sc = 下个扩容阈值，其实就是  0.75 * size
                    }
                } finally {
                    sizeCtl = sc;    // 复原，将 sc 赋值给 sizeCtl，存储下个扩容阈值
                }
                break;
            }
        }
        return tab;
    }
```
- 帮忙转移数据
```
    final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
        Node<K,V>[] nextTab; int sc;
        // tab不为空，且正在转移
        if (tab != null && (f instanceof ForwardingNode) &&
            (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
            int rs = resizeStamp(tab.length) << RESIZE_STAMP_SHIFT;   // 获取tab长度生成的扩容戳，并且放到高16位
            while (nextTab == nextTable && table == tab &&
                   (sc = sizeCtl) < 0) {              // 正在扩容中
                if (sc == rs + MAX_RESIZERS || sc == rs + 1 ||
                    transferIndex <= 0)
                    break;
                if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) {
                    transfer(tab, nextTab);
                    break;
                }
            }
            return nextTab;
        }
        return table;
    }
```
- 计算扩容戳
```
    static final int resizeStamp(int n) {
        // 获取 n 高位的0的个数
        // 1 << (16-1) ==>> 1 左移 15位
        // 相或操作，所以最后扩容戳 ==>> 第16位为1，低位表示 n 高位0的个数的值
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
```
