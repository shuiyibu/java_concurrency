package utils.condition;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class ComBetweenThreads {


    private final static Object MONITOR = new Object();
    private static int data = 0;
    private static boolean noUse = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) buildData();
        }).start();
        new Thread(() -> {
            while (true) useData();
        }).start();
    }

    private static void buildData() {
        synchronized (MONITOR) {
            while (noUse) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data++;
            sleep(1);
            out.println("P=>" + data);
            noUse = true;
            MONITOR.notify();
        }
    }

    private static void useData() {
        synchronized (MONITOR) {
            while (!noUse) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sleep(1);

            out.println("C=>" + data);
            noUse = false;
            MONITOR.notifyAll();
        }
    }

    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
