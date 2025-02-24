# AQS 解析
- aqs：AbstractQueuedSynchronizer  抽象同步器
- 扩展得到的相关锁类：ReentrantLock/ReentrantReadWriteLock/Semaphore/CountDownLatch/CyclicBarrier/Phaser/Exchanger
- AQS 类方法
```java
    private transient volatile Node head;  // 队列头节点
    private transient volatile Node tail;    // 尾节点
    private volatile int state;   // 队列状态
```
- 插入节点
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
- 根据指定模式添加节点
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
- 唤醒下一个节点
```java
    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         */
        int ws = node.waitStatus;
        if (ws < 0)
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
- 唤醒下一个节点
```java
    private void unparkSuccessor(Node node) {
        // 如果 node 节点的 waitStatus等待状态 小于 0 ，则 cas 设置为 0
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);
        
        Node s = node.next;   // 获取下个节点
        if (s == null || s.waitStatus > 0) {
            // 下个节点为空或者状态大于0的情况，waitStatus 大于 0 则表示取消状态
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                // 从tail往前遍历，一直往前找，获取第一个非取消节点（即waitStatus <= 0），也就是获取到 node 下的第一个符合情况的节点
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)   // s 不等于 null，则说明找到了下一个可以被唤醒的节点
            LockSupport.unpark(s.thread);   // 唤醒这个节点的线程
    }
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

# ReentrantLock
- ReentrantLock 继承了 AbstractQueuedSynchronizer
