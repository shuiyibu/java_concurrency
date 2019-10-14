package utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * @description: 没有裁判员（countdown）
 * @auther:
 * @date: 2019-09-29 20:18
 */
public class CyclicBarrierExample1 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            out.println("All of finished.");
        });

        new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(20);
                    out.println("T1 finished.");
                    barrier.await();
                    out.println("T1 the other thread finished too.");

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    out.println("T2 finished.");
                    barrier.await();
                    out.println("T2 the other thread finished too.");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //  barrier.await();
        //   out.println("========");
        while (true) {
            out.println(barrier.getNumberWaiting());
            out.println(barrier.getParties());
            out.println(barrier.isBroken());
            TimeUnit.SECONDS.sleep(1);
            out.println("--------------------");

        }

    }
}
