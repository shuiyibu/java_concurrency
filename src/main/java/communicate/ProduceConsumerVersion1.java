package communicate;

/***
 * 生产者消费者之间无通信
 */
public class ProduceConsumerVersion1 {

    final private Object LOCK = new Object();
    private int i = 1;

    public static void main(String[] args) {

        ProduceConsumerVersion1 pc = new ProduceConsumerVersion1();

        new Thread(() -> {
            while (true) pc.produce();
        }, "P").start();

        new Thread(() -> {
            while (true) pc.consume();
        }, "C").start();


    }

    private void produce() {
        synchronized (LOCK) {
            System.out.println("P->" + (i++));
        }
    }

    private void consume() {
        synchronized (LOCK) {
            System.out.println("C->" + i);
        }
    }
}
