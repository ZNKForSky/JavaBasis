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
}
