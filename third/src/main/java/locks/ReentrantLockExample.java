package locks;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

public class ReentrantLockExample {
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        IntStream.range(0, 2).forEach(i -> {
//            new Thread() {
//                @Override
//                public void run() {
//                    needLock();
//                }
//            }.start();
//        });

//        Thread thread1 = new Thread(() -> testUnInterruptibly());
//        thread1.start();
//        TimeUnit.SECONDS.sleep(1);
//
//        Thread thread2 = new Thread(() -> testUnInterruptibly());
//        thread2.start();
//        TimeUnit.SECONDS.sleep(1);
//        thread2.interrupt();
//        out.println("===========");

        Thread thread1 = new Thread(() -> testTryLock());
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(() -> testTryLock());
        thread2.start();
        TimeUnit.SECONDS.sleep(1);
        //  thread2.interrupt();
        out.println("===============================================================");
        Optional.of(lock.getQueueLength()).ifPresent(out::println);
        Optional.of(lock.hasQueuedThreads()).ifPresent(out::println);
        Optional.of(lock.getHoldCount()).ifPresent(out::println);
        Optional.of(lock.hasQueuedThread(thread1)).ifPresent(out::println);
        Optional.of(lock.hasQueuedThread(thread2)).ifPresent(out::println);
        Optional.of(lock.isLocked()).ifPresent(out::println);
    }

    public static void needLock() {
        try {
            lock.lock();
            Optional.of("The thread-" + Thread.currentThread().getName() + " get lock and will do working").ifPresent(out::println);
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void needLockBySync() {
        synchronized (ReentrantLockExample.class) {
            try {
                Optional.of("The thread-" + Thread.currentThread().getName() + " get lock and will do working").ifPresent(out::println);
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void testUnInterruptibly() {
        try {
//            lock.lock();//无法被打断
            lock.lockInterruptibly();//可以被打断
            Optional.of("The thread-" + Thread.currentThread().getName() + " get lock and will do working").ifPresent(out::println);
            while (true) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void testTryLock() {

        try {
            if (lock.tryLock(30, TimeUnit.SECONDS)) {
                Optional.of(Thread.currentThread().getName() + "'s holdCount: " + lock.getHoldCount()).ifPresent(out::println);

                try {
                    Optional.of("The thread-" + Thread.currentThread().getName() + " get lock and will do working").ifPresent(out::println);
                    while (true) {
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                Optional.of("The thread-" + Thread.currentThread().getName() + " does not get lock.").ifPresent(out::println);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
