package utils;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;

public class CountDownLatchExample2 {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);


        new Thread() {
            @Override
            public void run() {
                out.println("Do some initial working....");
                try {
                    Thread.sleep(1000);
                    latch.await();
                    out.println("Do other working");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                out.println("Asyn prepare for data....");
                try {
                    Thread.sleep(2000);
                    out.println("Data prepared!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    latch.await();
                    out.println("The second await release!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        Thread.currentThread().join(10_000);
    }
}
