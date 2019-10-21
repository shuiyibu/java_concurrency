package communicate;

import java.util.stream.Stream;

/**
 *
 */
public class DifferenceOfWaitAndSleep {

    private final static Object LOCK = new Object();

    public static void main(String[] args) {
        Stream.of("T1", "T2").forEach(name ->
                new Thread(name) {
                    @Override
                    public void run() {
                        m1();
                        m2();
                    }
                }.start()
        );
    }

    public static void m1() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread m1 " + Thread.currentThread().getName() + " enter.");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void m2() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread m2 " + Thread.currentThread().getName() + " enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}