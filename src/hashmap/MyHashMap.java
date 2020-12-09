//package hashmap;
//
//import java.io.Serializable;
//import java.util.*;
//
//public class MyHashMap<K, V>
//        extends AbstractMap<K, V>
//        implements Map<K, V>, Cloneable, Serializable {
//    /**
//     * 最大容量
//     */
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /**
//     * 默认加载因子
//     */
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//    /**
//     * hash table的因子
//     */
//    final float loadFactor;
//    /**
//     * 扩容的临界值
//     */
//    int threshold;
//
//    /**
//     * 共享的空表实例
//     */
//    static final Entry<?, ?>[] EMPTY_TABLE = {};
//
//    /**
//     * 根据需要调整表的大小，长度必须是2的次幂（为什么必须是2的次幂，后面会讲解）
//     */
//    transient Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;
//    /**
//     * 此Map中包含的键值对个数
//     */
//    transient int size;
//    /**
//     * 哈希种子：用来生成hashCode
//     */
//    transient int hashSeed = 0;
//
//    /**
//     * Hash构造方法
//     *
//     * @param initialCapacity 初始容量
//     * @param loadFactor      加载因子
//     */
//    public MyHashMap(int initialCapacity, float loadFactor) {
//        /*如果容量小于0，则抛IllegalArgumentException异常*/
//        if (initialCapacity < 0) {
//            throw new IllegalArgumentException("Illegal initial capacity: " +
//                    initialCapacity);
//        }
//        /*如果容量大于指定的最大容量，则取最大容量*/
//        if (initialCapacity > MAXIMUM_CAPACITY) {
//            initialCapacity = MAXIMUM_CAPACITY;
//        }
//        /*如果加载因子小于0或者加载因子不是数值类型，抛IllegalArgumentException异常*/
//        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
//            throw new IllegalArgumentException("Illegal load factor: " +
//                    loadFactor);
//        }
//
//        this.loadFactor = loadFactor;
//        threshold = initialCapacity;
//        init();
//    }
//
//    /**
//     * 作为子类的钩子函数，在此不做具体实现
//     */
//    private void init() {
//    }
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    /**
//     * 往HashMap中添加元素
//     *
//     * @param key   键
//     * @param value 值
//     * @return 如果key相同则返回旧值，否则返回null
//     */
//    @Override
//    public V put(K key, V value) {
//        /*当数组table是空数组是，对数组table进行填充*/
//        if (table == EMPTY_TABLE) {
//            inflateTable(threshold);
//        }
//        if (key == null) {
//            return putForNullKey(value);
//        }
//        int hash = hash(key);
//        int i = indexFor(hash, table.length);
//        for (Entry<K, V> e = table[i]; e != null; e = e.next) {
//            Object k;
//            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//
//        modCount++;
//        addEntry(hash, key, value, i);
//        return null;
//    }
//
//    /**
//     * 填充表
//     *
//     * @param toSize
//     */
//    private void inflateTable(int toSize) {
//        // 将指定的容量转换成一个大于它本身的最小2的次幂数，eg.6--->8
//        int capacity = roundUpToPowerOf2(toSize);
//
//        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
//        table = new Entry[capacity];
//        initHashSeedAsNeeded(capacity);
//    }
//
//    /**
//     * 当key为空时，把该元素插入到数组头部的链表中
//     */
//    private V putForNullKey(V value) {
//        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
//            if (e.key == null) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//        modCount++;
//        addEntry(0, null, value, 0);
//        return null;
//    }
//
//    /**
//     * 获取任意对象的hash值
//     *
//     * @param k 任意要进行hash算法操作的对象
//     * @return 充分离散后的hash值
//     */
//    final int hash(Object k) {
//        int h = hashSeed;
//        if (0 != h && k instanceof String) {
//            return sun.misc.Hashing.stringHash32((String) k);
//        }
//        h ^= k.hashCode();
//        /*这种方式是为了让int数值的高位充分离散化，详情见blog：https://www.cnblogs.com/jiang--nan/p/9014779.html*/
//        h ^= (h >>> 20) ^ (h >>> 12);
//        return h ^ (h >>> 7) ^ (h >>> 4);
//    }
//
//    void recordAccess(MyHashMap<K, V> m) {
//    }
//
//    /**
//     * 将number转换成大于它的最小2的次幂数
//     *
//     * @param number
//     * @return
//     */
//    private static int roundUpToPowerOf2(int number) {
//        // assert number >= 0 : "number must be non-negative";
//        return number >= MAXIMUM_CAPACITY
//                ? MAXIMUM_CAPACITY
//                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
//    }
//
//    static class Entry<K, V> implements Map.Entry<K, V> {
//        final K key;
//        V value;
//        Entry<K, V> next;
//        int hash;
//
//        /**
//         * Creates new entry.
//         */
//        Entry(int h, K k, V v, Entry<K, V> n) {
//            value = v;
//            next = n;
//            key = k;
//            hash = h;
//        }
//
//        @Override
//        public final K getKey() {
//            return key;
//        }
//
//        @Override
//        public final V getValue() {
//            return value;
//        }
//
//        @Override
//        public final V setValue(V newValue) {
//            V oldValue = value;
//            value = newValue;
//            return oldValue;
//        }
//
//        @Override
//        public final boolean equals(Object o) {
//            if (!(o instanceof Map.Entry)) {
//                return false;
//            }
//            Map.Entry e = (Map.Entry) o;
//            Object k1 = getKey();
//            Object k2 = e.getKey();
//            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
//                Object v1 = getValue();
//                Object v2 = e.getValue();
//                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        @Override
//        public final int hashCode() {
//            return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
//        }
//
//        @Override
//        public final String toString() {
//            return getKey() + "=" + getValue();
//        }
//
//        /**
//         * This method is invoked whenever the value in an entry is
//         * overwritten by an invocation of put(k,v) for a key k that's already
//         * in the HashMap.
//         */
//        void recordAccess(MyHashMap<K, V> m) {
//        }
//
//        /**
//         * This method is invoked whenever the entry is
//         * removed from the table.
//         */
//        void recordRemoval(MyHashMap<K, V> m) {
//        }
//    }
//
//    /**
//     * 添加Entry
//     *
//     * @param hash        hash值
//     * @param key         键
//     * @param value       值
//     * @param bucketIndex 桶的下标
//     */
//    void addEntry(int hash, K key, V value, int bucketIndex) {
//        if ((size >= threshold) && (null != table[bucketIndex])) {
//            resize(2 * table.length);
//            hash = (null != key) ? hash(key) : 0;
//            bucketIndex = indexFor(hash, table.length);
//        }
//
//        createEntry(hash, key, value, bucketIndex);
//    }
//
//    /**
//     * 扩容
//     *
//     * @param newCapacity 新的容量
//     */
//    void resize(int newCapacity) {
//        Entry[] oldTable = table;
//        int oldCapacity = oldTable.length;
//        if (oldCapacity == MAXIMUM_CAPACITY) {
//            threshold = Integer.MAX_VALUE;
//            return;
//        }
//
//        Entry[] newTable = new Entry[newCapacity];
//        transfer(newTable, initHashSeedAsNeeded(newCapacity));
//        table = newTable;
//        threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
//    }
//
//    /**
//     * 获取存储元素的数组下标
//     *
//     * @param h      hash值
//     * @param length 数组的长度
//     * @return 存储元素的数组下标
//     * <p>
//     * ps:如果传入的length不是2的幂，比如传一个9，h就是hash(key)创建出来的一个随机数，不管h怎么变，
//     * 最终结果只会存在这四种情况：00001000、00001010、00000010、00000000,而我们数组的长度是9,也
//     * 就是说5个索引没有用到，造成了空间的浪费，加大了hash冲突几率；而其他索引处的链表势必会变得很
//     * 长，我们获取元素的效率就会降低。
//     */
//    static int indexFor(int h, int length) {
//        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
//        /*一般情况下，我们的思路是对 h进行取模运算：h%length,但考虑到位运算是机器码直接被CPU处理的，效率会很高，所以采用以下方式*/
//        return h & (length - 1);
//    }
//
//    /**
//     * 创建Entry并将它指向链表头部，这就是所谓的“头插法”
//     *
//     * @param hash        hash值
//     * @param key         键
//     * @param value       值
//     * @param bucketIndex 桶的下标
//     */
//    void createEntry(int hash, K key, V value, int bucketIndex) {
//        /*1.先找到指定索引处原来存放的链表头部*/
//        Entry<K, V> e = table[bucketIndex];
//        /*2.将新的元素指向链表头部，其中 e指向了Entry中的 next属性*/
//        table[bucketIndex] = new Entry<>(hash, key, value, e);
//        /*3.Map存储元素的个数加一*/
//        size++;
//    }
//}
