# ArrayList 源码解析
- 默认创建10的长度，在第一个add时候才创建
```java
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
```
- 扩容，默认扩容1.5倍
```java
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);   // 扩容
    }
```
- 扩容方法
```java
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;    // 数据长度
        int newCapacity = oldCapacity + (oldCapacity >> 1);   // 新的数组长度，默认为旧的1.5倍
        if (newCapacity - minCapacity < 0) 
            // 需要的长度比1.5倍旧数组长度多
            newCapacity = minCapacity;    // 则新的数组长度为需要的数组长度
        if (newCapacity - MAX_ARRAY_SIZE > 0)  
            // 如果需要的数据长度大于  Integer最大值减8
            // 则 newCapacity 为数组最大值
            newCapacity = hugeCapacity(minCapacity);  
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);  // 扩容
    }
```
