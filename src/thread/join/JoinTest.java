package thread.join;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Thread t1 = new Thread(new Worker("thread-1"));
        Thread t2 = new Thread(new Worker("thread-2"));
        Thread t3 = new Thread(new Worker("thread-3"));

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

//  　　BLOCKED　　受阻塞并且正在等待监视器锁的某一线程的线程状态。　　下列情况会进入阻塞状态：
// 　　1.等待某个操作的返回，例如IO操作，该操作返回之前，线程不会继续下面的代码。
// 　　2.等待某个“锁”，在其他线程或程序释放这个“锁”之前，线程不会继续执行。
// 　　3.等待一定的触发条件。　
// 　4.线程执行了sleep方法。　
// 　5.线程被suspend()方法挂起。　　
// 一个被阻塞的线程在下列情况下会被重新激活：　
// 　1.执行了sleep()方法，睡眠时间已到。　　
// 2.等待的其他线程或程序持有的“锁”已被释放。　　
// 3.正在等待触发条件的线程，条件得到满足。　　
// 4.执行了suspend()方法，被调用了resume()方法。　　
// 5.等待的操作返回的线程，操作正确返回。 　　
//
// WAITING　　某一等待线程的线程状态。　　线程因为调用了Object.wait()或Thread.join()而未运行，就会进入WAITING状态。
//TIMED_WAITING　　具有指定等待时间的某一等待线程的线程状态。　　线程因为调用了Thread.sleep()，或者加上超时值来调用Object.wait()或Thread.join()而未运行，则会进入TIMED_WAITING状态。
//  　　TERMINATED　　已终止线程的线程状态。　　线程已运行完毕。它的run()方法已正常结束或通过抛出异常而结束。　　线程的终止　　run()方法结束，线程就结束。
