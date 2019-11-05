package atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 16:09
 * @Description:
 */
public class UnsafeTest {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
//        Unsafe unsafe = Unsafe.getUnsafe();
//        out.println(unsafe);
//        out.println(getUnsafe());

        /**
         * Stupid Counter
         *
         * Counter result: 100000000
         * Time passed in ms: 362
         */
//        Counter counter = new StupidCounter();

        /**
         * Sync Counter
         *
         * Counter result: 100000000
         * Time passed in ms: 3326 3184 3309
         */
//        Counter counter = new SyncCounter();

        /**
         * Lockr Counter
         *
         * Counter result: 100000000
         * Time passed in ms: 3758 3230 3430
         */
//        Counter counter = new LockCounter();
        /**
         * Atomic Counter
         *
         * Counter result: 100000000
         * Time passed in ms: 2431 2493 2513
         */
//        Counter counter = new AtomicCounter();
        /**
         * Atomic Counter
         *
         * Counter result: 100000000
         * Time passed in ms: 10891
         */
        Counter counter = new CASCounter();
        ExecutorService service = Executors.newFixedThreadPool(1000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnable(counter, 100000));
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        out.println("Counter result: " + counter.getCounter());
        out.println("Time passed in ms: " + (end - start));
    }

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface Counter {
        void increment();

        long getCounter();
    }

    static class CounterRunnable implements Runnable {
        private final Counter counter;
        private final int num;

        public CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }

    static class StupidCounter implements Counter {
        private int counter = 0;

        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class SyncCounter implements Counter {
        private int counter = 0;

        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class LockCounter implements Counter {
        private final Lock lock = new ReentrantLock();
        private int counter = 0;

        @Override
        public void increment() {
            try {
                lock.lock();
                counter++;
            } finally {
                lock.unlock();
            }

        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class AtomicCounter implements Counter {
        private final Lock lock = new ReentrantLock();
        private AtomicLong counter = new AtomicLong();

        @Override
        public void increment() {

            counter.incrementAndGet();


        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }

    static class CASCounter implements Counter {
        private final Lock lock = new ReentrantLock();
        private volatile long counter = 0;
        private Unsafe unsafe;
        private long offset;

        public CASCounter() throws NoSuchFieldException {
            unsafe = getUnsafe();
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {

            long current = counter;
            while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
                current = counter;
            }

        }

        @Override
        public long getCounter() {
            return counter;
        }
    }
}
