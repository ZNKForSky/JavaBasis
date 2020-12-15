package thread.sync;

/**
 * @author Luffy
 * @Classname ClassLock
 * @Description 类锁示例 ------ 类锁是用于类的静态方法、静态变量或者一个类的 Class对象上的
 * @Date 2020/12/15 13:57
 */
public class ClassLock {

    private static Object lock = new Object();

    /**
     * 锁住静态变量
     *
     * @throws InterruptedException
     */
    public void lockStaticVariable() throws InterruptedException {
        synchronized (lock) {
            System.out.println("lockStaticVariable ------ " + Thread.currentThread().getName());
            Thread.sleep(1000);
        }
    }

    /**
     * 锁住静态方法
     *
     * @throws InterruptedException
     */
    public static synchronized void methodLock() throws InterruptedException {
        System.out.println("methodLock ------ " + Thread.currentThread().getName());
        Thread.sleep(1000);
    }

    /**
     * 锁住类的Class对象，每个类只有一个Class对象
     *
     * @throws InterruptedException
     */
    public void lockClass() throws InterruptedException {
        synchronized (ClassLock.class) {
            System.out.println("lockClass ------ " + Thread.currentThread().getName());
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread worker = new Thread(new ClassLockWorker());
            worker.setName("Luffy-" + i);
            worker.start();
        }
    }

    public static class ClassLockWorker implements Runnable {
        @Override
        public void run() {
            try {
                ClassLock classLock = new ClassLock();
                /*===方式 1===*/
                classLock.lockStaticVariable();
                /*===方式 2===*/
                //ClassLock.methodLock();
                /*===方式 3===*/
                //classLock.lockClass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}