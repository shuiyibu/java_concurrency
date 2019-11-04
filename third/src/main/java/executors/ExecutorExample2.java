package executors;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorExample2 {
    public static void main(String[] args) throws InterruptedException {
        Optional.of(Runtime.getRuntime().availableProcessors()).ifPresent(out::println);
        ExecutorService service = Executors.newWorkStealingPool();
        List<Callable<String>> callableList = IntStream.range(0, 20).boxed().map(i ->
                (Callable<String>) () -> {
                    out.println(Thread.currentThread().getName());
                    sleep(10);
                    return "task-" + i;
                }
        ).collect(Collectors.toList());
        service.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(out::println);
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
