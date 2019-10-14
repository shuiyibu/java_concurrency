package utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class CyclicBarrierExample3 {


    public static void main(String[] args) throws InterruptedException {
        final MyCountDownLatch latch = new MyCountDownLatch(2, () -> {
            out.println(" all of work finished.");
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                out.println(Thread.currentThread().getName() + " finished");
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                out.println(Thread.currentThread().getName() + " finished.");

            }
        }.start();
    }

    static class MyCountDownLatch extends CountDownLatch {
        private final Runnable runnable;

        public MyCountDownLatch(int count, Runnable runnable) {
            super(count);
            this.runnable = runnable;
        }

        /**
         * 存在线程安全的问题
         */
        @Override
        public void countDown() {
            super.countDown();
            if (getCount() == 0) {
                this.runnable.run();

            }

        }


    }
}
