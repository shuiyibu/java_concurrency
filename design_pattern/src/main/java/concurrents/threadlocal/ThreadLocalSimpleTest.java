package concurrents.threadlocal;

/**
 *
 */
public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Alex");

    //JVM start main thread
    public static void main(String[] args) throws InterruptedException {
//        threadLocal.set("Alex");
        Thread.sleep(1000);
        String value = threadLocal.get();
        System.out.println(value);
    }
}