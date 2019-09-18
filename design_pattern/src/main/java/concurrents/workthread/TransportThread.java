package concurrents.workthread;

import java.util.Random;

/**
 * 搬运货物至传送带
 */
public class TransportThread extends Thread {
    private static final Random random = new Random(System.currentTimeMillis());//Random线程安全，只需一个实现多线程共享
    private final Channel channel;

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                Thread.sleep(random.nextInt(1_000));
            }
        } catch (Exception e) {
        }
    }
}