package design.single;

/**
 * @author Luffy
 * @Classname Singleton4
 * @Description 静态内部类实现单例模式
 * @Date 2021/3/8 15:27
 */
public class Singleton4 {
    //私有的构造方法
    private Singleton4() {
        System.out.println("Singleton4 被创建了~");
    }

    private static class SingletonBuilder {
        public static final Singleton4 INSTANCE = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return SingletonBuilder.INSTANCE;
    }

}
