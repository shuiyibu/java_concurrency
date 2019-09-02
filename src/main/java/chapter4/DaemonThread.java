package chapter4;

/**
 *
 */
public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName() + " done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); //new
        t.setDaemon(true);//随着main线程的结束，t线程也随之结束
        t.start();//runnable

        //runnable  ->running| ->dead| ->blocked


        Thread.sleep(5_000);   //JDK1.7
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 * A<---------------------------------->B
 * ->daemonThread(health check)
 */