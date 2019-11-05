package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 14:37
 * @Description:
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe me = new TestMe();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                final int MAX = 20;
                for (int j = 0; j < MAX; j++) {
                    int v = updater.getAndIncrement(me);
                    out.println(Thread.currentThread().getName() + " => " + v);
                }
            }).start();
        }
    }

    static class TestMe {
        volatile int i;
    }
}
