package thread.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用尝试拿锁的机制打破死锁
 */
public class TryLock {

    /**
     * 前端一号技师
     */
    private static final Lock NO_1 = new ReentrantLock();
    /**
     * 后端二号技师
     */
    private static final Lock NO_2 = new ReentrantLock();

    /**
     * 张总跟前端一号技师关系好，所以他先跟前端一号技师打招呼，先去抢NO_1，如果抢到了NO_1,再尝试去抢NO_2，
     * 如果很幸运NO_2也被张总说服了，就可以着手项目开发，一周之后项目开发完成，释放掉NO_1和NO_2。如果没
     * 有说服NO_2，则把NO_1也释放掉。
     */
    private static void zhangDo() throws InterruptedException {
        String name = Thread.currentThread().getName();
        Random random = new Random();
        while (true) {
            if (NO_1.tryLock()) {
                Thread.sleep(random.nextInt(3));
                System.out.println(name + " get NO_1");
                try {
                    if (NO_2.tryLock()) {
                        try {
                            System.out.println(name + " get NO_2，Project is running.");
                            System.out.println("A week later, the project was completed.");
                            break;
                        } finally {
                            NO_2.unlock();
                            System.out.println("张总释放NO_2");
                        }
                    }
                } finally {
                    NO_1.unlock();
                    System.out.println("张总释放NO_1");
                }
            }
            Thread.sleep(random.nextInt(3));
        }
    }

    /**
     * 赵总跟后端二号技师关系好，所以他想着先把前端一号技师搞定了，基本就没啥问题了。所以也先去抢NO_1，
     * 如果抢到了NO_1,再尝试去抢NO_2，如果NO_2如赵总所愿，也愿意先跟着赵总做项目，那么一周之后项目开
     * 发完成，赵总释放掉NO_1和NO_2。如果NO_2被张总策反了，则把NO_1也释放掉。
     */
    private static void zhaoDo() throws InterruptedException {
        String name = Thread.currentThread().getName();
        Random random = new Random();
        while (true) {
            if (NO_1.tryLock()) {
                Thread.sleep(random.nextInt(3));
                System.out.println(name + " get NO_1");
                try {
                    if (NO_2.tryLock()) {
                        try {
                            System.out.println(name + " get NO_2，Project is running.");
                            System.out.println("A week later, the project was completed.");
                            break;
                        } finally {
                            NO_2.unlock();
                            System.out.println("赵总释放NO_2");
                        }
                    }
                } finally {
                    NO_1.unlock();
                    System.out.println("赵总释放NO_1");
                }
            }
            Thread.sleep(random.nextInt(3));
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
