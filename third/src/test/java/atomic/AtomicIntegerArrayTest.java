package atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static org.junit.Assert.assertEquals;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 10:22
 * @Description:
 */
public class AtomicIntegerArrayTest {
    @Test
    public void testAtomicIntegerArray() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(10, array.length());
    }


    @Test
    public void testGet() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(0, array.get(5));
    }

    @Test
    public void testSet() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        array.set(5, 5);
        assertEquals(5, array.get(5));
    }
}
