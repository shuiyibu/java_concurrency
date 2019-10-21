package utils.condition;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class ConditionExample3 {
    private final static Lock lock = new ReentrantLock();

    private final static Condition PRODUCE_COND = lock.newCondition();

    private final static Condition CONSUME_COND = lock.newCondition();

    private final static LinkedList<Long> TIMESTAMP_POOL = new LinkedList<>();

    private final static int MAX_CAPACITY = 100;

    private static void produce() {
        try {
            lock.lock();
            while (TIMESTAMP_POOL.size() >= MAX_CAPACITY) PRODUCE_COND.await();
            long value = currentTimeMillis();
            Optional.of(Thread.currentThread().getName() + "-P-" + value).ifPresent(out::println);
            TIMESTAMP_POOL.add(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

    }
}
