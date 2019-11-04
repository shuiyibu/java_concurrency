package locks;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ForkJoinRecurveAction {
    private final static int MAX_THRESHOLD = 3;
    private final static AtomicInteger SUM = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ForkJoinPool pool = new ForkJoinPool();
        pool.submit(() -> {
        }, new CalculateRecursiveAction(0, 10));
        pool.awaitTermination(3, TimeUnit.SECONDS);
        Optional.of(SUM).ifPresent(out::println);
    }

    private static class CalculateRecursiveAction extends RecursiveAction {
        private final int start;
        private final int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveAction left = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction right = new CalculateRecursiveAction(middle + 1, end);
                left.fork();
                right.fork();
            }
        }
    }
}
