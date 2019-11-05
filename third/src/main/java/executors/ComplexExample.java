package executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

/**
 * @Auther: langdylan
 * @Date: 2019-11-05 21:41
 * @Description:
 */
public class ComplexExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(1);
//        List<Runnable> tasks = IntStream.range(0, 5).boxed().map(ComplexExample::toTask).collect(toList());
//        List<Future<?>> futureList = new ArrayList<>();
//        tasks.forEach(r -> futureList.add(service.submit(r)));
//        futureList.get(4).get();
//        out.println("=============4===================");
//        futureList.get(3).get();
//        out.println("=============3===================");
//        futureList.get(2).get();
//        out.println("=============2===================");
//        futureList.get(1).get();
//        out.println("=============1===================");
//        futureList.get(0).get();
//        out.println("=============0===================");

//        final CompletionService<Object> completionService = new ExecutorCompletionService(service);
//        tasks.forEach(r -> completionService.submit(Executors.callable(r)));
        Future future;
//        while ((future = completionService.take()) != null) {
//            out.println(future.get());
//        }
        //trap
//        sleep(2);
//        List<Runnable> shutdownNow = service.shutdownNow();
//        out.println(shutdownNow.size());
//        out.println(shutdownNow);
        //trap
        List<Callable<Integer>> tasks = IntStream.range(0, 5).boxed().map(MyTask::new).collect(toList());
        final CompletionService<Integer> completionService = new ExecutorCompletionService(service);

        tasks.forEach(completionService::submit);
        sleep(2);

        List<Runnable> shutdownNow = service.shutdownNow();
        tasks.stream().map(callable -> (MyTask) callable).filter(task -> !task.isSuccess()).forEach(out::println);


    }

    private static Runnable toTask(int i) {
        return () -> {
            out.println("The task " + i + " will be executed.");
            sleep(i);
            out.println("The task " + i + " has been executed.");
        };
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds * 5 + 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            out.println("The task " + seconds + " was interruped.");
        }
    }

    private static class MyTask implements Callable<Integer> {
        private final Integer value;
        private boolean success = false;

        private MyTask(Integer value) {
            this.value = value;
        }


        @Override
        public Integer call() throws Exception {
            out.println("The task " + value + " will be executed.");
            // sleep(value);
            TimeUnit.SECONDS.sleep(value * 5 + 10);
            out.println("The task " + value + " has been executed.");
            success = true;
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
