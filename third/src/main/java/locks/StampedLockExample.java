package locks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class StampedLockExample {

    private final static StampedLock lock = new StampedLock();
    private final static List<Long> DATA = new ArrayList<>();

    /**
     * ReentrantLock vs Synchronized
     *
     * <p>
     *
     * </p>
     * ReentrantReadWriteLock
     * 100 threads
     * 99 threads need read lock
     * 1 thread needs read lock
     * <p>
     * ===> 导致写饥饿
     *
     * @param args
     */
    public static void main(String[] args) {
        final ExecutorService service = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };
        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };

        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(writeTask);

    }

    private static void read() {
        long stamped = -1;
        try {
            stamped = lock.readLock();

            Optional.of(DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))).ifPresent(out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }
    }

    private static void write() {
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            DATA.add(System.currentTimeMillis());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }
}
