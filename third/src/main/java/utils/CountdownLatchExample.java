package utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-25 15:27
 * @Description:
 */
public class CountdownLatchExample {

    private static final CountDownLatch latch = new CountDownLatch(10);
    private static Random random = new Random((System.currentTimeMillis()));
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        int[] data = query();
        for (int i = 0; i < data.length; i++) {
            executor.execute(new SimpleRunnable(data, i, latch));
        }

        executor.shutdown();
        latch.await();
        // executor.awaitTermination(1, TimeUnit.DAYS);
        out.println("All of work finish down");

        out.println(data);
    }

    private static int[] query() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    }

    static class SimpleRunnable implements Runnable {
        private final int[] data;
        private final int index;
        private final CountDownLatch latch;


        public SimpleRunnable(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int value = data[index];
            if (value % 2 == 0) {
                data[index] = value * 2;
            } else {
                data[index] = value * 10;
            }
            out.println(Thread.currentThread().getName() + " finished.");
            latch.countDown();

        }
    }
}
