package executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ThreadPoolExecutorLongTimeTask {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue<>(10);
        ThreadFactory factory = r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, queue, factory, handler);

        IntStream.range(0, 20).boxed().forEach(i -> executor.execute(() -> {
            while (true) {
            }
        }));
        executor.shutdownNow();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        out.println("=======================================");
    }
}
