package concurrents.consumerproducer;

import java.util.Random;

/***
 *
 */
public class ConsumerThread extends Thread {

    private final static Random random = new Random(System.currentTimeMillis());
    private final MessageQueue messageQueue;

    public ConsumerThread(MessageQueue messageQueue, int seq) {
        super("Consumer-" + seq);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = messageQueue.take();
                System.out.println(Thread.currentThread().getName() + " take a message " + message.getData());
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
