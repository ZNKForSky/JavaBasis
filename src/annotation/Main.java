package annotation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 只有一个main方法，Java虚拟机却开启了6个线程，表明Java天生就是多线程的。
 * [6]Monitor Ctrl-Break
 * [5]Attach Listener
 * [4]Signal Dispatcher
 * [3]Finalizer
 * [2]Reference Handler
 * [1]main
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> stringFutureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Callable implements Thread~");
                return "Callable implements Thread";
            }
        });

        new Thread(stringFutureTask).start();
        System.out.println("stringFutureTask返回值 = " + stringFutureTask.get());
    }
}

