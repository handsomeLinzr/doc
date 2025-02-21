# ConcurrentHashMap源码解析
- 源码解析  
- new 一个CHM的时候，传入一个初始大小，实际上生成的大小是大于这个数的最近接2幂次方的数
- sizeCtl含义
  - -1：正在初始化/扩容转移数据，在初始化的时候会设置
- ForwardingNode 含义
  - 正在扩容
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
- put 源码注释解析
```java
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
                    if (binCount >= TREEIFY_THRESHOLD)   // 层级大于等于8，这里统计的是添加天的层级
                        treeifyBin(tab, i);    // 树化
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
```java
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
```java
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
```java
    static final int resizeStamp(int n) {
        // 获取 n 高位的0的个数
        // 1 << (16-1) ==>> 1 左移 15位
        // 相或操作，所以最后扩容戳 ==>> 第16位为1，低位表示 n 高位0的个数的值
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
```
-- 红黑树化
```java
    private final void treeifyBin(Node<K,V>[] tab, int index) {
        Node<K,V> b; int n, sc;
        if (tab != null) {
            if ((n = tab.length) < MIN_TREEIFY_CAPACITY)  // 数据量小于 64， 则进行扩容，不做树化操作
                tryPresize(n << 1);  // 扩容，传入的数为表长度的两倍
            else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
                // 红黑树化
                synchronized (b) {
                    if (tabAt(tab, index) == b) {
                        TreeNode<K,V> hd = null, tl = null;
                        for (Node<K,V> e = b; e != null; e = e.next) {
                            TreeNode<K,V> p =
                                new TreeNode<K,V>(e.hash, e.key, e.val,
                                                  null, null);
                            if ((p.prev = tl) == null)
                                hd = p;
                            else
                                tl.next = p;
                            tl = p;
                        }
                        setTabAt(tab, index, new TreeBin<K,V>(hd));
                    }
                }
            }
        }
    }
```
- 尝试扩容
```java
    private final void tryPresize(int size) {
        // 判断传入的数是不是大于最大容量的一半，最大容量 = 1 << 30
        // 若是，则 c = 最大容量；若不是，则 c = 大于传入的数，且最近的2的幂次方
        int c = (size >= (MAXIMUM_CAPACITY >>> 1)) ? MAXIMUM_CAPACITY :
            tableSizeFor(size + (size >>> 1) + 1);
        int sc;
        while ((sc = sizeCtl) >= 0) {  // sc用来缓存 sizeCtl 的值，大于等于0则是正常的情况，表示当前没有其他线程在扩容
            Node<K,V>[] tab = table; int n;
            if (tab == null || (n = tab.length) == 0) {   // 当前还没有tab，或者没有数据
                n = (sc > c) ? sc : c;     // 获取 sizeCtl 和 c 中最大的那个
                if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {  // cas 设置 sizeCtl = -1，设置成功后，可进入初始化阶段
                    try {
                        if (table == tab) {  // 再次判断
                            @SuppressWarnings("unchecked")
                            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];   // 创建扩容后的数组
                            table = nt;     // 将扩容后的数组赋值给 table
                            sc = n - (n >>> 2);    // sc 记录下个扩容阈值，即  length * 0.75
                        }
                    } finally {
                        sizeCtl = sc;
                    }
                }
            }
            else if (c <= sc || n >= MAXIMUM_CAPACITY)
                // c <= sc  说明已经有别的线程扩容完成了
                // n >= MAXIMUM_CAPACITY  说明已经达到chm的最大量，不能扩了
                break;
            else if (tab == table) {  // 继续判断，是否table没被别的线程改到，是则可以进行扩容
                int rs = resizeStamp(n);   // n为扩容目标容量，根据n获取当前的扩容戳 rs， rs ==>>  第16位为1，其他的表示 n 左边有多少个0
                if (sc < 0) {    // sc < 0 则说明有其他线程已经在进行扩容了
                    Node<K,V>[] nt;
                    if ((sc >>> RESIZE_STAMP_SHIFT) != rs || sc == rs + 1 ||
                        sc == rs + MAX_RESIZERS || (nt = nextTable) == null ||
                        transferIndex <= 0)
                        // sc >>> 16  != rs  说明不是当前的扩容戳，可能别的线程已经扩容完毕
                        // sc == rs + 1 说明扩容结束（因为当前其他线程在扩容，但是 sc == rs+1 说明正在扩容的线程数 = 0，则只有扩容结束了才会出现这样）
                        // sc == rs + MAX_RESIZERS  已经达到最大扩容线程数
                        // nextTable == null  没有需要进行转移的数
                        // transferIndex <= 0
                        break;
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1)) // cas设置 sizeCtl = sc + 1，表示多一个线程（当前线程）也加入扩容了
                        // 设置成功，进入扩容， nt
                        transfer(tab, nt);
                }
                // rs << 16 位，即将原来的数放到高16位，低16位表示表示有 n-1 个线程在扩容；这里 +2 是因为 +1+1，1表示当前一个线程，两一个1是作为固定标识
                // 至此，sizeCtl ==>> 最高位是1，接下来15位表示扩容后大小，最后16位表示当前扩容线程数+1
                // cas 设置 sizeCtl
                else if (U.compareAndSwapInt(this, SIZECTL, sc,
                                             (rs << RESIZE_STAMP_SHIFT) + 2))
                    // cas 成功，进入扩容
                    transfer(tab, null);
            }
        }
    }
