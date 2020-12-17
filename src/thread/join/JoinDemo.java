package thread.join;

/**
 * 面试题：
 * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行?
 *
 * @author Luffy
 */
@SuppressWarnings("all")
public class JoinDemo {

    public static void main(String[] args) {
        /*初始化线程t1*/
        final Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("t1 is running");
            }
        });
        /*初始化线程t2*/
        final Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    /*t1调用join方法,t2会等待t1运行完之后才会开始执行后续代码*/
                    t1.join();
                    System.out.println("t2 is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    System.out.println("t2 is running");
                }
            }
        });
        /*初始化线程t3*/
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    /*t2调用join方法,t3会等待t2运行完之后才会开始执行后续代码*/
                    t2.join();
                    System.out.println("t3 is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    System.out.println("t3 is running");
                }
            }
        });
        /*启动3个线程,启动顺序随便搞，反正不是我们说了算的，知者自知，不多做解释*/
        t3.start();
        t2.start();
        t1.start();
    }
}

