package utils.condition;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * @description: 模拟生产者-消费者模型
 * @auther:
 * @date: 2019-10-06 20:16
 */
public class ConditionExample {
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();
    private static int data = 0;

    private static volatile boolean noUse = true;


    private static void buildData() {
        try {
            lock.lock(); //synchronized keyword #monitor enter
            while (noUse) {
                condition.await(); //monitor.wait()
            }
            data++;
            Optional.of("P: " + data).ifPresent(out::println);
            //   TimeUnit.SECONDS.sleep(1);
            noUse = true;
            condition.signal(); //monitor.notify()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //synchronized keyword #monitor end
        }
    }


    private static void useData() {
        try {
            lock.lock();
            while (!noUse) {
                condition.await();
            }
            //  data--;
            Optional.of("C[" + Thread.currentThread().getName() + "]: " + data).ifPresent(out::println);
            noUse = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) buildData();
        }).start();
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                while (true) useData();
            }).start();
        });

    }
}
