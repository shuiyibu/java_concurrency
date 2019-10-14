package utils.exchanger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * 连接池
 * <p>
 * 获取线程策略
 * - get 1000ms then throw exception
 * - blocking
 * - discard
 * - get then throw exception
 * - get -> register the callback
 */
public class SemaphoreExample2 {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    out.println(Thread.currentThread().getName() + " in");
                    try {
                        semaphore.acquire(2);
                        out.println(Thread.currentThread().getName() + " get the semaphore");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        /**
                         * acquire 和 release 的 permits 数目必须一致
                         */
                        semaphore.release(1);
                    }
                    out.println(Thread.currentThread().getName() + " out");

                }
            }.start();

            while (true) {
                try {
                    out.println("AP -> " + semaphore.availablePermits());
                    out.println("QL -> " + semaphore.getQueueLength());
                    TimeUnit.SECONDS.sleep(1);
                    out.println("======================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
