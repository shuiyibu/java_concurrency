package concurrents.threadlocal;

import java.util.Random;

/***
 *
 */
public class ThreadLocalComplexTest {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    //seed
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new TestRunnable("Thread-T1"));
        Thread t2 = new Thread(new TestRunnable("Thread-T2"));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("============");
        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }

    static class TestRunnable implements Runnable {
        private String threadLocalName;

        public TestRunnable(String threadLocalName) {
            this.threadLocalName = threadLocalName;
        }

        @Override
        public void run() {
            threadLocal.set(threadLocalName);
            try {
                Thread.sleep(random.nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}