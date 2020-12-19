package thread.cas;

import thread.sync.SleepTools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luffy
 * @Classname UseCasInteger
 * @Description 演示使用CAS做递增和递减操作
 * @Date 2020/12/19 14:46
 */
public class UseCasInteger {
    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Increase()).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Decrease()).start();
        }
        SleepTools.second(1);
        System.out.println("result = " + atomicInteger.get());
    }

    /**
     * 递增
     */
    static class Increase implements Runnable {
        @Override
        public void run() {
            /*类似于整形中的 ++i */
            atomicInteger.addAndGet(1);
        }
    }

    /**
     * 递减
     */
    static class Decrease implements Runnable {

        @Override
        public void run() {
            /*类似于 类似于整形中的 --i */
            atomicInteger.decrementAndGet();
        }
    }
}
