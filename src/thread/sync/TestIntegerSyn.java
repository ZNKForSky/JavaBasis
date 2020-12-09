package thread.sync;

/**
 * 类说明：错误的加锁和原因分析
 */
public class TestIntegerSyn {

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker(0);

        for (int j = 0; j < 5; j++) {
            /**
             * 如果每次进来先休眠1秒，在这1秒中，线程足以完成任务，这样线程会按顺序开启
             */
            //Thread.sleep(1000);
            new Thread(worker).start();
        }
    }

    private static class Worker implements Runnable {
        private Integer i;
        //private Object obj = new Object();

        public Worker(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (i) {
                Thread thread = Thread.currentThread();
                //System.out.println(thread.getName() + "---自增之前---i === " + i + "---@" + System.identityHashCode(i));
                /**
                 * i++ 最终在JVM执行的是：Integer integer1 = this.i, integer2 = this.i = Integer.valueOf(this.i.intValue() + 1);
                 * 而Integer.valueOf(i)方法会创建新的Integer对象，所以我们锁的对象一直在变化，就导致锁失效了。
                 *
                 * 解决方案：创建一个Object对象，给它上锁
                 * private Object obj = new Object();
                 * synchronized (obj) {}
                 *
                 */
                i++;
                System.out.println(thread.getName() + "---自增之后---i === " + i + "---@" + System.identityHashCode(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(thread.getName() + "---finally---i === " + i + "---@" + System.identityHashCode(i));
            }
        }
    }
}
