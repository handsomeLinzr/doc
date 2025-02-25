# AQS 解析
- aqs：AbstractQueuedSynchronizer  抽象同步器
- 扩展得到的相关锁类：ReentrantLock/ReentrantReadWriteLock/Semaphore/CountDownLatch/CyclicBarrier/Phaser/Exchanger
- AQS 类方法
```java
    private transient volatile Node head;  // 队列头节点
    private transient volatile Node tail;    // 尾节点
    private volatile int state;   // 队列状态
```
- 插入节点，插入尾部
```java
    private Node enq(final Node node) {
        for (;;) {   // 自旋
            Node t = tail;   
            if (t == null) { // Must initialize 
                if (compareAndSetHead(new Node()))   // 初始化头节点
                    tail = head;   // 尾节点指向头结点，同一个
            } else {
                // 尾插法，将 node 节点插入尾部，然后tail指向这个node
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }
```
- 根据指定模式添加节点到尾部
```java
private Node addWaiter(Node mode) {  
        // 创建 node 节点，线程为当前线程，模式为给定模式_
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            // tail 不为空， 尾插法将 node 插入到尾部
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        // tail 为空，则走添加节点方法
        enq(node);
        return node;
    }
```
- 唤醒下一个需要被唤醒的节点，唤醒前会将自己的 status 设置为 0
```java
    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;
        if (ws < 0)    
            // 在唤醒下一个节点前，会将当前节点（头节点）的状态设置为 0
            compareAndSetWaitStatus(node, ws, 0);

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }
```

