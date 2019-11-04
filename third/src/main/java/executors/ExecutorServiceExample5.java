package executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.out;

public class ExecutorServiceExample5 {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executor.execute(() -> out.println("new"));
        executor.getQueue().add(() -> out.println("add"));
    }
}
