package thread.threadlocal;

/**
 * @author Luffy
 * @Classname UseThreadLocal
 * @Description 演示使用 ThreadLocal实现线程间数据隔离。
 * @Date 2020/12/17 13:52
 */
public class UseThreadLocal {
    /**
     * 创建一个存放 Integer类型的 ThreadLocal实例并调用初始化方法设置线程本地变量的初始值为0，
     * ThreadLocal实现了线程间数据隔离，所以是线程安全的。
     */
    static ThreadLocal threadLocalInter = new ThreadLocal<Integer>() {
        /**
         * 返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。
         * 这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。
         * ThreadLocal中的缺省实现直接返回一个null。
         * @return 初始值
         */
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        /*开启三个线程*/
        for (int i = 0; i < 3; i++) {
            new Thread(new WorkThread(i)).start();
            /*加休眠错开线程开启时间*/
            Thread.sleep(10);
        }
        /**
         * 将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。需要指出的是，
         * 当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是
         * 必须的操作，但它可以加快内存回收的速度。
         */
        threadLocalInter.remove();
    }

    /**
     * 测试线程：线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会互相影响
     */
    static class WorkThread implements Runnable {
        int id;

        public WorkThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            /*该方法返回当前线程所对应的线程局部变量。*/
            int oldValue = (int) threadLocalInter.get();
            /*设置当前线程的线程局部变量的值。*/
            threadLocalInter.set(id + oldValue);
            System.out.println("线程" + Thread.currentThread().getName() + "中本地变量新值是： " + threadLocalInter.get());
        }
    }
}
