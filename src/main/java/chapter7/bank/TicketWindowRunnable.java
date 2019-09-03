package chapter7.bank;

/***
 * 线程数据同步
 */
public class TicketWindowRunnable implements Runnable {

    private final static int MAX = 500;
    private final Object MONITOR = new Object();
    private int index = 1;

    @Override
    public void run() {

        while (true) {
            //1
            synchronized (MONITOR) {//线程数据同步
                if (index > MAX)
                    break;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " 的号码是:" + (index++));
            }
            //2
        }
    }
}