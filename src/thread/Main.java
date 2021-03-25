package thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 只有一个main方法，Java虚拟机却开启了6个线程，表明Java天生就是多线程的。
 * [6]Monitor Ctrl-Break
 * [5]Attach Listener
 * [4]Signal Dispatcher
 * [3]Finalizer
 * [2]Reference Handler
 * [1]main
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*Java虚拟机线程系统的管理接口*/
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        /*不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息*/
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        /*遍历线程信息，仅打印线程ID和线程名称*/
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

        /**
         *  =========================================================================
         *  实现线程的方式有两种：
         */
        /*======方式一：继承Thread类 ------ Thread是Java对线程的抽象*/
        new MyThread().start();
        /*======方式二：实现Runable接口 ------ Runable是Java对任务（业务逻辑）的抽象*/
        new Thread(new RunnableImpl()).start();
        new RunnableImpl().run();
        //FutureTask--->RunnableFuture--->Runnable --- 使用Callable这种方式最终还是通过Runnable的方式
        FutureTask<String> stringFutureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Callable implement Thread";
            }
        });
        new Thread(stringFutureTask).start();
        System.out.println("stringFutureTask.get() ====== " + stringFutureTask.get());
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                /*当线程不需要获取锁的时候，调用wait()会报IllegalMonitorStateException异常*/
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
            System.out.println("继承Thread --- 子线程的名字" + Thread.currentThread().getName());
            System.out.println("I inherited Thread~");
        }
    }

    static class RunnableImpl implements Runnable {
        @Override
        public void run() {
            System.out.println("实现Runnable --- 子线程的名字" + Thread.currentThread().getName());
            System.out.println("I implement Runable~");
        }
    }

/**
 * 公平锁的lock方法
 */
//final void lock() {
//    acquire(1);
//}

/**
 * AQS中的acquire(int)方法
 *
 * @param arg 公平锁传的值为1，后面我就直接以 1对待，不再赘述
 */
//public final void acquire(int arg) {
//    if (!tryAcquire(arg) &&
//            acquireQueued(addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), arg))
//        /*当前线程自行中断*/
//        selfInterrupt();
//}

/**
 * 回调 ReentrantLock.FairSync的 tryAcquire(int)方法
 *
 * @param acquires
 * @return
 */
//protected final boolean tryAcquire(int acquires) {
//    /*获取当前线程*/
//    final Thread current = Thread.currentThread();
//    /*获取同步状态，也就是锁被获取的次数*/
//    int c = getState();
//    /*如果锁被获取到的次数为0，即此时没有线程持锁*/
//    if (c == 0) {
//        /*查询是否有任何线程在等待获取比当前线程更长的时间，也就是当前线程是否位于队列的第一位。*/
//        if (!hasQueuedPredecessors() &&
//                /*如果使用CAS把锁的状态成功改为1，则返回true*/
//                compareAndSetState(0, acquires)) {
//            /*设置当前线程为独占模式同步的当前所有者，即当前线程拿到锁*/
//            setExclusiveOwnerThread(current);
//            return true;
//        }
//    }
//    /*如果当前线程已经是持锁线程*/
//    else if (current == getExclusiveOwnerThread()) {
//        /*持锁次数加1*/
//        int nextc = c + acquires;
//        if (nextc < 0)
//            throw new Error("Maximum lock count exceeded");
//        /*更新同步状态*/
//        setState(nextc);
//        return true;
//    }
//    return false;
//}

/**
 * 执行不公平的tryLock。 tryAcquire是在子类中实现的，但是都需要对trylock方法进行不公平的尝试。
 * https://www.cnblogs.com/aspirant/p/8657681.html
 */
//final boolean nonfairTryAcquire(int acquires) {
//    final Thread current = Thread.currentThread();
//    /*获取同步状态，也就是锁被获取的次数*/
//    int c = getState();
//    if (c == 0) {
//        /*如果锁被获取的次数是0，则使用CAS把锁的状态改为 acquires*/
//        if (compareAndSetState(0, acquires)) {
//            /*拿锁成功，设置当前线程为独占模式同步的当前所有者，即表明当前线程持锁。*/
//            setExclusiveOwnerThread(current);
//            return true;
//        }
//    } else if (current == getExclusiveOwnerThread()) {
//        int nextc = c + acquires;
//        if (nextc < 0) // overflow
//            throw new Error("Maximum lock count exceeded");
//        setState(nextc);
//        return true;
//    }
//    return false;
//}

//protected final boolean tryRelease(int releases) {
//    int c = getState() - releases;
//    if (Thread.currentThread() != getExclusiveOwnerThread())
//        throw new IllegalMonitorStateException();
//    boolean free = false;
//    if (c == 0) {
//        /*当锁被获取的次数为0时，锁才真正被释放*/
//        free = true;
//        setExclusiveOwnerThread(null);
//    }
//    setState(c);
//    return free;
//}

/**
 * 使用给定的公平性策略创建{@code ReentrantLock}的实例。
 *
 * @param fair 如果是true，创建公平锁；否则创建非公平锁。
 */
//public ReentrantLock(boolean fair) {
//    sync = fair ? new ReentrantLock.FairSync() : new ReentrantLock.NonfairSync();
//}
}
