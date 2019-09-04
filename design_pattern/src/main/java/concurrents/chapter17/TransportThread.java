package concurrents.chapter17;

import java.util.Random;

/***************************************
 * @author:Alex Wang
 * @Date:2017/3/25 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class TransportThread extends Thread {
    private static final Random random = new Random(System.currentTimeMillis());
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