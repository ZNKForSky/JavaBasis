package thread.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Luffy
 * @Classname UseAtomicStampedReference
 * @Description 使用JDK提供的 AtomicStampedReference解决CAS的ABA问题。
 * @Date 2020/12/19 23:44
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr
            = new AtomicStampedReference("Luffy", 0);

    public static void main(String[] args) throws InterruptedException {
        /*拿到当前的版本号(旧)*/
        final int oldStamp = asr.getStamp();
        /*拿到当前的引用变量(旧)*/
        final String oldReference = asr.getReference();
        System.out.println(Thread.currentThread().getName() + "当前变量值： " + oldReference + "，当前版本戳： " + oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ":当前变量值："
                        + oldReference + "，当前版本戳：" + oldStamp + "，"
                        + asr.compareAndSet(oldReference,
                        oldReference + " Java", oldStamp,
                        oldStamp + 1));
            }
        });

        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName()
                        + ":当前变量值："
                        + reference + "，当前版本戳：" + asr.getStamp() + "，"
                        + asr.compareAndSet(reference,
                        reference + " C", oldStamp,
                        oldStamp + 1));
            }
        });
        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();

        System.out.println(asr.getReference() + "============" + asr.getStamp());
    }
}
