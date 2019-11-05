package atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 14:45
 * @Description:
 */
public class AtomicIntegerFieldUpdaterFailTest {
    /**
     * can not access a member of class atomic.TestMe with modifiers "private volatile"
     */
    @Test
    public void test() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe me = new TestMe();
        updater.compareAndSet(me, 0, 1);
    }
}
