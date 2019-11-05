package atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Auther: langdylan
 * @Date: 2019-09-19 16:01
 * @Description:
 */
public class AtomicBooleanTest {
    @Test
    public void testCreateNoArg() {
        AtomicBoolean bool = new AtomicBoolean();
        assertFalse(bool.get());
    }

    @Test
    public void testCreate() {
        AtomicBoolean bool = new AtomicBoolean(true);
        assertTrue(bool.get());
    }

    @Test
    public void testGetAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.getAndSet(false);
        assertTrue(result);
        assertFalse(bool.get());
    }

    @Test
    public void testCompareAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(true, false);
        assertTrue(result);
        assertFalse(bool.get());
    }

    @Test
    public void testCompareAndSetFailed() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(false, false);
        assertFalse(result);
        assertTrue(bool.get());
    }
}
