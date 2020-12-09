package annotation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class TestCheck {
    public static void main(String[] args) throws IOException {
        //1.创建接受检测异常的类对象
        Calculator calculator = new Calculator();
        //2.获取该类对象的Class对象
        Class<? extends Calculator> cls = calculator.getClass();
        Integer[] a = new Integer[3];
        Integer[] o = (Integer[]) Array.newInstance(Integer.class);
        o[0] = 1;
        o[1] = 1;
        o[2] = 1;
        for (int i = 0; i <o.length ; i++) {
            System.out.println(o[i]);
        }

        //3.通过该类对象的Class对象获取该类所有方法
        Method[] methods = cls.getMethods();
        //4.定义变量，统计异常出现次数
        int exceptionCount = 0;
        //5.通过IO流的方式把异常写入“bug.txt"中
        BufferedWriter writer = new BufferedWriter(new FileWriter("bug.txt"));
        //6.检查每个方法是否被@Check标记
        for (Method method : methods) {
            System.out.println("method === "+method);
            //7.如果是，则执行方法，进行异常检测
            if (method.isAnnotationPresent(Check.class)) {
                System.out.println("进来了------");
                try {
                    method.invoke(calculator);
                } catch (Exception e) {
                    //7.捕获异常
                    e.printStackTrace();
                    exceptionCount++;
                    writer.write(method.getName() + "出现了异常");
                    writer.newLine();
                    writer.write("异常名称" + e.getCause().getClass().getName());
                    writer.newLine();
                    writer.write("异常详情" + e);
                    writer.newLine();
                    writer.write("-----------------------");
                }
            }
        }
        writer.write("Calculater总共出现了" + exceptionCount + "次异常");
        //8.最后记得刷新流并关闭流
        writer.flush();
        writer.close();

    }
}
