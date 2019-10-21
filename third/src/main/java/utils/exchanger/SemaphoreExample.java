package utils.exchanger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class SemaphoreExample {
    public static void main(String[] args) {
        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        lock.lock();
                        out.println(Thread.currentThread().getName() + " get the #semaphoreLock");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    out.println(Thread.currentThread().getName() + " release the #semaphoreLock");

                }
            }.start();
        }


    }

    private static synchronized void m() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 与 synchronized 的关键字相比，semaphore可以同时执行n个线程
     */
    static class SemaphoreLock {
        private final Semaphore semaphore = new Semaphore(2);

        private void lock() throws InterruptedException {
            semaphore.acquire();
        }

        private void unlock() {
            semaphore.release();
        }
    }
}
