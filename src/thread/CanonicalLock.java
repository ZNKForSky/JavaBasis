package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Luffy
 * @Classname CanonicalLock
 * @Description 显示锁的范式
 * @Date 2020/12/18 7:31
 */
public class CanonicalLock {
    private static transient int count;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(10);
            new MyThread().start();
        }
        Thread.sleep(200);
        System.out.println("result = " + count);
    }

    /**
     * 锁【Lock.lock()】必须紧跟try代码块，且unlock要放在finally第一行。
     */
    private static void accumulate() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * StackOverflowError 说明Java内置锁synchronized是可重入的
     */
    public static synchronized void incr2() {
        count++;
        incr2();
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
//            accumulate();
            incr2();
        }
    }
}
