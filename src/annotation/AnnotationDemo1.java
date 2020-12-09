package annotation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luffy
 * @version 1.0.0
 * @date 2020/10/10
 * @since jdk 1.5
 */
@SuppressWarnings("all")
@ZnkAnno2
public class AnnotationDemo1 {
    @ZnkAnno2
    int id = 1;

    /**
     * 计算两个整型数值的和
     *
     * @param a 第一个整型数值
     * @param b 第二个整型数值
     * @return 两个整型数值的和
     */
    public static int addition(int a, int b) {
        return a + b;
    }

    @Deprecated
    //This method is replaced by aiShow()
    public static void show() {
        System.out.println("一个展示技能的方法");
    }

    @ZnkAnno2
    public static void aiShow() {
        System.out.println("一个可以智能展示技能的方法");
    }

    @ZnkAnno2
    public static void test() {
        show();
        aiShow();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
        Date date = new Date();
        System.out.println("当前时间：" + sdf.format(date));

    }
}