## Node 节点
```java
/** Marker to indicate a node is waiting in shared mode */ 
static final Node SHARED = new Node();   // 共享锁模式
/** Marker to indicate a node is waiting in exclusive mode */
static final Node EXCLUSIVE = null;    // 排他锁模式

/** waitStatus value to indicate thread has cancelled */
static final int CANCELLED =  1;    // 取消
/** waitStatus value to indicate successor's thread needs unparking */
static final int SIGNAL    = -1;     // 需要被唤醒
/** waitStatus value to indicate thread is waiting on condition */
static final int CONDITION = -2;      // 等待中
/**
 * waitStatus value to indicate the next acquireShared should
 * unconditionally propagate
 */
static final int PROPAGATE = -3;   // 下个共享锁要直接解锁传播

volatile int waitStatus;  // 状态
volatile Node prev;   // 上一个节点
volatile Node next;   // 下一个节点
volatile Thread thread;   // 这个节点对应的线程
Node nextWaiter;    // 队列中正在等待的下个节点

final boolean isShared()   // 是否共享模式
final Node predecessor()    // 上个节点
```
- 释放共享锁
```java
    private void doReleaseShared() {
        for (;;) {   // 自旋
            Node h = head;    // 获取头节点
            if (h != null && h != tail) {
                // 获取头节点的状态
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {   // 节点状态为 -1， 表示需要被唤醒
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))  // 设置头节点的状态为 0
                        continue;            // loop to recheck cases    // 设置失败，则说明有并发冲突，继续下次循环
                    unparkSuccessor(h);      // 设置成功，则唤醒 h 的下个节点的线程
                }
                else if (ws == 0 &&
                         !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed  头节点没边，则跳出循环
                break;
        }
    }
```
```java
    private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; // Record old head for check below 原头结点
        setHead(node);     // 设置头节点
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
            (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }
```
- 取消节点
```java
    private void cancelAcquire(Node node) {
        if (node == null)
            return;
        node.thread = null;

        // Skip cancelled predecessors
        // 跳过取消的节点
        // 找到 node 前边最近一个 waitStatus 大于0的节点，让 node的前一个节点指向这个节点
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;
        // 临时变量存储这个节点的下一个节点
        Node predNext = pred.next;
        // 修改当前 node 节点的状态为取消状态
        node.waitStatus = Node.CANCELLED;

        // node 为 tail节点，则cas设置tail为pred
        if (node == tail && compareAndSetTail(node, pred)) {
            // cas 设置pred的下一个节点是null，因为 pred 被设置成 tail 了，它后边的都是 cancel 的节点，直接去掉
            compareAndSetNext(pred, predNext, null);
        } else {
            // node 不为 tail 节点，或者是 tail 节点已经被其他线程给设置了，需要继续执行
            int ws;
            if (pred != head &&
                ((ws = pred.waitStatus) == Node.SIGNAL ||
                 (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                pred.thread != null) {
                // pred 不是 head 节点，
                // 且 状态为 -1（唤醒信号） 或者  状态改为 -1 成功
                // 且 线程不为 null
                // 这些情况则需要设置 pred 的下个节点即可
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);    // 将 pre 的next 指向 node 的下一个节点(状态小于0的情况)
            } else {
                // 否则唤醒下个节点
                unparkSuccessor(node);
            }

            node.next = node; // help GC
        }
    }
```
- 获取锁失败后调用，当前一个节点的状态为 -1，则返回 true，否则设置前一个节点的状态为 -1，同时查看node的status，将 pred 到 node 之间status大于0（取消）的节点移除
```java
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            // 前边节点是 -1，可以被挂起
            return true;
        // 否则不能挂起
        if (ws > 0) {
            // 移除取消的节点
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            // 设置上个节点状态为 -1
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }
```
- 尝试获取锁，在获取锁失败后，添加到尾节点后进行阻塞，阻塞后被前一个节点唤醒后，继续走循环调用 tryAcquire 尝试获取锁，成功则设置当前节点为head，跳出循环
```java
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();   // 获取 node 的前一个节点
                if (p == head && tryAcquire(arg)) {  // p 是头结点
                    setHead(node);   // 将当前这个节点设置为头结点
                    p.next = null;   // help GC，p为前头结点，将 head 的next 设置为空，此时 前head 是游离状态，可以被回收
                    failed = false;   // 标记获取锁成功
                    return interrupted;   // 返回是否被打断标识
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())  // 阻塞
                    interrupted = true;   // 被打断，则记录打断标识
            }
        } finally {
            if (failed)
                // 其中一个环节出现异常，failed = true, 则取消节点
                cancelAcquire(node);
        }
    }
```
- 锁的通用获取
```java
    // 获取锁
    public final void acquire(int arg) { 
        // tryAcquire(arg) 尝试获取（子类自己实现）
        // acquireQueued(addWaiter(Node.EXCLUSIVE), arg))  独占模式假如aqs链尾
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            // acquireQueued 方法最后放回的是当前这个线程是否有被打断，如果为true，则需要在这里进行自我打断
            // 自我打断
            selfInterrupt();
    }
```
```java
    public final boolean hasQueuedPredecessors() {
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
        return h != t &&
            ((s = h.next) == null || s.thread != Thread.currentThread());
    }
```
- 添加节点
```java
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);   // 创建排他类型的 node 节点
        Node pred = tail;
        if (pred != null) {
            // 如果 tail 不为空的情况
            // 首先尝试直接替换到tail
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);  // 放入队列尾步
        return node;   // 返回这个node节点
    }
```
- 队列尾部添加节点
```java
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node()))   // 初始化，head=tail=new Node()
                    tail = head;
            } else {
                // 插入链尾
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }
```
```java
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {   // 上一个节点是head，再次尝试获取锁
                    setHead(node);   // 切换头节点，当前的这个 node 变成了头节点了
                    p.next = null; // help GC  此时 前 head 节点就是游离状态，可以被 GC 回收
                    failed = false;
                    return interrupted;   // 返回是否被打断
                }
                // 失败，设置 p 的 status = -1
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())   // 阻塞在这里
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
```
- 解锁
```java
    public final boolean release(int arg) {
        if (tryRelease(arg)) {   // 释放
            Node h = head;   // 获取头结点
            if (h != null && h.waitStatus != 0)  // 如果这里 h.waitStatus == 0, 说明 head 节点就是当前这个线程的节点，已经没有其他需要唤醒的节点了
                unparkSuccessor(h);   // 唤醒h的下个节点
            return true;
        }
        return false;
    }
```
- 释放锁
```java
        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;   // 减去
            if (Thread.currentThread() != getExclusiveOwnerThread())  
                // 如果aqs的线程不是当前线程，则异常
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {    // status减后为0，说明当前没锁了，可以释放；如果大于0，则说明有重入锁的应用，不能释放
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
```


