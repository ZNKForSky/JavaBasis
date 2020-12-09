package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Properties(className = "annotation.AnnotationDemo1", methodName = "show")
public class ReflectTest {
    public static void main(String[] args) {
        Properties properties = ReflectTest.class.getAnnotation(Properties.class);
        String className = properties.className();
        String methodName = properties.methodName();

        System.out.println("className === " + className);
        System.out.println("methodName === " + methodName);

        try {
            Class<?> cls = Class.forName(className);
            Object obj = null;
            obj = cls.newInstance();
            Method method = null;
            method = cls.getMethod(methodName);
            method.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
