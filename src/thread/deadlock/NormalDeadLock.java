package thread.deadlock;

/**
 * 演示死锁的产生
 *
 * @author Luffy
 */
public class NormalDeadLock {

    /**
     * 前端一号技师
     */
    private static final Object NO_1 = new Object();
    /**
     * 后端二号技师
     */
    private static final Object NO_2 = new Object();

    /**
     * 张总跟前端一号技师关系好，所以他先跟前端一号技师打招呼，先去抢No1，再去抢No2
     */
    private static void zhangDo() {
        String threadName = Thread.currentThread().getName();
        synchronized (NO_1) {
            System.out.println(threadName + " get No1");
            synchronized (NO_2) {
                System.out.println(threadName + " get No2");
            }
        }
    }

    /**
     * 赵总跟后端二号技师关系好，所以他先跟后端二号技师打招呼，先去抢No2，再去抢No1
     */
    private static void zhaoDo() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (NO_2) {
            System.out.println(threadName + " get No2");
            /*主线程先会被执行，这里加睡眠时间，是为了防止线程一运行就获得两个对象的锁*/
            Thread.sleep(100);
            synchronized (NO_1) {
                System.out.println(threadName + " get No1");
            }
        }
    }

    /**
     * 子线程，代表张总
     */
    private static class ZhangThread extends Thread {
        private String name;

        ZhangThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                zhangDo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*主线程，代表赵总*/
        Thread.currentThread().setName("赵总");
        ZhangThread zhangThread = new ZhangThread("张总");
        zhangThread.start();
        zhaoDo();
        System.out.println("Main Thread End!");
    }

}
