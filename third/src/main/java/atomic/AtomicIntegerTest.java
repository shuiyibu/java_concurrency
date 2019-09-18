package atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-18 15:43
 * @Description:
 */
public class AtomicIntegerTest {
    /**
     * 1. 可见性
     * 2. 内存屏障（顺序性）
     * <p>
     * > 不能保证原子性
     */
    private static volatile int value;

    private static Set set = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int x = 0;
            while (x++ < 500) {
                set.add(value);
                int tmp = value++;
                out.println(Thread.currentThread().getName() + ": " + tmp);
            }
        };

//        startThreads(runnable);
//        out.println(set.size());
        set.clear();

        final AtomicInteger value = new AtomicInteger();
        Runnable runnable2 = () -> {
            int x = 0;
            while (x++ < 500) {
                int tmp = value.getAndIncrement();
                set.add(tmp);
                out.println(Thread.currentThread().getName() + ": " + tmp);
            }
        };

        startThreads(runnable2);
        out.println(set.size());
    }

    public static void startThreads(Runnable runnable) throws InterruptedException {
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}
