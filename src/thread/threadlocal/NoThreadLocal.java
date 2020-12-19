package thread.threadlocal;

/**
 * @author Luffy
 * @Classname NoThreadLocal
 * @Description 本来想着实现线程分离，也就是说每个线程持有自己的变量，各个线程操作自己的变量，互不干扰，可运行结果一直在变。
 * @Date 2020/12/17 13:37
 */
public class NoThreadLocal {
    static Integer count = new Integer(1);

    /**
     * 运行3个线程
     */
    public void StartThreadArray() {
        for (int i = 0; i < 3; i++) {
            new Thread(new TestTask(i)).start();
        }
    }

    /**
     * @Description 完成任务id与count相加，并输出结果
     */
    public static class TestTask implements Runnable {
        int id;

        public TestTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            count = count + id;
            System.out.println(Thread.currentThread().getName() + ":"
                    + count);
        }
    }

    public static void main(String[] args) {
        NoThreadLocal test = new NoThreadLocal();
        test.StartThreadArray();
    }
}
