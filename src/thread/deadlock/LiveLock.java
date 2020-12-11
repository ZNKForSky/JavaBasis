package thread.deadlock;

import java.util.Random;

/**
 * ======================================================================================
 * 活锁例子
 * 创建一个勺子类，有且只有一个勺子的实例。
 * 丈夫和妻子用餐时，需要使用勺子，这时只能有一人持有，也就是说同一时刻只有一个人能够进餐。
 * 但是丈夫和妻子互相谦让，都想让对方先吃，所以勺子一直传递来传递去，谁都没法用餐。
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
@SuppressWarnings("all")
public class LiveLock {

    /**
     * 勺子类
     */
    static class Spoon {
        /*勺子的拥有者*/
        Diner owner;

        /**
         * 获取勺子拥有者
         */
        String getOwnerName() {
            return owner.getName();
        }

        /**
         * 设置拥有者
         *
         * @param diner 晚餐实例对象，也就是勺子的拥有者
         */
        void setOwner(Diner diner) {
            this.owner = diner;
        }

        Spoon(Diner diner) {
            this.owner = diner;
        }

        /**
         * 表示正在用餐
         */
        void use() {
            System.out.println(owner.getName() + " use this spoon and finish eat.");
        }
    }

    /**
     * 晚餐类
     */
    static class Diner {
        Diner(boolean isHungry, String name) {
            this.isHungry = isHungry;
            this.name = name;
        }

        /*是否饿了*/
        private boolean isHungry;
        /*定义当前用餐者的名字*/
        private String name;

        /**
         * 获取当前用餐者
         *
         * @return 当前用餐者
         */
        public String getName() {
            return name;
        }

        /**
         * 可以理解为和某人吃饭
         *
         * @param spouse      共餐对象
         * @param sharedSpoon 勺子对象实例，可以通过该实例获取勺子拥有者
         */
        void eatWith(Diner spouse, Spoon sharedSpoon) {
            try {
                synchronized (sharedSpoon) {
                    while (isHungry) {
                        /*当勺子拥有者和用餐者不是同一个人，则进行等待*/
                        while (!sharedSpoon.getOwnerName().equals(name)) {
                            sharedSpoon.wait();
                        }
                        /*spouse此时是饿了，把勺子分给他，并通知他可以用餐*/
                        if (spouse.isHungry) {
                            System.out.println("I am " + name + ", and my " + spouse.getName() + " is hungry, I should give it to him(her).\n");
                            sharedSpoon.setOwner(spouse);
                            sharedSpoon.notifyAll();
                        } else {
                            //用餐
                            System.out.println("用餐中---------------");
                            sharedSpoon.use();
                            sharedSpoon.setOwner(spouse);
                            isHungry = false;
                        }
                        Thread.sleep(500);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(name + " is interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        /*创建一个丈夫用餐类*/
        final Diner husband = new Diner(true, "husband");
        /*创建一个妻子用餐类*/
        final Diner wife = new Diner(true, "wife");
        /*创建一个勺子，初始状态由妻子持有*/
        final Spoon sharedSpoon = new Spoon(wife);

        /*创建一个线程，由丈夫进行用餐*/
        Thread h = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(new Random().nextInt(6));
                    /*表示和妻子用餐，这个过程判断妻子是否饿了，如果是，则会把勺子分给妻子，并通知她*/
                    husband.eatWith(wife, sharedSpoon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        h.start();

        /*创建一个线程，由妻子进行用餐*/
        Thread w = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(new Random().nextInt(6));
                    /*表示和妻子用餐，这个过程判断丈夫是否饿了，如果是，则会把勺子分给丈夫，并通知他*/
                    wife.eatWith(husband, sharedSpoon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        w.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        h.interrupt();
        w.interrupt();

        try {
            /*join()方法阻塞调用此方法的线程(calling thread)，直到线程t完成，此线程再继续；通常用于在main()主线程内，等待其它线程完成再结束main()主线程。*/
            h.join();
            w.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

