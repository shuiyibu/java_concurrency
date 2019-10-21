package locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * @description:
 * @auther:
 * @date: 2019-10-06 10:39
 */
public class ReadWriteLockExample {
    /*
     * 无法实现读操作的锁共享
     */
//    private final static ReentrantLock lock = new ReentrantLock(true);
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final static Lock readLock = lock.readLock();
    private final static Lock writeLock = lock.writeLock();

    private final static List<Long> data = new ArrayList<>();

    /**
     * W W X
     * W R X
     * R W X
     * R R O
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> write()).start();
        new Thread(() -> write()).start();
        TimeUnit.SECONDS.sleep(5);
        out.println("==============================================================");
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
    }

    public static void write() {
        try {
            writeLock.lock();
            data.add(currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void read() {
        try {
            readLock.lock();
            data.forEach(out::println);
        } finally {
            readLock.unlock();
        }
    }
}
