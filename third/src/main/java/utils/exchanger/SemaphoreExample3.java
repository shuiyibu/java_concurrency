package utils.exchanger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * @description: 不可被中断的信号量
 * @auther:
 * @date: 2019-10-03 09:59
 */
public class SemaphoreExample3 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
                out.println("T1 finished.");
            }
        };
        thread.start();
        TimeUnit.MICROSECONDS.sleep(50);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
                out.println("T2 finished.");

            }
        };
        thread2.start();
        TimeUnit.MICROSECONDS.sleep(50);
        thread2.interrupt();
    }
}
