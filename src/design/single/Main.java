package design.single;

import java.util.Random;

/**
 * @author Luffy
 * @Classname Main
 * @Description 测试各种单例模式的实现方式
 * @Date 2021/3/8 13:54
 */
public class Main {
    public static void main(String[] args) {
        //单线程模式下没有问题
//        Singleton1 instance1 = Singleton1.getInstance();
//        Singleton1 instance2 = Singleton1.getInstance();
//        Singleton1 instance3 = Singleton1.getInstance();
//
//        System.out.println("instance1 = " + instance1);
//        System.out.println("instance2 = " + instance2);
//        System.out.println("instance3 = " + instance3);

        //多线程模式下线程不安全
        for (int i = 0; i < 100; i++) {
            final int index = i;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(getSpecificRandom(100, 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Singleton4 instance = Singleton4.getInstance();
                    //Singleton5 singleton = Singleton5.INSTANCE;
                    System.out.println("instance" + index + " = " + instance);
                }
            }.start();
        }
    }

    /**
     * @param min 最小值
     * @param max 最大值
     * @return 返回[min, max]之间的一个整型数据
     */
    private static int getSpecificRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
