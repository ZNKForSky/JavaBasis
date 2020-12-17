package thread.interrupt;

/**
 * 案例一：安全中断线程
 *
 * @author Luffy
 */
public class InterruptThread1stCase {
    public static void main(String[] args) throws InterruptedException {
        UserThread endThread = new UserThread("EndThread");
        endThread.start();
        /*休眠20毫秒之后，中断子线程*/
        Thread.sleep(20);
        /*设置线程中断，其实只是设置线程中断标志位  Just to set the interrupt flag*/
        endThread.interrupt();
        /*stop方法过于蛮横，不用它*/
        //endThread.stop();

        Thread.sleep(6);
        Thread.State state = endThread.getState();
        System.out.println("子线程状态： " + state);
    }

    private static class UserThread extends Thread {
        public UserThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = getName();
            /*isInterrupted() 判断线程是否被中断，如果中断返回 true*/
            System.out.println(threadName + " interrupt flag = " + isInterrupted());
            /**
             * 如果线程在运行中，interrupt()只是会设置线程的中断标志位，没有任何其它作用。
             * 线程应该在运行过程中合适的位置检查中断标志位，比如说，如果主体代码是一个循
             * 环，可以在循环开始处进行检查，如下所示：
             */
            /*当没有中断时*/
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



