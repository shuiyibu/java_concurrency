package executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ThreadPoolExecutorTask {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue<>(10);
        ThreadFactory factory = r -> new Thread(r);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, queue, factory, handler);

        IntStream.range(0, 20).boxed().forEach(i -> executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                out.println(Thread.currentThread().getName() + "[" + i + "] finish done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        try {
            executor.shutdownNow();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        out.println("=======================================");
        Thread.sleep(10000);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        out.println("=======================================");
//        executor.
    }
}
