package executors;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class CompletableFutureExample3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Optional.of(CompletableFuture.supplyAsync(() -> "Hello").whenCompleteAsync((v, t) -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("done");

        }).get()).ifPresent(out::println);

//        CompletableFuture.supplyAsync(() -> "Hello").whenCompleteAsync((v, t) -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            out.println("done");
//
//        };
    }
}
