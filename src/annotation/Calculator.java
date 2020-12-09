package annotation;

public class Calculator {
    //加法
    @Check
    public void addition(){
        System.out.println("1 + 0 "+(1+0));
    }

    //减法
    @Check
    public void subtraction(){
        System.out.println("1 - 0 "+(1-0));
    }

    //乘法
    @Check
    public void multiplication(){
        System.out.println("1 * 0 "+(1*0));
    }

    //除法
    @Check
    public void division(){
        System.out.println("1 / 0 "+(1/0));
    }

    public void show(){
        System.out.println("祈祷代码永无bug~");
    }

}
