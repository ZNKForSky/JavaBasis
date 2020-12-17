package thread.join;

/**
 * 演示 Thread的 join()方法
 *
 * @author Luffy
 */
public class TestJoin {

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Thread t1 = new Thread(new Worker("thread-1"));
        Thread t2 = new Thread(new Worker("thread-2"));
        Thread t3 = new Thread(new Worker("thread-3"));
        /**
         * join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         * 程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程
         * 继续执行直到线程t1执行完毕所以结果是t1线程执行完后，才到主线程执行，相当于在
         * main线程中同步t1线程，t1执行完了，main线程才有执行的机会
         */
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();

        System.out.println("main end");
    }

    static class Worker implements Runnable {

        private String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500L);
                    Thread.State state = Thread.currentThread().getState();
                    System.out.println("当前线程" + Thread.currentThread().getName() + "的状态是 ： " + state);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name);
            }
        }

    }
}