package chapter6;

/**
 *
 */
public class ThreadInterrupt {


    /**
     * interrupt
     * java.lang.InterruptedException
     *
     * @param args
     */
    public static void main(String[] args) {
        //模拟打断join状态下的线程
        Thread t = new Thread(() -> {
            while (true) {

            }
        });

        t.start();
        Thread main = Thread.currentThread();
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            main.interrupt();//main线程打断
            System.out.println("interrupt");
        });

        t2.start();
        try {
            t.join();//这里是使main线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
