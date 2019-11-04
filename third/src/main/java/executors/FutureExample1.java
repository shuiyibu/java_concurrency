package executors;

import java.util.concurrent.*;

import static java.lang.System.out;

public class FutureExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testGet();
        testGetWithTimeout();
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
//            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
        Future<Integer> future = executor.submit(() -> {
            sleep(5);
            return 10;
        });
        out.println("I will be printed quickly...");
        Thread caller = Thread.currentThread();
        new Thread(() -> {
            sleep(3);
            caller.interrupt();
        }).start();
        Integer value = future.get();
        out.println(value);
    }

    private static void testGetWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
        Future<Integer> future = executor.submit(() -> {
            sleep(5);
            return 10;
        });
        out.println("I will be printed quickly...");
//        Thread caller = Thread.currentThread();
//        new Thread(() -> {
//            sleep(3);
//            caller.interrupt();
//        }).start();start
        Integer value = future.get(5, TimeUnit.SECONDS);
        out.println(value);
    }
}
