package thread.interrupt;

/**
 * 案例二：关于线程处于WAITING或者TIMED_WAITING状态下，对线程对象调用interrupt()会使得该线程抛出InterruptedException的问题处理
 *
 * @author Luffy
 */
@SuppressWarnings("all")
public class InterruptThread2ndCase {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        /*休眠一秒，模拟线程执行任务*/
                        sleep(2000);
                    } catch (InterruptedException e) {
                        /* ... 清理操作*/
                        /*InterruptedException被捕获，但是输出为false,说明标志位被清空了。 ------ 因为线程为了处理异常需要重新进入就绪状态，*/
                        System.out.println("catch --- " + isInterrupted());
                        System.out.println("当前线程状态: " + getState());
                        /*重设中断标志位为true*/
                        interrupt();

                    }
                    System.out.println("循环内---重设后当前线程是否被中断： " + isInterrupted());
                }
                System.out.println("子线程中---重设后当前线程是否被中断： " + isInterrupted());
            }
        };
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        thread.interrupt();
    }
}