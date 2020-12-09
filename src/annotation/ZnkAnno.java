package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@SuppressWarnings("all")
public @interface ZnkAnno {
    int age();

    String name();

    String sex();

    ZnkAnno2 annotation();

    Fruit fruitType();

    String[] hobby();

}
