package thread.vola;

import tools.SleepTools;

/**
 * 演示Volatile的提供的可见性
 * volatile是最轻量的同步机制，并不能替代synchronized
 */
public class VolatileCase {
    /**
     * 加了volatile关键字后，可以保证该成员在线程之间的可见性。
     */
    private static volatile boolean ready;
    private static int number;

    private static class PrintThread extends Thread {
        @Override
        public void run() {
            System.out.println("PrintThread is running.......");
            while (!ready) ;
            System.out.println("number = " + number);
        }
    }

    public static void main(String[] args) {
        new PrintThread().start();
        SleepTools.second(1);
        number = 51;
        ready = true;
        SleepTools.second(5);
        System.out.println("main is ended!");
    }
}
