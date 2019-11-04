package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorExample1 {
    public static void main(String[] args) {
        useCachedThreadPool();

    }


    private static void useSingleThreadPool() {
        ExecutorService service = Executors.newSingleThreadExecutor();
    }

    private static void useFixedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(10);
    }

    /**
     * These pools will typically improve the performance of programs that execute many short-lived asynchronous tasks.
     */
    private static void useCachedThreadPool() {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 100).boxed().forEach(i -> service.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            out.println(Thread.currentThread().getName() + "[" + i + "]");
        }));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println(((ThreadPoolExecutor) service).getActiveCount());
    }
}
