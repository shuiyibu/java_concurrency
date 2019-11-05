package atomic;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-19 16:16
 * @Description:
 */
public class AtomicBooleanFlag {
    private static final AtomicBoolean flag = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                while (flag.get()) {
                    try {
                        out.println("I am working");//println 有synchronize关键字
                        Thread.sleep(1000);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                out.println("finished...");
            }
        }.start();
        Thread.sleep(10000);
        flag.set(false);
    }
}
