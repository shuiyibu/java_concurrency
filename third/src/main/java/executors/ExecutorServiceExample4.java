package executors;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorServiceExample4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testInvokeAny();
//        testInvokeAnyTimeout();
        testInvokeAll();
    }

    private static void testInvokeAnyTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Integer value = service.invokeAny(IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) () -> {
            sleep();
            return i;
        }).collect(Collectors.toList()));
        out.println("========================================");
        out.println(value);
    }

    private static void testInvokeAny() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Integer value = service.invokeAny(IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) () -> {
            sleep();
            return i;
        }).collect(Collectors.toList()), 1, TimeUnit.SECONDS);
        out.println("========================================");
        out.println(value);
    }

    /**
     * RxJava
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testInvokeAll() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.invokeAll(IntStream.range(0, 10).boxed().map(i -> (Callable<Integer>) () -> {
            sleep();
            return i;
        }).collect(Collectors.toList())).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(out::println);
        out.println("========================================");
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(5);
//            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
