package design.single;

/**
 * @author Luffy
 * @Classname Singleton3
 * @Description 饿汉式单例模式，是线程安全的
 * @Date 2021/3/8 14:34
 */
public class Singleton3 {
    // 私有的静态实例
    private static Singleton3 mSingleTon = new Singleton3();

    // 私有的构造方法
    private Singleton3() {
        System.out.println("Singleton3 被创建了~");
    }

    //公开的创建实例方法
    public static Singleton3 getInstance() {
        return mSingleTon;
    }
}