# ReentrantLock
- ReentrantLock 实现了 Lock，内有变量 sync，sync 是一个 AQS
- 有属性 sync，其中的实现有 FairSync 和 NonfairSync
- 过程
  - 加锁
    1. 调用**aqs**获取其中的**state**, 如果0，则查看是否还有前置等待节点，没有则获取锁成功，设置当前节点为**head**
    2. 如果获取失败，则调用aqs的**addWaiter**，将当前线程封装成Node，并放入aqs的队列尾部
    3. 调用**acquireQueued**，设置前节点**status**为-1(SIGNAL)，并阻塞当前线程，直到等待被其他线程唤醒，唤醒后获取到锁，设置自己为head
  - 解锁
    1. 调用aqs的**release**方法
    2. 调用aqs的**tryRelease**方法，进行state的减操作，如果state减后为0，则设置aqs的线程为null，并唤醒head的下一个节点
    3. 如果state减后不为0，则说明重入锁的情况，还未完全释放，不进行唤醒
  - 公平锁 VS 非公平锁
    1. 非公平锁在获取锁时，先尝试CAS修改aqs的state为1，成功则获得锁，失败则继续走公平锁逻辑
    2. 非公平锁在继续走公平锁逻辑中，**tryAcquire**中不会查看是否有前置等待节点，而是直接CAS设置state

- 默认是非公平锁
```java
    public ReentrantLock() {
        sync = new NonfairSync();
    }
```
- 加锁
```java
    // 公平锁
    final void lock() {
        acquire(1);    // 获取锁，调用aqs的 acquire
    }
    
    // 非公平锁
    final void lock() {
        if (compareAndSetState(0, 1))
            setExclusiveOwnerThread(Thread.currentThread());   // 先尝试获取锁
        else
            acquire(1);    // 获取失败，再走公平锁逻辑
    }
```
```java
    // 公平锁
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();  // 刚开始时0
        if (c == 0) {
            // hasQueuedPredecessors() 是否有其他线程排前边
            // compareAndSetState 更新当前aqs的status
            if (!hasQueuedPredecessors() &&
                compareAndSetState(0, acquires)) {
                // 设置当前 aqs的 thread 为 当前线程，表示这个aqs已经被这个线程拿到了
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {   // 判断这个aqs是否是被当前这个线程拿到（重入锁的情况）
            int nextc = c + acquires;    // status 进行累加
            if (nextc < 0)    // 说明已经超过最大值，成为负数了
                throw new Error("Maximum lock count exceeded");
            setState(nextc);   // 设置 status
            return true;
        }
        // 获取不到锁的情况
        return false;
    }
```
- 解锁
```java
    public void unlock() {
        sync.release(1);
    }
```
- 解锁的具体实现
```java
    public final boolean release(int arg) {
        if (tryRelease(arg)) {   
            // 释放1，当释放后state == 0， 则返回true，进行唤醒
            // 如果释放1后，state > 0，则表示是重入锁的情况，还未完全释放，则返回false，不进行唤醒
            Node h = head;
            if (h != null && h.waitStatus != 0)   // 如果 h.waitStatus == 0，则 h 后已经没有其他节点需要唤醒了
                unparkSuccessor(h);   // 唤醒 h 的下一个节点
            return true;
        }
        return false;
    }
```

