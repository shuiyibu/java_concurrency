package locks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class ForkJoinRecursiveTask {
    private final static int MAX_THRESHOLD = 10;


    public static void main(String[] args) {

        Long start = currentTimeMillis();

        int sum = 0;
        for (int i = 0; i < 1000_000; i++) {
            sum += i;

        }
        Long end = currentTimeMillis();
        out.println(end - start);
        Long start1 = currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> future = pool.submit(new CalculateRecursiveTask(0, 1000_000));
        try {
            Integer result = future.get();
            out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Long end2 = currentTimeMillis();
        out.println(end2 - start1);
    }

    private static class CalculateRecursiveTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= MAX_THRESHOLD) {
                return IntStream.range(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveTask leftTask = new CalculateRecursiveTask(start, middle);
                CalculateRecursiveTask rightTask = new CalculateRecursiveTask(middle, end);

                leftTask.fork();
                rightTask.fork();

                return leftTask.join() + rightTask.join();
            }
        }
    }
}
