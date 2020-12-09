package thread.vola;

/**
 * 不加 synchronized,用volatile关键字并不能保证同步性,volatile只适用于一写多读的场景。
 */
public class NotSafe {

    private volatile long count = 0;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * count进行累加
     */
    public void increaseCount() {
        count++;
    }

    private static class Count extends Thread {

        private NotSafe simplOper;

        public Count(NotSafe simplOper) {
            this.simplOper = simplOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                simplOper.increaseCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotSafe simplOper = new NotSafe();
        /**
         * 启动两个线程
         */
        Count count1 = new Count(simplOper);
        Count count2 = new Count(simplOper);
        count1.start();
        count2.start();
        Thread.sleep(50);
        System.out.println(simplOper.count);
    }
}
