package communicate;

import java.util.stream.Stream;

/**
 * wait notify实现线程间通信
 * 当出现多个生产者和消费者时，会出现问题
 * notify无法确认唤醒线程
 */
public class ProduceConsumerVersion2 {

    final private Object LOCK = new Object();
    private int i = 0;
    private volatile boolean isProduced = false;

    public static void main(String[] args) {
        ProduceConsumerVersion2 pc = new ProduceConsumerVersion2();
        Stream.of("P1", "P2").forEach(n ->
                new Thread(() -> {
                    while (true) pc.produce();
                }, n).start()
        );
        Stream.of("C1", "C2").forEach(n ->
                new Thread(() -> {
                    while (true) pc.consume();
                }, n).start()

        );
    }

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->" + i);
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}