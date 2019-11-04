package executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorServiceExample3 {
    public static void main(String[] args) {
        //  test();
//        testAllowCoreThreadTimeOut();
        testRemove();
    }

    private static void test() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        out.println(executor.getActiveCount());
        executor.execute(() -> {
            sleep(12);
        });
        out.println(executor.getActiveCount());

    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 必须设置KeepAliveTime否则会报以下错误：
     * Exception in thread "main" java.lang.IllegalArgumentException: Core threads must have nonzero keep alive times
     * at java.util.concurrent.ThreadPoolExecutor.allowCoreThreadTimeOut(ThreadPoolExecutor.java:1658)
     */
    private static void testAllowCoreThreadTimeOut() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
        IntStream.range(0, 5).boxed().forEach(i -> executor.execute(() -> sleep(10)));
    }

    private static void testRemove() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
        IntStream.range(0, 2).boxed().forEach(i -> executor.execute(() -> {
            sleep(10);
            out.println(" I am finished.");
        }));
        sleep(2);
        Runnable runnable = () -> out.println("I will never be executed.");
        executor.execute(runnable);
        executor.remove(runnable);
    }
}
