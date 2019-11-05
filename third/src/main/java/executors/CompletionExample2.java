package executors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.out;

public class CompletionExample2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

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

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

        List<Future<Integer>> futures = new ArrayList<>();
        callableList.stream().forEach(callable -> futures.add(completionService.submit(callable)));
//        Future<Integer> future;
//        while ((future = completionService.take()) != null) {
//            out.println(future.get());
//        }
        out.println(completionService.poll());

    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
