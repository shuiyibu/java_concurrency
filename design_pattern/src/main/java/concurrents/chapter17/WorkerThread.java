package concurrents.chapter17;

import java.util.Random;

/***************************************
 * @author:Alex Wang
 * @Date:2017/3/25 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class WorkerThread extends Thread {

    private static final Random random = new Random(System.currentTimeMillis());
    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            channel.take().execute();
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}