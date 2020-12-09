import annotation.AnnotationDemo1;
import annotation.Fruit;
import annotation.ZnkAnno;
import annotation.ZnkAnno2;

@SuppressWarnings("all")
@ZnkAnno(age = 27, name = "luffy", sex = "male", annotation = @ZnkAnno2, fruitType = Fruit.APPLE, hobby = {"computer games", "soccer"})
public class Main {
    public int arr[] = {1, 2};

    public static void main(String[] args) {
        AnnotationDemo1.test();
//        Object obj = new Object();
//        /**
//         * identityHashCode
//         * 无论给定对象的类是否覆盖hashCode()，为给定对象返回与默认方法hashCode()返回的哈希代码相同的哈希代码。空引用的哈希码为零。默认hashCode()即Object的hashCode()。
//         */
//        System.out.println("identityHashCode = " + System.identityHashCode(obj));
//        System.out.println("hashCode = " + obj.hashCode());
    }

    private static int add(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
