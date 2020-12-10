package annotation;

@SuppressWarnings("all")
@ZnkAnno(age = 27, name = "luffy", sex = "male", annotation = @ZnkAnno2, fruitType = Fruit.APPLE, hobby = {"computer games", "soccer"})
public class Main {
    public int arr[] = {1, 2};

    public static void main(String[] args) {
        AnnotationDemo1.test();
    }

    private static int add(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
