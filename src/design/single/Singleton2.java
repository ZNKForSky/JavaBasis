package design.single;

/**
 * @author Luffy
 * @Classname Singleton2
 * @Description 对Singleton1的优化
 * @Date 2021/3/8 14:49
 */
public class Singleton2 {
    //私有的静态实例变量
    private volatile static Singleton2 mInstance;

    //私有的构造方法
    private Singleton2() {
        System.out.println("Singleton2被创建了~");
    }

    //DCL --- 双重检验锁
    public static Singleton2 getInstance() {
        //第一层检查，如果已创建过实例，就不用加锁了，极大提升了程序的性能
        if (mInstance == null) {
            synchronized (Singleton2.class) {
                //第二层检查：这是实现单例的必要操作
                if (mInstance == null) {
                    mInstance = new Singleton2();
                    //mInstance = new Singleton2();在字节码文件中会有三个步骤
                    //1. 为 mInstance实例分配对象
                    //2. 调用 Singleton2的构造方法初始化成员字段
                    //3. 将 Singleton2对象赋值给 mInstance
                    //上述三个步骤对应三个指令，JDK会指令重排，也就是说不一定会按照上面是哪个
                    // 步骤一步一步执行，就会导致 DCL失效,所以在 JDK1.5之后，引入了volatile
                    //关键字，防止指令重排
                }
            }
        }
        return mInstance;
    }
}
