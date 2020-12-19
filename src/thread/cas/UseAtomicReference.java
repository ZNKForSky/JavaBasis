package thread.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Luffy
 * @Classname UseAtomicReference
 * @Description 使用JDK提供的 AtomicReference解决“CAS只能保证一个共享变量的原子操作”的问题
 * @Date 2020/12/19 23:37
 */
public class UseAtomicReference {
    static AtomicReference<UserInfo> atomicUserRef;

    public static void main(String[] args) {
        /*要修改的实体的实例*/
        UserInfo user = new UserInfo("Luffy", 17);
        atomicUserRef = new AtomicReference(user);
        UserInfo updateUser = new UserInfo("Lady Gaga", 25);
        atomicUserRef.compareAndSet(user, updateUser);

        System.out.println(atomicUserRef.get());
        System.out.println(user);
    }

    /**
     * 定义一个用户实体类
     */
    static class UserInfo {
        private volatile String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
