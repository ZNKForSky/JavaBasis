package thread.threadlocal;

/**
 * 演示ThreadLocal的使用
 *
 * @author luffy
 */
public class UseThreadLocal {

    /**
     * 创建ThreadLocal实例对象，并初始化
     */
    static ThreadLocal threadLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return 1;
        }
    };

    /**
     * 运行3个线程
     */
    private void startThreads() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread thread = new Thread(new TestThread(i));
                /*加延时把线程开启的时间错开*/
                Thread.sleep(1000);
                thread.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试线程：线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会互相影响
     */
    public static class TestThread implements Runnable {
        int id;

        TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            Integer i = (Integer) threadLocal.get();
            id += i;
            threadLocal.set(id);
            System.out.println("threadLocal取出新值 = " + threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThreads();
    }
}
