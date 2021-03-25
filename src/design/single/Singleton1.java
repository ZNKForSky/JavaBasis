package design.single;

/**
 * @author Luffy
 * @Classname Singleton1
 * @Description 懒汉式单例模式
 * @Date 2021/3/8 13:56
 */
public class Singleton1 {
    //私有的静态实例变量
    private static Singleton1 mInstance;

    //私有的构造方法
    private Singleton1() {
        System.out.println("Singleton1被创建了~");
    }

    //懒汉式 加锁的情况下，由于 synchronized是重量级锁，粒度太大，影响性能；不加锁线程不安全
    public static synchronized Singleton1 getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton1();
        }
        return mInstance;
    }
}
