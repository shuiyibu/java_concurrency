package executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class ExecutorServiceExample1 {
    /**
     * {@link java.util.concurrent.ExecutorService}
     *
     * @param args
     */

    public static void main(String[] args) throws InterruptedException {
//        isShutdown();
//        isTerminated();
//        executeRunnableError();
        executeRunnableTask();
    }

    private static void isShutdown() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            sleep(10);
        });

        out.println(service.isShutdown());
        service.shutdown();
        out.println(service.isShutdown());
        service.execute(() -> out.println("done...."));//RejectedExecutionException
    }

    /**
     * {@link ExecutorService#isTerminated()}
     * {@link java.util.concurrent.ThreadPoolExecutor#isTerminated()}
     */
    private static void executeRunnableError() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10, new MyThreadFactory());
        service.execute(() -> sleep(2));
        IntStream.range(0, 10).boxed().forEach(i -> service.execute(() -> out.println(1 / 0)));
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
        out.println("===================");
    }

    private static void isTerminated() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(() -> sleep(10));
        service.shutdown();
        out.println(service.isShutdown());
        out.println(service.isTerminated());
        out.println(((ThreadPoolExecutor) service).isTerminating());
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * | --->
     * send request---> store db --->5--> | --->
     * | --->
     * | --->
     * | --->
     */
    private static void executeRunnableTask() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(0, 10).boxed().forEach(i -> service.execute(new MyTask(i) {
            @Override
            protected void error(Throwable cause) {
                out.println("The no:" + i + " failed, update status to ERROR.");
            }

            @Override
            protected void done() {
                out.println("The no: " + i + " successfully, update status to DONE.");
            }

            @Override
            protected void doExecute() {
                if (i % 3 == 0) out.println(i / 0);
            }

            @Override
            protected void doInit() {

            }
        }));
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
        out.println("===================");
    }

    /**
     * 模板方法
     */
    private abstract static class MyTask implements Runnable {
        protected final int no;

        protected MyTask(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecute();
                this.done();
            } catch (Throwable cause) {
                this.error(cause);
            }
        }

        protected abstract void error(Throwable cause);

        protected abstract void done();

        protected abstract void doExecute();

        protected abstract void doInit();
    }

    private static class MyThreadFactory implements ThreadFactory {
        private final static AtomicInteger SEQ = new AtomicInteger();


        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("My-Thread-" + SEQ.getAndIncrement());
            thread.setUncaughtExceptionHandler((t, cause) -> {
                out.println("The thread " + t.getName() + " execute failed.");
                cause.printStackTrace();
                out.println("=============");
            });
            return thread;
        }
    }
}
