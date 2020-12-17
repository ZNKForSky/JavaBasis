package thread.join;

/**
 * @author Luffy
 * @Classname UseJoin
 * @Description join就是把指定的线程插入到当前线程中，其实就是任务的插队。
 * 下面的案例是创建10个线程，调用 join() 方法使得他们可以按顺序执行。
 * @Date 2020/12/15 17:08
 */
@SuppressWarnings("all")
public class UseJoin {
    public static void main(String[] args) {
        /*第一次 previous就是主线程，后面就变成了新创建的子线程了*/
        Thread previous = Thread.currentThread();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JoinQueue(previous), String.valueOf(i));
            if (i == 1) {
                /*将线程1设置为守护线程 https://blog.csdn.net/shimiso/article/details/8964414*/
                thread.setDaemon(true);
            }
            thread.start();
            previous = thread;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread has been terminate.");
    }

    static class JoinQueue implements Runnable {
        /*插队的线程*/
        private Thread thread;

        public JoinQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " will be queued behind " + thread.getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminted.");
        }
    }
}
