package atomic;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-19 15:15
 * @Description:
 */
public class AtomicIntegerDetailTest2 {
    private final static CompareAndSetLock lock = new CompareAndSetLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        doSomething2();
                    } catch (Exception | GetLockException ex) {
                        ex.printStackTrace();
                    }

                }
            }.start();
        }
    }

    public static void doSomething() throws InterruptedException {
        synchronized (AtomicIntegerDetailTest2.class) {
            out.println(Thread.currentThread().getName() + " get the lock");
            Thread.sleep(100_000);
        }
    }

    /**
     * Thread-0 get the lock
     * atomic.GetLockException
     * Thread-2 get the lock
     * at atomic.CompareAndSetLock.tryLock(CompareAndSetLock.java:15)
     * at atomic.AtomicIntegerDetailTest2.doSomething2(AtomicIntegerDetailTest2.java:39)
     * at atomic.AtomicIntegerDetailTest2$1.run(AtomicIntegerDetailTest2.java:19)
     * atomic.GetLockException
     * Thread-4 get the lock
     * at atomic.CompareAndSetLock.tryLock(CompareAndSetLock.java:15)
     * at atomic.AtomicIntegerDetailTest2.doSomething2(AtomicIntegerDetailTest2.java:39)
     * at atomic.AtomicIntegerDetailTest2$1.run(AtomicIntegerDetailTest2.java:19)
     */
    public static void doSomething2() throws InterruptedException, GetLockException {

        try {
            lock.tryLock();
            out.println(Thread.currentThread().getName() + " get the lock");
            Thread.sleep(100_000);
        } finally {//一个锁可能被多个线程拿到
            lock.unLock();
        }

    }
}
