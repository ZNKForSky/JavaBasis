package thread.wn;

/**
 * @author Luffy
 * @Classname WaitAndNotifyDemo
 * @Description 等待通知机制案例
 * @Date 2020/12/16 14:24
 */
public class WaitAndNotifyDemo {
    private static Express expressInfo = new Express("深圳", 1500);

    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        /**
         * 开启3个线程检查快递包裹距离目的的公里数
         */
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new CheckDistance(previous));
            thread.start();
            previous = thread;
        }
        /**
         * 开启3个线程检查快递包裹是否抵达目的地
         */
        for (int i = 3; i < 6; i++) {
            Thread thread = new Thread(new CheckSite(previous));
            thread.start();
            previous = thread;
        }
        /*休眠5秒，模拟快递邮寄需要花费时间*/
        Thread.sleep(2000);

        expressInfo.changeSite();
        expressInfo.changeDistance();
        System.out.println("Main thread is terminated");
    }

    /**
     * 检查快递包裹距离目的地公里数的线程
     */
    static class CheckDistance implements Runnable {
        /*插队的线程*/
        private Thread thread;

        public CheckDistance(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                expressInfo.waitKM();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminted.");
        }
    }

    /**
     * 检查快递包裹位置的变化
     */
    static class CheckSite implements Runnable {
        /*插队的线程*/
        private Thread thread;

        public CheckSite(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                expressInfo.waitBringUp();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminted.");
        }
    }
}
