package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 15:10
 * @Description:
 */
public class AtomicIntegerFiledUpdate {
    private volatile int i;
    private AtomicIntegerFieldUpdater<AtomicIntegerFiledUpdate> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFiledUpdate.class, "i");

    public static void main(String[] args) {
        AtomicIntegerFiledUpdate test = new AtomicIntegerFiledUpdate();
        test.update(10);
        out.println(test.get());
    }

    public void update(int newValue) {
        updater.compareAndSet(this, i, newValue);
    }

    public int get() {
        return i;
    }
}
