package thread;

/**
 * 安全中断线程
 */
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        UserThread endThread = new UserThread("EndThread");
        endThread.start();
        Thread.sleep(20);
        /*设置线程中断，其实只是设置线程中断标志位  Just to set the interrupt flag*/
        endThread.interrupt();
    }

    private static class UserThread extends Thread {
        public UserThread(String name) {
            super(name);
        }


        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            /*isInterrupted() 判断线程是否被中断，如果中断返回 true*/
            System.out.println(threadName + " interrupt flag = " + isInterrupted());
            //当没有中断时
            while (!isInterrupted())
//            while (true)
//            while (!Thread.interrupted())
            /**
             * isInterrupted 和 Thread.interrupted()最终都会调用本地方法 isInterrupted，只不过前者传入参数false，不重置
             * 中断标志位，而后者传入true重置了中断标志位
             */ {
                System.out.println(threadName + " is running~");
                System.out.println(threadName + " inner interrupt flag is " + isInterrupted());
            }
            System.out.println(threadName + " outer interrupt flag is " + isInterrupted());
        }
    }
}



