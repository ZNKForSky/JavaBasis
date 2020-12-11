package thread.deadlock;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport源码注释大概意思：
 * <p>
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 * <p>
 * LockSupport和每个使用它的线程都关联一个许可证(permit)。permit相当于1，0的开关，默认是0，调用一次 unpark就加1变成1，
 * 调用一次 park会消费 permit, 也就是将1变成0，同时 park立即返回。再次调用 park会变成 block（因为permit为0了，会阻塞
 * 在这里，直到 permit变为1）, 这时调用 unpark会把 permit置为1。每个线程都有一个相关的 permit, permit最多只有一个，
 * 重复调用 unpark也不会积累。
 * <p>
 * park()和unpark()提供了有效的阻塞和解除线程的方法，不会有 “Thread.suspend”和 “Thread.resume”这两个已弃用的方法所可
 * 能引发的死锁问题，由于许可的存在，调用 park的线程和另一个试图将其 unpark 的线程之间的竞争将保持活性。此外，如果调用线
 * 程被中断，则 park方法会返回。同时 park也拥有可以设置超时时间的版本。
 * <p>
 * 需要特别注意的一点：park方法还可以在其他任何时间“毫无理由”地返回，因此通常必须在重新检查返回条件的循环里调用此方法。
 * 从这个意义上说，park 是“忙碌等待”的一种优化，它不会浪费这么多的时间进行自旋，但是必须将它与 unpark 配对使用才更高效。
 * <p>
 * 三种形式的 park还各自支持一个 blocker 对象参数。此对象在线程受阻塞时被记录，以允许监视工具和诊断工具确定线程受阻塞的原因。
 * （这样的工具可以使用方法 getBlocker(java.lang.Thread) 访问 blocker。）建议最好使用这些形式，而不是不带此参数的原始形式。
 * 在锁实现中提供的作为 blocker 的普通参数是 this。
 * <p>
 * LockSupport没有对外提供公开构造方法，它内部的方法都是静态的，主要有两类方法parkXXX()和unparkXXX()
 * <p>
 * public static void park(); // 调用native方法无期限阻塞当前线程
 * public static void parkNanos(long nanos); // 阻塞当前线程，不过有超时时间的限制
 * public static void parkUntil(long deadline); // 阻塞当前线程，直到某个时间
 * <p>
 * JDK1.6引入这三个方法对应的拥有Blocker版本,blocker为阻塞当前线程的对象。
 * public static void park(Object blocker);
 * public static void parkNanos(Object blocker, long nanos);
 * public static void parkUntil(Object blocker, long deadline);
 * <p>
 * public static void unpark(Thread thread); // 唤醒指定处于阻塞状态的线程
 *
 * @author Luffy
 */
public class TestLockSupport {

    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                String currentThreadName = currentThread().getName();
                System.out.println(currentThreadName + "开启时间毫秒数：" + System.currentTimeMillis());
                if (null == LockSupport.getBlocker(currentThread())) {
                    System.out.println("当前线程" + currentThreadName + "没有被阻塞");
                }
                LockSupport.park();
                if (null != LockSupport.getBlocker(currentThread())) {
                    System.out.println("当前线程" + currentThreadName + "被阻塞了");
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程" + currentThreadName + "状态：" + currentThread().getState());
                }
                System.out.println("当前线程" + currentThreadName + "继续执行");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(3000L);
        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
