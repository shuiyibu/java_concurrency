package executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * future无法提供callback
 */
public class CompletionExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        futureDefect2();
    }


    private static void futureDefect1() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<Integer> future = service.submit(() -> 100);
        out.println("=============================");
        Integer value = future.get();
    }

    private static void futureDefect2() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        final List<Callable<Integer>> callableList = Arrays.asList(() -> {
            sleep(10);
            out.println("The 10 finished.");
            return 10;
        }, () -> {
            sleep(20);
            out.println("The 20 finished.");
            return 20;
        });

        List<Future<Integer>> future = service.invokeAll(callableList);
        out.println("=============================");
        Integer value = future.get(0).get();
        out.println(value);
        Integer value2 = future.get(1).get();
        out.println(value2);

    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
