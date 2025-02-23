# ConcurrentHashMap源码解析
- 源码解析  
- new 一个CHM的时候，传入一个初始大小，实际上生成的大小是大于这个数的最近接2幂次方的数
- sizeCtl含义
  - -1：正在初始化/或者正在扩容
  - 正数：表示下个扩容阈值，也就是扩容阈值，当size >= sizeCtl的时候，则扩容
  - 0：表示还未初始化，此时table为null，sizeCtl为0，此时会进行初始化
  - 负数（-1除外）：表示正在扩容，其中高16位表示扩容戳，低16位-1得到的数，表示有这个数量的线程在进行转移数据
- ForwardingNode : 记录已经进行转移的数据，在扩容时需要将table的数据转移到新的table，转移完成则将原table对应位置的数据替换为这个
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
        addCount(1L, binCount);    // 数量+1
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
- 计算扩容戳，用于表示当前要扩容的n，可以用于表示一次扩容
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
                        sizeCtl = sc;  // 将 sc 赋值给 sizeCtl，存储下个扩容阈值
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
                // 创建一个扩大一倍的table
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
        // 扩容后的大小
        int nextn = nextTab.length;
        // 创建 fwd 表示正在迁移
        ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
        boolean advance = true;
        boolean finishing = false; // to ensure sweep before committing nextTab  是否已经完成
        for (int i = 0, bound = 0;;) {
            Node<K,V> f; int fh;
            while (advance) {
                int nextIndex, nextBound;
                if (--i >= bound || finishing)  // 如果 i >= bound则分配转移的数据已转移完，后续可以先跳出循环
                    advance = false;  
                else if ((nextIndex = transferIndex) <= 0) {  //没有下个能分配转移任务的槽了，后续跳出循环
                    i = -1;
                    advance = false;
                }
                else if (U.compareAndSwapInt
                         (this, TRANSFERINDEX, nextIndex,
                          nextBound = (nextIndex > stride ?
                                       nextIndex - stride : 0))) {   
                    // cas 设置 transferIndex = transferIndex - stride，表示当前线程处理 [transferIndex, nextBound) 位置的数据,
                    // nextBound 表示下个要转移的数据的组的开头
                    bound = nextBound;   // bound 赋值当前转移的最后一个位置
                    i = nextIndex - 1; // i为开始转移的位置
                    advance = false;   // advance = false, 表示已经分配了，跳出循环
                }
            }
            if (i < 0 || i >= n || i + n >= nextn) {
                // i < 0，则当前转移任务已经完成
                // i >= n 和 i + n >= nextn，说明已经完成
                int sc;
                if (finishing) {   // finishing = true 说明已经完成转移的后续处理，即已经把 sizeCtl 做了减1操作，表示当前线程已经完成转移任务
                    nextTable = null;    // 复原
                    table = nextTab;     // 将 nextTab 这个新表赋值给 table
                    sizeCtl = (n << 1) - (n >>> 1);   // sizeCtl = 0.75 * 长度，表示扩容阈值
                    return;   // 跳出循环
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {     // cas 设置 sizeCtl = sc - 1，表示当前线程已经完成转移任务
                    if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                        return;
                    finishing = advance = true;    // 设置为true，表示当前线程已经完成转移任务
                    i = n; // recheck before commit   把i重新赋值n，之后继续循环检查一遍
                }
            }
            else if ((f = tabAt(tab, i)) == null)  // i = 当前要处理的位置，这个位置没有数据的情况
                advance = casTabAt(tab, i, null, fwd);    // 放入刚刚的节点
            else if ((fh = f.hash) == MOVED)      // 这个位置的数据的值，hash = -1，说明已经在处理了
                advance = true; // already processed   跳过
            else {
                synchronized (f) {     // 加锁
                    if (tabAt(tab, i) == f) {   // 再次判断，tab[i] == f，说明没有其他线程在处理，可以进行转移
                        Node<K,V> ln, hn;   // 定义临时变量
                        if (fh >= 0) {      // 正常情况，fh = 节点的hash值
                            int runBit = fh & n;   // 获取对应的hash的高位
                            Node<K,V> lastRun = f;
                            // 这个for循环，是为了找到当前链表的最后一个节点，以及最后一个节点的hash值的高位
                            // 如果高位是1，则放到ln，否则放到hn
                            for (Node<K,V> p = f.next; p != null; p = p.next) {   // p为下一位
                                int b = p.hash & n;   // p的hash的高位
                                if (b != runBit) {   // 如果当前节点和上一次节点的hash值不一样，说明需要分到两个链表了
                                    runBit = b;
                                    lastRun = p;
                                }
                            }
                            // 最后的结果是，lastRun 指向最后放在一起的串的头部，runBit表示这个串的高位是0还是1
                            if (runBit == 0) {  // 高位为0，说明则
                                ln = lastRun;
                                hn = null;
                            }
                            else {
                                hn = lastRun;
                                ln = null;
                            }
                            for (Node<K,V> p = f; p != lastRun; p = p.next) {  // 开始遍历转移
                                // 当 p == lastRun 的时候，后续不再需要编译，因为最后的串是一起的，而且已经放在对应链的头部了
                                int ph = p.hash; K pk = p.key; V pv = p.val;
                                if ((ph & n) == 0)    
                                    // 放到低位链
                                    // 创建一个新节点，新节点的next指向原ln
                                    ln = new Node<K,V>(ph, pk, pv, ln);
                                else
                                    // 同理放高位链
                                    hn = new Node<K,V>(ph, pk, pv, hn);
                            }
                            setTabAt(nextTab, i, ln);   // 新表低位
                            setTabAt(nextTab, i + n, hn);   // 新表高位
                            setTabAt(tab, i, fwd);    // 放入一个ForwardingNode，表示已经处理完了
                            advance = true;    // 当前处理完了，修改标识
                        }
                        else if (f instanceof TreeBin) {   // f instanceof TreeBin，说明是红黑树节点，需要特殊处理
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
- 数量+1
```java
    private final void addCount(long x, int check) {
        CounterCell[] as; long b, s;
        // counterCells != null，说明前边已经出现了并发情况，初始化了 counterCells
        // 如果 counterCells 为空，说明没有并发，此时可以用 cas 方式更新 baseCount 的值
        if ((as = counterCells) != null ||
            !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
            // 如果有并发，或者是 cas 更新不成功，则需要处理成 CounterCell 的计数方式，减少并发冲突
            CounterCell a; long v; int m;
            boolean uncontended = true;   // 定义标识，默认是没有冲突
            // as 为空， as长度为0  
            // as[当前线程对应位置] 的值为空
            // cas 设置  as[当前线程对应位置] 的值 + x  失败
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
                !(uncontended =
                  U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
                // 以上任一失败，需要调用 fullAddCount 进行累加
                fullAddCount(x, uncontended);
                return;
            }
            if (check <= 1)
                return;
            s = sumCount();
        }
        if (check >= 0) {
            Node<K,V>[] tab, nt; int n, sc;
            // s为当前table的数量大小
            // 当 数量 大于等于 扩容阈值  且  table不为空  且   数量小于最大容量
            // 需要进行扩容
            while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
                   (n = tab.length) < MAXIMUM_CAPACITY) {
                // 计算当前的扩容戳，然后左移16位
                int rs = resizeStamp(n) << RESIZE_STAMP_SHIFT;
                if (sc < 0) {   // sc 小于0，则当前正在进行扩容，看看需不需要帮忙数据转移
                    // sc == rs + MAX_RESIZERS，是否已达最大线程数库容
                    // sc == rs + 1 说明当前没有在扩容的线程，转移数据已完成
                    // nt = nextTable) == null 
                    // transferIndex <= 0 说明扩容转移数据的任务已经被领完了
                    if (sc == rs + MAX_RESIZERS || sc == rs + 1 ||
                        (nt = nextTable) == null || transferIndex <= 0)
                        break;
                    // cas 设置 sizeCtl，表示帮忙扩容转移数据的线程+1
                    if (U.compareAndSwapInt(this, SIZECTL, sc, sc + 1))
                        // 转移数据
                        transfer(tab, nt);
                }
                // 开始扩容
                else if (U.compareAndSwapInt(this, SIZECTL, sc, rs + 2))
                    transfer(tab, null);
                // 统计当前数量赋值s
                s = sumCount();
            }
        }
    }
```
- 累加
```java
    private final void fullAddCount(long x, boolean wasUncontended) {
        int h;
        // 获取当前线程的 probe 值，每个线程唯一，如果为0，说明之前还没有初始化过，则不会有冲突
        if ((h = ThreadLocalRandom.getProbe()) == 0) {
            ThreadLocalRandom.localInit();      // force initialization
            h = ThreadLocalRandom.getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (;;) {  // 自旋操作
            CounterCell[] as; CounterCell a; int n; long v;
            if ((as = counterCells) != null && (n = as.length) > 0) {
                if ((a = as[(n - 1) & h]) == null) {    // countterCells对应当前线程的位置上数据为null时
                    if (cellsBusy == 0) {            // Try to attach new Cell   // 当前没在初始化
                        CounterCell r = new CounterCell(x); // Optimistic create  创建新的 cc
                        if (cellsBusy == 0 &&
                            U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {   // cas 获取 cellsBusy锁
                            boolean created = false;
                            try {               // Recheck under lock  锁里再次校验
                                CounterCell[] rs; int m, j;
                                if ((rs = counterCells) != null &&
                                    (m = rs.length) > 0 &&
                                    rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;  // 在位置上赋值
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;  // 解锁
                            }
                            if (created)
                                break;    // 退出循环
                            continue;           // Slot is now non-empty 如果created还是false说明rs位置有其他值，需要重新循环
                        }
                    }
                    collide = false;
                }
                else if (!wasUncontended)       // CAS already known to fail
                    wasUncontended = true;      // Continue after rehash
                else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))  // cas给当前的cc加上x
                    break;
                else if (counterCells != as || n >= NCPU)  
                    collide = false;            // At max size or stale 已经达到最大的cc数量
                else if (!collide)
                    collide = true;
                else if (cellsBusy == 0 &&
                         U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {  // 加锁
                    try {
                        if (counterCells == as) {// Expand table unless stale
                            CounterCell[] rs = new CounterCell[n << 1];   // 扩容，2倍
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];   // 转移
                            counterCells = rs;    // 赋值
                        }
                    } finally {
                        cellsBusy = 0;   // 解锁
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = ThreadLocalRandom.advanceProbe(h);
            }
            // counterCells为空，创建 counterCells，加锁
            // 因为只在第一次冲突的时候才需要创建，所以用这里的 cas + cellsBusy加锁，可以增加效率
            else if (cellsBusy == 0 && counterCells == as &&
                     U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) { 
                boolean init = false;
                try {                           // Initialize table
                    if (counterCells == as) {
                        CounterCell[] rs = new CounterCell[2];  // 初始化一个 CounterCell 数组，数量为2
                        rs[h & 1] = new CounterCell(x);   // 赋值
                        counterCells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;   // 解锁
                }
                if (init)
                    break;   // 初始化换成，跳出循环
            }
            else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))   // cellsBusy 锁获取不到，重新尝试加到 baseCount 上
                break;                          // Fall back on using base
        }
    }
```
