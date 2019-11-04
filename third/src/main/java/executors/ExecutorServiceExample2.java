package executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorServiceExample2 {
    public static void main(String[] args) {
//        testAbortPolicy();
//        testDiscardPolicy();
        testCallerRunsPolicy();
    }


    private static void testAbortPolicy() {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.AbortPolicy());
        IntStream.range(0, 3).boxed().forEach(i -> service.execute(() -> sleep(100)));
        sleep(1);
        service.execute(() -> out.println("failed..."));
    }

    private static void testDiscardPolicy() {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.DiscardPolicy());
        IntStream.range(0, 3).boxed().forEach(i -> service.execute(() -> sleep(100)));
        sleep(1);
        service.execute(() -> out.println("failed..."));
    }

    private static void testCallerRunsPolicy2() {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.CallerRunsPolicy());
        IntStream.range(0, 3).boxed().forEach(i -> service.execute(() -> sleep(100)));
        sleep(1);
        service.execute(() -> out.println(Thread.currentThread().getName()));
    }

    private static void testCallerRunsPolicy() {
        ExecutorService service = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), r -> {
            return new Thread(r);
        }, new ThreadPoolExecutor.DiscardPolicy());
        IntStream.range(0, 3).boxed().forEach(i -> service.execute(() -> sleep(100)));
        sleep(1);
        service.execute(() -> out.println(Thread.currentThread().getName()));
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
