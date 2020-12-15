package thread.sync;

/**
 * @author Luffy
 * @Classname ObjectLock
 * @Description 对象锁示例，也叫实例锁 ------ 对象锁是用于对象实例方法
 * @Date 2020/12/15 13:50
 */
public class ObjectLock {

    private Object lock = new Object();

    /**
     * 锁住非静态变量
     *
     * @throws InterruptedException 中断异常
     */
    public void lockObjectField() throws InterruptedException {
        synchronized (lock) {
            System.out.println("lockObjectField --- 当前线程名称： " + Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        }
    }

    /**
     * 锁住 this对象，即当前对象实例
     *
     * @throws InterruptedException 中断异常
     */
    public void lockThis() throws InterruptedException {
        synchronized (this) {
            System.out.println("lockThis --- 当前线程名称： " + Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        }
    }

    /**
     * 锁住非静态方法
     *
     * @throws InterruptedException 中断异常
     */
    public synchronized void methodLock() throws InterruptedException {
        System.out.println("methodLock --- 当前线程名称： " + Thread.currentThread().getName());
        Thread.sleep(10 * 1000);
    }

    public static void main(String[] args) {
        ObjectLockWorker objectLockWorker = new ObjectLockWorker();
        for (int i = 0; i < 5; i++) {
//            Thread worker = new Thread(objectLockWorker);
            Thread worker = new Thread(new ObjectLockWorker());
            worker.setName("Luffy-" + i);
            worker.start();
        }
    }

    public static class ObjectLockWorker implements Runnable {
        @Override
        public void run() {
            try {
                ObjectLock objectLock = new ObjectLock();
                /*类的对象实例可以有很多个，所以不同对象实例的对象锁是互不干扰的*/
                System.out.println("lock对象地址 === " + objectLock.lock);
                /*===方式 1===*/
                objectLock.lockObjectField();
                /*===方式 2===*/
//                objectLock.lockThis();
                /*===方式 3===*/
//                objectLock.methodLock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
