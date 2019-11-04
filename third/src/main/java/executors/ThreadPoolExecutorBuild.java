package executors;

import java.util.concurrent.*;

import static java.lang.System.out;

public class ThreadPoolExecutorBuild {
    public static void main(String[] args) {
        ThreadPoolExecutor service = buildThreadPoolExecutor();
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != service.getActiveCount() || queueSize != service.getQueue().size()) {
                out.println("ActiveCount: " + service.getActiveCount());
                out.println("CorePoolSize: " + service.getCorePoolSize());
                out.println("Queue: " + service.getQueue().size());
                out.println("MaximumPoolSize: " + service.getMaximumPoolSize());
                activeCount = service.getActiveCount();
                queueSize = service.getQueue().size();
                out.println("============================================================================================");
            }

        }
    }

    /**
     * Testing Point
     * <p>
     * core size=1 maxSize=2 blockQueue is null then 3 submitting tasks
     * core size=1 maxSize=2 blockQueue=5 then 7 submitting tasks
     * core size=1 maxSize=2 blockQueue=5 then 8 submitting tasks
     * </p>
     */
    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        BlockingQueue queue = new ArrayBlockingQueue<>(1);
        ThreadFactory factory = r -> new Thread(r);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, queue, factory, handler);
        executor.execute(() -> sleepSeconds(100));
        executor.execute(() -> sleepSeconds(10));
        executor.execute(() -> sleepSeconds(10));
        executor.execute(() -> sleepSeconds(10));
        return executor;
    }

    private static void sleepSeconds(long seconds) {
        try {
            out.println("* " + Thread.currentThread().getName() + " *");
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
