package thread.interrupt;

/**
 * 案例三：当线程处于阻塞状态时，调用 interrupt，线程不会理会，依然处于阻塞状态。
 *
 * @author Luffy
 */
public class InterruptThread3rdCase {
    /*创建一个锁对象*/
    private static Object lock = new Object();

    private static class MyThread extends Thread {
        @Override
        public void run() {
            /*等待lock锁*/
            synchronized (lock) {
                /*等待标志位被置为true*/
                while (!Thread.currentThread().isInterrupted()) {
                }
            }
            System.out.println("Child thread exit~");
        }
    }

    public static void test() throws InterruptedException {
        /*获取锁*/
        synchronized (lock) {
            MyThread myThread = new MyThread();
            myThread.start();
            Thread.sleep(1000);
            /*myThread在等待lock锁，interrupt 无法中断*/
            myThread.interrupt();
            /*myThread线程加入当前线程，等待执行完毕。如果注掉这行代码，主线程会先执行完，释放掉锁，子线程拿到锁后，执行完任务也会退出*/
            myThread.join();
            System.out.println("Main Thread is finished~");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }
}