```
- 扩容和复制转移数据
```java
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
        int n = tab.length, stride;
        if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
            stride = MIN_TRANSFER_STRIDE; // subdivide range   一个线程处理的数量最小为16
        if (nextTab == null) {            // initiating  最开始进行扩容
            try {
                @SuppressWarnings("unchecked")
                Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];   // 扩大一倍
                nextTab = nt;   // 赋值给  nextTab
            } catch (Throwable ex) {      // try to cope with OOME
                sizeCtl = Integer.MAX_VALUE;
                return;
            }
            nextTable = nextTab;   // 赋值给了全员变量  nextTable
            transferIndex = n;     // 赋值 n，表示当前转移的是 n 位置
        }
        int nextn = nextTab.length;
        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab
        for (int i = 0, bound = 0;;) {
            Node<K,V> f; int fh;
            while (advance) {
                int nextIndex, nextBound;
                if (--i >= bound || finishing)
                    advance = false;
                else if ((nextIndex = transferIndex) <= 0) {
                    i = -1;
                    advance = false;
                }
                else if (U.compareAndSwapInt
                         (this, TRANSFERINDEX, nextIndex,
                          nextBound = (nextIndex > stride ?
                                       nextIndex - stride : 0))) {
                    bound = nextBound;
                    i = nextIndex - 1;
                    advance = false;
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
                int sc;
                if (finishing) {
                    nextTable = null;
                    table = nextTab;
                    sizeCtl = (n << 1) - (n >>> 1);
                    return;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                        return;
                    finishing = advance = true;
                    i = n; // recheck before commit
                }
            }
            else if ((f = tabAt(tab, i)) == null)
                advance = casTabAt(tab, i, null, fwd);
            else if ((fh = f.hash) == MOVED)
                advance = true; // already processed
            else {
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        Node<K,V> ln, hn;
                        if (fh >= 0) {
                            int runBit = fh & n;
                            Node<K,V> lastRun = f;
                            for (Node<K,V> p = f.next; p != null; p = p.next) {
                                int b = p.hash & n;
                                if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            if (runBit == 0) {
                                ln = lastRun;
                                hn = null;
                            }
                            else {
                                hn = lastRun;
                                ln = null;
                            }
                            for (Node<K,V> p = f; p != lastRun; p = p.next) {
                                int ph = p.hash; K pk = p.key; V pv = p.val;
                                if ((ph & n) == 0)
                                    ln = new Node<K,V>(ph, pk, pv, ln);
                                else
                                    hn = new Node<K,V>(ph, pk, pv, hn);
                            }
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                        else if (f instanceof TreeBin) {
                            TreeBin<K,V> t = (TreeBin<K,V>)f;
                            TreeNode<K,V> lo = null, loTail = null;
                            TreeNode<K,V> hi = null, hiTail = null;
                            int lc = 0, hc = 0;
                            for (Node<K,V> e = t.first; e != null; e = e.next) {
                                int h = e.hash;
                                TreeNode<K,V> p = new TreeNode<K,V>
                                    (h, e.key, e.val, null, null);
                                if ((h & n) == 0) {
                                    if ((p.prev = loTail) == null)
                                        lo = p;
                                    else
                                        loTail.next = p;
                                    loTail = p;
                                    ++lc;
                                }
                                else {
                                    if ((p.prev = hiTail) == null)
                                        hi = p;
                                    else
                                        hiTail.next = p;
                                    hiTail = p;
                                    ++hc;
                                }
                            }
                            ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                                (hc != 0) ? new TreeBin<K,V>(lo) : t;
                            hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                                (lc != 0) ? new TreeBin<K,V>(hi) : t;
                            setTabAt(nextTab, i, ln);
                            setTabAt(nextTab, i + n, hn);
                            setTabAt(tab, i, fwd);
                            advance = true;
                        }
                    }
                }
            }
        }
    }
```

