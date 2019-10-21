package utils;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;

public class CountDownLatchExample3 {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Thread mainThread = Thread.currentThread();
        out.println("====");
        /**
         * Exception in thread "main" java.lang.IllegalArgumentException: count < 0
         * 	at java.util.concurrent.CountDownLatch.<init>(CountDownLatch.java:199)
         * 	at utils.CountDownLatchExample3.main(CountDownLatchExample3.java:12)
         */
        //    latch = new CountDownLatch(-1);


        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //        latch.countDown();
            }
        }.start();
        /**
         * Causes the current thread to wait until the latch has counted down to
         * zero, unless the thread is {@linkplain Thread#interrupt interrupted}.
         */
        mainThread.interrupt();
//        latch.await(1000, TimeUnit.SECONDS);
        latch.await();

    }
}
