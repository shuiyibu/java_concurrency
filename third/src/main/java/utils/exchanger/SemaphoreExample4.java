package utils.exchanger;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class SemaphoreExample4 {
    public static void main(String[] args) throws InterruptedException {
        final MySemaphore semaphore = new MySemaphore(5);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    semaphore.drainPermits();
                    TimeUnit.SECONDS.sleep(5);
                    out.println("t1: " + semaphore.availablePermits());
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
                out.println("T1 finished..");
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    out.println("t2: " + semaphore.availablePermits());

//                    semaphore.acquire();
                    semaphore.tryAcquire();
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
                out.println("T2 finished..");
            }
        };
        t2.start();

        TimeUnit.SECONDS.sleep(1);
        out.println("hasQueuedThreads: " + semaphore.hasQueuedThreads());
        Collection<Thread> waitingThreads = semaphore.getWaitingThreads();
        for (Thread t : waitingThreads) {
            out.println("waitingThreads" + t);
        }
    }

    static class MySemaphore extends Semaphore {


        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
            super.getQueuedThreads();
        }

        public Collection<Thread> getWaitingThreads() {
            return super.getQueuedThreads();
        }
    }
}
