package atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

/**
 * @Auther: langdylan
 * @Date: 2019-09-20 09:43
 * @Description:
 */
public class AtomicLongTest {

    @Test
    public void testCreate() {
        AtomicLong atomicLong = new AtomicLong(100L);
        /**
         * VMSupportsCSB
         * int 32
         * long 64
         */
        assertEquals(100L, atomicLong.get());
    }
}
